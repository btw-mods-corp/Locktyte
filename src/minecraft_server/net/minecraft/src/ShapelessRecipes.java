package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipes implements IRecipe
{
    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;

    /** Is a List of ItemStack that composes the recipe. */
    private final List recipeItems;

    public ShapelessRecipes(ItemStack par1ItemStack, List par2List)
    {
        this.recipeOutput = par1ItemStack;
        this.recipeItems = par2List;
    }

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting)
    {
        ArrayList var2 = new ArrayList(this.recipeItems);

        for (int var3 = 0; var3 < 4; ++var3)
        {
            for (int var4 = 0; var4 < 4; ++var4)
            {
                ItemStack var5 = par1InventoryCrafting.getStackInRowAndColumn(var4, var3);

                if (var5 != null && var5.itemID != mod_FCBetterThanWolves.fcItemMould.shiftedIndex)
                {
                    boolean var6 = false;
                    Iterator var7 = var2.iterator();

                    while (var7.hasNext())
                    {
                        ItemStack var8 = (ItemStack)var7.next();

                        if (var5.itemID == var8.itemID && (var8.getItemDamage() == -1 || var5.getItemDamage() == var8.getItemDamage()))
                        {
                            var6 = true;
                            var2.remove(var8);
                            break;
                        }
                    }

                    if (!var6)
                    {
                        return false;
                    }
                }
            }
        }

        return var2.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        return this.recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeItems.size();
    }

    public boolean matches(IRecipe var1)
    {
        if (var1 instanceof ShapelessRecipes)
        {
            ShapelessRecipes var2 = (ShapelessRecipes)var1;

            if (this.recipeOutput.getItem().shiftedIndex == var2.recipeOutput.getItem().shiftedIndex && this.recipeOutput.stackSize == var2.recipeOutput.stackSize && this.recipeOutput.getItemDamage() == var2.recipeOutput.getItemDamage() && this.recipeItems.size() == var2.recipeItems.size())
            {
                for (int var3 = 0; var3 < this.recipeItems.size(); ++var3)
                {
                    ItemStack var4 = (ItemStack)this.recipeItems.get(var3);
                    ItemStack var5 = (ItemStack)var2.recipeItems.get(var3);

                    if (var4 != null && var5 != null)
                    {
                        if (var4.getItem().shiftedIndex != var5.getItem().shiftedIndex || var4.stackSize != var5.stackSize || var4.getItemDamage() != var5.getItemDamage())
                        {
                            return false;
                        }
                    }
                    else if (var4 != null || var5 != null)
                    {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }
}
