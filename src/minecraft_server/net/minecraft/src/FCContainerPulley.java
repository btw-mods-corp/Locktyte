package net.minecraft.src;

import java.util.Iterator;

public class FCContainerPulley extends Container
{
    private final int m_iNumSlotRows = 2;
    private final int m_iNumSlotColumns = 2;
    private final int m_iNumSlots = 4;
    private FCTileEntityPulley m_localTileEntity;
    private int m_iLastMechanicalPowerIndicator;

    public FCContainerPulley(IInventory var1, FCTileEntityPulley var2)
    {
        this.m_localTileEntity = var2;
        this.m_iLastMechanicalPowerIndicator = 0;
        int var3;
        int var4;

        for (var3 = 0; var3 < 2; ++var3)
        {
            for (var4 = 0; var4 < 2; ++var4)
            {
                this.addSlotToContainer(new Slot(var2, var4 + var3 * 2, 71 + var4 * 18, 43 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 93 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(var1, var3, 8 + var3 * 18, 151));
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.m_localTileEntity.isUseableByPlayer(var1);
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

            if (var1 < 4)
            {
                if (!this.mergeItemStack(var4, 4, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, 4, false))
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
        var1.updateCraftingInventoryInfo(this, 0, this.m_localTileEntity.m_iMechanicalPowerIndicator);
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

            if (this.m_iLastMechanicalPowerIndicator != this.m_localTileEntity.m_iMechanicalPowerIndicator)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.m_localTileEntity.m_iMechanicalPowerIndicator);
            }
        }

        this.m_iLastMechanicalPowerIndicator = this.m_localTileEntity.m_iMechanicalPowerIndicator;
    }
}
