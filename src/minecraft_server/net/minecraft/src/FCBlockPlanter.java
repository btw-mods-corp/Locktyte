package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockPlanter extends Block implements FCIBlock, FCISoil, ITextureProvider, FCIBlockSolidTop
{
    public static final float m_fPlanterWidth = 0.75F;
    public static final float m_fPlanterHalfWidth = 0.375F;
    public static final float m_fPlanterBandHeight = 0.3125F;
    public static final float m_fPlanterBandHalfHeight = 0.15625F;
    private static final int m_iPlanterDirtTextureIndex = 78;
    private static final int m_iPlanterSoulSandTextureIndex = 111;
    private static final int m_iPlanterGrassTextureIndex = 117;
    private static final int m_iPlanterFertilizedTextureIndex = 136;
    public static final int m_iTypeEmpty = 0;
    public static final int m_iTypeSoil = 1;
    public static final int m_iTypeSoilFertilized = 2;
    public static final int m_iTypeSoulSand = 8;
    public static final int m_iTypeGrass0 = 9;
    public static final int m_iTypeGrass1 = 11;
    public static final int m_iTypeGrass2 = 13;
    public static final int m_iTypeGrass3 = 15;

    public FCBlockPlanter(int var1)
    {
        super(var1, Material.glass);
        this.blockIndexInTexture = 77;
        this.setHardness(0.6F);
        this.setStepSound(soundGlassFootstep);
        this.setBlockName("fcPlanter");
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomPlanterRenderID;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        if (var1 == 11 || var1 == 13 || var1 == 15)
        {
            var1 = 9;
        }

        return var1;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetPlanterType(var1, var2, var3, var4);

        if (var6 == 9 || var6 == 11 || var6 == 13 || var6 == 15)
        {
            int var7 = this.GetGrassGrowthState(var1, var2, var3, var4);
            int var8 = 0;
            int var9;

            if (var1.isAirBlock(var2, var3 + 1, var4) && var1.getBlockLightValue(var2, var3 + 1, var4) >= 8)
            {
                var8 = var7 + 1;

                if (var8 > 3)
                {
                    var8 = 0;
                    var9 = var5.nextInt(4);

                    if (var9 == 0)
                    {
                        var1.setBlockWithNotify(var2, var3 + 1, var4, Block.plantRed.blockID);
                    }
                    else if (var9 == 1)
                    {
                        var1.setBlockWithNotify(var2, var3 + 1, var4, Block.plantYellow.blockID);
                    }
                    else
                    {
                        var1.setBlockAndMetadataWithNotify(var2, var3 + 1, var4, Block.tallGrass.blockID, 1);
                    }
                }
            }

            if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9)
            {
                for (var9 = 0; var9 < 4; ++var9)
                {
                    int var10 = var2 + var5.nextInt(3) - 1;
                    int var11 = var3 + var5.nextInt(5) - 3;
                    int var12 = var4 + var5.nextInt(3) - 1;
                    int var13 = var1.getBlockId(var10, var11 + 1, var12);

                    if (var1.getBlockId(var10, var11, var12) == Block.dirt.blockID && var1.getBlockLightValue(var10, var11 + 1, var12) >= 4 && Block.lightOpacity[var13] <= 2)
                    {
                        var1.setBlockWithNotify(var10, var11, var12, Block.grass.blockID);
                    }
                }
            }

            if (var8 != var7)
            {
                this.SetGrassGrowthState(var1, var2, var3, var4, var8);
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote && !var5.isDead)
        {
            int var6 = this.GetPlanterType(var1, var2, var3, var4);

            if (var6 == 1 && var5 instanceof EntityItem)
            {
                EntityItem var7 = (EntityItem)var5;
                ItemStack var8 = var7.item;

                if (var8.getItem().shiftedIndex == Item.dyePowder.shiftedIndex && var8.getItemDamage() == 15)
                {
                    --var8.stackSize;

                    if (var8.stackSize <= 0)
                    {
                        var7.setDead();
                    }

                    this.SetPlanterType(var1, var2, var3, var4, 2);
                    var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.pop", 0.25F, ((var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
            }
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 0;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5) {}

    public int GetFacingFromMetadata(int var1)
    {
        return 0;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5) {}

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return var1;
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        return false;
    }

    public boolean CanPlantGrowOnBlock(World var1, int var2, int var3, int var4, Block var5)
    {
        int var6 = this.GetPlanterType(var1, var2, var3, var4);
        return var5.blockID == Block.netherStalk.blockID ? var6 == 8 : (var5.blockID == Block.waterlily.blockID ? false : ((var6 == 9 || var6 == 11 || var6 == 13 || var6 == 15) && (var5.blockID == Block.plantRed.blockID || var5.blockID == Block.plantYellow.blockID || var5.blockID == Block.tallGrass.blockID || var5.blockID == Block.sapling.blockID) ? true : var6 == 1 || var6 == 2));
    }

    public boolean IsPlantGrowthMaximizedOnBlock(World var1, int var2, int var3, int var4, Block var5)
    {
        int var6 = this.GetPlanterType(var1, var2, var3, var4);
        return (var6 == 1 || var6 == 2) && var5.blockID != Block.netherStalk.blockID;
    }

    public boolean IsBlockHydrated(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetPlanterType(var1, var2, var3, var4);
        return var5 == 1 || var5 == 2;
    }

    public boolean IsBlockConsideredNeighbouringWater(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetPlanterType(var1, var2, var3, var4);
        return var5 == 1 || var5 == 2;
    }

    public float GetGrowthMultiplier(World var1, int var2, int var3, int var4, Block var5)
    {
        int var6 = this.GetPlanterType(var1, var2, var3, var4);
        return var6 == 2 ? 2.0F : 1.0F;
    }

    public void NotifyOfPlantGrowth(World var1, int var2, int var3, int var4, Block var5)
    {
        int var6 = this.GetPlanterType(var1, var2, var3, var4);

        if (var6 == 2)
        {
            this.SetPlanterType(var1, var2, var3, var4, 1);
        }
    }

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetPlanterType(var1, var2, var3, var4);
        return var5 != 0;
    }

    public boolean DoesBlockNeighbourOnStem(IBlockAccess var1, int var2, int var3, int var4)
    {
        return Block.blocksList[var1.getBlockId(var2 + 1, var3, var4)] instanceof BlockStem ? true : (Block.blocksList[var1.getBlockId(var2 - 1, var3, var4)] instanceof BlockStem ? true : (Block.blocksList[var1.getBlockId(var2, var3, var4 + 1)] instanceof BlockStem ? true : Block.blocksList[var1.getBlockId(var2, var3, var4 - 1)] instanceof BlockStem));
    }

    public int GetPlanterType(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4);
    }

    public void SetPlanterType(World var1, int var2, int var3, int var4, int var5)
    {
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
    }

    public int GetGrassGrowthState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetPlanterType(var1, var2, var3, var4);

        switch (var5)
        {
            case 9:
                return 0;

            case 10:
            case 12:
            case 14:
            default:
                return 0;

            case 11:
                return 1;

            case 13:
                return 2;

            case 15:
                return 3;
        }
    }

    public void SetGrassGrowthState(World var1, int var2, int var3, int var4, int var5)
    {
        byte var6 = 9;

        if (var5 == 1)
        {
            var6 = 11;
        }
        else if (var5 == 2)
        {
            var6 = 13;
        }
        else if (var5 == 3)
        {
            var6 = 15;
        }

        this.SetPlanterType(var1, var2, var3, var4, var6);
    }
}
