package net.minecraft.src;

public abstract class FCItemBlockCustom extends ItemBlock
{
    public FCItemBlockCustom(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public abstract int GetBlockIDToPlace(int var1);

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        int var11 = var3.getBlockId(var4, var5, var6);

        if (var11 == Block.snow.blockID)
        {
            var7 = 1;
        }
        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
        {
            if (var7 == 0)
            {
                --var5;
            }

            if (var7 == 1)
            {
                ++var5;
            }

            if (var7 == 2)
            {
                --var6;
            }

            if (var7 == 3)
            {
                ++var6;
            }

            if (var7 == 4)
            {
                --var4;
            }

            if (var7 == 5)
            {
                ++var4;
            }
        }

        if (var1.stackSize == 0)
        {
            return false;
        }
        else if (!var2.canPlayerEdit(var4, var5, var6))
        {
            return false;
        }
        else
        {
            int var12 = this.GetBlockIDToPlace(var1.getItemDamage());

            if (var5 == 255 && Block.blocksList[var12].blockMaterial.isSolid())
            {
                return false;
            }
            else if (var3.canPlaceEntityOnSide(var12, var4, var5, var6, false, var7, var2))
            {
                Block var13 = Block.blocksList[var12];

                if (var3.setBlockAndMetadataWithNotify(var4, var5, var6, var12, this.getMetadata(var1.getItemDamage())))
                {
                    if (var3.getBlockId(var4, var5, var6) == var12)
                    {
                        Block.blocksList[var12].updateBlockMetadata(var3, var4, var5, var6, var7, var8, var9, var10);
                        Block.blocksList[var12].onBlockPlacedBy(var3, var4, var5, var6, var2);
                    }

                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);
                    --var1.stackSize;
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
