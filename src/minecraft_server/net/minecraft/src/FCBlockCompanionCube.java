package net.minecraft.src;

import forge.ITextureProvider;

public class FCBlockCompanionCube extends Block implements FCIBlock, ITextureProvider, FCIBlockSolidTop
{
    public static final int m_iNumSubtypes = 16;
    public final int m_iCubeTextureIDFront;
    public final int m_iCubeGutsTextureID;

    public FCBlockCompanionCube(int var1)
    {
        super(var1, Material.cloth);
        this.blockIndexInTexture = 20;
        this.m_iCubeTextureIDFront = 21;
        this.m_iCubeGutsTextureID = 22;
        this.setBlockName("fcCompanionCube");
        this.setRequiresSelfNotify();
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        if (!this.GetIsSlab(var1, var2, var3, var4))
        {
            this.SetFacing(var1, var2, var3, var4, FCUtilsMisc.GetOppositeFacing(var5));
        }
        else if (var5 == 0 || var5 != 1 && (double)var7 > 0.5D)
        {
            this.SetFacing(var1, var2, var3, var4, 1);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return (var1 & 8) > 0 ? 1 : 0;
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
        return mod_FCBetterThanWolves.iCustomCompanionCubeRenderID;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return this.GetIsSlab(var1, var2, var3, var4) ? (!this.GetIsUpsideDownSlab(var1, var2, var3, var4) ? AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)((float)(var2 + 1)), (double)((float)var3 + 0.5F), (double)((float)(var4 + 1))) : AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)((float)var3 + 0.5F), (double)var4, (double)((float)(var2 + 1)), (double)((float)(var3 + 1)), (double)((float)(var4 + 1)))) : AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var2, (double)var3, (double)var4, (double)((float)(var2 + 1)), (double)((float)(var3 + 1)), (double)((float)(var4 + 1)));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (this.GetIsSlab(var1, var2, var3, var4))
        {
            if (!this.GetIsUpsideDownSlab(var1, var2, var3, var4))
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
            else
            {
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        if (!this.GetIsSlab(var1, var2, var3, var4))
        {
            SpawnHearts(var1, var2, var3, var4);
            int var6 = FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacing(var5);
            this.SetFacing(var1, var2, var3, var4, var6);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        if (!this.GetIsSlab(var1, var2, var3, var4))
        {
            var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "mob.wolf.whine", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);
        }
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
        return !this.GetIsSlab(var1, var2, var3, var4);
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return !this.GetIsSlab(var1, var2, var3, var4);
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return !this.GetIsSlab(var1, var2, var3, var4);
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (!this.GetIsSlab(var1, var2, var3, var4) && FCUtilsMisc.StandardRotateAroundJ(this, var1, var2, var3, var4, var5) && var1.rand.nextInt(12) == 0)
        {
            var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "mob.wolf.whine", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);
        }
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (!this.GetIsSlab(var1, var2, var3, var4))
        {
            int var6 = this.GetFacing(var1, var2, var3, var4);
            var6 = FCUtilsMisc.CycleFacing(var6, var5);
            this.SetFacing(var1, var2, var3, var4, var6);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);

            if (var1.rand.nextInt(12) == 0)
            {
                var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "mob.wolf.whine", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetIsSlab(var1, var2, var3, var4) ? this.GetIsUpsideDownSlab(var1, var2, var3, var4) : true;
    }

    public boolean GetIsSlab(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetIsSlabFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public void SetIsSlab(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -9;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public boolean GetIsSlabFromMetadata(int var1)
    {
        return (var1 & 8) > 0;
    }

    public boolean GetIsUpsideDownSlab(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetIsUpsideDownSlabFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public boolean GetIsUpsideDownSlabFromMetadata(int var1)
    {
        return this.GetIsSlabFromMetadata(var1) ? this.GetFacingFromMetadata(var1) == 1 : false;
    }

    static void SpawnHearts(World var0, int var1, int var2, int var3)
    {
        String var4 = "heart";

        for (int var5 = 0; var5 < 7; ++var5)
        {
            double var6 = var0.rand.nextGaussian() * 0.02D;
            double var8 = var0.rand.nextGaussian() * 0.02D;
            double var10 = var0.rand.nextGaussian() * 0.02D;
            var0.spawnParticle(var4, (double)var1 + (double)var0.rand.nextFloat(), (double)(var2 + 1) + (double)var0.rand.nextFloat(), (double)var3 + (double)var0.rand.nextFloat(), var6, var8, var10);
        }
    }
}
