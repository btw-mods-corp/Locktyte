package net.minecraft.src;

public class FCTileEntityCrucible extends FCTileEntityCookingVessel
{
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.m_iCookCounter = var1.getInteger("m_iCrucibleCookCounter");

        if (var1.hasKey("m_iStokedCooldownCounter"))
        {
            this.m_iStokedCooldownCounter = var1.getInteger("m_iStokedCooldownCounter");
        }

        if (var1.hasKey("m_bContainsValidIngrediantsForState"))
        {
            this.m_bContainsValidIngrediantsForState = var1.getBoolean("m_bContainsValidIngrediantsForState");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("m_iCrucibleCookCounter", this.m_iCookCounter);
        var1.setInteger("m_iStokedCooldownCounter", this.m_iStokedCooldownCounter);
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "Crucible";
    }

    public void ValidateContentsForState()
    {
        this.m_bContainsValidIngrediantsForState = false;

        if (this.m_iFireUnderType == 1)
        {
            if (FCCraftingManagerCrucible.getInstance().GetCraftingResult(this) != null)
            {
                this.m_bContainsValidIngrediantsForState = true;
            }
        }
        else if (this.m_iFireUnderType == 2)
        {
            if (FCUtilsInventory.GetFirstOccupiedStackOfItem(this, Block.tnt.blockID) >= 0)
            {
                this.m_bContainsValidIngrediantsForState = true;
            }
            else if (FCUtilsInventory.GetFirstOccupiedStackOfItem(this, Item.gunpowder.shiftedIndex) >= 0)
            {
                this.m_bContainsValidIngrediantsForState = true;
            }
            else if (FCUtilsInventory.GetFirstOccupiedStackOfItem(this, mod_FCBetterThanWolves.fcHellfireDust.shiftedIndex) >= 0)
            {
                this.m_bContainsValidIngrediantsForState = true;
            }
            else if (FCCraftingManagerCrucibleStoked.getInstance().GetCraftingResult(this) != null)
            {
                this.m_bContainsValidIngrediantsForState = true;
            }
            else if (this.GetFirstStackThatContainsItemsDestroyedByStokedFire() >= 0)
            {
                this.m_bContainsValidIngrediantsForState = true;
            }
        }
    }

    protected FCCraftingManagerBulk GetCraftingManager(int var1)
    {
        return (FCCraftingManagerBulk)(var1 == 1 ? FCCraftingManagerCrucible.getInstance() : (var1 == 2 ? FCCraftingManagerCrucibleStoked.getInstance() : null));
    }

    protected boolean AttemptToCookStoked()
    {
        int var1 = this.GetFirstStackThatContainsItemsDestroyedByStokedFire();

        if (var1 >= 0)
        {
            this.decrStackSize(var1, 1);
            return true;
        }
        else
        {
            return super.AttemptToCookStoked();
        }
    }

    private int GetFirstStackThatContainsItemsDestroyedByStokedFire()
    {
        for (int var1 = 0; var1 < this.getSizeInventory(); ++var1)
        {
            if (this.getStackInSlot(var1) != null && this.IsItemDestroyedByStokedFire(this.getStackInSlot(var1).getItem().shiftedIndex))
            {
                return var1;
            }
        }

        return -1;
    }

