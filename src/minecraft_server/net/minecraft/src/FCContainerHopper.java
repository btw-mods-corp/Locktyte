package net.minecraft.src;

import java.util.Iterator;

public class FCContainerHopper extends Container
{
    private final int iNumHopperSlotRows = 2;
    private final int iNumHopperSlotColumns = 9;
    private final int iNumHopperSlots = 18;
    private FCTileEntityHopper localTileEntityHopper;
    private int m_iLastMechanicalPowerIndicator;

    public FCContainerHopper(IInventory var1, FCTileEntityHopper var2)
    {
        this.localTileEntityHopper = var2;
        this.m_iLastMechanicalPowerIndicator = 0;
        int var3;
        int var4;

        for (var3 = 0; var3 < 2; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var2, var4 + var3 * 9, 8 + var4 * 18, 60 + var3 * 18));
            }
        }

        this.addSlotToContainer(new Slot(var2, 18, 80, 37));

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
        return this.localTileEntityHopper.isUseableByPlayer(var1);
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

            if (var1 < 19)
            {
                if (!this.mergeItemStack(var4, 19, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (this.IsItemFilter(var4.itemID))
            {
                if (!this.mergeItemStack(var4, 18, 19, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, 18, false))
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

    public boolean IsItemFilter(int var1)
    {
        return var1 == Block.ladder.blockID || var1 == Block.trapdoor.blockID || var1 == mod_FCBetterThanWolves.fcGrate.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWicker.shiftedIndex || var1 == mod_FCBetterThanWolves.fcRollersItem.shiftedIndex || var1 == Block.slowSand.blockID || var1 == Block.fenceIron.blockID;
    }

    public void onCraftGuiOpened(ICrafting var1)
    {
        super.onCraftGuiOpened(var1);
        var1.updateCraftingInventoryInfo(this, 0, this.localTileEntityHopper.m_iMechanicalPowerIndicator);
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

            if (this.m_iLastMechanicalPowerIndicator != this.localTileEntityHopper.m_iMechanicalPowerIndicator)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.localTileEntityHopper.m_iMechanicalPowerIndicator);
            }
        }

        this.m_iLastMechanicalPowerIndicator = this.localTileEntityHopper.m_iMechanicalPowerIndicator;
    }
}
