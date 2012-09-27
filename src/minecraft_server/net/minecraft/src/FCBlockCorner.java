package net.minecraft.src;

import java.util.Random;

public class FCBlockCorner extends Block implements FCIBlock
{
    public static final int m_iNumSubtypes = 16;
    public static final int m_iStoneTextureID = 1;
    public static final int m_iWoodTextureID = 4;
    private static final float fCornerWidth = 0.5F;
    private static final float fCornerWidthOffset = 0.5F;

    protected FCBlockCorner(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(1.5F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcCorner");
        this.blockIndexInTexture = 0;
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
        return (var1 & 8) == 0 ? mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID : super.idDropped(var1, var2, var3);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return var1 & 8;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return (var2 & 8) > 0 ? 1 : 4;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        float var5 = (float)var2;
        float var6 = var5 + 0.5F;
        float var7 = (float)var3;
        float var8 = var7 + 0.5F;
        float var9 = (float)var4;
        float var10 = var9 + 0.5F;

        if (this.IsIOffset(var1, var2, var3, var4))
        {
            var5 += 0.5F;
            var6 += 0.5F;
        }

        if (this.IsJOffset(var1, var2, var3, var4))
        {
            var7 += 0.5F;
            var8 += 0.5F;
        }

        if (this.IsKOffset(var1, var2, var3, var4))
        {
            var9 += 0.5F;
            var10 += 0.5F;
        }

        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var5, (double)var7, (double)var9, (double)var6, (double)var8, (double)var10);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        float var5 = 0.0F;
        float var6 = var5 + 0.5F;
        float var7 = 0.0F;
        float var8 = var7 + 0.5F;
        float var9 = 0.0F;
        float var10 = var9 + 0.5F;

        if (this.IsIOffset(var1, var2, var3, var4))
        {
            var5 += 0.5F;
            var6 += 0.5F;
        }

        if (this.IsJOffset(var1, var2, var3, var4))
        {
            var7 += 0.5F;
            var8 += 0.5F;
        }

        if (this.IsKOffset(var1, var2, var3, var4))
        {
            var9 += 0.5F;
            var10 += 0.5F;
        }

        this.setBlockBounds(var5, var7, var9, var6, var8, var10);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }

    private boolean IsPlayerClickOffsetOnAxis(float var1)
    {
        return var1 > 0.0F && var1 >= 0.5F;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
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

        this.SetCornerAlignment(var1, var2, var3, var4, var9, var10, var11);
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
        return !this.IsJOffset(var1, var2, var3, var4);
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
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        int var7 = this.RotateMetadataAroundJAxis(var6, var5);

        if (var7 != var6)
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        }
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        boolean var3 = this.IsIOffset(var1);
        boolean var4 = this.IsJOffset(var1);
        boolean var5 = this.IsKOffset(var1);

        if (var2)
        {
            if (var3)
            {
                if (var5)
                {
                    var3 = false;
                }
                else
                {
                    var5 = true;
                }
            }
            else if (var5)
            {
                var5 = false;
            }
            else
            {
                var3 = true;
            }
        }
        else if (var3)
        {
            if (var5)
            {
                var5 = false;
            }
            else
            {
                var3 = false;
            }
        }
        else if (var5)
        {
            var3 = true;
        }
        else
        {
            var5 = true;
        }

        return this.SetCornerAlignmentInMetadata(var1, var3, var4, var5);
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetCornerAlignment(var1, var2, var3, var4);

        if (!var5)
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

        this.SetCornerAlignment(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        return true;
    }

    public int GetCornerAlignment(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & 7;
    }

    public void SetCornerAlignment(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 8;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public void SetCornerAlignment(World var1, int var2, int var3, int var4, boolean var5, boolean var6, boolean var7)
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

        this.SetCornerAlignment(var1, var2, var3, var4, var8);
    }

    public int SetCornerAlignmentInMetadata(int var1, int var2)
    {
        var1 &= 8;
        var1 |= var2;
        return var1;
    }

    public int SetCornerAlignmentInMetadata(int var1, boolean var2, boolean var3, boolean var4)
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

        return this.SetCornerAlignmentInMetadata(var1, var5);
    }

    public boolean IsIOffset(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.IsIOffset(var1.getBlockMetadata(var2, var3, var4));
    }

    public boolean IsIOffset(int var1)
    {
        return (var1 & 4) > 0;
    }

    public boolean IsJOffset(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.IsJOffset(var1.getBlockMetadata(var2, var3, var4));
    }

    public boolean IsJOffset(int var1)
    {
        return (var1 & 2) > 0;
    }

    public boolean IsKOffset(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.IsKOffset(var1.getBlockMetadata(var2, var3, var4));
    }

    public boolean IsKOffset(int var1)
    {
        return (var1 & 1) > 0;
    }

    public boolean GetIsStone(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetIsStone(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 7;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    private boolean GetIOffsetOnPlaceFromNeighbours(World var1, int var2, int var3, int var4)
    {
        return !var1.isAirBlock(var2 + 1, var3, var4) ? (!var1.isAirBlock(var2 - 1, var3, var4) ? var1.rand.nextInt(2) > 0 : true) : (var1.isAirBlock(var2 - 1, var3, var4) ? var1.rand.nextInt(2) > 0 : false);
    }

    private boolean GetJOffsetOnPlaceFromNeighbours(World var1, int var2, int var3, int var4)
    {
        return !var1.isAirBlock(var2, var3 + 1, var4) ? (!var1.isAirBlock(var2, var3 - 1, var4) ? var1.rand.nextInt(2) > 0 : true) : (var1.isAirBlock(var2, var3 - 1, var4) ? var1.rand.nextInt(2) > 0 : false);
    }

    private boolean GetKOffsetOnPlaceFromNeighbours(World var1, int var2, int var3, int var4)
    {
        return !var1.isAirBlock(var2, var3, var4 + 1) ? (!var1.isAirBlock(var2, var3, var4 - 1) ? var1.rand.nextInt(2) > 0 : true) : (var1.isAirBlock(var2, var3, var4 - 1) ? var1.rand.nextInt(2) > 0 : false);
    }
}
