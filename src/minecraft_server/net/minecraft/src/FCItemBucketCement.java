package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemBucketCement extends ItemBucket implements ITextureProvider
{
    public FCItemBucketCement(int var1, int var2)
    {
        super(var1, var2);
        this.setIconIndex(0);
        this.setItemName("fcBucketCement");
        this.setContainerItem(Item.bucketEmpty);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        var2.playSoundAtEntity(var3, "mob.ghast.moan", 0.5F, 2.6F + (var2.rand.nextFloat() - var2.rand.nextFloat()) * 0.8F);
        return super.onItemRightClick(var1, var2, var3);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
