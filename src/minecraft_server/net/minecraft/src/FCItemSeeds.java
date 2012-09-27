package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemSeeds extends ItemSeeds implements ITextureProvider
{
    public FCItemSeeds(int var1, int var2)
    {
        super(var1, var2, Block.tilledField.blockID);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
