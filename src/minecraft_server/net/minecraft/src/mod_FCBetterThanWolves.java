package net.minecraft.src;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;
import net.minecraft.server.MinecraftServer;

public class mod_FCBetterThanWolves
{
    public static final String fcVersionString = "4.16";
    public static boolean[] m_bBlocksRequireReliableUpdates = new boolean[256];
    public static boolean[] m_bBlocksPotentialFluidSources = new boolean[256];
    public static boolean[] m_bBlocksRequireSeperateRandomUpdates = new boolean[256];
    public static boolean[] m_bBlocksRequireClientNotificationOnMetadataChange = new boolean[256];
    public static boolean m_bHasInitialized = false;
    public static mod_FCBetterThanWolves m_instance = new mod_FCBetterThanWolves();
    public static StepSound fcSoundSquishFootstep;
    public static int iCustomAnvilRenderID;
    public static int iCustomCementRenderID;
    public static int iCustomHandCrankRenderID;
    public static int iCustomAnchorRenderID;
    public static int iCustomRopeRenderID;
    public static int iCustomAxleRenderID;
    public static int iCustomHopperRenderID;
    public static int iCustomSawRenderID;
    public static int iCustomPlatformRenderID;
    public static int iCustomCompanionCubeRenderID;
    public static int iCustomTurntableRenderID;
    public static int iCustomUnfiredPotteryRenderID;
    public static int iCustomCrucibleRenderID;
    public static int iCustomPlanterRenderID;
    public static int iCustomVaseRenderID;
    public static int iCustomAestheticNonOpaqueRenderID;
    public static int iCustomAestheticVegetationRenderID;
    public static int iCustomLensRenderID;
    public static int iCustomDetectorLogicDebugRenderID;
    public static int iCustomDetectorLogicGlowingRenderID;
    public static int iCustomCauldronRenderID;
    public static int iCustomInfernalEnchanterRenderID;
    public static int iCustomDirtSlabRenderID;
    public static int iCustomBloodWoodRenderID;
    public static int iCustomMouldingRenderID;
    public static final Material fcCementMaterial = new FCMaterialCement(MapColor.stoneColor);
    public static final Material fcSoulforgedSteelMaterial = new FCMaterialSoulforgedSteel(MapColor.ironColor);
    public static final Material fcWoodMaterial = (new Material(MapColor.woodColor)).setBurning();
    public static final Material fcNetherGrowthMaterial = new FCMaterialNetherGrowth(MapColor.foliageColor);
    public static Block fcBlockScrewPump;
    public static Block fcBlockWoodSpruceSidingAndCorner;
    public static Block fcBlockWoodSpruceMoulding;
    public static Block fcBlockWoodBirchSidingAndCorner;
    public static Block fcBlockWoodBirchMoulding;
    public static Block fcBlockWoodJungleSidingAndCorner;
    public static Block fcBlockWoodJungleMoulding;
    public static Block fcBlockStoneBrickSidingAndCorner;
    public static Block fcBlockStoneBrickMoulding;
    public static Block fcBlockFarmlandFertilized;
    public static Block fcBlockWoolSlabTop;
    public static Block fcBlockDirtSlab;
    public static Block fcBlockBloodMoss;
    public static Block fcInfernalEnchanter;
    public static Block fcSoulforgedSteelBlock;
    public static Block fcBlockDetectorGlowingLogic;
    public static Block fcLeaves;
    public static Block fcBloodWood;
    public static Block fcAestheticVegetation;
    public static Block fcStoneMoulding;
    public static Block fcAestheticOpaque;
    public static Block fcAestheticNonOpaque;
    public static Block fcMiningCharge;
    public static Block fcBuddyBlock;
    public static Block fcKiln;
    public static Block fcWoolSlab;
    public static Block fcAnvil;
    public static Block fcLightBulbOff;
    public static Block fcLightBulbOn;
    public static Block fcBBQ;
    public static Block fcHopper;
    public static Block fcSaw;
    public static Block fcPlatform;
    public static Block fcCement;
    public static Block fcPulley;
    public static Block fcPressurePlateObsidian;
    public static Block fcMoulding;
    public static Block fcCorner;
    public static Block fcBlockDispenser;
    public static Block fcCauldron;
    public static Block fcDetectorRailWood;
    public static Block fcDetectorRailObsidian;
    public static Block fcCompanionCube;
    public static Block fcBlockDetector;
    public static Block fcBlockDetectorLogic;
    public static Block fcLens;
    public static Block fcHempCrop;
    public static Block fcHandCrank;
    public static Block fcMillStone;
    public static Block fcAnchor;
    public static Block fcRopeBlock;
    public static Block fcOmniSlab;
    public static Block fcAxleBlock;
    public static Block fcGearBox;
    public static Block fcTurntable;
    public static Block fcBellows;
    public static Block fcStokedFire;
    public static Block fcUnfiredPottery;
    public static Block fcCrucible;
    public static Block fcPlanter;
    public static Block fcVase;
    public static Item fcBucketCement;
    public static Item fcWolfRaw;
    public static Item fcWolfCooked;
    public static Item fcNethercoal;
    public static Item fcHempSeeds;
    public static Item fcHemp;
    public static Item fcGear;
    public static Item fcFlour;
    public static Item fcHempFibers;
    public static Item fcScouredLeather;
    public static Item fcDonut;
    public static Item fcRopeItem;
    public static Item fcRollersItem;
    public static Item fcDung;
    public static Item fcWaterWheelItem;
    public static Item fcWindMillBladeItem;
    public static Item fcWindMillItem;
    public static Item fcHempCloth;
    public static Item fcGrate;
    public static Item fcWicker;
    public static Item fcTannedLeather;
    public static Item fcStrap;
    public static Item fcBelt;
    public static Item fcFoulFood;
    public static Item fcWoodBlade;
    public static Item fcGlue;
    public static Item fcTallow;
    public static Item fcHaft;
    public static Item fcSteel;
    public static Item fcRefinedPickAxe;
    public static Item fcRefinedShovel;
    public static Item fcRefinedHoe;
    public static Item fcBattleAxe;
    public static Item fcRefinedSword;
    public static Item fcGroundNetherrack;
    public static Item fcHellfireDust;
    public static Item fcConcentratedHellfire;
    public static Item fcArmorPlate;
    public static Item fcPlateHelm;
    public static Item fcPlateBreastPlate;
    public static Item fcPlateLeggings;
    public static Item fcPlateBoots;
    public static Item fcCompositeBow;
    public static Item fcBroadheadArrowhead;
    public static Item fcBroadheadArrow;
    public static Item fcCoalDust;
    public static Item fcPadding;
    public static Item fcFilament;
    public static Item fcPolishedLapis;
    public static Item fcUrn;
    public static Item fcSoulUrn;
    public static Item fcHardBoiledEgg;
    public static Item fcPotash;
    public static Item fcSoap;
    public static Item fcSawDust;
    public static Item fcTannedLeatherHelm;
    public static Item fcTannedLeatherChest;
    public static Item fcTannedLeatherLeggings;
    public static Item fcTannedLeatherBoots;
    public static Item fcDynamite;
    public static Item fcBreedingHarness;
    public static Item fcSoulDust;
    public static Item fcMattock;
    public static Item fcRefinedAxe;
    public static Item fcNetherSludge;
    public static Item fcNetherBrick;
    public static Item fcTuningFork;
    public static Item fcArcaneScroll;
    public static Item fcCandleItem;
    public static Item fcItemBloodMossSpores;
    public static Item fcItemMould;
    public static Item fcItemCanvas;
    public static Item fcItemDogFood;
    public static Item fcItemRawEgg;
    public static Item fcItemFriedEgg;
    public static Item fcItemScrew;
    public static Item fcItemRottenArrow;
    public static Item fcItemOcularOfEnder;
    public static Item fcItemEnderSpectacles;
    private static int fcBlockScrewPumpID = 195;
    private static int fcBlockWoodSpruceSidingAndCornerID = 196;
    private static int fcBlockWoodSpruceMouldingID = 197;
    private static int fcBlockWoodBirchSidingAndCornerID = 198;
    private static int fcBlockWoodBirchMouldingID = 199;
    private static int fcBlockWoodJungleSidingAndCornerID = 200;
    private static int fcBlockWoodJungleMouldingID = 201;
    private static int fcBlockStoneBrickSidingAndCornerID = 202;
    private static int fcBlockStoneBrickMouldingID = 203;
    private static int fcBlockFarmlandFertilizedID = 204;
    private static int fcBlockWoolSlabTopID = 205;
    private static int fcBlockDirtSlabID = 206;
    private static int fcBlockBloodMossID = 207;
    private static int fcInfernalEnchanterID = 208;
    private static int fcSoulforgedSteelBlockID = 209;
    private static int fcBlockDetectorGlowingLogicID = 210;
    private static int fcLeavesID = 211;
    private static int fcBloodWoodID = 212;
    private static int fcAestheticVegetationID = 213;
    private static int fcStoneMouldingID = 214;
    private static int fcAestheticOpaqueID = 215;
    private static int fcAestheticNonOpaqueID = 216;
    private static int fcMiningChargeID = 217;
    private static int fcBuddyBlockID = 218;
    private static int fcKilnID = 219;
    private static int fcWoolSlabID = 220;
    private static int fcAnvilID = 221;
    private static int fcLightBulbOffID = 222;
    private static int fcLightBulbOnID = 223;
    private static int fcBBQID = 224;
    private static int fcHopperID = 225;
    private static int fcSawID = 226;
    private static int fcPlatformID = 227;
    private static int fcCementID = 228;
    private static int fcPulleyID = 229;
    private static int fcPressurePlateObsidianID = 230;
    private static int fcMouldingID = 231;
    private static int fcCornerID = 232;
    private static int fcBlockDispenserID = 233;
    private static int fcCauldronID = 234;
    private static int fcDetectorRailWoodID = 235;
    private static int fcDetectorRailObsidianID = 236;
    private static int fcCompanionCubeID = 237;
    private static int fcBlockDetectorID = 238;
    private static int fcBlockDetectorLogicID = 239;
    private static int fcBlockLensID = 240;
    private static int fcHempCropID = 241;
    private static int fcHandCrankID = 242;
    private static int fcMillStoneID = 243;
    private static int fcAnchorID = 244;
    private static int fcRopeBlockID = 245;
    private static int fcOmniSlabID = 246;
    private static int fcAxleBlockID = 247;
    private static int fcGearBoxID = 248;
    private static int fcTurntableID = 249;
    private static int fcBellowsID = 250;
    private static int fcStokedFireID = 251;
    private static int fcUnfiredPotteryID = 252;
    private static int fcCrucibleID = 253;
    private static int fcPlanterID = 254;
    private static int fcVaseID = 255;
    private static int fcBucketCeme1ntID = 222;
    private static int fcWolfRawID = 223;
    private static int fcWolfCookedID = 224;
    private static int fcNethercoalID = 225;
    private static int fcHempSeedsID = 226;
    private static int fcHempID = 227;
    private static int fcGearID = 228;
    private static int fcFlourID = 229;
    private static int fcHempFibersID = 230;
    private static int fcScouredLeatherID = 231;
    private static int fcDonutID = 232;
    private static int fcRopeItemID = 233;
    private static int fcRollersItemID = 234;
    private static int fcDungID = 235;
    private static int fcWaterWheelItemID = 236;
    private static int fcWindMillBladeItemID = 237;
    private static int fcWindMillItemID = 238;
    private static int fcHempClothID = 239;
    private static int fcGrateID = 240;
    private static int fcWickerID = 241;
    private static int fcTannedLeatherID = 242;
    private static int fcStrapID = 243;
    private static int fcBeltID = 244;
    private static int fcFoulFoodID = 245;
    private static int fcWoodBladeID = 246;
    private static int fcGlueID = 247;
    private static int fcTallowID = 248;
    private static int fcHaftID = 249;
    private static int fcSteelID = 250;
    private static int fcRefinedPickAxeID = 251;
    private static int fcRefinedShovelID = 252;
    private static int fcRefinedHoeID = 253;
    private static int fcBattleAxeID = 254;
    private static int fcRefinedSwordID = 255;
    private static int fcGroundNetherrackID = 256;
    private static int fcHellfireDustID = 257;
    private static int fcConcentratedHellfireID = 258;
    private static int fcArmorPlateID = 259;
    private static int fcPlateHelmID = 260;
    private static int fcPlateBreastPlateID = 261;
    private static int fcPlateLeggingsID = 262;
    private static int fcPlateBootsID = 263;
    private static int fcCompositeBowID = 264;
    private static int fcBroadheadArrowheadID = 265;
    private static int fcBroadheadArrowID = 266;
    private static int fcCoalDustID = 267;
    private static int fcPaddingID = 268;
    private static int fcFilamentID = 269;
    private static int fcPolishedLapisID = 270;
    private static int fcUrnID = 271;
    private static int fcSoulUrnID = 272;
    private static int fcHardBoiledEggID = 273;
    private static int fcPotashID = 274;
    private static int fcSoapID = 275;
    private static int fcSawDustID = 276;
    private static int fcTannedLeatherHelmID = 277;
    private static int fcTannedLeatherChestID = 278;
    private static int fcTannedLeatherLeggingsID = 279;
    private static int fcTannedLeatherBootsID = 280;
    private static int fcDynamiteID = 281;
    private static int fcBreedingHarnessID = 282;
    private static int fcSoulDustID = 283;
    private static int fcMattockID = 284;
    private static int fcRefinedAxeID = 285;
    private static int fcNetherSludgeID = 286;
    private static int fcNetherBrickID = 287;
    private static int fcItemTuningForkID = 22222;
    private static int fcItemArcaneScrollID = 22223;
    private static int fcItemCandleID = 22224;
    private static int fcItemBloodMossSporesID = 22225;
    private static int fcItemMouldID = 22226;
    private static int fcItemCanvasID = 22227;
    private static int fcItemDogFoodID = 22228;
    private static int fcItemRawEggID = 22229;
    private static int fcItemFriedEggID = 22230;
    private static int fcItemScrewID = 22231;
    private static int fcItemRottenArrowID = 22232;
    private static int fcItemOcularOfEnderID = 22233;
    private static int fcItemEnderSpectaclesID = 22234;
    private static int fcWaterWheelEntityID = 222;
    private static int fcWindMillEntityID = 223;
    private static int fcMovingAnchorEntityID = 224;
    private static int fcMovingPlatformEntityID = 225;
    private static int fcBlockLiftedByPlatformEntityID = 226;
    private static int fcBroadheadArrowEntityID = 227;
    private static int fcUrnEntityID = 228;
    private static int fcDynamiteEntityID = 229;
    private static int fcMiningChargeEntityID = 230;
    private static int fcInfiniteArrowEntityID = 231;
    private static int fcItemFloatingEntityID = 232;
    private static int fcItemBloodWoodSaplingEntityID = 233;
    private static int fcCanvasEntityID = 234;
    private static int fcRottenArrowEntityID = 235;
    public static int fcMillStoneContainerID = 222;
    public static int fcCauldronContainerID = 223;
    public static int fcHopperContainerID = 224;
    public static int fcCrucibleContainerID = 225;
    public static int fcAnvilContainerID = 226;
    public static int fcBlockDispenserContainerID = 227;
    public static int fcPulleyContainerID = 228;
    public static int fcInfernalEnchanterContainerID = 229;
    public static boolean fcFaceGearBoxAwayFromPlayer = false;
    public static boolean fcDisableMinecartChanges = false;
    private static boolean fcLocalEnableHardcoreBuckets = true;
    private static boolean fcServerEnableHardcoreBuckets = true;
    private static boolean fcLocalEnableHardcoreBuoy = true;
    private static boolean fcServerEnableHardcoreBuoy = true;
    public static boolean fcLimitPigMenAndMagmaCubeSpawning = true;
    public static boolean fcLimitSlimeSpawning = true;
    public static boolean fcLimitSquidSpawning = true;
    public static boolean fcDisableEndText = true;
    public static boolean fcDisableGearBoxPowerDrain = false;
    public static boolean fcEnableHardcoreSheep = true;
    public static boolean fcEnableHardcoreChests = true;
    public static boolean bIsLensBeamBeingRemoved = false;
    public static final String fcCustomPacketChannelSpawnCustomEntity = "FC|SE";
    public static final String fcCustomPacketChannelVersionCheck = "FC|VC";
    public static final String fcCustomPacketChannelBTWOptions = "FC|OP";
    public static final int fcCustomSpawnEntityPacketTypeCanvas = 0;
    public static final int fcCustomSpawnEntityPacketTypeWindMill = 1;
    public static final int fcCustomSpawnEntityPacketTypeWaterWheel = 2;
    public static final int fcCustomSpawnEntityPacketTypeMiningCharge = 3;
    public static final int fcCustomSpawnEntityPacketTypeItemBloodWoodSapling = 4;
    public static final int fcCustomSpawnEntityPacketTypeItemFloating = 5;
    public static final int fcCustomSpawnEntityPacketTypeDynamite = 6;
    public static final int fcCustomSpawnEntityPacketTypeUrn = 7;
    public static final int fcCustomSpawnEntityPacketTypeBlockLiftedByPlatform = 8;
    public static int fcBlockWoodSidingItemStubID;
    public static int fcBlockWoodCornerItemStubID;
    public static int fcBlockWoodMouldingItemStubID;
    public static final int m_iAnimalBirthingAuxFXID = 2222;
    public static final int m_iSawDamageEntityAuxFXID = 2223;
    public static final int m_iNetherGrothSporesAuxFXID = 2224;
    public static final int m_iGhastScreamSoundAuxFXID = 2225;
    public static final int m_iBurpSoundAuxFXID = 2226;
    public static final int m_iFireFizzSoundAuxFXID = 2227;
    public static final int m_iGhastMoanSoundAuxFXID = 2228;
    public static final int m_iMiningChargeExplosionAuxFXID = 2229;
    public static final int m_iHopperXPEjectAuxFXID = 2230;
    public static final int m_iItemCollectionPopSoundAuxFXID = 2231;
    public static final int m_iXPEjectPopSoundAuxFXID = 2232;
    public static final int m_iHopperCloseSoundAuxFXID = 2233;
    public static final int m_iRedstonePowerClickSoundAuxFXID = 2234;
    public static final int m_iMechanicalDeviceExplodeAuxFXID = 2235;
    public static final int m_iBlockPlaceAuxFXID = 2236;
    public static final int m_iDynamiteFuseSoundAuxFXID = 2237;
    public static final int m_iClickLowPitchSoundAuxFXID = 2238;
    public static final int m_iWolfHurtSoundAuxFXID = 2239;
    public static final int m_iChickenHurtSoundAuxFXID = 2240;
    public static final int m_iBlockDispenserSmokeEffectAuxFXID = 2241;
    public static final int m_iCompanionCubeDeathAuxFXID = 2242;

