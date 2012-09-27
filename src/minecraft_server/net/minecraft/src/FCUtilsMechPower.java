package net.minecraft.src;

public class FCUtilsMechPower
{
    static boolean IsBlockPoweredByAxleToSide(World var0, int var1, int var2, int var3, int var4)
    {
        FCUtilsBlockPos var5 = new FCUtilsBlockPos(var1, var2, var3);
        var5.AddFacingAsOffset(var4);
        int var6 = var0.getBlockId(var5.i, var5.j, var5.k);

        if (var6 == mod_FCBetterThanWolves.fcAxleBlock.blockID)
        {
            FCBlockAxle var7 = (FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock;

            if (var7.IsAxleOrientedTowardsFacing(var0, var5.i, var5.j, var5.k, var4) && var7.GetPowerLevel(var0, var5.i, var5.j, var5.k) > 0)
            {
                return true;
            }
        }

        return false;
    }

    public static boolean DoesBlockHaveFacingAxleToSide(World var0, int var1, int var2, int var3, int var4)
    {
        FCUtilsBlockPos var5 = new FCUtilsBlockPos(var1, var2, var3);
        var5.AddFacingAsOffset(var4);
        int var6 = var0.getBlockId(var5.i, var5.j, var5.k);

        if (var6 == mod_FCBetterThanWolves.fcAxleBlock.blockID)
        {
            FCBlockAxle var7 = (FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock;

            if (var7.IsAxleOrientedTowardsFacing(var0, var5.i, var5.j, var5.k, var4))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean IsBlockPoweredByHandCrank(World var0, int var1, int var2, int var3)
    {
        for (int var4 = 1; var4 <= 5; ++var4)
        {
            if (IsBlockPoweredByHandCrankToSide(var0, var1, var2, var3, var4))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean IsBlockPoweredByHandCrankToSide(World var0, int var1, int var2, int var3, int var4)
    {
        FCUtilsBlockPos var5 = new FCUtilsBlockPos(var1, var2, var3);
        var5.AddFacingAsOffset(var4);
        int var6 = var0.getBlockId(var5.i, var5.j, var5.k);

        if (var6 == mod_FCBetterThanWolves.fcHandCrank.blockID)
        {
            Block var7 = Block.blocksList[var6];
            FCIMechanicalDevice var8 = (FCIMechanicalDevice)var7;

            if (var8.IsOutputtingMechanicalPower(var0, var5.i, var5.j, var5.k))
            {
                return true;
            }
        }

        return false;
    }

    public static void DestroyHorizontallyAttachedAxles(World var0, int var1, int var2, int var3)
    {
        for (int var4 = 2; var4 <= 5; ++var4)
        {
            FCUtilsBlockPos var5 = new FCUtilsBlockPos(var1, var2, var3);
            var5.AddFacingAsOffset(var4);

            if (var0.getBlockId(var5.i, var5.j, var5.k) == mod_FCBetterThanWolves.fcAxleBlock.blockID)
            {
                FCBlockAxle var6 = (FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock;

                if (var6.IsAxleOrientedTowardsFacing(var0, var5.i, var5.j, var5.k, var4))
                {
                    var6.BreakAxle(var0, var5.i, var5.j, var5.k);
                }
            }
        }
    }
}
