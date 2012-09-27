package net.minecraft.src;

import java.util.List;

public abstract class FCBlockSlab extends Block implements FCIBlockSolidTop
{
    public FCBlockSlab(int var1, int var2, Material var3)
    {
        super(var1, var2, var3);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        this.setLightOpacity(255);
        Block.useNeighborBrightness[var1] = true;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        if (var5 == 0 || var5 != 1 && (double)var7 > 0.5D)
        {
            this.SetIsUpsideDown(var1, var2, var3, var4, true);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (this.GetIsUpsideDown(var1, var2, var3, var4))
        {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
        super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
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

    public boolean DoesBlockHaveSolidTop(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetIsUpsideDown(var1, var2, var3, var4);
    }

    public abstract boolean GetIsUpsideDown(IBlockAccess var1, int var2, int var3, int var4);

    public abstract void SetIsUpsideDown(World var1, int var2, int var3, int var4, boolean var5);
}
