package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockMiningCharge extends Block implements FCIBlock, ITextureProvider
{
    public static final float m_fBoundingBoxHeight = 0.5F;
    private static final int m_iTickRate = 1;
    private static final int m_iTextureIndexTop = 84;
    private static final int m_iTextureIndexSide = 85;
    private static final int m_iTextureIndexSideFlipped = 86;
    private static final int m_iTextureIndexBottom = 87;

    public FCBlockMiningCharge(int var1)
    {
        super(var1, Material.tnt);
        this.blockIndexInTexture = 85;
        this.setHardness(0.0F);
        this.setStepSound(soundGrassFootstep);
        this.setBlockName("fcMiningCharge");
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 1;
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
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        int var3 = GetFacingFromMetaData(var2);
        return var3 <= 1 ? (var1 <= 3 ? 86 : (var1 == 4 ? 84 : 87)) : (var1 == 0 ? 87 : (var1 == 1 ? 84 : 85));
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 <= 3 ? 86 : (var1 == 4 ? 84 : 87);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        return var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4) ? true : (var1.isBlockNormalCube(var2, var3 + 1, var4) ? true : (var1.isBlockNormalCube(var2 - 1, var3, var4) ? true : (var1.isBlockNormalCube(var2 + 1, var3, var4) ? true : (var1.isBlockNormalCube(var2, var3, var4 - 1) ? true : var1.isBlockNormalCube(var2, var3, var4 + 1)))));
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
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        int var9 = FCUtilsMisc.GetOppositeFacing(var5);

        if (this.IsValidAnchorToFacing(var1, var2, var3, var4, var9))
        {
            this.SetFacing(var1, var2, var3, var4, var9);
        }
        else
        {
            byte var10 = 0;

            if (var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4))
            {
                var10 = 0;
            }
            else if (var1.isBlockNormalCube(var2 - 1, var3, var4))
            {
                var10 = 4;
            }
            else if (var1.isBlockNormalCube(var2 + 1, var3, var4))
            {
                var10 = 5;
            }
            else if (var1.isBlockNormalCube(var2, var3, var4 - 1))
            {
                var10 = 2;
            }
            else if (var1.isBlockNormalCube(var2, var3, var4 + 1))
            {
                var10 = 3;
            }
            else if (var1.isBlockNormalCube(var2, var3 + 1, var4))
            {
                var10 = 1;
            }

            this.SetFacing(var1, var2, var3, var4, var10);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 0.5F), (double)((float)var4 + 1.0F));

            case 1:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 1.0F - 0.5F), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 2:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.5F));

            case 3:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4 + 1.0F - 0.5F), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 4:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 0.5F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 1.0F - 0.5F), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                break;

            case 1:
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                break;

            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                break;

            case 4:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                break;

            default:
                this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
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
     * Called upon the block being destroyed by an explosion
     */
    public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        FCEntityMiningCharge var6 = CreatePrimedEntity(var1, var2, var3, var4, var5);
        var6.m_iFuse = 1;
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5)
    {
        if (!var1.isRemote)
        {
            this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(mod_FCBetterThanWolves.fcMiningCharge.blockID, 1, 0));
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

        if (var10 != null && var10.itemID == Item.flintAndSteel.shiftedIndex)
        {
            if (!var1.isRemote)
            {
                int var12 = var1.getBlockMetadata(var2, var3, var4);
                var1.setBlockWithNotify(var2, var3, var4, 0);
                CreatePrimedEntity(var1, var2, var3, var4, var12);
            }

            var10.damageItem(1, var5);
            return true;
        }
        else
        {
            return super.onBlockActivated(var1, var2, var3, var4, var5, var6, var7, var8, var9);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (this.IsGettingRedstonePower(var1, var2, var3, var4))
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            var1.setBlockWithNotify(var2, var3, var4, 0);
            CreatePrimedEntity(var1, var2, var3, var4, var6);
        }
        else if (!this.IsValidAnchorToFacing(var1, var2, var3, var4, this.GetFacing(var1, var2, var3, var4)))
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
            this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(mod_FCBetterThanWolves.fcMiningCharge.blockID, 1, 0));
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & 7;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -8;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1 & 7;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 &= -8;
        var1 |= var2;
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 1;
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
        if (FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5))
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        var6 = FCUtilsMisc.CycleFacing(var6, var5);
        this.SetFacing(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        return true;
    }

    public static int GetFacingFromMetaData(int var0)
    {
        return var0 & 7;
    }

    public boolean IsGettingRedstonePower(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockIndirectlyGettingPowered(var2, var3, var4);
    }

    public boolean IsValidAnchorToFacing(World var1, int var2, int var3, int var4, int var5)
    {
        FCUtilsBlockPos var6 = new FCUtilsBlockPos(var2, var3, var4);
        var6.AddFacingAsOffset(var5);
        return var5 == 0 ? var1.doesBlockHaveSolidTopSurface(var6.i, var6.j, var6.k) : var1.isBlockNormalCube(var6.i, var6.j, var6.k);
    }

    public static FCEntityMiningCharge CreatePrimedEntity(World var0, int var1, int var2, int var3, int var4)
    {
        FCEntityMiningCharge var5 = new FCEntityMiningCharge(var0, var1, var2, var3, GetFacingFromMetaData(var4));
        var0.spawnEntityInWorld(var5);
        var0.playSoundAtEntity(var5, "random.fuse", 1.0F, 1.0F);
        return var5;
    }
}
