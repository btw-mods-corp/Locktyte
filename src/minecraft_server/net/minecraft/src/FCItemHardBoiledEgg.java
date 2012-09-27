package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemHardBoiledEgg extends ItemFood implements ITextureProvider
{
    private static final int iHardBoiledEggHealthHealed = 4;
    private static final float iHardBoiledEggSaturationModifier = 0.4F;

    public FCItemHardBoiledEgg(int var1)
    {
        super(var1, 4, 0.4F, false);
        this.setIconIndex(49);
        this.setItemName("fcHardBoiledEgg");
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
