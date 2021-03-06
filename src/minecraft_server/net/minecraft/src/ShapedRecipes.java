package net.minecraft.src;

public class ShapedRecipes implements IRecipe
{
    /** How many horizontal slots this recipe is wide. */
    private int recipeWidth;

    /** How many vertical slots this recipe uses. */
    private int recipeHeight;

    /** Is a array of ItemStack that composes the recipe. */
    private ItemStack[] recipeItems;

    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;

    /** Is the itemID of the output item that you get when craft the recipe. */
    public final int recipeOutputItemID;

    public ShapedRecipes(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
    {
        this.recipeOutputItemID = par4ItemStack.itemID;
        this.recipeWidth = par1;
        this.recipeHeight = par2;
        this.recipeItems = par3ArrayOfItemStack;
        this.recipeOutput = par4ItemStack;
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
        for (int var2 = 0; var2 <= 4 - this.recipeWidth; ++var2)
        {
            for (int var3 = 0; var3 <= 4 - this.recipeHeight; ++var3)
            {
                if (this.checkMatch(par1InventoryCrafting, var2, var3, true))
                {
                    return true;
                }

                if (this.checkMatch(par1InventoryCrafting, var2, var3, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4)
    {
        for (int var5 = 0; var5 < 4; ++var5)
        {
            for (int var6 = 0; var6 < 4; ++var6)
            {
                int var7 = var5 - par2;
                int var8 = var6 - par3;
                ItemStack var9 = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight)
                {
                    if (par4)
                    {
                        var9 = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
                    }
                    else
                    {
                        var9 = this.recipeItems[var7 + var8 * this.recipeWidth];
                    }
                }

                ItemStack var10 = par1InventoryCrafting.getStackInRowAndColumn(var5, var6);

                if (var10 != null && var10.itemID == mod_FCBetterThanWolves.fcItemMould.shiftedIndex)
                {
                    var10 = null;
                }

                if (var10 != null || var9 != null)
                {
                    if (var10 == null && var9 != null || var10 != null && var9 == null)
                    {
                        return false;
                    }

                    if (var9.itemID != var10.itemID)
                    {
                        return false;
                    }

                    if (var9.getItemDamage() != -1 && var9.getItemDamage() != var10.getItemDamage())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        return new ItemStack(this.recipeOutput.itemID, this.recipeOutput.stackSize, this.recipeOutput.getItemDamage());
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }

    public boolean matches(IRecipe var1)
    {
        if (var1 instanceof ShapedRecipes)
        {
            ShapedRecipes var2 = (ShapedRecipes)var1;

            if (this.recipeWidth == var2.recipeWidth && this.recipeHeight == var2.recipeHeight && this.recipeOutputItemID == var2.recipeOutputItemID && this.recipeOutput.stackSize == var2.recipeOutput.stackSize && this.recipeOutput.getItemDamage() == var2.recipeOutput.getItemDamage() && this.recipeItems.length == var2.recipeItems.length)
            {
                for (int var3 = 0; var3 < this.recipeItems.length; ++var3)
                {
                    ItemStack var4 = this.recipeItems[var3];
                    ItemStack var5 = var2.recipeItems[var3];

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
