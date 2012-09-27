package net.minecraft.src;

public class FCItemDung extends FCItem
{
    public FCItemDung(int var1)
    {
        super(var1);
        this.setIconIndex(11);
        this.setItemName("fcDung");
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public boolean useItemOnEntity(ItemStack var1, EntityLiving var2)
    {
        if (var2 instanceof EntitySheep)
        {
            var2.attackEntityFrom(DamageSource.generic, 0);
            return true;
        }
        else
        {
            return false;
        }
    }
}
