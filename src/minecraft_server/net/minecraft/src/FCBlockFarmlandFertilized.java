package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockFarmlandFertilized extends Block implements FCIBlockSlabLighting, ITextureProvider, FCISoil
{
    private static final int m_iDefaultTexture = 2;
    private static final int m_iTopWetTexture = 136;
    public static final int m_iTopDryTexture = 137;

    protected FCBlockFarmlandFertilized(int var1)
    {
        super(var1, Material.ground);
        this.blockIndexInTexture = 2;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.setHardness(0.6F);
        this.setStepSound(soundGravelFootstep);
        this.setBlockName("farmland");
        this.setRequiresSelfNotify();
        this.setTickRandomly(true);
        this.setLightOpacity(255);
        Block.useNeighborBrightness[var1] = true;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)(var2 + 0), (double)(var3 + 0), (double)(var4 + 0), (double)(var2 + 1), (double)(var3 + 1), (double)(var4 + 1));
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
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return var1 == 1 ? (var2 > 0 ? 136 : 137) : this.blockIndexInTexture;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!this.isWaterNearby(var1, var2, var3, var4) && !var1.canLightningStrikeAt(var2, var3 + 1, var4))
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var6 > 0)
            {
                var1.setBlockMetadataWithNotify(var2, var3, var4, var6 - 1);
            }
            else if (!this.isCropsNearby(var1, var2, var3, var4))
            {
                var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
            }
        }
        else
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 7);
        }
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    public void onFallenUpon(World var1, int var2, int var3, int var4, Entity var5, float var6)
    {
        if (!var1.isRemote && var1.rand.nextFloat() < var6 - 0.5F)
        {
            var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
        }
    }

    private boolean isCropsNearby(World var1, int var2, int var3, int var4)
    {
        byte var5 = 0;

        for (int var6 = var2 - var5; var6 <= var2 + var5; ++var6)
        {
            for (int var7 = var4 - var5; var7 <= var4 + var5; ++var7)
            {
                int var8 = var1.getBlockId(var6, var3 + 1, var7);

                if (var8 == Block.crops.blockID || var8 == Block.melonStem.blockID || var8 == Block.pumpkinStem.blockID)
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isWaterNearby(World var1, int var2, int var3, int var4)
    {
        for (int var5 = var2 - 4; var5 <= var2 + 4; ++var5)
        {
            for (int var6 = var3; var6 <= var3 + 1; ++var6)
            {
                for (int var7 = var4 - 4; var7 <= var4 + 4; ++var7)
                {
                    if (var1.getBlockMaterial(var5, var6, var7) == Material.water)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
        Material var6 = var1.getBlockMaterial(var2, var3 + 1, var4);

        if (var6.isSolid())
        {
            var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return Block.dirt.idDropped(0, var2, var3);
    }

    public int idPicked(World var1, int var2, int var3, int var4)
    {
        return Block.dirt.blockID;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public boolean CanPlantGrowOnBlock(World var1, int var2, int var3, int var4, Block var5)
    {
        return var5.blockID == Block.crops.blockID || var5.blockID == mod_FCBetterThanWolves.fcHempCrop.blockID || var5.blockID == Block.pumpkinStem.blockID || var5.blockID == Block.melonStem.blockID || var5.blockID == Block.pumpkin.blockID || var5.blockID == Block.melon.blockID;
    }

    public boolean IsPlantGrowthMaximizedOnBlock(World var1, int var2, int var3, int var4, Block var5)
    {
        return false;
    }

    public boolean IsBlockHydrated(World var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) > 0;
    }

    public boolean IsBlockConsideredNeighbouringWater(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public float GetGrowthMultiplier(World var1, int var2, int var3, int var4, Block var5)
    {
        return 2.0F;
    }

    public void NotifyOfPlantGrowth(World var1, int var2, int var3, int var4, Block var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.tilledField.blockID, var6);
    }
}
