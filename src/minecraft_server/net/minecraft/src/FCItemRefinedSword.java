package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemRefinedSword extends ItemSword implements ITextureProvider
{
    private final int m_iWeaponDamage = 8;
    private final int m_iEnchantability = 0;
    private final int m_iMaxUses = 2250;

    public FCItemRefinedSword(int var1)
    {
        super(var1, EnumToolMaterial.EMERALD);
        this.setMaxDamage(2250);
        this.setIconIndex(31);
        this.setItemName("fcRefinedSword");
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity var1)
    {
        return 8;
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
