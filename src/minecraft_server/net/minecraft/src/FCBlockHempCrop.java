package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockHempCrop extends BlockFlower implements ITextureProvider, FCIBlock
{
    private static final int iMaxHempHeight = 2;
    private static final int iFirstSproutTextureID = 240;
    private static final int m_iPlantTopTextureID = 248;
    private static final float m_iCollisionBoxWidth = 0.4F;
    private static final float m_iCollisionBoxHalfWidth = 0.2F;

    protected FCBlockHempCrop(int var1)
    {
        super(var1, 240);
        this.setHardness(0.0F);
        this.setStepSound(soundGrassFootstep);
        this.setBlockName("fcHempCrop");
        this.disableStats();
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
        this.setCreativeTab((CreativeTabs)null);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean canThisPlantGrowOnThisBlockID(int var1)
    {
        return var1 == Block.tilledField.blockID || var1 == this.blockID;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.updateTick(var1, var2, var3, var4, var5);

        if (var1.getBlockLightValue(var2, var3, var4) >= 15 || var1.getBlockId(var2, var3 + 1, var4) == mod_FCBetterThanWolves.fcLightBulbOn.blockID || var1.getBlockId(var2, var3 + 2, var4) == mod_FCBetterThanWolves.fcLightBulbOn.blockID)
        {
            boolean var6 = false;
            int var7 = var1.getBlockId(var2, var3 - 1, var4);

            if (var7 == Block.tilledField.blockID && var1.getBlockMetadata(var2, var3 - 1, var4) > 0)
            {
                var6 = true;
            }
            else if (FCUtilsMisc.CanPlantGrowOnBlock(var1, var2, var3 - 1, var4, this))
            {
                Block var8 = Block.blocksList[var7];

                if (var8 instanceof FCISoil && ((FCISoil)var8).IsBlockHydrated(var1, var2, var3 - 1, var4))
                {
                    var6 = true;
                }
            }

            if (var6)
            {
                int var12 = var1.getBlockMetadata(var2, var3, var4);

                if (var12 < 7)
                {
                    float var9 = 0.2F * FCUtilsMisc.GetBlockGrowthMultiplier(var1, var2, var3 - 1, var4, this);

                    if (var5.nextFloat() <= var9)
                    {
                        ++var12;
                        var1.setBlockMetadataWithNotify(var2, var3, var4, var12);

                        if (var12 == 7)
                        {
                            FCUtilsMisc.NotifySoilOfPlantGrowth(var1, var2, var3 - 1, var4, this);
                        }
                    }
                }
                else
                {
                    for (int var13 = var3 + 1; var13 < var3 + 2; ++var13)
                    {
                        int var10 = var1.getBlockId(var2, var13, var4);

                        if (var10 != this.blockID)
                        {
                            if (var1.isAirBlock(var2, var13, var4))
                            {
                                float var11 = 0.067F * FCUtilsMisc.GetBlockGrowthMultiplier(var1, var2, var3 - 1, var4, this);

                                if (var5.nextFloat() <= var11)
                                {
                                    if (var13 == var3 + 2 - 1)
                                    {
                                        var1.setBlockAndMetadataWithNotify(var2, var13, var4, this.blockID, 8);
                                    }
                                    else
                                    {
                                        var1.setBlockAndMetadataWithNotify(var2, var13, var4, this.blockID, 7);
                                    }

                                    FCUtilsMisc.NotifySoilOfPlantGrowth(var1, var2, var3 - 1, var4, this);
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        if (var2 < 0)
        {
            var2 = 7;
        }
        else if (var2 >= 8)
        {
            return 248;
        }

        return this.blockIndexInTexture + var2;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
    {
        super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, var7);

        if (!var1.isRemote)
        {
            if (var1.rand.nextInt(100) < 50)
            {
                float var8 = 0.7F;
                float var9 = var1.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
                float var10 = var1.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
                float var11 = var1.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
                EntityItem var12 = new EntityItem(var1, (double)((float)var2 + var9), (double)((float)var3 + var10), (double)((float)var4 + var11), new ItemStack(mod_FCBetterThanWolves.fcHempSeeds));
                var12.delayBeforeCanPickup = 10;
                var1.spawnEntityInWorld(var12);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return var1 >= 7 ? mod_FCBetterThanWolves.fcHemp.shiftedIndex : -1;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 1;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);

        if (var5 < 7)
        {
            this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, (float)(1 + var5) / 8.0F, 0.7F);
        }
        else
        {
            this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
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
}
