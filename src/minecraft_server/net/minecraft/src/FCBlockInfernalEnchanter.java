package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockInfernalEnchanter extends BlockContainer implements FCIBlock, ITextureProvider
{
    private static final int m_iTopTextureID = 121;
    private static final int m_iSideTextureID = 122;
    private static final int m_iBottomTextureID = 123;
    private static final int m_iCandleTextureID = 225;
    public static final float m_fBlockHeight = 0.5F;
    public static final float m_fCandleHeight = 0.25F;
    private static final float m_fBlockHardness = 100.0F;
    private static final float m_fBlockExplosionResistance = 2000.0F;
    private static final int m_iHorizontalBookShelfCheckDistance = 8;
    private static final int m_iVerticalPositiveBookShelfCheckDistance = 8;
    private static final int m_iVerticalNegativeBookShelfCheckDistance = 8;

    protected FCBlockInfernalEnchanter(int var1)
    {
        super(var1, mod_FCBetterThanWolves.fcSoulforgedSteelMaterial);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        this.setLightOpacity(0);
        this.setHardness(100.0F);
        this.setResistance(2000.0F);
        this.setStepSound(soundMetalFootstep);
        this.setBlockName("fcInfernalEnchanter");
        this.blockIndexInTexture = 123;
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
        return mod_FCBetterThanWolves.iCustomInfernalEnchanterRenderID;
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
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return this.getBlockTextureFromSide(var1);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int var1)
    {
        return var1 == 0 ? 123 : (var1 == 1 ? 121 : 122);
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

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5) {}

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        return var1;
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityInfernalEnchanter();
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
            FCContainerInfernalEnchanter var12 = new FCContainerInfernalEnchanter(var5.inventory, var1, var2, var3, var4);
            mod_FCBetterThanWolves.BTWServerOpenWindow((EntityPlayerMP)var5, var12, mod_FCBetterThanWolves.fcInfernalEnchanterContainerID, 0, 0, 0);
        }

        return true;
    }

    private void DisplayMagicLetters(World var1, int var2, int var3, int var4, Random var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 != null && var6 instanceof FCTileEntityInfernalEnchanter)
        {
            FCTileEntityInfernalEnchanter var7 = (FCTileEntityInfernalEnchanter)var6;

            if (var7.m_bPlayerNear)
            {
                for (int var8 = 0; var8 < 64; ++var8)
                {
                    int var9 = var5.nextInt(17) - 8 + var2;
                    int var10 = var5.nextInt(17) - 8 + var3;
                    int var11 = var5.nextInt(17) - 8 + var4;

                    if (this.IsValidBookshelf(var1, var9, var10, var11))
                    {
                        Vec3 var12 = Vec3.createVectorHelper((double)(var2 - var9), (double)(var3 - var10), (double)(var4 - var11));
                        var12.normalize();
                        var1.spawnParticle("enchantmenttable", (double)var2 + 0.5D, (double)var3 + 1.75D, (double)var4 + 0.5D, var12.xCoord, var12.yCoord, var12.zCoord);
                    }
                }
            }
        }
    }

    private boolean IsValidBookshelf(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3, var4);
        return var5 == Block.bookShelf.blockID && (var1.isAirBlock(var2 + 1, var3, var4) || var1.isAirBlock(var2 - 1, var3, var4) || var1.isAirBlock(var2, var3, var4 + 1) || var1.isAirBlock(var2, var3, var4 - 1));
    }
}
