package net.minecraft.src;

import forge.ITextureProvider;

public class FCItem extends Item implements ITextureProvider
{
    public FCItem(int var1)
    {
        super(var1);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
