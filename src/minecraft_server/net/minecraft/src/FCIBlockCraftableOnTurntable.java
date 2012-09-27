package net.minecraft.src;

public interface FCIBlockCraftableOnTurntable
{
    int GetRotationsToCraft(IBlockAccess var1, int var2, int var3, int var4);

    void OnRotated(World var1, int var2, int var3, int var4);

    int GetBlockIDOnCraft(IBlockAccess var1, int var2, int var3, int var4);

    int GetBlockMetadataOnCraft(IBlockAccess var1, int var2, int var3, int var4);

    int GetItemIDDroppedOnCraft(IBlockAccess var1, int var2, int var3, int var4);

    int GetItemMetadataDroppedOnCraft(IBlockAccess var1, int var2, int var3, int var4);

    int GetNumItemsDroppedOnCraft(IBlockAccess var1, int var2, int var3, int var4);

    void OnCraft(World var1, int var2, int var3, int var4);
}
