package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemRefinedArmor extends ItemArmor implements ITextureProvider
{
    static final int m_iRenderIndex = 1;
    private final int m_iEnchantability = 0;
    static final int m_iMaxDamage = 576;

    public FCItemRefinedArmor(int var1, int var2)
    {
        super(var1, EnumArmorMaterial.DIAMOND, 1, var2);
        this.setMaxDamage(576);
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
