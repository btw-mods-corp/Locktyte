package net.minecraft.src;

import java.util.Random;

public class FCBlockKiln extends Block implements FCIBlock
{
    private static int m_iMaxTickRate = 50;
    private static int m_iMinTickRate = 20;

    public FCBlockKiln(int var1)
    {
        super(var1, Material.rock);
        this.blockIndexInTexture = 7;
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundStoneFootstep);
        this.setTickRandomly(true);
        this.setBlockName("fcKiln");
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return m_iMaxTickRate;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, m_iMaxTickRate);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (var1.getBlockId(var2, var3 - 1, var4) != mod_FCBetterThanWolves.fcStokedFire.blockID)
        {
            var1.setBlockWithNotify(var2, var3, var4, Block.brick.blockID);
        }
        else
        {
            int var6 = m_iMaxTickRate;
            boolean var7 = false;
            int var8 = this.GetCookCounter(var1, var2, var3, var4);

            if (this.CanBlockBeCooked(var1, var2, var3 + 1, var4) && this.CheckKilnIntegrity(var1, var2, var3, var4))
            {
                var7 = true;
            }

            if (var7)
            {
                int var9 = var8 + 1;

                if (var9 > 7)
                {
                    var9 = 0;
                    this.CookBlock(var1, var2, var3 + 1, var4);
                }
                else
                {
                    if (var9 == 1)
                    {
                        if (var1.getBlockId(var2, var3 + 1, var4) == mod_FCBetterThanWolves.fcUnfiredPottery.blockID)
                        {
                            var1.markBlocksDirty(var2, var3 + 1, var4, var2, var3 + 1, var4);
                        }

                        var1.markBlockNeedsUpdate(var2, var3, var4);
                    }

                    var6 = this.ComputeTickRateBasedOnFireFactor(var1, var2, var3, var4);
                }

                this.SetCookCounter(var1, var2, var3, var4, var9);

                if (var9 == 0)
                {
                    var1.markBlockNeedsUpdate(var2, var3, var4);
                }
            }
            else if (var8 != 0)
            {
                if (var1.getBlockId(var2, var3 + 1, var4) == mod_FCBetterThanWolves.fcUnfiredPottery.blockID)
                {
                    var1.markBlocksDirty(var2, var3 + 1, var4, var2, var3 + 1, var4);
                }

                this.SetCookCounter(var1, var2, var3, var4, 0);
                var1.markBlockNeedsUpdate(var2, var3, var4);
            }

            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, var6);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return Block.brick.blockID;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetCookCounter(var1, var2, var3, var4);

        if (var6 > 0 && !this.CanBlockBeCooked(var1, var2, var3 + 1, var4))
        {
            this.SetCookCounter(var1, var2, var3, var4, 0);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, m_iMaxTickRate);
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

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5) {}

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return var1;
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        return false;
    }

    public int GetCookCounter(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & 7;
    }

    public void SetCookCounter(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -8;
        var6 |= var5 & 7;
        var1.setBlockMetadata(var2, var3, var4, var6);
    }

    private boolean CanBlockBeCooked(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);
        return var5 == mod_FCBetterThanWolves.fcUnfiredPottery.blockID || var5 == Block.oreIron.blockID || var5 == Block.oreGold.blockID || var5 == Block.wood.blockID || var5 == mod_FCBetterThanWolves.fcBloodWood.blockID;
    }

    private void CookBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);
        int var6 = -1;
        byte var7 = 0;

        if (var5 == mod_FCBetterThanWolves.fcUnfiredPottery.blockID)
        {
            int var8 = var1.getBlockMetadata(var2, var3, var4);

            switch (var8)
            {
                case 0:
                    var6 = mod_FCBetterThanWolves.fcCrucible.blockID;
                    break;

                case 1:
                    var6 = mod_FCBetterThanWolves.fcPlanter.blockID;
                    break;

                case 2:
                    var6 = mod_FCBetterThanWolves.fcVase.blockID;
                    break;

                case 3:
                    var6 = mod_FCBetterThanWolves.fcUrn.shiftedIndex;
                    break;

                case 4:
                    var6 = mod_FCBetterThanWolves.fcItemMould.shiftedIndex;
            }
        }
        else if (var5 == Block.oreIron.blockID)
        {
            var6 = Item.ingotIron.shiftedIndex;
        }
        else if (var5 == Block.oreGold.blockID)
        {
            var6 = Item.ingotGold.shiftedIndex;
        }
        else if (var5 == Block.wood.blockID || var5 == mod_FCBetterThanWolves.fcBloodWood.blockID)
        {
            var6 = Item.coal.shiftedIndex;
            var7 = 1;
        }

        if (var6 >= 0)
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, var6, var7);
        }
    }

    private boolean CheckKilnIntegrity(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        for (int var6 = 1; var6 <= 5; ++var6)
        {
            FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3 + 1, var4);
            var7.AddFacingAsOffset(var6);
            int var8 = var1.getBlockId(var7.i, var7.j, var7.k);

            if (var8 == Block.brick.blockID || var8 == mod_FCBetterThanWolves.fcKiln.blockID)
            {
                ++var5;

                if (var5 >= 3)
                {
                    return true;
                }
            }
        }

        return false;
    }

    private int ComputeTickRateBasedOnFireFactor(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = 0;
        int var6;

        for (var6 = -1; var6 <= 1; ++var6)
        {
            for (int var7 = -1; var7 <= 1; ++var7)
            {
                if ((var6 != 0 || var7 != 0) && var1.getBlockId(var2 + var6, var3 - 1, var4 + var7) == mod_FCBetterThanWolves.fcStokedFire.blockID)
                {
                    ++var5;
                }
            }
        }

        var6 = (m_iMaxTickRate - m_iMinTickRate) * (8 - var5) / 8 + m_iMinTickRate;
        return var6;
    }
}
