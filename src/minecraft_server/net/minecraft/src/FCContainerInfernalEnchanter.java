package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class FCContainerInfernalEnchanter extends Container
{
    public IInventory m_tableInventory;
    private World m_localWorld;
    private int m_iBlockI;
    private int m_iBlockJ;
    private int m_iBlockK;
    private static final double m_dMaxInteractionDistance = 8.0D;
    private static final double m_dMaxInteractionDistanceSq = 64.0D;
    private static final int m_iSlotScreenWidth = 18;
    private static final int m_iSlotScreenHeight = 18;
    private static final int m_iScrollSlotScreenPosX = 17;
    private static final int m_iScrollSlotScreenPosY = 37;
    private static final int m_iItemSlotScreenPosX = 17;
    private static final int m_iItemSlotScreenPosY = 75;
    private static final int m_iPlayerInventoryScreenPosX = 8;
    private static final int m_iPlayerInventoryScreenPosY = 129;
    private static final int m_iPlayerHotbarScreenPosY = 187;
    private static final int m_iHorizontalBookShelfCheckDistance = 8;
    private static final int m_iVerticalPositiveBookShelfCheckDistance = 8;
    private static final int m_iVerticalNegativeBookShelfCheckDistance = 8;
    public static final int m_iMaxEnchantmentPowerLevel = 5;
    public int[] m_CurrentEnchantmentLevels;
    public int m_iMaxSurroundingBookshelfLevel;
    public int m_iLastMaxSurroundingBookshelfLevel;
    public long m_lNameSeed;
    Random rand;

    public FCContainerInfernalEnchanter(InventoryPlayer var1, World var2, int var3, int var4, int var5)
    {
        this.m_localWorld = var2;
        this.m_iBlockI = var3;
        this.m_iBlockJ = var4;
        this.m_iBlockK = var5;
        this.rand = new Random();
        this.m_lNameSeed = this.rand.nextLong();
        this.m_tableInventory = new FCInventoryInfernalEnchanter(this, "fcInfernalEnchanterInv", 2);
        this.m_CurrentEnchantmentLevels = new int[5];
        this.ResetEnchantingLevels();
        this.m_iMaxSurroundingBookshelfLevel = 0;
        this.m_iLastMaxSurroundingBookshelfLevel = 0;
        this.addSlotToContainer(new Slot(this.m_tableInventory, 0, 17, 37));
        this.addSlotToContainer(new Slot(this.m_tableInventory, 1, 17, 75));
        int var6;

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (int var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(var1, var7 + var6 * 9 + 9, 8 + var7 * 18, 129 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(var1, var6, 8 + var6 * 18, 187));
        }

        if (var2 != null && !var2.isRemote)
        {
            this.CheckForSurroundingBookshelves();
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory var1)
    {
        if (var1 == this.m_tableInventory)
        {
            this.m_lNameSeed = this.rand.nextLong();
            this.ResetEnchantingLevels();
            this.ComputeCurrentEnchantmentLevels();
            this.updateCraftingResults();
        }
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer var1)
    {
        super.onCraftGuiClosed(var1);

        if (this.m_localWorld != null && !this.m_localWorld.isRemote)
        {
            for (int var2 = 0; var2 < this.m_tableInventory.getSizeInventory(); ++var2)
            {
                ItemStack var3 = this.m_tableInventory.getStackInSlot(var2);

                if (var3 != null)
                {
                    var1.dropPlayerItem(var3);
                }
            }
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.m_localWorld != null && !this.m_localWorld.isRemote ? (this.m_localWorld.getBlockId(this.m_iBlockI, this.m_iBlockJ, this.m_iBlockK) != mod_FCBetterThanWolves.fcInfernalEnchanter.blockID ? false : var1.getDistanceSq((double)this.m_iBlockI + 0.5D, (double)this.m_iBlockJ + 0.5D, (double)this.m_iBlockK + 0.5D) <= 64.0D) : true;
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

            if (var1 <= 1)
            {
                if (!this.mergeItemStack(var4, 2, 38, false))
                {
                    return null;
                }
            }
            else if (var4.getItem().shiftedIndex == mod_FCBetterThanWolves.fcArcaneScroll.shiftedIndex)
            {
                if (!this.mergeItemStack(var4, 0, 1, false))
                {
                    return null;
                }
            }
            else if (this.GetMaximumEnchantmentCost(var4) > 0)
            {
                if (!this.mergeItemStack(var4, 1, 2, false))
                {
                    return null;
                }
            }
            else if (var1 >= 2 && var1 < 29)
            {
                if (!this.mergeItemStack(var4, 29, 38, false))
                {
                    return null;
                }
            }
            else if (var1 >= 29 && var1 < 38 && !this.mergeItemStack(var4, 2, 29, false))
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

    private void CheckForSurroundingBookshelves()
    {
        int var1 = 0;

        for (int var2 = this.m_iBlockI - 8; var2 <= this.m_iBlockI + 8; ++var2)
        {
            for (int var3 = this.m_iBlockJ - 8; var3 <= this.m_iBlockJ + 8; ++var3)
            {
                for (int var4 = this.m_iBlockK - 8; var4 <= this.m_iBlockK + 8; ++var4)
                {
                    if (this.IsValidBookshelf(var2, var3, var4))
                    {
                        ++var1;
                    }
                }
            }
        }

        this.m_iMaxSurroundingBookshelfLevel = var1;
    }

    private boolean IsValidBookshelf(int var1, int var2, int var3)
    {
        int var4 = this.m_localWorld.getBlockId(var1, var2, var3);
        return var4 == Block.bookShelf.blockID && (this.m_localWorld.isAirBlock(var1 + 1, var2, var3) || this.m_localWorld.isAirBlock(var1 - 1, var2, var3) || this.m_localWorld.isAirBlock(var1, var2, var3 + 1) || this.m_localWorld.isAirBlock(var1, var2, var3 - 1));
    }

    private void SetCurrentEnchantingLevels(int var1, int var2, int var3)
    {
        this.ResetEnchantingLevels();

        if (var1 == 1)
        {
            this.m_CurrentEnchantmentLevels[0] = 50;
        }
        else if (var1 == 2)
        {
            this.m_CurrentEnchantmentLevels[0] = 25;
            this.m_CurrentEnchantmentLevels[1] = 50;
        }
        else if (var1 == 3)
        {
            this.m_CurrentEnchantmentLevels[0] = 16;
            this.m_CurrentEnchantmentLevels[1] = 33;
            this.m_CurrentEnchantmentLevels[2] = 50;
        }
        else if (var1 == 4)
        {
            this.m_CurrentEnchantmentLevels[0] = 12;
            this.m_CurrentEnchantmentLevels[1] = 25;
            this.m_CurrentEnchantmentLevels[2] = 37;
            this.m_CurrentEnchantmentLevels[3] = 50;
        }
        else if (var1 == 5)
        {
            this.m_CurrentEnchantmentLevels[0] = 10;
            this.m_CurrentEnchantmentLevels[1] = 20;
            this.m_CurrentEnchantmentLevels[2] = 30;
            this.m_CurrentEnchantmentLevels[3] = 40;
            this.m_CurrentEnchantmentLevels[4] = 50;
        }

        int var4 = (var2 - 1) * 50;

        for (int var5 = 0; var5 < 5; ++var5)
        {
            if (this.m_CurrentEnchantmentLevels[var5] > 0)
            {
                if (var3 < this.m_CurrentEnchantmentLevels[var5])
                {
                    this.m_CurrentEnchantmentLevels[var5] = 0;
                }
                else
                {
                    this.m_CurrentEnchantmentLevels[var5] += var4;
                }
            }
        }
    }

    private void ResetEnchantingLevels()
    {
        for (int var1 = 0; var1 < 5; ++var1)
        {
            this.m_CurrentEnchantmentLevels[var1] = 0;
        }
    }

    private void ComputeCurrentEnchantmentLevels()
    {
        ItemStack var1 = this.m_tableInventory.getStackInSlot(0);

        if (var1 != null && var1.getItem().shiftedIndex == mod_FCBetterThanWolves.fcArcaneScroll.shiftedIndex)
        {
            ItemStack var2 = this.m_tableInventory.getStackInSlot(1);

            if (var2 != null)
            {
                int var3 = this.GetMaximumEnchantmentCost(var2);

                if (var3 > 0)
                {
                    int var4 = var1.getItemDamage();

                    if (this.IsEnchantmentAppropriateForItem(var4, var2) && !this.DoesEnchantmentConflictWithExistingOnes(var4, var2))
                    {
                        int var5 = this.GetMaximumNumberOfEnchantments(var2);
                        int var6 = 0;
                        NBTTagList var7 = var2.getEnchantmentTagList();

                        if (var7 != null)
                        {
                            var6 = var2.getEnchantmentTagList().tagCount();
                        }

                        if (var6 < var5)
                        {
                            this.SetCurrentEnchantingLevels(this.GetMaxEnchantmentPowerLevel(var4, var2), var6 + 1, this.GetMaximumEnchantmentCost(var2));
                        }
                    }
                }
            }
        }
    }

    private int GetMaximumEnchantmentCost(ItemStack var1)
    {
        Item var2 = var1.getItem();
        int var3 = var2.shiftedIndex;
        return var3 != Item.axeStone.shiftedIndex && var3 != Item.pickaxeStone.shiftedIndex && var3 != Item.shovelStone.shiftedIndex && var3 != Item.swordStone.shiftedIndex ? (var3 != Item.axeWood.shiftedIndex && var3 != Item.pickaxeWood.shiftedIndex && var3 != Item.shovelWood.shiftedIndex && var3 != Item.swordWood.shiftedIndex ? (var3 != Item.axeSteel.shiftedIndex && var3 != Item.pickaxeSteel.shiftedIndex && var3 != Item.shovelSteel.shiftedIndex && var3 != Item.swordSteel.shiftedIndex ? (var3 != Item.axeDiamond.shiftedIndex && var3 != Item.pickaxeDiamond.shiftedIndex && var3 != Item.shovelDiamond.shiftedIndex && var3 != Item.swordDiamond.shiftedIndex ? (var3 != Item.axeGold.shiftedIndex && var3 != Item.pickaxeGold.shiftedIndex && var3 != Item.shovelGold.shiftedIndex && var3 != Item.swordGold.shiftedIndex ? (var3 != mod_FCBetterThanWolves.fcRefinedAxe.shiftedIndex && var3 != mod_FCBetterThanWolves.fcRefinedPickAxe.shiftedIndex && var3 != mod_FCBetterThanWolves.fcRefinedShovel.shiftedIndex && var3 != mod_FCBetterThanWolves.fcRefinedSword.shiftedIndex && var3 != mod_FCBetterThanWolves.fcBattleAxe.shiftedIndex && var3 != mod_FCBetterThanWolves.fcMattock.shiftedIndex ? (var3 != Item.helmetLeather.shiftedIndex && var3 != Item.plateLeather.shiftedIndex && var3 != Item.legsLeather.shiftedIndex && var3 != Item.bootsLeather.shiftedIndex ? (var3 != Item.helmetSteel.shiftedIndex && var3 != Item.plateSteel.shiftedIndex && var3 != Item.legsSteel.shiftedIndex && var3 != Item.bootsSteel.shiftedIndex ? (var3 != Item.helmetDiamond.shiftedIndex && var3 != Item.plateDiamond.shiftedIndex && var3 != Item.legsDiamond.shiftedIndex && var3 != Item.bootsDiamond.shiftedIndex ? (var3 != Item.helmetGold.shiftedIndex && var3 != Item.plateGold.shiftedIndex && var3 != Item.legsGold.shiftedIndex && var3 != Item.bootsGold.shiftedIndex ? (var3 != mod_FCBetterThanWolves.fcPlateHelm.shiftedIndex && var3 != mod_FCBetterThanWolves.fcPlateBreastPlate.shiftedIndex && var3 != mod_FCBetterThanWolves.fcPlateLeggings.shiftedIndex && var3 != mod_FCBetterThanWolves.fcPlateBoots.shiftedIndex ? (var2 instanceof ItemBow ? 50 : 0) : 50) : 50) : 50) : 30) : 15) : 50) : 50) : 50) : 30) : 30) : 15;
    }

    private int GetMaximumNumberOfEnchantments(ItemStack var1)
    {
        Item var2 = var1.getItem();
        int var3 = var2.shiftedIndex;
        return var3 != Item.axeStone.shiftedIndex && var3 != Item.pickaxeStone.shiftedIndex && var3 != Item.shovelStone.shiftedIndex && var3 != Item.swordStone.shiftedIndex ? (var3 != Item.axeWood.shiftedIndex && var3 != Item.pickaxeWood.shiftedIndex && var3 != Item.shovelWood.shiftedIndex && var3 != Item.swordWood.shiftedIndex ? (var3 != Item.axeSteel.shiftedIndex && var3 != Item.pickaxeSteel.shiftedIndex && var3 != Item.shovelSteel.shiftedIndex && var3 != Item.swordSteel.shiftedIndex ? (var3 != Item.axeDiamond.shiftedIndex && var3 != Item.pickaxeDiamond.shiftedIndex && var3 != Item.shovelDiamond.shiftedIndex && var3 != Item.swordDiamond.shiftedIndex ? (var3 != Item.axeGold.shiftedIndex && var3 != Item.pickaxeGold.shiftedIndex && var3 != Item.shovelGold.shiftedIndex && var3 != Item.swordGold.shiftedIndex ? (var3 != mod_FCBetterThanWolves.fcRefinedAxe.shiftedIndex && var3 != mod_FCBetterThanWolves.fcRefinedPickAxe.shiftedIndex && var3 != mod_FCBetterThanWolves.fcRefinedShovel.shiftedIndex && var3 != mod_FCBetterThanWolves.fcRefinedSword.shiftedIndex && var3 != mod_FCBetterThanWolves.fcBattleAxe.shiftedIndex && var3 != mod_FCBetterThanWolves.fcMattock.shiftedIndex ? (var3 != Item.helmetLeather.shiftedIndex && var3 != Item.plateLeather.shiftedIndex && var3 != Item.legsLeather.shiftedIndex && var3 != Item.bootsLeather.shiftedIndex ? (var3 != Item.helmetSteel.shiftedIndex && var3 != Item.plateSteel.shiftedIndex && var3 != Item.legsSteel.shiftedIndex && var3 != Item.bootsSteel.shiftedIndex ? (var3 != Item.helmetDiamond.shiftedIndex && var3 != Item.plateDiamond.shiftedIndex && var3 != Item.legsDiamond.shiftedIndex && var3 != Item.bootsDiamond.shiftedIndex ? (var3 != Item.helmetGold.shiftedIndex && var3 != Item.plateGold.shiftedIndex && var3 != Item.legsGold.shiftedIndex && var3 != Item.bootsGold.shiftedIndex ? (var3 != mod_FCBetterThanWolves.fcPlateHelm.shiftedIndex && var3 != mod_FCBetterThanWolves.fcPlateBreastPlate.shiftedIndex && var3 != mod_FCBetterThanWolves.fcPlateLeggings.shiftedIndex && var3 != mod_FCBetterThanWolves.fcPlateBoots.shiftedIndex ? (var2 instanceof ItemBow ? 3 : 0) : 4) : 3) : 2) : 2) : 2) : 4) : 3) : 2) : 2) : 2) : 1;
    }

    private boolean IsEnchantmentAppropriateForItem(int var1, ItemStack var2)
    {
        return Enchantment.enchantmentsList[var1].type.canEnchantItem(var2.getItem());
    }

    private boolean DoesEnchantmentConflictWithExistingOnes(int var1, ItemStack var2)
    {
        NBTTagList var3 = var2.getEnchantmentTagList();

        if (var3 != null)
        {
            int var4 = var2.getEnchantmentTagList().tagCount();

            for (int var5 = 0; var5 < var4; ++var5)
            {
                short var6 = ((NBTTagCompound)var3.tagAt(var5)).getShort("id");

                if (var6 == var1)
                {
                    return true;
                }

                if (var1 == Enchantment.silkTouch.effectId && var6 == Enchantment.fortune.effectId || var1 == Enchantment.fortune.effectId && var6 == Enchantment.silkTouch.effectId)
                {
                    return true;
                }
            }
        }

        return false;
    }

    private int GetMaxEnchantmentPowerLevel(int var1, ItemStack var2)
    {
        return var1 == Enchantment.respiration.effectId && var2.getItem().shiftedIndex == mod_FCBetterThanWolves.fcPlateHelm.shiftedIndex ? 5 : Enchantment.enchantmentsList[var1].getMaxLevel();
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean enchantItem(EntityPlayer var1, int var2)
    {
        if (this.m_localWorld != null && !this.m_localWorld.isRemote)
        {
            int var3 = this.m_CurrentEnchantmentLevels[var2];

            if (var3 > 0)
            {
                boolean var4 = var3 <= var1.experienceLevel && var3 <= this.m_iMaxSurroundingBookshelfLevel;

                if (var4)
                {
                    ItemStack var5 = this.m_tableInventory.getStackInSlot(0);

                    if (var5 != null && var5.getItem().shiftedIndex == mod_FCBetterThanWolves.fcArcaneScroll.shiftedIndex)
                    {
                        ItemStack var6 = this.m_tableInventory.getStackInSlot(1);

                        if (var6 != null)
                        {
                            int var7 = var5.getItemDamage();
                            var6.addEnchantment(Enchantment.enchantmentsList[var7], var2 + 1);
                            var1.removeExperience(var3);
                            this.m_tableInventory.decrStackSize(0, 1);
                            this.onCraftMatrixChanged(this.m_tableInventory);
                            this.m_localWorld.playSoundAtEntity(var1, "ambient.weather.thunder", 1.0F, this.m_localWorld.rand.nextFloat() * 0.4F + 0.8F);
                            return true;
                        }
                    }
                }
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    public void onCraftGuiOpened(ICrafting var1)
    {
        super.onCraftGuiOpened(var1);
        var1.updateCraftingInventoryInfo(this, 0, this.m_iMaxSurroundingBookshelfLevel);
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

            if (this.m_iLastMaxSurroundingBookshelfLevel != this.m_iMaxSurroundingBookshelfLevel)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.m_iMaxSurroundingBookshelfLevel);
            }
        }

        this.m_iLastMaxSurroundingBookshelfLevel = this.m_iMaxSurroundingBookshelfLevel;
    }
}
