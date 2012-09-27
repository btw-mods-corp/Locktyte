package net.minecraft.src;

public class FCItemBlockAestheticOpaque extends ItemBlock
{
    public FCItemBlockAestheticOpaque(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcAestheticOpaque");
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
        switch (var1.getItemDamage())
        {
            case 0:
                return super.getItemName() + "." + "wicker";

            case 1:
                return super.getItemName() + "." + "dung";

            case 2:
                return super.getItemName() + "." + "steel";

            case 3:
                return super.getItemName() + "." + "hellfire";

            case 4:
                return super.getItemName() + "." + "padding";

            case 5:
                return super.getItemName() + "." + "soap";

            case 6:
                return super.getItemName() + "." + "rope";

            case 7:
                return super.getItemName() + "." + "flint";

            default:
                return super.getItemName();
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        int var11 = var1.getItemDamage();
        return var11 == 2 ? FCUtilsMisc.OnItemBlockUseWithBlockSubstitution(var1, var2, var3, var4, var5, var6, var7, mod_FCBetterThanWolves.fcSoulforgedSteelBlock.blockID, 0) : super.onItemUse(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
    }

    public float GetBuoyancy(int var1)
    {
        switch (var1)
        {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
                return 1.0F;

            case 2:
            case 3:
            default:
                return super.GetBuoyancy(var1);
        }
    }
}
