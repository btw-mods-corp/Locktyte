package net.minecraft.src;

public class FCItemBlockWoodMouldingStub extends FCItemBlockCustom
{
    public FCItemBlockWoodMouldingStub(int var1)
    {
        super(var1);
    }

    public int GetBlockIDToPlace(int var1)
    {
        switch (var1)
        {
            case 0:
                return mod_FCBetterThanWolves.fcMoulding.blockID;

            case 1:
                return mod_FCBetterThanWolves.fcBlockWoodSpruceMoulding.blockID;

            case 2:
                return mod_FCBetterThanWolves.fcBlockWoodBirchMoulding.blockID;

            default:
                return mod_FCBetterThanWolves.fcBlockWoodJungleMoulding.blockID;
        }
    }

    public String getItemNameIS(ItemStack var1)
    {
        return var1.getItemDamage() == 0 ? super.getItemName() + "." + "oak" : (var1.getItemDamage() == 1 ? super.getItemName() + "." + "spruce" : (var1.getItemDamage() == 2 ? super.getItemName() + "." + "birch" : super.getItemName() + "." + "jungle"));
    }
}
