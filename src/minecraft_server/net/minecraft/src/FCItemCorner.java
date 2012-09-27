package net.minecraft.src;

public class FCItemCorner extends ItemBlock
{
    public FCItemCorner(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcCorner");
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
        return var1 > 0 ? -1.0F : super.GetBuoyancy(var1);
    }
}
