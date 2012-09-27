package net.minecraft.src;

import forge.ITextureProvider;

public class FCBlockUnfiredPottery extends Block implements FCIBlock, ITextureProvider, FCIBlockCraftableOnTurntable
{
    public static final int m_iNumSubtypes = 5;
    public static final int m_iSubtypeCrucible = 0;
    public static final int m_iSubtypePlanter = 1;
    public static final int m_iSubtypeVase = 2;
    public static final int m_iSubtypeUrn = 3;
    public static final int m_iSubtypeMould = 4;
    public static final int m_iRotationsOnTurntableToChangState = 8;
    public static final float m_fUnfiredPotteryCrucibleHeight = 1.0F;
    public static final float m_fUnfiredPotteryCrucibleWidth = 0.875F;
    public static final float m_fUnfiredPotteryCrucibleHalfWidth = 0.4375F;
    public static final float m_fUnfiredPotteryCrucibleBandHeight = 0.75F;
    public static final float m_fUnfiredPotteryCrucibleBandHalfHeight = 0.375F;
    public static final float m_fUnfiredPotteryPotWidth = 0.75F;
    public static final float m_fUnfiredPotteryPotHalfWidth = 0.375F;
    public static final float m_fUnfiredPotteryPotBandHeight = 0.3125F;
    public static final float m_fUnfiredPotteryPotBandHalfHeight = 0.15625F;
    public static final float m_fUnfiredPotteryVaseBaseWidth = 0.5F;
    public static final float m_fUnfiredPotteryVaseBaseHalfWidth = 0.25F;
    public static final float m_fUnfiredPotteryVaseBaseHeight = 0.0625F;
    public static final float m_fUnfiredPotteryVaseBodyWidth = 0.625F;
    public static final float m_fUnfiredPotteryVaseBodyHalfWidth = 0.3125F;
    public static final float m_fUnfiredPotteryVaseBodyHeight = 0.375F;
    public static final float m_fUnfiredPotteryVaseNeckBaseWidth = 0.5F;
    public static final float m_fUnfiredPotteryVaseNeckBaseHalfWidth = 0.25F;
    public static final float m_fUnfiredPotteryVaseNeckBaseHeight = 0.0625F;
    public static final float m_fUnfiredPotteryVaseNeckWidth = 0.25F;
    public static final float m_fUnfiredPotteryVaseNeckHalfWidth = 0.125F;
    public static final float m_fUnfiredPotteryVaseNeckHeight = 0.4375F;
    public static final float m_fUnfiredPotteryVaseTopWidth = 0.375F;
    public static final float m_fUnfiredPotteryVaseTopHalfWidth = 0.1875F;
    public static final float m_fUnfiredPotteryVaseTopHeight = 0.0625F;
    public static final float m_fUnfiredPotteryUrnBaseWidth = 0.25F;
    public static final float m_fUnfiredPotteryUrnBaseHalfWidth = 0.125F;
    public static final float m_fUnfiredPotteryUrnBaseHeight = 0.0625F;
    public static final float m_fUnfiredPotteryUrnBodyWidth = 0.375F;
    public static final float m_fUnfiredPotteryUrnBodyHalfWidth = 0.1875F;
    public static final float m_fUnfiredPotteryUrnBodyHeight = 0.375F;
    public static final float m_fUnfiredPotteryUrnNeckWidth = 0.25F;
    public static final float m_fUnfiredPotteryUrnNeckHalfWidth = 0.125F;
    public static final float m_fUnfiredPotteryUrnNeckHeight = 0.0625F;
    public static final float m_fUnfiredPotteryUrnTopWidth = 0.375F;
    public static final float m_fUnfiredPotteryUrnTopHalfWidth = 0.1875F;
    public static final float m_fUnfiredPotteryUrnTopHeight = 0.0625F;
    public static final float m_fUnfiredPotteryUrnLidWidth = 0.25F;
    public static final float m_fUnfiredPotteryUrnLidHalfWidth = 0.125F;
    public static final float m_fUnfiredPotteryUrnLidHeight = 0.0625F;
    public static final float m_fUnfiredPotteryUrnHeight = 0.625F;
    public static final float m_fUnfiredPotteryMouldHeight = 0.125F;
    public static final float m_fUnfiredPotteryMouldWidth = 0.375F;
    public static final float m_fUnfiredPotteryMouldHalfWidth = 0.1875F;
    private final int iUnfiredPotteryCookingTexture = 76;

    public FCBlockUnfiredPottery(int var1)
    {
        super(var1, Material.clay);
        this.blockIndexInTexture = 75;
        this.setHardness(0.6F);
        this.setStepSound(soundGravelFootstep);
        this.setBlockName("fcUnfiredPottery");
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        if (!var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4))
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return var1;
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
        return mod_FCBetterThanWolves.iCustomUnfiredPotteryRenderID;
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
            case 1:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 2:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.1875F), (double)((float)var3), (double)((float)var4 + 0.1875F), (double)((float)var2 + 0.8125F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.8125F));

            case 3:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.3125F), (double)((float)var3), (double)((float)var4 + 0.3125F), (double)((float)var2 + 0.6875F), (double)((float)var3 + 0.625F), (double)((float)var4 + 0.6875F));

            case 4:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.3125F), (double)((float)var3), (double)((float)var4 + 0.3125F), (double)((float)var2 + 0.6875F), (double)((float)var3 + 0.125F), (double)((float)var4 + 0.6875F));

            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
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
            case 1:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 2:
                this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
                break;

            case 3:
                this.setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.625F, 0.6875F);
                break;

            case 4:
                this.setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.125F, 0.6875F);
                break;

            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        return var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4);
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
        else
        {
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
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

    public int GetRotationsToCraft(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 8;
    }

    public void OnRotated(World var1, int var2, int var3, int var4)
    {
        var1.playAuxSFX(2001, var2, var3, var4, this.blockID);
    }

    public int GetBlockIDOnCraft(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return var5 < 4 ? this.blockID : 0;
    }

    public int GetBlockMetadataOnCraft(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return var5 < 4 ? var5 + 1 : 0;
    }

    public int GetItemIDDroppedOnCraft(IBlockAccess var1, int var2, int var3, int var4)
    {
        return Item.clay.shiftedIndex;
    }

    public int GetItemMetadataDroppedOnCraft(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 0;
    }

    public int GetNumItemsDroppedOnCraft(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return var5 != 0 && var5 != 3 ? 1 : 0;
    }

    public void OnCraft(World var1, int var2, int var3, int var4) {}
}
