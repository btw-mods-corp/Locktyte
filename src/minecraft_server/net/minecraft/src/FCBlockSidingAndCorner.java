package net.minecraft.src;

import java.util.Random;

public class FCBlockSidingAndCorner extends Block implements FCIBlock, FCIBlockSolidTop
{
    public static float m_fSidingHeight = 0.5F;
    private static final float m_fCornerWidth = 0.5F;
    private static final float m_fCornerWidthOffset = 0.5F;

    protected FCBlockSidingAndCorner(int var1, Material var2, int var3, float var4, float var5, StepSound var6, String var7)
    {
        super(var1, var2);
        this.blockIndexInTexture = var3;
        this.setHardness(var4);
        this.setResistance(var5);
        this.setStepSound(var6);
        this.setBlockName(var7);
        this.setRequiresSelfNotify();
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
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return this.blockID != mod_FCBetterThanWolves.fcBlockWoodSpruceSidingAndCorner.blockID && this.blockID != mod_FCBetterThanWolves.fcBlockWoodBirchSidingAndCorner.blockID && this.blockID != mod_FCBetterThanWolves.fcBlockWoodJungleSidingAndCorner.blockID ? super.idDropped(var1, var2, var3) : ((var1 & 1) == 0 ? mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID : mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return this.blockID == mod_FCBetterThanWolves.fcBlockWoodSpruceSidingAndCorner.blockID ? 1 : (this.blockID == mod_FCBetterThanWolves.fcBlockWoodBirchSidingAndCorner.blockID ? 2 : (this.blockID == mod_FCBetterThanWolves.fcBlockWoodJungleSidingAndCorner.blockID ? 3 : var1 & 1));
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            switch (var5)
            {
                case 0:
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 1.0F - m_fSidingHeight), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

                case 1:
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + m_fSidingHeight), (double)((float)var4 + 1.0F));

