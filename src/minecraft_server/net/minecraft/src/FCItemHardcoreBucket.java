package net.minecraft.src;

public class FCItemHardcoreBucket extends ItemBucket
{
    private int isFull;

    public FCItemHardcoreBucket(int var1, int var2)
    {
        super(var1, var2);
        this.isFull = var2;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (!mod_FCBetterThanWolves.IsHardcoreBucketsEnabled(var2))
        {
            return super.onItemRightClick(var1, var2, var3);
        }
        else
        {
            MovingObjectPosition var4;

            if (this.isFull == 0)
            {
                var4 = FCUtilsMisc.GetMovingObjectPositionFromPlayerHitWaterAndLava(var2, var3, true);
            }
            else
            {
                var4 = this.getMovingObjectPositionFromPlayer(var2, var3, false);
            }

            if (var4 == null)
            {
                return var1;
            }
            else
            {
                if (var4.typeOfHit == EnumMovingObjectType.TILE)
                {
                    int var5 = var4.blockX;
                    int var6 = var4.blockY;
                    int var7 = var4.blockZ;
                    var2.getBlockId(var5, var6, var7);

                    if (!var2.canMineBlock(var3, var5, var6, var7))
                    {
                        return var1;
                    }

                    int var9;

                    if (this.isFull == 0)
                    {
                        if (!var3.canPlayerEdit(var5, var6, var7))
                        {
                            return var1;
                        }

                        if (var2.getBlockMaterial(var5, var6, var7) == Material.water)
                        {
                            if (--var1.stackSize <= 0)
                            {
                                return new ItemStack(Item.bucketWater);
                            }

                            if (!var3.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater)))
                            {
                                var3.dropPlayerItem(new ItemStack(Item.bucketWater.shiftedIndex, 1, 0));
                            }

                            return var1;
                        }

                        if (var2.getBlockMaterial(var5, var6, var7) == Material.lava)
                        {
                            var3.dealFireDamage(1);
                            var2.playSoundEffect((double)var5 + 0.5D, (double)var6 + 0.5D, (double)var7 + 0.5D, "random.fizz", 0.5F, 2.6F + (var2.rand.nextFloat() - var2.rand.nextFloat()) * 0.8F);

                            if (var2.isRemote)
                            {
                                for (var9 = 0; var9 < 8; ++var9)
                                {
                                    var2.spawnParticle("largesmoke", (double)var5 + Math.random(), (double)var6 + Math.random(), (double)var7 + Math.random(), 0.0D, 0.0D, 0.0D);
                                }
                            }

                            return var1;
                        }
                    }
                    else
                    {
                        if (this.isFull < 0)
                        {
                            return new ItemStack(Item.bucketEmpty);
                        }

                        if (var4.sideHit == 0)
                        {
                            --var6;
                        }

                        if (var4.sideHit == 1)
                        {
                            ++var6;
                        }

                        if (var4.sideHit == 2)
                        {
                            --var7;
                        }

                        if (var4.sideHit == 3)
                        {
                            ++var7;
                        }

                        if (var4.sideHit == 4)
                        {
                            --var5;
                        }

                        if (var4.sideHit == 5)
                        {
                            ++var5;
                        }

                        if (!var3.canPlayerEdit(var5, var6, var7))
                        {
                            return var1;
                        }

                        if (var2.isAirBlock(var5, var6, var7) || !var2.getBlockMaterial(var5, var6, var7).isSolid())
                        {
                            if (var2.provider.isHellWorld && this.isFull == Block.waterMoving.blockID)
                            {
                                var2.playSoundEffect((double)var5 + 0.5D, (double)var6 + 0.5D, (double)var7 + 0.5D, "random.fizz", 0.5F, 2.6F + (var2.rand.nextFloat() - var2.rand.nextFloat()) * 0.8F);

                                if (var2.isRemote)
                                {
                                    for (var9 = 0; var9 < 8; ++var9)
                                    {
                                        var2.spawnParticle("largesmoke", (double)var5 + Math.random(), (double)var6 + Math.random(), (double)var7 + Math.random(), 0.0D, 0.0D, 0.0D);
                                    }
                                }
                            }
                            else
                            {
                                var9 = var2.getBlockId(var5, var6, var7);

                                if (this.isFull == Block.waterMoving.blockID)
                                {
                                    if (var9 != Block.waterMoving.blockID && var9 != Block.waterStill.blockID || var2.getBlockMetadata(var5, var6, var7) != 0)
                                    {
                                        if (var9 != Block.lavaMoving.blockID && var9 != Block.lavaStill.blockID)
                                        {
                                            if (!var2.isRemote)
                                            {
                                                FCUtilsMisc.PlaceNonPersistantWater(var2, var5, var6, var7);
                                            }
                                        }
                                        else
                                        {
                                            var2.playSoundEffect((double)var5 + 0.5D, (double)var6 + 0.5D, (double)var7 + 0.5D, "random.fizz", 0.5F, 2.6F + (var2.rand.nextFloat() - var2.rand.nextFloat()) * 0.8F);

                                            if (!var2.isRemote)
                                            {
                                                int var10 = var2.getBlockMetadata(var5, var6, var7);

                                                if (var10 == 0)
                                                {
                                                    var2.setBlockWithNotify(var5, var6, var7, Block.obsidian.blockID);
                                                }
                                                else
                                                {
                                                    var2.setBlockWithNotify(var5, var6, var7, Block.cobblestone.blockID);
                                                }
                                            }
                                        }
                                    }
                                }
                                else if (this.isFull == Block.lavaMoving.blockID)
                                {
                                    if (var9 != Block.lavaMoving.blockID && var9 != Block.lavaStill.blockID || var2.getBlockMetadata(var5, var6, var7) != 0)
                                    {
                                        if (var9 != Block.waterMoving.blockID && var9 != Block.waterStill.blockID)
                                        {
                                            if (!var2.isRemote)
                                            {
                                                var2.setBlockAndMetadataWithNotify(var5, var6, var7, this.isFull, 1);
                                            }
                                        }
                                        else
                                        {
                                            var2.playSoundEffect((double)var5 + 0.5D, (double)var6 + 0.5D, (double)var7 + 0.5D, "random.fizz", 0.5F, 2.6F + (var2.rand.nextFloat() - var2.rand.nextFloat()) * 0.8F);

                                            if (!var2.isRemote)
                                            {
                                                var2.setBlockWithNotify(var5, var6, var7, Block.cobblestone.blockID);
                                            }
                                        }
                                    }
                                }
                                else if (!var2.isRemote)
                                {
                                    var2.setBlockAndMetadataWithNotify(var5, var6, var7, this.isFull, 1);
                                }
                            }

                            if (var3.capabilities.isCreativeMode)
                            {
                                return var1;
                            }

                            return new ItemStack(Item.bucketEmpty);
                        }
                    }
                }
                else if (this.isFull == 0 && var4.entityHit instanceof EntityCow)
                {
                    if (--var1.stackSize <= 0)
                    {
                        return new ItemStack(Item.bucketMilk);
                    }

                    if (!var3.inventory.addItemStackToInventory(new ItemStack(Item.bucketMilk)))
                    {
                        var3.dropPlayerItem(new ItemStack(Item.bucketMilk));
                    }

                    return var1;
                }

                return var1;
            }
        }
    }
}
