package net.minecraft.src;

public class FCItemWoolSlab extends FCItemBlockSlab
{
    public FCItemWoolSlab(int var1)
    {
        super(var1);
        this.setHasSubtypes(true);
        this.setItemName("fcWoolSlab");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    public boolean canCombineWithBlock(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockId(var2, var3, var4);
        int var7 = var1.getBlockMetadata(var2, var3, var4);
        return (var6 == mod_FCBetterThanWolves.fcWoolSlab.blockID || var6 == mod_FCBetterThanWolves.fcBlockWoolSlabTop.blockID) && var7 == var5;
    }

    public boolean convertToFullBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);

        if (var5 != mod_FCBetterThanWolves.fcWoolSlab.blockID && var5 != mod_FCBetterThanWolves.fcBlockWoolSlabTop.blockID)
        {
            return false;
        }
        else
        {
            FCBlockSlab var6 = (FCBlockSlab)((FCBlockSlab)Block.blocksList[var5]);
            var6.GetIsUpsideDown(var1, var2, var3, var4);
            int var8 = Block.cloth.blockID;
            int var9 = var1.getBlockMetadata(var2, var3, var4);
            return var1.setBlockAndMetadataWithNotify(var2, var3, var4, var8, var9);
        }
    }
}
