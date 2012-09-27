package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockMillStone extends BlockContainer implements FCIMechanicalDevice, FCIBlock, FCIBlockRequireClientNotification, ITextureProvider
{
    public final int m_iTopTextureIndex = 25;
    public final int m_iSideTextureIndex = 26;
    public final int m_iBottomTextureIndex = 27;
    private static int m_iTickRate = 10;
    public static final int m_iCurrentGrindingNothing = 0;
    public static final int m_iCurrentGrindingNormal = 1;
    public static final int m_iCurrentGrindingNetherrack = 2;
    public static final int m_iCurrentGrindingCompanionCube = 3;

    protected FCBlockMillStone(int var1)
    {
        super(var1, Material.rock);
        this.blockIndexInTexture = 14;
        this.setHardness(3.5F);
        this.setStepSound(soundStoneFootstep);
        this.setBlockName("fcMillStone");
        this.setRequiresSelfNotify();
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return m_iTickRate;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);

        if (this.IsMechanicalOn(var1, var2, var3, var4) != var6)
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        ItemStack var10 = var5.getCurrentEquippedItem();

        if (var10 != null && var10.getItem() instanceof FCIItem)
        {
            FCIItem var11 = (FCIItem)var10.getItem();

            if (var11.DoesItemOverrideBlockActivation())
            {
                return false;
            }
        }

        if (!var1.isRemote)
        {
            FCTileEntityMillStone var13 = (FCTileEntityMillStone)var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof EntityPlayerMP)
            {
                FCContainerMillStone var12 = new FCContainerMillStone(var5.inventory, var13);
                mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var12, mod_FCBetterThanWolves.fcMillStoneContainerID, 0, 0, 0);
            }
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityMillStone();
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsMechanicalOn(var1, var2, var3, var4);

        if (var7 != var6)
        {
            this.SetMechanicalOn(var1, var2, var3, var4, var6);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        FCUtilsInventory.EjectInventoryContents(var1, var2, var3, var4, (IInventory)var1.getBlockTileEntity(var2, var3, var4));
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 0;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5) {}

    public int GetFacingFromMetadata(int var1)
    {
        return 0;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5) {}

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return var1;
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        return false;
    }

    public boolean CanOutputMechanicalPower()
    {
        return false;
    }

    public boolean CanInputMechanicalPower()
    {
        return true;
    }

    public boolean IsInputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        for (int var5 = 0; var5 <= 1; ++var5)
        {
            if (FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, var5))
            {
                return true;
            }
        }

        return FCUtilsMechPower.IsBlockPoweredByHandCrank(var1, var2, var3, var4);
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void Overpower(World var1, int var2, int var3, int var4) {}

    public void ClientNotificationOfMetadataChange(World var1, int var2, int var3, int var4, int var5, int var6) {}

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public boolean IsMechanicalOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.IsMechanicalOnFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public void SetMechanicalOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -2;

        if (var5)
        {
            var6 |= 1;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean IsMechanicalOnFromMetadata(int var1)
    {
        return (var1 & 1) > 0;
    }

    public int GetCurrentGrindingType(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetCurrentGrindingTypeFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public void SetCurrentGrindingType(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -7;
        var6 |= var5 << 1;
        var1.setBlockMetadata(var2, var3, var4, var6);
    }

    public int GetCurrentGrindingTypeFromMetadata(int var1)
    {
        return (var1 & 6) >> 1;
    }
}
