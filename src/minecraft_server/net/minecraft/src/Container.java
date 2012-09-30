package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class Container
{
    /** the list of all items(stacks) for the corresponding slot */
    public List inventoryItemStacks = new ArrayList();

    /** the list of all slots in the inventory */
    public List inventorySlots = new ArrayList();
    public int windowId = 0;
    private short transactionID = 0;

    /**
     * list of all people that need to be notified when this craftinventory changes
     */
    protected List crafters = new ArrayList();
    private Set playerList = new HashSet();

    /**
     * Adds an item slot to this container
     */
    protected Slot addSlotToContainer(Slot par1Slot)
    {
        par1Slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(par1Slot);
        this.inventoryItemStacks.add((Object)null);
        return par1Slot;
    }

    public void onCraftGuiOpened(ICrafting par1ICrafting)
    {
        if (this.crafters.contains(par1ICrafting))
        {
            throw new IllegalArgumentException("Listener already listening");
        }
        else
        {
            this.crafters.add(par1ICrafting);
            par1ICrafting.updateCraftingInventory(this, this.getInventory());
            this.updateCraftingResults();
        }
    }

    /**
     * returns a list if itemStacks, for each slot.
     */
    public List getInventory()
    {
        ArrayList var1 = new ArrayList();
        Iterator var2 = this.inventorySlots.iterator();

        while (var2.hasNext())
        {
            Slot var3 = (Slot)var2.next();
            var1.add(var3.getStack());
        }

        return var1;
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
        {
            ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
            ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

            if (!ItemStack.areItemStacksEqual(var3, var2))
            {
                var3 = var2 == null ? null : var2.copy();
                this.inventoryItemStacks.set(var1, var3);
                Iterator var4 = this.crafters.iterator();

                while (var4.hasNext())
                {
                    ICrafting var5 = (ICrafting)var4.next();
                    var5.updateCraftingInventorySlot(this, var1, var3);
                }
            }
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
    {
        return false;
    }

    public Slot getSlotFromInventory(IInventory par1IInventory, int par2)
    {
        Iterator var3 = this.inventorySlots.iterator();
        Slot var4;

        do
        {
            if (!var3.hasNext())
            {
                return null;
            }

            var4 = (Slot)var3.next();
        }
        while (!var4.isHere(par1IInventory, par2));

        return var4;
    }

    public Slot getSlot(int par1)
    {
        return (Slot)this.inventorySlots.get(par1);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        Slot var2 = (Slot)this.inventorySlots.get(par1);
        return var2 != null ? var2.getStack() : null;
    }

    /**
     * @param par1 slotId
     * @param par2 mouseClick: 1 if rightclick, 0 otherwise
     * @param par3 if shift is being held
     * @param par4EntityPlayer
     */
    public ItemStack slotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
    {
        ItemStack var5 = null;

    	//net.minecraft.server.MinecraftServer.logger.info("slotClick #" + slotId + " in " + this.getClass().getName() +  " using " + mouseButton + (isShift ? " w/shift" : "") + " by " + par4EntityPlayer.username);

        if (par2 > 1)
        {
            return null;
        }
        else
        {
            if (par2 == 0 || par2 == 1)
            {
                InventoryPlayer var6 = par4EntityPlayer.inventory;

                if (par1 == -999)
                {
                    if (var6.getItemStack() != null && par1 == -999)
                    {
                    	// ContainerWatcher-->
                    	par4EntityPlayer.watcher.itemDropped(var6.getItemStack(), par2 == 0 ? var6.getItemStack().stackSize : 1);
                    	// <--ContainerWatcher
                    	
                        if (par2 == 0)
                        {
                            par4EntityPlayer.dropPlayerItem(var6.getItemStack());
                            var6.setItemStack((ItemStack)null);
                        }

                        if (par2 == 1)
                        {
                            par4EntityPlayer.dropPlayerItem(var6.getItemStack().splitStack(1));

                            if (var6.getItemStack().stackSize == 0)
                            {
                                var6.setItemStack((ItemStack)null);
                            }
                        }
                    }
                }
                else if (par3)
                {
                    ItemStack var7 = this.transferStackInSlot(par1);

                    if (var7 != null)
                    {
                    	// ContainerWatcher-->
                    	Slot clickedSlot = (Slot)this.inventorySlots.get(par1);
                    	ItemStack remainingItemStack = clickedSlot.getStack();
                    	int quantity = remainingItemStack == null ? var7.stackSize : var7.stackSize - remainingItemStack.stackSize;
                    	
                    	if (clickedSlot.inventory instanceof InventoryPlayer) {
                        	par4EntityPlayer.watcher.itemDeposited(this, par1, var7, quantity, par3);
                        }
                        else {
                        	par4EntityPlayer.watcher.itemWithdrawn(this, par1, var7, quantity, par3);
                        }
                        // <--ContainerWatcher

                        int var8 = var7.itemID;
                        var5 = var7.copy();
                        Slot var9 = (Slot)this.inventorySlots.get(par1);

                        if (var9 != null && var9.getStack() != null && var9.getStack().itemID == var8)
                        {
                            this.retrySlotClick(par1, par2, par3, par4EntityPlayer);
                        }
                    }
                }
                else
                {
                    if (par1 < 0)
                    {
                        return null;
                    }

                    Slot var12 = (Slot)this.inventorySlots.get(par1);

                    if (var12 != null)
                    {
                        ItemStack var13 = var12.getStack();
                        ItemStack var14 = var6.getItemStack();

                        if (var13 != null)
                        {
                            var5 = var13.copy();
                        }

                        int var10;

                        // The slot is empty.
                        if (var13 == null)
                        {
                        	// Player is holding an item and is valid for the slot.
                            if (var14 != null && var12.isItemValid(var14))
                            {
                                // Quantity to store in spot is 1 if right click, otherwise the stack size.
                                var10 = par2 == 0 ? var14.stackSize : 1;

                            	// Make sure the stack is not larger than the slot allows.
                                if (var10 > var12.getSlotStackLimit())
                                {
                                    var10 = var12.getSlotStackLimit();
                                }

                                // ContainerWatcher-->
                                if (var12.inventory instanceof InventoryPlayer) {
                                	par4EntityPlayer.watcher.itemWithdrawn(this, par1, var14, var10);
                                }
                                else {
                                	par4EntityPlayer.watcher.itemDeposited(this, par1, var14, var10);
                                }
                                // <--ContainerWatcher
                                
                                // Store the specified quantity of items in the slot.
                                var12.putStack(var14.splitStack(var10));

                                // Empty the players hand if the entire stack was placed.
                                if (var14.stackSize == 0)
                                {
                                    var6.setItemStack((ItemStack)null);
                                }
                            }
                        }
                        // The player is not holding an item.
                        else if (var14 == null)
                        {
                        	// Determine the quantity to take: half if right clicked, or the entire stack if left clicked.
                            var10 = par2 == 0 ? var13.stackSize : (var13.stackSize + 1) / 2;
                            // Get the specified quantity of items from the stack.
                            ItemStack var11 = var12.decrStackSize(var10);
                            // Place the items into the players hand.
                            var6.setItemStack(var11);

                            // ContainerWatcher-->
                            par4EntityPlayer.watcher.itemWithdrawn(this, par1, var13, var10);
                            // <--ContainerWatcher
                            
                            // Reset the slot if it is empty?
                            if (var13.stackSize == 0)
                            {
                                var12.putStack((ItemStack)null);
                            }

                            var12.onPickupFromSlot(var6.getItemStack());
                        }
                        // The player is holding an item that belongs in the slot.
                        else if (var12.isItemValid(var14))
                        {
                        	// The item in the players hand and the slot are the same type.
                            if (var13.itemID == var14.itemID && (!var13.getHasSubtypes() || var13.getItemDamage() == var14.getItemDamage()) && ItemStack.func_77970_a(var13, var14))
                            {
                            	// Set the quantity to store to 1 if right clicking, otherwise it's the size of the stack.
                                var10 = par2 == 0 ? var14.stackSize : 1;

                                // Limit the quantity of items being stored to the remaining space in the slot. 
                                if (var10 > var12.getSlotStackLimit() - var13.stackSize)
                                {
                                    var10 = var12.getSlotStackLimit() - var13.stackSize;
                                }
                                // Not sure the point of this.
                                if (var10 > var14.getMaxStackSize() - var13.stackSize)
                                {
                                    var10 = var14.getMaxStackSize() - var13.stackSize;
                                }
                                // Remove the quantity of items from the players hand.
                                var14.splitStack(var10);

                                // Empty the players hand if the stack is now 0.
                                if (var14.stackSize == 0)
                                {
                                    var6.setItemStack((ItemStack)null);
                                }

                                // Add the quantity of items to the slot's stack.
                                var13.stackSize += var10;
                                
                                // ContainerWatcher-->
                            	// TODO: par4EntityPlayer.containerWatcher.itemWithdrawn(clickedItemStack, slotId, quantity);
                            	// <--ContainerWatcher
                            	
                            	// TODO: why comparing par1 (slotId) values?
                                /*if (par4EntityPlayer.hasStealableInventoryOpen== true)
                                {
                                	if (par4EntityPlayer.hasChestOpen == true)
                                	{
                                		if (par1 <= 26 && par1 >= 0)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> has just PLACED "+ var13.getItemName()+" ("+var13.itemID+") x"+ var10 +" into a small chest.");
                                        }
                                		else if (par1 <= 62 && par1 >= 27)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> Has just PLACED " + var13.getItemName()+" ("+var13.itemID+") x"+ var10 + " into their pocket.");
                                		}
                                	}
                                	
                                	if (par4EntityPlayer.hasLargeChestOpen == true)
                                	{
                                		if (par1 <= 53 && par1 >= 0)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> Has just PICKED UP " + var13.getItemName()+" ("+var13.itemID+") x"+ var10 + " into a large chest.");
                                		}
                                		else if (par1 <= 89 && par1 >= 0)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> Has just PICKED UP " + var13.getItemName()+" ("+var13.itemID+") x"+ var10 + " into a their pocket.");
                                		}
                                	}	
                                 }*/
                                
                            }
                            // Items are not the same, and the quantity of items can fit in the slot.
                            else if (var14.stackSize <= var12.getSlotStackLimit())
                            {
                            	 // TODO: why comparing par1 (slotId) values?
                                 /*if (par4EntityPlayer.hasStealableInventoryOpen== true)
                                 {
                                 	if (par4EntityPlayer.hasChestOpen == true)
                                 	{
                                 		if (par1 <= 26 && par1 >= 0)
                                 		{
                                 			logger.info("<"+par4EntityPlayer.username+"> has just SWAPPED "+ var14.getItemName()+" ("+var14.itemID+") x"+ var14.stackSize +" from their pocket for "+ var13.getItemName()+" ("+var13.itemID+") x"+ var13.stackSize +" from a small chest");
                                         }
                                 		else if (par1 <= 62 && par1 >= 27)
                                 		{
                                 			logger.info("<"+par4EntityPlayer.username+"> Has just SWAPPED "+ var14.getItemName()+" ("+var14.itemID+") x"+ var14.stackSize +" from their pocket for "+ var13.getItemName()+" ("+var13.itemID+") x"+ var13.stackSize +" from a small chest");                                 		
                                 		}
                                 	}
                                 	
                                 	if (par4EntityPlayer.hasLargeChestOpen == true)
                                 	{
                                 		if (par1 <= 53 && par1 >= 0)
                                 		{
                                 			logger.info("<"+par4EntityPlayer.username+"> Has just SWAPPED "+ var14.getItemName()+" ("+var14.itemID+") x"+ var14.stackSize +" from their pocket for "+ var13.getItemName()+" ("+var13.itemID+") x"+ var13.stackSize +" from a small chest");
                                 		}
                                 		else if (par1 <= 89 && par1 >= 0)
                                 		{
                                 			logger.info("<"+par4EntityPlayer.username+"> Has just SWAPPED "+ var14.getItemName()+" ("+var14.itemID+") x"+ var14.stackSize +" from their pocket for "+ var13.getItemName()+" ("+var13.itemID+") x"+ var13.stackSize +" from a small chest");
                                 		}
                                 	}	
                                  }*/
                                var12.putStack(var14);
                                var6.setItemStack(var13);
                            }
                        }
                        // TODO: if the items are the same and the slots are invalid <armor>
                        else if (var13.itemID == var14.itemID && var14.getMaxStackSize() > 1 && (!var13.getHasSubtypes() || var13.getItemDamage() == var14.getItemDamage()) && ItemStack.func_77970_a(var13, var14))
                        {
                            var10 = var13.stackSize;

                            if (var10 > 0 && var10 + var14.stackSize <= var14.getMaxStackSize())
                            {
                                var14.stackSize += var10;
                                var13 = var12.decrStackSize(var10);

                                if (var13.stackSize == 0)
                                {
                                    var12.putStack((ItemStack)null);
                                }

                                var12.onPickupFromSlot(var6.getItemStack());
                            }
                        }

                        var12.onSlotChanged();
                    }
                }
            }

            return var5;
        }
    }

    protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
    {
        this.slotClick(par1, par2, par3, par4EntityPlayer);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
    	// ContainerWatcher-->
        par1EntityPlayer.watcher.containerClosed(this);
        // <--ContainerWatcher
    	
        InventoryPlayer var2 = par1EntityPlayer.inventory;

        if (var2.getItemStack() != null)
        {
            par1EntityPlayer.dropPlayerItem(var2.getItemStack());
            var2.setItemStack((ItemStack)null);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.updateCraftingResults();
    }

    /**
     * args: slotID, itemStack to put in slot
     */
    public void putStackInSlot(int par1, ItemStack par2ItemStack)
    {
        this.getSlot(par1).putStack(par2ItemStack);
    }

    /**
     * gets whether or not the player can craft in this inventory or not
     */
    public boolean getCanCraft(EntityPlayer par1EntityPlayer)
    {
        return !this.playerList.contains(par1EntityPlayer);
    }

    /**
     * sets whether the player can craft in this inventory or not
     */
    public void setCanCraft(EntityPlayer par1EntityPlayer, boolean par2)
    {
        if (par2)
        {
            this.playerList.remove(par1EntityPlayer);
        }
        else
        {
            this.playerList.add(par1EntityPlayer);
        }
    }

    public abstract boolean canInteractWith(EntityPlayer var1);

    /**
     * merges provided ItemStack with the first avaliable one in the container/player inventory
     */
    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean var5 = false;
        int var6 = par2;

        if (par4)
        {
            var6 = par3 - 1;
        }

        Slot var7;
        ItemStack var8;

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.stackSize > 0 && (!par4 && var6 < par3 || par4 && var6 >= par2))
            {
                var7 = (Slot)this.inventorySlots.get(var6);
                var8 = var7.getStack();

                if (var8 != null && var8.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == var8.getItemDamage()) && ItemStack.func_77970_a(par1ItemStack, var8))
                {
                    int var9 = var8.stackSize + par1ItemStack.stackSize;

                    if (var9 <= par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.stackSize = 0;
                        var8.stackSize = var9;
                        var7.onSlotChanged();
                        var5 = true;
                    }
                    else if (var8.stackSize < par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - var8.stackSize;
                        var8.stackSize = par1ItemStack.getMaxStackSize();
                        var7.onSlotChanged();
                        var5 = true;
                    }
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        if (par1ItemStack.stackSize > 0)
        {
            if (par4)
            {
                var6 = par3 - 1;
            }
            else
            {
                var6 = par2;
            }

            while (!par4 && var6 < par3 || par4 && var6 >= par2)
            {
                var7 = (Slot)this.inventorySlots.get(var6);
                var8 = var7.getStack();

                if (var8 == null)
                {
                    var7.putStack(par1ItemStack.copy());
                    var7.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    var5 = true;
                    break;
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        return var5;
    }
}
