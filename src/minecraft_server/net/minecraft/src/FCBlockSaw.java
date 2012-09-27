package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public class FCBlockSaw extends Block implements FCIBlock, FCIMechanicalDevice, FCIBlockSolidTop, ITextureProvider
{
    private static final int iSawTickRate = 10;
    public static final float fSawBaseHeight = 0.75F;
    private final int iSawTopTextureIndex = 56;
    private final int iSawSideTextureIndex = 57;
    private final int iSawBladeTextureIndex = 58;

    protected FCBlockSaw(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcWoodMaterial);
        this.setHardness(2.0F);
        this.setStepSound(soundWoodFootstep);
        this.setBlockName("fcSaw");
        this.blockIndexInTexture = 57;
        this.setRequiresSelfNotify();
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
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
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomSawRenderID;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        float var5 = 0.71875F;
        int var6 = this.GetFacing(var1, var2, var3, var4);

        switch (var6)
        {
            case 0:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 1.0F - var5), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 1:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + var5), (double)((float)var4 + 1.0F));

            case 2:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4 + 1.0F - var5), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 3:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + var5));

            case 4:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 1.0F - var5), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + var5), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
                this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 1:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                break;

            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                break;

            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                break;

            case 4:
                this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (var5 != mod_FCBetterThanWolves.fcAxleBlock.blockID && var5 != mod_FCBetterThanWolves.fcHandCrank.blockID)
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate() + var1.rand.nextInt(6));
        }
        else
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsBlockOn(var1, var2, var3, var4);

        if (var7 != var6)
        {
            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.old_explode", 0.2F, 1.25F);
            this.EmitSawParticles(var1, var2, var3, var4, var5);
            this.SetBlockOn(var1, var2, var3, var4, var6);

            if (var6)
            {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate() + var5.nextInt(6));
            }
        }
        else if (var7)
        {
            int var8 = this.GetFacing(var1, var2, var3, var4);
            FCUtilsBlockPos var9 = new FCUtilsBlockPos(var2, var3, var4);
            var9.AddFacingAsOffset(var8);

            if (!this.AttemptToSawBlock(var1, var9.i, var9.j, var9.k, var5, var8))
            {
                this.BreakSaw(var1, var2, var3, var4);
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote)
        {
            if (this.IsBlockOn(var1, var2, var3, var4) && var5 instanceof EntityLiving)
            {
                int var6 = this.GetFacing(var1, var2, var3, var4);
                float var7 = 0.3125F;
                float var8 = 0.0078125F;
                float var9 = 0.25F;
                AxisAlignedBB var10;

                switch (var6)
                {
                    case 0:
                        var10 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)(0.5F - var7), 0.0D, (double)(0.5F - var8), (double)(0.5F + var7), (double)var9, (double)(0.5F + var8));
                        break;

                    case 1:
                        var10 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)(0.5F - var7), (double)(1.0F - var9), (double)(0.5F - var8), (double)(0.5F + var7), 1.0D, (double)(0.5F + var8));
                        break;

                    case 2:
                        var10 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)(0.5F - var7), (double)(0.5F - var8), 0.0D, (double)(0.5F + var7), (double)(0.5F + var8), (double)var9);
                        break;

                    case 3:
                        var10 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)(0.5F - var7), (double)(0.5F - var8), (double)(1.0F - var9), (double)(0.5F + var7), (double)(0.5F + var8), 1.0D);
                        break;

                    case 4:
                        var10 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(0.0D, (double)(0.5F - var8), (double)(0.5F - var7), (double)var9, (double)(0.5F + var8), (double)(0.5F + var7));
                        break;

                    default:
                        var10 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)(1.0F - var9), (double)(0.5F - var8), (double)(0.5F - var7), 1.0D, (double)(0.5F + var8), (double)(0.5F + var7));
                }

                var10 = var10.getOffsetBoundingBox((double)var2, (double)var3, (double)var4);
                List var11 = null;
                var11 = var1.getEntitiesWithinAABB(EntityLiving.class, var10);

                if (var11 != null && var11.size() > 0)
                {
                    for (int var12 = 0; var12 < var11.size(); ++var12)
                    {
                        EntityLiving var13 = (EntityLiving)var11.get(var12);

                        if (var13.attackEntityFrom(DamageSource.cactus, 4))
                        {
                            var1.playAuxSFX(2223, var2, var3, var4, var6);
                        }
                    }
                }
            }
        }
    }

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 == 0;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetFacingFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.SetFacingInMetadata(var1.getBlockMetadata(var2, var3, var4), var5);
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1 & 7;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        var1 &= -8;
        var1 |= var2;
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 0;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 0 && var5 != 1;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5))
        {
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
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetBlockOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 7;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    void EmitSawParticles(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        float var7 = (float)var2;
        float var8 = (float)var3;
        float var9 = (float)var4;
        float var10 = 0.0F;
        float var11 = 0.0F;

        switch (var6)
        {
            case 0:
                var7 += 0.5F;
                var9 += 0.5F;
                var10 = 1.0F;
                break;

            case 1:
                var7 += 0.5F;
                var9 += 0.5F;
                ++var8;
                var10 = 1.0F;
                break;

            case 2:
                var7 += 0.5F;
                var8 += 0.5F;
                var10 = 1.0F;
                break;

            case 3:
                var7 += 0.5F;
                var8 += 0.5F;
                ++var9;
                var10 = 1.0F;
                break;

            case 4:
                var8 += 0.5F;
                var9 += 0.5F;
                var11 = 1.0F;
                break;

            default:
                var8 += 0.5F;
                var9 += 0.5F;
                ++var7;
                var11 = 1.0F;
        }

        for (int var12 = 0; var12 < 5; ++var12)
        {
            float var13 = var7 + (var5.nextFloat() - 0.5F) * var10;
            float var14 = var8 + var5.nextFloat() * 0.1F;
            float var15 = var9 + (var5.nextFloat() - 0.5F) * var11;
            var1.spawnParticle("smoke", (double)var13, (double)var14, (double)var15, 0.0D, 0.0D, 0.0D);
        }
    }

    boolean AttemptToSawBlock(World var1, int var2, int var3, int var4, Random var5, int var6)
    {
        if (!var1.isAirBlock(var2, var3, var4))
        {
            int var7 = var1.getBlockId(var2, var3, var4);
            int var8 = var1.getBlockMetadata(var2, var3, var4);
            boolean var9 = false;
            Block var10 = Block.blocksList[var7];
            boolean var11 = true;
            int var12;

            if (var7 == Block.wood.blockID)
            {
                var8 &= 3;

                for (var12 = 0; var12 < 4; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.planks.blockID, var8);
                }

                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcSawDust.shiftedIndex, 0);
                }

                var9 = true;
            }
            else if (var7 == mod_FCBetterThanWolves.fcBloodWood.blockID)
            {
                for (var12 = 0; var12 < 4; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.planks.blockID, 3);
                }

                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcSoulDust.shiftedIndex, 0);
                }

                var9 = true;
            }
            else if (var7 == Block.planks.blockID)
            {
                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodSidingItemStubID, var8);
                }

                var9 = true;
            }
            else if (var7 == Block.woodDoubleSlab.blockID)
            {
                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.woodSingleSlab.blockID, var8 & 7);
                }

                var9 = true;
            }
            else if (var7 == Block.woodSingleSlab.blockID)
            {
                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, var8 & 7);
                }

                var9 = true;
            }
            else if (var7 == Block.stoneDoubleSlab.blockID && (var8 & 7) == 2)
            {
                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.woodSingleSlab.blockID, 0);
                }

                var9 = true;
            }
            else if (var7 == Block.stoneSingleSlab.blockID && (var8 & 7) == 2)
            {
                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 0);
                }

                var9 = true;
            }
            else if (var7 == mod_FCBetterThanWolves.fcOmniSlab.blockID)
            {
                if (!((FCBlockOmniSlab)mod_FCBetterThanWolves.fcOmniSlab).IsSlabWood(var1, var2, var3, var4))
                {
                    return false;
                }

                for (var12 = 0; var12 < 2; ++var12)
                {
                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 0);
                }

                var9 = true;
            }
            else
            {
                int var13;

                if (var7 != mod_FCBetterThanWolves.fcBlockWoodSpruceSidingAndCorner.blockID && var7 != mod_FCBetterThanWolves.fcBlockWoodBirchSidingAndCorner.blockID && var7 != mod_FCBetterThanWolves.fcBlockWoodJungleSidingAndCorner.blockID)
                {
                    if (var7 != mod_FCBetterThanWolves.fcMoulding.blockID && var7 != mod_FCBetterThanWolves.fcBlockWoodSpruceMoulding.blockID && var7 != mod_FCBetterThanWolves.fcBlockWoodBirchMoulding.blockID && var7 != mod_FCBetterThanWolves.fcBlockWoodJungleMoulding.blockID)
                    {
                        if (var7 == mod_FCBetterThanWolves.fcCorner.blockID)
                        {
                            if (((FCBlockCorner)mod_FCBetterThanWolves.fcCorner).GetIsStone(var1, var2, var3, var4))
                            {
                                return false;
                            }

                            for (var12 = 0; var12 < 2; ++var12)
                            {
                                FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
                            }

                            var9 = true;
                        }
                        else if (var7 == Block.vine.blockID)
                        {
                            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.vine.blockID, 0);
                            var9 = true;
                        }
                        else if (var7 == Block.fence.blockID)
                        {
                            for (var12 = 0; var12 < 2; ++var12)
                            {
                                FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID, 0);
                            }

                            var9 = true;
                        }
                        else if (var7 == mod_FCBetterThanWolves.fcAestheticOpaque.blockID && var8 == 0)
                        {
                            for (var12 = 0; var12 < 2; ++var12)
                            {
                                FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID, 5);
                            }

                            var9 = true;
                        }
                        else if (var7 == mod_FCBetterThanWolves.fcCompanionCube.blockID)
                        {
                            FCBlockCompanionCube var15 = (FCBlockCompanionCube)((FCBlockCompanionCube)mod_FCBetterThanWolves.fcCompanionCube);

                            if (!var15.GetIsSlab(var1, var2, var3, var4))
                            {
                                if (var6 != 0 && var6 != 1)
                                {
                                    FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcCompanionCube.blockID, 1);
                                    var15.SetIsSlab(var1, var2, var3, var4, true);
                                    var15.SetFacing(var1, var2, var3, var4, 0);
                                    var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
                                    var11 = false;
                                }
                                else
                                {
                                    for (var13 = 0; var13 < 2; ++var13)
                                    {
                                        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcCompanionCube.blockID, 1);
                                    }
                                }

                                FCUtilsBlockPos var14 = new FCUtilsBlockPos(var2, var3, var4);
                                var14.AddFacingAsOffset(FCUtilsMisc.GetOppositeFacing(var6));
                                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "mob.wolf.hurt", 5.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F);
                                var9 = true;
                            }
                            else if (var6 == 0 || var6 == 1)
                            {
                                FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcCompanionCube.blockID, 1);
                                var9 = true;
                            }
                        }
                        else if (var7 != Block.leaves.blockID && var7 != Block.reed.blockID && var7 != Block.crops.blockID && var7 != mod_FCBetterThanWolves.fcHempCrop.blockID)
                        {
                            if (var7 != Block.pistonMoving.blockID && var7 != Block.sapling.blockID && var7 != Block.pumpkinStem.blockID && var7 != Block.melonStem.blockID && (var7 != mod_FCBetterThanWolves.fcAestheticVegetation.blockID || var8 != 2) && var10 != null)
                            {
                                Material var16 = var10.blockMaterial;

                                if (var7 == mod_FCBetterThanWolves.fcAestheticNonOpaque.blockID)
                                {
                                    if (var8 == 4 || var8 == 5 || var8 == 6 || var8 == 7)
                                    {
                                        var16 = Material.wood;
                                    }
                                }
                                else if (var7 == mod_FCBetterThanWolves.fcAestheticOpaque.blockID && var8 == 0)
                                {
                                    var16 = Material.wood;
                                }

                                if (var16 != Material.wood && var16 != Material.cactus && var16 != Material.pumpkin && var16 != Material.leaves && var16 != Material.plants && var16 != Material.vine && var16 != Material.snow && var16 != Material.craftedSnow && var16 != mod_FCBetterThanWolves.fcWoodMaterial)
                                {
                                    if (var16.isSolid())
                                    {
                                        return false;
                                    }
                                }
                                else
                                {
                                    var10.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
                                    var9 = true;
                                }
                            }
                        }
                        else
                        {
                            var10.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
                            var9 = true;
                        }
                    }
                    else
                    {
                        var12 = var10.damageDropped(var8);

                        for (var13 = 0; var13 < 2; ++var13)
                        {
                            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodCornerItemStubID, var12);
                        }

                        var9 = true;
                    }
                }
                else
                {
                    var12 = var10.damageDropped(var8);

                    if ((var8 & 1) == 0)
                    {
                        for (var13 = 0; var13 < 2; ++var13)
                        {
                            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID, var12);
                        }
                    }
                    else
                    {
                        for (var13 = 0; var13 < 2; ++var13)
                        {
                            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
                        }
                    }

                    var9 = true;
                }
            }

            if (var9)
            {
                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.old_explode", 0.2F, 1.25F);
                this.EmitSawParticles(var1, var2, var3, var4, var5);

                if (var11)
                {
                    var1.setBlockWithNotify(var2, var3, var4, 0);
                }
            }
        }

        return true;
    }

    public void BreakSaw(World var1, int var2, int var3, int var4)
    {
        int var5;

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcGear.shiftedIndex, 0);
        }

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Block.planks.blockID, 0);
        }

        for (var5 = 0; var5 < 2; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, Item.ingotIron.shiftedIndex, 0);
        }

        for (var5 = 0; var5 < 1; ++var5)
        {
            FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcBelt.shiftedIndex, 0);
        }

        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
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
            if (var6 != var5 && FCUtilsMechPower.IsBlockPoweredByAxleToSide(var1, var2, var3, var4, var6))
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

    public void Overpower(World var1, int var2, int var3, int var4)
    {
        this.BreakSaw(var1, var2, var3, var4);
    }
}
