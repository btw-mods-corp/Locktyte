package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public class FCBlockAestheticVegetation extends Block implements FCIBlock, ITextureProvider
{
    public static final int m_iSubtypeVineTrap = 0;
    public static final int m_iSubtypeVineTrapTriggeredByEntity = 1;
    public static final int m_iSubtypeBloodWoodSapling = 2;
    public static final int m_iSubtypeBloodLeaves = 3;
    public static final int m_iSubtypeVineTrapUpsideDown = 4;
    public static final int m_iSubtypeVineTrapUpsideDownTriggeredByEntity = 5;
    public static final int m_iNumSubtypes = 6;
    private static final float m_fVineTrapHeight = 0.125F;
    private static final int m_iVineTrapTextureID = 105;
    private static final int m_iBloodWoodSaplingTextureID = 108;
    private static final int m_iBloodLeavesTextureID = 109;
    private static final float m_fHardness = 0.2F;
    private static final int m_iTickRate = 10;
    public static final int m_iBloodWoodSaplingMinTrunkHeight = 4;

    public FCBlockAestheticVegetation(int var1)
    {
        super(var1, Material.leaves);
        this.blockIndexInTexture = 105;
        this.setHardness(0.2F);
        this.setStepSound(soundGrassFootstep);
        this.setBlockName("fcAestheticVegetation");
        this.setTickRandomly(true);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        switch (var2)
        {
            case 0:
            case 1:
            case 4:
            case 5:
                return 105;

            case 2:
                return 108;

            case 3:
                return 109;

            default:
                return this.blockIndexInTexture;
        }
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
        return mod_FCBetterThanWolves.iCustomAestheticVegetationRenderID;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        int var9 = var1.getBlockMetadata(var2, var3, var4);

        if (var9 == 0 && var5 != 1)
        {
            boolean var10 = true;

            if (var5 >= 2 && var7 < 0.5F)
            {
                var10 = false;
            }

            if (var10)
            {
                this.SetSubtype(var1, var2, var3, var4, 4);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
        int var6 = this.GetSubtype(var1, var2, var3, var4);

        if (var6 == 2)
        {
            this.ValidateBloodWoodSapling(var1, var2, var3, var4);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetSubtype(var1, var2, var3, var4);

        if (var5 == 2)
        {
            int var6 = var1.getBlockId(var2, var3 - 1, var4);
            return var6 == Block.slowSand.blockID ? true : var6 == mod_FCBetterThanWolves.fcPlanter.blockID && ((FCBlockPlanter)mod_FCBetterThanWolves.fcPlanter).GetPlanterType(var1, var2, var3 - 1, var4) == 8;
        }
        else
        {
            return super.canBlockStay(var1, var2, var3, var4);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        int var2 = var1;

        switch (var1)
        {
            case 0:
            case 1:
            case 4:
            case 5:
                var2 = 0;

            case 2:
            default:
                break;

            case 3:
                var2 = 2;
        }

        return var2;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
    {
        if (var5 == 3)
        {
            if (var1.isRemote)
            {
                return;
            }

            int var8 = var1.rand.nextInt(20) != 0 ? 0 : 1;

            for (int var9 = 0; var9 < var8; ++var9)
            {
                if (var1.rand.nextFloat() <= var6)
                {
                    int var10 = this.idDropped(var5, var1.rand, var7);

                    if (var10 > 0)
                    {
                        this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(var10, 1, this.damageDropped(var5)));
                    }
                }
            }
        }
        else
        {
            super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, var7);
        }
    }

    /**
     * Spawns EntityItem in the world for the given ItemStack if the world is not remote.
     */
    protected void dropBlockAsItem_do(World var1, int var2, int var3, int var4, ItemStack var5)
    {
        if (var5.itemID == this.blockID && var5.getItemDamage() == 2)
        {
            if (!var1.isRemote)
            {
                float var6 = 0.7F;
                double var7 = (double)(var1.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
                double var9 = (double)(var1.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
                double var11 = (double)(var1.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
                FCEntityItemBloodWoodSapling var13 = new FCEntityItemBloodWoodSapling(var1, (double)var2 + var7, (double)var3 + var9, (double)var4 + var11, var5);
                var13.delayBeforeCanPickup = 10;
                var1.spawnEntityInWorld(var13);
            }
        }
        else
        {
            super.dropBlockAsItem_do(var1, var2, var3, var4, var5);
        }
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6)
    {
        if (!var1.isRemote && var2.getCurrentEquippedItem() != null && var2.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex && var6 == 3)
        {
            this.dropBlockAsItem_do(var1, var3, var4, var5, new ItemStack(mod_FCBetterThanWolves.fcLeaves, 1, 0));
            var2.getCurrentEquippedItem().damageItem(1, var2);
        }
        else
        {
            super.harvestBlock(var1, var2, var3, var4, var5, var6);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetSubtype(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
                return null;

            case 3:
            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetSubtype(var1, var2, var3, var4);
        this.SetBlockBoundsBasedOnSubType(var5);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote)
        {
            int var6 = this.GetSubtype(var1, var2, var3, var4);

            if (var6 == 0 || var6 == 4)
            {
                List var7 = null;
                float var8 = 0.0F;

                if (var6 == 4)
                {
                    var8 = 0.875F;
                }

                var7 = var1.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + var8), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3 + 0.125F + var8), (double)((float)(var4 + 1))));

                if (var7 != null && var7.size() > 0)
                {
                    var1.playSoundAtEntity(var5, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch());
                    this.SetSubtype(var1, var2, var3, var4, var6 + 1);
                    var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetSubtype(var1, var2, var3, var4);

        if (var6 != 1 && var6 != 5)
        {
            if (var6 == 2 && this.ValidateBloodWoodSapling(var1, var2, var3, var4) && var5.nextInt(14) == 0)
            {
                WorldChunkManager var9 = var1.getWorldChunkManager();

                if (var9 != null)
                {
                    BiomeGenBase var10 = var9.getBiomeGenAt(var2, var4);

                    if (var10 instanceof BiomeGenHell)
                    {
                        this.AttemptToGrowBloodwoodSapling(var1, var2, var3, var4, var5);
                    }
                }
            }
        }
        else
        {
            List var7 = null;
            float var8 = 0.0F;

            if (var6 == 4)
            {
                var8 = 0.875F;
            }

            var7 = var1.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + var8), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3 + 0.125F + var8), (double)((float)(var4 + 1))));

            if (var7 != null && var7.size() > 0)
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            }
            else
            {
                this.SetSubtype(var1, var2, var3, var4, var6 - 1);
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

    public void SetBlockBoundsBasedOnSubType(int var1)
    {
        switch (var1)
        {
            case 0:
            case 1:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
                break;

            case 2:
            case 3:
            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 4:
            case 5:
                this.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public boolean ValidateBloodWoodSapling(World var1, int var2, int var3, int var4)
    {
        if (!this.canBlockStay(var1, var2, var3, var4))
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
            var1.setBlockWithNotify(var2, var3, var4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void AttemptToGrowBloodwoodSapling(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6;

        for (var6 = var3 + 1; var6 < var3 + 4; ++var6)
        {
            if (var6 >= 256 || !var1.isAirBlock(var2, var6, var4))
            {
                return;
            }
        }

        for (var6 = var3; var6 < var3 + 4 - 1; ++var6)
        {
            var1.setBlockAndMetadataWithNotify(var2, var6, var4, mod_FCBetterThanWolves.fcBloodWood.blockID, 0);
        }

        FCBlockBloodWood var12 = (FCBlockBloodWood)((FCBlockBloodWood)mod_FCBetterThanWolves.fcBloodWood);
        int var7 = var3 + 4 - 1;
        var1.setBlockAndMetadataWithNotify(var2, var7, var4, mod_FCBetterThanWolves.fcBloodWood.blockID, 1);
        var12.GrowLeaves(var1, var2, var7, var4);
        var12.Grow(var1, var2, var7, var4, var5);

        for (int var8 = var2 - 1; var8 <= var2 + 1; ++var8)
        {
            for (int var9 = var7; var9 <= var7 + 1; ++var9)
            {
                for (int var10 = var4 - 1; var10 <= var4 + 1; ++var10)
                {
                    if (var1.getBlockId(var8, var9, var10) == mod_FCBetterThanWolves.fcBloodWood.blockID)
                    {
                        int var11 = var12.GetFacing(var1, var8, var9, var10);

                        if (var11 != 0 && (var8 != var2 || var9 != var7 || var10 != var4))
                        {
                            var12.Grow(var1, var8, var9, var10, var5);
                        }
                    }
                }
            }
        }

        var1.playAuxSFX(2228, var2, var3, var4, 0);
    }
}
