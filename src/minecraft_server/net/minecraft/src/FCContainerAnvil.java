package net.minecraft.src;

public class FCContainerAnvil extends Container
{
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 4, 4);
    public IInventory craftResult = new InventoryCraftResult();
    private World m_localWorld;
    private int m_anvilI;
    private int m_anvilJ;
    private int m_anvilK;
    private static final double m_dMaxInteractionDistance = 8.0D;
    private static final double m_dMaxInteractionDistanceSq = 64.0D;
    private static final int m_iCraftingGridWidth = 4;
    private static final int m_iCraftingGridHeight = 4;
    private static final int m_iCraftingGridSize = 16;
    private static final int m_iSlotScreenWidth = 18;
    private static final int m_iSlotScreenHeight = 18;
    private static final int m_iGridScreenPosX = 23;
    private static final int m_iGridScreenPosY = 17;
    private static final int m_iPlayerInventoryScreenPosX = 8;
    private static final int m_iPlayerInventoryScreenPosY = 102;
    private static final int m_iPlayerHotbarScreenPosY = 160;

    public FCContainerAnvil(InventoryPlayer var1, World var2, int var3, int var4, int var5)
    {
        this.m_localWorld = var2;
        this.m_anvilI = var3;
        this.m_anvilJ = var4;
        this.m_anvilK = var5;
        this.addSlotToContainer(new SlotCrafting(var1.player, this.craftMatrix, this.craftResult, 0, 135, 44));
        int var6;
        int var7;

        for (var6 = 0; var6 < 4; ++var6)
        {
            for (var7 = 0; var7 < 4; ++var7)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, var7 + var6 * 4, 23 + var7 * 18, 17 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(var1, var7 + var6 * 9 + 9, 8 + var7 * 18, 102 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(var1, var6, 8 + var6 * 18, 160));
        }

        if (var2 != null && !var2.isRemote)
        {
            FCTileEntityAnvil var11 = (FCTileEntityAnvil)var2.getBlockTileEntity(var3, var4, var5);

            if (var11 != null)
            {
                for (var7 = 0; var7 < 4; ++var7)
                {
                    for (int var8 = 0; var8 < 4; ++var8)
                    {
                        if (var11.DoesSlotContainMould(var8, var7))
                        {
                            ItemStack var9 = new ItemStack(mod_FCBetterThanWolves.fcItemMould);
                            Slot var10 = (Slot)this.inventorySlots.get(var8 + var7 * 4 + 1);
                            var10.putStack(var9);
                        }
                    }
                }

                var11.ClearMouldContents();
            }
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory var1)
    {
        ItemStack var2 = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix);

        if (var2 == null)
        {
            var2 = FCCraftingManagerAnvil.getInstance().findMatchingRecipe(this.craftMatrix);
        }

        this.craftResult.setInventorySlotContents(0, var2);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer var1)
    {
        super.onCraftGuiClosed(var1);

        if (this.m_localWorld != null && !this.m_localWorld.isRemote)
        {
            FCTileEntityAnvil var2 = (FCTileEntityAnvil)this.m_localWorld.getBlockTileEntity(this.m_anvilI, this.m_anvilJ, this.m_anvilK);

            for (int var3 = 0; var3 < 16; ++var3)
            {
                ItemStack var4 = this.craftMatrix.getStackInSlot(var3);

                if (var4 != null)
                {
                    if (var4.itemID == mod_FCBetterThanWolves.fcItemMould.shiftedIndex && var2 != null)
                    {
                        var2.SetSlotContainsMould(var3);

                        if (var4.stackSize > 1)
                        {
                            --var4.stackSize;
                            var1.dropPlayerItem(var4);
                        }
                    }
                    else
                    {
                        var1.dropPlayerItem(var4);
                    }
                }
            }
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.m_localWorld != null && !this.m_localWorld.isRemote ? (this.m_localWorld.getBlockId(this.m_anvilI, this.m_anvilJ, this.m_anvilK) != mod_FCBetterThanWolves.fcAnvil.blockID ? false : var1.getDistanceSq((double)this.m_anvilI + 0.5D, (double)this.m_anvilJ + 0.5D, (double)this.m_anvilK + 0.5D) <= 64.0D) : true;
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

            if (var1 == 0)
            {
                if (!this.mergeItemStack(var4, 17, 53, true))
                {
                    return null;
                }
            }
            else if (var1 > 16 && var1 <= 52)
            {
                if (!this.mergeItemStack(var4, 1, 17, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 17, 53, false))
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

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
    }
}