    public String getVersion()
    {
        return "4.16";
    }

    public void load()
    {
        if (!m_bHasInitialized)
        {
            MinecraftServer.getServer();
            MinecraftServer.logger.info("Better Than Wolves Version " + this.getVersion() + " Initializing...");
            this.ModInstallationIntegretityTest();
            ReadModConfigFile();
            fcSoundSquishFootstep = new FCStepSoundSquish("squish", 0.5F, 0.1F);
            this.SetupCustomRenderers();
            InstantiateBlocksAndItems();
            AddFCNames();
            this.RegisterFCBlocksAndEntities();
            this.RegisterCustomBlockItems();
            this.RegisterCustomContainers();
            FCRecipes.AddAllModRecipes();
            this.SetupCustomFireProperties();
            this.SetupCustomToolProperties();
            this.PreloadTextures();
            this.ReflectVanillaItems();
            this.RegisterCustomChannels();
            this.InitBlocksRequiringReliableUpdates();
            this.InitBlocksPotentialFluidSources();
            this.InitBlocksRequireSeperateRandomUpdates();
            this.InitBlocksRequireClientNotificationOnMetadataChange();
            this.InitCreativeTabsServer();
            this.InitBuoyancy();
            InitBellowsBlowDistance();
            m_bHasInitialized = true;
            MinecraftServer.getServer();
            MinecraftServer.logger.info("Better Than Wolves Initialization Complete.");
        }
    }

    private void InitBlocksRequiringReliableUpdates()
    {
        for (int var1 = 0; var1 < m_bBlocksRequireReliableUpdates.length; ++var1)
        {
            m_bBlocksRequireReliableUpdates[var1] = false;
        }

        this.FlagBlockForReliableUpdates(Block.tnt.blockID);
        this.FlagBlockForReliableUpdates(Block.redstoneWire.blockID);
        this.FlagBlockForReliableUpdates(Block.doorWood.blockID);
        this.FlagBlockForReliableUpdates(Block.lever.blockID);
        this.FlagBlockForReliableUpdates(Block.pressurePlateStone.blockID);
        this.FlagBlockForReliableUpdates(Block.doorSteel.blockID);
        this.FlagBlockForReliableUpdates(Block.pressurePlatePlanks.blockID);
        this.FlagBlockForReliableUpdates(Block.torchRedstoneIdle.blockID);
        this.FlagBlockForReliableUpdates(Block.torchRedstoneActive.blockID);
        this.FlagBlockForReliableUpdates(Block.button.blockID);
        this.FlagBlockForReliableUpdates(Block.jukebox.blockID);
        this.FlagBlockForReliableUpdates(Block.redstoneRepeaterIdle.blockID);
        this.FlagBlockForReliableUpdates(Block.redstoneRepeaterActive.blockID);
        this.FlagBlockForReliableUpdates(Block.trapdoor.blockID);
        this.FlagBlockForReliableUpdates(Block.fenceGate.blockID);
        this.FlagBlockForReliableUpdates(Block.redstoneLampIdle.blockID);
        this.FlagBlockForReliableUpdates(Block.redstoneLampActive.blockID);
        this.FlagBlockForReliableUpdates(Block.tripWireSource.blockID);
        this.FlagBlockForReliableUpdates(Block.tripWire.blockID);
        this.FlagBlockForReliableUpdates(fcBlockDetectorGlowingLogic.blockID);
        this.FlagBlockForReliableUpdates(fcMiningCharge.blockID);
        this.FlagBlockForReliableUpdates(fcBuddyBlock.blockID);
        this.FlagBlockForReliableUpdates(fcKiln.blockID);
        this.FlagBlockForReliableUpdates(fcLightBulbOff.blockID);
        this.FlagBlockForReliableUpdates(fcLightBulbOn.blockID);
        this.FlagBlockForReliableUpdates(fcBBQ.blockID);
        this.FlagBlockForReliableUpdates(fcHopper.blockID);
        this.FlagBlockForReliableUpdates(fcSaw.blockID);
        this.FlagBlockForReliableUpdates(fcPlatform.blockID);
        this.FlagBlockForReliableUpdates(fcCement.blockID);
        this.FlagBlockForReliableUpdates(fcPulley.blockID);
        this.FlagBlockForReliableUpdates(fcPressurePlateObsidian.blockID);
        this.FlagBlockForReliableUpdates(fcBlockDispenser.blockID);
        this.FlagBlockForReliableUpdates(fcCauldron.blockID);
        this.FlagBlockForReliableUpdates(fcDetectorRailWood.blockID);
        this.FlagBlockForReliableUpdates(fcDetectorRailObsidian.blockID);
        this.FlagBlockForReliableUpdates(fcBlockDetector.blockID);
        this.FlagBlockForReliableUpdates(fcBlockDetectorLogic.blockID);
        this.FlagBlockForReliableUpdates(fcLens.blockID);
        this.FlagBlockForReliableUpdates(fcHandCrank.blockID);
        this.FlagBlockForReliableUpdates(fcMillStone.blockID);
        this.FlagBlockForReliableUpdates(fcAxleBlock.blockID);
        this.FlagBlockForReliableUpdates(fcGearBox.blockID);
        this.FlagBlockForReliableUpdates(fcTurntable.blockID);
        this.FlagBlockForReliableUpdates(fcBellows.blockID);
        this.FlagBlockForReliableUpdates(fcStokedFire.blockID);
        this.FlagBlockForReliableUpdates(fcCrucible.blockID);
        this.FlagBlockForReliableUpdates(fcBlockScrewPump.blockID);
    }

    private void FlagBlockForReliableUpdates(int var1)
    {
        m_bBlocksRequireReliableUpdates[var1] = true;
    }

    private void InitBlocksPotentialFluidSources()
    {
        for (int var1 = 1; var1 < 256; ++var1)
        {
            Block var2 = Block.blocksList[var1];

            if (var2 != null && var2 instanceof FCIBlockFluidSource)
            {
                m_bBlocksPotentialFluidSources[var1] = true;
            }
            else
            {
                m_bBlocksPotentialFluidSources[var1] = false;
            }
        }
    }

    private void InitBlocksRequireSeperateRandomUpdates()
    {
        for (int var1 = 1; var1 < 256; ++var1)
        {
            Block var2 = Block.blocksList[var1];

            if (var2 != null && var2 instanceof FCIBlockSeperateRandomUpdates)
            {
                m_bBlocksRequireSeperateRandomUpdates[var1] = true;
            }
            else
            {
                m_bBlocksRequireSeperateRandomUpdates[var1] = false;
            }
        }
    }

    private void InitBlocksRequireClientNotificationOnMetadataChange()
    {
        for (int var1 = 1; var1 < 256; ++var1)
        {
            Block var2 = Block.blocksList[var1];

            if (var2 != null && var2 instanceof FCIBlockRequireClientNotification)
            {
                m_bBlocksRequireClientNotificationOnMetadataChange[var1] = true;
            }
            else
            {
                m_bBlocksRequireClientNotificationOnMetadataChange[var1] = false;
            }
        }
    }

    public void ModInstallationIntegretityTest()
    {
        try
        {
            BlockFire.InstallationIntegrityTest();
        }
        catch (Exception var2)
        {
            System.out.println("***Better Than Wolves has not been properly installed.  Please consult the readme.txt file for installation instructions***");
            throw new RuntimeException(var2);
        }
    }

    public void addRenderer(Map var1) {}

    private void SetupCustomRenderers() {}

    private void PreloadTextures() {}

    private void SetupCustomFireProperties()
    {
        SetFirePropertiesOfBlock(fcBlockScrewPump.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcLeaves.blockID, 60, 100);
        SetFirePropertiesOfBlock(fcBloodWood.blockID, 60, 100);
        SetFirePropertiesOfBlock(fcAestheticVegetation.blockID, 60, 100);
        SetFirePropertiesOfBlock(fcHempCrop.blockID, 30, 60);
        SetFirePropertiesOfBlock(fcCompanionCube.blockID, 30, 60);
        SetFirePropertiesOfBlock(fcGearBox.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcHopper.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcSaw.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcPlatform.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcPulley.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcStokedFire.blockID, 60, 0);
        SetFirePropertiesOfBlock(fcMiningCharge.blockID, 15, 100);
        SetFirePropertiesOfBlock(fcBlockWoodSpruceSidingAndCorner.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcBlockWoodSpruceMoulding.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcBlockWoodBirchSidingAndCorner.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcBlockWoodBirchMoulding.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcBlockWoodJungleSidingAndCorner.blockID, 5, 20);
        SetFirePropertiesOfBlock(fcBlockWoodJungleMoulding.blockID, 5, 20);
    }

