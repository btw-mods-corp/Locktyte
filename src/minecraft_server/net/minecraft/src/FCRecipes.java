package net.minecraft.src;

public abstract class FCRecipes
{
    public static void AddAllModRecipes()
    {
        AddBlockRecipes();
        AddItemRecipes();
        AddDyeRecipes();
        AddAlternateVanillaRecipes();
        AddConversionRecipes();
        AddSmeltingRecipes();
        AddAnvilRecipes();
        AddCauldronRecipes();
        AddCrucibleRecipes();
        AddMillStoneRecipes();
        AddTuningForkRecipes();
        RemoveVanillaRecipes();
        AddDebugRecipes();
    }

    public static void AddVanillaRecipe(ItemStack var0, Object[] var1)
    {
        CraftingManager.getInstance().addRecipe(var0, var1);
    }

    public static void AddShapelessVanillaRecipe(ItemStack var0, Object[] var1)
    {
        CraftingManager.getInstance().addShapelessRecipe(var0, var1);
    }

    public static void RemoveVanillaRecipe(ItemStack var0, Object[] var1)
    {
        CraftingManager.getInstance().RemoveRecipe(var0, var1);
    }

    public static void RemoveShapelessVanillaRecipe(ItemStack var0, Object[] var1)
    {
        CraftingManager.getInstance().RemoveShapelessRecipe(var0, var1);
    }

    public static void AddAnvilRecipe(ItemStack var0, Object[] var1)
    {
        FCCraftingManagerAnvil.getInstance().addRecipe(var0, var1);
    }

    public static void AddShapelessAnvilRecipe(ItemStack var0, Object[] var1)
    {
        FCCraftingManagerAnvil.getInstance().addShapelessRecipe(var0, var1);
    }

    public static void RemoveAnvilRecipe(ItemStack var0, Object[] var1)
    {
        FCCraftingManagerAnvil.getInstance().RemoveRecipe(var0, var1);
    }

    public static void RemoveShapelessAnvilRecipe(ItemStack var0, Object[] var1)
    {
        FCCraftingManagerAnvil.getInstance().RemoveShapelessRecipe(var0, var1);
    }

    public static void AddCauldronRecipe(ItemStack var0, ItemStack[] var1)
    {
        FCCraftingManagerCauldron.getInstance().AddRecipe(var0, var1);
    }

    public static void AddCauldronRecipe(ItemStack[] var0, ItemStack[] var1)
    {
        FCCraftingManagerCauldron.getInstance().AddRecipe(var0, var1);
    }

    public static void AddStokedCauldronRecipe(ItemStack var0, ItemStack[] var1)
    {
        FCCraftingManagerCauldronStoked.getInstance().AddRecipe(var0, var1);
    }

    public static void AddStokedCauldronRecipe(ItemStack[] var0, ItemStack[] var1)
    {
        FCCraftingManagerCauldronStoked.getInstance().AddRecipe(var0, var1);
    }

    public static void AddCrucibleRecipe(ItemStack var0, ItemStack[] var1)
    {
        FCCraftingManagerCrucible.getInstance().AddRecipe(var0, var1);
    }

    public static void AddCrucibleRecipe(ItemStack[] var0, ItemStack[] var1)
    {
        FCCraftingManagerCrucible.getInstance().AddRecipe(var0, var1);
    }

    public static void AddStokedCrucibleRecipe(ItemStack var0, ItemStack[] var1)
    {
        FCCraftingManagerCrucibleStoked.getInstance().AddRecipe(var0, var1);
    }

    public static void AddStokedCrucibleRecipe(ItemStack[] var0, ItemStack[] var1)
    {
        FCCraftingManagerCrucibleStoked.getInstance().AddRecipe(var0, var1);
    }

    public static void AddMillStoneRecipe(ItemStack var0, ItemStack[] var1)
    {
        FCCraftingManagerMillStone.getInstance().AddRecipe(var0, var1);
    }

    public static void AddMillStoneRecipe(ItemStack var0, ItemStack var1)
    {
        FCCraftingManagerMillStone.getInstance().AddRecipe(var0, var1);
    }

    public static void AddMillStoneRecipe(ItemStack[] var0, ItemStack[] var1)
    {
        FCCraftingManagerMillStone.getInstance().AddRecipe(var0, var1);
    }

