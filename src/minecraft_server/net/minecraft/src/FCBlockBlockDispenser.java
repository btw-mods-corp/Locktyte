package net.minecraft.src;

import forge.ITextureProvider;
import java.util.List;
import java.util.Random;

public class FCBlockBlockDispenser extends BlockContainer implements FCIBlock, ITextureProvider
{
    public final int blockDispenserTextureIDFront;
    public final int blockDispenserTextureIDSide;
    public final int blockDispenserTextureIDTop;
    public final int blockDispenserTextureIDBottom;
    private final int iBlockDispenserTickRate = 4;

    protected FCBlockBlockDispenser(int var1)
    {
        super(var1, Material.rock);
        this.blockIndexInTexture = 6;
        this.blockDispenserTextureIDTop = 6;
        this.blockDispenserTextureIDFront = 7;
        this.blockDispenserTextureIDSide = 8;
        this.blockDispenserTextureIDBottom = 9;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 4;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return mod_FCBetterThanWolves.fcBlockDispenser.blockID;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        this.SetFacing(var1, var2, var3, var4, FCUtilsMisc.GetOppositeFacing(var5));
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacing(var5);
        this.SetFacing(var1, var2, var3, var4, var6);
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
            FCTileEntityBlockDispenser var13 = (FCTileEntityBlockDispenser)var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof EntityPlayerMP)
            {
                FCContainerBlockDispenser var12 = new FCContainerBlockDispenser(var5.inventory, var13);
                mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var12, mod_FCBetterThanWolves.fcBlockDispenserContainerID, 0, 0, 0);
            }
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityBlockDispenser();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (this.IsRedstoneOn(var1, var2, var3, var4) != this.IsReceivingRedstonePower(var1, var2, var3, var4))
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
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
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        this.ValidateBlockDispenser(var1, var2, var3, var4);
        boolean var6 = this.IsReceivingRedstonePower(var1, var2, var3, var4);

        if (var6)
        {
            if (!this.IsRedstoneOn(var1, var2, var3, var4))
            {
                this.SetRedstoneOn(var1, var2, var3, var4, true);
                this.DispenseBlockOrItem(var1, var2, var3, var4, var1.rand);
            }
        }
        else if (this.IsRedstoneOn(var1, var2, var3, var4))
        {
            this.SetRedstoneOn(var1, var2, var3, var4, false);
            this.ConsumeFacingBlock(var1, var2, var3, var4);
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) & -9;
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 8;
        var6 |= var5;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1 & -9;
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
        FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5);
        var1.markBlockNeedsUpdate(var2, var3, var4);
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

    public boolean IsRedstoneOn(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return (var5 & 8) > 0;
    }

    private void SetRedstoneOn(World var1, int var2, int var3, int var4, boolean var5)
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

    private boolean IsReceivingRedstonePower(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockGettingPowered(var2, var3, var4) || var1.isBlockGettingPowered(var2, var3 + 1, var4);
    }

    private boolean IsBlockConsumable(Block var1)
    {
        return var1 != Block.bedrock && var1 != Block.waterMoving && var1 != Block.waterStill && var1 != Block.lavaMoving && var1 != Block.lavaStill && var1 != mod_FCBetterThanWolves.fcCement && var1 != Block.fire && var1 != Block.mobSpawner && var1 != Block.portal && var1 != Block.pistonExtension && var1 != Block.pistonMoving && var1 != Block.endPortal && var1 != Block.endPortalFrame;
    }

    private boolean AddBlockToInventory(World var1, int var2, int var3, int var4, Block var5, int var6)
    {
        this.ValidateBlockDispenser(var1, var2, var3, var4);
        FCTileEntityBlockDispenser var7 = (FCTileEntityBlockDispenser)var1.getBlockTileEntity(var2, var3, var4);
        int var8 = var5.damageDropped(var6);
        boolean var9 = false;
        int var10;

        if (var5.blockID == Block.cake.blockID)
        {
            if ((var6 & -9) != 0)
            {
                return false;
            }

            var10 = Item.cake.shiftedIndex;
        }
        else if (var5.blockID != Block.vine.blockID && var5.blockID != Block.melon.blockID && var5.blockID != Block.blockSnow.blockID && var5.blockID != Block.glowStone.blockID && var5.blockID != Block.blockClay.blockID && var5.blockID != Block.gravel.blockID && var5.blockID != Block.stone.blockID && var5.blockID != Block.grass.blockID && var5.blockID != Block.bookShelf.blockID && var5.blockID != Block.oreCoal.blockID && var5.blockID != Block.oreLapis.blockID && var5.blockID != Block.oreDiamond.blockID && var5.blockID != Block.oreRedstone.blockID && var5.blockID != Block.mushroomCapBrown.blockID && var5.blockID != Block.mushroomCapRed.blockID && var5.blockID != Block.mycelium.blockID && var5.blockID != Block.thinGlass.blockID && var5.blockID != Block.ice.blockID && var5.blockID != Block.enderChest.blockID && var5.blockID != Block.oreEmerald.blockID)
        {
            if (var5.blockID == Block.leaves.blockID)
            {
                var10 = var5.blockID;
                var8 = var6 & 3;
            }
            else if (var5.blockID == Block.oreRedstoneGlowing.blockID)
            {
                var10 = Block.oreRedstone.blockID;
            }
            else if (var5.blockID == Block.tallGrass.blockID)
            {
                var10 = var5.blockID;
                var8 = var6;
            }
            else if (var5.blockID == Block.cocoaPlant.blockID)
            {
                var10 = Item.dyePowder.shiftedIndex;
                var8 = 3;
            }
            else if (var5.blockID == mod_FCBetterThanWolves.fcAestheticVegetation.blockID && var6 == 3)
            {
                var10 = var5.blockID;
                var8 = 3;
            }
            else if (var5.blockID == mod_FCBetterThanWolves.fcAestheticOpaque.blockID && var6 == 7)
            {
                var10 = var5.blockID;
                var8 = 7;
            }
            else if (var5.blockID == mod_FCBetterThanWolves.fcLeaves.blockID)
            {
                var10 = var5.blockID;
                var8 = var6 & 3;
            }
            else
            {
                var10 = var5.idDropped(var6, var1.rand, 0);
            }
        }
        else
        {
            var10 = var5.blockID;
            var8 = 0;
        }

        return var10 > 0 ? FCUtilsInventory.AddSingleItemToInventory(var7, var10, var8) : false;
    }

    private boolean ConsumeEntityAtTargetLoc(World var1, int var2, int var3, int var4, int var5, int var6, int var7)
    {
        this.ValidateBlockDispenser(var1, var2, var3, var4);
        List var8 = null;
        var8 = var1.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var5, (double)var6, (double)var7, (double)(var5 + 1), (double)(var6 + 1), (double)(var7 + 1)));

        if (var8 != null && var8.size() > 0)
        {
            FCTileEntityBlockDispenser var9 = (FCTileEntityBlockDispenser)var1.getBlockTileEntity(var2, var3, var4);

            for (int var10 = 0; var10 < var8.size(); ++var10)
            {
                Entity var11 = (Entity)var8.get(var10);

                if (!var11.isDead)
                {
                    int var13;

                    if (var11 instanceof EntityMinecart)
                    {
                        EntityMinecart var16 = (EntityMinecart)var11;
                        var13 = var16.minecartType;

                        if (var16.riddenByEntity != null)
                        {
                            var16.riddenByEntity.mountEntity(var16);
                        }

                        var16.setDead();

                        switch (var13)
                        {
                            case 0:
                                FCUtilsInventory.AddSingleItemToInventory(var9, Item.minecartEmpty.shiftedIndex, 0);
                                break;

                            case 1:
                                FCUtilsInventory.AddSingleItemToInventory(var9, Item.minecartCrate.shiftedIndex, 0);
                                break;

                            default:
                                FCUtilsInventory.AddSingleItemToInventory(var9, Item.minecartPowered.shiftedIndex, 0);
                        }

                        var1.playAuxSFX(1001, var2, var3, var4, 0);
                        return true;
                    }

                    if (var11 instanceof EntityBoat)
                    {
                        var11.setDead();
                        FCUtilsInventory.AddSingleItemToInventory(var9, Item.boat.shiftedIndex, 0);
                        var1.playAuxSFX(1001, var2, var3, var4, 0);
                        return true;
                    }

                    if (var11 instanceof EntityWolf)
                    {
                        EntityWolf var15 = (EntityWolf)var11;
                        var1.playAuxSFX(2239, var2, var3, var4, 0);
                        var11.setDead();
                        FCUtilsInventory.AddSingleItemToInventory(var9, mod_FCBetterThanWolves.fcCompanionCube.blockID, 0);

                        for (var13 = 0; var13 < 2; ++var13)
                        {
                            this.SpitOutItem(var1, var2, var3, var4, new ItemStack(Item.silk), var1.rand);
                        }

                        return true;
                    }

                    if (var11 instanceof EntityChicken)
                    {
                        EntityChicken var14 = (EntityChicken)var11;
                        var1.playAuxSFX(2240, var2, var3, var4, 0);
                        var11.setDead();
                        FCUtilsInventory.AddSingleItemToInventory(var9, Item.egg.shiftedIndex, 0);
                        this.SpitOutItem(var1, var2, var3, var4, new ItemStack(Item.feather), var1.rand);
                        return true;
                    }

                    if (var11 instanceof EntitySheep)
                    {
                        EntitySheep var12 = (EntitySheep)var11;

                        if (!var12.getSheared() && !var12.isChild())
                        {
                            var12.setSheared(true);
                            FCUtilsInventory.AddSingleItemToInventory(var9, Block.cloth.blockID, var12.getFleeceColor());
                            var11.attackEntityFrom(DamageSource.generic, 0);

                            for (var13 = 0; var13 < 2; ++var13)
                            {
                                this.SpitOutItem(var1, var2, var3, var4, new ItemStack(Item.silk), var1.rand);
                            }

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private void ConsumeFacingBlock(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var6 = new FCUtilsBlockPos(var2, var3, var4);
        var6.AddFacingAsOffset(var5);

        if (!this.ConsumeEntityAtTargetLoc(var1, var2, var3, var4, var6.i, var6.j, var6.k) && !var1.isAirBlock(var6.i, var6.j, var6.k))
        {
            int var7 = var1.getBlockId(var6.i, var6.j, var6.k);
            Block var8 = Block.blocksList[var7];

            if (var8 != null && this.IsBlockConsumable(var8))
            {
                int var9 = var1.getBlockMetadata(var6.i, var6.j, var6.k);

                if (var7 == this.blockID)
                {
                    FCTileEntityBlockDispenser var10 = (FCTileEntityBlockDispenser)var1.getBlockTileEntity(var6.i, var6.j, var6.k);
                    FCUtilsInventory.ClearInventoryContents(var10);
                }

                if (this.AddBlockToInventory(var1, var2, var3, var4, var8, var9))
                {
                    var1.playAuxSFX(2001, var6.i, var6.j, var6.k, var7 + (var9 << 12));
                    var1.setBlockWithNotify(var6.i, var6.j, var6.k, 0);
                }
            }
        }
    }

    private void SpitOutItem(World var1, int var2, int var3, int var4, ItemStack var5, Random var6)
    {
        int var7 = this.GetFacing(var1, var2, var3, var4);
        float var8 = 0.0F;
        float var9 = 0.0F;
        float var10 = 0.0F;

        switch (var7)
        {
            case 0:
                var8 = -1.0F;
                break;

            case 1:
                var8 = 1.0F;
                break;

            case 2:
                var10 = -1.0F;
                break;

            case 3:
                var10 = 1.0F;
                break;

            case 4:
                var9 = -1.0F;
                break;

            default:
                var9 = 1.0F;
        }

        double var11 = (double)var2 + (double)var9 * 0.5D + 0.5D;
        double var13 = (double)var3 + (double)var8 + 0.5D;
        double var15 = (double)var4 + (double)var10 * 0.5D + 0.5D;

        if (var8 < 0.1F && var8 > -0.1F)
        {
            var8 = 0.1F;
        }

        EntityItem var17 = new EntityItem(var1, var11, var13 - 0.3D, var15, var5);
        double var18 = var6.nextDouble() * 0.1D + 0.2D;
        var17.motionX = (double)var9 * var18;
        var17.motionY = (double)var8 * var18 + 0.2000000029802322D;
        var17.motionZ = (double)var10 * var18;
        var17.motionX += var6.nextGaussian() * 0.007499999832361937D * 6.0D;
        var17.motionY += var6.nextGaussian() * 0.007499999832361937D * 6.0D;
        var17.motionZ += var6.nextGaussian() * 0.007499999832361937D * 6.0D;
        var1.spawnEntityInWorld(var17);
    }

    private void DispenseBlockOrItem(World var1, int var2, int var3, int var4, Random var5)
    {
        this.ValidateBlockDispenser(var1, var2, var3, var4);
        int var6 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4);
        var7.AddFacingAsOffset(var6);
        int var8 = var1.getBlockId(var7.i, var7.j, var7.k);
        Block var9 = Block.blocksList[var8];
        boolean var10 = false;
        boolean var11 = false;
        boolean var12 = false;

        if (var9 == null)
        {
            var10 = true;
        }
        else if (FCUtilsWorld.IsReplaceableBlock(var1, var7.i, var7.j, var7.k) || !var9.blockMaterial.isSolid() && var9.blockMaterial != Material.vine)
        {
            var10 = true;
        }

        if (var10)
        {
            float var13 = 0.0F;
            float var14 = 0.0F;
            float var15 = 0.0F;

            if (var6 == 0)
            {
                var13 = -1.0F;
            }
            else if (var6 == 1)
            {
                var13 = 1.0F;
            }
            else if (var6 == 3)
            {
                var15 = 1.0F;
            }
            else if (var6 == 2)
            {
                var15 = -1.0F;
            }
            else if (var6 == 5)
            {
                var14 = 1.0F;
            }
            else
            {
                var14 = -1.0F;
            }

            FCTileEntityBlockDispenser var16 = (FCTileEntityBlockDispenser)var1.getBlockTileEntity(var2, var3, var4);
            ItemStack var17 = var16.GetNextStackFromInventory();
            double var18 = (double)var2 + (double)var14 * 0.6D + 0.5D;
            double var20 = (double)var3 + (double)var13 * 0.6D + 0.5D;
            double var22 = (double)var4 + (double)var15 * 0.6D + 0.5D;

            if (var17 != null)
            {
                if (var13 < 0.1F && var13 > -0.1F)
                {
                    var13 = 0.1F;
                }

                if (var17.itemID == Item.arrow.shiftedIndex)
                {
                    EntityArrow var24 = new EntityArrow(var1, var18, var20, var22);
                    var24.setArrowHeading((double)var14, (double)var13, (double)var15, 1.1F, 6.0F);
                    var24.canBePickedUp = 1;
                    var1.spawnEntityInWorld(var24);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);
                    var11 = true;
                }
                else if (var17.itemID == mod_FCBetterThanWolves.fcItemRottenArrow.shiftedIndex)
                {
                    FCEntityRottenArrow var33 = new FCEntityRottenArrow(var1, var18, var20, var22);
                    var33.setArrowHeading((double)var14, (double)var13, (double)var15, 1.1F, 6.0F);
                    var33.canBePickedUp = 2;
                    var1.spawnEntityInWorld(var33);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);
                    var11 = true;
                }
                else if (var17.itemID == mod_FCBetterThanWolves.fcBroadheadArrow.shiftedIndex)
                {
                    FCEntityBroadheadArrow var34 = new FCEntityBroadheadArrow(var1, var18, var20, var22);
                    var34.setArrowHeading((double)var14, (double)var13, (double)var15, 1.1F, 6.0F);
                    var34.canBePickedUp = 1;
                    var1.spawnEntityInWorld(var34);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);
                    var11 = true;
                }
                else if (var17.itemID == Item.egg.shiftedIndex)
                {
                    EntityEgg var29 = new EntityEgg(var1, var18, var20, var22);
                    var29.setThrowableHeading((double)var14, (double)var13, (double)var15, 1.1F, 6.0F);
                    var1.spawnEntityInWorld(var29);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);
                    var11 = true;
                }
                else if (var17.itemID == Item.potion.shiftedIndex && ItemPotion.isSplash(var17.getItemDamage()))
                {
                    EntityPotion var38 = new EntityPotion(var1, var18, var20, var22, var17.getItemDamage());
                    var38.setThrowableHeading((double)var14, (double)var13, (double)var15, 1.375F, 3.0F);
                    var1.spawnEntityInWorld(var38);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);
                    var11 = true;
                }
                else if (var17.itemID == mod_FCBetterThanWolves.fcSoulUrn.shiftedIndex)
                {
                    FCEntityUrn var30 = new FCEntityUrn(var1, var18, var20, var22, var17.itemID);
                    var30.SetUrnHeading((double)var14, (double)var13, (double)var15, 1.1F, 6.0F);
                    var1.spawnEntityInWorld(var30);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);
                    var11 = true;
                }
                else if (var17.itemID == mod_FCBetterThanWolves.fcDynamite.shiftedIndex)
                {
                    FCEntityDynamite var31 = new FCEntityDynamite(var1, var18, var20, var22, var17.itemID);
                    var31.SetDynamiteHeading((double)var14, (double)var13, (double)var15, 1.1F, 6.0F);
                    var1.spawnEntityInWorld(var31);
                    var1.playAuxSFX(1002, var2, var3, var4, 0);

                    if (var8 == Block.fire.blockID || var8 == mod_FCBetterThanWolves.fcStokedFire.blockID)
                    {
                        var31.m_iFuse = 100;
                        var1.playAuxSFX(2237, var2, var3, var4, 0);
                    }

                    var11 = true;
                }
                else
                {
                    EntityMinecart var32;

                    if (var17.itemID == Item.minecartEmpty.shiftedIndex)
                    {
                        var32 = new EntityMinecart(var1, var18 + (double)var14 * 0.75D, var20 - 0.5D, var22 + (double)var15 * 0.75D, 0);
                        var1.spawnEntityInWorld(var32);
                        var1.playAuxSFX(1000, var2, var3, var4, 0);
                        var11 = true;
                    }
                    else if (var17.itemID == Item.minecartCrate.shiftedIndex)
                    {
                        var32 = new EntityMinecart(var1, var18 + (double)var14 * 0.75D, var20 - 0.5D, var22 + (double)var15 * 0.75D, 1);
                        var1.spawnEntityInWorld(var32);
                        var1.playAuxSFX(1000, var2, var3, var4, 0);
                        var11 = true;
                    }
                    else if (var17.itemID == Item.minecartPowered.shiftedIndex)
                    {
                        var32 = new EntityMinecart(var1, var18 + (double)var14 * 0.75D, var20 - 0.5D, var22 + (double)var15 * 0.75D, 2);
                        var1.spawnEntityInWorld(var32);
                        var1.playAuxSFX(1000, var2, var3, var4, 0);
                        var11 = true;
                    }
                    else if (var17.itemID == Item.boat.shiftedIndex)
                    {
                        EntityBoat var36 = new EntityBoat(var1, var18 + (double)var14, var20 - 0.5D, var22 + (double)var15);
                        var1.spawnEntityInWorld(var36);
                        var1.playAuxSFX(1000, var2, var3, var4, 0);
                        var11 = true;
                    }
                    else if (var17.getItem() instanceof ItemSeeds)
                    {
                        ++var17.stackSize;

                        if (!var17.getItem().onItemUse(var17, (EntityPlayer)null, var1, var7.i, var7.j - 1, var7.k, 1, 0.0F, 0.0F, 0.0F))
                        {
                            FCUtilsInventory.AddSingleItemToInventory(var16, var17.itemID, 0);
                        }
                        else
                        {
                            var1.playAuxSFX(2236, var2, var3, var4, Block.crops.blockID);
                            var11 = true;
                        }
                    }
                    else if (var17.itemID == Item.dyePowder.shiftedIndex && var17.getItemDamage() == 3)
                    {
                        FCUtilsBlockPos var37 = new FCUtilsBlockPos(var7.i, var7.j, var7.k);
                        var37.AddFacingAsOffset(var6);
                        ++var17.stackSize;

                        if (!var17.getItem().onItemUse(var17, (EntityPlayer)null, var1, var37.i, var37.j, var37.k, FCUtilsMisc.GetOppositeFacing(var6), 0.0F, 0.0F, 0.0F))
                        {
                            FCUtilsInventory.AddSingleItemToInventory(var16, var17.itemID, 3);
                        }
                        else
                        {
                            var1.playAuxSFX(2236, var2, var3, var4, Block.cocoaPlant.blockID);
                            var11 = true;
                        }
                    }
                    else if (var17.itemID == mod_FCBetterThanWolves.fcMiningCharge.blockID)
                    {
                        FCBlockMiningCharge.CreatePrimedEntity(var1, var7.i, var7.j, var7.k, var6);
                        var1.playAuxSFX(2236, var2, var3, var4, mod_FCBetterThanWolves.fcMiningCharge.blockID);
                        var11 = true;
                    }
                    else if (var17.getItem() instanceof FCIItemUseableByBlockDispenser)
                    {
                        if (((FCIItemUseableByBlockDispenser)((FCIItemUseableByBlockDispenser)var17.getItem())).OnItemUsedByBlockDispenser(var1, var7.i, var7.j, var7.k, var6))
                        {
                            var11 = true;
                        }
                        else
                        {
                            FCUtilsInventory.AddSingleItemToInventory(var16, var17.itemID, var17.getItemDamage());
                        }
                    }
                    else
                    {
                        Block var35 = null;
                        byte var25 = -1;
                        int var26;

                        if (var17.itemID < 256)
                        {
                            if (var17.itemID == mod_FCBetterThanWolves.fcAestheticVegetation.blockID && var17.getItemDamage() == 2)
                            {
                                var26 = var1.getBlockId(var7.i, var7.j - 1, var7.k);

                                if (var26 == Block.slowSand.blockID || var26 == mod_FCBetterThanWolves.fcPlanter.blockID && ((FCBlockPlanter)mod_FCBetterThanWolves.fcPlanter).GetPlanterType(var1, var7.i, var7.j - 1, var7.k) == 8)
                                {
                                    var35 = Block.blocksList[var17.itemID];
                                }

                                var12 = true;
                            }
                            else if (Item.itemsList[var17.itemID] instanceof FCItemBlockCustom)
                            {
                                var26 = ((FCItemBlockCustom)((FCItemBlockCustom)Item.itemsList[var17.itemID])).GetBlockIDToPlace(var17.getItemDamage());
                                var35 = Block.blocksList[var26];
                            }
                            else
                            {
                                var35 = Block.blocksList[var17.itemID];
                            }
                        }
                        else if (var17.itemID == Item.reed.shiftedIndex)
                        {
                            var35 = Block.reed;
                        }
                        else if (var17.itemID == Item.cake.shiftedIndex)
                        {
                            var35 = Block.cake;
                        }
                        else if (var17.itemID == Item.redstoneRepeater.shiftedIndex)
                        {
                            var35 = Block.redstoneRepeaterIdle;
                        }
                        else if (var17.itemID == Item.redstone.shiftedIndex)
                        {
                            var35 = Block.redstoneWire;
                        }
                        else if (var17.itemID == Item.clay.shiftedIndex)
                        {
                            var26 = FCUtilsInventory.CountItemsInInventory(var16, var17.itemID, -1);

                            if (var26 >= 3)
                            {
                                FCUtilsInventory.ConsumeItemsInInventory(var16, var17.itemID, -1, 3);
                                var35 = Block.blockClay;
                            }
                            else
                            {
                                var12 = true;
                            }
                        }
                        else if (var17.itemID == Item.snowball.shiftedIndex)
                        {
                            var26 = FCUtilsInventory.CountItemsInInventory(var16, var17.itemID, -1);

                            if (var26 >= 3)
                            {
                                FCUtilsInventory.ConsumeItemsInInventory(var16, var17.itemID, -1, 3);
                                var35 = Block.blockSnow;
                            }
                            else
                            {
                                var12 = true;
                            }
                        }
                        else if (var17.itemID == mod_FCBetterThanWolves.fcUrn.shiftedIndex)
                        {
                            var35 = mod_FCBetterThanWolves.fcAestheticNonOpaque;
                            var25 = 0;
                        }
                        else if (var17.itemID == mod_FCBetterThanWolves.fcGrate.shiftedIndex)
                        {
                            var35 = mod_FCBetterThanWolves.fcAestheticNonOpaque;
                            var25 = 6;
                        }
                        else if (var17.itemID == mod_FCBetterThanWolves.fcWicker.shiftedIndex)
                        {
                            var35 = mod_FCBetterThanWolves.fcAestheticNonOpaque;
                            var25 = 7;
                        }
                        else if (var17.itemID == mod_FCBetterThanWolves.fcRollersItem.shiftedIndex)
                        {
                            var35 = mod_FCBetterThanWolves.fcAestheticNonOpaque;
                            var25 = 8;
                        }

                        if (var35 != null)
                        {
                            var26 = var35.blockID;
                            int var27 = FCUtilsMisc.GetOppositeFacing(var6);

                            if (var26 == Block.lever.blockID && !var1.canPlaceEntityOnSide(var26, var7.i, var7.j, var7.k, true, var27, (Entity)null))
                            {
                                var27 = 1;
                            }

                            if (var1.canPlaceEntityOnSide(var26, var7.i, var7.j, var7.k, true, var27, (Entity)null))
                            {
                                if (var26 != Block.pistonBase.blockID && var26 != Block.pistonStickyBase.blockID)
                                {
                                    if (var26 == Block.redstoneRepeaterIdle.blockID)
                                    {
                                        if (var27 == 4)
                                        {
                                            var27 = 1;
                                        }
                                        else if (var27 == 2)
                                        {
                                            var27 = 2;
                                        }
                                        else if (var27 == 5)
                                        {
                                            var27 = 3;
                                        }
                                        else
                                        {
                                            var27 = 0;
                                        }

                                        var1.setBlockAndMetadataWithNotify(var7.i, var7.j, var7.k, var26, var27);
                                    }
                                    else if (var25 >= 0)
                                    {
                                        var1.setBlockAndMetadataWithNotify(var7.i, var7.j, var7.k, var26, var25);
                                    }
                                    else
                                    {
                                        var1.setBlockAndMetadataWithNotify(var7.i, var7.j, var7.k, var26, var17.getItem().getMetadata(var17.getItemDamage()));
                                    }
                                }
                                else
                                {
                                    var1.setBlockAndMetadataWithNotify(var7.i, var7.j, var7.k, var26, var6);
                                }

                                var35.updateBlockMetadata(var1, var7.i, var7.j, var7.k, var27, 0.5F, 0.25F, 0.5F);

                                if (var26 == mod_FCBetterThanWolves.fcLens.blockID)
                                {
                                    ((FCBlockLens)((FCBlockLens)mod_FCBetterThanWolves.fcLens)).SetupBeam(var1, var7.i, var7.j, var7.k);
                                }

                                var1.playAuxSFX(2236, var2, var3, var4, var26);
                                var11 = true;
                            }
                            else if (var17.itemID != Item.clay.shiftedIndex && var17.itemID != Item.snowball.shiftedIndex)
                            {
                                FCUtilsInventory.AddSingleItemToInventory(var16, var17.itemID, var17.getItemDamage());
                            }
                            else
                            {
                                ItemStack var28 = new ItemStack(var17.itemID, 4, -1);
                                FCUtilsInventory.AddItemStackToInventory(var16, var28);
                            }
                        }
                        else if (!var12)
                        {
                            this.SpitOutItem(var1, var2, var3, var4, var17, var5);
                            var1.playAuxSFX(1000, var2, var3, var4, 0);
                            var11 = true;
                        }
                        else
                        {
                            FCUtilsInventory.AddSingleItemToInventory(var16, var17.itemID, var17.getItemDamage());
                        }
                    }
                }
            }

            if (var11)
            {
                var1.playAuxSFX(2241, var2, var3, var4, var6);
            }
        }

        if (!var11)
        {
            var1.playAuxSFX(2238, var2, var3, var4, 0);
        }
    }

    private boolean ValidateBlockDispenser(World var1, int var2, int var3, int var4)
    {
        TileEntity var5 = var1.getBlockTileEntity(var2, var3, var4);

        if (var5 instanceof FCTileEntityBlockDispenser)
        {
            return true;
        }
        else
        {
            FCTileEntityBlockDispenser var6 = new FCTileEntityBlockDispenser();

            if (var5 instanceof TileEntityDispenser)
            {
                TileEntityDispenser var7 = (TileEntityDispenser)var5;
                int var8 = var7.getSizeInventory();
                int var9 = var6.getSizeInventory();

                for (int var10 = 0; var10 < var8 && var10 < var9; ++var10)
                {
                    ItemStack var11 = var7.getStackInSlot(var10);

                    if (var11 != null)
                    {
                        var6.setInventorySlotContents(var10, var11.copy());
                    }
                }
            }

            var1.setBlockTileEntity(var2, var3, var4, var6);
            return false;
        }
    }
}