    private void SetupCustomToolProperties()
    {
        this.SetupCustomAxeProperties();
        this.SetupCustomPickProperties();
        this.SetupCustomShovelProperties();
    }

    private void SetupCustomAxeProperties()
    {
        SetAxeToBeEffectiveVsBlock(Block.melon);
        SetAxeToBeEffectiveVsBlock(Block.cocoaPlant);
        SetAxeToBeEffectiveVsBlock(fcBlockScrewPump);
        SetAxeToBeEffectiveVsBlock(fcBlockWoodSpruceSidingAndCorner);
        SetAxeToBeEffectiveVsBlock(fcBlockWoodSpruceMoulding);
        SetAxeToBeEffectiveVsBlock(fcBlockWoodBirchSidingAndCorner);
        SetAxeToBeEffectiveVsBlock(fcBlockWoodBirchMoulding);
        SetAxeToBeEffectiveVsBlock(fcBlockWoodJungleSidingAndCorner);
        SetAxeToBeEffectiveVsBlock(fcBlockWoodJungleMoulding);
        SetAxeToBeEffectiveVsBlock(fcLeaves);
        SetAxeToBeEffectiveVsBlock(fcBloodWood);
        SetAxeToBeEffectiveVsBlock(fcAestheticVegetation);
        SetAxeToBeEffectiveVsBlock(fcOmniSlab);
        SetAxeToBeEffectiveVsBlock(fcCorner);
        SetAxeToBeEffectiveVsBlock(fcRopeBlock);
        SetAxeToBeEffectiveVsBlock(fcAestheticNonOpaque);
        SetAxeToBeEffectiveVsBlock(fcAestheticOpaque);
        SetAxeToBeEffectiveVsBlock(fcAxleBlock);
        SetAxeToBeEffectiveVsBlock(fcBellows);
        SetAxeToBeEffectiveVsBlock(fcGearBox);
        SetAxeToBeEffectiveVsBlock(fcHopper);
        SetAxeToBeEffectiveVsBlock(fcPlatform);
        SetAxeToBeEffectiveVsBlock(fcPulley);
        SetAxeToBeEffectiveVsBlock(fcSaw);
        SetAxeToBeEffectiveVsBlock(fcMoulding);
    }

    private void SetupCustomPickProperties()
    {
        SetPickToBeEffectiveVsBlock(fcBlockStoneBrickSidingAndCorner);
        SetPickToBeEffectiveVsBlock(fcBlockStoneBrickMoulding);
        SetPickToBeEffectiveVsBlock(fcOmniSlab);
        SetPickToBeEffectiveVsBlock(fcCorner);
        SetPickToBeEffectiveVsBlock(fcCrucible);
        SetPickToBeEffectiveVsBlock(fcPlanter);
        SetPickToBeEffectiveVsBlock(fcAestheticNonOpaque);
        SetPickToBeEffectiveVsBlock(fcAestheticOpaque);
        SetPickToBeEffectiveVsBlock(fcDetectorRailObsidian);
        SetPickToBeEffectiveVsBlock(fcDetectorRailWood);
    }

    private void SetupCustomShovelProperties()
    {
        SetShovelToBeEffectiveVsBlock(Block.tilledField);
        SetShovelToBeEffectiveVsBlock(fcBlockFarmlandFertilized);
        SetShovelToBeEffectiveVsBlock(fcUnfiredPottery);
        SetShovelToBeEffectiveVsBlock(fcBlockDirtSlab);
    }

    public static void SetFirePropertiesOfBlock(int var0, int var1, int var2)
    {
        try
        {
            int[] var3 = (int[])((int[])getPrivateValue(BlockFire.class, Block.fire, 0));
            int[] var4 = (int[])((int[])getPrivateValue(BlockFire.class, Block.fire, 1));
            var3[var0] = var1;
            var4[var0] = var2;
        }
        catch (SecurityException var5)
        {
            throw new RuntimeException(var5);
        }
        catch (NoSuchFieldException var6)
        {
            throw new RuntimeException(var6);
        }
    }

    public static void SetPickToBeEffectiveVsBlock(Block var0)
    {
        int var1 = var0.blockID;

        try
        {
            Block[] var2 = (Block[])((Block[])getPrivateValue(ItemPickaxe.class, (Object)null, 0));
            int var3 = var2.length;
            Block[] var4 = new Block[var3 + 1];

            for (int var5 = 0; var5 < var3; ++var5)
            {
                var4[var5] = var2[var5];
            }

            var4[var3] = Block.blocksList[var1];
            Field var9 = ItemPickaxe.class.getDeclaredFields()[0];
            var9.setAccessible(true);
            var9.set((Object)null, var4);
            var9 = ItemTool.class.getDeclaredFields()[0];
            var9.setAccessible(true);
            var9.set(Item.pickaxeWood, var4);
            var9.set(Item.pickaxeStone, var4);
            var9.set(Item.pickaxeSteel, var4);
            var9.set(Item.pickaxeGold, var4);
            var9.set(Item.pickaxeDiamond, var4);
            var9.set(fcRefinedPickAxe, var4);
            var9.set(fcMattock, var4);
        }
        catch (SecurityException var6)
        {
            throw new RuntimeException(var6);
        }
        catch (NoSuchFieldException var7)
        {
            throw new RuntimeException(var7);
        }
        catch (IllegalAccessException var8)
        {
            throw new RuntimeException(var8);
        }
    }

    public static void SetAxeToBeEffectiveVsBlock(Block var0)
    {
        int var1 = var0.blockID;

        try
        {
            Block[] var2 = (Block[])((Block[])getPrivateValue(ItemAxe.class, (Object)null, 0));
            int var3 = var2.length;
            Block[] var4 = new Block[var3 + 1];

            for (int var5 = 0; var5 < var3; ++var5)
            {
                var4[var5] = var2[var5];
            }

            var4[var3] = Block.blocksList[var1];
            Field var9 = ItemAxe.class.getDeclaredFields()[0];
            var9.setAccessible(true);
            var9.set((Object)null, var4);
            var9 = ItemTool.class.getDeclaredFields()[0];
            var9.setAccessible(true);
            var9.set(Item.axeWood, var4);
            var9.set(Item.axeStone, var4);
            var9.set(Item.axeSteel, var4);
            var9.set(Item.axeGold, var4);
            var9.set(Item.axeDiamond, var4);
            var9.set(fcRefinedAxe, var4);
        }
        catch (SecurityException var6)
        {
            throw new RuntimeException(var6);
        }
        catch (NoSuchFieldException var7)
        {
            throw new RuntimeException(var7);
        }
        catch (IllegalAccessException var8)
        {
            throw new RuntimeException(var8);
        }
    }

    public static void SetShovelToBeEffectiveVsBlock(Block var0)
    {
        int var1 = var0.blockID;

        try
        {
            Block[] var2 = (Block[])((Block[])getPrivateValue(ItemSpade.class, (Object)null, 0));
            int var3 = var2.length;
            Block[] var4 = new Block[var3 + 1];

            for (int var5 = 0; var5 < var3; ++var5)
            {
                var4[var5] = var2[var5];
            }

            var4[var3] = Block.blocksList[var1];
            Field var9 = ItemSpade.class.getDeclaredFields()[0];
            var9.setAccessible(true);
            var9.set((Object)null, var4);
            var9 = ItemTool.class.getDeclaredFields()[0];
            var9.setAccessible(true);
            var9.set(Item.shovelWood, var4);
            var9.set(Item.shovelStone, var4);
            var9.set(Item.shovelSteel, var4);
            var9.set(Item.shovelGold, var4);
            var9.set(Item.shovelDiamond, var4);
            var9.set(fcRefinedShovel, var4);
        }
        catch (SecurityException var6)
        {
            throw new RuntimeException(var6);
        }
        catch (NoSuchFieldException var7)
        {
            throw new RuntimeException(var7);
        }
        catch (IllegalAccessException var8)
        {
            throw new RuntimeException(var8);
        }
    }

    public static boolean IsHardcoreBucketsEnabled(World var0)
    {
        return !var0.isRemote ? fcLocalEnableHardcoreBuckets : fcServerEnableHardcoreBuckets;
    }

    public static boolean IsHardcoreBuoyEnabled(World var0)
    {
        return !var0.isRemote ? fcLocalEnableHardcoreBuoy : fcServerEnableHardcoreBuoy;
    }

    private void RegisterCustomBlockItems()
    {
        Item.itemsList[fcWoolSlab.blockID] = new FCItemWoolSlab(fcWoolSlab.blockID - 256);
        Item.itemsList[fcOmniSlab.blockID] = new FCItemOmniSlab(fcOmniSlab.blockID - 256);
        Item.itemsList[fcCompanionCube.blockID] = new FCItemBlockCompanionCube(fcCompanionCube.blockID - 256);
        Item.itemsList[fcUnfiredPottery.blockID] = new FCItemUnfiredPottery(fcUnfiredPottery.blockID - 256);
        Item.itemsList[fcVase.blockID] = new FCItemVase(fcVase.blockID - 256);
        Item.itemsList[fcPlanter.blockID] = new FCItemPlanter(fcPlanter.blockID - 256);
        Item.itemsList[fcAestheticNonOpaque.blockID] = new FCItemBlockAestheticNonOpaque(fcAestheticNonOpaque.blockID - 256);
        Item.itemsList[fcAestheticOpaque.blockID] = new FCItemBlockAestheticOpaque(fcAestheticOpaque.blockID - 256);
        Item.itemsList[fcCorner.blockID] = new FCItemCorner(fcCorner.blockID - 256);
        Item.itemsList[fcAestheticVegetation.blockID] = new FCItemBlockAestheticVegetation(fcAestheticVegetation.blockID - 256);
        Item.itemsList[fcLeaves.blockID] = new FCItemLeaves(fcLeaves.blockID - 256);
        Item.itemsList[fcBlockDirtSlab.blockID] = new FCItemDirtSlab(fcBlockDirtSlab.blockID - 256);
        Item.itemsList[fcBlockStoneBrickSidingAndCorner.blockID] = new FCItemBlockSidingAndCorner(fcBlockStoneBrickSidingAndCorner.blockID - 256);
        Item.itemsList[fcBlockStoneBrickMoulding.blockID] = new FCItemBlockMoulding(fcBlockStoneBrickMoulding.blockID - 256);
        Item.itemsList[fcBlockWoodSpruceSidingAndCorner.blockID] = new FCItemBlockWoodSidingStub(fcBlockWoodSpruceSidingAndCorner.blockID - 256);
        Item.itemsList[fcBlockWoodSpruceMoulding.blockID] = new FCItemBlockWoodMouldingStub(fcBlockWoodSpruceMoulding.blockID - 256);
        Item.itemsList[fcBlockWoodBirchSidingAndCorner.blockID] = new FCItemBlockWoodCornerStub(fcBlockWoodBirchSidingAndCorner.blockID - 256);
        Item.itemsList[fcBlockWoodBirchMoulding.blockID] = new FCItemBlockMoulding(fcBlockWoodBirchMoulding.blockID - 256);
        Item.itemsList[fcBlockWoodJungleSidingAndCorner.blockID] = new FCItemBlockSidingAndCorner(fcBlockWoodJungleSidingAndCorner.blockID - 256);
        Item.itemsList[fcBlockWoodJungleMoulding.blockID] = new FCItemBlockMoulding(fcBlockWoodJungleMoulding.blockID - 256);
        fcBlockWoodSidingItemStubID = fcBlockWoodSpruceSidingAndCorner.blockID;
        fcBlockWoodMouldingItemStubID = fcBlockWoodSpruceMoulding.blockID;
        fcBlockWoodCornerItemStubID = fcBlockWoodBirchSidingAndCorner.blockID;
    }

