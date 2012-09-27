package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockGearBox extends Block implements FCIMechanicalDevice, FCIBlock, FCIBlockRequireClientNotification, ITextureProvider
{
    public static final int iGearBoxTickRate = 10;
    private static final int m_iGearBoxTurnOnTickRate = 10;
    private static final int m_iGearBoxTurnOffTickRate = 9;
    private final int iGearBoxFrontTextureIndex;
    private final int iGearBoxOutputTextureIndex;

    protected FCBlockGearBox(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcGearBox");
        this.blockIndexInTexture = 38;
        this.iGearBoxFrontTextureIndex = 36;
        this.iGearBoxOutputTextureIndex = 37;
        this.setRequiresSelfNotify();
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
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

        if (mod_FCBetterThanWolves.fcFaceGearBoxAwayFromPlayer)
        {
            var6 = FCUtilsMisc.GetOppositeFacing(var6);
        }

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
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        if (this.IsGearBoxOn(var1, var2, var3, var4))
        {
            this.ValidateOutputs(var1, var2, var3, var4, false, true);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 3 ? this.iGearBoxFrontTextureIndex : this.blockIndexInTexture;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (!mod_FCBetterThanWolves.fcDisableGearBoxPowerDrain)
        {
            boolean var6 = this.IsGearBoxOn(var1, var2, var3, var4);

            if (var6)
            {
                boolean var7 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
                boolean var8 = var1.isBlockGettingPowered(var2, var3, var4) || var1.isBlockGettingPowered(var2, var3 + 1, var4);

                if (var8)
                {
                    var7 = false;
                }

                if (var6 != var7)
                {
                    var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 9);
                    return;
                }
            }
        }

        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 10);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsGearBoxOn(var1, var2, var3, var4);
        boolean var8 = var1.isBlockGettingPowered(var2, var3, var4) || var1.isBlockGettingPowered(var2, var3 + 1, var4);

        if (var8)
        {
            var6 = false;
        }

        if (var7 != var6)
        {
            if (var7)
            {
                this.SetGearBoxOnState(var1, var2, var3, var4, false);
                this.ValidateOutputs(var1, var2, var3, var4, false, false);
            }
            else
            {
                this.SetGearBoxOnState(var1, var2, var3, var4, true);
                this.ValidateOutputs(var1, var2, var3, var4, true, false);
            }
        }
        else
        {
            this.ValidateOutputs(var1, var2, var3, var4, false, false);
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        ItemStack var10 = var5.getCurrentEquippedItem();

        if (var10 != null)
        {
            return false;
        }
        else
        {
            if (!var1.isRemote)
            {
                this.ToggleFacing(var1, var2, var3, var4, false);
                FCUtilsMisc.PlayPlaceSoundForBlock(var1, var2, var3, var4);
            }

            return true;
        }
    }

    public void ClientNotificationOfMetadataChange(World var1, int var2, int var3, int var4, int var5, int var6) {}

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4) & 7;
        return var5;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 8;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1 & 7;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 &= 8;
        var1 |= var2;
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
        int var6 = this.GetFacing(var1, var2, var3, var4);
        int var7 = FCUtilsMisc.RotateFacingAroundJ(var6, var5);

        if (var7 != var6)
        {
            if (this.IsGearBoxOn(var1, var2, var3, var4))
            {
                this.SetGearBoxOnState(var1, var2, var3, var4, false);
                this.ValidateOutputs(var1, var2, var3, var4, false, false);
            }

            this.SetFacing(var1, var2, var3, var4, var7);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            FCUtilsMechPower.DestroyHorizontallyAttachedAxles(var1, var2, var3, var4);
        }
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (this.IsGearBoxOn(var1, var2, var3, var4))
        {
            this.SetGearBoxOnState(var1, var2, var3, var4, false);
            this.ValidateOutputs(var1, var2, var3, var4, false, false);
        }

        int var6 = this.GetFacing(var1, var2, var3, var4);
        var6 = FCUtilsMisc.CycleFacing(var6, var5);
        this.SetFacing(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        var1.notifyBlockChange(var2, var3, var4, this.blockID);
        return true;
    }

    public boolean CanOutputMechanicalPower()
    {
        return true;
    }

    public boolean CanInputMechanicalPower()
    {
        return true;
    }

    public boolean IsInputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, this.GetFacing(var1, var2, var3, var4));
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return this.IsGearBoxOn(var1, var2, var3, var4);
    }

    public void Overpower(World var1, int var2, int var3, int var4)
    {
        if (this.IsGearBoxOn(var1, var2, var3, var4))
        {
            this.BreakGearBox(var1, var2, var3, var4);
        }
    }

    public boolean IsGearBoxOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return (var5 & 8) > 0;
    }

    public void SetGearBoxOnState(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 7;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean GetIsGearBoxOnFromMetadata(int var1)
    {
        return (var1 & 8) > 0;
    }

    void EmitGearBoxParticles(World var1, int var2, int var3, int var4, Random var5)
    {
        for (int var6 = 0; var6 < 5; ++var6)
        {
            float var7 = (float)var2 + var5.nextFloat();
            float var8 = (float)var3 + var5.nextFloat() * 0.5F + 1.0F;
            float var9 = (float)var4 + var5.nextFloat();
            var1.spawnParticle("smoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
        }
    }

    private void ValidateOutputs(World var1, int var2, int var3, int var4, boolean var5, boolean var6)
    {
        int var7 = this.GetFacing(var1, var2, var3, var4);
        boolean var8 = this.IsGearBoxOn(var1, var2, var3, var4) && !var6;

        for (int var9 = 0; var9 <= 5; ++var9)
        {
            if (var9 != var7)
            {
                FCUtilsBlockPos var10 = new FCUtilsBlockPos(var2, var3, var4);
                var10.AddFacingAsOffset(var9);

                if (var1.getBlockId(var10.i, var10.j, var10.k) == mod_FCBetterThanWolves.fcAxleBlock.blockID)
                {
                    FCBlockAxle var11 = (FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock;

                    if (var11.IsAxleOrientedTowardsFacing(var1, var10.i, var10.j, var10.k, var9))
                    {
                        int var12 = var11.GetPowerLevel(var1, var10.i, var10.j, var10.k);

                        if (var12 > 0 && var5)
                        {
                            var11.BreakAxle(var1, var10.i, var10.j, var10.k);
                        }
                        else if (var8)
                        {
                            if (var12 != 3)
                            {
                                if (var12 == 0)
                                {
                                    var11.SetPowerLevel(var1, var10.i, var10.j, var10.k, 3);
                                }
                                else
                                {
                                    var11.BreakAxle(var1, var10.i, var10.j, var10.k);
                                }
                            }
                        }
                        else if (var12 != 0)
                        {
                            var11.SetPowerLevel(var1, var10.i, var10.j, var10.k, 0);
                        }
                    }
                }
            }
        }
    }

    public void BreakGearBox(World var1, int var2, int var3, int var4)
    {
        int var5;

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.planks.blockID, 0);
        }

        for (var5 = 0; var5 < 3; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
        }

        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Item.redstone.shiftedIndex, 0);
        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}
