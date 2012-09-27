package net.minecraft.src;

public class FCItemMattock extends FCItemRefinedPickAxe
{
    protected FCItemMattock(int var1)
    {
        super(var1);
        this.setIconIndex(63);
        this.setItemName("fcMattock");
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack var1, Block var2)
    {
        float var3 = super.getStrVsBlock(var1, var2);
        float var4 = ((FCItemRefinedShovel)((FCItemRefinedShovel)mod_FCBetterThanWolves.fcRefinedShovel)).getStrVsBlock(var1, var2);
        return var4 > var3 ? var4 : var3;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block var1)
    {
        return super.canHarvestBlock(var1) || ((FCItemRefinedShovel)((FCItemRefinedShovel)mod_FCBetterThanWolves.fcRefinedShovel)).canHarvestBlock(var1);
    }
}
