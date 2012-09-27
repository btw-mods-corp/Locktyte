package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockHandCrank extends Block implements FCIMechanicalDevice, ITextureProvider, FCIBlock
{
    private static int iHandCrankTickRate = 3;
    private static int iHandCrankDelayBeforeReset = 15;
    public static float fHandCrankBaseHeight = 0.25F;
    private final int iHandCrankShaftTextureIndex;
    private final int iHandCrankBaseTopTextureIndex;
    private final int iHandCrankBaseSideTextureIndex;
    private final int iHandCrankBaseBottomTextureIndex;

    protected FCBlockHandCrank(int var1)
    {
        super(var1, Material.circuits);
        this.setHardness(0.5F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcHandCrank");
        this.blockIndexInTexture = 31;
        this.iHandCrankShaftTextureIndex = 28;
        this.iHandCrankBaseTopTextureIndex = 29;
        this.iHandCrankBaseSideTextureIndex = 30;
        this.iHandCrankBaseBottomTextureIndex = 31;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return iHandCrankTickRate;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 1 ? this.iHandCrankBaseTopTextureIndex : (var1 == 0 ? this.iHandCrankBaseBottomTextureIndex : this.iHandCrankBaseSideTextureIndex);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + fHandCrankBaseHeight), (double)((float)var4 + 1.0F));
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomHandCrankRenderID;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        return var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4);
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5)
    {
        this.onBlockActivated(var1, var2, var3, var4, var5, 0, 0.0F, 0.0F, 0.0F);
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

        if (var1.isRemote)
        {
            return true;
        }
        else
        {
            int var12 = var1.getBlockMetadata(var2, var3, var4);

            if (var12 == 0)
            {
                var5.addExhaustion(3.2F);

                if (!this.CheckForOverpower(var1, var2, var3, var4))
                {
                    var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
                    var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
                    var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 1.0F, 2.0F);
                    var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                    var1.markBlockNeedsUpdate(var2, var3, var4);
                }
                else
                {
                    this.BreakCrankWithDrop(var1, var2, var3, var4);
                }
            }

            return true;
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var6 > 0)
        {
            if (var6 < 7)
            {
                if (var6 <= 6)
                {
                    var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 1.0F, 2.0F);
                }

                if (var6 <= 5)
                {
                    var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate() + var6);
                }
                else
                {
                    var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, iHandCrankDelayBeforeReset);
                }

                var1.setBlockMetadata(var2, var3, var4, var6 + 1);
            }
            else
            {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 0);
                var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
                var1.markBlockNeedsUpdate(var2, var3, var4);
                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 0.3F, 0.7F);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (!var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4))
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
            var1.setBlockWithNotify(var2, var3, var4, 0);
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
        return false;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
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
        return true;
    }

    public boolean CanInputMechanicalPower()
    {
        return false;
    }

    public boolean IsInputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) > 0;
    }

    public void Overpower(World var1, int var2, int var3, int var4) {}

    public boolean CheckForOverpower(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        for (int var6 = 0; var6 <= 5; ++var6)
        {
            if (var6 != 1)
            {
                FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);
                var7.AddFacingAsOffset(var6);
                int var8 = var1.getBlockId(var7.i, var7.j, var7.k);
                Block var9 = Block.blocksList[var8];

                if (var9 != null && var9 instanceof FCIMechanicalDevice)
                {
                    FCIMechanicalDevice var10 = (FCIMechanicalDevice)var9;

                    if (var10.CanInputMechanicalPower())
                    {
                        ++var5;
                    }
                }
            }
        }

        return var5 > 1;
    }

    public void BreakCrankWithDrop(World var1, int var2, int var3, int var4)
    {
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Item.stick.shiftedIndex, 0);
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.cobblestone.blockID, 0);
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.cobblestone.blockID, 0);
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}
