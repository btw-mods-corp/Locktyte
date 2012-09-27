package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemArcaneScroll extends Item implements ITextureProvider
{
    public FCItemArcaneScroll(int var1)
    {
        super(var1);
        this.setIconIndex(68);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcArcaneScroll");
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
