package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public class FCBlockBloodMoss extends Block implements FCIBlock, ITextureProvider
{
    private static final float m_fBlockHardness = 0.2F;
    private static final int m_iTextureID = 130;
    private static final int m_iTopGrowingTextureID = 127;
    private static final int m_iFullGrownTopTextureID = 128;
    private static final int m_iSidesTextureID = 129;
    private static final int m_iBottomTextureID = 130;

    protected FCBlockBloodMoss(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcNetherGrowthMaterial);
        this.setHardness(0.2F);
        this.setStepSound(mod_FCBetterThanWolves.fcSoundSquishFootstep);
        this.setBlockName("fcBlockBloodMoss");
        this.blockIndexInTexture = 130;
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        int var5 = var1.getBlockId(var2, var3 - 1, var4);

        if (var5 == Block.netherrack.blockID)
        {
            var1.setBlockAndMetadata(var2, var3 - 1, var4, mod_FCBetterThanWolves.fcAestheticOpaque.blockID, 8);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        int var7 = this.GetHeightLevel(var1, var2, var3, var4);

        if (var7 == 7)
        {
            this.ReleaseSpores(var1, var2, var3, var4);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
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
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        float var5 = (float)(this.GetHeightLevel(var1, var2, var3, var4) + 1) / 16.0F;
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + var5), (double)((float)var4 + 1.0F));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        float var5 = (float)(this.GetHeightLevel(var1, var2, var3, var4) + 1) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return 0;
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
        if (!this.canPlaceBlockAt(var1, var2, var3, var4))
        {
            var1.playAuxSFX(2001, var2, var3, var4, this.blockID + (var1.getBlockMetadata(var2, var3, var4) << 12));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetHeightLevel(var1, var2, var3, var4);
        int var7 = var1.getBlockId(var2, var3 - 1, var4);
        boolean var8 = false;
        int var9;

        if (var7 == Block.netherrack.blockID)
        {
            var1.setBlockAndMetadata(var2, var3 - 1, var4, mod_FCBetterThanWolves.fcAestheticOpaque.blockID, 8);
            var8 = true;
        }
        else if (var7 == mod_FCBetterThanWolves.fcAestheticOpaque.blockID)
        {
            var9 = var1.getBlockMetadata(var2, var3 - 1, var4);

            if (var9 == 8)
            {
                var8 = true;
            }
        }

        if (var6 < 7)
        {
            boolean var11 = false;

            if (var8)
            {
                var11 = true;
            }
            else if (this.GetMaxHeightOfNeighbors(var1, var2, var3, var4) > var6 + 1)
            {
                var11 = true;
            }

            if (var11)
            {
                ++var6;
                this.SetHeightLevel(var1, var2, var3, var4, var6);
                var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            }
        }

        if (var6 >= 1)
        {
            var9 = var5.nextInt(4) + 2;
            FCUtilsBlockPos var10 = new FCUtilsBlockPos(var2, var3, var4);
            var10.AddFacingAsOffset(var9);

            if (this.IsBlockOpenToSpread(var1, var10.i, var10.j, var10.k))
            {
                if (var1.doesBlockHaveSolidTopSurface(var10.i, var10.j - 1, var10.k))
                {
                    this.SpreadToBlock(var1, var10.i, var10.j, var10.k);
                }
                else if (var8)
                {
                    --var10.j;

                    if (this.IsBlockOpenToSpread(var1, var10.i, var10.j, var10.k) && var1.doesBlockHaveSolidTopSurface(var10.i, var10.j - 1, var10.k))
                    {
                        this.SpreadToBlock(var1, var10.i, var10.j, var10.k);
                    }
                }
            }
            else if (var1.isAirBlock(var2, var3 + 1, var4) && var1.getBlockId(var10.i, var10.j, var10.k) == Block.netherrack.blockID)
            {
                ++var10.j;

                if (this.IsBlockOpenToSpread(var1, var10.i, var10.j, var10.k))
                {
                    this.SpreadToBlock(var1, var10.i, var10.j, var10.k);
                }
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var5.isDead && !var1.isRemote)
        {
            int var6 = this.GetHeightLevel(var1, var2, var3, var4);

            if (var6 >= 7)
            {
                if (var5 instanceof EntityLiving)
                {
                    boolean var7 = true;

                    if (var5 instanceof EntityPlayer)
                    {
                        EntityPlayer var8 = (EntityPlayer)var5;

                        if (var8.IsWearingSoulforgedBoots())
                        {
                            var7 = false;
                        }
                    }

                    if (var7 && var5.attackEntityFrom(DamageSource.cactus, 2))
                    {
                        var5.isAirBorne = true;
                        var5.motionY += 0.84D;
                        var1.playAuxSFX(2225, var2, var3, var4, 0);
                    }
                }
                else if (var5 instanceof EntityItem)
                {
                    EntityItem var9 = (EntityItem)var5;

                    if (var9.delayBeforeCanPickup <= 0 && (var9.item.getItem() instanceof ItemFood || var9.item.itemID == Block.mushroomRed.blockID || var9.item.itemID == Block.mushroomBrown.blockID))
                    {
                        var9.setDead();
                        var1.playAuxSFX(2226, var2, var3, var4, 0);
                    }
                }
            }
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

    public int GetHeightLevel(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & 7;
    }

    public void SetHeightLevel(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 8;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    private int GetMaxHeightOfNeighbors(World var1, int var2, int var3, int var4)
    {
        int var5 = -1;

        for (int var6 = 2; var6 <= 5; ++var6)
        {
            FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);
            var7.AddFacingAsOffset(var6);

            if (var1.getBlockId(var7.i, var7.j, var7.k) == this.blockID)
            {
                int var8 = this.GetHeightLevel(var1, var7.i, var7.j, var7.k);

                if (var8 > var5)
                {
                    var5 = var8;
                }
            }
        }

        return var5;
    }

    private void SpreadToBlock(World var1, int var2, int var3, int var4)
    {
        if (var1.getBlockId(var2, var3, var4) == Block.fire.blockID)
        {
            var1.playAuxSFX(2227, var2, var3, var4, 0);
        }
        else if (var1.getBlockId(var2, var3, var4) == Block.mushroomBrown.blockID || var1.getBlockId(var2, var3, var4) == Block.mushroomRed.blockID)
        {
            var1.playAuxSFX(2226, var2, var3, var4, 0);
        }

        if (var1.setBlockWithNotify(var2, var3, var4, this.blockID))
        {
            this.updateBlockMetadata(var1, var2, var3, var4, 0, 0.0F, 0.0F, 0.0F);
            var1.playAuxSFX(2228, var2, var3, var4, 0);
        }
    }

    private boolean IsBlockOpenToSpread(World var1, int var2, int var3, int var4)
    {
        if (var1.isAirBlock(var2, var3, var4))
        {
            return true;
        }
        else
        {
            int var5 = var1.getBlockId(var2, var3, var4);
            return var5 == Block.fire.blockID || var5 == Block.mushroomRed.blockID || var5 == Block.mushroomBrown.blockID;
        }
    }

    private void ReleaseSpores(World var1, int var2, int var3, int var4)
    {
        var1.playAuxSFX(2224, var2, var3, var4, 0);

        for (int var5 = var2 - 3; var5 <= var2 + 3; ++var5)
        {
            for (int var6 = var3 - 3; var6 <= var3 + 3; ++var6)
            {
                for (int var7 = var4 - 3; var7 <= var4 + 3; ++var7)
                {
                    if ((var5 != var2 || var6 != var3 || var7 != var4) && this.IsBlockOpenToSpread(var1, var5, var6, var7) && var1.doesBlockHaveSolidTopSurface(var5, var6 - 1, var7) && var1.rand.nextInt(2) == 0)
                    {
                        var1.setBlockWithNotify(var5, var6, var7, this.blockID);
                    }
                }
            }
        }

        double var16 = (double)var2 + 0.5D;
        double var17 = (double)var3 + 0.5D;
        double var9 = (double)var4 + 0.5D;
        List var11 = var1.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(var16 - 5.0D, var17 - 5.0D, var9 - 5.0D, var16 + 5.0D, var17 + 5.0D, var9 + 5.0D));

        if (var11 != null && var11.size() > 0)
        {
            for (int var12 = 0; var12 < var11.size(); ++var12)
            {
                EntityLiving var13 = (EntityLiving)var11.get(var12);
                boolean var14 = true;

                if (var13 instanceof EntityPlayer)
                {
                    EntityPlayer var15 = (EntityPlayer)var13;

                    if (var15.IsWearingFullSuitSoulforgedArmor())
                    {
                        var14 = false;
                    }
                }

                if (var14)
                {
                    var13.attackEntityFrom(DamageSource.drown, 4);
                    var13.addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0));
                }
            }
        }
    }
}