    private boolean IsItemDestroyedByStokedFire(int var1)
    {
        return var1 < 256 ? Block.fire.CanBlockBeDestroyedByFire(var1) : var1 == Item.bow.shiftedIndex || var1 == Item.appleRed.shiftedIndex || var1 == Item.arrow.shiftedIndex || var1 == Item.swordWood.shiftedIndex || var1 == Item.shovelWood.shiftedIndex || var1 == Item.pickaxeWood.shiftedIndex || var1 == Item.axeWood.shiftedIndex || var1 == Item.stick.shiftedIndex || var1 == Item.bowlEmpty.shiftedIndex || var1 == Item.bowlSoup.shiftedIndex || var1 == Item.silk.shiftedIndex || var1 == Item.feather.shiftedIndex || var1 == Item.hoeWood.shiftedIndex || var1 == Item.seeds.shiftedIndex || var1 == Item.wheat.shiftedIndex || var1 == Item.bread.shiftedIndex || var1 == Item.helmetLeather.shiftedIndex || var1 == Item.plateLeather.shiftedIndex || var1 == Item.legsLeather.shiftedIndex || var1 == Item.bootsLeather.shiftedIndex || var1 == Item.plateLeather.shiftedIndex || var1 == Item.porkRaw.shiftedIndex || var1 == Item.porkCooked.shiftedIndex || var1 == Item.painting.shiftedIndex || var1 == Item.sign.shiftedIndex || var1 == Item.doorWood.shiftedIndex || var1 == Item.saddle.shiftedIndex || var1 == Item.snowball.shiftedIndex || var1 == Item.boat.shiftedIndex || var1 == Item.leather.shiftedIndex || var1 == Item.reed.shiftedIndex || var1 == Item.paper.shiftedIndex || var1 == Item.book.shiftedIndex || var1 == Item.egg.shiftedIndex || var1 == Item.fishingRod.shiftedIndex || var1 == Item.egg.shiftedIndex || var1 == Item.fishRaw.shiftedIndex || var1 == Item.fishCooked.shiftedIndex || var1 == Item.bone.shiftedIndex || var1 == Item.sugar.shiftedIndex || var1 == Item.cake.shiftedIndex || var1 == Item.bed.shiftedIndex || var1 == Item.cookie.shiftedIndex || var1 == Item.melon.shiftedIndex || var1 == Item.pumpkinSeeds.shiftedIndex || var1 == Item.melonSeeds.shiftedIndex || var1 == Item.beefRaw.shiftedIndex || var1 == Item.beefCooked.shiftedIndex || var1 == Item.chickenRaw.shiftedIndex || var1 == Item.chickenCooked.shiftedIndex || var1 == Item.rottenFlesh.shiftedIndex || var1 == Item.spiderEye.shiftedIndex || var1 == Item.fermentedSpiderEye.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWolfRaw.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWolfCooked.shiftedIndex || var1 == mod_FCBetterThanWolves.fcHempSeeds.shiftedIndex || var1 == mod_FCBetterThanWolves.fcHemp.shiftedIndex || var1 == mod_FCBetterThanWolves.fcGear.shiftedIndex || var1 == mod_FCBetterThanWolves.fcFlour.shiftedIndex || var1 == mod_FCBetterThanWolves.fcHempFibers.shiftedIndex || var1 == mod_FCBetterThanWolves.fcScouredLeather.shiftedIndex || var1 == mod_FCBetterThanWolves.fcDonut.shiftedIndex || var1 == mod_FCBetterThanWolves.fcRopeItem.shiftedIndex || var1 == mod_FCBetterThanWolves.fcRollersItem.shiftedIndex || var1 == mod_FCBetterThanWolves.fcDung.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWaterWheelItem.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWindMillBladeItem.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWindMillItem.shiftedIndex || var1 == mod_FCBetterThanWolves.fcHempCloth.shiftedIndex || var1 == mod_FCBetterThanWolves.fcGrate.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWicker.shiftedIndex || var1 == mod_FCBetterThanWolves.fcTannedLeather.shiftedIndex || var1 == mod_FCBetterThanWolves.fcStrap.shiftedIndex || var1 == mod_FCBetterThanWolves.fcBelt.shiftedIndex || var1 == mod_FCBetterThanWolves.fcFoulFood.shiftedIndex || var1 == mod_FCBetterThanWolves.fcWoodBlade.shiftedIndex || var1 == mod_FCBetterThanWolves.fcGlue.shiftedIndex || var1 == mod_FCBetterThanWolves.fcTallow.shiftedIndex || var1 == mod_FCBetterThanWolves.fcHaft.shiftedIndex || var1 == mod_FCBetterThanWolves.fcCompositeBow.shiftedIndex || var1 == mod_FCBetterThanWolves.fcPadding.shiftedIndex || var1 == mod_FCBetterThanWolves.fcHardBoiledEgg.shiftedIndex || var1 == mod_FCBetterThanWolves.fcSoap.shiftedIndex || var1 == mod_FCBetterThanWolves.fcSawDust.shiftedIndex || var1 == mod_FCBetterThanWolves.fcTannedLeatherHelm.shiftedIndex || var1 == mod_FCBetterThanWolves.fcTannedLeatherChest.shiftedIndex || var1 == mod_FCBetterThanWolves.fcTannedLeatherLeggings.shiftedIndex || var1 == mod_FCBetterThanWolves.fcTannedLeatherBoots.shiftedIndex || var1 == mod_FCBetterThanWolves.fcBreedingHarness.shiftedIndex || var1 == mod_FCBetterThanWolves.fcSoulDust.shiftedIndex || var1 == mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex;
    }
}
