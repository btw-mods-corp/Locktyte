package net.minecraft.src;

import java.util.Random;

public class FCUtilsInventory
{
    public static void ClearInventoryContents(IInventory var0)
    {
        for (int var1 = 0; var1 < var0.getSizeInventory(); ++var1)
        {
            ItemStack var2 = var0.getStackInSlot(var1);

            if (var2 != null)
            {
                var0.setInventorySlotContents(var1, (ItemStack)null);
            }
        }
    }

    public static void EjectInventoryContents(World var0, int var1, int var2, int var3, IInventory var4)
    {
        for (int var5 = 0; var5 < var4.getSizeInventory(); ++var5)
        {
            ItemStack var6 = var4.getStackInSlot(var5);

            if (var6 != null)
            {
                float var7 = var0.rand.nextFloat() * 0.7F + 0.15F;
                float var8 = var0.rand.nextFloat() * 0.7F + 0.15F;
                float var9 = var0.rand.nextFloat() * 0.7F + 0.15F;

                while (var6.stackSize > 0)
                {
                    int var10 = var0.rand.nextInt(21) + 10;

                    if (var10 > var6.stackSize)
                    {
                        var10 = var6.stackSize;
                    }

                    var6.stackSize -= var10;
                    EntityItem var11 = new EntityItem(var0, (double)((float)var1 + var7), (double)((float)var2 + var8), (double)((float)var3 + var9), new ItemStack(var6.itemID, var10, var6.getItemDamage()));
                    float var12 = 0.05F;
                    var11.motionX = (double)((float)var0.rand.nextGaussian() * var12);
                    var11.motionY = (double)((float)var0.rand.nextGaussian() * var12 + 0.2F);
                    var11.motionZ = (double)((float)var0.rand.nextGaussian() * var12);
                    CopyEnchantments(var11.item, var6);
                    var0.spawnEntityInWorld(var11);
                }
            }
        }
    }

    public static void CopyEnchantments(ItemStack var0, ItemStack var1)
    {
        if (var1.hasTagCompound())
        {
            var0.setTagCompound((NBTTagCompound)var1.getTagCompound().copy());
        }
    }

