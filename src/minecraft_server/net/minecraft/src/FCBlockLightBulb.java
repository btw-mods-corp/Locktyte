package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockLightBulb extends Block implements FCIBlock, ITextureProvider
{
    private final int iLightBulbOffTextureIndex = 4;
    private final int iLightBulbOnTextureIndex = 5;
    private static final int iLightBulbTickRate = 2;

    public FCBlockLightBulb(int var1, boolean var2)
    {
        super(var1, 0, Material.glass);
        this.setHardness(0.4F);
        this.setStepSound(Block.soundGlassFootstep);
        this.setBlockName("fclightbulb");
        this.blockIndexInTexture = 4;
        this.setRequiresSelfNotify();

        if (var2)
        {
            this.setLightValue(1.0F);
        }
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 2;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return this.blockID == mod_FCBetterThanWolves.fcLightBulbOn.blockID ? 5 : 4;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return mod_FCBetterThanWolves.fcLightBulbOff.blockID;
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
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4);

        if (var6)
        {
            if (!this.IsLightOn(var1, var2, var3, var4))
            {
                this.LightBulbTurnOn(var1, var2, var3, var4);
                return;
            }
        }
        else if (this.IsLightOn(var1, var2, var3, var4))
        {
            this.LightBulbTurnOff(var1, var2, var3, var4);
            return;
        }
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
        return true;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
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

    private void LightBulbTurnOn(World var1, int var2, int var3, int var4)
    {
        var1.setBlockWithNotify(var2, var3, var4, mod_FCBetterThanWolves.fcLightBulbOn.blockID);
    }

    private void LightBulbTurnOff(World var1, int var2, int var3, int var4)
    {
        var1.setBlockWithNotify(var2, var3, var4, mod_FCBetterThanWolves.fcLightBulbOff.blockID);
    }

    public boolean IsLightOn(World var1, int var2, int var3, int var4)
    {
        return var1.getBlockId(var2, var3, var4) == mod_FCBetterThanWolves.fcLightBulbOn.blockID;
    }
}
