package net.minecraft.src;

public class FCItemDirtSlab extends FCItemBlockSlab
{
    public FCItemDirtSlab(int var1)
    {
        super(var1);
    }

    public boolean canCombineWithBlock(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockId(var2, var3, var4);
        return var6 == mod_FCBetterThanWolves.fcBlockDirtSlab.blockID;
    }

    public boolean convertToFullBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);

        if (var5 == mod_FCBetterThanWolves.fcBlockDirtSlab.blockID)
        {
            FCBlockDirtSlab var6 = (FCBlockDirtSlab)((FCBlockDirtSlab)mod_FCBetterThanWolves.fcBlockDirtSlab);
            boolean var7 = var6.GetIsUpsideDown(var1, var2, var3, var4);
            int var8 = Block.dirt.blockID;

            if (var7 && var6.GetSubtype(var1, var2, var3, var4) == 1)
            {
                var8 = Block.grass.blockID;
            }

            return var1.setBlockWithNotify(var2, var3, var4, var8);
        }
        else
        {
            return false;
        }
    }
}
