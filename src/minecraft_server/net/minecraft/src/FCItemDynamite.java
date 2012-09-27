package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemDynamite extends Item implements ITextureProvider
{
    public FCItemDynamite(int var1)
    {
        super(var1);
        this.setIconIndex(60);
        this.setItemName("fcDynamite");
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        int var4 = -1;

        for (int var5 = 0; var5 < var3.inventory.mainInventory.length; ++var5)
        {
            if (var3.inventory.mainInventory[var5] != null && var3.inventory.mainInventory[var5].itemID == Item.flintAndSteel.shiftedIndex)
            {
                var4 = var5;
                break;
            }
        }

        if (!var2.isRemote)
        {
            boolean var7 = false;

            if (var4 >= 0)
            {
                var7 = true;
                ItemStack var6 = var3.inventory.getStackInSlot(var4);
                var6.damageItem(1, var3);

                if (var6.stackSize <= 0)
                {
                    var3.inventory.mainInventory[var4] = null;
                }
            }

            --var1.stackSize;
            FCEntityDynamite var8 = new FCEntityDynamite(var2, var3, this.shiftedIndex, var7);
            var2.spawnEntityInWorld(var8);

            if (var7)
            {
                var2.playSoundAtEntity(var8, "random.fuse", 1.0F, 1.0F);
            }
            else
            {
                var2.playSoundAtEntity(var8, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }
        }

        return var1;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