    public static ItemStack DecrStackSize(IInventory var0, int var1, int var2)
    {
        if (var0.getStackInSlot(var1) != null)
        {
            ItemStack var3;

            if (var0.getStackInSlot(var1).stackSize <= var2)
            {
                var3 = var0.getStackInSlot(var1);
                var0.setInventorySlotContents(var1, (ItemStack)null);
                return var3;
            }
            else
            {
                var3 = var0.getStackInSlot(var1).splitStack(var2);

                if (var0.getStackInSlot(var1).stackSize == 0)
                {
                    var0.setInventorySlotContents(var1, (ItemStack)null);
                }
                else
                {
                    var0.onInventoryChanged();
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    public static int GetNumOccupiedStacks(IInventory var0)
    {
        int var1 = 0;

        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2)
        {
            if (var0.getStackInSlot(var2) != null)
            {
                ++var1;
            }
        }

        return var1;
    }

    public static int GetNumOccupiedStacksInRange(IInventory var0, int var1, int var2)
    {
        int var3 = 0;

        for (int var4 = var1; var4 < var2; ++var4)
        {
            if (var0.getStackInSlot(var4) != null)
            {
                ++var3;
            }
        }

        return var3;
    }

    public static int GetRandomOccupiedStackInRange(IInventory var0, Random var1, int var2, int var3)
    {
        int var4 = GetNumOccupiedStacksInRange(var0, var2, var3);

        if (var4 > 0)
        {
            int var5 = var1.nextInt(var4) + 1;
            int var6 = 0;

            for (int var7 = var2; var7 < var3; ++var7)
            {
                if (var0.getStackInSlot(var7) != null)
                {
                    ++var6;

                    if (var6 >= var5)
                    {
                        return var7;
                    }
                }
            }
        }

        return -1;
    }

    public static int GetFirstOccupiedStack(IInventory var0)
    {
        for (int var1 = 0; var1 < var0.getSizeInventory(); ++var1)
        {
            if (var0.getStackInSlot(var1) != null)
            {
                return var1;
            }
        }

        return -1;
    }

    public static int GetFirstOccupiedStackNotOfItem(IInventory var0, int var1)
    {
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2)
        {
            if (var0.getStackInSlot(var2) != null && var0.getStackInSlot(var2).getItem().shiftedIndex != var1)
            {
                return var2;
            }
        }

        return -1;
    }

    public static int GetFirstOccupiedStackOfItem(IInventory var0, int var1)
    {
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2)
        {
            if (var0.getStackInSlot(var2) != null && var0.getStackInSlot(var2).getItem().shiftedIndex == var1)
            {
                return var2;
            }
        }

        return -1;
    }

    public static int GetFirstOccupiedStackOfItemInSlotRange(IInventory var0, int var1, int var2, int var3, int var4)
    {
        for (int var5 = var3; var5 < var4; ++var5)
        {
            if (var0.getStackInSlot(var5) != null)
            {
                ItemStack var6 = var0.getStackInSlot(var5);

                if (var6.getItem().shiftedIndex == var1 && (var2 < 0 || var6.getItemDamage() == var2))
                {
                    return var5;
                }
            }
        }

        return -1;
    }

    public static int CountItemsInInventory(IInventory var0, int var1, int var2)
    {
        return CountItemsInInventory(var0, var1, var2, false);
    }

    public static int CountItemsInInventory(IInventory var0, int var1, int var2, boolean var3)
    {
        int var4 = 0;

        for (int var5 = 0; var5 < var0.getSizeInventory(); ++var5)
        {
            ItemStack var6 = var0.getStackInSlot(var5);

            if (var6 != null && var6.getItem().shiftedIndex == var1 && (var2 == -1 || !var3 && var6.getItemDamage() == var2 || var3 && var6.getItemDamage() != var2))
            {
                var4 += var0.getStackInSlot(var5).stackSize;
            }
        }

        return var4;
    }

    public static boolean ConsumeItemsInInventory(IInventory var0, int var1, int var2, int var3)
    {
        return ConsumeItemsInInventory(var0, var1, var2, var3, false);
    }

    public static boolean ConsumeItemsInInventory(IInventory var0, int var1, int var2, int var3, boolean var4)
    {
        for (int var5 = 0; var5 < var0.getSizeInventory(); ++var5)
        {
            ItemStack var6 = var0.getStackInSlot(var5);

            if (var6 != null)
            {
                Item var7 = var6.getItem();

                if (var7.shiftedIndex == var1 && (var2 == -1 || !var4 && var6.getItemDamage() == var2 || var4 && var6.getItemDamage() != var2))
                {
                    if (var6.stackSize >= var3)
                    {
                        DecrStackSize(var0, var5, var3);
                        return true;
                    }

                    var3 -= var6.stackSize;
                    var0.setInventorySlotContents(var5, (ItemStack)null);
                }
            }
        }

        return false;
    }

    public static boolean AddSingleItemToInventory(IInventory var0, int var1, int var2)
    {
        ItemStack var3 = new ItemStack(var1, 1, var2);
        return AddItemStackToInventory(var0, var3);
    }

    public static boolean AddItemStackToInventory(IInventory var0, ItemStack var1)
    {
        if (!var1.isItemDamaged())
        {
            var1.stackSize = StorePartialItemStack(var0, var1);

            if (var1.stackSize == 0)
            {
                return true;
            }
        }

        int var2 = GetFirstEmptyStack(var0);

        if (var2 >= 0)
        {
            var0.setInventorySlotContents(var2, var1);
            return true;
        }
        else
        {
            return false;
        }
    }

    private static int GetFirstEmptyStack(IInventory var0)
    {
        for (int var1 = 0; var1 < var0.getSizeInventory(); ++var1)
        {
            if (var0.getStackInSlot(var1) == null)
            {
                return var1;
            }
        }

        return -1;
    }

    private static int StorePartialItemStack(IInventory var0, ItemStack var1)
    {
        int var2 = var1.itemID;
        int var3 = var1.stackSize;
        int var4 = FindSlotToStoreItemStack(var0, var1);

        if (var4 < 0)
        {
            var4 = GetFirstEmptyStack(var0);
        }

        if (var4 < 0)
        {
            return var3;
        }
        else
        {
            if (var0.getStackInSlot(var4) == null)
            {
                ItemStack var5 = new ItemStack(var2, 0, var1.getItemDamage());
                CopyEnchantments(var5, var1);
                var0.setInventorySlotContents(var4, var5);
            }

            int var7 = var3;
            ItemStack var6 = var0.getStackInSlot(var4);

            if (var3 > var6.getMaxStackSize() - var6.stackSize)
            {
                var7 = var6.getMaxStackSize() - var6.stackSize;
            }

            if (var7 > var0.getInventoryStackLimit() - var6.stackSize)
            {
                var7 = var0.getInventoryStackLimit() - var6.stackSize;
            }

            if (var7 == 0)
            {
                return var3;
            }
            else
            {
                var3 -= var7;
                var6.stackSize += var7;
                var0.setInventorySlotContents(var4, var6);
                return var3;
            }
        }
    }

    private static int FindSlotToStoreItemStack(IInventory var0, ItemStack var1)
    {
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2)
        {
            ItemStack var3 = var0.getStackInSlot(var2);

            if (var3 != null && var3.itemID == var1.itemID && var3.isStackable() && var3.stackSize < var3.getMaxStackSize() && var3.stackSize < var0.getInventoryStackLimit() && (!var3.getHasSubtypes() || var3.getItemDamage() == var1.getItemDamage()))
            {
                return var2;
            }
        }

        return -1;
    }

