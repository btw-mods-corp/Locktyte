package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockVase extends BlockContainer implements FCIBlock, ITextureProvider
{
    public static final float m_fVaseBaseWidth = 0.5F;
    public static final float m_fVaseBaseHalfWidth = 0.25F;
    public static final float m_fVaseBaseHeight = 0.0625F;
    public static final float m_fVaseBodyWidth = 0.625F;
    public static final float m_fVaseBodyHalfWidth = 0.3125F;
    public static final float m_fVaseBodyHeight = 0.375F;
    public static final float m_fVaseNeckBaseWidth = 0.5F;
    public static final float m_fVaseNeckBaseHalfWidth = 0.25F;
    public static final float m_fVaseNeckBaseHeight = 0.0625F;
    public static final float m_fVaseNeckWidth = 0.25F;
    public static final float m_fVaseNeckHalfWidth = 0.125F;
    public static final float m_fVaseNeckHeight = 0.4375F;
    public static final float m_fVaseTopWidth = 0.375F;
    public static final float m_fVaseTopHalfWidth = 0.1875F;
    public static final float m_fVaseTopHeight = 0.0625F;
    private final int iVaseFirstTextureIndex = 224;

    public FCBlockVase(int var1)
    {
        super(var1, Material.glass);
        this.blockIndexInTexture = 224;
        this.setHardness(0.0F);
        this.setStepSound(soundGlassFootstep);
        this.setBlockName("fcVase");
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
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
        return mod_FCBetterThanWolves.iCustomVaseRenderID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return var1;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return var2 == 0 ? 224 : 225 + (~var2 & 15);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityVase();
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

        if (var1.isRemote)
        {
            return true;
        }
        else
        {
            if (var10 != null && var10.stackSize > 0)
            {
                FCTileEntityVase var13 = (FCTileEntityVase)var1.getBlockTileEntity(var2, var3, var4);
                int var12 = var10.stackSize;

                if (FCUtilsInventory.AddItemStackToInventory(var13, var10))
                {
                    var5.destroyCurrentEquippedItem();
                    var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.pop", 0.25F, ((var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    return true;
                }

                if (var10.stackSize < var12)
                {
                    var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.pop", 0.25F, ((var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        FCTileEntityVase var7 = (FCTileEntityVase)((FCTileEntityVase)var1.getBlockTileEntity(var2, var3, var4));

        if (var7 != null)
        {
            FCUtilsInventory.EjectInventoryContents(var1, var2, var3, var4, var7);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
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

    public void BreakVase(World var1, int var2, int var3, int var4)
    {
        var1.playAuxSFX(2001, var2, var3, var4, this.blockID);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}
