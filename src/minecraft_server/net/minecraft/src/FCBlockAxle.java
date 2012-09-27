package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockAxle extends Block implements ITextureProvider, FCIBlock
{
    public static final float fAxleWidth = 0.25F;
    public final int iAxleTextureIDHorizontal;
    public final int iAxleTextureIDVertical;
    public static final int iAxleTickRate = 1;

    protected FCBlockAxle(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcAxle");
        this.blockIndexInTexture = 35;
        this.iAxleTextureIDHorizontal = 33;
        this.iAxleTextureIDVertical = 34;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 1;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 != 2 && var1 != 3 ? (var1 != 0 && var1 != 1 ? this.iAxleTextureIDHorizontal : this.iAxleTextureIDVertical) : this.blockIndexInTexture;
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
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        this.SetAxisAlignmentBasedOnFacing(var1, var2, var3, var4, var5);
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
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        this.SetPowerLevel(var1, var2, var3, var4, 0);
        this.ValidatePowerLevel(var1, var2, var3, var4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetAxisAlignment(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.5F - 0.125F), (double)((float)var3), (double)((float)var4 + 0.5F - 0.125F), (double)((float)var2 + 0.5F + 0.125F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.5F + 0.125F));

            case 1:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.5F - 0.125F), (double)((float)var3 + 0.5F - 0.125F), (double)((float)var4), (double)((float)var2 + 0.5F + 0.125F), (double)((float)var3 + 0.5F + 0.125F), (double)((float)var4 + 1.0F));

            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 0.5F - 0.125F), (double)((float)var4 + 0.5F - 0.125F), (double)((float)var2 + 1.0F), (double)((float)var3 + 0.5F + 0.125F), (double)((float)var4 + 0.5F + 0.125F));
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetAxisAlignment(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
                this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;

            case 1:
                this.setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
                break;

            default:
                this.setBlockBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        this.ValidatePowerLevel(var1, var2, var3, var4);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        ItemStack var10 = var5.getCurrentEquippedItem();

        if (var10 != null)
        {
            return false;
        }
        else
        {
            if (!var1.isRemote)
            {
                this.ToggleFacing(var1, var2, var3, var4, false);
                FCUtilsMisc.PlayPlaceSoundForBlock(var1, var2, var3, var4);
            }

            return true;
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetAxisAlignment(var1, var2, var3, var4) << 1;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        this.SetAxisAlignmentBasedOnFacing(var1, var2, var3, var4, var5);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return this.GetAxisAlignmentFromMetadata(var1) << 1;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        return this.SetAxisAlignmentInMetadataBasedOnFacing(var1, var2);
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

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5);
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetAxisAlignment(var1, var2, var3, var4);

        if (!var5)
        {
            ++var6;

            if (var6 > 2)
            {
                var6 = 0;
            }
        }
        else
        {
            --var6;

            if (var6 < 0)
            {
                var6 = 2;
            }
        }

        this.SetAxisAlignment(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        this.SetPowerLevel(var1, var2, var3, var4, 0);
        this.ValidatePowerLevel(var1, var2, var3, var4);
        return true;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetAxisAlignment(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) >> 2;
    }

    public void SetAxisAlignment(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 3;
        var6 |= var5 << 2;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetAxisAlignmentFromMetadata(int var1)
    {
        return var1 >> 2;
    }

    public void SetAxisAlignmentBasedOnFacing(World var1, int var2, int var3, int var4, int var5)
    {
        byte var6;

        switch (var5)
        {
            case 0:
            case 1:
                var6 = 0;
                break;

            case 2:
            case 3:
                var6 = 1;
                break;

            default:
                var6 = 2;
        }

        int var7 = var1.getBlockMetadata(var2, var3, var4) & 3;
        var7 |= var6 << 2;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
    }

    public int SetAxisAlignmentInMetadataBasedOnFacing(int var1, int var2)
    {
        byte var3;

        switch (var2)
        {
            case 0:
            case 1:
                var3 = 0;
                break;

            case 2:
            case 3:
                var3 = 1;
                break;

            default:
                var3 = 2;
        }

        var1 &= 3;
        var1 |= var3 << 2;
        return var1;
    }

    public int GetPowerLevel(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & 3;
    }

    public void SetPowerLevel(World var1, int var2, int var3, int var4, int var5)
    {
        var5 &= 3;
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 12;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean IsAxleOrientedTowardsFacing(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetAxisAlignment(var1, var2, var3, var4);

        switch (var6)
        {
            case 0:
                if (var5 == 0 || var5 == 1)
                {
                    return true;
                }

                break;

            case 1:
                if (var5 == 2 || var5 == 3)
                {
                    return true;
                }

                break;

            default:
                if (var5 == 4 || var5 == 5)
                {
                    return true;
                }
        }

        return false;
    }

    public void BreakAxle(World var1, int var2, int var3, int var4)
    {
        if (var1.getBlockId(var2, var3, var4) == this.blockID)
        {
            int var5;

            for (var5 = 0; var5 < 5; ++var5)
            {
                FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcHempFibers.shiftedIndex, 0);
            }

            for (var5 = 0; var5 < 2; ++var5)
            {
                FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Item.stick.shiftedIndex, 0);
            }

            var1.playAuxSFX(2235, var2, var3, var4, 0);
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    private void ValidatePowerLevel(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetPowerLevel(var1, var2, var3, var4);
        int var6 = this.GetAxisAlignment(var1, var2, var3, var4);

        if (var5 != 3)
        {
            FCUtilsBlockPos[] var7 = new FCUtilsBlockPos[] {new FCUtilsBlockPos(var2, var3, var4), new FCUtilsBlockPos(var2, var3, var4)};

            switch (var6)
            {
                case 0:
                    var7[0].AddFacingAsOffset(0);
                    var7[1].AddFacingAsOffset(1);
                    break;

                case 1:
                    var7[0].AddFacingAsOffset(2);
                    var7[1].AddFacingAsOffset(3);
                    break;

                default:
                    var7[0].AddFacingAsOffset(4);
                    var7[1].AddFacingAsOffset(5);
            }

            int var8 = 0;
            int var9 = 0;
            int var10;

            for (var10 = 0; var10 < 2; ++var10)
            {
                int var11 = var1.getBlockId(var7[var10].i, var7[var10].j, var7[var10].k);

                if (var11 == this.blockID)
                {
                    int var12 = this.GetAxisAlignment(var1, var7[var10].i, var7[var10].j, var7[var10].k);

                    if (var12 == var6)
                    {
                        int var13 = this.GetPowerLevel(var1, var7[var10].i, var7[var10].j, var7[var10].k);

                        if (var13 > var8)
                        {
                            var8 = var13;
                        }

                        if (var13 > var5)
                        {
                            ++var9;
                        }
                    }
                }
            }

            if (var9 >= 2)
            {
                this.BreakAxle(var1, var2, var3, var4);
                return;
            }

            if (var8 > var5)
            {
                if (var8 == 1)
                {
                    this.BreakAxle(var1, var2, var3, var4);
                    return;
                }

                var10 = var8 - 1;
            }
            else
            {
                var10 = 0;
            }

            if (var10 != var5)
            {
                this.SetPowerLevel(var1, var2, var3, var4, var10);
            }
        }
    }

    private void EmitAxleParticles(World var1, int var2, int var3, int var4, Random var5)
    {
        for (int var6 = 0; var6 < 2; ++var6)
        {
            float var7 = (float)var2 + var5.nextFloat();
            float var8 = (float)var3 + var5.nextFloat() * 0.5F + 0.625F;
            float var9 = (float)var4 + var5.nextFloat();
            var1.spawnParticle("smoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
        }
    }

    public void Overpower(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetPowerLevel(var1, var2, var3, var4);
        int var6 = this.GetAxisAlignment(var1, var2, var3, var4);
        FCUtilsBlockPos[] var7 = new FCUtilsBlockPos[] {new FCUtilsBlockPos(var2, var3, var4), new FCUtilsBlockPos(var2, var3, var4)};

        switch (var6)
        {
            case 0:
                var7[0].AddFacingAsOffset(0);
                var7[1].AddFacingAsOffset(1);
                break;

            case 1:
                var7[0].AddFacingAsOffset(2);
                var7[1].AddFacingAsOffset(3);
                break;

            default:
                var7[0].AddFacingAsOffset(4);
                var7[1].AddFacingAsOffset(5);
        }

        for (int var8 = 0; var8 < 2; ++var8)
        {
            int var9 = var1.getBlockId(var7[var8].i, var7[var8].j, var7[var8].k);

            if (var9 == this.blockID)
            {
                int var10 = this.GetAxisAlignment(var1, var7[var8].i, var7[var8].j, var7[var8].k);

                if (var10 == var6)
                {
                    int var11 = this.GetPowerLevel(var1, var7[var8].i, var7[var8].j, var7[var8].k);

                    if (var11 < var5)
                    {
                        this.Overpower(var1, var7[var8].i, var7[var8].j, var7[var8].k);
                    }
                }
            }
            else if (Block.blocksList[var9] instanceof FCIMechanicalDevice)
            {
                FCIMechanicalDevice var12 = (FCIMechanicalDevice)((FCIMechanicalDevice)Block.blocksList[var9]);
                var12.Overpower(var1, var7[var8].i, var7[var8].j, var7[var8].k);
            }
        }
    }
}
