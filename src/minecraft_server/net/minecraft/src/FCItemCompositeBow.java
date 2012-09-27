package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemCompositeBow extends ItemBow implements ITextureProvider
{
    private static int m_iMaxDamage = 576;

    public FCItemCompositeBow(int var1)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(m_iMaxDamage);
        this.setIconIndex(40);
        this.setItemName("fcCompositeBow");
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        boolean var5 = var3.inventory.hasItem(mod_FCBetterThanWolves.fcBroadheadArrow.shiftedIndex);

        if (var5)
        {
            this.AttemptToFireBroadheadArrow(var1, var2, var3, var4);
        }
        else
        {
            boolean var6 = var3.inventory.hasItem(Item.arrow.shiftedIndex) || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) > 0;

            if (var6)
            {
                this.AttemptToFireRegularArrow(var1, var2, var3, var4);
            }
            else
            {
                boolean var10000;

                if (!var3.inventory.hasItem(mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex) && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) <= 0)
                {
                    var10000 = false;
                }
                else
                {
                    var10000 = true;
                }

                this.AttemptToFireRottenArrow(var1, var2, var3, var4);
            }
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.inventory.hasItem(Item.arrow.shiftedIndex) || var3.inventory.hasItem(mod_FCBetterThanWolves.fcBroadheadArrow.shiftedIndex) || var3.inventory.hasItem(mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex) || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) > 0)
        {
            var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
        }

        return var1;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }

    private float GetCurrentPullStrength(ItemStack var1, int var2)
    {
        int var3 = this.getMaxItemUseDuration(var1) - var2;
        float var4 = (float)var3 / 20.0F;
        var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;

        if (var4 > 1.0F)
        {
            var4 = 1.0F;
        }

        return var4;
    }

    private void AttemptToFireRegularArrow(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        boolean var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) > 0;
        float var6 = this.GetCurrentPullStrength(var1, var4);

        if ((double)var6 >= 0.1D)
        {
            boolean var7 = var6 == 1.0F;
            Object var8;

            if (var5)
            {
                var8 = new FCEntityInfiniteArrow(var2, var3, var6 * 3.0F);
            }
            else
            {
                var8 = new EntityArrow(var2, var3, var6 * 3.0F);
            }

            if (var7)
            {
                ((EntityArrow)var8).setIsCritical(true);
            }

            int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1);

            if (var9 > 0)
            {
                ((EntityArrow)var8).setDamage(((EntityArrow)var8).getDamage() + (double)var9 * 0.5D + 0.5D);
            }

            var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, var1);

            if (var9 > 0)
            {
                ((EntityArrow)var8).setKnockbackStrength(var9);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) > 0)
            {
                ((EntityArrow)var8).setFire(100);
            }

            if (var5)
            {
                ((EntityArrow)var8).canBePickedUp = 2;
            }
            else
            {
                var3.inventory.consumeInventoryItem(Item.arrow.shiftedIndex);
            }

            var1.damageItem(1, var3);
            var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var6 * 0.5F);

            if (!var2.isRemote)
            {
                var2.spawnEntityInWorld((Entity)var8);
            }
        }
    }

    private void AttemptToFireBroadheadArrow(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        float var5 = this.GetCurrentPullStrength(var1, var4);

        if ((double)var5 >= 0.1D)
        {
            boolean var6 = var5 == 1.0F;
            FCEntityBroadheadArrow var7 = new FCEntityBroadheadArrow(var2, var3, var5 * 3.0F);

            if (var6)
            {
                var7.setIsCritical(true);
            }

            int var8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1);

            if (var8 > 0)
            {
                var7.setDamage(var7.getDamage() + (double)var8 * 0.5D + 0.5D);
            }

            var8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, var1);

            if (var8 > 0)
            {
                var7.setKnockbackStrength(var8);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) > 0)
            {
                var7.setFire(100);
            }

            var3.inventory.consumeInventoryItem(mod_FCBetterThanWolves.fcBroadheadArrow.shiftedIndex);
            var1.damageItem(1, var3);
            var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var5 * 0.5F);

            if (!var2.isRemote)
            {
                var2.spawnEntityInWorld(var7);
            }
        }
    }

    private void AttemptToFireRottenArrow(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        float var5 = this.GetCurrentPullStrength(var1, var4);
        var3.inventory.consumeInventoryItem(mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex);
        var1.damageItem(1, var3);
        var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var5 * 0.5F);
        var2.playSoundAtEntity(var3, "random.break", 0.8F, 0.8F + var2.rand.nextFloat() * 0.4F);
        float var6 = -MathHelper.sin(var3.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(var3.rotationPitch / 180.0F * (float)Math.PI) * var5;
        float var7 = MathHelper.cos(var3.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(var3.rotationPitch / 180.0F * (float)Math.PI) * var5;
        float var8 = -MathHelper.sin(var3.rotationPitch / 180.0F * (float)Math.PI) * var5;

        for (int var9 = 0; var9 < 32; ++var9)
        {
            var2.spawnParticle("iconcrack_333", var3.posX, var3.posY + (double)var3.getEyeHeight(), var3.posZ, (double)var6 + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F), (double)var8 + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F), (double)var7 + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F));
        }
    }
}
