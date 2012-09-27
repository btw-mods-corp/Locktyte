package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemCandle extends Item implements ITextureProvider
{
    public static final int[] m_iCandeColors = new int[] {1052688, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 8618883, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 16777215};

    public FCItemCandle(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setIconIndex(69);
        this.setItemName("fcCandle");
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