    public static int GetNumOccupiedStacksInSlotRange(IInventory var0, int var1, int var2)
    {
        int var3 = 0;

        for (int var4 = var1; var4 < var0.getSizeInventory() && var4 <= var2; ++var4)
        {
            if (var0.getStackInSlot(var4) != null)
            {
                ++var3;
            }
        }

        return var3;
    }

    public static boolean AddItemStackToInventoryInSlotRange(IInventory var0, ItemStack var1, int var2, int var3)
    {
        if (!var1.isItemDamaged())
        {
            var1.stackSize = StorePartialItemStackInSlotRange(var0, var1, var2, var3);

            if (var1.stackSize == 0)
            {
                return true;
            }
        }

        int var4 = GetFirstEmptyStackInSlotRange(var0, var2, var3);

        if (var4 >= 0)
        {
            var0.setInventorySlotContents(var4, var1);
            return true;
        }
        else
        {
            return false;
        }
    }

    private static int GetFirstEmptyStackInSlotRange(IInventory var0, int var1, int var2)
    {
        for (int var3 = var1; var3 < var0.getSizeInventory() && var3 <= var2; ++var3)
        {
            if (var0.getStackInSlot(var3) == null)
            {
                return var3;
            }
        }

        return -1;
    }

    private static int StorePartialItemStackInSlotRange(IInventory var0, ItemStack var1, int var2, int var3)
    {
        int var4 = var1.itemID;
        int var5 = var1.stackSize;
        int var6 = FindSlotToStoreItemStackInSlotRange(var0, var1, var2, var3);

        if (var6 < 0)
        {
            var6 = GetFirstEmptyStackInSlotRange(var0, var2, var3);
        }

        if (var6 < 0)
        {
            return var5;
        }
        else
        {
            if (var0.getStackInSlot(var6) == null)
            {
                ItemStack var7 = new ItemStack(var4, 0, var1.getItemDamage());
                CopyEnchantments(var7, var1);
                var0.setInventorySlotContents(var6, var7);
            }

            int var9 = var5;
            ItemStack var8 = var0.getStackInSlot(var6);

            if (var5 > var8.getMaxStackSize() - var8.stackSize)
            {
                var9 = var8.getMaxStackSize() - var8.stackSize;
            }

            if (var9 > var0.getInventoryStackLimit() - var8.stackSize)
            {
                var9 = var0.getInventoryStackLimit() - var8.stackSize;
            }

            if (var9 == 0)
            {
                return var5;
            }
            else
            {
                var5 -= var9;
                var8.stackSize += var9;
                var0.setInventorySlotContents(var6, var8);
                return var5;
            }
        }
    }

    private static int FindSlotToStoreItemStackInSlotRange(IInventory var0, ItemStack var1, int var2, int var3)
    {
        for (int var4 = var2; var4 < var0.getSizeInventory() && var4 <= var3; ++var4)
        {
            ItemStack var5 = var0.getStackInSlot(var4);

            if (var5 != null && var5.itemID == var1.itemID && var5.isStackable() && var5.stackSize < var5.getMaxStackSize() && var5.stackSize < var0.getInventoryStackLimit() && (!var5.getHasSubtypes() || var5.getItemDamage() == var1.getItemDamage()))
            {
                return var4;
            }
        }

        return -1;
    }
}
