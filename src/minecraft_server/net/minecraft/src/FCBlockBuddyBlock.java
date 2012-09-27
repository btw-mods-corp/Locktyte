package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockBuddyBlock extends Block implements FCIBlock, ITextureProvider
{
    private static final int m_iTickRate = 5;
    private static final int m_iTextureIndexOff = 80;
    private static final int m_iTextureIndexOn = 81;
    private static final int m_iTextureIndexOutputOff = 82;
    private static final int m_iTextureIndexOutputOn = 83;

    public FCBlockBuddyBlock(int var1)
    {
        super(var1, Material.rock);
        this.blockIndexInTexture = 80;
        this.setHardness(3.5F);
        this.setStepSound(soundStoneFootstep);
        this.setBlockName("fcBuddyBlock");
        this.setRequiresSelfNotify();
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 5;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        this.SetFacing(var1, var2, var3, var4, FCUtilsMisc.GetOppositeFacing(var5));
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacing(var5);
        this.SetFacing(var1, var2, var3, var4, var6);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (!this.IsRedstoneOn(var1, var2, var3, var4) && var5 != Block.redstoneWire.blockID && var5 != Block.redstoneRepeaterActive.blockID && var5 != Block.redstoneRepeaterIdle.blockID && var5 != Block.torchRedstoneActive.blockID && var5 != Block.torchRedstoneIdle.blockID && var5 != mod_FCBetterThanWolves.fcBlockDetectorLogic.blockID && var5 != this.blockID)
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (this.IsRedstoneOn(var1, var2, var3, var4))
        {
            this.SetBlockRedstoneOn(var1, var2, var3, var4, false);
        }
        else
        {
            this.SetBlockRedstoneOn(var1, var2, var3, var4, true);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        return FCUtilsMisc.GetOppositeFacing(var5) == var6 ? this.IsRedstoneOn(var1, var2, var3, var4) : false;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        return FCUtilsMisc.GetOppositeFacing(var5) == var6 ? this.IsRedstoneOn(var1, var2, var3, var4) : false;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & -2) >> 1;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        var6 = var6 & 1 | var5 << 1;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return (var1 & -2) >> 1;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 = var1 & 1 | var2 << 1;
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

    public boolean IsRedstoneOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 1) > 0;
    }

    public void SetBlockRedstoneOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (var5 != this.IsRedstoneOn(var1, var2, var3, var4))
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var5)
            {
                var6 |= 1;
                var1.playAuxSFX(2234, var2, var3, var4, 0);
            }
            else
            {
                var6 &= -2;
            }

            var1.setBlockMetadata(var2, var3, var4, var6);
            int var7 = this.GetFacing(var1, var2, var3, var4);
            FCUtilsBlockPos var8 = new FCUtilsBlockPos(var2, var3, var4);
            var8.AddFacingAsOffset(var7);
            Block var9 = Block.blocksList[var1.getBlockId(var8.i, var8.j, var8.k)];

            if (var9 != null)
            {
                var9.onNeighborBlockChange(var1, var8.i, var8.j, var8.k, this.blockID);
            }

            var1.notifyBlocksOfNeighborChange(var8.i, var8.j, var8.k, this.blockID);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        }
    }
}
