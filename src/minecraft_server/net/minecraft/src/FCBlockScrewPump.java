package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockScrewPump extends Block implements FCIBlockSeperateRandomUpdates, FCIBlock, FCIMechanicalDevice, FCIBlockFluidSource, ITextureProvider
{
    public static final int m_iTickRate = 20;
    private final int m_iTopTextureIndex = 138;
    private final int m_iFrontTextureIndex = 139;
    private final int m_iSideTextureIndex = 140;
    private final int m_iBottomTextureIndex = 141;

    public FCBlockScrewPump(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcScrewPump");
        this.setTickRandomly(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 20;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        if (var5 < 2)
        {
            var5 = 2;
        }

        this.SetFacing(var1, var2, var3, var4, var5);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = FCUtilsMisc.ConvertPlacingEntityOrientationToFlatBlockFacing(var5);
        this.SetFacing(var1, var2, var3, var4, var6);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsJammed(var1, var2, var3, var4);

        if (var6)
        {
            FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);
            var7.AddFacingAsOffset(this.GetFacing(var1, var2, var3, var4));
            int var8 = var1.getBlockId(var7.i, var7.j, var7.k);

            if (var8 != Block.waterMoving.blockID && var8 != Block.waterStill.blockID)
            {
                this.SetIsJammed(var1, var2, var3, var4, false);
            }
        }

        boolean var11 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var12 = this.IsMechanicalOn(var1, var2, var3, var4);

        if (var11 != var12)
        {
            this.SetMechanicalOn(var1, var2, var3, var4, var11);
            var1.markBlockNeedsUpdate(var2, var3, var4);

            if (this.IsPumpingWater(var1, var2, var3, var4))
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            }

            if (!var11 && this.IsJammed(var1, var2, var3, var4))
            {
                this.SetIsJammed(var1, var2, var3, var4, false);
            }
        }
        else if (var12)
        {
            int var9;

            if (this.IsPumpingWater(var1, var2, var3, var4))
            {
                var9 = var1.getBlockId(var2, var3 + 1, var4);

                if (var9 != Block.waterMoving.blockID && var9 != Block.waterStill.blockID)
                {
                    if (var1.isAirBlock(var2, var3 + 1, var4))
                    {
                        var1.setBlockAndMetadataWithNotify(var2, var3 + 1, var4, Block.waterMoving.blockID, 7);
                        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                    }
                }
                else
                {
                    int var10 = var1.getBlockMetadata(var2, var3 + 1, var4);

                    if (var10 > 1 && var10 < 8)
                    {
                        var1.setBlockAndMetadataWithNotify(var2, var3 + 1, var4, Block.waterMoving.blockID, var10 - 1);
                        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                    }
                }
            }
            else
            {
                var9 = var1.getBlockId(var2, var3 + 1, var4);

                if (var9 == Block.waterMoving.blockID || var9 == Block.waterStill.blockID)
                {
                    Block.blocksList[var9].onNeighborBlockChange(var1, var2, var3 + 1, var4, this.blockID);
                }
            }
        }
    }

    public void RandomUpdateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsJammed(var1, var2, var3, var4);
        boolean var8 = this.IsMechanicalOn(var1, var2, var3, var4);
        boolean var7;

        if (var8)
        {
            FCUtilsBlockPos var9 = new FCUtilsBlockPos(var2, var3, var4);
            var9.AddFacingAsOffset(this.GetFacing(var1, var2, var3, var4));
            int var10 = var1.getBlockId(var9.i, var9.j, var9.k);

            if (var10 != Block.waterMoving.blockID && var10 != Block.waterStill.blockID)
            {
                var7 = false;
            }
            else
            {
                int var11 = this.GetRandomDistanceForSourceCheck(var5);
                var7 = !this.DoesWaterHaveValidSource(var1, var9.i, var9.j, var9.k, var9.i, var9.j, var9.k, var11);

                if (!var7 && var6)
                {
                    var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                }
            }
        }
        else
        {
            var7 = false;
        }

        if (var6 != var7)
        {
            this.SetIsJammed(var1, var2, var3, var4, var7);
        }
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 3) + 2;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -4;

        if (var5 >= 2)
        {
            var5 -= 2;
        }
        else
        {
            var5 = 0;
        }

        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return (var1 & 3) + 2;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 &= -4;

        if (var2 >= 2)
        {
            var2 -= 2;
        }
        else
        {
            var2 = 0;
        }

        var1 |= var2;
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5);
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        int var7 = FCUtilsMisc.RotateFacingAroundJ(var6, var5);

        if (var7 != var6)
        {
            this.SetFacing(var1, var2, var3, var4, var7);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean CanOutputMechanicalPower()
    {
        return false;
    }

    public boolean CanInputMechanicalPower()
    {
        return true;
    }

    public boolean IsInputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, 0);
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void Overpower(World var1, int var2, int var3, int var4) {}

    public int IsSourceToFluidBlockAtFacing(World var1, int var2, int var3, int var4, int var5)
    {
        if (var5 == 1 && this.IsPumpingWater(var1, var2, var3, var4))
        {
            int var6 = var1.getBlockId(var2, var3 + 1, var4);

            if (var6 == Block.waterMoving.blockID || var6 == Block.waterStill.blockID)
            {
                int var7 = 0;
                int var8 = var1.getBlockMetadata(var2, var3 + 1, var4);

                if (var8 > 0 && var8 < 8)
                {
                    var7 = var8 - 1;
                }

                return var7;
            }
        }

        return -1;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public boolean IsMechanicalOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 4) > 0;
    }

    public void SetMechanicalOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -5;

        if (var5)
        {
            var6 |= 4;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean IsJammed(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetIsJammed(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -9;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean IsPumpingWater(World var1, int var2, int var3, int var4)
    {
        if (this.IsMechanicalOn(var1, var2, var3, var4) && !this.IsJammed(var1, var2, var3, var4))
        {
            FCUtilsBlockPos var5 = new FCUtilsBlockPos(var2, var3, var4);
            var5.AddFacingAsOffset(this.GetFacing(var1, var2, var3, var4));
            int var6 = var1.getBlockId(var5.i, var5.j, var5.k);

            if (var6 == Block.waterMoving.blockID || var6 == Block.waterStill.blockID)
            {
                return true;
            }
        }

        return false;
    }

    private boolean DoesWaterHaveValidSource(World var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        if (var8 <= 0)
        {
            return true;
        }
        else if (!var1.checkChunksExist(var2 - 1, 64, var4 - 1, var2 + 1, 64, var4 + 1))
        {
            return true;
        }
        else
        {
            int var9 = var1.getBlockMetadata(var2, var3, var4);

            if (var9 == 0)
            {
                return true;
            }
            else
            {
                int var13;
                int var16;

                if (var9 >= 8)
                {
                    FCUtilsBlockPos var17 = new FCUtilsBlockPos(var2, var3, var4);
                    var17.AddFacingAsOffset(1);
                    var16 = var1.getBlockId(var17.i, var17.j, var17.k);

                    if (var16 == Block.waterMoving.blockID || var16 == Block.waterStill.blockID)
                    {
                        if (this.DoesWaterHaveValidSourceWithSourceCheck(var1, var17.i, var17.j, var17.k, var5, var6, var7, var8 - 1))
                        {
                            return true;
                        }

                        for (int var18 = 2; var18 < 6; ++var18)
                        {
                            var17 = new FCUtilsBlockPos(var2, var3, var4);
                            var17.AddFacingAsOffset(var18);
                            var16 = var1.getBlockId(var17.i, var17.j, var17.k);

                            if (var16 == Block.waterMoving.blockID || var16 == Block.waterStill.blockID)
                            {
                                var13 = var1.getBlockMetadata(var17.i, var17.j, var17.k);
                                boolean var20 = false;

                                if (var13 == 0)
                                {
                                    return true;
                                }
                            }
                        }
                    }

                    return false;
                }
                else
                {
                    int var10 = var1.getBlockId(var2, var3 - 1, var4);
                    FCUtilsBlockPos var12;

                    if (mod_FCBetterThanWolves.m_bBlocksPotentialFluidSources[var10])
                    {
                        FCIBlockFluidSource var11 = (FCIBlockFluidSource)((FCIBlockFluidSource)Block.blocksList[var10]);

                        if (var11.IsSourceToFluidBlockAtFacing(var1, var2, var3 - 1, var4, 1) >= 0)
                        {
                            if (var10 != this.blockID)
                            {
                                return true;
                            }

                            if (var3 - 1 < var6)
                            {
                                return true;
                            }

                            var12 = new FCUtilsBlockPos(var2, var3 - 1, var4);
                            var13 = this.GetFacing(var1, var12.i, var12.j, var12.k);
                            var12.AddFacingAsOffset(var13);

                            if (this.DoesWaterHaveValidSourceWithSourceCheck(var1, var12.i, var12.j, var12.k, var5, var6, var7, var8 - 1))
                            {
                                return true;
                            }
                        }
                    }

                    for (var16 = 2; var16 < 6; ++var16)
                    {
                        var12 = new FCUtilsBlockPos(var2, var3, var4);
                        var12.AddFacingAsOffset(var16);
                        var13 = var1.getBlockId(var12.i, var12.j, var12.k);

                        if (var13 != Block.waterMoving.blockID && var13 != Block.waterStill.blockID)
                        {
                            if (mod_FCBetterThanWolves.m_bBlocksPotentialFluidSources[var13])
                            {
                                FCIBlockFluidSource var19 = (FCIBlockFluidSource)((FCIBlockFluidSource)Block.blocksList[var13]);

                                if (var19.IsSourceToFluidBlockAtFacing(var1, var12.i, var12.j, var12.k, FCUtilsMisc.GetOppositeFacing(var16)) >= 0)
                                {
                                    return true;
                                }
                            }
                        }
                        else
                        {
                            int var14 = var1.getBlockMetadata(var12.i, var12.j, var12.k);
                            boolean var15 = false;

                            if (var14 >= 8)
                            {
                                var15 = true;
                            }
                            else if (var14 < var9)
                            {
                                var15 = true;
                            }

                            if (var15 && this.DoesWaterHaveValidSourceWithSourceCheck(var1, var12.i, var12.j, var12.k, var5, var6, var7, var8 - 1))
                            {
                                return true;
                            }
                        }
                    }

                    return false;
                }
            }
        }
    }

    private boolean DoesWaterHaveValidSourceWithSourceCheck(World var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        return var2 == var5 && var3 == var6 && var4 == var7 ? false : this.DoesWaterHaveValidSource(var1, var2, var3, var4, var5, var6, var7, var8);
    }

    private int GetRandomDistanceForSourceCheck(Random var1)
    {
        short var2 = 32;
        int var3 = var1.nextInt(32);

        if (var3 == 0)
        {
            var2 = 512;
        }
        else if (var3 <= 2)
        {
            var2 = 256;
        }
        else if (var3 <= 6)
        {
            var2 = 128;
        }
        else if (var3 <= 14)
        {
            var2 = 64;
        }

        return var2;
    }
}
