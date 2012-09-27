package net.minecraft.src;

import java.util.List;

public class FCTileEntityHopper extends TileEntity implements IInventory, FCITileEntityDataPacketHandler
{
    private final int iHopperInventorySize = 19;
    private final int iHopperStackSizeLimit = 64;
    private final int iHopperStackSizeToDrop = 8;
    private final double dHopperMaxPlayerInteractionDist = 64.0D;
    private final int iHopperOverloadSoulCount = 8;
    private final int m_iHopperXPInventorySpace = 100;
    private final int m_iHopperXPEjectUnitSize = 20;
    private final int m_iHopperXPDelayBetweenDrops = 10;
    private final int iHopperTimeToEject = 3;
    private ItemStack[] hopperContents = new ItemStack[19];
    private int iHopperEjectCounter = 0;
    private int iHopperContainedSoulCount = 0;
    private int iHopperContainedXPCount = 0;
    private int m_iHopperXPDropDelayCount = 10;
    public boolean bHopperEjectBlocked = false;
    public int m_iMechanicalPowerIndicator = 0;
    public short m_sFilterType = 0;
    public short m_sStorageSlotsOccupied = 0;

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.hopperContents = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.hopperContents.length)
            {
                this.hopperContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        if (var1.hasKey("grindCounter"))
        {
            this.iHopperEjectCounter = var1.getInteger("grindCounter");
        }

        if (var1.hasKey("iHoppeContainedSoulCount"))
        {
            this.iHopperContainedSoulCount = var1.getInteger("iHoppeContainedSoulCount");
        }

        if (var1.hasKey("iHopperContainedXPCount"))
        {
            this.iHopperContainedXPCount = var1.getInteger("iHopperContainedXPCount");
        }

        this.ValidateInventoryStateVariables();
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.hopperContents.length; ++var3)
        {
            if (this.hopperContents[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.hopperContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        var1.setTag("Items", var2);
        var1.setInteger("grindCounter", this.iHopperEjectCounter);
        var1.setInteger("iHoppeContainedSoulCount", this.iHopperContainedSoulCount);
        var1.setInteger("iHopperContainedXPCount", this.iHopperContainedXPCount);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            boolean var1 = ((FCBlockHopper)mod_FCBetterThanWolves.fcHopper).IsBlockOn(this.worldObj, this.xCoord, this.yCoord, this.zCoord);

            if (var1)
            {
                this.m_iMechanicalPowerIndicator = 1;
                this.AttemptToEjectXPFromInv();

                if (!this.bHopperEjectBlocked)
                {
                    ++this.iHopperEjectCounter;

                    if (this.iHopperEjectCounter >= 3)
                    {
                        this.AttemptToEjectStackFromInv();
                        this.iHopperEjectCounter = 0;
                    }
                }
                else
                {
                    this.iHopperEjectCounter = 0;
                }
            }
            else
            {
                this.m_iMechanicalPowerIndicator = 0;
                this.iHopperEjectCounter = 0;
                this.m_iHopperXPDropDelayCount = 0;
            }

            if (this.iHopperContainedSoulCount > 0)
            {
                if (this.m_sFilterType == 6)
                {
                    int var2 = this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord);
                    int var3 = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1, this.zCoord);

                    if (var1 && (var2 != mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID || var3 != 0))
                    {
                        this.iHopperContainedSoulCount = 0;
                    }

                    if (this.iHopperContainedSoulCount >= 8)
                    {
                        if (var1 && var2 == mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID && var3 == 0)
                        {
                            this.worldObj.setBlockWithNotify(this.xCoord, this.yCoord - 1, this.zCoord, 0);
                            ItemStack var4 = new ItemStack(mod_FCBetterThanWolves.fcSoulUrn.shiftedIndex, 1, 0);
                            FCUtilsItem.EjectStackWithRandomOffset(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, var4);
                            this.iHopperContainedSoulCount = 0;
                        }
                        else
                        {
                            this.HopperSoulOverload();
                        }
                    }
                }
                else
                {
                    this.iHopperContainedSoulCount = 0;
                }
            }
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setShort("f", this.m_sFilterType);
        var1.setShort("s", this.m_sStorageSlotsOccupied);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }

    public void readNBTFromPacket(NBTTagCompound var1)
    {
        this.m_sFilterType = var1.getShort("f");
        this.m_sStorageSlotsOccupied = var1.getShort("s");
        this.worldObj.markBlocksDirty(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 19;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return this.hopperContents[var1];
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
        if (this.hopperContents[var1] != null)
        {
            ItemStack var2 = this.hopperContents[var1];
            this.hopperContents[var1] = null;
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
        this.hopperContents[var1] = var2;

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
        return "Hopper";
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

        if (this.worldObj != null)
        {
            this.bHopperEjectBlocked = false;

            if (this.ValidateInventoryStateVariables())
            {
                this.worldObj.markBlockNeedsUpdate(this.xCoord, this.yCoord, this.zCoord);
            }

            int var1 = FCUtilsInventory.GetNumOccupiedStacksInSlotRange(this, 0, 17);
            ((FCBlockHopper)((FCBlockHopper)mod_FCBetterThanWolves.fcHopper)).SetHopperFull(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.m_sStorageSlotsOccupied == 18);
            ((FCBlockHopper)((FCBlockHopper)mod_FCBetterThanWolves.fcHopper)).SetHasFilter(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.m_sFilterType > 0);
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

    private boolean ValidateInventoryStateVariables()
    {
        boolean var1 = false;
        short var2 = this.CheckFilterTypeBasedOnInventory();

        if (var2 != this.m_sFilterType)
        {
            this.m_sFilterType = var2;
            var1 = true;
        }

        short var3 = (short)FCUtilsInventory.GetNumOccupiedStacksInSlotRange(this, 0, 17);

        if (var3 != this.m_sStorageSlotsOccupied)
        {
            this.m_sStorageSlotsOccupied = var3;
            var1 = true;
        }

        return var1;
    }

    public short CheckFilterTypeBasedOnInventory()
    {
        ItemStack var1 = this.getStackInSlot(18);

        if (var1 != null && var1.stackSize > 0)
        {
            if (var1.itemID == Block.ladder.blockID)
            {
                return (short)1;
            }

            if (var1.itemID == Block.trapdoor.blockID)
            {
                return (short)2;
            }

            if (var1.itemID == mod_FCBetterThanWolves.fcGrate.shiftedIndex)
            {
                return (short)3;
            }

            if (var1.itemID == mod_FCBetterThanWolves.fcWicker.shiftedIndex)
            {
                return (short)4;
            }

            if (var1.itemID == mod_FCBetterThanWolves.fcRollersItem.shiftedIndex)
            {
                return (short)5;
            }

            if (var1.itemID == Block.slowSand.blockID)
            {
                return (short)6;
            }

            if (var1.itemID == Block.fenceIron.blockID)
            {
                return (short)7;
            }
        }

        return (short)0;
    }

    public boolean CanCurrentFilterProcessItem(ItemStack var1)
    {
        Item var2 = var1.getItem();

        if (this.m_sFilterType > 0)
        {
            if (var2.shiftedIndex < 256 && var2.shiftedIndex != Block.sand.blockID && var2.shiftedIndex != Block.gravel.blockID && var2.shiftedIndex != Block.torchWood.blockID && var2.shiftedIndex != Block.torchRedstoneActive.blockID && var2.shiftedIndex != Block.sapling.blockID && var2.shiftedIndex != Block.plantYellow.blockID && var2.shiftedIndex != Block.plantRed.blockID && var2.shiftedIndex != Block.mushroomBrown.blockID && var2.shiftedIndex != Block.mushroomRed.blockID)
            {
                return false;
            }

            if (this.m_sFilterType == 1)
            {
                return true;
            }

            if (this.m_sFilterType < 6)
            {
                if (var2.shiftedIndex != Block.sand.blockID && var2.shiftedIndex != Block.gravel.blockID && var2.shiftedIndex != Item.seeds.shiftedIndex && var2.shiftedIndex != Item.gunpowder.shiftedIndex && var2.shiftedIndex != Item.redstone.shiftedIndex && var2.shiftedIndex != Item.lightStoneDust.shiftedIndex && (var2.shiftedIndex != Item.dyePowder.shiftedIndex || var1.getItemDamage() == 0) && var2.shiftedIndex != Item.sugar.shiftedIndex && var2.shiftedIndex != Item.pumpkinSeeds.shiftedIndex && var2.shiftedIndex != Item.melonSeeds.shiftedIndex && var2.shiftedIndex != Item.netherStalkSeeds.shiftedIndex && var2.shiftedIndex != Item.blazePowder.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHempSeeds.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcFlour.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcGroundNetherrack.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHellfireDust.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcCoalDust.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcPotash.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcSawDust.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcSoulDust.shiftedIndex)
                {
                    if (this.m_sFilterType == 4)
                    {
                        return false;
                    }

                    if (var2.shiftedIndex != Item.silk.shiftedIndex && var2.shiftedIndex != Item.paper.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHempFibers.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcArcaneScroll.shiftedIndex)
                    {
                        if (this.m_sFilterType == 5)
                        {
                            if (var2.shiftedIndex != Item.leather.shiftedIndex && var2.shiftedIndex != Item.map.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcTannedLeather.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcScouredLeather.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHempCloth.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHemp.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcStrap.shiftedIndex)
                            {
                                return false;
                            }

                            return true;
                        }

                        if (var2.shiftedIndex != Block.plantYellow.blockID && var2.shiftedIndex != Block.plantRed.blockID && var2.shiftedIndex != Block.mushroomBrown.blockID && var2.shiftedIndex != Block.mushroomRed.blockID && var2.shiftedIndex != Item.appleRed.shiftedIndex && var2.shiftedIndex != Item.coal.shiftedIndex && var2.shiftedIndex != Item.diamond.shiftedIndex && var2.shiftedIndex != Item.feather.shiftedIndex && var2.shiftedIndex != Item.flint.shiftedIndex && var2.shiftedIndex != Item.appleGold.shiftedIndex && var2.shiftedIndex != Item.snowball.shiftedIndex && var2.shiftedIndex != Item.clay.shiftedIndex && var2.shiftedIndex != Item.slimeBall.shiftedIndex && var2.shiftedIndex != Item.egg.shiftedIndex && var2.shiftedIndex != Item.cookie.shiftedIndex && var2.shiftedIndex != Item.dyePowder.shiftedIndex && var2.shiftedIndex != Item.enderPearl.shiftedIndex && var2.shiftedIndex != Item.ghastTear.shiftedIndex && var2.shiftedIndex != Item.goldNugget.shiftedIndex && var2.shiftedIndex != Item.spiderEye.shiftedIndex && var2.shiftedIndex != Item.fermentedSpiderEye.shiftedIndex && var2.shiftedIndex != Item.eyeOfEnder.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcNethercoal.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcDonut.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcDung.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcBroadheadArrowhead.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcSoap.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHardBoiledEgg.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcFilament.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcPolishedLapis.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcItemDogFood.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcItemRawEgg.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcItemFriedEgg.shiftedIndex)
                        {
                            if (this.m_sFilterType == 3)
                            {
                                return false;
                            }

                            if (var2.shiftedIndex != Item.bone.shiftedIndex && var2.shiftedIndex != Item.arrow.shiftedIndex && var2.shiftedIndex != Item.stick.shiftedIndex && var2.shiftedIndex != Item.wheat.shiftedIndex && var2.shiftedIndex != Item.reed.shiftedIndex && var2.shiftedIndex != Item.fishingRod.shiftedIndex && var2.shiftedIndex != Item.blazeRod.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcBroadheadArrow.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcRopeItem.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcStrap.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcBelt.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcHaft.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            else if (this.m_sFilterType == 6)
            {
                if (var2.shiftedIndex != mod_FCBetterThanWolves.fcGroundNetherrack.shiftedIndex && var2.shiftedIndex != mod_FCBetterThanWolves.fcSoulDust.shiftedIndex)
                {
                    return false;
                }
            }
            else
            {
                if (this.m_sFilterType != 7)
                {
                    return false;
                }

                if (var2.maxStackSize <= 1)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean IsEjecting()
    {
        return ((FCBlockHopper)mod_FCBetterThanWolves.fcHopper).IsBlockOn(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    private void AttemptToEjectStackFromInv()
    {
        int var1 = FCUtilsInventory.GetRandomOccupiedStackInRange(this, this.worldObj.rand, 0, 18);

        if (var1 >= 0 && var1 < 18)
        {
            ItemStack var2 = this.getStackInSlot(var1);
            int var3;

            if (8 > var2.stackSize)
            {
                var3 = var2.stackSize;
            }
            else
            {
                var3 = 8;
            }

            ItemStack var4 = new ItemStack(var2.itemID, var3, var2.getItemDamage());
            FCUtilsInventory.CopyEnchantments(var4, var2);
            int var5 = this.xCoord;
            int var6 = this.yCoord - 1;
            int var7 = this.zCoord;
            boolean var8 = false;
            int var12;

            if (this.worldObj.isAirBlock(var5, var6, var7))
            {
                var8 = true;
            }
            else
            {
                int var9 = this.worldObj.getBlockId(var5, var6, var7);

                if (FCUtilsWorld.IsReplaceableBlock(this.worldObj, var5, var6, var7))
                {
                    var8 = true;
                }
                else
                {
                    Block var10 = Block.blocksList[var9];

                    if (!var10.blockMaterial.isSolid())
                    {
                        var8 = true;
                    }
                    else
                    {
                        TileEntity var11 = this.worldObj.getBlockTileEntity(var5, var6, var7);
                        var12 = 0;

                        if (var11 != null && var11 instanceof IInventory && var9 != Block.brewingStand.blockID)
                        {
                            byte var13 = 0;
                            int var14 = ((IInventory)var11).getSizeInventory() - 1;
                            boolean var15 = true;

                            if (var9 != Block.stoneOvenIdle.blockID && var9 != Block.stoneOvenActive.blockID)
                            {
                                if (var9 == mod_FCBetterThanWolves.fcHopper.blockID)
                                {
                                    var14 = 17;
                                    short var16 = ((FCTileEntityHopper)var11).m_sFilterType;

                                    if (var16 > 0)
                                    {
                                        var15 = false;
                                    }
                                }
                            }
                            else
                            {
                                var14 = 0;
                            }

                            if (var15)
                            {
                                if (!FCUtilsInventory.AddItemStackToInventoryInSlotRange((IInventory)var11, var4, var13, var14))
                                {
                                    if (var9 == Block.chest.blockID)
                                    {
                                        for (int var23 = 2; var23 <= 5; ++var23)
                                        {
                                            FCUtilsBlockPos var17 = new FCUtilsBlockPos(var23);
                                            int var18 = this.worldObj.getBlockId(var5 + var17.i, var6 + var17.j, var7 + var17.k);

                                            if (var18 == Block.chest.blockID)
                                            {
                                                var11 = this.worldObj.getBlockTileEntity(var5 + var17.i, var6 + var17.j, var7 + var17.k);

                                                if (var11 != null && var11 instanceof IInventory)
                                                {
                                                    if (FCUtilsInventory.AddItemStackToInventory((IInventory)var11, var4))
                                                    {
                                                        var12 = var3;
                                                    }
                                                    else
                                                    {
                                                        var12 = var3 - var4.stackSize;
                                                    }
                                                }

                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        var12 = var3 - var4.stackSize;
                                    }
                                }
                                else
                                {
                                    var12 = var3;
                                }

                                if (var12 > 0)
                                {
                                    this.decrStackSize(var1, var12);
                                    this.worldObj.playAuxSFX(2231, this.xCoord, this.yCoord, this.zCoord, 0);
                                }
                            }
                            else
                            {
                                this.bHopperEjectBlocked = true;
                            }
                        }
                        else
                        {
                            this.bHopperEjectBlocked = true;
                        }
                    }
                }
            }

            if (var8)
            {
                List var19 = this.worldObj.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord + 0.4F), (double)((float)this.yCoord - 0.5F), (double)((float)this.zCoord + 0.4F), (double)((float)this.xCoord + 0.6F), (double)this.yCoord, (double)((float)this.zCoord + 0.6F)));

                if (var19 != null && var19.size() > 0)
                {
                    for (int var20 = 0; var20 < var19.size(); ++var20)
                    {
                        EntityMinecart var21 = (EntityMinecart)var19.get(var20);

                        if (var21.minecartType == 1 && var21.boundingBox.intersectsWith(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord), (double)((float)this.yCoord - 0.5F), (double)((float)this.zCoord), (double)((float)this.xCoord + 0.25F), (double)this.yCoord, (double)((float)this.zCoord + 1.0F))) && var21.boundingBox.intersectsWith(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord + 0.75F), (double)((float)this.yCoord - 0.5F), (double)((float)this.zCoord), (double)((float)this.xCoord + 1.0F), (double)this.yCoord, (double)((float)this.zCoord + 1.0F))) && var21.boundingBox.intersectsWith(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord), (double)((float)this.yCoord - 0.5F), (double)((float)this.zCoord), (double)((float)this.xCoord + 1.0F), (double)this.yCoord, (double)((float)this.zCoord + 0.25F))) && var21.boundingBox.intersectsWith(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord), (double)((float)this.yCoord - 0.5F), (double)((float)this.zCoord + 0.75F), (double)((float)this.xCoord + 1.0F), (double)this.yCoord, (double)((float)this.zCoord + 1.0F))))
                        {
                            boolean var22 = false;

                            if (FCUtilsInventory.AddItemStackToInventory(var21, var4))
                            {
                                var12 = var3;
                            }
                            else
                            {
                                var12 = var3 - var4.stackSize;
                            }

                            if (var12 > 0)
                            {
                                this.decrStackSize(var1, var12);
                                this.worldObj.playAuxSFX(2231, this.xCoord, this.yCoord, this.zCoord, 0);
                            }

                            var8 = false;
                            break;
                        }
                    }
                }
            }

            if (var8)
            {
                this.EjectStack(var4);
                this.decrStackSize(var1, var3);
            }
        }
    }

    private void EjectStack(ItemStack var1)
    {
        float var2 = this.worldObj.rand.nextFloat() * 0.1F + 0.45F;
        float var3 = -0.35F;
        float var4 = this.worldObj.rand.nextFloat() * 0.1F + 0.45F;
        EntityItem var5 = new EntityItem(this.worldObj, (double)((float)this.xCoord + var2), (double)((float)this.yCoord + var3), (double)((float)this.zCoord + var4), var1);
        var5.motionX = 0.0D;
        var5.motionY = -0.009999999776482582D;
        var5.motionZ = 0.0D;
        var5.delayBeforeCanPickup = 10;
        this.worldObj.spawnEntityInWorld(var5);
    }

    public void AttemptToEjectXPFromInv()
    {
        boolean var1 = true;

        if (this.iHopperContainedXPCount >= 20)
        {
            int var2 = this.xCoord;
            int var3 = this.yCoord - 1;
            int var4 = this.zCoord;
            boolean var5 = false;

            if (this.worldObj.isAirBlock(var2, var3, var4))
            {
                var5 = true;
            }
            else
            {
                int var6 = this.worldObj.getBlockId(var2, var3, var4);

                if (var6 == mod_FCBetterThanWolves.fcHopper.blockID)
                {
                    FCBlockHopper var7 = (FCBlockHopper)mod_FCBetterThanWolves.fcHopper;
                    FCTileEntityHopper var8 = (FCTileEntityHopper)this.worldObj.getBlockTileEntity(var2, var3, var4);

                    if (var8 != null)
                    {
                        short var9 = var8.m_sFilterType;

                        if (var9 == 6)
                        {
                            int var10 = 100 - var8.iHopperContainedXPCount;

                            if (var10 > 0)
                            {
                                if (this.m_iHopperXPDropDelayCount <= 0)
                                {
                                    int var11 = 20;

                                    if (var10 < var11)
                                    {
                                        var11 = var10;
                                    }

                                    var8.iHopperContainedXPCount += var11;
                                    this.iHopperContainedXPCount -= var11;
                                    this.worldObj.playAuxSFX(2232, this.xCoord, this.yCoord, this.zCoord, 0);
                                }
                                else
                                {
                                    var1 = false;
                                }
                            }
                        }
                    }
                }
                else if (FCUtilsWorld.IsReplaceableBlock(this.worldObj, var2, var3, var4))
                {
                    var5 = true;
                }
                else
                {
                    Block var12 = Block.blocksList[var6];

                    if (!var12.blockMaterial.isSolid())
                    {
                        var5 = true;
                    }
                }
            }

            if (var5)
            {
                if (this.m_iHopperXPDropDelayCount <= 0)
                {
                    this.EjectXPOrb(20);
                    this.iHopperContainedXPCount -= 20;
                }
                else
                {
                    var1 = false;
                }
            }
        }

        if (var1)
        {
            this.ResetXPEjectCount();
        }
        else
        {
            --this.m_iHopperXPDropDelayCount;
        }
    }

    private void ResetXPEjectCount()
    {
        this.m_iHopperXPDropDelayCount = 10 + this.worldObj.rand.nextInt(3);
    }

    private void EjectXPOrb(int var1)
    {
        float var2 = this.worldObj.rand.nextFloat() * 0.1F + 0.45F;
        float var3 = -0.1F;
        float var4 = this.worldObj.rand.nextFloat() * 0.1F + 0.45F;
        EntityXPOrb var5 = new EntityXPOrb(this.worldObj, (double)((float)this.xCoord + var2), (double)((float)this.yCoord + var3), (double)((float)this.zCoord + var4), var1);
        var5.motionX = 0.0D;
        var5.motionY = 0.0D;
        var5.motionZ = 0.0D;
        this.worldObj.spawnEntityInWorld(var5);
        this.worldObj.playAuxSFX(2230, this.xCoord, this.yCoord, this.zCoord, 0);
    }

    public void ResetContainedSoulCount()
    {
        this.iHopperContainedSoulCount = 0;
    }

    public void IncrementContainedSoulCount(int var1)
    {
        this.iHopperContainedSoulCount += var1;
    }

    private void HopperSoulOverload()
    {
        if (this.SpawnGhast())
        {
            this.worldObj.playAuxSFX(2225, this.xCoord, this.yCoord, this.zCoord, 0);
        }

        ((FCBlockHopper)((FCBlockHopper)mod_FCBetterThanWolves.fcHopper)).BreakHopper(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    private boolean SpawnGhast()
    {
        EntityGhast var1 = new EntityGhast(this.worldObj);

        for (int var2 = 0; var2 < 200; ++var2)
        {
            double var3 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 10.0D;
            double var5 = (double)(this.yCoord + this.worldObj.rand.nextInt(21) - 10);
            double var7 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 10.0D;
            var1.setLocationAndAngles(var3, var5, var7, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);

            if (var1.getCanSpawnHere())
            {
                this.worldObj.spawnEntityInWorld(var1);
                return true;
            }
        }

        return false;
    }

    public boolean AttemptToSwallowXPOrb(World var1, int var2, int var3, int var4, EntityXPOrb var5)
    {
        int var6 = 100 - this.iHopperContainedXPCount;

        if (var6 > 0)
        {
            if (var5.xpValue <= var6)
            {
                this.iHopperContainedXPCount += var5.xpValue;
                var5.setDead();
                return true;
            }

            var5.xpValue -= var6;
            this.iHopperContainedXPCount = 100;
        }

        return false;
    }
}
