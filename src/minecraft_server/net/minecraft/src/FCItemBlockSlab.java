package net.minecraft.src;

public abstract class FCItemBlockSlab extends ItemBlock
{
    public FCItemBlockSlab(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
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
        else if (!var2.canPlayerEdit(var4, var5, var6))
        {
            return false;
        }
        else if (this.attemptToCombineWithBlock(var1, var2, var3, var4, var5, var6, var7, true))
        {
            return true;
        }
        else
        {
            FCUtilsBlockPos var11 = new FCUtilsBlockPos(var4, var5, var6);
            var11.AddFacingAsOffset(var7);
            return this.attemptToCombineWithBlock(var1, var2, var3, var11.i, var11.j, var11.k, var7, false) ? true : super.onItemUse(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
        }
    }

    public abstract boolean canCombineWithBlock(World var1, int var2, int var3, int var4, int var5);

    public abstract boolean convertToFullBlock(World var1, int var2, int var3, int var4);

    public boolean attemptToCombineWithBlock(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, boolean var8)
    {
        if (this.canCombineWithBlock(var3, var4, var5, var6, var1.getItemDamage()))
        {
            int var9 = var3.getBlockId(var4, var5, var6);
            Block var10 = Block.blocksList[var9];

            if (var10 != null && var10 instanceof FCBlockSlab)
            {
                FCBlockSlab var11 = (FCBlockSlab)var10;
                boolean var12 = var11.GetIsUpsideDown(var3, var4, var5, var6);

                if ((!var8 || var7 == 1 && !var12 || var7 == 0 && var12) && var3.checkIfAABBIsClear(var11.getCollisionBoundingBoxFromPool(var3, var4, var5, var6)) && this.convertToFullBlock(var3, var4, var5, var6))
                {
                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var11.stepSound.getStepSound(), (var11.stepSound.getVolume() + 1.0F) / 2.0F, var11.stepSound.getPitch() * 0.8F);
                    --var1.stackSize;
                    return true;
                }
            }
        }

        return false;
    }
}
