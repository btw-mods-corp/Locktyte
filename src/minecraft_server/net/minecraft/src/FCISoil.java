package net.minecraft.src;

public interface FCISoil
{
    boolean CanPlantGrowOnBlock(World var1, int var2, int var3, int var4, Block var5);

    boolean IsPlantGrowthMaximizedOnBlock(World var1, int var2, int var3, int var4, Block var5);

    boolean IsBlockHydrated(World var1, int var2, int var3, int var4);

    boolean IsBlockConsideredNeighbouringWater(World var1, int var2, int var3, int var4);

    float GetGrowthMultiplier(World var1, int var2, int var3, int var4, Block var5);

    void NotifyOfPlantGrowth(World var1, int var2, int var3, int var4, Block var5);
}
