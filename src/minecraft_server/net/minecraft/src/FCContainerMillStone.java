package net.minecraft.src;

import java.util.Iterator;

public class FCContainerMillStone extends Container
{
    private final int iNumMillStoneSlotRows = 1;
    private final int iNumMillStoneSlotColumns = 3;
    private final int iNumMillStoneSlots = 3;
    private FCTileEntityMillStone localTileEntityMillStone;
    private int m_iLastMillStoneGrindCounter;

    public FCContainerMillStone(IInventory var1, FCTileEntityMillStone var2)
    {
        this.localTileEntityMillStone = var2;
        this.m_iLastMillStoneGrindCounter = 0;
        int var3;
        int var4;

        for (var3 = 0; var3 < 1; ++var3)
        {
            for (var4 = 0; var4 < 3; ++var4)
            {
                this.addSlotToContainer(new Slot(var2, var4 + var3 * 3, 62 + var4 * 18, 43 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 76 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(var1, var3, 8 + var3 * 18, 134));
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.localTileEntityMillStone.isUseableByPlayer(var1);
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

            if (var1 < 3)
            {
                if (!this.mergeItemStack(var4, 3, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, 3, false))
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
        var1.updateCraftingInventoryInfo(this, 0, this.localTileEntityMillStone.m_iGrindCounter);
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

            if (this.m_iLastMillStoneGrindCounter != this.localTileEntityMillStone.m_iGrindCounter)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.localTileEntityMillStone.m_iGrindCounter);
            }
        }

        this.m_iLastMillStoneGrindCounter = this.localTileEntityMillStone.m_iGrindCounter;
    }
}
