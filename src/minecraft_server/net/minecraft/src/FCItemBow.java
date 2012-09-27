package net.minecraft.src;

public class FCItemBow extends ItemBow
{
    public FCItemBow(int var1)
    {
        super(var1);
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        boolean var5 = var3.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) > 0;
        boolean var6 = var3.inventory.hasItem(Item.arrow.shiftedIndex);

        if (var5 || var6 || var3.inventory.hasItem(mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex))
        {
            int var7 = this.getMaxItemUseDuration(var1) - var4;
            float var8 = (float)var7 / 20.0F;
            var8 = (var8 * var8 + var8 * 2.0F) / 3.0F;

            if ((double)var8 < 0.1D)
            {
                return;
            }

            if (var8 > 1.0F)
            {
                var8 = 1.0F;
            }

            Object var9;

            if (var5)
            {
                var9 = new FCEntityInfiniteArrow(var2, var3, var8 * 2.0F);
            }
            else if (var6)
            {
                var9 = new EntityArrow(var2, var3, var8 * 2.0F);
            }
            else
            {
                var9 = new FCEntityRottenArrow(var2, var3, var8 * 2.0F);
            }

            if (var8 == 1.0F)
            {
                ((EntityArrow)var9).setIsCritical(true);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1);

            if (var10 > 0)
            {
                ((EntityArrow)var9).setDamage(((EntityArrow)var9).getDamage() + (double)var10 * 0.5D + 0.5D);
            }

            int var11 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, var1);

            if (var11 > 0)
            {
                ((EntityArrow)var9).setKnockbackStrength(var11);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) > 0)
            {
                ((EntityArrow)var9).setFire(100);
            }

            var1.damageItem(1, var3);
            var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var8 * 0.5F);

            if (var5)
            {
                ((EntityArrow)var9).canBePickedUp = 2;
            }
            else if (var6)
            {
                var3.inventory.consumeInventoryItem(Item.arrow.shiftedIndex);
            }
            else
            {
                var3.inventory.consumeInventoryItem(mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex);
                ((EntityArrow)var9).canBePickedUp = 2;
            }

            if (!var2.isRemote)
            {
                var2.spawnEntityInWorld((Entity)var9);
            }
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.capabilities.isCreativeMode || var3.inventory.hasItem(Item.arrow.shiftedIndex) || var3.inventory.hasItem(mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex) || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) > 0)
        {
            var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
        }

        return var1;
    }
}