    private static void AddBlockRecipes()
    {
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 0), new Object[] {"##", "##", '#', mod_FCBetterThanWolves.fcWicker});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 1), new Object[] {"###", "###", "###", '#', mod_FCBetterThanWolves.fcDung});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 3), new Object[] {"###", "###", "###", '#', mod_FCBetterThanWolves.fcConcentratedHellfire});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 4), new Object[] {"###", "###", "###", '#', mod_FCBetterThanWolves.fcPadding});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 5), new Object[] {"###", "###", "###", '#', mod_FCBetterThanWolves.fcSoap});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 6), new Object[] {"###", "###", "###", '#', mod_FCBetterThanWolves.fcRopeItem});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 7), new Object[] {"###", "###", "###", '#', Item.flint});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 4, 5), new Object[] {"##", '#', new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 0)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 4, 4), new Object[] {"###", " X ", " X ", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 4, 4), new Object[] {"###", " X ", " X ", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcMiningCharge, 2), new Object[] {"XYX", "###", "###", '#', mod_FCBetterThanWolves.fcDynamite, 'X', mod_FCBetterThanWolves.fcRopeItem, 'Y', mod_FCBetterThanWolves.fcGlue});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticVegetation, 1, 0), new Object[] {"###", '#', Block.vine});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcMiningCharge, 2), new Object[] {"XYX", "###", "###", '#', mod_FCBetterThanWolves.fcDynamite, 'X', mod_FCBetterThanWolves.fcRopeItem, 'Y', Item.slimeBall});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAnvil, 1), new Object[] {"###", " # ", "###", '#', mod_FCBetterThanWolves.fcSteel});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcLightBulbOff, 1), new Object[] {" # ", "#X#", " Y ", '#', Block.thinGlass, 'X', mod_FCBetterThanWolves.fcFilament, 'Y', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBBQ), new Object[] {"XXX", "#Z#", "#Y#", '#', Block.stone, 'X', mod_FCBetterThanWolves.fcConcentratedHellfire, 'Y', Item.redstone, 'Z', mod_FCBetterThanWolves.fcFilament});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHopper), new Object[] {"# #", "XYX", " Z ", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcGear, 'Y', Block.pressurePlatePlanks, 'Z', new ItemStack(mod_FCBetterThanWolves.fcCorner, 1, 0)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHopper), new Object[] {"# #", "XYX", " Z ", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcGear, 'Y', Block.pressurePlatePlanks, 'Z', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcSaw), new Object[] {"YYY", "XZX", "#X#", '#', Block.planks, 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.ingotIron, 'Z', mod_FCBetterThanWolves.fcBelt});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlatform), new Object[] {"#X#", " # ", "#X#", '#', Block.planks, 'X', mod_FCBetterThanWolves.fcWicker});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlatform), new Object[] {"X#X", " X ", "X#X", '#', mod_FCBetterThanWolves.fcWicker, 'X', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlatform), new Object[] {"X#X", " X ", "X#X", '#', mod_FCBetterThanWolves.fcWicker, 'X', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPulley), new Object[] {"#Y#", "XZX", "#Y#", '#', Block.planks, 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.ingotIron, 'Z', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPulley), new Object[] {"#Y#", "XZX", "#Y#", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.ingotIron, 'Z', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPulley), new Object[] {"#Y#", "XZX", "#Y#", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.ingotIron, 'Z', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCauldron), new Object[] {"#Y#", "#X#", "###", '#', Item.ingotIron, 'X', Item.bucketWater, 'Y', Item.bone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcDetectorRailWood, 6), new Object[] {"X X", "X#X", "XRX", 'X', Item.ingotIron, 'R', Item.redstone, '#', Block.pressurePlatePlanks});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcDetectorRailObsidian, 6), new Object[] {"X X", "X#X", "XRX", 'X', Item.ingotIron, 'R', Item.redstone, '#', mod_FCBetterThanWolves.fcPressurePlateObsidian});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHandCrank), new Object[] {"  Y", " Y ", "#X#", '#', Block.cobblestone, 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.stick});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcMillStone), new Object[] {"YYY", "YYY", "YXY", 'X', mod_FCBetterThanWolves.fcGear, 'Y', Block.stone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAxleBlock), new Object[] {"#X#", '#', Block.planks, 'X', mod_FCBetterThanWolves.fcRopeItem});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAxleBlock), new Object[] {"#X#", '#', mod_FCBetterThanWolves.fcMoulding, 'X', mod_FCBetterThanWolves.fcRopeItem});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAxleBlock), new Object[] {"#X#", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcRopeItem});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcGearBox), new Object[] {"#X#", "XYX", "#X#", '#', Block.planks, 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcGearBox), new Object[] {"#X#", "XYX", "#X#", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcGearBox), new Object[] {"#X#", "XYX", "#X#", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcGear, 'Y', Item.redstone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTurntable), new Object[] {"###", "ZXZ", "ZYZ", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', Item.redstone, 'Y', mod_FCBetterThanWolves.fcGear, 'Z', Block.stone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTurntable), new Object[] {"###", "ZXZ", "ZYZ", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', Item.redstone, 'Y', mod_FCBetterThanWolves.fcGear, 'Z', Block.stone});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBellows), new Object[] {"###", "XXX", "YZY", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcTannedLeather, 'Y', mod_FCBetterThanWolves.fcGear, 'Z', mod_FCBetterThanWolves.fcBelt});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBellows), new Object[] {"###", "XXX", "YZY", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcTannedLeather, 'Y', mod_FCBetterThanWolves.fcGear, 'Z', mod_FCBetterThanWolves.fcBelt});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlanter, 1, 2), new Object[] {"Y", "B", "#", '#', new ItemStack(mod_FCBetterThanWolves.fcPlanter, 1, 0), 'B', new ItemStack(Item.dyePowder, 1, 15), 'Y', Block.dirt});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlanter, 1, 8), new Object[] {"S", "P", 'P', new ItemStack(mod_FCBetterThanWolves.fcPlanter, 1, 0), 'S', Block.slowSand});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlanter, 1, 9), new Object[] {"Y", "B", "#", '#', new ItemStack(mod_FCBetterThanWolves.fcPlanter, 1, 0), 'B', new ItemStack(Item.dyePowder, 1, 15), 'Y', Block.grass});

        for (int var0 = 0; var0 < 16; ++var0)
        {
            AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 4, var0), new Object[] {"##", '#', new ItemStack(Block.cloth, 1, var0)});
        }

        AddVanillaRecipe(new ItemStack(Block.netherBrick, 1), new Object[] {"##", "##", '#', mod_FCBetterThanWolves.fcNetherBrick});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockDirtSlab, 4), new Object[] {"##", '#', new ItemStack(Block.dirt)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockScrewPump), new Object[] {"XGX", "WSW", "WgW", 'W', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'g', mod_FCBetterThanWolves.fcGear, 'S', mod_FCBetterThanWolves.fcItemScrew, 'G', mod_FCBetterThanWolves.fcGrate, 'X', mod_FCBetterThanWolves.fcGlue});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockScrewPump), new Object[] {"XGX", "WSW", "WgW", 'W', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'g', mod_FCBetterThanWolves.fcGear, 'S', mod_FCBetterThanWolves.fcItemScrew, 'G', mod_FCBetterThanWolves.fcGrate, 'X', mod_FCBetterThanWolves.fcGlue});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockScrewPump), new Object[] {"XGX", "WSW", "WgW", 'W', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'g', mod_FCBetterThanWolves.fcGear, 'S', mod_FCBetterThanWolves.fcItemScrew, 'G', mod_FCBetterThanWolves.fcGrate, 'X', Item.slimeBall});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockScrewPump), new Object[] {"XGX", "WSW", "WgW", 'W', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'g', mod_FCBetterThanWolves.fcGear, 'S', mod_FCBetterThanWolves.fcItemScrew, 'G', mod_FCBetterThanWolves.fcGrate, 'X', Item.slimeBall});
    }

    private static void AddItemRecipes()
    {
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcGear, 2), new Object[] {" X ", "X#X", " X ", '#', Block.planks, 'X', Item.stick});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcRopeItem, 1), new Object[] {"##", "##", "##", '#', mod_FCBetterThanWolves.fcHempFibers});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAnchor), new Object[] {" X ", "###", '#', Block.stone, 'X', Item.ingotIron});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWaterWheelItem), new Object[] {"###", "# #", "###", '#', mod_FCBetterThanWolves.fcWoodBlade});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWindMillBladeItem), new Object[] {"###", "###", "XXX", '#', mod_FCBetterThanWolves.fcHempCloth, 'X', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWindMillBladeItem), new Object[] {"###", "###", "XXX", '#', mod_FCBetterThanWolves.fcHempCloth, 'X', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWindMillBladeItem), new Object[] {"###", "###", "XXX", '#', mod_FCBetterThanWolves.fcHempCloth, 'X', Block.planks});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWindMillItem), new Object[] {" # ", "# #", " # ", '#', mod_FCBetterThanWolves.fcWindMillBladeItem});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHempCloth, 1), new Object[] {"###", "###", "###", '#', mod_FCBetterThanWolves.fcHempFibers});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcGrate, 1), new Object[] {"##", "##", '#', Item.stick});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWicker, 1), new Object[] {"##", "##", '#', Item.reed});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWicker, 4), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWicker, 2), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 1, 5)});
        AddShapelessVanillaRecipe(new ItemStack(Item.reed, 4), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcWicker, 1)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcDung, 9), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 1)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcConcentratedHellfire, 9), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 3)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPadding, 9), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 4)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcSoap, 9), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 5)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcRopeItem, 9), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 6)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcRollersItem, 1), new Object[] {"##", "##", '#', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcRollersItem, 1), new Object[] {"##", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcStrap, 8), new Object[] {"#", '#', mod_FCBetterThanWolves.fcTannedLeather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBelt, 1), new Object[] {" # ", "# #", " # ", '#', mod_FCBetterThanWolves.fcStrap});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoodBlade, 1), new Object[] {"#  ", "#X#", "#  ", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', Item.slimeBall});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoodBlade, 1), new Object[] {"#  ", "#X#", "#  ", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', Item.slimeBall});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoodBlade, 1), new Object[] {"#  ", "#X#", "#  ", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcGlue});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoodBlade, 1), new Object[] {"#  ", "#X#", "#  ", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcGlue});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHaft, 1), new Object[] {"Y", "X", "#", '#', mod_FCBetterThanWolves.fcMoulding, 'X', mod_FCBetterThanWolves.fcGlue, 'Y', mod_FCBetterThanWolves.fcStrap});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHaft, 1), new Object[] {"Y", "X", "#", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'X', mod_FCBetterThanWolves.fcGlue, 'Y', mod_FCBetterThanWolves.fcStrap});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHaft, 1), new Object[] {"Y", "X", "#", '#', mod_FCBetterThanWolves.fcMoulding, 'X', Item.slimeBall, 'Y', mod_FCBetterThanWolves.fcStrap});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHaft, 1), new Object[] {"Y", "X", "#", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'X', Item.slimeBall, 'Y', mod_FCBetterThanWolves.fcStrap});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCompositeBow, 1), new Object[] {"X#Y", "ZX#", "X#Y", '#', mod_FCBetterThanWolves.fcMoulding, 'X', Item.bone, 'Y', Item.slimeBall, 'Z', Item.silk});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCompositeBow, 1), new Object[] {"X#Y", "ZX#", "X#Y", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'X', Item.bone, 'Y', Item.slimeBall, 'Z', Item.silk});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCompositeBow, 1), new Object[] {"X#Y", "ZX#", "X#Y", '#', mod_FCBetterThanWolves.fcMoulding, 'X', Item.bone, 'Y', mod_FCBetterThanWolves.fcGlue, 'Z', Item.silk});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCompositeBow, 1), new Object[] {"X#Y", "ZX#", "X#Y", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'X', Item.bone, 'Y', mod_FCBetterThanWolves.fcGlue, 'Z', Item.silk});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBroadheadArrow, 4), new Object[] {"#", "X", "Y", '#', mod_FCBetterThanWolves.fcBroadheadArrowhead, 'X', mod_FCBetterThanWolves.fcMoulding, 'Y', Item.feather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBroadheadArrow, 4), new Object[] {"#", "X", "Y", '#', mod_FCBetterThanWolves.fcBroadheadArrowhead, 'X', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'Y', Item.feather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherHelm), new Object[] {"###", "# #", '#', mod_FCBetterThanWolves.fcTannedLeather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherChest), new Object[] {"# #", "###", "###", '#', mod_FCBetterThanWolves.fcTannedLeather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherLeggings), new Object[] {"###", "# #", "# #", '#', mod_FCBetterThanWolves.fcTannedLeather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherBoots), new Object[] {"# #", "# #", '#', mod_FCBetterThanWolves.fcTannedLeather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcPadding), new Object[] {" C ", "FFF", " C ", 'C', mod_FCBetterThanWolves.fcHempCloth, 'F', Item.feather});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcDynamite), new Object[] {"#X", "#Y", "#Z", '#', Item.paper, 'X', mod_FCBetterThanWolves.fcHellfireDust, 'Y', mod_FCBetterThanWolves.fcTallow, 'Z', mod_FCBetterThanWolves.fcSawDust});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcDynamite), new Object[] {"#X", "#Y", "#Z", '#', Item.paper, 'X', mod_FCBetterThanWolves.fcHellfireDust, 'Y', mod_FCBetterThanWolves.fcTallow, 'Z', mod_FCBetterThanWolves.fcSoulDust});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBreedingHarness), new Object[] {"#", "X", "Y", '#', new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherHelm, 1, -1), 'X', new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherChest, 1, -1), 'Y', new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherLeggings, 1, -1)});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCandleItem, 2, 15), new Object[] {"H", "T", "T", 'H', mod_FCBetterThanWolves.fcHempFibers, 'T', mod_FCBetterThanWolves.fcTallow});
        AddVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcItemOcularOfEnder, 2, 0), new Object[] {"ggg", "gEg", "ggg", 'g', Item.goldNugget, 'E', Item.eyeOfEnder});
    }

    private static void AddDyeRecipes()
    {
        int var0;

        for (var0 = 0; var0 < 15; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCandleItem, 1, var0), new Object[] {new ItemStack(Item.dyePowder, 1, var0), new ItemStack(mod_FCBetterThanWolves.fcCandleItem, 1, 15)});
        }

        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcCandleItem, 1, 3), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcDung), new ItemStack(mod_FCBetterThanWolves.fcCandleItem, 1, 15)});

        for (var0 = 0; var0 < 15; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcVase, 1, BlockCloth.getBlockFromDye(var0)), new Object[] {new ItemStack(Item.dyePowder, 1, var0), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcVase.blockID], 1, 0)});
            AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 1, BlockCloth.getBlockFromDye(var0)), new Object[] {new ItemStack(Item.dyePowder, 1, var0), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcWoolSlab.blockID], 1, 0)});
        }

        AddShapelessVanillaRecipe(new ItemStack(Block.cloth, 1, 12), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcDung), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcVase, 1, 12), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcDung), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcVase.blockID], 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 1, 12), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcDung), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcWoolSlab.blockID], 1, 0)});
    }

    private static void AddAlternateVanillaRecipes()
    {
        AddShapelessVanillaRecipe(new ItemStack(Block.planks, 4, 3), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBloodWood)});
        AddVanillaRecipe(new ItemStack(Block.pistonStickyBase, 1), new Object[] {"#", "X", '#', mod_FCBetterThanWolves.fcGlue, 'X', Block.pistonBase});
        AddVanillaRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"###", "XYX", "XZX", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', Block.cobblestone, 'Y', Item.ingotIron, 'Z', Item.redstone});
        AddVanillaRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"###", "XYX", "XZX", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', Block.cobblestone, 'Y', Item.ingotIron, 'Z', Item.redstone});
        AddVanillaRecipe(new ItemStack(Block.fence, 2), new Object[] {"###", '#', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(Block.fence, 2), new Object[] {"###", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Block.fenceGate, 1), new Object[] {"#X#", '#', mod_FCBetterThanWolves.fcMoulding, 'X', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1)});
        AddVanillaRecipe(new ItemStack(Block.fenceGate, 1), new Object[] {"#X#", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1), 'X', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Block.ladder, 4), new Object[] {"# #", "###", "# #", '#', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(Block.ladder, 4), new Object[] {"# #", "###", "# #", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Block.stairCompactPlanks, 1), new Object[] {"# ", "##", '#', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(Block.stairCompactPlanks, 1), new Object[] {"# ", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 0)});
        AddVanillaRecipe(new ItemStack(Block.stairsWoodSpruce, 1), new Object[] {"# ", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 1)});
        AddVanillaRecipe(new ItemStack(Block.stairsWoodBirch, 1), new Object[] {"# ", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 2)});
        AddVanillaRecipe(new ItemStack(Block.stairsWoodJungle, 1), new Object[] {"# ", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 3)});
        AddVanillaRecipe(new ItemStack(Block.stairsStoneBrickSmooth), new Object[] {"# ", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickMoulding)});
        AddVanillaRecipe(new ItemStack(Item.sign, 3), new Object[] {"#", "X", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', mod_FCBetterThanWolves.fcMoulding});
        AddVanillaRecipe(new ItemStack(Item.sign, 3), new Object[] {"#", "X", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Item.doorWood, 1), new Object[] {"##", "##", "##", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1)});
        AddVanillaRecipe(new ItemStack(Item.doorWood, 1), new Object[] {"##", "##", "##", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Block.trapdoor, 2), new Object[] {"###", "###", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1)});
        AddVanillaRecipe(new ItemStack(Block.trapdoor, 2), new Object[] {"###", "###", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Item.cake, 1), new Object[] {"AAA", "BEB", "CCC", 'A', Item.sugar, 'B', Item.bucketMilk, 'C', mod_FCBetterThanWolves.fcFlour, 'E', Item.egg});
        AddVanillaRecipe(new ItemStack(Block.torchWood, 4), new Object[] {"#", "X", '#', mod_FCBetterThanWolves.fcNethercoal, 'X', Item.stick});
        AddVanillaRecipe(new ItemStack(Block.bookShelf), new Object[] {"###", "XXX", "###", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', Item.book});
        AddVanillaRecipe(new ItemStack(Block.bookShelf), new Object[] {"###", "XXX", "###", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', Item.book});
        AddVanillaRecipe(new ItemStack(Block.chest), new Object[] {"###", "# #", "###", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1)});
        AddVanillaRecipe(new ItemStack(Block.chest), new Object[] {"###", "# #", "###", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Item.redstoneRepeater), new Object[] {"TRT", "SSS", 'S', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 0), 'R', new ItemStack(Item.redstone), 'T', new ItemStack(Block.torchRedstoneActive)});
        AddVanillaRecipe(new ItemStack(Block.pressurePlatePlanks), new Object[] {"SS", 'S', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1)});
        AddVanillaRecipe(new ItemStack(Block.pressurePlatePlanks), new Object[] {"SS", 'S', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1)});
        AddVanillaRecipe(new ItemStack(Block.pressurePlateStone), new Object[] {"SS", 'S', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(Item.stick), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcMoulding)});
        AddShapelessVanillaRecipe(new ItemStack(Item.stick), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddShapelessVanillaRecipe(new ItemStack(Item.book, 2), new Object[] {mod_FCBetterThanWolves.fcTannedLeather, Item.paper, Item.paper, Item.paper, Item.paper, Item.paper, Item.paper});
        AddVanillaRecipe(new ItemStack(Block.jukebox, 1), new Object[] {"###", "#X#", "###", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', Item.diamond});
        AddVanillaRecipe(new ItemStack(Block.jukebox, 1), new Object[] {"###", "#X#", "###", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', Item.diamond});
        AddVanillaRecipe(new ItemStack(Block.music, 1), new Object[] {"###", "#X#", "###", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), 'X', Item.redstone});
        AddVanillaRecipe(new ItemStack(Block.music, 1), new Object[] {"###", "#X#", "###", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, -1), 'X', Item.redstone});
    }

    private static void AddConversionRecipes()
    {
        AddShapelessVanillaRecipe(new ItemStack(Block.planks), new Object[] {new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 1)});
        int var0;

        for (var0 = 0; var0 < 4; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(Block.planks, 1, var0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, var0), new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, var0)});
        }

        AddShapelessVanillaRecipe(new ItemStack(Block.stone), new Object[] {new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 0), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 0), new Object[] {new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcMoulding.blockID]), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcMoulding.blockID])});

        for (var0 = 0; var0 < 4; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, var0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, var0), new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, var0)});
        }

        AddShapelessVanillaRecipe(new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 0), new Object[] {new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcStoneMoulding.blockID]), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcStoneMoulding.blockID])});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 0), new Object[] {new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcCorner.blockID]), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcCorner.blockID])});

        for (var0 = 0; var0 < 4; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, var0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, var0), new ItemStack(mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, var0)});
        }

        AddShapelessVanillaRecipe(new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcStoneMoulding.blockID]), new Object[] {new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcCorner.blockID], 1, 8), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcCorner.blockID], 1, 8)});
        AddShapelessVanillaRecipe(new ItemStack(Block.stoneBrick), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 1, 0), new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 1, 0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickMoulding), new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickMoulding)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickMoulding), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 1, 1), new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 1, 1)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHempFibers, 6), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcRopeItem)});
        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcHempFibers, 9), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcHempCloth)});
        AddShapelessVanillaRecipe(new ItemStack(Block.dirt), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcBlockDirtSlab), new ItemStack(mod_FCBetterThanWolves.fcBlockDirtSlab)});

        for (var0 = 0; var0 < 16; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(Block.cloth, 1, var0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 1, var0), new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 1, var0)});
        }

        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 1, 5), new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 1, 5)});
        AddShapelessVanillaRecipe(new ItemStack(Item.clay, 3), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcUnfiredPottery, 1, 0)});
        AddShapelessVanillaRecipe(new ItemStack(Item.clay, 3), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcUnfiredPottery, 1, 1)});
        AddShapelessVanillaRecipe(new ItemStack(Item.clay, 2), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcUnfiredPottery, 1, 2)});
        AddShapelessVanillaRecipe(new ItemStack(Item.clay, 1), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcUnfiredPottery, 1, 3)});
        AddShapelessVanillaRecipe(new ItemStack(Item.clay, 1), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcUnfiredPottery, 1, 4)});
    }

    private static void AddSmeltingRecipes()
    {
        FurnaceRecipes.smelting().addSmelting(mod_FCBetterThanWolves.fcWolfRaw.shiftedIndex, new ItemStack(mod_FCBetterThanWolves.fcWolfCooked), 0.0F);
        FurnaceRecipes.smelting().addSmelting(mod_FCBetterThanWolves.fcFlour.shiftedIndex, new ItemStack(Item.bread), 0.0F);
        FurnaceRecipes.smelting().addSmelting(mod_FCBetterThanWolves.fcBloodWood.blockID, new ItemStack(Item.coal, 1, 1), 0.0F);
        FurnaceRecipes.smelting().addSmelting(mod_FCBetterThanWolves.fcNetherSludge.shiftedIndex, new ItemStack(mod_FCBetterThanWolves.fcNetherBrick), 0.0F);
        FurnaceRecipes.smelting().addSmelting(mod_FCBetterThanWolves.fcItemRawEgg.shiftedIndex, new ItemStack(mod_FCBetterThanWolves.fcItemFriedEgg), 0.0F);
    }

    private static void AddAnvilRecipes()
    {
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcRefinedSword, 1), new Object[] {"#", "#", "#", "X", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcRefinedShovel, 1), new Object[] {"#", "X", "X", "X", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcRefinedPickAxe, 1), new Object[] {"###", " X ", " X ", " X ", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcMattock, 1), new Object[] {" ###", "# X ", "  X ", "  X ", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcRefinedHoe, 1), new Object[] {"##", " X", " X", " X", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBattleAxe, 1), new Object[] {"###", "#X#", " X ", " X ", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcRefinedAxe, 1), new Object[] {"##", "#X", " X", " X", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcHaft});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcArmorPlate, 1), new Object[] {"#XY#", '#', mod_FCBetterThanWolves.fcStrap, 'X', mod_FCBetterThanWolves.fcSteel, 'Y', mod_FCBetterThanWolves.fcPadding});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlateHelm, 1), new Object[] {"####", "#  #", "#  #", " XX ", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcArmorPlate});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlateBreastPlate, 1), new Object[] {"X  X", "####", "####", "####", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcArmorPlate});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlateLeggings, 1), new Object[] {"####", "X##X", "X  X", "X  X", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcArmorPlate});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcPlateBoots, 1), new Object[] {" ## ", " ## ", "#XX#", '#', mod_FCBetterThanWolves.fcSteel, 'X', mod_FCBetterThanWolves.fcArmorPlate});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBroadheadArrowhead, 16), new Object[] {" # ", " # ", "###", " # ", '#', mod_FCBetterThanWolves.fcSteel});
        AddAnvilRecipe(new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 8, 0), new Object[] {"####", '#', Block.stone});
        AddAnvilRecipe(new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcStoneMoulding.blockID], 8, 0), new Object[] {"####", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcOmniSlab.blockID], 1, 0)});
        AddAnvilRecipe(new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcCorner.blockID], 8, 8), new Object[] {"####", '#', new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcStoneMoulding.blockID], 1, 0)});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcPressurePlateObsidian, 2), new Object[] {"####", '#', Block.obsidian});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcPolishedLapis, 2), new Object[] {"###", "###", "GGG", " R ", '#', new ItemStack(Item.dyePowder, 1, 4), 'G', Item.goldNugget, 'R', Item.redstone});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockDetector), new Object[] {"####", "XTTX", "ZYYZ", "ZYYZ", '#', Block.cobblestone, 'X', mod_FCBetterThanWolves.fcPolishedLapis, 'Y', Item.redstone, 'Z', Block.stone, 'T', Block.torchRedstoneActive});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcLens), new Object[] {"GDDG", "G  G", "G  G", "G##G", '#', Block.thinGlass, 'G', Item.ingotGold, 'D', Item.diamond});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockDispenser), new Object[] {"####", "#ZZ#", "YTTY", "YXXY", '#', Block.cobblestoneMossy, 'X', Item.redstone, 'Y', Block.stone, 'Z', mod_FCBetterThanWolves.fcSoulUrn, 'T', Block.torchRedstoneActive});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBuddyBlock, 1), new Object[] {"##X#", "XYY#", "#YYX", "#X##", '#', Block.stone, 'X', mod_FCBetterThanWolves.fcPolishedLapis, 'Y', Block.torchRedstoneActive});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 8, 1), new Object[] {"##", "##", "##", "##", '#', Block.stone});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticNonOpaque, 10, 2), new Object[] {" ## ", "####", "####", '#', Block.stone});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcSoulforgedSteelBlock, 1), new Object[] {"####", "####", "####", "####", '#', mod_FCBetterThanWolves.fcSteel});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcTuningFork, 6), new Object[] {"# #", "# #", " # ", " # ", '#', mod_FCBetterThanWolves.fcSteel});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcInfernalEnchanter), new Object[] {"CBBC", "DSSD", "SSSS", 'S', mod_FCBetterThanWolves.fcSteel, 'D', Item.diamond, 'C', new ItemStack(mod_FCBetterThanWolves.fcCandleItem, 1, 0), 'B', Item.bone});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcItemCanvas), new Object[] {"MMMM", "MFFM", "MFFM", "MMMM", 'F', mod_FCBetterThanWolves.fcHempCloth, 'M', mod_FCBetterThanWolves.fcMoulding});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcItemCanvas), new Object[] {"MMMM", "MFFM", "MFFM", "MMMM", 'F', mod_FCBetterThanWolves.fcHempCloth, 'M', new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, -1)});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 8, 0), new Object[] {"####", '#', Block.stoneBrick});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickMoulding.blockID, 8, 0), new Object[] {"####", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 1, 0)});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner, 8, 1), new Object[] {"####", '#', new ItemStack(mod_FCBetterThanWolves.fcBlockStoneBrickMoulding.blockID, 1, 0)});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcItemScrew), new Object[] {"## ", " ##", "## ", " ##", '#', new ItemStack(Item.ingotIron)});
        AddAnvilRecipe(new ItemStack(mod_FCBetterThanWolves.fcItemEnderSpectacles), new Object[] {"SOOS", 'S', mod_FCBetterThanWolves.fcStrap, 'O', mod_FCBetterThanWolves.fcItemOcularOfEnder});
    }

    private static void AddCauldronRecipes()
    {
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcNetherSludge, 8), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPotash, 1, -1), new ItemStack(mod_FCBetterThanWolves.fcHellfireDust, 8, -1)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcNethercoal, 2), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcHellfireDust, 1), new ItemStack(mod_FCBetterThanWolves.fcCoalDust, 1)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcConcentratedHellfire, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcHellfireDust, 8)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcDung, 1), new ItemStack(mod_FCBetterThanWolves.fcScouredLeather, 1)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcDonut, 4), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcFlour, 1)});

        for (int var0 = 0; var0 < 15; ++var0)
        {
            AddCauldronRecipe(new ItemStack(Block.cloth, 8, BlockCloth.getBlockFromDye(var0)), new ItemStack[] {new ItemStack(Item.dyePowder, 1, var0), new ItemStack(Block.cloth, 8, 0)});
            AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 16, BlockCloth.getBlockFromDye(var0)), new ItemStack[] {new ItemStack(Item.dyePowder, 1, var0), new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 16, 0)});
        }

        AddCauldronRecipe(new ItemStack(Block.cloth, 8, 12), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcDung, 1), new ItemStack(Item.itemsList[Block.cloth.blockID], 8, 0)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 16, 12), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcDung, 1), new ItemStack(Item.itemsList[mod_FCBetterThanWolves.fcWoolSlab.blockID], 16, 0)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcAestheticVegetation, 1, 2), new ItemStack[] {new ItemStack(Block.sapling, 1, 0), new ItemStack(Block.sapling, 1, 1), new ItemStack(Block.sapling, 1, 2), new ItemStack(Block.sapling, 1, 3), new ItemStack(mod_FCBetterThanWolves.fcSoulUrn, 8), new ItemStack(Item.netherStalkSeeds)});
        AddCauldronRecipe(new ItemStack[] {new ItemStack(Block.dirt), new ItemStack(mod_FCBetterThanWolves.fcItemBloodMossSpores)}, new ItemStack[] {new ItemStack(Block.mycelium), new ItemStack(Block.mushroomBrown), new ItemStack(Block.mushroomRed), new ItemStack(mod_FCBetterThanWolves.fcSoulUrn, 8), new ItemStack(mod_FCBetterThanWolves.fcDung), new ItemStack(Item.netherStalkSeeds)});
        FCCraftingManagerCauldron.getInstance().AddRecipe(new ItemStack(Block.cloth, 8, 0), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPotash, 1, -1), new ItemStack(Block.cloth, 8, 0)}, true);
        FCCraftingManagerCauldron.getInstance().AddRecipe(new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 16, 0), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPotash, 1, -1), new ItemStack(mod_FCBetterThanWolves.fcWoolSlab, 16, 0)}, true);
        AddCauldronRecipe(new ItemStack(Block.tnt), new ItemStack[] {new ItemStack(Block.sand, 4), new ItemStack(Item.gunpowder, 5)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcFilament, 1), new ItemStack[] {new ItemStack(Item.lightStoneDust), new ItemStack(Item.redstone), new ItemStack(Item.silk)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcFilament, 1), new ItemStack[] {new ItemStack(Item.lightStoneDust), new ItemStack(Item.redstone), new ItemStack(mod_FCBetterThanWolves.fcHempFibers)});
        AddCauldronRecipe(new ItemStack(Block.cloth), new ItemStack[] {new ItemStack(Item.silk, 4)});
        AddCauldronRecipe(new ItemStack(Item.dyePowder, 1, 2), new ItemStack[] {new ItemStack(Block.cactus)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcBucketCement, 1), new ItemStack[] {new ItemStack(Block.sand), new ItemStack(Block.gravel), new ItemStack(Item.bucketEmpty), new ItemStack(mod_FCBetterThanWolves.fcSoulUrn)});
        AddCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcHardBoiledEgg), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcItemRawEgg)});
        AddCauldronRecipe(new ItemStack(Block.pistonBase, 4), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcSoap), new ItemStack(Block.pistonStickyBase, 4)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 1), new ItemStack[] {new ItemStack(Item.leather, 1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcTannedLeather, 1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcScouredLeather, 1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBelt, 2)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcStrap, 8)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 5), new ItemStack[] {new ItemStack(Item.helmetLeather, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 8), new ItemStack[] {new ItemStack(Item.plateLeather, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 7), new ItemStack[] {new ItemStack(Item.legsLeather, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 4), new ItemStack[] {new ItemStack(Item.bootsLeather, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 3), new ItemStack[] {new ItemStack(Item.saddle, 1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 5), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherHelm, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 8), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherChest, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 7), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherLeggings, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 4), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcTannedLeatherBoots, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcGlue, 20), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBreedingHarness, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTallow, 1), new ItemStack[] {new ItemStack(Item.porkCooked, 1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTallow, 1), new ItemStack[] {new ItemStack(Item.porkRaw, 1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTallow, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcWolfCooked, 8)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTallow, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcWolfRaw, 8)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTallow, 1), new ItemStack[] {new ItemStack(Item.beefCooked, 4)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcTallow, 1), new ItemStack[] {new ItemStack(Item.beefRaw, 4)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(Block.wood, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBloodWood, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(Block.planks, 6, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 12, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 24, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID, 48, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcSawDust, 16)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcSoulDust, 16)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcSoap, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPotash, 1), new ItemStack(mod_FCBetterThanWolves.fcTallow, 1)});
        AddStokedCauldronRecipe(new ItemStack[] {new ItemStack(Item.silk, 2), new ItemStack(Item.stick, 2)}, new ItemStack[] {new ItemStack(Item.bow, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack[] {new ItemStack(Item.stick, 2), new ItemStack(Item.silk, 2), new ItemStack(Item.bone, 1)}, new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcCompositeBow, 1, -1)});
        AddStokedCauldronRecipe(new ItemStack[] {new ItemStack(Item.flint, 2), new ItemStack(Item.stick, 1), new ItemStack(Item.feather, 1)}, new ItemStack[] {new ItemStack(Item.arrow, 8, -1)});
        AddStokedCauldronRecipe(new ItemStack[] {new ItemStack(Item.flint, 2)}, new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcItemRottenArrow, 8, -1)});
        AddStokedCauldronRecipe(new ItemStack(mod_FCBetterThanWolves.fcItemDogFood, 2), new ItemStack[] {new ItemStack(Item.rottenFlesh, 4), new ItemStack(Item.dyePowder, 4, 15), new ItemStack(Item.sugar, 1)});
    }

    private static void AddCrucibleRecipes()
    {
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.bucketEmpty)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.bucketLava)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.bucketWater)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.bucketMilk)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBucketCement)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.pickaxeSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.axeSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 2), new ItemStack[] {new ItemStack(Item.swordSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 2), new ItemStack[] {new ItemStack(Item.hoeSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[] {new ItemStack(Item.shovelSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 5), new ItemStack[] {new ItemStack(Item.helmetSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 8), new ItemStack[] {new ItemStack(Item.plateSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 7), new ItemStack[] {new ItemStack(Item.legsSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 4), new ItemStack[] {new ItemStack(Item.bootsSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Item.helmetChain, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 4), new ItemStack[] {new ItemStack(Item.plateChain, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 4), new ItemStack[] {new ItemStack(Item.legsChain, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 2), new ItemStack[] {new ItemStack(Item.bootsChain, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 4), new ItemStack[] {new ItemStack(Item.compass)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[] {new ItemStack(Item.flintAndSteel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 6), new ItemStack[] {new ItemStack(Item.doorSteel)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 4), new ItemStack[] {new ItemStack(Item.map)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 2), new ItemStack[] {new ItemStack(Item.shears, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[] {new ItemStack(Block.railDetector)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcDetectorRailWood)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcDetectorRailObsidian)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 9), new ItemStack[] {new ItemStack(Block.blockSteel)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 5), new ItemStack[] {new ItemStack(Item.minecartEmpty)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 5), new ItemStack[] {new ItemStack(Item.minecartCrate)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 5), new ItemStack[] {new ItemStack(Item.minecartPowered)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Block.rail, 8)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 3), new ItemStack[] {new ItemStack(Block.fenceIron, 8)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 7), new ItemStack[] {new ItemStack(Item.cauldron)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 7), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcCauldron)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 8), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcItemScrew)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 3), new ItemStack[] {new ItemStack(Item.pickaxeGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 3), new ItemStack[] {new ItemStack(Item.axeGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 2), new ItemStack[] {new ItemStack(Item.swordGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 2), new ItemStack[] {new ItemStack(Item.hoeGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 1), new ItemStack[] {new ItemStack(Item.shovelGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 5), new ItemStack[] {new ItemStack(Item.helmetGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 8), new ItemStack[] {new ItemStack(Item.plateGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 7), new ItemStack[] {new ItemStack(Item.legsGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 4), new ItemStack[] {new ItemStack(Item.bootsGold, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 4), new ItemStack[] {new ItemStack(Item.pocketSundial)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 1), new ItemStack[] {new ItemStack(Block.railPowered)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 9), new ItemStack[] {new ItemStack(Block.blockGold)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 5), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBattleAxe, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 3), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcRefinedAxe, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 3), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcRefinedPickAxe, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 4), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcMattock, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 3), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcRefinedSword, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 2), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcRefinedHoe, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcRefinedShovel, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 7), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcAnvil)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcArmorPlate)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 10), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPlateHelm, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 14), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPlateBreastPlate, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 12), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPlateLeggings, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 8), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcPlateBoots, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 3), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBroadheadArrowhead, 8)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 3), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcBroadheadArrow, 32)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 16), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcAestheticOpaque, 1, 2)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 16), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcSoulforgedSteelBlock)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 1), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcTuningFork, 1, -1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.ingotGold, 1), new ItemStack[] {new ItemStack(Item.goldNugget, 9)});
        AddStokedCrucibleRecipe(new ItemStack(mod_FCBetterThanWolves.fcSteel, 1), new ItemStack[] {new ItemStack(Item.ingotIron, 1), new ItemStack(mod_FCBetterThanWolves.fcCoalDust, 1), new ItemStack(mod_FCBetterThanWolves.fcSoulUrn, 1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.redstone, 7), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcConcentratedHellfire, 1), new ItemStack(Item.goldNugget, 1)});
        AddStokedCrucibleRecipe(new ItemStack(Item.redstone, 63), new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcConcentratedHellfire, 9), new ItemStack(Item.ingotGold, 1)});
        AddStokedCrucibleRecipe(new ItemStack(Block.glass), new ItemStack[] {new ItemStack(Block.sand)});
        AddStokedCrucibleRecipe(new ItemStack(Block.glass, 3), new ItemStack[] {new ItemStack(Block.thinGlass, 8)});
        AddStokedCrucibleRecipe(new ItemStack(Block.stone, 1), new ItemStack[] {new ItemStack(Block.cobblestone, 1)});
    }

    private static void AddMillStoneRecipes()
    {
        AddMillStoneRecipe(new ItemStack(mod_FCBetterThanWolves.fcFlour), new ItemStack(Item.wheat));
        AddMillStoneRecipe(new ItemStack(mod_FCBetterThanWolves.fcScouredLeather), new ItemStack(Item.leather));
        AddMillStoneRecipe(new ItemStack(mod_FCBetterThanWolves.fcHempFibers, 4, 0), new ItemStack(mod_FCBetterThanWolves.fcHemp));
        AddMillStoneRecipe(new ItemStack(Item.sugar), new ItemStack(Item.reed));
        AddMillStoneRecipe(new ItemStack[] {new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.silk), new ItemStack(Item.dyePowder, 1, 1), new ItemStack(Item.dyePowder, 1, 1), new ItemStack(Item.dyePowder, 1, 1), new ItemStack(mod_FCBetterThanWolves.fcWolfRaw, 1, 0)}, new ItemStack[] {new ItemStack(mod_FCBetterThanWolves.fcCompanionCube)});
        AddMillStoneRecipe(new ItemStack(mod_FCBetterThanWolves.fcWolfRaw, 1, 0), new ItemStack(mod_FCBetterThanWolves.fcCompanionCube, 1, 1));
        AddMillStoneRecipe(new ItemStack(mod_FCBetterThanWolves.fcGroundNetherrack), new ItemStack(Block.netherrack));
        AddMillStoneRecipe(new ItemStack(Item.dyePowder, 3, 15), new ItemStack(Item.bone));
        AddMillStoneRecipe(new ItemStack(mod_FCBetterThanWolves.fcCoalDust), new ItemStack(Item.coal, 1, -1));
        AddMillStoneRecipe(new ItemStack(Item.dyePowder, 2, 1), new ItemStack(Block.plantRed));
        AddMillStoneRecipe(new ItemStack(Item.dyePowder, 2, 11), new ItemStack(Block.plantYellow));
    }

    private static void AddTuningForkRecipes()
    {
        for (int var0 = 0; var0 < 24; ++var0)
        {
            AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTuningFork, 1, var0 + 1), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcTuningFork, 1, var0)});
        }

        AddShapelessVanillaRecipe(new ItemStack(mod_FCBetterThanWolves.fcTuningFork, 1, 0), new Object[] {new ItemStack(mod_FCBetterThanWolves.fcTuningFork, 1, 24)});
    }

    private static void RemoveVanillaRecipes()
    {
        RemoveVanillaRecipe(new ItemStack(Item.bread, 1), new Object[] {"###", '#', Item.wheat});
        RemoveShapelessVanillaRecipe(new ItemStack(Item.dyePowder, 3, 15), new Object[] {Item.bone});
        RemoveVanillaRecipe(new ItemStack(Item.sugar, 1), new Object[] {"#", '#', Item.reed});
        RemoveVanillaRecipe(new ItemStack(Item.cake, 1), new Object[] {"AAA", "BEB", "CCC", 'A', Item.bucketMilk, 'B', Item.sugar, 'C', Item.wheat, 'E', Item.egg});
    }

    private static void AddDebugRecipes() {}
}
