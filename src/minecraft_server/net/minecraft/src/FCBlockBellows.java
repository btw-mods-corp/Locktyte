package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public class FCBlockBellows extends Block implements FCIMechanicalDevice, FCIBlock, FCIBlockRequireClientNotification, ITextureProvider
{
    private static int m_iBellowsTickRate = 37;
    public static final float m_fBellowsContractedHeight = 0.6875F;
    private final int m_iTopTextureIndex = 68;
    private final int m_iFrontTextureIndex = 69;
    private final int m_iSideTextureIndex = 70;
    private final int m_iBottomTextureIndex = 71;
    private final double m_dBlowItemStrength = 0.2D;
    private final double m_dParticleSpeed = 0.10000000149011612D;

    protected FCBlockBellows(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcBellows");
        this.blockIndexInTexture = 68;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return m_iBellowsTickRate;
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
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
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
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return this.IsBlockMechanicalOn(var1, var2, var3, var4) ? AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 0.6875F), (double)((float)var4 + 1.0F)) : AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (this.IsBlockMechanicalOn(var1, var2, var3, var4))
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6875F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6875F, 1.0F);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        boolean var6 = FCUtilsWorld.IsUpdateAlreadyScheduledForBlock(var1, var2, var3, var4, this.blockID);
        boolean var7;
        boolean var8;

        if (!var6)
        {
            var7 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
            var8 = this.IsBlockMechanicalOn(var1, var2, var3, var4);

            if (var7 != var8)
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                this.SetIsContinuousMechanicalStateChange(var1, var2, var3, var4, true);
            }
        }
        else
        {
            var7 = this.IsContinuousMechanicalStateChange(var1, var2, var3, var4);

            if (var7)
            {
                var8 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
                boolean var9 = this.IsBlockMechanicalOn(var1, var2, var3, var4);

                if (var8 == var9)
                {
                    this.SetIsContinuousMechanicalStateChange(var1, var2, var3, var4, false);
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsBlockMechanicalOn(var1, var2, var3, var4);
        boolean var8 = this.IsContinuousMechanicalStateChange(var1, var2, var3, var4);

        if (var7 != var6)
        {
            if (var8)
            {
                this.SetIsContinuousMechanicalStateChange(var1, var2, var3, var4, false);
                this.SetBlockMechanicalOn(var1, var2, var3, var4, var6);
                var1.markBlockNeedsUpdate(var2, var3, var4);
                var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);

                if (var6)
                {
                    this.Blow(var1, var2, var3, var4);
                }
                else
                {
                    this.LiftCollidingEntities(var1, var2, var3, var4);
                }
            }
            else
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
                this.SetIsContinuousMechanicalStateChange(var1, var2, var3, var4, true);
            }
        }
        else if (var8)
        {
            this.SetIsContinuousMechanicalStateChange(var1, var2, var3, var4, false);
        }
    }

    public void ClientNotificationOfMetadataChange(World var1, int var2, int var3, int var4, int var5, int var6) {}

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
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
        return false;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5))
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            FCUtilsMechPower.DestroyHorizontallyAttachedAxles(var1, var2, var3, var4);
            var1.markBlockNeedsUpdate(var2, var3, var4);
        }
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
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            var1.notifyBlockChange(var2, var3, var4, this.blockID);
            var1.markBlockNeedsUpdate(var2, var3, var4);
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
        int var5 = this.GetFacing(var1, var2, var3, var4);

        for (int var6 = 0; var6 <= 5; ++var6)
        {
            if (var6 != var5 && var6 != 1 && FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, var6))
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
        this.BreakBellows(var1, var2, var3, var4);
    }

    public boolean IsBlockMechanicalOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetIsBlockMechanicalOnFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public void SetBlockMechanicalOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -5;

        if (var5)
        {
            var6 |= 4;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean GetIsBlockMechanicalOnFromMetadata(int var1)
    {
        return (var1 & 4) > 0;
    }

    public boolean IsContinuousMechanicalStateChange(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetIsContinuousMechanicalStateChange(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -9;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadata(var2, var3, var4, var6);
    }

    private void Blow(World var1, int var2, int var3, int var4)
    {
        this.StokeFiresInFront(var1, var2, var3, var4);
        this.BlowLightItemsInFront(var1, var2, var3, var4);
    }

    private void StokeFiresInFront(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        int var6 = FCUtilsMisc.RotateFacingAroundJ(var5, false);
        int var7 = FCUtilsMisc.RotateFacingAroundJ(var5, true);
        FCUtilsBlockPos var8 = new FCUtilsBlockPos(var2, var3, var4);

        for (int var9 = 0; var9 < 3; ++var9)
        {
            var8.AddFacingAsOffset(var5);
            int var10 = var1.getBlockId(var8.i, var8.j, var8.k);

            if (var10 != Block.fire.blockID && var10 != mod_FCBetterThanWolves.fcStokedFire.blockID)
            {
                if (!var1.isAirBlock(var8.i, var8.j, var8.k))
                {
                    break;
                }
            }
            else
            {
                this.StokeFire(var1, var8.i, var8.j, var8.k);
            }

            FCUtilsBlockPos var11 = new FCUtilsBlockPos(var8.i, var8.j, var8.k);
            var11.AddFacingAsOffset(var6);
            var10 = var1.getBlockId(var11.i, var11.j, var11.k);

            if (var10 == Block.fire.blockID || var10 == mod_FCBetterThanWolves.fcStokedFire.blockID)
            {
                this.StokeFire(var1, var11.i, var11.j, var11.k);
            }

            FCUtilsBlockPos var12 = new FCUtilsBlockPos(var8.i, var8.j, var8.k);
            var12.AddFacingAsOffset(var7);
            var10 = var1.getBlockId(var12.i, var12.j, var12.k);

            if (var10 == Block.fire.blockID || var10 == mod_FCBetterThanWolves.fcStokedFire.blockID)
            {
                this.StokeFire(var1, var12.i, var12.j, var12.k);
            }
        }
    }

    private void BlowLightItemsInFront(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var6 = new FCUtilsBlockPos(var2, var3, var4);
        var6.AddFacingAsOffset(var5);
        List var7 = null;
        int var8 = this.ComputeBlowRange(var1, var2, var3, var4);

        if (var8 > 0)
        {
            AxisAlignedBB var9 = this.CreateBlowBoundingBox(var1, var2, var3, var4, var8);

            if (var9 != null)
            {
                var7 = var1.getEntitiesWithinAABB(EntityItem.class, var9);

                if (var7 != null && var7.size() > 0)
                {
                    Vec3 var10 = FCUtilsMisc.ConvertBlockFacingToVector(var5);
                    var10.xCoord *= 0.2D;
                    var10.yCoord *= 0.2D;
                    var10.zCoord *= 0.2D;

                    for (int var11 = 0; var11 < var7.size(); ++var11)
                    {
                        EntityItem var12 = (EntityItem)var7.get(var11);

                        if (!var12.isDead)
                        {
                            ItemStack var13 = var12.item;
                            int var14 = var13.getItem().GetBellowsBlowDistance(var13.getItemDamage());

                            if (var14 > 0 && (var14 >= var8 || this.IsEntityWithinBlowRange(var1, var2, var3, var4, var14, var12)))
                            {
                                var12.motionX += var10.xCoord;
                                var12.motionY += var10.yCoord;
                                var12.motionZ += var10.zCoord;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean IsEntityWithinBlowRange(World var1, int var2, int var3, int var4, int var5, Entity var6)
    {
        AxisAlignedBB var7 = this.CreateBlowBoundingBox(var1, var2, var3, var4, var5);
        return var7.intersectsWith(var6.boundingBox);
    }

    private AxisAlignedBB CreateBlowBoundingBox(World var1, int var2, int var3, int var4, int var5)
    {
        AxisAlignedBB var6 = null;

        if (var5 > 0)
        {
            int var7 = this.GetFacing(var1, var2, var3, var4);
            FCUtilsBlockPos var8 = new FCUtilsBlockPos(var2, var3, var4);
            var8.AddFacingAsOffset(var7);
            var6 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var8.i), (double)((float)var8.j), (double)((float)var8.k), (double)((float)(var8.i + 1)), (double)((float)(var8.j + 1)), (double)((float)(var8.k + 1)));

            if (var5 > 1)
            {
                Vec3 var9 = FCUtilsMisc.ConvertBlockFacingToVector(var7);
                double var10 = (double)(var5 - 1);
                var9.xCoord *= var10;
                var9.yCoord *= var10;
                var9.zCoord *= var10;
                var6 = var6.addCoord(var9.xCoord, var9.yCoord, var9.zCoord);
            }
        }

        return var6;
    }

    private int ComputeBlowRange(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;
        int var6 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);

        for (int var8 = 0; var8 < 3; ++var8)
        {
            var7.AddFacingAsOffset(var6);

            if (!this.CanBlowThroughBlock(var1, var7.i, var7.j, var7.k))
            {
                break;
            }

            ++var5;
        }

        return var5;
    }

    private boolean CanBlowThroughBlock(World var1, int var2, int var3, int var4)
    {
        if (!var1.isAirBlock(var2, var3, var4))
        {
            int var5 = var1.getBlockId(var2, var3, var4);

            if (var5 != Block.fire.blockID && var5 != mod_FCBetterThanWolves.fcStokedFire.blockID && var5 != Block.trapdoor.blockID)
            {
                return false;
            }
        }

        return true;
    }

    private void StokeFire(World var1, int var2, int var3, int var4)
    {
        if (var1.getBlockId(var2, var3 - 1, var4) == mod_FCBetterThanWolves.fcBBQ.blockID)
        {
            if (var1.getBlockId(var2, var3, var4) == mod_FCBetterThanWolves.fcStokedFire.blockID)
            {
                var1.setBlockMetadata(var2, var3, var4, 0);
            }
            else
            {
                var1.setBlockWithNotify(var2, var3, var4, mod_FCBetterThanWolves.fcStokedFire.blockID);
            }

            if (var1.isAirBlock(var2, var3 + 1, var4))
            {
                var1.setBlockWithNotify(var2, var3 + 1, var4, fire.blockID);
            }
        }
        else
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    private void LiftCollidingEntities(World var1, int var2, int var3, int var4)
    {
        List var5 = var1.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 0.6875F), (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)(var3 + 1)), (double)((float)(var4 + 1))));
        float var6 = (float)(var3 + 1);

        if (var5 != null && var5.size() > 0)
        {
            for (int var7 = 0; var7 < var5.size(); ++var7)
            {
                Entity var8 = (Entity)var5.get(var7);

                if (!var8.isDead && (var8.canBePushed() || var8 instanceof EntityItem))
                {
                    double var9 = var8.boundingBox.minY;

                    if (var9 < (double)var6)
                    {
                        double var11 = (double)var6 - var9;
                        var8.setPosition(var8.posX, var8.posY + var11, var8.posZ);
                    }
                }
            }
        }
    }

    public void BreakBellows(World var1, int var2, int var3, int var4)
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

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcTannedLeather.shiftedIndex, 0);
        }

        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}
