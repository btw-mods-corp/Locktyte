package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemTannedArmor extends ItemArmor implements ITextureProvider
{
    static final int m_iRenderIndex = 1;
    static final int m_iArmorLevel = 1;
    static final int m_iMaxDamage = 72;

    public FCItemTannedArmor(int var1, int var2)
    {
        super(var1, EnumArmorMaterial.CLOTH, 1, var2);
        this.setMaxDamage(72);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
