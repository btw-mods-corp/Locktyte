package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemRefinedHoe extends Item implements ITextureProvider
{
    private final int maxUses = 2250;

    public FCItemRefinedHoe(int var1)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(2250);
        this.setIconIndex(29);
        this.setItemName("fcRefinedHoe");
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        int var11 = var3.getBlockId(var4, var5, var6);
        int var12 = var3.getBlockId(var4, var5 + 1, var6);

        if ((var7 == 0 || var12 != 0 || var11 != Block.grass.blockID) && var11 != Block.dirt.blockID)
        {
            return false;
        }
        else
        {
            Block var13 = Block.tilledField;
            var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

            if (var3.isRemote)
            {
                return true;
            }
            else
            {
                if (var11 == Block.grass.blockID)
                {
                    byte var14 = 16;

                    if (var3.rand.nextInt(100) < var14)
                    {
                        float var15 = 0.7F;
                        float var16 = var3.rand.nextFloat() * var15 + (1.0F - var15) * 0.5F;
                        float var17 = 1.2F;
                        float var18 = var3.rand.nextFloat() * var15 + (1.0F - var15) * 0.5F;
                        EntityItem var19 = new EntityItem(var3, (double)((float)var4 + var16), (double)((float)var5 + var17), (double)((float)var6 + var18), new ItemStack(mod_FCBetterThanWolves.fcHempSeeds));
                        var19.delayBeforeCanPickup = 10;
                        var3.spawnEntityInWorld(var19);
                    }
                }

                var3.setBlockWithNotify(var4, var5, var6, var13.blockID);
                var1.damageItem(1, var2);
                return true;
            }
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
