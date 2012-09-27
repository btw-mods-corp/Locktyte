package net.minecraft.src;

public class FCItemLeaves extends ItemBlock
{
    public FCItemLeaves(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1 | 4;
    }
}
