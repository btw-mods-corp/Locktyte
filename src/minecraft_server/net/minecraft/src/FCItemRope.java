package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemRope extends Item implements ITextureProvider
{
    public FCItemRope(int var1)
    {
        super(var1);
        this.setIconIndex(9);
        this.setItemName("fcRopeItem");
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (var1.stackSize == 0)
        {
            return false;
        }
        else
        {
            int var11 = var3.getBlockId(var4, var5, var6);

            if (var11 == mod_FCBetterThanWolves.fcAnchor.blockID || var11 == mod_FCBetterThanWolves.fcRopeBlock.blockID)
            {
                for (int var12 = var5 - 1; var12 >= 0; --var12)
                {
                    int var13 = var3.getBlockId(var4, var12, var6);

                    if (FCUtilsWorld.IsReplaceableBlock(var3, var4, var12, var6))
                    {
                        if (var3.setBlockWithNotify(var4, var12, var6, mod_FCBetterThanWolves.fcRopeBlock.blockID))
                        {
                            mod_FCBetterThanWolves.fcRopeBlock.updateBlockMetadata(var3, var4, var12, var6, var7, 0.0F, 0.0F, 0.0F);
                            mod_FCBetterThanWolves.fcRopeBlock.onBlockPlacedBy(var3, var4, var12, var6, var2);
                            var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), mod_FCBetterThanWolves.fcRopeBlock.stepSound.getStepSound(), (mod_FCBetterThanWolves.fcRopeBlock.stepSound.getVolume() + 1.0F) / 2.0F, mod_FCBetterThanWolves.fcRopeBlock.stepSound.getPitch() * 0.8F);
                            --var1.stackSize;
                            return true;
                        }

                        return false;
                    }

                    if (var13 != mod_FCBetterThanWolves.fcRopeBlock.blockID)
                    {
                        return false;
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
}
