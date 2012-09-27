package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockBBQ extends Block implements ITextureProvider, FCIBlock
{
    private final int bbqTextureIDTop = 59;
    private final int bbqTextureIDSide = 60;
    private final int bbqTextureIDBottom = 61;
    private static final int iBBQTickRate = 4;

    public FCBlockBBQ(int var1)
    {
        super(var1, Material.rock);
        this.setHardness(3.5F);
        this.setStepSound(Block.soundStoneFootstep);
        this.setBlockName("fcbbq");
        this.blockIndexInTexture = 0;
        this.setTickRandomly(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 4;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 1 ? 59 : (var1 == 0 ? 61 : 60);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsBBQGettingPowered(var1, var2, var3, var4);
        int var7;

        if (var6)
        {
            if (!this.IsBBQLit(var1, var2, var3, var4))
            {
                this.BBQIgnite(var1, var2, var3, var4);
            }
            else
            {
                var7 = var1.getBlockId(var2, var3 + 1, var4);

                if (var7 != Block.fire.blockID && var7 != mod_FCBetterThanWolves.fcStokedFire.blockID && this.BBQShouldIgniteAbove(var1, var2, var3, var4))
                {
                    var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "fire.ignite", 1.0F, var1.rand.nextFloat() * 0.4F + 0.8F);
                    var1.setBlockWithNotify(var2, var3 + 1, var4, fire.blockID);
                }
            }
        }
        else if (this.IsBBQLit(var1, var2, var3, var4))
        {
            this.BBQExtinguish(var1, var2, var3, var4);
        }
        else
        {
            var7 = var1.getBlockId(var2, var3 + 1, var4);

            if (var7 == Block.fire.blockID || var7 == mod_FCBetterThanWolves.fcStokedFire.blockID)
            {
                var1.setBlockWithNotify(var2, var3 + 1, var4, 0);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        boolean var6 = false;

        if (var5 != fire.blockID)
        {
            boolean var7 = this.IsBBQGettingPowered(var1, var2, var3, var4);

            if (this.IsBBQLit(var1, var2, var3, var4) != var7)
            {
                var6 = true;
            }
            else if (this.IsBBQLit(var1, var2, var3, var4))
            {
                int var8 = var1.getBlockId(var2, var3 + 1, var4);

                if (var8 != Block.fire.blockID && var8 != mod_FCBetterThanWolves.fcStokedFire.blockID && this.BBQShouldIgniteAbove(var1, var2, var3, var4))
                {
                    var6 = true;
                }
            }
        }

        if (var6)
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
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

    public boolean IsBBQLit(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return (var5 & 4) > 0;
    }

    private void SetBBQLitFlag(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5 | 4);
    }

    private void ClearBBQLitFlag(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5 & -5);
    }

    private boolean IsBBQGettingPowered(World var1, int var2, int var3, int var4)
    {
        boolean var5 = var1.isBlockGettingPowered(var2, var3, var4) || var1.isBlockGettingPowered(var2, var3 + 1, var4);
        return var5;
    }

    private boolean BBQShouldIgniteAbove(World var1, int var2, int var3, int var4)
    {
        boolean var5 = false;
        int var6 = var1.getBlockId(var2, var3 + 1, var4);
        Block var7 = Block.blocksList[var6];

        if (!var1.isBlockOpaqueCube(var2, var3 + 1, var4))
        {
            if (var7 != null)
            {
                if (!(var7 instanceof BlockFluid) && var7 != mod_FCBetterThanWolves.fcCement && var7 != Block.pistonExtension && var7 != Block.pistonMoving && var7 != Block.pistonBase && var7 != Block.pistonStickyBase)
                {
                    var5 = true;
                }
            }
            else
            {
                var5 = true;
            }
        }
        else if (fire.canBlockCatchFire(var1, var2, var3 + 1, var4))
        {
            var5 = true;
        }

        return var5;
    }

    private void BBQIgnite(World var1, int var2, int var3, int var4)
    {
        this.SetBBQLitFlag(var1, var2, var3, var4);
        var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "fire.ignite", 1.0F, var1.rand.nextFloat() * 0.4F + 0.8F);

        if (this.BBQShouldIgniteAbove(var1, var2, var3, var4))
        {
            var1.setBlockWithNotify(var2, var3 + 1, var4, fire.blockID);
        }
    }

    private void BBQExtinguish(World var1, int var2, int var3, int var4)
    {
        this.ClearBBQLitFlag(var1, var2, var3, var4);
        var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);
        boolean var5 = var1.getBlockId(var2, var3 + 1, var4) == fire.blockID || var1.getBlockId(var2, var3 + 1, var4) == mod_FCBetterThanWolves.fcStokedFire.blockID;

        if (var5)
        {
            var1.setBlockWithNotify(var2, var3 + 1, var4, 0);
        }
    }
}
