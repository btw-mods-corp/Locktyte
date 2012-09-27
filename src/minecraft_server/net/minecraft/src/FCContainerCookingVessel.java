package net.minecraft.src;

import java.util.Iterator;

public class FCContainerCookingVessel extends Container
{
    private final int m_iNumSlotRows = 3;
    private final int m_iNumSlotColumns = 9;
    private final int m_iNumSlots = 27;
    private FCTileEntityCookingVessel m_AssociatedTileEntity;
    private int m_iLastCookCounter;

    public FCContainerCookingVessel(IInventory var1, FCTileEntityCookingVessel var2)
    {
        this.m_AssociatedTileEntity = var2;
        this.m_iLastCookCounter = 0;
        int var3;
        int var4;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var2, var4 + var3 * 9, 8 + var4 * 18, 43 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 111 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(var1, var3, 8 + var3 * 18, 169));
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.m_AssociatedTileEntity.isUseableByPlayer(var1);
    }

    public ItemStack slotClick(int var1, int var2, boolean var3, EntityPlayer var4)
    {
        ItemStack var5 = super.slotClick(var1, var2, var3, var4);
        this.m_AssociatedTileEntity.onInventoryChanged();
        return var5;
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

            if (var1 < 27)
            {
                if (!this.mergeItemStack(var4, 27, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, 27, false))
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

    public void onCraftGuiOpened(ICrafting var1)
    {
        super.onCraftGuiOpened(var1);
        var1.updateCraftingInventoryInfo(this, 0, this.m_AssociatedTileEntity.m_iScaledCookCounter);
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

            if (this.m_iLastCookCounter != this.m_AssociatedTileEntity.m_iScaledCookCounter)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.m_AssociatedTileEntity.m_iScaledCookCounter);
            }
        }

        this.m_iLastCookCounter = this.m_AssociatedTileEntity.m_iScaledCookCounter;
    }
}
