package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockDetectorBlock extends Block implements FCIBlock, ITextureProvider
{
    private final int detectorTextureIDTop;
    private final int detectorTextureIDFront;
    private final int detectorTextureIDFrontOn;
    private final int detectorTextureIDSide;
    private final int detectorTextureIDBottom;
    private static final int iDetectorTickRate = 4;

    public FCBlockDetectorBlock(int var1)
    {
        super(var1, Material.rock);
        this.blockIndexInTexture = 10;
        this.detectorTextureIDTop = 10;
        this.detectorTextureIDFront = 11;
        this.detectorTextureIDFrontOn = 12;
        this.detectorTextureIDSide = 13;
        this.detectorTextureIDBottom = 14;
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 4;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 3 ? this.detectorTextureIDFront : (var1 == 1 ? this.detectorTextureIDTop : (var1 == 0 ? this.detectorTextureIDBottom : this.detectorTextureIDSide));
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
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        this.SetBlockOn(var1, var2, var3, var4, false);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
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
        boolean var6 = this.PlaceDetectorLogicIfNecessary(var1, var2, var3, var4);
        boolean var7 = this.CheckForDetection(var1, var2, var3, var4);
        int var8 = this.GetFacing(var1, var2, var3, var4);

        if (var8 == 1)
        {
            if (!var7 && FCUtilsMisc.IsBlockBeingPrecipitatedOn(var1, var2, var3 + 1, var4))
            {
                var7 = true;
            }

            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }

        if (var7)
        {
            if (!this.IsBlockOn(var1, var2, var3, var4))
            {
                this.SetBlockOn(var1, var2, var3, var4, true);
            }
        }
        else if (this.IsBlockOn(var1, var2, var3, var4))
        {
            if (!var6)
            {
                this.SetBlockOn(var1, var2, var3, var4, false);
            }
            else
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            }
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.IsBlockOn(var1, var2, var3, var4);
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5)
    {
        return false;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote && var5 instanceof EntityArrow)
        {
            int var6 = this.GetFacing(var1, var2, var3, var4);
            FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);
            var7.AddFacingAsOffset(var6);

            if (var1.getBlockId(var7.i, var7.j, var7.k) == mod_FCBetterThanWolves.fcBlockDetectorLogic.blockID)
            {
                mod_FCBetterThanWolves.fcBlockDetectorLogic.onEntityCollidedWithBlock(var1, var7.i, var7.j, var7.k, var5);
            }
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
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
        if (FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5))
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
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
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        var1.notifyBlockChange(var2, var3, var4, this.blockID);
        return true;
    }

    public boolean IsBlockOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 1) > 0;
    }

    public void SetBlockOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (var5 != this.IsBlockOn(var1, var2, var3, var4))
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

            var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        }
    }

    public boolean PlaceDetectorLogicIfNecessary(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var6 = new FCUtilsBlockPos(var2, var3, var4);
        var6.AddFacingAsOffset(var5);
        int var7 = var1.getBlockId(var6.i, var6.j, var6.k);
        FCBlockDetectorLogic var8;

        if (var7 == 0)
        {
            var8 = (FCBlockDetectorLogic)((FCBlockDetectorLogic)mod_FCBetterThanWolves.fcBlockDetectorLogic);
            var1.setBlock(var6.i, var6.j, var6.k, var8.blockID);
            var8.SetIsDetectorLogicFlag(var1, var6.i, var6.j, var6.k, true);
            var8.FullyValidateBlock(var1, var6.i, var6.j, var6.k);
            return true;
        }
        else
        {
            if (var7 == mod_FCBetterThanWolves.fcBlockDetectorLogic.blockID || var7 == mod_FCBetterThanWolves.fcBlockDetectorGlowingLogic.blockID)
            {
                var8 = (FCBlockDetectorLogic)((FCBlockDetectorLogic)mod_FCBetterThanWolves.fcBlockDetectorLogic);

                if (!var8.IsDetectorLogicFlagOn(var1, var6.i, var6.j, var6.k))
                {
                    var8.SetIsDetectorLogicFlag(var1, var6.i, var6.j, var6.k, true);

                    if (var8.HasValidLensSource(var1, var6.i, var6.j, var6.k))
                    {
                        var8.SetIsIntersectionPointFlag(var1, var6.i, var6.j, var6.k, true);
                    }
                }
            }

            return false;
        }
    }

    public boolean CheckForDetection(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var6 = new FCUtilsBlockPos(var2, var3, var4);
        var6.AddFacingAsOffset(var5);
        int var7 = var1.getBlockId(var6.i, var6.j, var6.k);

        if (var7 > 0)
        {
            if (!FCBlockDetectorLogic.IsLogicBlock(var7))
            {
                if (var7 == mod_FCBetterThanWolves.fcLens.blockID)
                {
                    FCBlockLens var9 = (FCBlockLens)((FCBlockLens)mod_FCBetterThanWolves.fcLens);

                    if (var9.GetFacing(var1, var6.i, var6.j, var6.k) == FCUtilsMisc.GetOppositeFacing(var5))
                    {
                        return var9.IsLit(var1, var6.i, var6.j, var6.k);
                    }
                }

                return true;
            }

            FCBlockDetectorLogic var8 = (FCBlockDetectorLogic)((FCBlockDetectorLogic)mod_FCBetterThanWolves.fcBlockDetectorLogic);

            if (var8.IsEntityCollidingFlagOn(var1, var6.i, var6.j, var6.k) || var8.IsLitFlagOn(var1, var6.i, var6.j, var6.k))
            {
                return true;
            }

            if (var1.getBlockId(var6.i, var6.j - 1, var6.k) == Block.crops.blockID && var1.getBlockMetadata(var6.i, var6.j - 1, var6.k) == 7)
            {
                return true;
            }
        }

        return false;
    }
}
