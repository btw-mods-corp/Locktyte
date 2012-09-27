package net.minecraft.src;

public class FCItemBlockWoodCornerStub extends FCItemBlockCustom
{
    public FCItemBlockWoodCornerStub(int var1)
    {
        super(var1);
    }

    public int GetBlockIDToPlace(int var1)
    {
        switch (var1)
        {
            case 0:
                return mod_FCBetterThanWolves.fcCorner.blockID;

            case 1:
                return mod_FCBetterThanWolves.fcBlockWoodSpruceSidingAndCorner.blockID;

            case 2:
                return mod_FCBetterThanWolves.fcBlockWoodBirchSidingAndCorner.blockID;

            default:
                return mod_FCBetterThanWolves.fcBlockWoodJungleSidingAndCorner.blockID;
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1 == 0 ? 0 : 1;
    }

    public String getItemNameIS(ItemStack var1)
    {
        return var1.getItemDamage() == 0 ? super.getItemName() + "." + "oak" : (var1.getItemDamage() == 1 ? super.getItemName() + "." + "spruce" : (var1.getItemDamage() == 2 ? super.getItemName() + "." + "birch" : super.getItemName() + "." + "jungle"));
    }
}
