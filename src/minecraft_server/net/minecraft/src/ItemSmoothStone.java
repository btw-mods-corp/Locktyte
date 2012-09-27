package net.minecraft.src;

public class ItemSmoothStone extends ItemBlock
{
    /** Instance of the Block. */
    private Block theSmoothStoneBlock;

    public ItemSmoothStone(int par1, Block par2Block)
    {
        super(par1);
        this.theSmoothStoneBlock = par2Block;
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

        if (var2 < 0 || var2 >= BlockStoneBrick.STONE_BRICK_TYPES.length)
        {
            var2 = 0;
        }

        return super.getItemName() + "." + BlockStoneBrick.STONE_BRICK_TYPES[var2];
    }
}
