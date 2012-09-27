package net.minecraft.src;

public class FCItemOmniSlab extends ItemBlock
{
    public FCItemOmniSlab(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcOmniSlab");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    public float GetBuoyancy(int var1)
    {
        return var1 == 0 ? -1.0F : super.GetBuoyancy(var1);
    }
}
