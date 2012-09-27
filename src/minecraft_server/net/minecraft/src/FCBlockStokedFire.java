package net.minecraft.src;

import java.util.Random;

public class FCBlockStokedFire extends BlockFire implements FCIBlock
{
    private final int m_iTickRate = 42;

    protected FCBlockStokedFire(int var1)
    {
        super(var1, 31);
        this.setHardness(0.0F);
        this.setLightValue(1.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcStokedFire");
        this.disableStats();
        this.setTickRandomly(false);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 42;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!this.canPlaceBlockAt(var1, var2, var3, var4))
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }

        if (var1.getBlockId(var2, var3 - 1, var4) != mod_FCBetterThanWolves.fcBBQ.blockID)
        {
            var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.fire.blockID, 15);
        }
        else if (!((FCBlockBBQ)mod_FCBetterThanWolves.fcBBQ).IsBBQLit(var1, var2, var3 - 1, var4))
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
        else
        {
            if (var1.getBlockId(var2, var3 + 1, var4) == Block.brick.blockID)
            {
                var1.setBlockWithNotify(var2, var3 + 1, var4, mod_FCBetterThanWolves.fcKiln.blockID);
            }

            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var6 < 15)
            {
                ++var6;
                var1.setBlockMetadata(var2, var3, var4, var6);
            }

            this.tryToCatchBlockOnFire(var1, var2 + 1, var3, var4, 300, var5, 15);
            this.tryToCatchBlockOnFire(var1, var2 - 1, var3, var4, 300, var5, 15);
            this.tryToCatchBlockOnFire(var1, var2, var3 - 1, var4, 250, var5, 15);
            this.tryToCatchBlockOnFire(var1, var2, var3 + 1, var4, 250, var5, 15);
            this.tryToCatchBlockOnFire(var1, var2, var3, var4 - 1, 300, var5, 15);
            this.tryToCatchBlockOnFire(var1, var2, var3, var4 + 1, 300, var5, 15);

            for (int var7 = var2 - 1; var7 <= var2 + 1; ++var7)
            {
                for (int var8 = var4 - 1; var8 <= var4 + 1; ++var8)
                {
                    for (int var9 = var3 - 1; var9 <= var3 + 4; ++var9)
                    {
                        if (var7 != var2 || var9 != var3 || var8 != var4)
                        {
                            int var10 = 100;

                            if (var9 > var3 + 1)
                            {
                                var10 += (var9 - (var3 + 1)) * 100;
                            }

                            int var11 = this.getChanceOfNeighborsEncouragingFire(var1, var7, var9, var8);

                            if (var11 > 0)
                            {
                                int var12 = (var11 + 40) / 45;

                                if (var12 > 0 && var5.nextInt(var10) <= var12 && (!var1.isRaining() || !var1.canLightningStrikeAt(var7, var9, var8)) && !var1.canLightningStrikeAt(var7 - 1, var9, var4) && !var1.canLightningStrikeAt(var7 + 1, var9, var8) && !var1.canLightningStrikeAt(var7, var9, var8 - 1) && !var1.canLightningStrikeAt(var7, var9, var8 + 1))
                                {
                                    int var13 = var6 + var5.nextInt(5) / 4;

                                    if (var13 > 15)
                                    {
                                        var13 = 15;
                                    }

                                    var1.setBlockAndMetadataWithNotify(var7, var9, var8, Block.fire.blockID, var13);
                                }
                            }
                        }
                    }
                }
            }

            if (var6 >= 3)
            {
                var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.fire.blockID, 15);
            }
            else
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate() + var1.rand.nextInt(10));
            }
        }
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
}
