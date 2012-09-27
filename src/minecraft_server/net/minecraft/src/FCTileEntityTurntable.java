package net.minecraft.src;

public class FCTileEntityTurntable extends TileEntity
{
    private final int m_iMaxHeightOfBlocksRotated = 2;
    private final int m_iRotationsToSpinPottery = 8;
    private int m_iRotationCount = 0;
    public int m_iPotteryRotationCount = 0;
    private boolean m_bPotteryRotated = false;
    private static int[] m_iTicksToRotate = new int[] {10, 20, 40, 80, 200, 600, 1200, 2400, 6000, 12000, 24000};
    private int m_iSwitchOverride = -1;

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.m_iRotationCount = var1.getInteger("m_iRotationCount");

        if (var1.hasKey("m_iSwitchSetting"))
        {
            this.m_iSwitchOverride = var1.getInteger("m_iSwitchSetting");

            if (this.m_iSwitchOverride > 3)
            {
                this.m_iSwitchOverride = 3;
            }
        }

        if (var1.hasKey("m_iPotteryRotationCount"))
        {
            this.m_iPotteryRotationCount = var1.getInteger("m_iPotteryRotationCount");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("m_iRotationCount", this.m_iRotationCount);
        var1.setInteger("m_iPotteryRotationCount", this.m_iPotteryRotationCount);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (this.m_iSwitchOverride >= 0)
        {
            ((FCBlockTurntable)mod_FCBetterThanWolves.fcTurntable).SetSwitchSetting(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.m_iSwitchOverride);
            this.m_iSwitchOverride = -1;
        }

        byte var1 = 9;

        if (this.worldObj.checkChunksExist(this.xCoord - var1, this.yCoord - var1, this.zCoord - var1, this.xCoord + var1, this.yCoord + var1, this.zCoord + var1))
        {
            if (((FCBlockTurntable)mod_FCBetterThanWolves.fcTurntable).IsBlockMechanicalOn(this.worldObj, this.xCoord, this.yCoord, this.zCoord))
            {
                ++this.m_iRotationCount;

                if (this.m_iRotationCount >= this.GetTicksToRotate())
                {
                    this.RotateTurntable();
                    this.m_iRotationCount = 0;
                }
            }
            else
            {
                this.m_iRotationCount = 0;
            }
        }
    }

    private int GetTicksToRotate()
    {
        return m_iTicksToRotate[((FCBlockTurntable)mod_FCBetterThanWolves.fcTurntable).GetSwitchSetting(this.worldObj, this.xCoord, this.yCoord, this.zCoord)];
    }

    private void RotateTurntable()
    {
        boolean var1 = ((FCBlockTurntable)mod_FCBetterThanWolves.fcTurntable).IsBlockRedstoneOn(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        this.m_bPotteryRotated = false;

        for (int var2 = this.yCoord + 1; var2 <= this.yCoord + 2; ++var2)
        {
            boolean var3 = this.CanBlockTransmitRotationHorizontally(this.xCoord, var2, this.zCoord);
            boolean var4 = this.CanBlockTransmitRotationVertically(this.xCoord, var2, this.zCoord);
            this.RotateBlock(this.xCoord, var2, this.zCoord, var1);

            if (var3)
            {
                this.RotateBlocksAttachedToBlock(this.xCoord, var2, this.zCoord, var1);
            }

            if (!var4)
            {
                break;
            }
        }

        if (!this.m_bPotteryRotated)
        {
            this.m_iPotteryRotationCount = 0;
        }

        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, mod_FCBetterThanWolves.fcTurntable.blockID);
    }

