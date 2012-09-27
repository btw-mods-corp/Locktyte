package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class Container
{
    public static Logger logger = Logger.getLogger("Minecraft");

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

    // TODO: args: slotID where clicked, mouseclick (1 if rightclick 0 if else), boolean Holding Shift, and player entity doing the clicking
    public ItemStack slotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
    {
        ItemStack var5 = null;

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
                        if (par2 == 0)
                        {
                        	if (par4EntityPlayer.hasStealableInventoryOpen == true);
                    		{
                    	logger.info("<"+par4EntityPlayer.username+"> has dropped "+var6.getItemStack().getItemName()+" ("+var6.getItemStack().itemID+") x"+var6.getItemStack().stackSize);
                    		}
                        	
                            par4EntityPlayer.dropPlayerItem(var6.getItemStack());
                            var6.setItemStack((ItemStack)null);
                        }

                        if (par2 == 1)
                        {
                        	if (par4EntityPlayer.hasStealableInventoryOpen == true);
                    		{
                    	logger.info("<"+par4EntityPlayer.username+"> has dropped one "+var6.getItemStack().getItemName()+" ("+var6.getItemStack().itemID+")");
                    		}
                        	
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
                        if (par4EntityPlayer.hasStealableInventoryOpen== true)
                        {
                        	if (par4EntityPlayer.hasChestOpen == true)
                        	{
                        		if (par1 <= 26 && par1 >= 0)
                        		{
                        			logger.info("<"+par4EntityPlayer.username+"> Has just WITHDREW "+ var7.getItemName()+" ("+var7.itemID+") x"+ var7.stackSize +" from a small chest.");
                                }
                        		else if (par1 <= 62 && par1 >= 27)
                        		{
                        			logger.info("<"+par4EntityPlayer.username+"> Has just DEPOSITED " + var7.getItemName()+" ("+var7.itemID+") x"+ var7.stackSize + " into a small chest.");
                        		}
                        	}
                        	
                        	if (par4EntityPlayer.hasLargeChestOpen == true)
                        	{
                        		if (par1 <= 53 && par1 >= 0)
                        		{
                        			logger.info("<"+par4EntityPlayer.username+"> Has just WITHDREW " + var7.getItemName()+" ("+var7.itemID+") x"+ var7.stackSize + " from a large chest.");
                        		}
                        		else if (par1 <= 89 && par1 >= 0)
                        		{
                        			logger.info("<"+par4EntityPlayer.username+"> Has just DEPOSITED " + var7.getItemName()+" ("+var7.itemID+") x"+ var7.stackSize + " into a large chest.");
                        		}
                        	}	
                         }

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

                    //so long as you click on an inventory slot, var12 is not null
                    //var 13 deals with the item in the slot youre clicking on
                    //var 14 deals with the item your mouse is holding when you click on a slot.
                    if (var12 != null)
                    {
                        ItemStack var13 = var12.getStack();
                        ItemStack var14 = var6.getItemStack();

                        if (var13 != null)
                        {
                            var5 = var13.copy();
                        }

                        int var10;
                        
                        //nothing in slot
                        if (var13 == null)
                        {
                        	//something in hand & slot is valid <armor>
                            if (var14 != null && var12.isItemValid(var14))
                            {
                                //if right clicking, var10 = 1, else var10 = size of stack in hand
                            	var10 = par2 == 0 ? var14.stackSize : 1;
                                
                            	//prevents stack larger than limit from being placed in a slot (precautionary)
                                if (var10 > var12.getSlotStackLimit())
                                {
                                    var10 = var12.getSlotStackLimit();
                                }
                                
                                //places var10 (quantity) of var14 (item) in var12 (slot)
                                var12.putStack(var14.splitStack(var10));
                                
                                if (par4EntityPlayer.hasStealableInventoryOpen== true)
                                {
                                	if (par4EntityPlayer.hasChestOpen == true)
                                	{
                                		if (par1 <= 26 && par1 >= 0)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> has just PLACED "+ var14.getItemName()+" ("+var14.itemID+") x"+ var10 +" into a small chest.");
                                        }
                                		else if (par1 <= 62 && par1 >= 27)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> Has just PLACED " + var14.getItemName()+" ("+var14.itemID+") x"+ var10 + " into their pocket.");
                                		}
                                	}
                                	
                                	if (par4EntityPlayer.hasLargeChestOpen == true)
                                	{
                                		if (par1 <= 53 && par1 >= 0)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> Has just PLACED " + var14.getItemName()+" ("+var14.itemID+") x"+ var10 + " into a large chest.");
                                		}
                                		else if (par1 <= 89 && par1 >= 0)
                                		{
                                			logger.info("<"+par4EntityPlayer.username+"> Has just PLACED " + var14.getItemName()+" ("+var14.itemID+") x"+ var10 + " into a their pocket.");
                                		}
                                	}	
                                 }
                                
                                //last item/whole stack is placed, sets hand to empty
                                if (var14.stackSize == 0)
                                {
                                    var6.setItemStack((ItemStack)null);
                                }
                            }
                        }
                        //not holding an item
                        else if (var14 == null)
                        {	
                        	//var6 is stack in hand
                        	//if leftclicked var10 = size of stack in slot; rightclicked = half size of stack in slot
                            var10 = par2 == 0 ? var13.stackSize : (var13.stackSize + 1) / 2;
                            // var11 is the stack after the slot-stack is decreased by var10
                            ItemStack var11 = var12.decrStackSize(var10);
                            // place the items into the player's hand
                            var6.setItemStack(var11);

                            if (par4EntityPlayer.hasStealableInventoryOpen== true)
                            {
                            	if (par4EntityPlayer.hasChestOpen == true)
                            	{
                            		if (par1 <= 26 && par1 >= 0)
                            		{
                            			logger.info("<"+par4EntityPlayer.username+"> has just PICKED UP "+ var11.getItemName()+" ("+var11.itemID+") x"+ var10 +" from a small chest.");
                                    }
                            		else if (par1 <= 62 && par1 >= 27)
                            		{
                            			logger.info("<"+par4EntityPlayer.username+"> Has just PICKED UP " + var11.getItemName()+" ("+var11.itemID+") x"+ var10 + " from their pocket.");
                            		}
                            	}
                            	
                            	if (par4EntityPlayer.hasLargeChestOpen == true)
                            	{
                            		if (par1 <= 53 && par1 >= 0)
                            		{
                            			logger.info("<"+par4EntityPlayer.username+"> Has just PICKED UP " + var11.getItemName()+" ("+var11.itemID+") x"+ var10 + " from a large chest.");
                            		}
                            		else if (par1 <= 89 && par1 >= 0)
                            		{
                            			logger.info("<"+par4EntityPlayer.username+"> Has just PICKED UP " + var11.getItemName()+" ("+var11.itemID+") x"+ var10 + " from a their pocket.");
                            		}
                            	}	
                             }
                            
                            //tries to pick something up from an empty slot
                            if (var13.stackSize == 0)
                            {
                                var12.putStack((ItemStack)null);
                            }

                            var12.onPickupFromSlot(var6.getItemStack());
                        }
                        //does the item in hand belong in the slot <armor>
                        else if (var12.isItemValid(var14))
                        {	
                        	// if the item in hand and slot are the same
                            if (var13.itemID == var14.itemID && (!var13.getHasSubtypes() || var13.getItemDamage() == var14.getItemDamage()) && ItemStack.func_77970_a(var13, var14))
                            {
                            	//var10 = stack sice in hand OR 1 (right click)
                                var10 = par2 == 0 ? var14.stackSize : 1;
                                
                                //if stack size or 1 is more than the slot's limit minus the size of the stack in the slot
                                //then var10 is the available room in the slot. mainly for armor
                                if (var10 > var12.getSlotStackLimit() - var13.stackSize)
                                {
                                    var10 = var12.getSlotStackLimit() - var13.stackSize;
                                }
                                //if var10 is more than the max stack size of the item in hand minus the stack size in slot
                                //same as above for items?
                                if (var10 > var14.getMaxStackSize() - var13.stackSize)
                                {
                                    var10 = var14.getMaxStackSize() - var13.stackSize;
                                }
                                //Remove the argument from the stack size. Return a new stack object with argument size.
                                var14.splitStack(var10);

                                if (var14.stackSize == 0)
                                {
                                    var6.setItemStack((ItemStack)null);
                                }

                                var13.stackSize += var10;
                                
                                if (par4EntityPlayer.hasStealableInventoryOpen== true)
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
                                 }
                                
                            }
                            //if theyre not the same and if the size of items in hand can fit in the slot.
                            else if (var14.stackSize <= var12.getSlotStackLimit())
                            {
                            	 if (par4EntityPlayer.hasStealableInventoryOpen== true)
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
                                  }
                                var12.putStack(var14);
                                var6.setItemStack(var13);
                            }
                        }
                        //if the items are the same and the slots are invalid <armor>
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
