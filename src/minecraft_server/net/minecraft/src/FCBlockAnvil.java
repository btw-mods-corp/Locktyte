package net.minecraft.src;

import forge.ITextureProvider;

public class FCBlockAnvil extends BlockContainer implements FCIBlock, ITextureProvider
{
    public static final float m_fAnvilBaseHeight = 0.125F;
    public static final float m_fAnvilBaseWidth = 0.5F;
    public static final float m_fAnvilHalfBaseWidth = 0.25F;
    public static final float m_fAnvilShaftHeight = 0.4375F;
    public static final float m_fAnvilShaftWidth = 0.25F;
    public static final float m_fAnvilHalfShaftWidth = 0.125F;
    public static final float m_fAnvilTopHeight = 0.4375F;
    public static final float m_fAnvilTopWidth = 0.375F;
    public static final float m_fAnvilTopHalfWidth = 0.1875F;

    public FCBlockAnvil(int var1)
    {
        super(var1, Material.iron);
        this.blockIndexInTexture = 79;
        this.setHardness(3.5F);
        this.setStepSound(soundMetalFootstep);
        this.setBlockName("fcAnvil");
        this.setRequiresSelfNotify();
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
        return mod_FCBetterThanWolves.iCustomAnvilRenderID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityAnvil();
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
        else
        {
            var5 = FCUtilsMisc.GetOppositeFacing(var5);
        }

        this.SetFacing(var1, var2, var3, var4, var5);
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

        if (!var1.isRemote && var5 instanceof EntityPlayerMP)
        {
            FCContainerAnvil var12 = new FCContainerAnvil(var5.inventory, var1, var2, var3, var4);
            mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var12, mod_FCBetterThanWolves.fcAnvilContainerID, 0, 0, 0);
        }

        return true;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 2 && var5 != 3 ? AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2), (double)((float)var3), (double)((float)var4 + 0.5F - 0.25F), (double)((float)var2 + 1.0F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.5F + 0.25F)) : AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.5F - 0.25F), (double)((float)var3), (double)((float)var4), (double)((float)var2 + 0.5F + 0.25F), (double)((float)var3 + 1.0F), (double)((float)var4 + 1.0F));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);

        if (var5 != 2 && var5 != 3)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
        }
        else
        {
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        FCTileEntityAnvil var7 = (FCTileEntityAnvil)var1.getBlockTileEntity(var2, var3, var4);

        if (var7 != null)
        {
            var7.EjectMoulds();
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
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
        return true;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
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
        this.RotateAroundJAxis(var1, var2, var3, var4, var5);
        return true;
    }

    public void SetBlockBoundsRotatedAboutJToFacing(float var1, float var2, float var3, float var4, float var5, float var6, int var7)
    {
        float var8;
        float var9;
        float var10;
        float var11;

        if (var7 == 4)
        {
            var8 = 1.0F - var4;
            var9 = 1.0F - var6;
            var10 = 1.0F - var1;
            var11 = 1.0F - var3;
        }
        else if (var7 == 3)
        {
            var8 = var3;
            var9 = var1;
            var10 = var6;
            var11 = var4;
        }
        else if (var7 == 2)
        {
            var8 = 1.0F - var6;
            var9 = 1.0F - var4;
            var10 = 1.0F - var3;
            var11 = 1.0F - var1;
        }
        else
        {
            var8 = var1;
            var9 = var3;
            var10 = var4;
            var11 = var6;
        }

        this.setBlockBounds(var8, var2, var9, var10, var5, var11);
    }
}