    private void RotateBlock(int var1, int var2, int var3, boolean var4)
    {
        if (!this.worldObj.isAirBlock(var1, var2, var3))
        {
            int var5 = this.worldObj.getBlockId(var1, var2, var3);
            Block var6 = Block.blocksList[var5];
            this.worldObj.notifyBlocksOfNeighborChange(var1, var2, var3, var5);

            if (var5 == Block.blockClay.blockID)
            {
                this.RotateClay(var1, var2, var3, var4);
                this.m_bPotteryRotated = true;
            }
            else if (var6 instanceof FCIBlockCraftableOnTurntable)
            {
                this.RotateCraftableBlock((FCIBlockCraftableOnTurntable)var6, var1, var2, var3, var4);
                this.m_bPotteryRotated = true;
            }
            else
            {
                if (var6 instanceof BlockRail)
                {
                    BlockRail var7 = (BlockRail)var6;
                    this.RotateRail(var7, var1, var2, var3, var4);
                }

                if (var6 instanceof FCIBlock)
                {
                    FCIBlock var8 = (FCIBlock)var6;

                    if (var8.CanRotateOnTurntable(this.worldObj, var1, var2, var3))
                    {
                        var8.RotateAroundJAxis(this.worldObj, var1, var2, var3, var4);
                    }
                }
                else if (var5 != Block.redstoneRepeaterActive.blockID && var5 != Block.redstoneRepeaterIdle.blockID)
                {
                    if (var6 instanceof BlockPistonBase)
                    {
                        BlockPistonBase var9 = (BlockPistonBase)var6;
                        this.RotatePiston(var9, var1, var2, var3, var4);
                    }
                    else if (var5 == Block.dispenser.blockID)
                    {
                        this.RotateDispenser(var1, var2, var3, var4);
                    }
                    else if (var5 == Block.chest.blockID)
                    {
                        this.RotateChest(var1, var2, var3, var4);
                    }
                    else if (var6 instanceof BlockFurnace)
                    {
                        BlockFurnace var11 = (BlockFurnace)var6;
                        this.RotateFurnace(var1, var2, var3, var4);
                    }
                    else if (var6 instanceof BlockStairs)
                    {
                        this.RotateStairs(var1, var2, var3, var4);
                    }
                    else if (var6 instanceof BlockPumpkin)
                    {
                        this.RotatePumpkin(var1, var2, var3, var4);
                    }
                }
                else
                {
                    BlockRedstoneRepeater var10 = (BlockRedstoneRepeater)var6;
                    this.RotateRepeater(var10, var1, var2, var3, var4);
                }
            }
        }
    }

    private boolean CanBlockTransmitRotationHorizontally(int var1, int var2, int var3)
    {
        int var4 = this.worldObj.getBlockId(var1, var2, var3);
        Block var5 = Block.blocksList[var4];

        if (var5 instanceof FCIBlock)
        {
            FCIBlock var7 = (FCIBlock)var5;
            return var7.CanTransmitRotationHorizontallyOnTurntable(this.worldObj, var1, var2, var3);
        }
        else if (var4 == Block.glass.blockID)
        {
            return true;
        }
        else if (var5 instanceof BlockPistonBase)
        {
            int var6 = this.worldObj.getBlockMetadata(var1, var2, var3);
            return !BlockPistonBase.isExtended(var6);
        }
        else
        {
            return var4 != Block.pistonExtension.blockID && var4 != Block.pistonMoving.blockID ? this.worldObj.isBlockNormalCube(var1, var2, var3) : false;
        }
    }

    private boolean CanBlockTransmitRotationVertically(int var1, int var2, int var3)
    {
        int var4 = this.worldObj.getBlockId(var1, var2, var3);
        Block var5 = Block.blocksList[var4];

        if (var5 instanceof FCIBlock)
        {
            FCIBlock var7 = (FCIBlock)var5;
            return var7.CanTransmitRotationVerticallyOnTurntable(this.worldObj, var1, var2, var3);
        }
        else if (var4 == Block.glass.blockID)
        {
            return true;
        }
        else if (var4 == Block.blockClay.blockID)
        {
            return false;
        }
        else if (var5 instanceof BlockPistonBase)
        {
            int var6 = this.worldObj.getBlockMetadata(var1, var2, var3);
            return !BlockPistonBase.isExtended(var6);
        }
        else
        {
            return var4 != Block.pistonExtension.blockID && var4 != Block.pistonMoving.blockID ? this.worldObj.isBlockNormalCube(var1, var2, var3) : false;
        }
    }

