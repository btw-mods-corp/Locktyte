package net.minecraft.src;

import java.util.Random;

public class BlockStoneBrick extends Block
{
    public static final String[] STONE_BRICK_TYPES = new String[] {"default", "mossy", "cracked", "chiseled"};

    public BlockStoneBrick(int par1)
    {
        super(par1, 54, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        switch (par2)
        {
            case 1:
                return 100;

            case 2:
                return 101;

            case 3:
                return 213;

            default:
                return 54;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var6 == 0 && !var1.getBlockMaterial(var2, var3 - 1, var4).blocksMovement())
        {
            int var7 = var1.getBlockId(var2, var3 + 1, var4);

            if (var7 != Block.waterMoving.blockID && var7 != Block.waterStill.blockID)
            {
                if ((var7 == Block.lavaMoving.blockID || var7 == Block.lavaStill.blockID) && var5.nextInt(15) == 0)
                {
                    var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
                    var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
                }
            }
            else if (var5.nextInt(15) == 0)
            {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
                var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            }
        }
    }
}
