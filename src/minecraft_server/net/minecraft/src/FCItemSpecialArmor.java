package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemSpecialArmor extends ItemArmor implements ITextureProvider
{
    static final int m_iRenderIndex = 1;
    static final int m_iArmorLevel = 1;
    static final int m_iMaxDamage = 12;

    public FCItemSpecialArmor(int var1, int var2)
    {
        super(var1, EnumArmorMaterial.CLOTH, 1, var2);
        this.setMaxDamage(12);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
