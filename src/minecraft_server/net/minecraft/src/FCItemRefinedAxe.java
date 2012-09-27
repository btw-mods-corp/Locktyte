package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemRefinedAxe extends ItemAxe implements ITextureProvider
{
    private final int m_iWeaponDamage = 6;
    private final int m_iEnchantability = 0;
    private final int maxUses = 2250;

    protected FCItemRefinedAxe(int var1)
    {
        super(var1, EnumToolMaterial.EMERALD);
        this.setMaxDamage(2250);
        this.efficiencyOnProperMaterial = 12.0F;
        this.setIconIndex(64);
        this.setItemName("fcRefinedAxe");
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack var1)
    {
        return EnumAction.block;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack var1)
    {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
        return var1;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity var1)
    {
        return 6;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
