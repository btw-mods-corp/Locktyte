package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockBloodWood extends Block implements FCIBlock, ITextureProvider
{
    private static final int m_iBloodWoodParticleTextureID = 225;
    private static final int m_iBloodWoodTopAndBottomTextureID = 106;
    private static final int m_iBloodWoodSideTextureID = 107;
    private static final float m_fHardness = 2.0F;

    public FCBlockBloodWood(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.blockIndexInTexture = 225;
        this.setHardness(2.0F);
        this.setStepSound(mod_FCBetterThanWolves.fcSoundSquishFootstep);
        this.setBlockName("fcBloodWood");
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        int var3 = var2 & -9;
        return var3 < 2 ? (var1 < 2 ? 106 : 107) : (var3 < 4 ? (var1 != 2 && var1 != 3 ? 107 : 106) : (var1 != 4 && var1 != 5 ? 107 : 106));
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = BlockPistonBase.determineOrientation(var1, var2, var3, var4, (EntityPlayer)var5);
        this.SetFacing(var1, var2, var3, var4, var6);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        var1.playAuxSFX(2225, var2, var3, var4, 0);
        this.NotifySurroundingBloodLeavesOfBlockRemoval(var1, var2, var3, var4);
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomBloodWoodRenderID;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (this.GetCanGrow(var1, var2, var3, var4))
        {
            int var6 = this.GetFacing(var1, var2, var3, var4);

            if (var6 != 0)
            {
                WorldChunkManager var7 = var1.getWorldChunkManager();

                if (var7 != null)
                {
                    BiomeGenBase var8 = var7.getBiomeGenAt(var2, var4);

                    if (var8 instanceof BiomeGenHell)
                    {
                        this.Grow(var1, var2, var3, var4, var5);
                    }
                }
            }

            this.SetCanGrow(var1, var2, var3, var4, false);
        }
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & 7;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -8;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1 & 7;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 &= -8;
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
        var6 = FCUtilsMisc.CycleFacing(var6, var5);
        this.SetFacing(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        return true;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public boolean GetCanGrow(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetCanGrow(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -9;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadata(var2, var3, var4, var6);
    }

    public void Grow(World var1, int var2, int var3, int var4, Random var5)
    {
        if (this.CountBloodWoodNeighboringOnBlockWithSoulSand(var1, var2, var3, var4) < 2)
        {
            int var6 = this.GetFacing(var1, var2, var3, var4);
            int var7;
            int var8;
            FCUtilsBlockPos var9;
            int var13;

            if (var6 == 1)
            {
                var7 = var5.nextInt(100);

                if (var7 < 25)
                {
                    this.AttemptToGrowIntoBlock(var1, var2, var3 + 1, var4, 1);
                }
                else if (var7 < 90)
                {
                    var8 = var5.nextInt(4) + 2;
                    var9 = new FCUtilsBlockPos(var2, var3, var4);
                    var9.AddFacingAsOffset(var8);
                    this.AttemptToGrowIntoBlock(var1, var9.i, var9.j, var9.k, var8);
                    this.AttemptToGrowIntoBlock(var1, var2, var3 + 1, var4, 1);
                }
                else
                {
                    for (var8 = 0; var8 < 2; ++var8)
                    {
                        var13 = var5.nextInt(4) + 2;
                        FCUtilsBlockPos var10 = new FCUtilsBlockPos(var2, var3, var4);
                        var10.AddFacingAsOffset(var13);
                        this.AttemptToGrowIntoBlock(var1, var10.i, var10.j, var10.k, var13);
                    }
                }
            }
            else
            {
                var7 = var5.nextInt(100);

                if (var7 < 40)
                {
                    this.AttemptToGrowIntoBlock(var1, var2, var3 + 1, var4, var6);
                    this.SetFacing(var1, var2, var3, var4, 1);
                }
                else if (var7 < 65)
                {
                    FCUtilsBlockPos var14 = new FCUtilsBlockPos(var2, var3, var4);
                    var14.AddFacingAsOffset(var6);
                    this.AttemptToGrowIntoBlock(var1, var14.i, var14.j, var14.k, var6);
                }
                else
                {
                    int var15;

                    if (var7 < 90)
                    {
                        var8 = var5.nextInt(4) + 2;

                        if (var8 == var6)
                        {
                            var8 = 1;
                        }

                        var9 = new FCUtilsBlockPos(var2, var3, var4);
                        var9.AddFacingAsOffset(var8);
                        var15 = var6;

                        if (var8 >= 2)
                        {
                            var15 = var8;
                        }

                        this.AttemptToGrowIntoBlock(var1, var9.i, var9.j, var9.k, var15);
                        var9 = new FCUtilsBlockPos(var2, var3, var4);
                        var9.AddFacingAsOffset(var6);

                        if (!this.AttemptToGrowIntoBlock(var1, var9.i, var9.j, var9.k, var6) && var8 == 1)
                        {
                            this.SetFacing(var1, var2, var3, var4, 1);
                        }
                    }
                    else
                    {
                        int[] var16 = new int[2];

                        for (var13 = 0; var13 < 2; ++var13)
                        {
                            var16[var13] = 0;
                            var15 = var5.nextInt(4) + 2;

                            if (var15 == var6)
                            {
                                var15 = 1;
                            }

                            FCUtilsBlockPos var11 = new FCUtilsBlockPos(var2, var3, var4);
                            var11.AddFacingAsOffset(var15);
                            int var12 = var6;

                            if (var15 >= 2)
                            {
                                var12 = var15;
                            }

                            if (this.AttemptToGrowIntoBlock(var1, var11.i, var11.j, var11.k, var12))
                            {
                                var16[var13] = var15;
                            }
                        }

                        if (var16[0] == 1 && var16[1] <= 1 || var16[1] == 1 && var16[0] == 0)
                        {
                            this.SetFacing(var1, var2, var3, var4, 1);
                        }
                    }
                }
            }
        }
    }

    public boolean AttemptToGrowIntoBlock(World var1, int var2, int var3, int var4, int var5)
    {
        if ((var1.isAirBlock(var2, var3, var4) || this.IsBloodLeafBlock(var1, var2, var3, var4)) && this.CountBloodWoodNeighboringOnBlockWithSoulSand(var1, var2, var3, var4) < 2)
        {
            var1.setBlockAndMetadataWithNotify(var2, var3, var4, this.blockID, var5 | 8);
            this.GrowLeaves(var1, var2, var3, var4);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void GrowLeaves(World var1, int var2, int var3, int var4)
    {
        for (int var5 = var2 - 1; var5 <= var2 + 1; ++var5)
        {
            for (int var6 = var3 - 1; var6 <= var3 + 1; ++var6)
            {
                for (int var7 = var4 - 1; var7 <= var4 + 1; ++var7)
                {
                    if (var1.isAirBlock(var5, var6, var7))
                    {
                        var1.setBlockAndMetadataWithNotify(var5, var6, var7, mod_FCBetterThanWolves.fcLeaves.blockID, 0);
                    }
                }
            }
        }
    }

    public boolean IsBloodLeafBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);

        if (var5 == mod_FCBetterThanWolves.fcLeaves.blockID)
        {
            return true;
        }
        else
        {
            if (var5 == mod_FCBetterThanWolves.fcAestheticVegetation.blockID)
            {
                int var6 = var1.getBlockMetadata(var2, var3, var4);

                if (var6 == 3)
                {
                    return true;
                }
            }

            return false;
        }
    }

    public int CountBloodWoodNeighboringOnBlockWithSoulSand(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        for (int var6 = 0; var6 < 6; ++var6)
        {
            FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);
            var7.AddFacingAsOffset(var6);

            if (var1.getBlockId(var7.i, var7.j, var7.k) == this.blockID)
            {
                ++var5;
            }
        }

        if (var1.getBlockId(var2, var3 - 1, var4) == Block.slowSand.blockID)
        {
            ++var5;
        }

        return var5;
    }

    public int CountBloodWoodNeighboringOnBlockIncludingDiagnals(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        for (int var6 = var2 - 1; var6 <= var2 + 1; ++var6)
        {
            for (int var7 = var3 - 1; var7 <= var3 + 1; ++var7)
            {
                for (int var8 = var4 - 1; var8 <= var4 + 1; ++var8)
                {
                    if (var1.getBlockId(var6, var7, var8) == this.blockID && (var6 != var2 || var7 != var3 || var8 != var4))
                    {
                        ++var5;
                    }
                }
            }
        }

        return var5;
    }

    public void NotifySurroundingBloodLeavesOfBlockRemoval(World var1, int var2, int var3, int var4)
    {
        byte var5 = 4;
        int var6 = var5 + 1;

        if (var1.checkChunksExist(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6))
        {
            for (int var7 = -var5; var7 <= var5; ++var7)
            {
                for (int var8 = -var5; var8 <= var5; ++var8)
                {
                    for (int var9 = -var5; var9 <= var5; ++var9)
                    {
                        int var10 = var1.getBlockId(var2 + var7, var3 + var8, var4 + var9);

                        if (var10 == mod_FCBetterThanWolves.fcLeaves.blockID)
                        {
                            int var11 = var1.getBlockMetadata(var2 + var7, var3 + var8, var4 + var9);

                            if ((var11 & 8) == 0)
                            {
                                var1.setBlockMetadata(var2 + var7, var3 + var8, var4 + var9, var11 | 8);
                            }
                        }
                    }
                }
            }
        }
    }
}