    private void InitBuoyancy()
    {
        Item.itemsList[Block.planks.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.sapling.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.wood.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.leaves.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.music.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.bed.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.web.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.tallGrass.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.deadBush.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.cloth.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.plantYellow.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.plantRed.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.mushroomBrown.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.mushroomRed.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.tnt.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.bookShelf.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.torchWood.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.stairCompactPlanks.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.chest.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.workbench.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.crops.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.signPost.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.doorWood.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.ladder.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.signWall.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.pressurePlatePlanks.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.torchRedstoneIdle.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.torchRedstoneActive.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.snow.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.ice.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.blockSnow.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.cactus.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.reed.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.jukebox.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.fence.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.pumpkin.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.pumpkinLantern.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.cake.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.trapdoor.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.mushroomCapBrown.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.mushroomCapRed.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.melon.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.pumpkinStem.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.melonStem.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.vine.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.fenceGate.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.waterlily.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.woodDoubleSlab.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.woodSingleSlab.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.cocoaPlant.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.stairsWoodSpruce.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.stairsWoodBirch.blockID].SetBuoyancy(1.0F);
        Item.itemsList[Block.stairsWoodJungle.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockScrewPump.blockID].SetBuoyancy(0.0F);
        Item.itemsList[fcBlockWoodSpruceSidingAndCorner.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockWoodSpruceMoulding.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockWoodBirchSidingAndCorner.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockWoodBirchMoulding.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockWoodJungleSidingAndCorner.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockWoodJungleMoulding.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBlockWoolSlabTop.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcLeaves.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBloodWood.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcAestheticVegetation.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcWoolSlab.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcHopper.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcSaw.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcPlatform.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcPulley.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcMoulding.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcCorner.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcCompanionCube.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcHempCrop.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcRopeBlock.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcOmniSlab.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcAxleBlock.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcGearBox.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcBellows.blockID].SetBuoyancy(1.0F);
        Item.itemsList[fcVase.blockID].SetBuoyancy(1.0F);
        Item.appleRed.SetBuoyancy(1.0F);
        Item.bow.SetBuoyancy(1.0F);
        Item.arrow.SetBuoyancy(1.0F);
        Item.swordWood.SetBuoyancy(1.0F);
        Item.shovelWood.SetBuoyancy(1.0F);
        Item.pickaxeWood.SetBuoyancy(1.0F);
        Item.axeWood.SetBuoyancy(1.0F);
        Item.stick.SetBuoyancy(1.0F);
        Item.bowlEmpty.SetBuoyancy(1.0F);
        Item.bowlSoup.SetBuoyancy(1.0F);
        Item.silk.SetBuoyancy(1.0F);
        Item.feather.SetBuoyancy(1.0F);
        Item.hoeWood.SetBuoyancy(1.0F);
        Item.seeds.SetBuoyancy(1.0F);
        Item.wheat.SetBuoyancy(1.0F);
        Item.bread.SetBuoyancy(1.0F);
        Item.helmetLeather.SetBuoyancy(1.0F);
        Item.plateLeather.SetBuoyancy(1.0F);
        Item.legsLeather.SetBuoyancy(1.0F);
        Item.bootsLeather.SetBuoyancy(1.0F);
        Item.porkRaw.SetBuoyancy(1.0F);
        Item.porkCooked.SetBuoyancy(1.0F);
        Item.painting.SetBuoyancy(1.0F);
        Item.sign.SetBuoyancy(1.0F);
        Item.doorWood.SetBuoyancy(1.0F);
        Item.saddle.SetBuoyancy(1.0F);
        Item.snowball.SetBuoyancy(1.0F);
        Item.boat.SetBuoyancy(1.0F);
        Item.leather.SetBuoyancy(1.0F);
        Item.reed.SetBuoyancy(1.0F);
        Item.paper.SetBuoyancy(1.0F);
        Item.book.SetBuoyancy(1.0F);
        Item.slimeBall.SetBuoyancy(0.0F);
        Item.fishingRod.SetBuoyancy(1.0F);
        Item.fishRaw.SetBuoyancy(1.0F);
        Item.fishCooked.SetBuoyancy(1.0F);
        Item.bone.SetBuoyancy(1.0F);
        Item.sugar.SetBuoyancy(1.0F);
        Item.cake.SetBuoyancy(1.0F);
        Item.bed.SetBuoyancy(1.0F);
        Item.cookie.SetBuoyancy(1.0F);
        Item.map.SetBuoyancy(1.0F);
        Item.melon.SetBuoyancy(1.0F);
        Item.pumpkinSeeds.SetBuoyancy(1.0F);
        Item.melonSeeds.SetBuoyancy(1.0F);
        Item.beefRaw.SetBuoyancy(1.0F);
        Item.beefCooked.SetBuoyancy(1.0F);
        Item.chickenRaw.SetBuoyancy(1.0F);
        Item.chickenCooked.SetBuoyancy(1.0F);
        Item.beefRaw.SetBuoyancy(1.0F);
        Item.rottenFlesh.SetBuoyancy(1.0F);
        Item.beefRaw.SetBuoyancy(1.0F);
        Item.netherStalkSeeds.SetBuoyancy(1.0F);
        Item.potion.SetBuoyancy(0.0F);
        Item.glassBottle.SetBuoyancy(1.0F);
        Item.spiderEye.SetBuoyancy(0.0F);
        Item.fermentedSpiderEye.SetBuoyancy(0.0F);
        Item.magmaCream.SetBuoyancy(0.0F);
        Item.writableBook.SetBuoyancy(1.0F);
        Item.writtenBook.SetBuoyancy(1.0F);
        fcWolfRaw.SetBuoyancy(1.0F);
        fcWolfCooked.SetBuoyancy(1.0F);
        fcHempSeeds.SetBuoyancy(1.0F);
        fcHemp.SetBuoyancy(1.0F);
        fcGear.SetBuoyancy(1.0F);
        fcFlour.SetBuoyancy(1.0F);
        fcHempFibers.SetBuoyancy(1.0F);
        fcScouredLeather.SetBuoyancy(1.0F);
        fcDonut.SetBuoyancy(1.0F);
        fcRopeItem.SetBuoyancy(1.0F);
        fcRollersItem.SetBuoyancy(1.0F);
        fcDung.SetBuoyancy(1.0F);
        fcWaterWheelItem.SetBuoyancy(1.0F);
        fcWindMillBladeItem.SetBuoyancy(1.0F);
        fcWindMillItem.SetBuoyancy(1.0F);
        fcHempCloth.SetBuoyancy(1.0F);
        fcGrate.SetBuoyancy(1.0F);
        fcWicker.SetBuoyancy(1.0F);
        fcTannedLeather.SetBuoyancy(1.0F);
        fcStrap.SetBuoyancy(1.0F);
        fcBelt.SetBuoyancy(1.0F);
        fcFoulFood.SetBuoyancy(1.0F);
        fcWoodBlade.SetBuoyancy(1.0F);
        fcGlue.SetBuoyancy(0.0F);
        fcTallow.SetBuoyancy(1.0F);
        fcHaft.SetBuoyancy(1.0F);
        fcCompositeBow.SetBuoyancy(1.0F);
        fcPadding.SetBuoyancy(1.0F);
        fcUrn.SetBuoyancy(1.0F);
        fcSoulUrn.SetBuoyancy(1.0F);
        fcHardBoiledEgg.SetBuoyancy(0.0F);
        fcSoap.SetBuoyancy(1.0F);
        fcSawDust.SetBuoyancy(1.0F);
        fcTannedLeatherHelm.SetBuoyancy(1.0F);
        fcTannedLeatherChest.SetBuoyancy(1.0F);
        fcTannedLeatherLeggings.SetBuoyancy(1.0F);
        fcTannedLeatherBoots.SetBuoyancy(1.0F);
        fcDynamite.SetBuoyancy(1.0F);
        fcBreedingHarness.SetBuoyancy(1.0F);
        fcSoulDust.SetBuoyancy(1.0F);
        fcNetherSludge.SetBuoyancy(0.0F);
        fcArcaneScroll.SetBuoyancy(1.0F);
        fcCandleItem.SetBuoyancy(1.0F);
        fcItemBloodMossSpores.SetBuoyancy(1.0F);
        fcItemCanvas.SetBuoyancy(1.0F);
        fcItemDogFood.SetBuoyancy(1.0F);
        fcItemRawEgg.SetBuoyancy(1.0F);
        fcItemFriedEgg.SetBuoyancy(1.0F);
        fcItemRottenArrow.SetBuoyancy(1.0F);
    }

    private static void InitBellowsBlowDistance()
    {
        Item.arrow.SetBellowsBlowDistance(1);
        Item.wheat.SetBellowsBlowDistance(1);
        Item.feather.SetBellowsBlowDistance(3);
        Item.map.SetBellowsBlowDistance(3);
        Item.silk.SetBellowsBlowDistance(2);
        Item.paper.SetBellowsBlowDistance(3);
        Item.seeds.SetBellowsBlowDistance(2);
        Item.gunpowder.SetBellowsBlowDistance(3);
        Item.redstone.SetBellowsBlowDistance(3);
        Item.lightStoneDust.SetBellowsBlowDistance(3);
        Item.dyePowder.SetBellowsBlowDistance(2);
        Item.sugar.SetBellowsBlowDistance(3);
        Item.pumpkinSeeds.SetBellowsBlowDistance(2);
        Item.melonSeeds.SetBellowsBlowDistance(2);
        Item.netherStalkSeeds.SetBellowsBlowDistance(1);
        Item.blazePowder.SetBellowsBlowDistance(3);
        fcHemp.SetBellowsBlowDistance(2);
        fcHempFibers.SetBellowsBlowDistance(2);
        fcFilament.SetBellowsBlowDistance(1);
        fcHempCloth.SetBellowsBlowDistance(1);
        fcStrap.SetBellowsBlowDistance(1);
        fcHempSeeds.SetBellowsBlowDistance(2);
        fcFlour.SetBellowsBlowDistance(3);
        fcGroundNetherrack.SetBellowsBlowDistance(2);
        fcHellfireDust.SetBellowsBlowDistance(3);
        fcCoalDust.SetBellowsBlowDistance(3);
        fcPotash.SetBellowsBlowDistance(3);
        fcSawDust.SetBellowsBlowDistance(3);
        fcSoulDust.SetBellowsBlowDistance(3);
        fcItemRottenArrow.SetBellowsBlowDistance(1);
        fcArcaneScroll.SetBellowsBlowDistance(3);
    }

    private static int ValidateIDFromFile(int var0, int var1)
    {
        return var1 > 0 ? var1 : var0;
    }

