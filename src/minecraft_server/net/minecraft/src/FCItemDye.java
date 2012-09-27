package net.minecraft.src;

public class FCItemDye extends ItemDye
{
    public FCItemDye(int var1)
    {
        super(var1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        return var2 != null && !var2.canPlayerEdit(var4, var5, var6) ? false : (var1.getItemDamage() == 15 ? this.ApplyBoneMeal(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10) : (var1.getItemDamage() == 3 ? this.ApplyCocoaBeans(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10) : false));
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public boolean useItemOnEntity(ItemStack var1, EntityLiving var2)
    {
        if (var2 instanceof EntitySheep)
        {
            EntitySheep var3 = (EntitySheep)var2;
            int var4 = BlockCloth.getBlockFromDye(var1.getItemDamage());

            if (!var3.getSheared() && var3.getFleeceColor() != var4)
            {
                var3.setSuperficialFleeceColor(var4);
                --var1.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean CanBonemealBeAppliedToBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        return var5 == Block.tilledField.blockID || var5 == mod_FCBetterThanWolves.fcPlanter.blockID && var6 == 1 || var5 == Block.grass.blockID;
    }

    private boolean ApplyCocoaBeans(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        int var11 = var3.getBlockId(var4, var5, var6);
        int var12 = var3.getBlockMetadata(var4, var5, var6);

        if (var11 == Block.wood.blockID && BlockLog.limitToValidMetadata(var12) == 3)
        {
            if (var7 != 0 && var7 != 1)
            {
                FCUtilsBlockPos var13 = new FCUtilsBlockPos(var4, var5, var6);
                var13.AddFacingAsOffset(var7);

                if (var3.isAirBlock(var13.i, var13.j, var13.k))
                {
                    int var14 = Block.cocoaPlant.blockID;
                    var3.setBlockWithNotify(var13.i, var13.j, var13.k, var14);

                    if (var3.getBlockId(var13.i, var13.j, var13.k) == var14)
                    {
                        Block.blocksList[var14].updateBlockMetadata(var3, var13.i, var13.j, var13.k, var7, var8, var9, var10);
                    }

                    if (var2 == null || !var2.capabilities.isCreativeMode)
                    {
                        --var1.stackSize;
                    }
                }

                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean ApplyBoneMeal(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (!this.CanBonemealBeAppliedToBlock(var3, var4, var5, var6))
        {
            --var5;
        }

        if (this.CanBonemealBeAppliedToBlock(var3, var4, var5, var6))
        {
            if (!var3.isRemote)
            {
                int var11 = var3.getBlockId(var4, var5, var6);

                if (var11 == Block.tilledField.blockID)
                {
                    int var12 = var3.getBlockMetadata(var4, var5, var6);
                    var3.setBlockAndMetadataWithNotify(var4, var5, var6, mod_FCBetterThanWolves.fcBlockFarmlandFertilized.blockID, var12);
                }
                else if (var11 == mod_FCBetterThanWolves.fcPlanter.blockID)
                {
                    ((FCBlockPlanter)((FCBlockPlanter)mod_FCBetterThanWolves.fcPlanter)).SetPlanterType(var3, var4, var5, var6, 2);
                }
                else if (var11 == Block.grass.blockID)
                {
                    this.GrowTallGrassAndFLowers(var3, var4, var5, var6);
                }

                --var1.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private void GrowTallGrassAndFLowers(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        while (var5 < 128)
        {
            int var6 = var2;
            int var7 = var3 + 1;
            int var8 = var4;
            boolean var9 = true;
            int var10 = 0;

            while (true)
            {
                if (var10 < var5 / 16)
                {
                    var6 += itemRand.nextInt(3) - 1;
                    var7 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
                    var8 += itemRand.nextInt(3) - 1;

                    if (var1.getBlockId(var6, var7 - 1, var8) == Block.grass.blockID && !var1.isBlockNormalCube(var6, var7, var8))
                    {
                        ++var10;
                        continue;
                    }

                    var9 = false;
                }

                if (var9 && var1.getBlockId(var6, var7, var8) == 0)
                {
                    if (itemRand.nextInt(10) != 0 && Block.tallGrass.canBlockStay(var1, var6, var7, var8))
                    {
                        var1.setBlockAndMetadataWithNotify(var6, var7, var8, Block.tallGrass.blockID, 1);
                    }
                    else if (itemRand.nextInt(3) != 0 && Block.plantYellow.canBlockStay(var1, var6, var7, var8))
                    {
                        var1.setBlockWithNotify(var6, var7, var8, Block.plantYellow.blockID);
                    }
                    else if (Block.plantRed.canBlockStay(var1, var6, var7, var8))
                    {
                        var1.setBlockWithNotify(var6, var7, var8, Block.plantRed.blockID);
                    }
                }

                ++var5;
                break;
            }
        }
    }
}