                case 2:
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4 + 1.0F - m_fSidingHeight), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

                case 3:
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + m_fSidingHeight));

                case 4:
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 1.0F - m_fSidingHeight), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

                default:
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + m_fSidingHeight), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
            }
        }
        else
        {
            float var6 = (float)var2;
            float var7 = var6 + 0.5F;
            float var8 = (float)var3;
            float var9 = var8 + 0.5F;
            float var10 = (float)var4;
            float var11 = var10 + 0.5F;

            if (this.IsCornerFacingIOffset(var5))
            {
                var6 += 0.5F;
                var7 += 0.5F;
            }

            if (this.IsCornerFacingJOffset(var5))
            {
                var8 += 0.5F;
                var9 += 0.5F;
            }

            if (this.IsCornerFacingKOffset(var5))
            {
                var10 += 0.5F;
                var11 += 0.5F;
            }

            return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var6, (double)var8, (double)var10, (double)var7, (double)var9, (double)var11);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            switch (var5)
            {
                case 0:
                    this.setBlockBounds(0.0F, m_fSidingHeight, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 1:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, m_fSidingHeight, 1.0F);
                    break;

                case 2:
                    this.setBlockBounds(0.0F, 0.0F, m_fSidingHeight, 1.0F, 1.0F, 1.0F);
                    break;

                case 3:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, m_fSidingHeight);
                    break;

                case 4:
                    this.setBlockBounds(m_fSidingHeight, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                default:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, m_fSidingHeight, 1.0F, 1.0F);
            }
        }
        else
        {
            float var6 = 0.0F;
            float var7 = var6 + 0.5F;
            float var8 = 0.0F;
            float var9 = var8 + 0.5F;
            float var10 = 0.0F;
            float var11 = var10 + 0.5F;

            if (this.IsCornerFacingIOffset(var5))
            {
                var6 += 0.5F;
                var7 += 0.5F;
            }

            if (this.IsCornerFacingJOffset(var5))
            {
                var8 += 0.5F;
                var9 += 0.5F;
            }

            if (this.IsCornerFacingKOffset(var5))
            {
                var10 += 0.5F;
                var11 += 0.5F;
            }

            this.setBlockBounds(var6, var8, var10, var7, var9, var11);
        }
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            this.SetFacing(var1, var2, var3, var4, var5);
        }
        else
        {
            boolean var9 = false;
            boolean var10 = false;
            boolean var11 = false;

            if (var5 == 0)
            {
                var10 = true;
                var9 = this.IsPlayerClickOffsetOnAxis(var6);
                var11 = this.IsPlayerClickOffsetOnAxis(var8);
            }
            else if (var5 == 1)
            {
                var9 = this.IsPlayerClickOffsetOnAxis(var6);
                var11 = this.IsPlayerClickOffsetOnAxis(var8);
            }
            else if (var5 == 2)
            {
                var11 = true;
                var9 = this.IsPlayerClickOffsetOnAxis(var6);
                var10 = this.IsPlayerClickOffsetOnAxis(var7);
            }
            else if (var5 == 3)
            {
                var9 = this.IsPlayerClickOffsetOnAxis(var6);
                var10 = this.IsPlayerClickOffsetOnAxis(var7);
            }
            else if (var5 == 4)
            {
                var9 = true;
                var10 = this.IsPlayerClickOffsetOnAxis(var7);
                var11 = this.IsPlayerClickOffsetOnAxis(var8);
            }
            else if (var5 == 5)
            {
                var10 = this.IsPlayerClickOffsetOnAxis(var7);
                var11 = this.IsPlayerClickOffsetOnAxis(var8);
            }

            this.SetCornerFacing(var1, var2, var3, var4, var9, var10, var11);
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

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) >> 1;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        var6 &= 1;
        var6 |= var5 << 1;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1 >> 1;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 &= 1;
        var1 |= var2 << 1;
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return !this.GetIsCorner(var1, var2, var3, var4) ? var5 != 0 : !this.IsCornerFacingJOffset(var5);
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            int var5 = this.GetFacing(var1, var2, var3, var4);

            if (var5 > 1)
            {
                return true;
            }
        }

        return false;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5);
        }
        else
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            int var7 = this.RotateMetadataAroundJAxis(var6, var5);

            if (var7 != var6)
            {
                var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
                var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            }
        }
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        int var3 = this.GetFacingFromMetadata(var1);

        if ((var1 & 1) == 0)
        {
            return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
        }
        else
        {
            boolean var4 = this.IsCornerFacingIOffset(var3);
            boolean var5 = this.IsCornerFacingJOffset(var3);
            boolean var6 = this.IsCornerFacingKOffset(var3);

            if (var2)
            {
                if (var4)
                {
                    if (var6)
                    {
                        var4 = false;
                    }
                    else
                    {
                        var6 = true;
                    }
                }
                else if (var6)
                {
                    var6 = false;
                }
                else
                {
                    var4 = true;
                }
            }
            else if (var4)
            {
                if (var6)
                {
                    var6 = false;
                }
                else
                {
                    var4 = false;
                }
            }
            else if (var6)
            {
                var4 = true;
            }
            else
            {
                var6 = true;
            }

            return this.SetCornerFacingInMetadata(var1, var4, var5, var6);
        }
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);

        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            var6 = FCUtilsMisc.CycleFacing(var6, var5);
        }
        else if (!var5)
        {
            ++var6;

            if (var6 > 7)
            {
                var6 = 0;
            }
        }
        else
        {
            --var6;

            if (var6 < 0)
            {
                var6 = 7;
            }
        }

        this.SetFacing(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        return true;
    }

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (!this.GetIsCorner(var1, var2, var3, var4))
        {
            int var5 = this.GetFacing(var1, var2, var3, var4);

            if (var5 == 0)
            {
                return true;
            }
        }

        return false;
    }

    public boolean GetIsCorner(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 1) > 0;
    }

    public boolean IsCornerFacingIOffset(int var1)
    {
        return (var1 & 4) > 0;
    }

    public boolean IsCornerFacingJOffset(int var1)
    {
        return (var1 & 2) > 0;
    }

    public boolean IsCornerFacingKOffset(int var1)
    {
        return (var1 & 1) > 0;
    }

    private boolean IsPlayerClickOffsetOnAxis(float var1)
    {
        return var1 > 0.0F && var1 >= 0.5F;
    }

    public void SetCornerFacing(World var1, int var2, int var3, int var4, boolean var5, boolean var6, boolean var7)
    {
        int var8 = 0;

        if (var5)
        {
            var8 |= 4;
        }

        if (var6)
        {
            var8 |= 2;
        }

        if (var7)
        {
            var8 |= 1;
        }

        this.SetFacing(var1, var2, var3, var4, var8);
    }

    public int SetCornerFacingInMetadata(int var1, boolean var2, boolean var3, boolean var4)
    {
        int var5 = 0;

        if (var2)
        {
            var5 |= 4;
        }

        if (var3)
        {
            var5 |= 2;
        }

        if (var4)
        {
            var5 |= 1;
        }

        return this.SetFacingInMetadata(var1, var5);
    }
}
