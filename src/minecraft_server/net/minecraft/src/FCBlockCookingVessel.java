package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public abstract class FCBlockCookingVessel extends BlockContainer implements FCIBlock, ITextureProvider, FCIMechanicalDevice
{
    private static final int m_iTickRate = 1;
    public static final double m_dCollisionBoxHeight = 1.0D;
    protected static final float m_fModelHeight = 1.0F;
    protected static final float m_fModelWidth = 0.875F;
    protected static final float m_fModelHalfWidth = 0.4375F;
    protected static final float m_fModelBandHeight = 0.75F;
    protected static final float m_fModelBandHalfHeight = 0.375F;
    protected int m_iTopTextureID;
    protected int m_iSideTextureID;
    protected int m_iBottomTextureID;

    public FCBlockCookingVessel(int var1, Material var2)
    {
        super(var1, var2);
        this.setRequiresSelfNotify();
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 1;
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
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 0 ? this.m_iBottomTextureID : (var1 == 1 ? this.m_iTopTextureID : this.m_iSideTextureID);
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
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)(var2 + 1), (double)var3 + 1.0D, (double)var4 + 1.0D);
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote)
        {
            List var6 = null;

            if (!this.GetMechanicallyPoweredFlag(var1, var2, var3, var4))
            {
                var6 = var1.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3) + 1.0D, (double)((float)var4), (double)((float)(var2 + 1)), (double)((float)var3) + 1.0D + 0.05000000074505806D, (double)((float)(var4 + 1))));

                if (var6 != null && var6.size() > 0)
                {
                    TileEntity var7 = var1.getBlockTileEntity(var2, var3, var4);

                    if (!(var7 instanceof IInventory))
                    {
                        return;
                    }

                    IInventory var8 = (IInventory)var7;

                    for (int var9 = 0; var9 < var6.size(); ++var9)
                    {
                        EntityItem var10 = (EntityItem)var6.get(var9);

                        if (!var10.isDead && FCUtilsInventory.AddItemStackToInventory(var8, var10.item))
                        {
                            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.pop", 0.25F, ((var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            var10.setDead();
                        }
                    }
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        this.ValidateFireUnderState(var1, var2, var3, var4);
        boolean var6 = this.GetMechanicallyPoweredFlag(var1, var2, var3, var4);
        boolean var7 = false;
        int var8 = 0;

        for (int var9 = 2; var9 <= 5; ++var9)
        {
            if (FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, var9) || FCUtilsMechPower.IsBlockPoweredByHandCrankToSide(var1, var2, var3, var4, var9))
            {
                var7 = true;
                var8 = var9;
                this.BreakPowerSourceThatOpposePoweredFacing(var1, var2, var3, var4, var9);
            }
        }

        if (var6 != var7)
        {
            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.old_explode", 0.2F, 1.25F);
            this.SetMechanicallyPoweredFlag(var1, var2, var3, var4, var7);

            if (!var7)
            {
                this.SetTiltFacing(var1, var2, var3, var4, 0);
            }
            else
            {
                this.SetFacingBasedOnPoweredFromFacing(var1, var2, var3, var4, var8);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        this.ValidateFireUnderState(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
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
            TileEntity var14 = var1.getBlockTileEntity(var2, var3, var4);

            if (var14 instanceof FCTileEntityCookingVessel)
            {
                FCTileEntityCookingVessel var12 = (FCTileEntityCookingVessel)var1.getBlockTileEntity(var2, var3, var4);

                if (var5 instanceof EntityPlayerMP)
                {
                    FCContainerCookingVessel var13 = new FCContainerCookingVessel(var5.inventory, var12);
                    mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var13, this.GetContainerID(), 0, 0, 0);
                }
            }
        }

        return true;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = 1;

        if (this.GetMechanicallyPoweredFlag(var1, var2, var3, var4))
        {
            var5 = this.GetTiltFacing(var1, var2, var3, var4);
        }

        return var5;
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

        return false;
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void Overpower(World var1, int var2, int var3, int var4) {}

    protected abstract void ValidateFireUnderState(World var1, int var2, int var3, int var4);

    protected abstract int GetContainerID();

    public int GetTiltFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 3) + 2;
    }

    public void SetTiltFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var5 - 2;

        if (var6 < 0)
        {
            var6 = 0;
        }

        int var7 = var1.getBlockMetadata(var2, var3, var4) & -4;
        var7 |= var6 & 3;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
    }

    public boolean GetMechanicallyPoweredFlag(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 4) > 0;
    }

    private void SetMechanicallyPoweredFlag(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -5;

        if (var5)
        {
            var6 |= 4;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    private void SetFacingBasedOnPoweredFromFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = FCUtilsMisc.RotateFacingAroundJ(var5, false);
        this.SetTiltFacing(var1, var2, var3, var4, var6);
    }

    private void BreakPowerSourceThatOpposePoweredFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = FCUtilsMisc.GetOppositeFacing(var5);

        for (int var7 = 2; var7 <= 5; ++var7)
        {
            if (var7 != var5)
            {
                boolean var8 = false;

                if (var7 == var6)
                {
                    if (FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, var7))
                    {
                        var8 = true;
                    }
                }
                else if (FCUtilsMechPower.DoesBlockHaveFacingAxleToSide(var1, var2, var3, var4, var7))
                {
                    var8 = true;
                }

                FCUtilsBlockPos var9;

                if (var8)
                {
                    var9 = new FCUtilsBlockPos(var2, var3, var4);
                    var9.AddFacingAsOffset(var7);
                    ((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock).BreakAxle(var1, var9.i, var9.j, var9.k);
                }

                if (FCUtilsMechPower.IsBlockPoweredByHandCrankToSide(var1, var2, var3, var4, var7))
                {
                    var9 = new FCUtilsBlockPos(var2, var3, var4);
                    var9.AddFacingAsOffset(var7);
                    ((FCBlockHandCrank)mod_FCBetterThanWolves.fcHandCrank).BreakCrankWithDrop(var1, var9.i, var9.j, var9.k);
                }
            }
        }
    }
}