    private static void ReadModConfigFile()
    {
        File var0 = new File(new File("."), "BTWConfig.txt");

        try
        {
            if (!var0.exists())
            {
                MinecraftServer.getServer();
                MinecraftServer.logger.info("BTW config file not found...");
                return;
            }

            MinecraftServer.getServer();
            MinecraftServer.logger.info("BTW reading custom config file...");
            BufferedReader var1 = new BufferedReader(new FileReader(var0));
            String var2 = "";

            while ((var2 = var1.readLine()) != null)
            {
                String[] var3 = var2.split("=");
                int var4;

                for (var4 = 0; var4 < var3.length; ++var4)
                {
                    var3[var4] = var3[var4].trim();
                }

                if (var3[0].equals("fcDisableMinecartChanges"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcDisableMinecartChanges = false;
                    }
                    else
                    {
                        fcDisableMinecartChanges = true;
                    }
                }
                else if (var3[0].equals("fcFaceGearBoxAwayFromPlayer"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcFaceGearBoxAwayFromPlayer = false;
                    }
                    else
                    {
                        fcFaceGearBoxAwayFromPlayer = true;
                    }
                }
                else if (var3[0].equals("fcEnableHardcoreBuckets"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcLocalEnableHardcoreBuckets = false;
                    }
                    else
                    {
                        fcLocalEnableHardcoreBuckets = true;
                    }
                }
                else if (var3[0].equals("fcEnableHardcoreBuoy"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcLocalEnableHardcoreBuoy = false;
                    }
                    else
                    {
                        fcLocalEnableHardcoreBuoy = true;
                    }
                }
                else if (var3[0].equals("fcLimitPigMenAndMagmaCubeSpawning"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcLimitPigMenAndMagmaCubeSpawning = false;
                    }
                    else
                    {
                        fcLimitPigMenAndMagmaCubeSpawning = true;
                    }
                }
                else if (var3[0].equals("fcLimitSlimeSpawning"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcLimitSlimeSpawning = false;
                    }
                    else
                    {
                        fcLimitSlimeSpawning = true;
                    }
                }
                else if (var3[0].equals("fcLimitSquidSpawning"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcLimitSquidSpawning = false;
                    }
                    else
                    {
                        fcLimitSquidSpawning = true;
                    }
                }
                else if (var3[0].equals("fcDisableGearBoxPowerDrain"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcDisableGearBoxPowerDrain = false;
                    }
                    else
                    {
                        fcDisableGearBoxPowerDrain = true;
                    }
                }
                else if (var3[0].equals("fcEnableHardcoreChests"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcEnableHardcoreChests = false;
                    }
                    else
                    {
                        fcEnableHardcoreChests = true;
                    }
                }
                else if (var3[0].equals("fcDisableEndText"))
                {
                    var4 = Integer.parseInt(var3[1]);

                    if (var4 == 0)
                    {
                        fcDisableEndText = false;
                    }
                    else
                    {
                        fcDisableEndText = true;
                    }
                }
                else if (var3[0].equals("fcBlockScrewPumpID"))
                {
                    fcBlockScrewPumpID = ValidateIDFromFile(fcBlockScrewPumpID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoodSpruceSidingAndCornerID"))
                {
                    fcBlockWoodSpruceSidingAndCornerID = ValidateIDFromFile(fcBlockWoodSpruceSidingAndCornerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoodSpruceMouldingID"))
                {
                    fcBlockWoodSpruceMouldingID = ValidateIDFromFile(fcBlockWoodSpruceMouldingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoodBirchSidingAndCornerID"))
                {
                    fcBlockWoodBirchSidingAndCornerID = ValidateIDFromFile(fcBlockWoodBirchSidingAndCornerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoodBirchMouldingID"))
                {
                    fcBlockWoodBirchMouldingID = ValidateIDFromFile(fcBlockWoodBirchMouldingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoodJungleSidingAndCornerID"))
                {
                    fcBlockWoodJungleSidingAndCornerID = ValidateIDFromFile(fcBlockWoodJungleSidingAndCornerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoodJungleMouldingID"))
                {
                    fcBlockWoodJungleMouldingID = ValidateIDFromFile(fcBlockWoodJungleMouldingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockStoneBrickSidingAndCornerID"))
                {
                    fcBlockStoneBrickSidingAndCornerID = ValidateIDFromFile(fcBlockStoneBrickSidingAndCornerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockStoneBrickMouldingID"))
                {
                    fcBlockStoneBrickMouldingID = ValidateIDFromFile(fcBlockStoneBrickMouldingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockFarmlandFertilizedID"))
                {
                    fcBlockFarmlandFertilizedID = ValidateIDFromFile(fcBlockFarmlandFertilizedID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockWoolSlabTopID"))
                {
                    fcBlockWoolSlabTopID = ValidateIDFromFile(fcBlockWoolSlabTopID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockDirtSlabID"))
                {
                    fcBlockDirtSlabID = ValidateIDFromFile(fcBlockDirtSlabID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockNetherGrowthID"))
                {
                    fcBlockBloodMossID = ValidateIDFromFile(fcBlockBloodMossID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcInfernalEnchanterID"))
                {
                    fcInfernalEnchanterID = ValidateIDFromFile(fcInfernalEnchanterID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSoulforgedSteelBlockID"))
                {
                    fcSoulforgedSteelBlockID = ValidateIDFromFile(fcSoulforgedSteelBlockID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockDetectorGlowingLogicID"))
                {
                    fcBlockDetectorGlowingLogicID = ValidateIDFromFile(fcBlockDetectorGlowingLogicID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcLeavesID"))
                {
                    fcLeavesID = ValidateIDFromFile(fcLeavesID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBloodWoodID"))
                {
                    fcBloodWoodID = ValidateIDFromFile(fcBloodWoodID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAestheticVegetationID"))
                {
                    fcAestheticVegetationID = ValidateIDFromFile(fcAestheticVegetationID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcStoneMouldingID"))
                {
                    fcStoneMouldingID = ValidateIDFromFile(fcStoneMouldingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAestheticOpaqueID"))
                {
                    fcAestheticOpaqueID = ValidateIDFromFile(fcAestheticOpaqueID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAestheticNonOpaqueID"))
                {
                    fcAestheticNonOpaqueID = ValidateIDFromFile(fcAestheticNonOpaqueID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMiningChargeID"))
                {
                    fcMiningChargeID = ValidateIDFromFile(fcMiningChargeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBuddyBlockID"))
                {
                    fcBuddyBlockID = ValidateIDFromFile(fcBuddyBlockID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcKilnID"))
                {
                    fcKilnID = ValidateIDFromFile(fcKilnID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWoolSlabID"))
                {
                    fcWoolSlabID = ValidateIDFromFile(fcWoolSlabID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAnvilID"))
                {
                    fcAnvilID = ValidateIDFromFile(fcAnvilID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcLightBulbOffID"))
                {
                    fcLightBulbOffID = ValidateIDFromFile(fcLightBulbOffID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcLightBulbOnID"))
                {
                    fcLightBulbOnID = ValidateIDFromFile(fcLightBulbOnID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBBQID"))
                {
                    fcBBQID = ValidateIDFromFile(fcBBQID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHopperID"))
                {
                    fcHopperID = ValidateIDFromFile(fcHopperID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSawID"))
                {
                    fcSawID = ValidateIDFromFile(fcSawID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPlatformID"))
                {
                    fcPlatformID = ValidateIDFromFile(fcPlatformID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCementID"))
                {
                    fcCementID = ValidateIDFromFile(fcCementID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPulleyID"))
                {
                    fcPulleyID = ValidateIDFromFile(fcPulleyID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPressurePlateObsidianID"))
                {
                    fcPressurePlateObsidianID = ValidateIDFromFile(fcPressurePlateObsidianID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMouldingID"))
                {
                    fcMouldingID = ValidateIDFromFile(fcMouldingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCornerID"))
                {
                    fcCornerID = ValidateIDFromFile(fcCornerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockDispenserID"))
                {
                    fcBlockDispenserID = ValidateIDFromFile(fcBlockDispenserID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCauldronID"))
                {
                    fcCauldronID = ValidateIDFromFile(fcCauldronID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcDetectorRailWoodID"))
                {
                    fcDetectorRailWoodID = ValidateIDFromFile(fcDetectorRailWoodID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcDetectorRailObsidianID"))
                {
                    fcDetectorRailObsidianID = ValidateIDFromFile(fcDetectorRailObsidianID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCompanionCubeID"))
                {
                    fcCompanionCubeID = ValidateIDFromFile(fcCompanionCubeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockDetectorID"))
                {
                    fcBlockDetectorID = ValidateIDFromFile(fcBlockDetectorID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockDetectorLogicID"))
                {
                    fcBlockDetectorLogicID = ValidateIDFromFile(fcBlockDetectorLogicID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockLensID"))
                {
                    fcBlockLensID = ValidateIDFromFile(fcBlockLensID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHempCropID"))
                {
                    fcHempCropID = ValidateIDFromFile(fcHempCropID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHandCrankID"))
                {
                    fcHandCrankID = ValidateIDFromFile(fcHandCrankID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMillStoneID"))
                {
                    fcMillStoneID = ValidateIDFromFile(fcMillStoneID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAnchorID"))
                {
                    fcAnchorID = ValidateIDFromFile(fcAnchorID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRopeBlockID"))
                {
                    fcRopeBlockID = ValidateIDFromFile(fcRopeBlockID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcOmniSlabID"))
                {
                    fcOmniSlabID = ValidateIDFromFile(fcOmniSlabID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAxleBlockID"))
                {
                    fcAxleBlockID = ValidateIDFromFile(fcAxleBlockID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcGearBoxID"))
                {
                    fcGearBoxID = ValidateIDFromFile(fcGearBoxID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTurntableID"))
                {
                    fcTurntableID = ValidateIDFromFile(fcTurntableID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBellowsID"))
                {
                    fcBellowsID = ValidateIDFromFile(fcBellowsID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcStokedFireID"))
                {
                    fcStokedFireID = ValidateIDFromFile(fcStokedFireID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcUnfiredPotteryID"))
                {
                    fcUnfiredPotteryID = ValidateIDFromFile(fcUnfiredPotteryID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCrucibleID"))
                {
                    fcCrucibleID = ValidateIDFromFile(fcCrucibleID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPlanterID"))
                {
                    fcPlanterID = ValidateIDFromFile(fcPlanterID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcVaseID"))
                {
                    fcVaseID = ValidateIDFromFile(fcVaseID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBucketCeme1ntID"))
                {
                    fcBucketCeme1ntID = ValidateIDFromFile(fcBucketCeme1ntID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWolfRawID"))
                {
                    fcWolfRawID = ValidateIDFromFile(fcWolfRawID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWolfCookedID"))
                {
                    fcWolfCookedID = ValidateIDFromFile(fcWolfCookedID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcNethercoalID"))
                {
                    fcNethercoalID = ValidateIDFromFile(fcNethercoalID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHempSeedsID"))
                {
                    fcHempSeedsID = ValidateIDFromFile(fcHempSeedsID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHempID"))
                {
                    fcHempID = ValidateIDFromFile(fcHempID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcGearID"))
                {
                    fcGearID = ValidateIDFromFile(fcGearID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcFlourID"))
                {
                    fcFlourID = ValidateIDFromFile(fcFlourID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHempFibersID"))
                {
                    fcHempFibersID = ValidateIDFromFile(fcHempFibersID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcScouredLeatherID"))
                {
                    fcScouredLeatherID = ValidateIDFromFile(fcScouredLeatherID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcDonutID"))
                {
                    fcDonutID = ValidateIDFromFile(fcDonutID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRopeItemID"))
                {
                    fcRopeItemID = ValidateIDFromFile(fcRopeItemID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRollersItemID"))
                {
                    fcRollersItemID = ValidateIDFromFile(fcRollersItemID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcDungID"))
                {
                    fcDungID = ValidateIDFromFile(fcDungID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWaterWheelItemID"))
                {
                    fcWaterWheelItemID = ValidateIDFromFile(fcWaterWheelItemID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWindMillBladeItemID"))
                {
                    fcWindMillBladeItemID = ValidateIDFromFile(fcWindMillBladeItemID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWindMillItemID"))
                {
                    fcWindMillItemID = ValidateIDFromFile(fcWindMillItemID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHempClothID"))
                {
                    fcHempClothID = ValidateIDFromFile(fcHempClothID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcGrateID"))
                {
                    fcGrateID = ValidateIDFromFile(fcGrateID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWickerID"))
                {
                    fcWickerID = ValidateIDFromFile(fcWickerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTannedLeatherID"))
                {
                    fcTannedLeatherID = ValidateIDFromFile(fcTannedLeatherID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcStrapID"))
                {
                    fcStrapID = ValidateIDFromFile(fcStrapID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBeltID"))
                {
                    fcBeltID = ValidateIDFromFile(fcBeltID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcFoulFoodID"))
                {
                    fcFoulFoodID = ValidateIDFromFile(fcFoulFoodID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWoodBladeID"))
                {
                    fcWoodBladeID = ValidateIDFromFile(fcWoodBladeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcGlueID"))
                {
                    fcGlueID = ValidateIDFromFile(fcGlueID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTallowID"))
                {
                    fcTallowID = ValidateIDFromFile(fcTallowID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHaftID"))
                {
                    fcHaftID = ValidateIDFromFile(fcHaftID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSteelID"))
                {
                    fcSteelID = ValidateIDFromFile(fcSteelID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRefinedPickAxeID"))
                {
                    fcRefinedPickAxeID = ValidateIDFromFile(fcRefinedPickAxeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRefinedShovelID"))
                {
                    fcRefinedShovelID = ValidateIDFromFile(fcRefinedShovelID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRefinedHoeID"))
                {
                    fcRefinedHoeID = ValidateIDFromFile(fcRefinedHoeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBattleAxeID"))
                {
                    fcBattleAxeID = ValidateIDFromFile(fcBattleAxeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRefinedSwordID"))
                {
                    fcRefinedSwordID = ValidateIDFromFile(fcRefinedSwordID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcGroundNetherrackID"))
                {
                    fcGroundNetherrackID = ValidateIDFromFile(fcGroundNetherrackID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHellfireDustID"))
                {
                    fcHellfireDustID = ValidateIDFromFile(fcHellfireDustID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcConcentratedHellfireID"))
                {
                    fcConcentratedHellfireID = ValidateIDFromFile(fcConcentratedHellfireID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcArmorPlateID"))
                {
                    fcArmorPlateID = ValidateIDFromFile(fcArmorPlateID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPlateHelmID"))
                {
                    fcPlateHelmID = ValidateIDFromFile(fcPlateHelmID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPlateBreastPlateID"))
                {
                    fcPlateBreastPlateID = ValidateIDFromFile(fcPlateBreastPlateID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPlateLeggingsID"))
                {
                    fcPlateLeggingsID = ValidateIDFromFile(fcPlateLeggingsID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPlateBootsID"))
                {
                    fcPlateBootsID = ValidateIDFromFile(fcPlateBootsID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCompositeBowID"))
                {
                    fcCompositeBowID = ValidateIDFromFile(fcCompositeBowID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBroadheadArrowheadID"))
                {
                    fcBroadheadArrowheadID = ValidateIDFromFile(fcBroadheadArrowheadID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBroadheadArrowID"))
                {
                    fcBroadheadArrowID = ValidateIDFromFile(fcBroadheadArrowID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCoalDustID"))
                {
                    fcCoalDustID = ValidateIDFromFile(fcCoalDustID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPaddingID"))
                {
                    fcPaddingID = ValidateIDFromFile(fcPaddingID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcFilamentID"))
                {
                    fcFilamentID = ValidateIDFromFile(fcFilamentID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPolishedLapisID"))
                {
                    fcPolishedLapisID = ValidateIDFromFile(fcPolishedLapisID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcUrnID"))
                {
                    fcUrnID = ValidateIDFromFile(fcUrnID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSoulUrnID"))
                {
                    fcSoulUrnID = ValidateIDFromFile(fcSoulUrnID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHardBoiledEggID"))
                {
                    fcHardBoiledEggID = ValidateIDFromFile(fcHardBoiledEggID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPotashID"))
                {
                    fcPotashID = ValidateIDFromFile(fcPotashID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSoapID"))
                {
                    fcSoapID = ValidateIDFromFile(fcSoapID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSawDustID"))
                {
                    fcSawDustID = ValidateIDFromFile(fcSawDustID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTannedLeatherHelmID"))
                {
                    fcTannedLeatherHelmID = ValidateIDFromFile(fcTannedLeatherHelmID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTannedLeatherChestID"))
                {
                    fcTannedLeatherChestID = ValidateIDFromFile(fcTannedLeatherChestID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTannedLeatherLeggingsID"))
                {
                    fcTannedLeatherLeggingsID = ValidateIDFromFile(fcTannedLeatherLeggingsID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcTannedLeatherBootsID"))
                {
                    fcTannedLeatherBootsID = ValidateIDFromFile(fcTannedLeatherBootsID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcDynamiteID"))
                {
                    fcDynamiteID = ValidateIDFromFile(fcDynamiteID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBreedingHarnessID"))
                {
                    fcBreedingHarnessID = ValidateIDFromFile(fcBreedingHarnessID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcSoulDustID"))
                {
                    fcSoulDustID = ValidateIDFromFile(fcSoulDustID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMattockID"))
                {
                    fcMattockID = ValidateIDFromFile(fcMattockID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRefinedAxeID"))
                {
                    fcRefinedAxeID = ValidateIDFromFile(fcRefinedAxeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcNetherSludgeID"))
                {
                    fcNetherSludgeID = ValidateIDFromFile(fcNetherSludgeID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcNetherBrickID"))
                {
                    fcNetherBrickID = ValidateIDFromFile(fcNetherBrickID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemTuningForkID"))
                {
                    fcItemTuningForkID = ValidateIDFromFile(fcItemTuningForkID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemArcaneScrollID"))
                {
                    fcItemArcaneScrollID = ValidateIDFromFile(fcItemArcaneScrollID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemCandleID"))
                {
                    fcItemCandleID = ValidateIDFromFile(fcItemCandleID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemNetherGrowthSporesID"))
                {
                    fcItemBloodMossSporesID = ValidateIDFromFile(fcItemBloodMossSporesID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemMouldID"))
                {
                    fcItemMouldID = ValidateIDFromFile(fcItemMouldID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemCanvasID"))
                {
                    fcItemCanvasID = ValidateIDFromFile(fcItemCanvasID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemDogFoodID"))
                {
                    fcItemDogFoodID = ValidateIDFromFile(fcItemDogFoodID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemRawEggID"))
                {
                    fcItemRawEggID = ValidateIDFromFile(fcItemRawEggID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemFriedEggID"))
                {
                    fcItemFriedEggID = ValidateIDFromFile(fcItemFriedEggID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemScrewID"))
                {
                    fcItemScrewID = ValidateIDFromFile(fcItemScrewID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemRottenArrowID"))
                {
                    fcItemRottenArrowID = ValidateIDFromFile(fcItemRottenArrowID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWaterWheelEntityID"))
                {
                    fcWaterWheelEntityID = ValidateIDFromFile(fcWaterWheelEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcWindMillEntityID"))
                {
                    fcWindMillEntityID = ValidateIDFromFile(fcWindMillEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMovingAnchorEntityID"))
                {
                    fcMovingAnchorEntityID = ValidateIDFromFile(fcMovingAnchorEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMovingPlatformEntityID"))
                {
                    fcMovingPlatformEntityID = ValidateIDFromFile(fcMovingPlatformEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockLiftedByPlatformEntityID"))
                {
                    fcBlockLiftedByPlatformEntityID = ValidateIDFromFile(fcBlockLiftedByPlatformEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBroadheadArrowEntityID"))
                {
                    fcBroadheadArrowEntityID = ValidateIDFromFile(fcBroadheadArrowEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcUrnEntityID"))
                {
                    fcUrnEntityID = ValidateIDFromFile(fcUrnEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcDynamiteEntityID"))
                {
                    fcDynamiteEntityID = ValidateIDFromFile(fcDynamiteEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMiningChargeEntityID"))
                {
                    fcMiningChargeEntityID = ValidateIDFromFile(fcMiningChargeEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcInfiniteArrowEntityID"))
                {
                    fcInfiniteArrowEntityID = ValidateIDFromFile(fcInfiniteArrowEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemFloatingEntityID"))
                {
                    fcItemFloatingEntityID = ValidateIDFromFile(fcItemFloatingEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcItemBloodWoodSaplingEntityID"))
                {
                    fcItemBloodWoodSaplingEntityID = ValidateIDFromFile(fcItemBloodWoodSaplingEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCanvasEntityID"))
                {
                    fcCanvasEntityID = ValidateIDFromFile(fcCanvasEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcRottenArrowEntityID"))
                {
                    fcRottenArrowEntityID = ValidateIDFromFile(fcRottenArrowEntityID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcMillStoneContainerID"))
                {
                    fcMillStoneContainerID = ValidateIDFromFile(fcMillStoneContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCauldronContainerID"))
                {
                    fcCauldronContainerID = ValidateIDFromFile(fcCauldronContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcHopperContainerID"))
                {
                    fcHopperContainerID = ValidateIDFromFile(fcHopperContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcCrucibleContainerID"))
                {
                    fcCrucibleContainerID = ValidateIDFromFile(fcCrucibleContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcAnvilContainerID"))
                {
                    fcAnvilContainerID = ValidateIDFromFile(fcAnvilContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcBlockDispenserContainerID"))
                {
                    fcBlockDispenserContainerID = ValidateIDFromFile(fcBlockDispenserContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcPulleyContainerID"))
                {
                    fcPulleyContainerID = ValidateIDFromFile(fcPulleyContainerID, Integer.parseInt(var3[1]));
                }
                else if (var3[0].equals("fcInfernalEnchanterContainerID"))
                {
                    fcInfernalEnchanterContainerID = ValidateIDFromFile(fcInfernalEnchanterContainerID, Integer.parseInt(var3[1]));
                }
            }

            var1.close();
        }
        catch (Exception var5)
        {
            System.out.println("Failed to load Better Than Wolves config file");
            var5.printStackTrace();
        }
    }

    private static void InstantiateBlocksAndItems()
    {
        fcBlockScrewPump = new FCBlockScrewPump(fcBlockScrewPumpID);
        fcBlockWoodSpruceSidingAndCorner = new FCBlockSidingAndCorner(fcBlockWoodSpruceSidingAndCornerID, fcWoodMaterial, 198, 2.0F, 5.0F, Block.soundWoodFootstep, "fcWoodSpruceSiding");
        fcBlockWoodSpruceMoulding = new FCBlockMoulding(fcBlockWoodSpruceMouldingID, fcWoodMaterial, 198, fcBlockWoodSpruceSidingAndCornerID, 2.0F, 5.0F, Block.soundWoodFootstep, "fcWoodSpruceMoulding");
        fcBlockWoodBirchSidingAndCorner = new FCBlockSidingAndCorner(fcBlockWoodBirchSidingAndCornerID, fcWoodMaterial, 214, 2.0F, 5.0F, Block.soundWoodFootstep, "fcWoodBirchSiding");
        fcBlockWoodBirchMoulding = new FCBlockMoulding(fcBlockWoodBirchMouldingID, fcWoodMaterial, 214, fcBlockWoodBirchSidingAndCornerID, 2.0F, 5.0F, Block.soundWoodFootstep, "fcWoodBirchMoulding");
        fcBlockWoodJungleSidingAndCorner = new FCBlockSidingAndCorner(fcBlockWoodJungleSidingAndCornerID, fcWoodMaterial, 199, 2.0F, 5.0F, Block.soundWoodFootstep, "fcWoodJungleSiding");
        fcBlockWoodJungleMoulding = new FCBlockMoulding(fcBlockWoodJungleMouldingID, fcWoodMaterial, 199, fcBlockWoodJungleSidingAndCornerID, 2.0F, 5.0F, Block.soundWoodFootstep, "fcWoodJungleMoulding");
        fcBlockStoneBrickSidingAndCorner = new FCBlockSidingAndCorner(fcBlockStoneBrickSidingAndCornerID, Material.rock, 54, 1.5F, 10.0F, Block.soundStoneFootstep, "fcStoneBrickSiding");
        fcBlockStoneBrickMoulding = new FCBlockMoulding(fcBlockStoneBrickMouldingID, Material.rock, 54, fcBlockStoneBrickSidingAndCornerID, 1.5F, 10.0F, Block.soundStoneFootstep, "fcStoneBrickMoulding");
        fcBlockFarmlandFertilized = new FCBlockFarmlandFertilized(fcBlockFarmlandFertilizedID);
        fcBlockWoolSlabTop = new FCBlockWoolSlab(fcBlockWoolSlabTopID, true);
        fcBlockDirtSlab = new FCBlockDirtSlab(fcBlockDirtSlabID);
        fcBlockBloodMoss = new FCBlockBloodMoss(fcBlockBloodMossID);
        fcInfernalEnchanter = new FCBlockInfernalEnchanter(fcInfernalEnchanterID);
        fcSoulforgedSteelBlock = new FCBlockSoulforgedSteel(fcSoulforgedSteelBlockID);
        fcBlockDetectorGlowingLogic = (new FCBlockDetectorLogicGlowing(fcBlockDetectorGlowingLogicID)).setBlockName("fcBlockDetectorGlowingLogic");
        fcLeaves = new FCBlockLeaves(fcLeavesID);
        fcBloodWood = new FCBlockBloodWood(fcBloodWoodID);
        fcAestheticVegetation = new FCBlockAestheticVegetation(fcAestheticVegetationID);
        fcStoneMoulding = new FCBlockMoulding(fcStoneMouldingID, Material.rock, 1, fcCornerID, 1.5F, 10.0F, Block.soundStoneFootstep, "fcStoneMoulding");
        fcAestheticOpaque = new FCBlockAestheticOpaque(fcAestheticOpaqueID);
        fcAestheticNonOpaque = new FCBlockAestheticNonOpaque(fcAestheticNonOpaqueID);
        fcMiningCharge = new FCBlockMiningCharge(fcMiningChargeID);
        fcBuddyBlock = new FCBlockBuddyBlock(fcBuddyBlockID);
        fcKiln = new FCBlockKiln(fcKilnID);
        fcWoolSlab = new FCBlockWoolSlab(fcWoolSlabID, false);
        fcAnvil = new FCBlockAnvil(fcAnvilID);
        fcLightBulbOff = new FCBlockLightBulb(fcLightBulbOffID, false);
        fcLightBulbOn = new FCBlockLightBulb(fcLightBulbOnID, true);
        fcBBQ = new FCBlockBBQ(fcBBQID);
        fcHopper = new FCBlockHopper(fcHopperID);
        fcSaw = new FCBlockSaw(fcSawID);
        fcPlatform = new FCBlockPlatform(fcPlatformID);
        fcCement = new FCBlockCement(fcCementID);
        fcPulley = new FCBlockPulley(fcPulleyID);
        fcPressurePlateObsidian = (new BlockPressurePlate(fcPressurePlateObsidianID, 37, EnumMobType.players, Material.rock)).setHardness(0.5F).setResistance(2000.0F).setStepSound(Block.soundStoneFootstep).setBlockName("pressurePlate");
        fcMoulding = new FCBlockMoulding(fcMouldingID, fcWoodMaterial, 4, fcCornerID, 2.0F, 5.0F, Block.soundWoodFootstep, "fcMoulding");
        fcCorner = new FCBlockCorner(fcCornerID);
        fcBlockDispenser = (new FCBlockBlockDispenser(fcBlockDispenserID)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setBlockName("fcBlockDispenser");
        fcCauldron = new FCBlockCauldron(fcCauldronID);
        fcDetectorRailWood = new FCBlockDetectorRail(fcDetectorRailWoodID, 23);
        fcDetectorRailObsidian = new FCBlockDetectorRail(fcDetectorRailObsidianID, 24);
        fcCompanionCube = (new FCBlockCompanionCube(fcCompanionCubeID)).setHardness(0.4F).setStepSound(Block.soundClothFootstep);
        fcBlockDetector = (new FCBlockDetectorBlock(fcBlockDetectorID)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setBlockName("fcBlockDetector");
        fcBlockDetectorLogic = (new FCBlockDetectorLogic(fcBlockDetectorLogicID)).setBlockName("fcBlockDetectorLogic");
        fcLens = new FCBlockLens(fcBlockLensID);
        fcHempCrop = new FCBlockHempCrop(fcHempCropID);
        fcHandCrank = new FCBlockHandCrank(fcHandCrankID);
        fcMillStone = new FCBlockMillStone(fcMillStoneID);
        fcAnchor = new FCBlockAnchor(fcAnchorID);
        fcRopeBlock = new FCBlockRope(fcRopeBlockID);
        fcOmniSlab = new FCBlockOmniSlab(fcOmniSlabID);
        fcAxleBlock = new FCBlockAxle(fcAxleBlockID);
        fcGearBox = new FCBlockGearBox(fcGearBoxID);
        fcTurntable = new FCBlockTurntable(fcTurntableID);
        fcBellows = new FCBlockBellows(fcBellowsID);
        fcStokedFire = new FCBlockStokedFire(fcStokedFireID);
        fcUnfiredPottery = new FCBlockUnfiredPottery(fcUnfiredPotteryID);
        fcCrucible = new FCBlockCrucible(fcCrucibleID);
        fcPlanter = new FCBlockPlanter(fcPlanterID);
        fcVase = new FCBlockVase(fcVaseID);
        fcBucketCement = new FCItemBucketCement(fcBucketCeme1ntID, fcCement.blockID);
        fcWolfRaw = (new ItemFood(fcWolfRawID, 3, 0.3F, false)).setIconCoord(7, 5).setItemName("fcWolfRraw");
        fcWolfCooked = (new ItemFood(fcWolfCookedID, 8, 0.8F, false)).setIconCoord(8, 5).setItemName("fcWolfCooked");
        fcNethercoal = (new FCItem(fcNethercoalID)).setIconIndex(1).setItemName("fcNethercoal");
        fcHempSeeds = (new FCItemSeeds(fcHempSeedsID, fcHempCrop.blockID)).setIconIndex(2).setItemName("fcHempSeeds");
        fcHemp = (new FCItem(fcHempID)).setIconIndex(3).setItemName("fcHemp");
        fcGear = (new FCItem(fcGearID)).setIconIndex(4).setItemName("fcGear");
        fcFlour = (new FCItem(fcFlourID)).setIconIndex(5).setItemName("fcFlour");
        fcHempFibers = (new FCItem(fcHempFibersID)).setIconIndex(6).setItemName("fcHempFibers");
        fcScouredLeather = (new FCItem(fcScouredLeatherID)).setIconIndex(7).setItemName("fcScouredLeather");
        fcDonut = (new FCItemFood(fcDonutID, 8, 2, 0.2F, false, "fcDonut")).setAlwaysEdible();
        fcRopeItem = new FCItemRope(fcRopeItemID);
        fcRollersItem = (new FCItemSlats(fcRollersItemID)).setIconIndex(10).setItemName("fcRollers");
        fcDung = new FCItemDung(fcDungID);
        fcWaterWheelItem = new FCItemWaterWheel(fcWaterWheelItemID);
        fcWindMillBladeItem = (new FCItem(fcWindMillBladeItemID)).setIconIndex(13).setItemName("fcWindMillBlade");
        fcWindMillBladeItem.maxStackSize = 1;
        fcWindMillItem = new FCItemWindMill(fcWindMillItemID);
        fcHempCloth = (new FCItem(fcHempClothID)).setIconIndex(15).setItemName("fcHempCloth");
        fcGrate = (new FCItemGrate(fcGrateID)).setIconIndex(16).setItemName("fcGrate");
        fcWicker = (new FCItemWicker(fcWickerID)).setIconIndex(17).setItemName("fcWicker");
        fcTannedLeather = (new FCItem(fcTannedLeatherID)).setIconIndex(18).setItemName("fcTannedLeather");
        fcStrap = (new FCItem(fcStrapID)).setIconIndex(19).setItemName("fcStrap");
        fcBelt = (new FCItem(fcBeltID)).setIconIndex(20).setItemName("fcBelt");
        fcFoulFood = new FCItemFoulFood(fcFoulFoodID);
        fcWoodBlade = (new FCItem(fcWoodBladeID)).setIconIndex(22).setItemName("fcWoodBlade");
        fcGlue = (new FCItem(fcGlueID)).setIconIndex(23).setItemName("fcGlue");
        fcTallow = (new FCItem(fcTallowID)).setIconIndex(24).setItemName("fcTallow");
        fcHaft = (new FCItem(fcHaftID)).setIconIndex(25).setItemName("fcHaft");
        fcSteel = (new FCItem(fcSteelID)).setIconIndex(26).setItemName("fcSteel");
        fcRefinedPickAxe = new FCItemRefinedPickAxe(fcRefinedPickAxeID);
        fcRefinedShovel = new FCItemRefinedShovel(fcRefinedShovelID);
        fcRefinedHoe = new FCItemRefinedHoe(fcRefinedHoeID);
        fcBattleAxe = new FCItemBattleAxe(fcBattleAxeID);
        fcRefinedSword = new FCItemRefinedSword(fcRefinedSwordID);
        fcGroundNetherrack = (new FCItem(fcGroundNetherrackID)).setIconIndex(32).setItemName("fcGroundNetherrack");
        fcHellfireDust = (new FCItem(fcHellfireDustID)).setIconIndex(33).setItemName("fcHellfireDust");
        fcConcentratedHellfire = (new FCItem(fcConcentratedHellfireID)).setIconIndex(34).setItemName("fcConcentratedHellfire");
        fcArmorPlate = (new FCItem(fcArmorPlateID)).setIconIndex(35).setItemName("fcArmorPlate");
        fcPlateHelm = (new FCItemRefinedArmor(fcPlateHelmID, 0)).setIconIndex(36).setItemName("fcPlateHelm");
        fcPlateBreastPlate = (new FCItemRefinedArmor(fcPlateBreastPlateID, 1)).setIconIndex(37).setItemName("fcPlateBreastPlate");
        fcPlateLeggings = (new FCItemRefinedArmor(fcPlateLeggingsID, 2)).setIconIndex(38).setItemName("fcPlateLeggings");
        fcPlateBoots = (new FCItemRefinedArmor(fcPlateBootsID, 3)).setIconIndex(39).setItemName("fcPlateBoots");
        fcCompositeBow = new FCItemCompositeBow(fcCompositeBowID);
        fcBroadheadArrowhead = (new FCItem(fcBroadheadArrowheadID)).setIconIndex(41).setItemName("fcBroadheadArrowhead");
        fcBroadheadArrow = (new FCItem(fcBroadheadArrowID)).setIconIndex(42).setItemName("fcBroadheadArrow");
        fcCoalDust = (new FCItem(fcCoalDustID)).setIconIndex(43).setItemName("fcCoalDust");
        fcPadding = (new FCItem(fcPaddingID)).setIconIndex(44).setItemName("fcPadding");
        fcFilament = (new FCItem(fcFilamentID)).setIconIndex(45).setItemName("fcFilament");
        fcPolishedLapis = (new FCItem(fcPolishedLapisID)).setIconIndex(46).setItemName("fcPolishedLapis");
        fcUrn = (new FCItemUrn(fcUrnID)).setIconIndex(47).setItemName("fcUrn");
        fcSoulUrn = (new FCItemUrn(fcSoulUrnID)).setIconIndex(48).setItemName("fcSoulUrn");
        fcHardBoiledEgg = new FCItemHardBoiledEgg(fcHardBoiledEggID);
        fcPotash = (new FCItem(fcPotashID)).setIconIndex(50).setItemName("fcPotash");
        fcSoap = (new FCItem(fcSoapID)).setIconIndex(51).setItemName("fcSoap");
        fcSawDust = (new FCItem(fcSawDustID)).setIconIndex(52).setItemName("fcSawDust");
        fcTannedLeatherHelm = (new FCItemTannedArmor(fcTannedLeatherHelmID, 0)).setIconIndex(53).setItemName("fcTannedLeatherHelm");
        fcTannedLeatherChest = (new FCItemTannedArmor(fcTannedLeatherChestID, 1)).setIconIndex(54).setItemName("fcTannedLeatherChest");
        fcTannedLeatherLeggings = (new FCItemTannedArmor(fcTannedLeatherLeggingsID, 2)).setIconIndex(55).setItemName("fcTannedLeatherLeggings");
        fcTannedLeatherBoots = (new FCItemTannedArmor(fcTannedLeatherBootsID, 3)).setIconIndex(56).setItemName("fcTannedLeatherBoots");
        fcDynamite = new FCItemDynamite(fcDynamiteID);
        fcBreedingHarness = new FCItemBreedingHarness(fcBreedingHarnessID);
        fcSoulDust = (new FCItem(fcSoulDustID)).setIconIndex(62).setItemName("fcSoulDust");
        fcMattock = new FCItemMattock(fcMattockID);
        fcRefinedAxe = new FCItemRefinedAxe(fcRefinedAxeID);
        fcNetherSludge = (new FCItem(fcNetherSludgeID)).setIconIndex(65).setItemName("fcNetherSludge");
        fcNetherBrick = (new FCItem(fcNetherBrickID)).setIconIndex(66).setItemName("fcNetherBrick");
        fcTuningFork = new FCItemTuningFork(fcItemTuningForkID);
        fcArcaneScroll = new FCItemArcaneScroll(fcItemArcaneScrollID);
        fcCandleItem = new FCItemCandle(fcItemCandleID);
        fcItemBloodMossSpores = new FCItemBloodMossSpores(fcItemBloodMossSporesID);
        fcItemMould = (new FCItem(fcItemMouldID)).setIconIndex(71).setItemName("fcMouldItem");
        fcItemCanvas = new FCItemCanvas(fcItemCanvasID);
        fcItemDogFood = (new FCItemFood(fcItemDogFoodID, 73, 4, 0.4F, true, "fcDogfood")).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F);
        fcItemRawEgg = new FCItemFood(fcItemRawEggID, 74, 2, 0.2F, false, "fcRawEgg");
        fcItemFriedEgg = new FCItemFood(fcItemFriedEggID, 75, 4, 0.4F, false, "fcFriedEgg");
        fcItemScrew = (new FCItem(fcItemScrewID)).setIconIndex(76).setItemName("fcScrewItem");
        fcItemRottenArrow = (new FCItem(fcItemRottenArrowID)).setIconIndex(77).setItemName("fcRottenArrow");
        fcItemOcularOfEnder = (new FCItem(fcItemOcularOfEnderID)).setIconIndex(78).setItemName("fcOcularOfEnder");
        fcItemEnderSpectacles = (new FCItemSpecialArmor(fcItemEnderSpectaclesID, 0)).setIconIndex(79).setItemName("fcEnderSpectacles");
    }

    private static void AddFCNames() {}

    public static void registerBlock(Block var0)
    {
        int var1 = var0.blockID;
        ItemBlock var2 = null;
        var2 = new ItemBlock(var1 - 256);

        if (Block.blocksList[var1] != null && Item.itemsList[var1] == null)
        {
            Item.itemsList[var1] = var2;
        }
    }

    private void RegisterFCBlocksAndEntities()
    {
        registerBlock(fcBlockScrewPump);
        registerBlock(fcBlockStoneBrickSidingAndCorner);
        registerBlock(fcBlockStoneBrickMoulding);
        registerBlock(fcBlockFarmlandFertilized);
        registerBlock(fcBlockWoolSlabTop);
        registerBlock(fcBlockDirtSlab);
        registerBlock(fcBlockBloodMoss);
        registerBlock(fcInfernalEnchanter);
        registerBlock(fcSoulforgedSteelBlock);
        registerBlock(fcBlockDetectorGlowingLogic);
        registerBlock(fcLeaves);
        registerBlock(fcBloodWood);
        registerBlock(fcAestheticVegetation);
        registerBlock(fcStoneMoulding);
        registerBlock(fcAestheticOpaque);
        registerBlock(fcAestheticNonOpaque);
        registerBlock(fcMiningCharge);
        registerBlock(fcBuddyBlock);
        registerBlock(fcKiln);
        registerBlock(fcWoolSlab);
        registerBlock(fcAnvil);
        registerBlock(fcLightBulbOff);
        registerBlock(fcLightBulbOn);
        registerBlock(fcBBQ);
        registerBlock(fcHopper);
        registerBlock(fcSaw);
        registerBlock(fcPlatform);
        registerBlock(fcCement);
        registerBlock(fcPulley);
        registerBlock(fcPressurePlateObsidian);
        registerBlock(fcMoulding);
        registerBlock(fcCorner);
        registerBlock(fcBlockDispenser);
        registerBlock(fcCauldron);
        registerBlock(fcDetectorRailWood);
        registerBlock(fcDetectorRailObsidian);
        registerBlock(fcCompanionCube);
        registerBlock(fcBlockDetector);
        registerBlock(fcBlockDetectorLogic);
        registerBlock(fcLens);
        registerBlock(fcHempCrop);
        registerBlock(fcHandCrank);
        registerBlock(fcMillStone);
        registerBlock(fcAnchor);
        registerBlock(fcRopeBlock);
        registerBlock(fcOmniSlab);
        registerBlock(fcAxleBlock);
        registerBlock(fcGearBox);
        registerBlock(fcTurntable);
        registerBlock(fcBellows);
        registerBlock(fcStokedFire);
        registerBlock(fcUnfiredPottery);
        registerBlock(fcCrucible);
        registerBlock(fcPlanter);
        registerBlock(fcVase);
        TileEntity.addMapping(FCTileEntityCement.class, "Cement");
        TileEntity.addMapping(FCTileEntityCauldron.class, "fcCauldron");
        TileEntity.addMapping(FCTileEntityMillStone.class, "MillStone");
        TileEntity.addMapping(FCTileEntityHopper.class, "Hopper");
        TileEntity.addMapping(FCTileEntityBlockDispenser.class, "BlockDispenser");
        TileEntity.addMapping(FCTileEntityPulley.class, "Pulley");
        TileEntity.addMapping(FCTileEntityTurntable.class, "Turntable");
        TileEntity.addMapping(FCTileEntityVase.class, "Vase");
        TileEntity.addMapping(FCTileEntityCrucible.class, "Crucible");
        TileEntity.addMapping(FCTileEntityInfernalEnchanter.class, "fcInfernalEnchanter");
        TileEntity.addMapping(FCTileEntityAnvil.class, "fcAnvil");
        EntityList.addMapping(FCEntityWaterWheel.class, "WaterWheel", fcWaterWheelEntityID);
        EntityList.addMapping(FCEntityWindMill.class, "WindMill", fcWindMillEntityID);
        EntityList.addMapping(FCEntityMovingAnchor.class, "MovingAnchor", fcMovingAnchorEntityID);
        EntityList.addMapping(FCEntityMovingPlatform.class, "MovingPlatform", fcMovingPlatformEntityID);
        EntityList.addMapping(FCEntityBlockLiftedByPlatform.class, "BlockLiftedByPlatform", fcBlockLiftedByPlatformEntityID);
        EntityList.addMapping(FCEntityBroadheadArrow.class, "BroadheadArrow", fcBroadheadArrowEntityID);
        EntityList.addMapping(FCEntityUrn.class, "Urn", fcUrnEntityID);
        EntityList.addMapping(FCEntityDynamite.class, "Dynamite", fcDynamiteEntityID);
        EntityList.addMapping(FCEntityMiningCharge.class, "MiningCharge", fcMiningChargeEntityID);
        EntityList.addMapping(FCEntityInfiniteArrow.class, "fcInfiniteArrow", fcInfiniteArrowEntityID);
        EntityList.addMapping(FCEntityItemFloating.class, "fcItemFloating", fcItemFloatingEntityID);
        EntityList.addMapping(FCEntityItemBloodWoodSapling.class, "fcItemBloodWoodSapling", fcItemBloodWoodSaplingEntityID);
        EntityList.addMapping(FCEntityCanvas.class, "fcCanvas", fcCanvasEntityID);
        EntityList.addMapping(FCEntityRottenArrow.class, "fcRottenArrow", fcRottenArrowEntityID);
    }

    public void ReflectVanillaItems()
    {
        Item.bow = (new FCItemBow(5)).setIconCoord(5, 1).setItemName("bow").SetBuoyancy(1.0F);
        Item.dyePowder = (new FCItemDye(95)).setIconCoord(14, 4).setItemName("dyePowder").SetBellowsBlowDistance(2);
        Item.glassBottle = (new FCItemGlassBottle(118)).setIconCoord(12, 8).setItemName("glassBottle").SetBuoyancy(1.0F);
        Item.bucketEmpty = (new FCItemHardcoreBucket(69, 0)).setIconCoord(10, 4).setItemName("bucket").setMaxStackSize(16);
        Item.bucketWater = (new FCItemHardcoreBucket(70, Block.waterMoving.blockID)).setIconCoord(11, 4).setItemName("bucketWater").setContainerItem(Item.bucketEmpty);
        Item.bucketLava = (new FCItemHardcoreBucket(71, Block.lavaMoving.blockID)).setIconCoord(12, 4).setItemName("bucketLava").setContainerItem(Item.bucketEmpty);
    }

    public int addFuel(int var1, int var2)
    {
        return var1 == fcNethercoal.shiftedIndex ? 3200 : (var1 == fcCoalDust.shiftedIndex ? 1600 : (var1 == fcBloodWood.blockID ? 300 : (var1 != fcSawDust.shiftedIndex && var1 != fcSoulDust.shiftedIndex ? (var1 == fcOmniSlab.blockID && (var2 & 1) > 0 ? 150 : (var1 == fcMoulding.blockID ? 75 : (var1 == fcCorner.blockID && (var2 & 8) == 0 ? 37 : 0))) : 150)));
    }

    public int dispenseEntity(World var1, ItemStack var2, Random var3, int var4, int var5, int var6, int var7, int var8, double var9, double var11, double var13)
    {
        if (var2.itemID == fcSoulUrn.shiftedIndex)
        {
            FCEntityUrn var21 = new FCEntityUrn(var1, var9, var11, var13, var2.itemID);
            var21.SetUrnHeading((double)var7, 0.10000000149011612D, (double)var8, 1.1F, 6.0F);
            var1.spawnEntityInWorld(var21);
            var1.playAuxSFX(1002, var4, var5, var6, 0);
            return 1;
        }
        else if (var2.itemID == fcBroadheadArrow.shiftedIndex)
        {
            FCEntityBroadheadArrow var22 = new FCEntityBroadheadArrow(var1, var9, var11, var13);
            var22.setArrowHeading((double)var7, 0.10000000149011612D, (double)var8, 1.1F, 6.0F);
            var22.canBePickedUp = 1;
            var1.spawnEntityInWorld(var22);
            var1.playAuxSFX(1002, var4, var5, var6, 0);
            return 1;
        }
        else if (var2.itemID == fcItemRottenArrow.shiftedIndex)
        {
            FCEntityRottenArrow var20 = new FCEntityRottenArrow(var1, var9, var11, var13);
            var20.setArrowHeading((double)var7, 0.10000000149011612D, (double)var8, 1.1F, 6.0F);
            var20.canBePickedUp = 2;
            var1.spawnEntityInWorld(var20);
            var1.playAuxSFX(1002, var4, var5, var6, 0);
            return 1;
        }
        else if (var2.itemID != fcDynamite.shiftedIndex)
        {
            return 0;
        }
        else
        {
            FCEntityDynamite var15 = new FCEntityDynamite(var1, var9, var11, var13, var2.itemID);
            var15.SetDynamiteHeading((double)var7, 0.10000000149011612D, (double)var8, 1.1F, 6.0F);
            var1.spawnEntityInWorld(var15);
            int var16 = MathHelper.floor_double(var9);
            int var17 = MathHelper.floor_double(var11);
            int var18 = MathHelper.floor_double(var13);
            int var19 = var1.getBlockId(var16, var17, var18);

            if (var19 == Block.fire.blockID || var19 == fcStokedFire.blockID)
            {
                var15.m_iFuse = 100;
                var1.playSoundAtEntity(var15, "random.fuse", 1.0F, 1.0F);
            }

            var1.playAuxSFX(1002, var4, var5, var6, 0);
            return 1;
        }
    }

    private void RegisterCustomChannels() {}

    private void InitCreativeTabsServer()
    {
        fcBlockScrewPump.setCreativeTab(CreativeTabs.tabRedstone);
        fcBlockStoneBrickSidingAndCorner.setCreativeTab(CreativeTabs.tabDecorations);
        fcBlockStoneBrickMoulding.setCreativeTab(CreativeTabs.tabDecorations);
        fcBlockDirtSlab.setCreativeTab(CreativeTabs.tabBlock);
        fcSoulforgedSteelBlock.setCreativeTab(CreativeTabs.tabBlock);
        fcLeaves.setCreativeTab(CreativeTabs.tabDecorations);
        fcBloodWood.setCreativeTab(CreativeTabs.tabBlock);
        fcAestheticVegetation.setCreativeTab(CreativeTabs.tabDecorations);
        fcStoneMoulding.setCreativeTab(CreativeTabs.tabDecorations);
        fcAestheticOpaque.setCreativeTab(CreativeTabs.tabDecorations);
        fcAestheticNonOpaque.setCreativeTab(CreativeTabs.tabDecorations);
        fcMiningCharge.setCreativeTab(CreativeTabs.tabRedstone);
        fcBuddyBlock.setCreativeTab(CreativeTabs.tabRedstone);
        fcWoolSlab.setCreativeTab(CreativeTabs.tabBlock);
        fcAnvil.setCreativeTab(CreativeTabs.tabDecorations);
        fcLightBulbOff.setCreativeTab(CreativeTabs.tabRedstone);
        fcBBQ.setCreativeTab(CreativeTabs.tabRedstone);
        fcHopper.setCreativeTab(CreativeTabs.tabRedstone);
        fcSaw.setCreativeTab(CreativeTabs.tabRedstone);
        fcPlatform.setCreativeTab(CreativeTabs.tabTransport);
        fcPulley.setCreativeTab(CreativeTabs.tabRedstone);
        fcMoulding.setCreativeTab(CreativeTabs.tabDecorations);
        fcCorner.setCreativeTab(CreativeTabs.tabDecorations);
        fcBlockDispenser.setCreativeTab(CreativeTabs.tabRedstone);
        fcCauldron.setCreativeTab(CreativeTabs.tabRedstone);
        fcDetectorRailWood.setCreativeTab(CreativeTabs.tabTransport);
        fcDetectorRailObsidian.setCreativeTab(CreativeTabs.tabTransport);
        fcCompanionCube.setCreativeTab(CreativeTabs.tabDecorations);
        fcBlockDetector.setCreativeTab(CreativeTabs.tabRedstone);
        fcLens.setCreativeTab(CreativeTabs.tabRedstone);
        fcHempCrop.setCreativeTab((CreativeTabs)null);
        fcHandCrank.setCreativeTab(CreativeTabs.tabRedstone);
        fcMillStone.setCreativeTab(CreativeTabs.tabRedstone);
        fcAnchor.setCreativeTab(CreativeTabs.tabTransport);
        fcOmniSlab.setCreativeTab(CreativeTabs.tabDecorations);
        fcAxleBlock.setCreativeTab(CreativeTabs.tabRedstone);
        fcGearBox.setCreativeTab(CreativeTabs.tabRedstone);
        fcTurntable.setCreativeTab(CreativeTabs.tabRedstone);
        fcBellows.setCreativeTab(CreativeTabs.tabRedstone);
        fcCrucible.setCreativeTab(CreativeTabs.tabRedstone);
        fcPlanter.setCreativeTab(CreativeTabs.tabDecorations);
        fcVase.setCreativeTab(CreativeTabs.tabDecorations);
        fcBucketCement.setCreativeTab(CreativeTabs.tabMisc);
        fcNethercoal.setCreativeTab(CreativeTabs.tabMaterials);
        fcHemp.setCreativeTab(CreativeTabs.tabMaterials);
        fcGear.setCreativeTab(CreativeTabs.tabMaterials);
        fcFlour.setCreativeTab(CreativeTabs.tabMaterials);
        fcHempFibers.setCreativeTab(CreativeTabs.tabMaterials);
        fcScouredLeather.setCreativeTab(CreativeTabs.tabMaterials);
        fcRopeItem.setCreativeTab(CreativeTabs.tabTransport);
        fcRollersItem.setCreativeTab(CreativeTabs.tabDecorations);
        fcDung.setCreativeTab(CreativeTabs.tabMaterials);
        fcWaterWheelItem.setCreativeTab(CreativeTabs.tabRedstone);
        fcWindMillBladeItem.setCreativeTab(CreativeTabs.tabMaterials);
        fcWindMillItem.setCreativeTab(CreativeTabs.tabRedstone);
        fcHempCloth.setCreativeTab(CreativeTabs.tabMaterials);
        fcGrate.setCreativeTab(CreativeTabs.tabDecorations);
        fcWicker.setCreativeTab(CreativeTabs.tabDecorations);
        fcTannedLeather.setCreativeTab(CreativeTabs.tabMaterials);
        fcStrap.setCreativeTab(CreativeTabs.tabMaterials);
        fcBelt.setCreativeTab(CreativeTabs.tabMaterials);
        fcWoodBlade.setCreativeTab(CreativeTabs.tabMaterials);
        fcGlue.setCreativeTab(CreativeTabs.tabMaterials);
        fcTallow.setCreativeTab(CreativeTabs.tabMaterials);
        fcHaft.setCreativeTab(CreativeTabs.tabMaterials);
        fcSteel.setCreativeTab(CreativeTabs.tabMaterials);
        fcGroundNetherrack.setCreativeTab(CreativeTabs.tabMaterials);
        fcHellfireDust.setCreativeTab(CreativeTabs.tabMaterials);
        fcConcentratedHellfire.setCreativeTab(CreativeTabs.tabMaterials);
        fcArmorPlate.setCreativeTab(CreativeTabs.tabMaterials);
        fcBroadheadArrowhead.setCreativeTab(CreativeTabs.tabMaterials);
        fcBroadheadArrow.setCreativeTab(CreativeTabs.tabCombat);
        fcCoalDust.setCreativeTab(CreativeTabs.tabMaterials);
        fcPadding.setCreativeTab(CreativeTabs.tabMaterials);
        fcFilament.setCreativeTab(CreativeTabs.tabMaterials);
        fcPolishedLapis.setCreativeTab(CreativeTabs.tabMaterials);
        fcUrn.setCreativeTab(CreativeTabs.tabMaterials);
        fcSoulUrn.setCreativeTab(CreativeTabs.tabMaterials);
        fcPotash.setCreativeTab(CreativeTabs.tabMaterials);
        fcSoap.setCreativeTab(CreativeTabs.tabMaterials);
        fcSawDust.setCreativeTab(CreativeTabs.tabMaterials);
        fcDynamite.setCreativeTab(CreativeTabs.tabCombat);
        fcBreedingHarness.setCreativeTab(CreativeTabs.tabMaterials);
        fcSoulDust.setCreativeTab(CreativeTabs.tabMaterials);
        fcNetherSludge.setCreativeTab(CreativeTabs.tabMaterials);
        fcNetherBrick.setCreativeTab(CreativeTabs.tabMaterials);
        fcTuningFork.setCreativeTab(CreativeTabs.tabMisc);
        fcCandleItem.setCreativeTab(CreativeTabs.tabMaterials);
        fcItemBloodMossSpores.setCreativeTab(CreativeTabs.tabMaterials);
        fcItemMould.setCreativeTab(CreativeTabs.tabMaterials);
        fcItemCanvas.setCreativeTab(CreativeTabs.tabDecorations);
        fcRefinedHoe.setCreativeTab(CreativeTabs.tabTools);
        fcItemScrew.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public static void BTWServerOpenWindow(EntityPlayerMP var0, Container var1, int var2, int var3, int var4, int var5)
    {
        try
        {
            Field var6 = EntityPlayerMP.class.getDeclaredFields()[17];
            var6.setAccessible(true);
            int var7 = var6.getInt(var0);
            var7 = var7 % 100 + 1;
            var6.setInt(var0, var7);
            ByteArrayOutputStream var8 = new ByteArrayOutputStream();
            DataOutputStream var9 = new DataOutputStream(var8);
            var9.write(var7);
            var9.writeInt(var2);
            var9.writeInt(var3);
            var9.writeInt(var4);
            var9.writeInt(var5);
            var9.write(var0.dimension);
            var0.playerNetServerHandler.sendPacket(new Packet250CustomPayload("ML|OpenTE", var8.toByteArray()));
            var0.craftingInventory = var1;
            var0.craftingInventory.windowId = var7;
            var0.craftingInventory.onCraftGuiOpened(var0);
        }
        catch (Exception var10)
        {
            var10.printStackTrace();
        }
    }

    public static Object getPrivateValue(Class var0, Object var1, int var2) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field var3 = var0.getDeclaredFields()[var2];
            var3.setAccessible(true);
            return var3.get(var1);
        }
        catch (IllegalAccessException var4)
        {
            return null;
        }
    }

    public static void ServerPlayerConnectionInitialized(NetServerHandler var0, EntityPlayerMP var1)
    {
        ByteArrayOutputStream var2;
        DataOutputStream var3;

        if (!MinecraftServer.getServer().isSinglePlayer())
        {
            var0.sendPacket(new Packet3Chat("\u00a7e" + var1.username + " connected to Better Than Wolves server V" + "4.16"));
            var2 = new ByteArrayOutputStream();
            var3 = new DataOutputStream(var2);

            try
            {
                var3.writeUTF("4.16");
            }
            catch (Exception var9)
            {
                var9.printStackTrace();
            }

            Packet250CustomPayload var4 = new Packet250CustomPayload("FC|VC", var2.toByteArray());
            var0.sendPacket(var4);
        }
        else
        {
            var0.sendPacket(new Packet3Chat("\u00a7f" + "BTW V" + "4.16"));
        }

        var2 = new ByteArrayOutputStream();
        var3 = new DataOutputStream(var2);
        byte var10 = 0;

        if (fcLocalEnableHardcoreBuckets)
        {
            var10 = 1;
        }

        byte var5 = 0;

        if (fcLocalEnableHardcoreBuoy)
        {
            var5 = 1;
        }

        try
        {
            var3.writeByte(var10);
            var3.writeByte(var5);
        }
        catch (Exception var8)
        {
            var8.printStackTrace();
        }

        Packet250CustomPayload var6 = new Packet250CustomPayload("FC|OP", var2.toByteArray());
        var0.sendPacket(var6);

        if (!MinecraftServer.getServer().isSinglePlayer())
        {
            String var7 = "\u00a7f" + "Hardcore Buckets: ";

            if (fcLocalEnableHardcoreBuckets)
            {
                var7 = var7 + "On";
            }
            else
            {
                var7 = var7 + "Off";
            }

            var7 = var7 + " Hardcore Buoy: ";

            if (fcLocalEnableHardcoreBuoy)
            {
                var7 = var7 + "On";
            }
            else
            {
                var7 = var7 + "Off";
            }

            var0.sendPacket(new Packet3Chat(var7));
        }
    }

    private void RegisterCustomContainers() {}
}
