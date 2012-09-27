package net.minecraft.src;

public class FCItemBlockAestheticNonOpaque extends ItemBlock
{
    public FCItemBlockAestheticNonOpaque(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("fcAestheticNonOpaque");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    public String getItemNameIS(ItemStack var1)
    {
        switch (var1.getItemDamage())
        {
            case 0:
                return super.getItemName() + "." + "urn";

            case 1:
                return super.getItemName() + "." + "column";

            case 2:
            case 3:
                return super.getItemName() + "." + "pedestal";

            case 4:
                return super.getItemName() + "." + "table";

            case 5:
                return super.getItemName() + "." + "wickerslab";

            default:
                return super.getItemName();
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (var1.getItemDamage() != 5)
        {
            return super.onItemUse(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
        }
        else if (var1.stackSize == 0)
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

    public float GetBuoyancy(int var1)
    {
        switch (var1)
        {
            case 0:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return 1.0F;

            case 1:
            case 2:
            case 3:
            default:
                return super.GetBuoyancy(var1);
        }
    }

    public boolean canCombineWithBlock(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockId(var2, var3, var4);

        if (var5 == 5 && var6 == mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID)
        {
            int var7 = var1.getBlockMetadata(var2, var3, var4);

            if (var7 == 5 || var7 == 9)
            {
                return true;
            }
        }

        return false;
    }

    public boolean convertToFullBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);

        if (var5 == mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID)
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var6 == 5 || var6 == 9)
            {
                int var7 = mod_FCBetterThanWolves.fcAestheticOpaque.blockID;
                byte var8 = 0;
                return var1.setBlockAndMetadataWithNotify(var2, var3, var4, var7, var8);
            }
        }

        return false;
    }

    public boolean IsSlabUpsideDown(int var1, int var2)
    {
        return var1 == mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID && var2 == 9;
    }

    public boolean attemptToCombineWithBlock(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, boolean var8)
    {
        if (this.canCombineWithBlock(var3, var4, var5, var6, var1.getItemDamage()))
        {
            int var9 = var3.getBlockId(var4, var5, var6);
            Block var10 = Block.blocksList[var9];

            if (var10 != null)
            {
                int var11 = var3.getBlockMetadata(var4, var5, var6);
                boolean var12 = this.IsSlabUpsideDown(var9, var11);

                if ((!var8 || var7 == 1 && !var12 || var7 == 0 && var12) && var3.checkIfAABBIsClear(var10.getCollisionBoundingBoxFromPool(var3, var4, var5, var6)) && this.convertToFullBlock(var3, var4, var5, var6))
                {
                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var10.stepSound.getStepSound(), (var10.stepSound.getVolume() + 1.0F) / 2.0F, var10.stepSound.getPitch() * 0.8F);
                    --var1.stackSize;
                    return true;
                }
            }
        }

        return false;
    }
}