    private void RotateBlocksAttachedToBlock(int var1, int var2, int var3, boolean var4)
    {
        int[] var5 = new int[4];
        int var6;

        for (var6 = 0; var6 < 4; ++var6)
        {
            var5[var6] = 0;
        }

        int var8;
        int var9;
        Block var11;

        for (var6 = 2; var6 <= 5; ++var6)
        {
            FCUtilsBlockPos var7 = new FCUtilsBlockPos(var1, var2, var3);
            var7.AddFacingAsOffset(var6);
            var8 = this.worldObj.getBlockId(var7.i, var7.j, var7.k);
            var9 = this.worldObj.getBlockMetadata(var7.i, var7.j, var7.k);
            boolean var10 = false;

            if (var8 != Block.torchWood.blockID && var8 != Block.torchRedstoneActive.blockID && var8 != Block.torchRedstoneIdle.blockID)
            {
                if (var8 == Block.ladder.blockID)
                {
                    if (var9 == var6)
                    {
                        var10 = true;
                    }
                }
                else if (var8 == Block.signWall.blockID)
                {
                    var11 = Block.blocksList[var8];

                    if (var9 == var6)
                    {
                        var11.dropBlockAsItem(this.worldObj, var7.i, var7.j, var7.k, var8, 0);
                        this.worldObj.setBlockWithNotify(var7.i, var7.j, var7.k, 0);
                    }
                }
                else if (var8 == Block.button.blockID || var8 == Block.lever.blockID)
                {
                    var11 = Block.blocksList[var8];

                    if (var9 == 1 && var6 == 5 || var9 == 2 && var6 == 4 || var9 == 3 && var6 == 3 || var9 == 4 && var6 == 2)
                    {
                        var11.dropBlockAsItem(this.worldObj, var7.i, var7.j, var7.k, var8, 0);
                        this.worldObj.setBlockWithNotify(var7.i, var7.j, var7.k, 0);
                    }
                }
            }
            else if (var9 == 1 && var6 == 5 || var9 == 2 && var6 == 4 || var9 == 3 && var6 == 3 || var9 == 4 && var6 == 2)
            {
                var10 = true;

                if (var8 == Block.torchRedstoneIdle.blockID)
                {
                    var8 = Block.torchRedstoneActive.blockID;
                }
            }

            if (var10)
            {
                int var16 = FCUtilsMisc.RotateFacingAroundJ(var6, var4);
                var5[var16 - 2] = var8;
                this.worldObj.setBlockWithNotify(var7.i, var7.j, var7.k, 0);
            }
        }

        for (var6 = 0; var6 < 4; ++var6)
        {
            int var14 = var5[var6];

            if (var14 != 0)
            {
                var8 = var6 + 2;
                var9 = 0;
                FCUtilsBlockPos var15 = new FCUtilsBlockPos(var1, var2, var3);
                var15.AddFacingAsOffset(var8);

                if (var14 != Block.torchWood.blockID && var14 != Block.torchRedstoneActive.blockID)
                {
                    if (var14 == Block.ladder.blockID)
                    {
                        var9 = var8;
                    }
                }
                else
                {
                    byte var17 = 0;

                    if (var8 == 2)
                    {
                        var17 = 4;
                    }
                    else if (var8 == 3)
                    {
                        var17 = 3;
                    }
                    else if (var8 == 4)
                    {
                        var17 = 2;
                    }
                    else if (var8 == 5)
                    {
                        var17 = 1;
                    }

                    var9 = var17;
                }

                if (FCUtilsWorld.IsReplaceableBlock(this.worldObj, var15.i, var15.j, var15.k))
                {
                    this.worldObj.setBlockWithNotify(var15.i, var15.j, var15.k, var14);
                    this.worldObj.setBlockMetadataWithNotify(var15.i, var15.j, var15.k, var9);
                }
                else
                {
                    var11 = Block.blocksList[var14];
                    int var12 = FCUtilsMisc.RotateFacingAroundJ(var8, !var4);
                    FCUtilsBlockPos var13 = new FCUtilsBlockPos(var1, var2, var3);
                    var13.AddFacingAsOffset(var12);
                    var11.dropBlockAsItem(this.worldObj, var13.i, var13.j, var13.k, var14, 0);
                }
            }
        }
    }

