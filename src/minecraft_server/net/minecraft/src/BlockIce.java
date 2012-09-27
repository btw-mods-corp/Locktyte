package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable
{
    public BlockIce(int par1, int par2)
    {
        super(par1, par2, Material.ice, false);
        this.slipperiness = 0.98F;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        par2EntityPlayer.addExhaustion(0.025F);

        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer.inventory))
        {
            ItemStack var9 = this.createStackedBlock(par6);

            if (var9 != null)
            {
                this.dropBlockAsItem_do(par1World, par3, par4, par5, var9);
            }
        }
        else
        {
            if (par1World.provider.isHellWorld)
            {
                par1World.setBlockWithNotify(par3, par4, par5, 0);
                return;
            }

            int var7 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer.inventory);
            this.dropBlockAsItem(par1World, par3, par4, par5, par6, var7);
            Material var8 = par1World.getBlockMaterial(par3, par4 - 1, par5);

            if (mod_FCBetterThanWolves.IsHardcoreBucketsEnabled(par1World))
            {
                FCUtilsMisc.PlaceNonPersistantWater(par1World, par3, par4, par5);
                return;
            }

            if (var8.blocksMovement() || var8.isLiquid())
            {
                par1World.setBlockWithNotify(par3, par4, par5, Block.waterMoving.blockID);
            }
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (mod_FCBetterThanWolves.IsHardcoreBucketsEnabled(par1World))
        {
            if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[this.blockID])
            {
                FCUtilsMisc.PlaceNonPersistantWater(par1World, par2, par3, par4);
            }
        }
        else
        {
            if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[this.blockID])
            {
                if (par1World.provider.isHellWorld)
                {
                    par1World.setBlockWithNotify(par2, par3, par4, 0);
                    return;
                }

                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockWithNotify(par2, par3, par4, Block.waterStill.blockID);
            }
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 0;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        if (var1.provider.isHellWorld)
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

            for (int var5 = 0; var5 < 8; ++var5)
            {
                var1.spawnParticle("largesmoke", (double)var2 + Math.random(), (double)var3 + Math.random(), (double)var4 + Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        if (mod_FCBetterThanWolves.IsHardcoreBucketsEnabled(var1))
        {
            int var9 = var1.getBlockId(var2, var3 + 1, var4);

            if (!FCUtilsMisc.IsIKInColdBiome(var1, var2, var4) || var9 != this.blockID && !var1.canBlockSeeTheSky(var2, var3 + 1, var4))
            {
                FCUtilsMisc.PlaceNonPersistantWater(var1, var2, var3, var4);
            }
        }
    }
}
