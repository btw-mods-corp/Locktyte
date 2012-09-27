package net.minecraft.src;

import java.util.List;

public class FCTileEntityMillStone extends TileEntity implements IInventory
{
    private final int m_iInventorySize = 3;
    private final int m_iStackSizeLimit = 64;
    private final double m_dMaxPlayerInteractionDist = 64.0D;
    private final int m_iTimeToGrind = 200;
    private boolean m_bValidateContentsOnUpdate = true;
    private boolean m_bContainsIngrediantsToGrind;
    private ItemStack[] m_InventoryContents = new ItemStack[3];
    public int m_iGrindCounter = 0;

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.m_InventoryContents = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.m_InventoryContents.length)
            {
                this.m_InventoryContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        if (var1.hasKey("grindCounter"))
        {
            this.m_iGrindCounter = var1.getInteger("grindCounter");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.m_InventoryContents.length; ++var3)
        {
            if (this.m_InventoryContents[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.m_InventoryContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        var1.setTag("Items", var2);
        var1.setInteger("grindCounter", this.m_iGrindCounter);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            int var1 = this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord);
            Block var2 = Block.blocksList[var1];

            if (var2 != null && var2 instanceof FCBlockMillStone)
            {
                FCBlockMillStone var3 = (FCBlockMillStone)var2;

                if (this.m_bValidateContentsOnUpdate)
                {
                    this.ValidateContentsForGrinding(var3);
                }

                if (this.m_bContainsIngrediantsToGrind && var3.IsMechanicalOn(this.worldObj, this.xCoord, this.yCoord, this.zCoord))
                {
                    ++this.m_iGrindCounter;

                    if (this.m_iGrindCounter >= 200)
                    {
                        this.GrindContents(var3);
                        this.m_iGrindCounter = 0;
                        this.m_bValidateContentsOnUpdate = true;
                    }
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 3;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return this.m_InventoryContents[var1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        return FCUtilsInventory.DecrStackSize(this, var1, var2);
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        if (this.m_InventoryContents[var1] != null)
        {
            ItemStack var2 = this.m_InventoryContents[var1];
            this.m_InventoryContents[var1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        this.m_InventoryContents[var1] = var2;

        if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
        {
            var2.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "MillStone";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        super.onInventoryChanged();

        if (this.worldObj != null && !this.worldObj.isRemote)
        {
            if (this.IsWholeCompanionCubeInInventory())
            {
                this.worldObj.playSoundEffect((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), "mob.wolf.whine", 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
            }

            this.m_bValidateContentsOnUpdate = true;
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    public void EjectStack(ItemStack var1)
    {
        FCUtilsBlockPos var2 = new FCUtilsBlockPos(this.xCoord, this.yCoord, this.zCoord);
        int var3 = 2 + this.worldObj.rand.nextInt(4);
        var2.AddFacingAsOffset(var3);
        float var4 = this.worldObj.rand.nextFloat() * 0.7F + 0.15F;
        float var5 = this.worldObj.rand.nextFloat() * 0.2F + 0.1F;
        float var6 = this.worldObj.rand.nextFloat() * 0.7F + 0.15F;
        EntityItem var7 = new EntityItem(this.worldObj, (double)((float)var2.i + var4), (double)((float)var2.j + var5), (double)((float)var2.k + var6), var1);
        float var8 = 0.05F;
        var7.motionX = (double)((float)this.worldObj.rand.nextGaussian() * var8);
        var7.motionY = (double)((float)this.worldObj.rand.nextGaussian() * var8 + 0.2F);
        var7.motionZ = (double)((float)this.worldObj.rand.nextGaussian() * var8);

        switch (var3)
        {
            case 2:
                var7.motionZ = (double)(-var8);
                break;

            case 3:
                var7.motionZ = (double)var8;
                break;

            case 4:
                var7.motionX = (double)(-var8);
                break;

            case 5:
                var7.motionX = (double)var8;
        }

        var7.delayBeforeCanPickup = 10;
        this.worldObj.spawnEntityInWorld(var7);
    }

    public int getGrindProgressScaled(int var1)
    {
        return this.m_iGrindCounter * var1 / 200;
    }

    public boolean IsGrinding()
    {
        return this.m_iGrindCounter > 0;
    }

    public boolean IsWholeCompanionCubeInInventory()
    {
        for (int var1 = 0; var1 < 3; ++var1)
        {
            if (this.m_InventoryContents[var1] != null)
            {
                Item var2 = this.m_InventoryContents[var1].getItem();

                if (var2 != null && var2.shiftedIndex == mod_FCBetterThanWolves.fcCompanionCube.blockID && this.m_InventoryContents[var1].getItemDamage() == 0)
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean GrindContents(FCBlockMillStone var1)
    {
        FCCraftingManagerMillStone var2 = FCCraftingManagerMillStone.getInstance();
        List var3 = FCCraftingManagerMillStone.getInstance().GetValidCraftingIngrediants(this);

        if (var3 == null)
        {
            return false;
        }
        else
        {
            for (int var4 = 0; var4 < var3.size(); ++var4)
            {
                ItemStack var5 = ((ItemStack)var3.get(var4)).copy();

                if (var5 != null)
                {
                    int var6 = var5.getItem().shiftedIndex;

                    if (var6 == mod_FCBetterThanWolves.fcCompanionCube.blockID && var5.getItemDamage() == 0)
                    {
                        this.worldObj.playAuxSFX(2242, this.xCoord, this.yCoord, this.zCoord, 0);
                        break;
                    }
                }
            }

            List var7 = var2.ConsumeIngrediantsAndReturnResult(this);
            assert var7 != null && var7.size() > 0;

            for (int var8 = 0; var8 < var7.size(); ++var8)
            {
                ItemStack var9 = ((ItemStack)var7.get(var8)).copy();

                if (var9 != null)
                {
                    this.EjectStack(var9);
                }
            }

            return true;
        }
    }

    private void ValidateContentsForGrinding(FCBlockMillStone var1)
    {
        int var2 = var1.GetCurrentGrindingType(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        byte var3 = 0;
        List var4 = FCCraftingManagerMillStone.getInstance().GetValidCraftingIngrediants(this);

        if (var4 != null)
        {
            this.m_bContainsIngrediantsToGrind = true;
            var3 = 1;

            for (int var5 = 0; var5 < var4.size(); ++var5)
            {
                ItemStack var6 = ((ItemStack)var4.get(var5)).copy();

                if (var6 != null)
                {
                    int var7 = var6.getItem().shiftedIndex;

                    if (var7 == mod_FCBetterThanWolves.fcCompanionCube.blockID && var6.getItemDamage() == 0)
                    {
                        var3 = 3;
                        break;
                    }

                    if (var7 == Block.netherrack.blockID)
                    {
                        var3 = 2;
                        break;
                    }
                }
            }
        }
        else
        {
            this.m_iGrindCounter = 0;
            this.m_bContainsIngrediantsToGrind = false;
        }

        this.m_bValidateContentsOnUpdate = false;

        if (var2 != var3)
        {
            var1.SetCurrentGrindingType(this.worldObj, this.xCoord, this.yCoord, this.zCoord, var3);
        }
    }
}
