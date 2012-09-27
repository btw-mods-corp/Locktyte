package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockRope extends Block implements ITextureProvider, FCIBlock, FCIBlockClimbable
{
    public static final float fRopeWidth = 0.125F;

    protected FCBlockRope(int var1)
    {
        super(var1, Material.circuits);
        this.setHardness(0.5F);
        this.setStepSound(soundGrassFootstep);
        this.setBlockName("fcRopeBlock");
        this.blockIndexInTexture = 32;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return mod_FCBetterThanWolves.fcRopeItem.shiftedIndex;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockId(var2, var3 + 1, var4);
        boolean var7 = true;

        if (var6 == mod_FCBetterThanWolves.fcAnchor.blockID)
        {
            int var8 = ((FCBlockAnchor)mod_FCBetterThanWolves.fcAnchor).GetFacing(var1, var2, var3 + 1, var4);

            if (var8 == 1)
            {
                var7 = false;
            }
        }
        else if (var6 != this.blockID && var6 != mod_FCBetterThanWolves.fcPulley.blockID)
        {
            var7 = false;
        }

        if (!var7)
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3 + 1, var4);
        return var5 == this.blockID || var5 == mod_FCBetterThanWolves.fcAnchor.blockID;
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
        return mod_FCBetterThanWolves.iCustomRopeRenderID;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)var2 + 0.5F - 0.0625F), (double)((float)var3), (double)((float)var4 + 0.5F - 0.0625F), (double)((float)var2 + 0.5F + 0.0625F), (double)((float)var3 + 1.0F), (double)((float)var4 + 0.5F + 0.0625F));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetFacing(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 0;
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

    public boolean IsBlockClimable(World var1, int var2, int var3, int var4)
    {
        return true;
    }

    public void BreakRope(World var1, int var2, int var3, int var4)
    {
        FCUtilsItem.EjectSingleItemWithRandomOffset(var1, var2, var3, var4, mod_FCBetterThanWolves.fcRopeItem.shiftedIndex, 0);
        var1.playAuxSFX(2001, var2, var3, var4, this.blockID);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}
