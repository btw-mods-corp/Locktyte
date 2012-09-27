package net.minecraft.src;

public class FCUtilsItem
{
    public static void EjectStackWithRandomOffset(World var0, int var1, int var2, int var3, ItemStack var4)
    {
        float var5 = var0.rand.nextFloat() * 0.7F + 0.15F;
        float var6 = var0.rand.nextFloat() * 0.2F + 0.1F;
        float var7 = var0.rand.nextFloat() * 0.7F + 0.15F;
        EjectStackWithRandomVelocity(var0, (float)var1 + var5, (float)var2 + var6, (float)var3 + var7, var4);
    }

    public static void EjectSingleItemWithRandomOffset(World var0, int var1, int var2, int var3, int var4, int var5)
    {
        ItemStack var6 = new ItemStack(var4, 1, var5);
        EjectStackWithRandomOffset(var0, var1, var2, var3, var6);
    }

    public static void EjectStackWithRandomVelocity(World var0, float var1, float var2, float var3, ItemStack var4)
    {
        EntityItem var5 = new EntityItem(var0, (double)var1, (double)var2, (double)var3, var4);
        float var6 = 0.05F;
        var5.motionX = (double)((float)var0.rand.nextGaussian() * var6);
        var5.motionY = (double)((float)var0.rand.nextGaussian() * var6 + 0.2F);
        var5.motionZ = (double)((float)var0.rand.nextGaussian() * var6);
        var5.delayBeforeCanPickup = 10;
        var0.spawnEntityInWorld(var5);
    }

    public static void EjectSingleItemWithRandomVelocity(World var0, float var1, float var2, float var3, int var4, int var5)
    {
        ItemStack var6 = new ItemStack(var4, 1, var5);
        EjectStackWithRandomVelocity(var0, var1, var2, var3, var6);
    }

    public static void DropStackAsIfBlockHarvested(World var0, int var1, int var2, int var3, ItemStack var4)
    {
        float var5 = 0.7F;
        double var6 = (double)(var0.rand.nextFloat() * var5) + (double)(1.0F - var5) * 0.5D;
        double var8 = (double)(var0.rand.nextFloat() * var5) + (double)(1.0F - var5) * 0.5D;
        double var10 = (double)(var0.rand.nextFloat() * var5) + (double)(1.0F - var5) * 0.5D;
        EntityItem var12 = new EntityItem(var0, (double)var1 + var6, (double)var2 + var8, (double)var3 + var10, var4);
        var12.delayBeforeCanPickup = 10;
        var0.spawnEntityInWorld(var12);
    }

    public static void DropSingleItemAsIfBlockHarvested(World var0, int var1, int var2, int var3, int var4, int var5)
    {
        ItemStack var6 = new ItemStack(var4, 1, var5);
        DropStackAsIfBlockHarvested(var0, var1, var2, var3, var6);
    }
}
