package net.minecraft.src;

public class ItemWood extends ItemBlock
{
    /** The instance of Block. */
    private Block theWoodBlock;

    public ItemWood(int par1, Block par2Block)
    {
        super(par1);
        this.theWoodBlock = par2Block;
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

        if (var2 < 0 || var2 >= BlockWood.woodType.length)
        {
            var2 = 0;
        }

        return super.getItemName() + "." + BlockWood.woodType[var2];
    }
}