    private void RotatePiston(BlockPistonBase var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.worldObj.getBlockMetadata(var2, var3, var4);

        if (!BlockPistonBase.isExtended(var6))
        {
            int var7 = var6 & 7;
            int var8 = FCUtilsMisc.RotateFacingAroundJ(var7, var5);

            if (var7 != var8)
            {
                var6 = var6 & -8 | var8;
                this.worldObj.setBlockMetadataWithNotify(var2, var3, var4, var6);
                this.worldObj.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            }
        }
    }

    private void RotateRepeater(BlockRedstoneRepeater var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.worldObj.getBlockMetadata(var2, var3, var4);
        int var7 = var6 & 3;

        if (var5)
        {
            ++var7;

            if (var7 > 3)
            {
                var7 = 0;
            }
        }
        else
        {
            --var7;

            if (var7 < 0)
            {
                var7 = 3;
            }
        }

        var6 = var6 & -4 | var7;
        this.worldObj.setBlockMetadataWithNotify(var2, var3, var4, var6);
        this.worldObj.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        var1.onNeighborBlockChange(this.worldObj, var2, var3, var4, 0);
    }

    private void RotateRail(BlockRail var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.worldObj.getBlockMetadata(var2, var3, var4);
        int var7 = var6;

        if (var1.isPowered())
        {
            var7 = var6 & 7;
        }

        if (var7 == 0)
        {
            var7 = 1;
        }
        else if (var7 == 1)
        {
            var7 = 0;
        }
        else if (var7 != 2 && var7 != 3 && var7 != 4 && var7 != 5)
        {
            if (var7 == 6)
            {
                if (var5)
                {
                    var7 = 7;
                }
                else
                {
                    var7 = 9;
                }
            }
            else if (var7 == 7)
            {
                if (var5)
                {
                    var7 = 8;
                }
                else
                {
                    var7 = 6;
                }
            }
            else if (var7 == 8)
            {
                if (var5)
                {
                    var7 = 9;
                }
                else
                {
                    var7 = 7;
                }
            }
            else if (var7 == 9)
            {
                if (var5)
                {
                    var7 = 6;
                }
                else
                {
                    var7 = 8;
                }
            }
        }

        if (var1.isPowered())
        {
            var6 = var6 & 8 | var7;
        }
        else
        {
            var6 = var7;
        }

        this.worldObj.setBlockMetadataWithNotify(var2, var3, var4, var6);
        this.worldObj.markBlocksDirty(var2, var3, var4, var2, var3, var4);
    }

    private void RotateDispenser(int var1, int var2, int var3, boolean var4)
    {
        int var5 = this.worldObj.getBlockMetadata(var1, var2, var3);
        int var6 = FCUtilsMisc.RotateFacingAroundJ(var5, var4);
        this.worldObj.setBlockMetadataWithNotify(var1, var2, var3, var6);
        this.worldObj.markBlocksDirty(var1, var2, var3, var1, var2, var3);
    }

    private void RotateFurnace(int var1, int var2, int var3, boolean var4)
    {
        int var5 = this.worldObj.getBlockMetadata(var1, var2, var3);
        int var6 = FCUtilsMisc.RotateFacingAroundJ(var5, var4);
        this.worldObj.setBlockMetadataWithNotify(var1, var2, var3, var6);
        this.worldObj.markBlocksDirty(var1, var2, var3, var1, var2, var3);
    }

    private void RotateStairs(int var1, int var2, int var3, boolean var4)
    {
        int var5 = this.worldObj.getBlockMetadata(var1, var2, var3);
        int var6 = (var5 & -5) + 2;
        var6 = FCUtilsMisc.RotateFacingAroundJ(var6, !var4);
        var5 = var5 & 4 | var6 - 2;
        this.worldObj.setBlockMetadataWithNotify(var1, var2, var3, var5);
        this.worldObj.markBlocksDirty(var1, var2, var3, var1, var2, var3);
    }

