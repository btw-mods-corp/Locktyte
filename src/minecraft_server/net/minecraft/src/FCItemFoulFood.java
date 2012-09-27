package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemFoulFood extends ItemFood implements ITextureProvider
{
    private static final int m_iHealthHealed = 1;
    private static final float m_iSaturationModifier = 0.1F;

    public FCItemFoulFood(int var1)
    {
        super(var1, 1, 0.1F, false);
        this.setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        this.maxStackSize = 64;
        this.setIconIndex(21);
        this.setItemName("fcFoulFood");
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
