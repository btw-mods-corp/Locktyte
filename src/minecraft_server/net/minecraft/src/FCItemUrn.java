package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemUrn extends Item implements ITextureProvider
{
    public FCItemUrn(int var1)
    {
        super(var1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (var2 != null && !var2.canPlayerEdit(var4, var5, var6))
        {
            return false;
        }
        else if (var1.stackSize == 0)
        {
            return false;
        }
        else
        {
            int var12;

            if (var1.itemID == mod_FCBetterThanWolves.fcUrn.shiftedIndex)
            {
                FCUtilsBlockPos var15 = new FCUtilsBlockPos(var4, var5, var6);
                var15.AddFacingAsOffset(var7);
                var12 = mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID;
                byte var17 = 0;

                if (var3.canPlaceEntityOnSide(var12, var15.i, var15.j, var15.k, false, var7, var2) && var3.setBlockAndMetadataWithNotify(var15.i, var15.j, var15.k, var12, var17))
                {
                    Block var18 = Block.blocksList[var12];

                    if (var3.getBlockId(var15.i, var15.j, var15.k) == var12)
                    {
                        Block.blocksList[var12].updateBlockMetadata(var3, var15.i, var15.j, var15.k, var7, 0.0F, 0.0F, 0.0F);
                        Block.blocksList[var12].onBlockPlacedBy(var3, var15.i, var15.j, var15.k, var2);
                    }

                    var3.playSoundEffect((double)((float)var15.i + 0.5F), (double)((float)var15.j + 0.5F), (double)((float)var15.k + 0.5F), var18.stepSound.getStepSound(), (var18.stepSound.getVolume() + 1.0F) / 2.0F, var18.stepSound.getPitch() * 0.8F);
                    --var1.stackSize;
                }

                return true;
            }
            else
            {
                if (var1.itemID == mod_FCBetterThanWolves.fcSoulUrn.shiftedIndex)
                {
                    boolean var11 = false;
                    var12 = var3.getBlockId(var4, var5, var6);
                    int var13;

                    if (var12 == mod_FCBetterThanWolves.fcAestheticVegetation.blockID)
                    {
                        var13 = var3.getBlockMetadata(var4, var5, var6);

                        if (var13 == 2)
                        {
                            if (!var3.isRemote)
                            {
                                ((FCBlockAestheticVegetation)mod_FCBetterThanWolves.fcAestheticVegetation).AttemptToGrowBloodwoodSapling(var3, var4, var5, var6, var3.rand);
                            }

                            var11 = true;
                        }
                    }
                    else if (var12 == Block.netherStalk.blockID)
                    {
                        var13 = var3.getBlockMetadata(var4, var5, var6);

                        if (var13 < 3)
                        {
                            if (!var3.isRemote)
                            {
                                var3.setBlockMetadataWithNotify(var4, var5, var6, 3);
                                var3.markBlocksDirty(var4, var5, var6, var4, var5, var6);
                            }

                            var11 = true;
                        }
                    }
                    else if (var12 == mod_FCBetterThanWolves.fcBlockBloodMoss.blockID)
                    {
                        FCBlockBloodMoss var16 = (FCBlockBloodMoss)mod_FCBetterThanWolves.fcBlockBloodMoss;
                        int var14 = var16.GetHeightLevel(var3, var4, var5, var6);

                        if (var14 < 7)
                        {
                            var16.SetHeightLevel(var3, var4, var5, var6, 7);
                            var3.markBlocksDirty(var4, var5, var6, var4, var5, var6);
                        }

                        var11 = true;
                    }

                    if (var11)
                    {
                        if (!var3.isRemote)
                        {
                            --var1.stackSize;
                            var3.playAuxSFX(2225, var4, var5, var6, 0);
                            var3.playSoundEffect((double)var4 + 0.5D, (double)var5 + 0.5D, (double)var6 + 0.5D, "random.glass", 1.0F, 1.2F / (var3.rand.nextFloat() * 0.2F + 0.9F));
                        }

                        return true;
                    }
                }

                return false;
            }
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var1.itemID != mod_FCBetterThanWolves.fcUrn.shiftedIndex)
        {
            --var1.stackSize;
            var2.playSoundAtEntity(var3, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!var2.isRemote)
            {
                var2.spawnEntityInWorld(new FCEntityUrn(var2, var3, this.shiftedIndex));
            }
        }

        return var1;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
