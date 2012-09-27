package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public class FCBlockAestheticNonOpaque extends Block implements FCIBlock, ITextureProvider, FCIBlockSolidTop
{
    public static final int m_iSubtypeUrn = 0;
    public static final int m_iSubtypeColumn = 1;
    public static final int m_iSubtypePedestalUp = 2;
    public static final int m_iSubtypePedestalDown = 3;
    public static final int m_iSubtypeTable = 4;
    public static final int m_iSubtypeWickerSlab = 5;
    public static final int m_iSubtypeGrate = 6;
    public static final int m_iSubtypeWicker = 7;
    public static final int m_iSubtypeSlats = 8;
    public static final int m_iSubtypeWickerSlabUpsideDown = 9;
    public static final int m_iNumSubtypes = 10;
    private static final int m_iDefaultTextureID = 0;
    private static final int m_iUrnTextureID = 88;
    private static final int m_iColumnTopAndBottomTextureID = 89;
    private static final int m_iColumnSideTextureID = 90;
    private static final int m_iPedestalTopAndBottomTextureID = 91;
    private static final int m_iPedestalSidesTextureID = 92;
    private static final int m_iTableTopTextureID = 93;
    private static final int m_iTableLegTextureID = 94;
    private static final int m_iWickerSlabTextureID = 95;
    private static final int m_iGrateTextureID = 104;
    private static final int m_iWickerTextureID = 110;
    private static final int m_iSlatsTextureID = 116;
    private static final int m_iSlatsSideTextureID = 1;
    private static final float m_fDefaultHardness = 2.0F;
    private static final float m_fColumWidth = 0.625F;
    private static final float m_fColumHalfWidth = 0.3125F;
    private static final float m_fPedestalBaseHeight = 0.75F;
    private static final float m_fPedestalMiddleHeight = 0.125F;
    private static final float m_fPedestalMiddleWidth = 0.875F;
    private static final float m_fPedestalMiddleHalfWidth = 0.4375F;
    private static final float m_fPedestalTopHeight = 0.125F;
    private static final float m_fPedestalTopWidth = 0.75F;
    private static final float m_fPedestalTopHalfWidth = 0.375F;
    private static final float m_fTableTopHeight = 0.125F;
    private static final float m_fTableLegHeight = 0.875F;
    private static final float m_fTableLegWidth = 0.125F;
    private static final float m_fTableLegHalfWidth = 0.0625F;

    public FCBlockAestheticNonOpaque(int var1)
    {
        super(var1, Material.ground);
        this.blockIndexInTexture = 0;
        this.setHardness(2.0F);
        this.setStepSound(soundStoneFootstep);
        this.setBlockName("fcAestheticNonOpaque");
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
        return mod_FCBetterThanWolves.iCustomAestheticNonOpaqueRenderID;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        if (var1 == 3)
        {
            var1 = 2;
        }
        else if (var1 == 9)
        {
            var1 = 5;
        }
        else if (var1 == 0 || var1 == 6 || var1 == 7 || var1 == 8)
        {
            var1 = 0;
        }

        return var1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return var1 == 0 ? mod_FCBetterThanWolves.fcUrn.shiftedIndex : (var1 == 6 ? mod_FCBetterThanWolves.fcGrate.shiftedIndex : (var1 == 7 ? mod_FCBetterThanWolves.fcWicker.shiftedIndex : (var1 == 8 ? mod_FCBetterThanWolves.fcRollersItem.shiftedIndex : this.blockID)));
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        int var9 = var1.getBlockMetadata(var2, var3, var4);

        if (var9 == 2 && var5 == 0)
        {
            var1.setBlockMetadata(var2, var3, var4, 3);
        }
        else if (var9 == 5 && (var5 == 0 || var5 != 1 && (double)var7 > 0.5D))
        {
            var1.setBlockMetadata(var2, var3, var4, 9);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var6 == 2 && var5.rotationPitch < -45.0F)
        {
            var1.setBlockMetadata(var2, var3, var4, 3);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);

        switch (var5)
        {
            case 0:
                if (var1.getBlockId(var2, var3 + 1, var4) != mod_FCBetterThanWolves.fcHopper.blockID)
                {
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.3125F), (double)((float)var3), (double)((float)var4 + 0.3125F), (double)((float)var2 + 0.6875F), (double)((float)var3 + 0.625F), (double)((float)var4 + 0.6875F));
                }

                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.3125F), (double)((float)var3 + 1.0F - 0.625F), (double)((float)var4 + 0.3125F), (double)((float)var2 + 0.6875F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.6875F));

            case 1:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.1875F), (double)((float)var3), (double)((float)var4 + 0.1875F), (double)((float)var2 + 0.8125F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.8125F));

            case 2:
            case 3:
            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 4:
                if (!this.IsTableOnCorner(var1, var2, var3, var4))
                {
                    return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 1.0F - 0.125F), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
                }

                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 5:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 0.5F), (double)((float)var4 + 1.0F));

            case 6:
            case 7:
            case 8:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)var3 + this.maxY, (double)var4 + this.maxZ);

            case 9:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 0.5F), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);

        switch (var5)
        {
            case 0:
                if (var1.getBlockId(var2, var3 + 1, var4) != mod_FCBetterThanWolves.fcHopper.blockID)
                {
                    this.setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.625F, 0.6875F);
                }
                else
                {
                    this.setBlockBounds(0.3125F, 0.375F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
                }

                break;

            case 1:
            case 2:
            case 3:
            case 5:
            default:
                this.SetBlockBoundsBasedOnSubType(var5);
                break;

            case 4:
                if (!this.IsTableOnCorner(var1, var2, var3, var4))
                {
                    this.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }

                break;

            case 6:
            case 7:
            case 8:
                this.SetBlockBoundsForPane(var1, var2, var3, var4, var5);
        }
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        int var8 = var1.getBlockMetadata(var2, var3, var4);

        if (var8 != 6 && var8 != 7 && var8 != 8)
        {
            super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
        }
        else
        {
            boolean var9 = this.ShouldPaneConnectToBlock(var1, var2, var3, var4 - 1, var8);
            boolean var10 = this.ShouldPaneConnectToBlock(var1, var2, var3, var4 + 1, var8);
            boolean var11 = this.ShouldPaneConnectToBlock(var1, var2 - 1, var3, var4, var8);
            boolean var12 = this.ShouldPaneConnectToBlock(var1, var2 + 1, var3, var4, var8);

            if ((!var11 || !var12) && (var11 || var12 || var9 || var10))
            {
                if (var11 && !var12)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                    super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                }
                else if (!var11 && var12)
                {
                    this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                    super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
            }

            if ((!var9 || !var10) && (var11 || var12 || var9 || var10))
            {
                if (var9 && !var10)
                {
                    this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                    super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                }
                else if (!var9 && var10)
                {
                    this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                    super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                }
            }
            else
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
            }
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

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetSubtype(var1, var2, var3, var4);
        return var5 == 4 || var5 == 3 || var5 == 9;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetSubtype(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4);
    }

    public void SetSubtype(World var1, int var2, int var3, int var4, int var5)
    {
        var1.setBlockMetadata(var2, var3, var4, var5);
    }

    public boolean IsBlockTable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockId(var2, var3, var4) == mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID && var1.getBlockMetadata(var2, var3, var4) == 4;
    }

    public boolean IsTableOnCorner(IBlockAccess var1, int var2, int var3, int var4)
    {
        boolean var5 = this.IsBlockTable(var1, var2 + 1, var3, var4);
        boolean var6 = this.IsBlockTable(var1, var2 - 1, var3, var4);
        boolean var7 = this.IsBlockTable(var1, var2, var3, var4 + 1);
        boolean var8 = this.IsBlockTable(var1, var2, var3, var4 - 1);
        return !var5 && (!var7 || !var8) || !var6 && (!var7 || !var8);
    }

    private boolean ShouldPaneConnectToBlock(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockId(var2, var3, var4);

        if (!Block.opaqueCubeLookup[var6] && var6 != Block.glass.blockID)
        {
            if (var6 == this.blockID)
            {
                int var7 = var1.getBlockMetadata(var2, var3, var4);

                if (var7 == var5)
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    public void SetBlockBoundsForPane(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        float var6 = 0.4375F;
        float var7 = 0.5625F;
        float var8 = 0.4375F;
        float var9 = 0.5625F;
        boolean var10 = this.ShouldPaneConnectToBlock(var1, var2, var3, var4 - 1, var5);
        boolean var11 = this.ShouldPaneConnectToBlock(var1, var2, var3, var4 + 1, var5);
        boolean var12 = this.ShouldPaneConnectToBlock(var1, var2 - 1, var3, var4, var5);
        boolean var13 = this.ShouldPaneConnectToBlock(var1, var2 + 1, var3, var4, var5);

        if ((!var12 || !var13) && (var12 || var13 || var10 || var11))
        {
            if (var12 && !var13)
            {
                var6 = 0.0F;
            }
            else if (!var12 && var13)
            {
                var7 = 1.0F;
            }
        }
        else
        {
            var6 = 0.0F;
            var7 = 1.0F;
        }

        if ((!var10 || !var11) && (var12 || var13 || var10 || var11))
        {
            if (var10 && !var11)
            {
                var8 = 0.0F;
            }
            else if (!var10 && var11)
            {
                var9 = 1.0F;
            }
        }
        else
        {
            var8 = 0.0F;
            var9 = 1.0F;
        }

        this.setBlockBounds(var6, 0.0F, var8, var7, 1.0F, var9);
    }

    public void SetBlockBoundsBasedOnSubType(int var1)
    {
        switch (var1)
        {
            case 0:
                this.setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.625F, 0.6875F);
                break;

            case 1:
                this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
                break;

            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 5:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                break;

            case 9:
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