    private void RotatePumpkin(int var1, int var2, int var3, boolean var4)
    {
        int var5 = this.worldObj.getBlockMetadata(var1, var2, var3);
        int var6;

        if (var4)
        {
            var6 = var5 + 1;

            if (var6 > 3)
            {
                var6 = 0;
            }
        }
        else
        {
            var6 = var5 - 1;

            if (var6 < 0)
            {
                var6 = 3;
            }
        }

        this.worldObj.setBlockMetadataWithNotify(var1, var2, var3, var6);
        this.worldObj.markBlocksDirty(var1, var2, var3, var1, var2, var3);
    }

    private void RotateChest(int var1, int var2, int var3, boolean var4)
    {
        if (this.worldObj.getBlockId(var1 - 1, var2, var3) != Block.chest.blockID && this.worldObj.getBlockId(var1 + 1, var2, var3) != Block.chest.blockID && this.worldObj.getBlockId(var1, var2, var3 - 1) != Block.chest.blockID && this.worldObj.getBlockId(var1, var2, var3 + 1) != Block.chest.blockID)
        {
            int var5 = this.worldObj.getBlockMetadata(var1, var2, var3);
            int var6 = FCUtilsMisc.RotateFacingAroundJ(var5, var4);
            this.worldObj.setBlockMetadataWithNotify(var1, var2, var3, var6);
            this.worldObj.markBlocksDirty(var1, var2, var3, var1, var2, var3);
        }
    }

    private void RotateClay(int var1, int var2, int var3, boolean var4)
    {
        Block var5 = Block.blockClay;
        this.worldObj.playAuxSFX(2001, var1, var2, var3, this.worldObj.getBlockId(var1, var2, var3) + (this.worldObj.getBlockMetadata(var1, var2, var3) << 12));
        ++this.m_iPotteryRotationCount;

        if (this.m_iPotteryRotationCount >= 8)
        {
            this.worldObj.setBlockWithNotify(var1, var2, var3, mod_FCBetterThanWolves.fcUnfiredPottery.blockID);
            FCUtilsItem.EjectSingleItemWithRandomOffset(this.worldObj, var1, var2 + 1, var3, Item.clay.shiftedIndex, 0);
            this.m_iPotteryRotationCount = 0;
        }
    }

    private void RotateCraftableBlock(FCIBlockCraftableOnTurntable var1, int var2, int var3, int var4, boolean var5)
    {
        var1.OnRotated(this.worldObj, var2, var3, var4);
        ++this.m_iPotteryRotationCount;

        if (this.m_iPotteryRotationCount >= var1.GetRotationsToCraft(this.worldObj, var2, var3, var4))
        {
            this.worldObj.playAuxSFX(2001, var2, var3, var4, this.worldObj.getBlockId(var2, var3, var4) + (this.worldObj.getBlockMetadata(var2, var3, var4) << 12));
            var1.OnCraft(this.worldObj, var2, var3, var4);
            int var6 = var1.GetBlockIDOnCraft(this.worldObj, var2, var3, var4);
            int var7 = var1.GetBlockMetadataOnCraft(this.worldObj, var2, var3, var4);
            int var8 = var1.GetNumItemsDroppedOnCraft(this.worldObj, var2, var3, var4);
            int var9 = var1.GetItemIDDroppedOnCraft(this.worldObj, var2, var3, var4);
            int var10 = var1.GetItemMetadataDroppedOnCraft(this.worldObj, var2, var3, var4);

            for (int var11 = 0; var11 < var8; ++var11)
            {
                FCUtilsItem.EjectSingleItemWithRandomOffset(this.worldObj, var2, var3 + 1, var4, var9, var10);
            }

            this.worldObj.setBlockAndMetadataWithNotify(var2, var3, var4, var6, var7);
            this.m_iPotteryRotationCount = 0;
        }
    }
}
