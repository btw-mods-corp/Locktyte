package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemBreedingHarness extends Item implements ITextureProvider
{
    public FCItemBreedingHarness(int var1)
    {
        super(var1);
        this.setIconIndex(61);
        this.setItemName("fcBreedingHarness");
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public boolean useItemOnEntity(ItemStack var1, EntityLiving var2)
    {
        if (var2 instanceof EntityCow || var2 instanceof EntitySheep || var2 instanceof EntityPig)
        {
            EntityAnimal var3 = (EntityAnimal)var2;

            if (!var3.isChild() && !var3.getWearingBreedingHarness())
            {
                --var1.stackSize;
                var3.setWearingBreedingHarness(true);

                if (var3 instanceof EntitySheep)
                {
                    EntitySheep var4 = (EntitySheep)var3;
                    var4.setSheared(true);
                }

                return true;
            }
        }

        return false;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
