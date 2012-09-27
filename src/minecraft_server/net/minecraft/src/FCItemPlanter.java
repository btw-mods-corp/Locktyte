package net.minecraft.src;

public class FCItemPlanter extends ItemBlock
{
    public FCItemPlanter(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcPlanter");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }
}
