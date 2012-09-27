package net.minecraft.src;

public class FCItemBlockMoulding extends ItemBlock
{
    public FCItemBlockMoulding(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName(Block.blocksList[this.getBlockID()].getBlockName());
    }

    public String getItemNameIS(ItemStack var1)
    {
        return super.getItemName() + "." + "moulding";
    }
}
