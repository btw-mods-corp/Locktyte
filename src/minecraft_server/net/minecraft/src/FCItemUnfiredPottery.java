package net.minecraft.src;

public class FCItemUnfiredPottery extends ItemBlock
{
    public FCItemUnfiredPottery(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcUnfiredPottery");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }
}
