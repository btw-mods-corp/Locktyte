package net.minecraft.src;

import java.util.Random;

public class FCBlockWoolSlab extends FCBlockSlab
{
    private boolean m_bIsUpsideDown;
    private static final int iWoolSlabDefaultTexture = 64;
    public static final int m_iNumSubtypes = 16;

    public FCBlockWoolSlab(int var1, boolean var2)
    {
        super(var1, 64, Material.cloth);
        this.setHardness(0.8F);
        this.setStepSound(soundClothFootstep);
        this.setBlockName("fcWoolSlab");
        this.setRequiresSelfNotify();
        this.m_bIsUpsideDown = var2;

        if (!var2)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        if (var2 == 0)
        {
            return this.blockIndexInTexture;
        }
        else
        {
            var2 = ~(var2 & 15);
            return 113 + ((var2 & 8) >> 3) + (var2 & 7) * 16;
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return mod_FCBetterThanWolves.fcWoolSlab.blockID;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return var1;
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
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean canSilkHarvest()
    {
        return false;
    }

    public boolean GetIsUpsideDown(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.m_bIsUpsideDown;
    }

    public void SetIsUpsideDown(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (this.m_bIsUpsideDown != var5)
        {
            int var6 = mod_FCBetterThanWolves.fcBlockWoolSlabTop.blockID;
            int var7 = var1.getBlockMetadata(var2, var3, var4);

            if (this.blockID == mod_FCBetterThanWolves.fcBlockWoolSlabTop.blockID)
            {
                var6 = mod_FCBetterThanWolves.fcWoolSlab.blockID;
            }

            var1.setBlockAndMetadataWithNotify(var2, var3, var4, var6, var7);
        }
    }
}
