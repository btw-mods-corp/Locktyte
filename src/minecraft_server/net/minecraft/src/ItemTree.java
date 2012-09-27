package net.minecraft.src;

public class ItemTree extends ItemBlock
{
    /** Instance of Block. */
    private Block theBlock;

    public ItemTree(int par1, Block par2Block)
    {
        super(par1);
        this.theBlock = par2Block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();

        if (var2 < 0 || var2 >= BlockLog.woodType.length)
        {
            var2 = 0;
        }

        return super.getItemName() + "." + BlockLog.woodType[var2];
    }
}
