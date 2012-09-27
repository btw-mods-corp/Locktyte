package net.minecraft.src;

import java.util.Iterator;

public class FCContainerBlockDispenser extends Container
{
    private FCTileEntityBlockDispenser m_LocalTileEntity;
    private final int m_iNumSlots = 16;
    private int m_iLastNextSlotIndexToDispense;

    public FCContainerBlockDispenser(IInventory var1, FCTileEntityBlockDispenser var2)
    {
        this.m_LocalTileEntity = var2;
        this.m_LocalTileEntity.openChest();
        int var3;
        int var4;

        for (var3 = 0; var3 < 4; ++var3)
        {
            for (var4 = 0; var4 < 4; ++var4)
            {
                this.addSlotToContainer(new Slot(var2, var4 + var3 * 4, 53 + var4 * 18, 17 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 102 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(var1, var3, 8 + var3 * 18, 160));
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.m_LocalTileEntity.isUseableByPlayer(var1);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int var1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(var1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (var1 < 16)
            {
                if (!this.mergeItemStack(var4, 16, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, 16, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

    public ItemStack slotClick(int var1, int var2, boolean var3, EntityPlayer var4)
    {
        if (var1 < 16)
        {
            this.m_LocalTileEntity.iNextSlotIndexToDispense = 0;
        }

        return super.slotClick(var1, var2, var3, var4);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer var1)
    {
        super.onCraftGuiClosed(var1);
        this.m_LocalTileEntity.closeChest();
    }

    public void onCraftGuiOpened(ICrafting var1)
    {
        super.onCraftGuiOpened(var1);
        var1.updateCraftingInventoryInfo(this, 0, this.m_LocalTileEntity.iNextSlotIndexToDispense);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.m_iLastNextSlotIndexToDispense != this.m_LocalTileEntity.iNextSlotIndexToDispense)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.m_LocalTileEntity.iNextSlotIndexToDispense);
            }
        }

        this.m_iLastNextSlotIndexToDispense = this.m_LocalTileEntity.iNextSlotIndexToDispense;
    }
}
