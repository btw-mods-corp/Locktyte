package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemFood extends ItemFood implements ITextureProvider
{
    public static final int m_iDonutIconIndex = 8;
    public static final int m_iDonutHungerHealed = 2;
    public static final float m_fDonutSaturationModifier = 0.2F;
    public static final String m_sDonutItemName = "fcDonut";
    public static final int m_iDogFoodIconIndex = 73;
    public static final int m_iDogFoodHungerHealed = 4;
    public static final float m_fDogFoodSaturationModifier = 0.4F;
    public static final String m_sDogFoodItemName = "fcDogfood";
    public static final int m_iRawEggIconIndex = 74;
    public static final int m_iRawEggHungerHealed = 2;
    public static final float m_fRawEggSaturationModifier = 0.2F;
    public static final String m_sRawEggItemName = "fcRawEgg";
    public static final int m_iFriedEggIconIndex = 75;
    public static final int m_iFriedEggHungerHealed = 4;
    public static final float m_fFriedEggSaturationModifier = 0.4F;
    public static final String m_sFriedEggItemName = "fcFriedEgg";

    public FCItemFood(int var1, int var2, int var3, float var4, boolean var5, String var6)
    {
        super(var1, var3, var4, var5);
        this.setIconIndex(var2);
        this.setItemName(var6);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
