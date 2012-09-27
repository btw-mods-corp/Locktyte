package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemGrate extends Item implements ITextureProvider
{
    public FCItemGrate(int var1)
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
            FCUtilsBlockPos var11 = new FCUtilsBlockPos(var4, var5, var6);
            var11.AddFacingAsOffset(var7);
            int var12 = mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID;
            byte var13 = 6;

            if (var3.canPlaceEntityOnSide(var12, var11.i, var11.j, var11.k, false, var7, var2) && var3.setBlockAndMetadataWithNotify(var11.i, var11.j, var11.k, var12, var13))
            {
                Block var14 = Block.blocksList[var12];

                if (var3.getBlockId(var11.i, var11.j, var11.k) == var12)
                {
                    Block.blocksList[var12].updateBlockMetadata(var3, var11.i, var11.j, var11.k, var7, 0.0F, 0.0F, 0.0F);
                    Block.blocksList[var12].onBlockPlacedBy(var3, var11.i, var11.j, var11.k, var2);
                }

                var3.playSoundEffect((double)((float)var11.i + 0.5F), (double)((float)var11.j + 0.5F), (double)((float)var11.k + 0.5F), var14.stepSound.getStepSound(), (var14.stepSound.getVolume() + 1.0F) / 2.0F, var14.stepSound.getPitch() * 0.8F);
                --var1.stackSize;
            }

            return true;
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
