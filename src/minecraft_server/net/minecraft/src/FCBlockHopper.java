package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockHopper extends BlockContainer implements FCIMechanicalDevice, FCIBlock, ITextureProvider
{
    public final int m_iHopperTopTextureIndex = 46;
    public final int m_iHopperSideTextureIndex = 47;
    public final int m_iHopperBottomTextureIndex = 48;
    public final int m_iHopperContentsTextureIndex = 49;
    private final int m_iHopperLadderFilterTextureID = 50;
    private final int m_iHopperTrapDoorFilterTextureID = 51;
    private final int m_iHopperGrateFilterTextureID = 52;
    private final int m_iHopperRollersFilterTextureID = 53;
    private final int m_iHopperWhickerFilterTextureID = 54;
    private final int m_iHopperSoulSandFilterTextureID = 55;
    private final int m_iHopperIronBarsFilterTextureID = 118;
    private static final int m_iHopperTickRate = 10;
    public static final float m_fHopperCollisionBoxHeight = 1.0F;
    public static final float m_fHopperCollisionBoxHeightWithFilter = 1.0F;
    public static final float m_fHopperVisualHeight = 1.0F;

    protected FCBlockHopper(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcHopper");
        this.blockIndexInTexture = 4;
        this.setTickRandomly(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return !this.HasFilter(var1, var2, var3, var4) ? AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)((float)(var2 + 1)), (double)((float)var3 + 1.0F), (double)((float)(var4 + 1))) : AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)((float)(var2 + 1)), (double)((float)var3 + 1.0F), (double)((float)(var4 + 1)));
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
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
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 0 ? 48 : (var1 == 1 ? 46 : 47);
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
        return mod_FCBetterThanWolves.iCustomHopperRenderID;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);

        if (this.IsBlockOn(var1, var2, var3, var4) != var6)
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }

        ((FCTileEntityHopper)var1.getBlockTileEntity(var2, var3, var4)).bHopperEjectBlocked = false;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        ItemStack var10 = var5.getCurrentEquippedItem();

        if (var10 != null && var10.getItem() instanceof FCIItem)
        {
            FCIItem var11 = (FCIItem)var10.getItem();

            if (var11.DoesItemOverrideBlockActivation())
            {
                return false;
            }
        }

        if (!var1.isRemote)
        {
            FCTileEntityHopper var13 = (FCTileEntityHopper)var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof EntityPlayerMP)
            {
                FCContainerHopper var12 = new FCContainerHopper(var5.inventory, var13);
                mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var12, mod_FCBetterThanWolves.fcHopperContainerID, 0, 0, 0);
            }
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityHopper();
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsBlockOn(var1, var2, var3, var4);
        boolean var8 = this.IsHopperFull(var1, var2, var3, var4);
        boolean var9 = this.IsRedstoneOutputOn(var1, var2, var3, var4);

        if (var7 != var6)
        {
            var1.playAuxSFX(2233, var2, var3, var4, 0);
            this.SetBlockOn(var1, var2, var3, var4, var6);
        }

        if (var8 != var9)
        {
            var1.playAuxSFX(2234, var2, var3, var4, 0);
            this.SetRedstoneOutputOn(var1, var2, var3, var4, var8);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        FCUtilsInventory.EjectInventoryContents(var1, var2, var3, var4, (IInventory)var1.getBlockTileEntity(var2, var3, var4));
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote)
        {
            if (var5 instanceof EntityItem)
            {
                this.OnEntityItemCollidedWithBlock(var1, var2, var3, var4, (EntityItem)var5);
            }
            else if (var5 instanceof EntityXPOrb)
            {
                this.OnEntityXPOrbCollidedWithBlock(var1, var2, var3, var4, (EntityXPOrb)var5);
            }
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.IsRedstoneOutputOn(var1, var2, var3, var4);
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
        return true;
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
        for (int var5 = 2; var5 <= 5; ++var5)
        {
            if (FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, var5))
            {
                return true;
            }
        }

        return FCUtilsMechPower.IsBlockPoweredByHandCrank(var1, var2, var3, var4);
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void Overpower(World var1, int var2, int var3, int var4)
    {
        this.BreakHopper(var1, var2, var3, var4);
    }

    public boolean IsBlockOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 1) > 0;
    }

    public void SetBlockOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var5)
        {
            var6 |= 1;
        }
        else
        {
            var6 &= -2;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean IsHopperFull(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 2) > 0;
    }

    public void SetHopperFull(World var1, int var2, int var3, int var4, boolean var5)
    {
        boolean var6 = this.IsHopperFull(var1, var2, var3, var4);

        if (var6 != var5)
        {
            int var7 = var1.getBlockMetadata(var2, var3, var4);

            if (var5)
            {
                var7 |= 2;
            }
            else
            {
                var7 &= -3;
            }

            var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
    }

    public boolean IsRedstoneOutputOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 4) > 0;
    }

    public void SetRedstoneOutputOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (var5 != this.IsRedstoneOutputOn(var1, var2, var3, var4))
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var5)
            {
                var6 |= 4;
            }
            else
            {
                var6 &= -5;
            }

            var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
        }
    }

    public boolean HasFilter(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetHasFilter(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var5)
        {
            var6 |= 8;
        }
        else
        {
            var6 &= -9;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public void BreakHopper(World var1, int var2, int var3, int var4)
    {
        int var5;

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, 0);
        }

        for (var5 = 0; var5 < 1; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
        }

        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }

    public void OnEntityArrowCollidedWithBlock(World var1, int var2, int var3, int var4, EntityArrow var5)
    {
        if (!(var5 instanceof FCEntityInfiniteArrow) && !(var5 instanceof FCEntityRottenArrow))
        {
            Vec3 var6 = Vec3.createVectorHelper(var5.posX, var5.posY, var5.posZ);
            AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 0.9F), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3 + 1.1F), (double)((float)(var4 + 1)));

            if (var7.isVecInside(var6) && !var5.isDead)
            {
                FCTileEntityHopper var8 = (FCTileEntityHopper)var1.getBlockTileEntity(var2, var3, var4);
                ItemStack var9 = new ItemStack(Item.arrow.shiftedIndex, 1, 0);

                if (var8.CanCurrentFilterProcessItem(var9) && FCUtilsInventory.AddItemStackToInventoryInSlotRange(var8, var9, 0, 17))
                {
                    var5.setDead();
                    var1.playAuxSFX(2231, var2, var3, var4, 0);
                }
            }
        }
    }

    public void OnEntityBroadheadCollidedWithBlock(World var1, int var2, int var3, int var4, FCEntityBroadheadArrow var5)
    {
        Vec3 var6 = Vec3.createVectorHelper(var5.posX, var5.posY, var5.posZ);
        AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 0.9F), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3 + 1.1F), (double)((float)(var4 + 1)));

        if (var7.isVecInside(var6) && !var5.isDead)
        {
            FCTileEntityHopper var8 = (FCTileEntityHopper)var1.getBlockTileEntity(var2, var3, var4);
            ItemStack var9 = new ItemStack(mod_FCBetterThanWolves.fcBroadheadArrow.shiftedIndex, 1, 0);

            if (var8.CanCurrentFilterProcessItem(var9) && FCUtilsInventory.AddItemStackToInventoryInSlotRange(var8, var9, 0, 17))
            {
                var5.setDead();
                var1.playAuxSFX(2231, var2, var3, var4, 0);
            }
        }
    }

    private void OnEntityItemCollidedWithBlock(World var1, int var2, int var3, int var4, EntityItem var5)
    {
        boolean var7 = this.HasFilter(var1, var2, var3, var4);
        float var6;

        if (!var7)
        {
            var6 = 1.0F;
        }
        else
        {
            var6 = 1.0F;
        }

        AxisAlignedBB var8 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + var6), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3 + var6 + 0.05F), (double)((float)(var4 + 1)));

        if (var5.boundingBox.intersectsWith(var8))
        {
            FCTileEntityHopper var9 = (FCTileEntityHopper)var1.getBlockTileEntity(var2, var3, var4);
            boolean var10 = false;

            if (!var5.isDead)
            {
                Item var11 = Item.itemsList[var5.item.itemID];
                ItemStack var12 = var5.item;

                if (var9.CanCurrentFilterProcessItem(var12))
                {
                    short var13 = var9.m_sFilterType;
                    int var14 = var11.shiftedIndex;
                    ItemStack var15;

                    if (var13 == 4 && var14 == Block.gravel.blockID)
                    {
                        var15 = new ItemStack(Block.sand.blockID, var12.stackSize, 0);
                        boolean var19 = false;
                        int var20;

                        if (FCUtilsInventory.AddItemStackToInventoryInSlotRange(var9, var15, 0, 17))
                        {
                            var20 = var12.stackSize;
                            var5.setDead();
                            var10 = true;
                        }
                        else
                        {
                            var20 = var5.item.stackSize - var15.stackSize;
                            var5.item.stackSize -= var20;
                        }

                        if (var20 > 0)
                        {
                            var1.playAuxSFX(2231, var2, var3, var4, 0);
                            ItemStack var17 = new ItemStack(Item.flint.shiftedIndex, var20, 0);
                            EntityItem var18 = new EntityItem(var1, var5.posX, var5.posY, var5.posZ, var17);
                            var18.delayBeforeCanPickup = 10;
                            var1.spawnEntityInWorld(var18);
                        }
                    }
                    else
                    {
                        EntityItem var16;

                        if (var13 == 6 && var14 == mod_FCBetterThanWolves.fcGroundNetherrack.shiftedIndex)
                        {
                            var15 = new ItemStack(mod_FCBetterThanWolves.fcHellfireDust, var5.item.stackSize, 0);
                            var16 = new EntityItem(var1, var5.posX, var5.posY, var5.posZ, var15);
                            var16.delayBeforeCanPickup = 10;
                            var9.IncrementContainedSoulCount(var15.stackSize);
                            var1.spawnEntityInWorld(var16);
                            var1.playAuxSFX(2228, var2, var3, var4, 0);
                            var5.setDead();
                            var10 = true;
                        }
                        else if (var13 == 6 && var14 == mod_FCBetterThanWolves.fcSoulDust.shiftedIndex)
                        {
                            var15 = new ItemStack(mod_FCBetterThanWolves.fcSawDust, var12.stackSize, 0);
                            var16 = new EntityItem(var1, var5.posX, var5.posY, var5.posZ, var15);
                            var16.delayBeforeCanPickup = 10;
                            var9.IncrementContainedSoulCount(var15.stackSize);
                            var1.spawnEntityInWorld(var16);
                            var1.playAuxSFX(2228, var2, var3, var4, 0);
                            var5.setDead();
                            var10 = true;
                        }
                        else if (FCUtilsInventory.AddItemStackToInventoryInSlotRange(var9, var5.item, 0, 17))
                        {
                            var1.playAuxSFX(2231, var2, var3, var4, 0);
                            var5.setDead();
                            var10 = true;
                        }
                    }
                }
            }
        }
    }

    private void OnEntityXPOrbCollidedWithBlock(World var1, int var2, int var3, int var4, EntityXPOrb var5)
    {
        if (!var5.isDead)
        {
            float var6 = 1.0F;
            AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + var6), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3 + var6 + 0.05F), (double)((float)(var4 + 1)));

            if (var5.boundingBox.intersectsWith(var7))
            {
                boolean var8 = false;
                FCTileEntityHopper var9 = (FCTileEntityHopper)var1.getBlockTileEntity(var2, var3, var4);
                short var10 = var9.m_sFilterType;

                if (var10 == 6)
                {
                    var8 = var9.AttemptToSwallowXPOrb(var1, var2, var3, var4, var5);
                }

                if (var8)
                {
                    var1.playAuxSFX(2231, var2, var3, var4, 0);
                }
                else
                {
                    int var11 = var1.getBlockId(var2, var3 + 1, var4);

                    if (var11 == Block.waterMoving.blockID || var11 == Block.waterStill.blockID)
                    {
                        double var12 = (double)var3 + 1.05D;

                        if (var5.boundingBox.minY < var12)
                        {
                            double var14 = var12 - var5.boundingBox.minY;
                            var5.setPosition(var5.posX, var5.posY + var14, var5.posZ);
                        }
                    }
                }
            }
        }
    }
}
