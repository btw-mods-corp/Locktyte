package net.minecraft.src;

import java.util.Random;

public class BlockSapling extends BlockFlower
{
    public static final String[] WOOD_TYPES = new String[] {"oak", "spruce", "birch", "jungle"};

    protected BlockSapling(int par1, int par2)
    {
        super(par1, par2);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            super.updateTick(par1World, par2, par3, par4, par5Random);

            if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
            {
                int var6 = par1World.getBlockMetadata(par2, par3, par4);

                if ((var6 & 8) == 0)
                {
                    par1World.setBlockMetadata(par2, par3, par4, var6 | 8);
                }
                else
                {
                    this.growTree(par1World, par2, par3, par4, par5Random);
                }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        par2 &= 3;
        return par2 == 1 ? 63 : (par2 == 2 ? 79 : (par2 == 3 ? 30 : super.getBlockTextureFromSideAndMetadata(par1, par2)));
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;

        if (var6 < 4)
        {
            this.FCGrowTree(par1World, par2, par3, par4, par5Random);
        }
        else
        {
            Object var7 = null;
            int var8 = 0;
            int var9 = 0;
            boolean var10 = false;

            if (var6 == 1)
            {
                var7 = new WorldGenTaiga2(true);
            }
            else if (var6 == 2)
            {
                var7 = new WorldGenForest(true);
            }
            else if (var6 == 3)
            {
                for (var8 = 0; var8 >= -1; --var8)
                {
                    for (var9 = 0; var9 >= -1; --var9)
                    {
                        if (this.isSameSapling(par1World, par2 + var8, par3, par4 + var9, 3) && this.isSameSapling(par1World, par2 + var8 + 1, par3, par4 + var9, 3) && this.isSameSapling(par1World, par2 + var8, par3, par4 + var9 + 1, 3) && this.isSameSapling(par1World, par2 + var8 + 1, par3, par4 + var9 + 1, 3))
                        {
                            var7 = new WorldGenHugeTrees(true, 10 + par5Random.nextInt(20), 3, 3);
                            var10 = true;
                            break;
                        }
                    }

                    if (var7 != null)
                    {
                        break;
                    }
                }

                if (var7 == null)
                {
                    var9 = 0;
                    var8 = 0;
                    var7 = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
                }
            }
            else
            {
                var7 = new WorldGenTrees(true);

                if (par5Random.nextInt(10) == 0)
                {
                    var7 = new WorldGenBigTree(true);
                }
            }

            if (var10)
            {
                par1World.setBlock(par2 + var8, par3, par4 + var9, 0);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, 0);
                par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, 0);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, 0);
            }
            else
            {
                par1World.setBlock(par2, par3, par4, 0);
            }

            if (!((WorldGenerator)((WorldGenerator)var7)).generate(par1World, par5Random, par2 + var8, par3, par4 + var9))
            {
                if (var10)
                {
                    par1World.setBlockAndMetadata(par2 + var8, par3, par4 + var9, this.blockID, var6);
                    par1World.setBlockAndMetadata(par2 + var8 + 1, par3, par4 + var9, this.blockID, var6);
                    par1World.setBlockAndMetadata(par2 + var8, par3, par4 + var9 + 1, this.blockID, var6);
                    par1World.setBlockAndMetadata(par2 + var8 + 1, par3, par4 + var9 + 1, this.blockID, var6);
                }
                else
                {
                    par1World.setBlockAndMetadata(par2, par3, par4, this.blockID, var6);
                }
            }
        }
    }

    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5)
    {
        return par1World.getBlockId(par2, par3, par4) == this.blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1 & 3;
    }

    public void FCGrowTree(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 3;
        boolean var7 = false;
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;

        if (var6 != 3)
        {
            var1.setBlock(var2, var3, var4, 0);
        }

        if (var6 == 1)
        {
            var7 = FCUtilsTrees.GenerateTaiga2(var1, var5, var2, var3, var4);
        }
        else if (var6 == 2)
        {
            var7 = FCUtilsTrees.GenerateForest(var1, var5, var2, var3, var4);
        }
        else if (var6 == 3)
        {
            while (var8 >= -1)
            {
                for (var9 = 0; var9 >= -1; --var9)
                {
                    if (this.isSameSapling(var1, var2 + var8, var3, var4 + var9, 3) && this.isSameSapling(var1, var2 + var8 + 1, var3, var4 + var9, 3) && this.isSameSapling(var1, var2 + var8, var3, var4 + var9 + 1, 3) && this.isSameSapling(var1, var2 + var8 + 1, var3, var4 + var9 + 1, 3))
                    {
                        var1.setBlock(var2 + var8, var3, var4 + var9, 0);
                        var1.setBlock(var2 + var8 + 1, var3, var4 + var9, 0);
                        var1.setBlock(var2 + var8, var3, var4 + var9 + 1, 0);
                        var1.setBlock(var2 + var8 + 1, var3, var4 + var9 + 1, 0);
                        FCUtilsGenHugeTree var11 = new FCUtilsGenHugeTree(true, 10 + var5.nextInt(20), 3, 3);
                        var7 = var11.generate(var1, var5, var2 + var8, var3, var4 + var9);
                        var10 = true;
                        break;
                    }
                }

                if (var10)
                {
                    break;
                }

                --var8;
            }

            if (!var10)
            {
                var9 = 0;
                var8 = 0;
                var1.setBlock(var2, var3, var4, 0);
                var7 = FCUtilsTrees.GenerateTrees(var1, var5, var2, var3, var4, 4 + var5.nextInt(7), 3, 3, false);
            }
        }
        else if (var5.nextInt(10) == 0)
        {
            FCUtilsGenBigTree var12 = new FCUtilsGenBigTree(true);
            var7 = var12.generate(var1, var5, var2, var3, var4);
        }
        else
        {
            var7 = FCUtilsTrees.GenerateTrees(var1, var5, var2, var3, var4);
        }

        if (!var7)
        {
            if (var10)
            {
                var1.setBlockAndMetadata(var2 + var8, var3, var4 + var9, this.blockID, var6);
                var1.setBlockAndMetadata(var2 + var8 + 1, var3, var4 + var9, this.blockID, var6);
                var1.setBlockAndMetadata(var2 + var8, var3, var4 + var9 + 1, this.blockID, var6);
                var1.setBlockAndMetadata(var2 + var8 + 1, var3, var4 + var9 + 1, this.blockID, var6);
            }
            else
            {
                var1.setBlockAndMetadata(var2, var3, var4, this.blockID, var6);
            }
        }
    }
}
