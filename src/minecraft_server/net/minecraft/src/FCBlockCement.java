package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockCement extends BlockContainer implements ITextureProvider, FCIBlock
{
    private final int iCementTexture;
    private final int iCementPartiallyDryTexture;
    public static final int iMaxCementSpreadDist = 16;
    public static final int iCementTicksToDry = 12;
    public static final int iCementTicksToPartiallyDry = 8;
    boolean[] tempSpreadToSideFlags;
    int[] tempClosestDownslopeToSideDist;

    protected FCBlockCement(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcCementMaterial);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setHardness(100.0F);
        this.setLightOpacity(255);
        this.setBlockName("fccement");
        this.setStepSound(Block.soundSandFootstep);
        this.iCementTexture = 15;
        this.iCementPartiallyDryTexture = 16;
        this.blockIndexInTexture = this.iCementTexture;
        this.tempSpreadToSideFlags = new boolean[4];
        this.tempClosestDownslopeToSideDist = new int[4];
        this.setTickRandomly(true);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityCement();
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
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
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int var1, boolean var2)
    {
        return var2 && var1 == 0;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return var1.getBlockId(var2, var3 + 1, var4) != this.blockID ? AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)((float)(var2 + 1)), (double)((float)var3 + 0.5F), (double)((float)(var4 + 1))) : AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)((float)(var2 + 1)), (double)((float)(var3 + 1)), (double)((float)(var4 + 1)));
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomCementRenderID;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
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

        if (var1.getBlockId(var2, var3, var4) == this.blockID)
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());

            if (var1.isBlockIndirectlyGettingPowered(var2, var3, var4))
            {
                this.SetCementPowered(var1, var2, var3, var4, true);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetCementSpreadDist(var1, var2, var3, var4);
        boolean var7 = this.IsCementPowered(var1, var2, var3, var4);
        boolean var8 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4);

        if (var7 != var8)
        {
            this.SetCementPowered(var1, var2, var3, var4, var8);
        }

        int var10;
        int var13;

        if (var6 > 0)
        {
            byte var9 = -100;
            var13 = this.CheckForLesserSpreadDist(var1, var2 - 1, var3, var4, var9);
            var13 = this.CheckForLesserSpreadDist(var1, var2 + 1, var3, var4, var13);
            var13 = this.CheckForLesserSpreadDist(var1, var2, var3, var4 - 1, var13);
            var13 = this.CheckForLesserSpreadDist(var1, var2, var3, var4 + 1, var13);
            var13 = this.CheckForLesserSpreadDist(var1, var2, var3 + 1, var4, var13);

            if (var13 < 0)
            {
                var13 = -1;
            }
            else
            {
                ++var13;
            }

            var10 = this.GetCementSpreadDist(var1, var2, var3 + 1, var4);

            if (var10 >= 0 && var10 < var13)
            {
                var13 = var10 + 1;
            }

            if (var13 > 0 && var13 < var6)
            {
                var6 = var13;
                this.SetCementSpreadDist(var1, var2, var3, var4, var13);
                this.SetCementDryTime(var1, var2, var3, var4, 0);
            }
        }
        else if (var6 == 0 && var8)
        {
            this.SetCementDryTime(var1, var2, var3, var4, 0);
        }

        var13 = this.GetCementDryTime(var1, var2, var3, var4);
        ++var13;
        var10 = this.CheckNeighboursCloserToSourceForMinDryTime(var1, var2, var3, var4);

        if (var10 <= var13)
        {
            if (var10 <= 0)
            {
                var13 = 0;
            }
            else
            {
                var13 = var10 - 1;
            }
        }

        if (var13 > 12)
        {
            var1.setBlockWithNotify(var2, var3, var4, Block.stone.blockID);
        }
        else
        {
            this.SetCementDryTime(var1, var2, var3, var4, var13);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());

            if (this.IsBlockOpenToSpread(var1, var2, var3 - 1, var4))
            {
                int var11 = var6 + 1;

                if (var11 <= 16)
                {
                    var1.setBlockWithNotify(var2, var3 - 1, var4, this.blockID);
                    this.SetCementSpreadDist(var1, var2, var3 - 1, var4, var11);
                }
            }
            else if (var6 >= 0 && (var6 == 0 || this.blockBlocksFlow(var1, var2, var3 - 1, var4)))
            {
                boolean[] var14 = this.CheckSideBlocksForPotentialSpread(var1, var2, var3, var4);
                int var12 = var6 + 1;

                if (var12 <= 16)
                {
                    if (var14[0])
                    {
                        this.AttemptToSpreadToBlock(var1, var2 - 1, var3, var4, var12);
                    }

                    if (var14[1])
                    {
                        this.AttemptToSpreadToBlock(var1, var2 + 1, var3, var4, var12);
                    }

                    if (var14[2])
                    {
                        this.AttemptToSpreadToBlock(var1, var2, var3, var4 - 1, var12);
                    }

                    if (var14[3])
                    {
                        this.AttemptToSpreadToBlock(var1, var2, var3, var4 + 1, var12);
                    }
                }
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

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public boolean IsCementPowered(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return (var5 & 1) > 0;
    }

    private void SetCementPowered(World var1, int var2, int var3, int var4, boolean var5)
    {
        boolean var6 = this.IsCementPowered(var1, var2, var3, var4);

        if (var6 != var5)
        {
            int var7 = var1.getBlockMetadata(var2, var3, var4);

            if (var5)
            {
                var1.playAuxSFX(2225, var2, var3, var4, 0);
                var7 |= 1;
            }
            else
            {
                var7 &= -2;
            }

            var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
        }
    }

    public float GetRenderHeight(IBlockAccess var1, int var2, int var3, int var4)
    {
        float var5 = 1.0F;

        if (var1.getBlockMaterial(var2, var3, var4) == this.blockMaterial)
        {
            int var6 = this.GetCementSpreadDist(var1, var2, var3, var4);
            var5 = (float)(var6 + 1) / 18.0F;

            if (this.IsCementPartiallyDry(var1, var2, var3, var4))
            {
                var5 *= 0.1F;
            }
            else
            {
                var5 *= 0.5F;
            }
        }

        return var5;
    }

    public int GetCementSpreadDist(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (var1.getBlockMaterial(var2, var3, var4) != this.blockMaterial)
        {
            return -1;
        }
        else
        {
            FCTileEntityCement var5 = (FCTileEntityCement)var1.getBlockTileEntity(var2, var3, var4);
            return var5.GetSpreadDist();
        }
    }

    public void SetCementSpreadDist(World var1, int var2, int var3, int var4, int var5)
    {
        FCTileEntityCement var6 = (FCTileEntityCement)var1.getBlockTileEntity(var2, var3, var4);
        var6.SetSpreadDist(var5);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
    }

    public boolean IsCementSourceBlock(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetCementSpreadDist(var1, var2, var3, var4) == 0;
    }

    public int GetCementDryTime(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (var1.getBlockMaterial(var2, var3, var4) != this.blockMaterial)
        {
            return 0;
        }
        else
        {
            FCTileEntityCement var5 = (FCTileEntityCement)var1.getBlockTileEntity(var2, var3, var4);
            return var5.GetDryTime();
        }
    }

    public void SetCementDryTime(World var1, int var2, int var3, int var4, int var5)
    {
        FCTileEntityCement var6 = (FCTileEntityCement)var1.getBlockTileEntity(var2, var3, var4);
        var6.SetDryTime(var5);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
    }

    public boolean IsCementPartiallyDry(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetCementDryTime(var1, var2, var3, var4) >= 8;
    }

    private int CheckNeighboursCloserToSourceForMinDryTime(World var1, int var2, int var3, int var4)
    {
        short var5 = 1000;
        int var6 = this.GetCementSpreadDist(var1, var2, var3, var4);
        int var7 = this.GetLesserDryTimeIfCloserToSource(var1, var2, var3 + 1, var4, var6, var5);
        var7 = this.GetLesserDryTimeIfCloserToSource(var1, var2 + 1, var3, var4, var6, var7);
        var7 = this.GetLesserDryTimeIfCloserToSource(var1, var2 - 1, var3, var4, var6, var7);
        var7 = this.GetLesserDryTimeIfCloserToSource(var1, var2, var3, var4 + 1, var6, var7);
        var7 = this.GetLesserDryTimeIfCloserToSource(var1, var2, var3, var4 - 1, var6, var7);
        return var7;
    }

    private int GetLesserDryTimeIfCloserToSource(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        Material var7 = var1.getBlockMaterial(var2, var3, var4);

        if (var7 == this.blockMaterial)
        {
            int var8 = this.GetCementSpreadDist(var1, var2, var3, var4);

            if (var8 < var5)
            {
                int var9 = this.GetCementDryTime(var1, var2, var3, var4);

                if (var9 < var6)
                {
                    return var9;
                }
            }
        }

        return var6;
    }

    private void AttemptToSpreadToBlock(World var1, int var2, int var3, int var4, int var5)
    {
        if (this.IsBlockOpenToSpread(var1, var2, var3, var4))
        {
            int var6 = var1.getBlockId(var2, var3, var4);

            if (var6 > 0)
            {
                Block.blocksList[var6].dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
            }

            var1.setBlockWithNotify(var2, var3, var4, this.blockID);
            this.SetCementSpreadDist(var1, var2, var3, var4, var5);
        }
    }

    private boolean[] CheckSideBlocksForPotentialSpread(World var1, int var2, int var3, int var4)
    {
        for (int var5 = 0; var5 < 4; ++var5)
        {
            int var6 = var2;
            int var8 = var4;

            switch (var5)
            {
                case 0:
                    var6 = var2 - 1;
                    break;

                case 1:
                    var6 = var2 + 1;
                    break;

                case 2:
                    var8 = var4 - 1;
                    break;

                default:
                    var8 = var4 + 1;
            }

            if (!this.blockBlocksFlow(var1, var6, var3, var8) && (var1.getBlockMaterial(var6, var3, var8) != this.blockMaterial || !this.IsCementSourceBlock(var1, var6, var3, var8)))
            {
                this.tempSpreadToSideFlags[var5] = true;
            }
            else
            {
                this.tempSpreadToSideFlags[var5] = false;
            }
        }

        return this.tempSpreadToSideFlags;
    }

    private boolean[] CheckSideBlocksForDownslope(World var1, int var2, int var3, int var4)
    {
        int var5;
        int var6;

        for (var5 = 0; var5 < 4; ++var5)
        {
            this.tempClosestDownslopeToSideDist[var5] = 1000;
            var6 = var2;
            int var8 = var4;

            if (var5 == 0)
            {
                var6 = var2 - 1;
            }
            else if (var5 == 1)
            {
                var6 = var2 + 1;
            }
            else if (var5 == 2)
            {
                var8 = var4 - 1;
            }
            else if (var5 == 3)
            {
                var8 = var4 + 1;
            }

            if (!this.blockBlocksFlow(var1, var6, var3, var8) && (var1.getBlockMaterial(var6, var3, var8) != this.blockMaterial || !this.IsCementSourceBlock(var1, var6, var3, var8)))
            {
                if (!this.blockBlocksFlow(var1, var6, var3 - 1, var8))
                {
                    this.tempClosestDownslopeToSideDist[var5] = 0;
                }
                else
                {
                    this.tempClosestDownslopeToSideDist[var5] = this.RecursivelyCheckSideBlocksForDownSlope(var1, var6, var3, var8, 1, var5);
                }
            }
        }

        var5 = this.tempClosestDownslopeToSideDist[0];

        for (var6 = 1; var6 < 4; ++var6)
        {
            if (this.tempClosestDownslopeToSideDist[var6] < var5)
            {
                var5 = this.tempClosestDownslopeToSideDist[var6];
            }
        }

        for (var6 = 0; var6 < 4; ++var6)
        {
            this.tempSpreadToSideFlags[var6] = this.tempClosestDownslopeToSideDist[var6] == var5;
        }

        return this.tempSpreadToSideFlags;
    }

    private int RecursivelyCheckSideBlocksForDownSlope(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        int var7 = 1000;

        for (int var8 = 0; var8 < 4; ++var8)
        {
            if ((var8 != 0 || var6 != 1) && (var8 != 1 || var6 != 0) && (var8 != 2 || var6 != 3) && (var8 != 3 || var6 != 2))
            {
                int var9 = var2;
                int var11 = var4;

                if (var8 == 0)
                {
                    var9 = var2 - 1;
                }
                else if (var8 == 1)
                {
                    var9 = var2 + 1;
                }
                else if (var8 == 2)
                {
                    var11 = var4 - 1;
                }
                else if (var8 == 3)
                {
                    var11 = var4 + 1;
                }

                if (!this.blockBlocksFlow(var1, var9, var3, var11) && this.GetCementSpreadDist(var1, var9, var3, var11) != 0)
                {
                    if (!this.blockBlocksFlow(var1, var9, var3 - 1, var11))
                    {
                        return var5;
                    }

                    if (var5 < 4)
                    {
                        int var12 = this.RecursivelyCheckSideBlocksForDownSlope(var1, var9, var3, var11, var5 + 1, var8);

                        if (var12 < var7)
                        {
                            var7 = var12;
                        }
                    }
                }
            }
        }

        return var7;
    }

    private boolean blockBlocksFlow(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);

        if (var5 != Block.doorWood.blockID && var5 != Block.doorSteel.blockID && var5 != Block.signPost.blockID && var5 != Block.ladder.blockID && var5 != Block.reed.blockID)
        {
            if (var5 == 0)
            {
                return false;
            }
            else
            {
                Material var6 = Block.blocksList[var5].blockMaterial;
                return var6 == this.blockMaterial ? false : var6.blocksMovement();
            }
        }
        else
        {
            return true;
        }
    }

    protected int CheckForLesserSpreadDist(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetCementSpreadDist(var1, var2, var3, var4);
        return var6 < 0 ? var5 : (var5 >= 0 && var6 >= var5 ? var5 : var6);
    }

    private boolean IsBlockOpenToSpread(World var1, int var2, int var3, int var4)
    {
        if (var3 < 0)
        {
            return false;
        }
        else
        {
            Material var5 = var1.getBlockMaterial(var2, var3, var4);
            return var5 == this.blockMaterial ? false : !this.blockBlocksFlow(var1, var2, var3, var4);
        }
    }
}
