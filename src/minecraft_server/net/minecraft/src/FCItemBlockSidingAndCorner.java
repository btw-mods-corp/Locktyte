package net.minecraft.src;

public class FCItemBlockSidingAndCorner extends ItemBlock
{
    public FCItemBlockSidingAndCorner(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName(Block.blocksList[this.getBlockID()].getBlockName());
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    public String getItemNameIS(ItemStack var1)
    {
        return var1.getItemDamage() == 0 ? super.getItemName() + "." + "siding" : super.getItemName() + "." + "corner";
    }
}
