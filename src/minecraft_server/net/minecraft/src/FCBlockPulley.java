package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockPulley extends BlockContainer implements FCIMechanicalDevice, FCIBlock, ITextureProvider
{
    private final int iPulleyTopIndex = 62;
    private final int iPulleySideIndex = 63;
    private final int iPulleyBottomIndex = 64;
    private static int iPulleyTickRate = 10;

    protected FCBlockPulley(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcPulley");
        this.blockIndexInTexture = 62;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 0 ? 64 : (var1 == 1 ? 62 : 63);
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
            FCTileEntityPulley var13 = (FCTileEntityPulley)var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof EntityPlayerMP)
            {
                FCContainerPulley var12 = new FCContainerPulley(var5.inventory, var13);
                mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var12, mod_FCBetterThanWolves.fcPulleyContainerID, 0, 0, 0);
            }
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityPulley();
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
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        TileEntity var7 = var1.getBlockTileEntity(var2, var3, var4);

        if (var7 != null)
        {
            FCUtilsInventory.EjectInventoryContents(var1, var2, var3, var4, (IInventory)var7);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return iPulleyTickRate;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsBlockOn(var1, var2, var3, var4);
        boolean var8 = false;

        if (var7 != var6)
        {
            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.old_explode", 0.2F, 1.25F);
            this.EmitPulleyParticles(var1, var2, var3, var4, var5);
            this.SetBlockOn(var1, var2, var3, var4, var6);
            var8 = true;
        }

        boolean var9 = this.IsRedstoneOn(var1, var2, var3, var4);
        boolean var10 = var1.isBlockGettingPowered(var2, var3, var4) || var1.isBlockGettingPowered(var2, var3 + 1, var4);

        if (var9 != var10)
        {
            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.old_explode", 0.2F, 1.25F);
            this.EmitPulleyParticles(var1, var2, var3, var4, var5);
            this.SetRedstoneOn(var1, var2, var3, var4, var10);
            var8 = true;
        }

        if (var8)
        {
            ((FCTileEntityPulley)var1.getBlockTileEntity(var2, var3, var4)).NotifyPulleyEntityOfBlockStateChange();
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
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
        return true;
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
        for (int var5 = 2; var5 <= 5; ++var5)
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

    public void Overpower(World var1, int var2, int var3, int var4)
    {
        this.BreakPulley(var1, var2, var3, var4);
    }

    public boolean IsBlockOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 1) > 0;
    }

    public void SetBlockOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var5)
        {
            var6 |= 1;
        }
        else
        {
            var6 &= -2;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean IsRedstoneOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 2) > 0;
    }

    public void SetRedstoneOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -3;

        if (var5)
        {
            var6 |= 2;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    void EmitPulleyParticles(World var1, int var2, int var3, int var4, Random var5)
    {
        for (int var6 = 0; var6 < 5; ++var6)
        {
            float var7 = (float)var2 + var5.nextFloat();
            float var8 = (float)var3 + var5.nextFloat() * 0.5F + 1.0F;
            float var9 = (float)var4 + var5.nextFloat();
            var1.spawnParticle("smoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
        }
    }

    public void BreakPulley(World var1, int var2, int var3, int var4)
    {
        int var5;

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.planks.blockID, 0);
        }

        for (var5 = 0; var5 < 1; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
        }

        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Item.ingotIron.shiftedIndex, 0);
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Item.redstone.shiftedIndex, 0);
        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}
