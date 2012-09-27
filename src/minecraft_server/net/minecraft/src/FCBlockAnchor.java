package net.minecraft.src;

import forge.ITextureProvider;

public class FCBlockAnchor extends Block implements ITextureProvider, FCIBlock, FCIBlockSolidTop
{
    public static float fAnchorBaseHeight = 0.375F;
    public final int iAnchorLoopTextureIndex = 39;
    private final int iAnchorBaseTopAndBottomTextureIndex = 40;
    private final int iAnchorBaseSideTextureIndex = 41;
    public final int iAnchorRopeTextureIndex = 32;

    protected FCBlockAnchor(int var1)
    {
        super(var1, Material.rock);
        this.setHardness(2.0F);
        this.setStepSound(soundStoneFootstep);
        this.setBlockName("fcAnchor");
        this.blockIndexInTexture = 41;
        this.setRequiresSelfNotify();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return var1 != var2 && var1 != FCUtilsMisc.GetOppositeFacing(var2) ? 41 : 40;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        switch (var5)
        {
            case 0:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3 + 1.0F - fAnchorBaseHeight), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 1:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + fAnchorBaseHeight), (double)((float)var4 + 1.0F));

            case 2:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4 + 1.0F - fAnchorBaseHeight), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            case 3:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + fAnchorBaseHeight));

            case 4:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 1.0F - fAnchorBaseHeight), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));

            default:
                return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4), (double)((float)var2 + fAnchorBaseHeight), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
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
                this.setBlockBounds(0.0F, 0.35F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 1:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.65F, 1.0F);
                break;

            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.35F, 1.0F, 1.0F, 1.0F);
                break;

            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.65F);
                break;

            case 4:
                this.setBlockBounds(0.35F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.65F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
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
        return mod_FCBetterThanWolves.iCustomAnchorRenderID;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        this.SetFacing(var1, var2, var3, var4, var5);
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
            this.RetractRope(var1, var2, var3, var4, var5);
            return true;
        }
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4);
    }

    public void SetFacing(World var1, int var2, int var3, int var4, int var5)
    {
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
    }

    public int GetFacingFromMetadata(int var1)
    {
        return var1;
    }

    public int SetFacingInMetadata(int var1, int var2)
    {
        return var2;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 0;
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

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 == 0;
    }

    void RetractRope(World var1, int var2, int var3, int var4, EntityPlayer var5)
    {
        for (int var6 = var3 - 1; var6 >= 0; --var6)
        {
            int var7 = var1.getBlockId(var2, var6, var4);

            if (var7 != mod_FCBetterThanWolves.fcRopeBlock.blockID)
            {
                break;
            }

            if (var1.getBlockId(var2, var6 - 1, var4) != mod_FCBetterThanWolves.fcRopeBlock.blockID)
            {
                this.AddRopeToPlayerInventory(var1, var2, var3, var4, var5);
                Block var8 = mod_FCBetterThanWolves.fcRopeBlock;

                if (!var1.isRemote)
                {
                    var1.playAuxSFX(2001, var2, var3, var4, var7);
                    var1.setBlockWithNotify(var2, var6, var4, 0);
                }

                break;
            }
        }
    }

    private void AddRopeToPlayerInventory(World var1, int var2, int var3, int var4, EntityPlayer var5)
    {
        ItemStack var6 = new ItemStack(mod_FCBetterThanWolves.fcRopeItem);

        if (var5.inventory.addItemStackToInventory(var6))
        {
            var1.playSoundAtEntity(var5, "random.pop", 0.2F, ((var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        else
        {
            FCUtilsItem.EjectStackWithRandomOffset(var1, var2, var3, var4, var6);
        }
    }

    public boolean NotifyAnchorBlockOfAttachedPulleyStateChange(FCTileEntityPulley var1, World var2, int var3, int var4, int var5)
    {
        byte var6 = 0;

        if (var1.IsRaising())
        {
            if (var2.getBlockId(var3, var4 + 1, var5) == mod_FCBetterThanWolves.fcRopeBlock.blockID)
            {
                var6 = 1;
            }
        }
        else if (var1.IsLowering() && (var2.isAirBlock(var3, var4 - 1, var5) || var2.getBlockId(var3, var4 - 1, var5) == mod_FCBetterThanWolves.fcPlatform.blockID))
        {
            var6 = -1;
        }

        if (var6 != 0)
        {
            this.ConvertAnchorToEntity(var2, var3, var4, var5, var1, var6);
            return true;
        }
        else
        {
            return false;
        }
    }

    private void ConvertAnchorToEntity(World var1, int var2, int var3, int var4, FCTileEntityPulley var5, int var6)
    {
        FCUtilsBlockPos var7 = new FCUtilsBlockPos(var5.xCoord, var5.yCoord, var5.zCoord);
        FCEntityMovingAnchor var8 = new FCEntityMovingAnchor(var1, (double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), var7, var6);
        var1.spawnEntityInWorld(var8);
        this.ConvertConnectedPlatformsToEntities(var1, var2, var3, var4, var8);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }

    private void ConvertConnectedPlatformsToEntities(World var1, int var2, int var3, int var4, FCEntityMovingAnchor var5)
    {
        int var6 = var3 - 1;
        int var7 = var1.getBlockId(var2, var6, var4);

        if (var7 == mod_FCBetterThanWolves.fcPlatform.blockID)
        {
            ((FCBlockPlatform)mod_FCBetterThanWolves.fcPlatform).CovertToEntitiesFromThisPlatform(var1, var2, var6, var4, var5);
        }
    }
}
