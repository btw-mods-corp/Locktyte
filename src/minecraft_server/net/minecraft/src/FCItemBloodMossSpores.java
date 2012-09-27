package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;

public class FCItemBloodMossSpores extends Item implements ITextureProvider
{
    public FCItemBloodMossSpores(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
        this.setIconIndex(70);
        this.setItemName("fcBloodMossSpores");
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
            FCUtilsBlockPos var11 = new FCUtilsBlockPos(var4, var5, var6);
            var11.AddFacingAsOffset(var7);
            WorldChunkManager var12 = var3.getWorldChunkManager();

            if (var12 != null)
            {
                BiomeGenBase var13 = var12.getBiomeGenAt(var4, var6);

                if (var13 instanceof BiomeGenHell)
                {
                    int var14 = mod_FCBetterThanWolves.fcBlockBloodMoss.blockID;
                    byte var15 = 0;

                    if (var3.canPlaceEntityOnSide(var14, var11.i, var11.j, var11.k, false, var7, (Entity)null))
                    {
                        if (!var3.isRemote && var3.setBlockAndMetadataWithNotify(var11.i, var11.j, var11.k, var14, var15))
                        {
                            Block var16 = Block.blocksList[var14];

                            if (var3.getBlockId(var11.i, var11.j, var11.k) == var14)
                            {
                                Block.blocksList[var14].updateBlockMetadata(var3, var11.i, var11.j, var11.k, var7, 0.0F, 0.0F, 0.0F);
                                Block.blocksList[var14].onBlockPlacedBy(var3, var11.i, var11.j, var11.k, var2);
                            }

                            var3.playSoundEffect((double)((float)var11.i + 0.5F), (double)((float)var11.j + 0.5F), (double)((float)var11.k + 0.5F), var16.stepSound.getStepSound(), (var16.stepSound.getVolume() + 1.0F) / 2.0F, var16.stepSound.getPitch() * 0.8F);
                            this.AngerPigmen(var3, var2);
                        }

                        --var1.stackSize;
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }

    private void AngerPigmen(World var1, EntityPlayer var2)
    {
        List var3 = var1.getEntitiesWithinAABB(EntityPigZombie.class, var2.boundingBox.expand(32.0D, 32.0D, 32.0D));

        for (int var4 = 0; var4 < var3.size(); ++var4)
        {
            Entity var5 = (Entity)var3.get(var4);
            var5.attackEntityFrom(DamageSource.causePlayerDamage(var2), 0);
        }
    }
}
