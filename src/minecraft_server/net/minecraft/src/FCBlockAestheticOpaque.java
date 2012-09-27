package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockAestheticOpaque extends Block implements FCIBlock, ITextureProvider
{
    public static final int m_iSubtypeWicker = 0;
    public static final int m_iSubtypeDung = 1;
    public static final int m_iSubtypeSteel = 2;
    public static final int m_iSubtypeHellfire = 3;
    public static final int m_iSubtypePadding = 4;
    public static final int m_iSubtypeSoap = 5;
    public static final int m_iSubtypeRope = 6;
    public static final int m_iSubtypeFlint = 7;
    public static final int m_iSubtypeNetherrackWithGrowth = 8;
    public static final int m_iNumSubtypes = 9;
    private static final int m_iDefaultTextureID = 0;
    private static final int m_iWickerTextureID = 95;
    private static final int m_iDungTextureID = 96;
    private static final int m_iSteelTextureID = 97;
    private static final int m_iHellfireTextureID = 98;
    private static final int m_iPaddingTextureID = 99;
    private static final int m_iSoapTopTextureID = 100;
    private static final int m_iSoapSidesTextureID = 101;
    private static final int m_iRopeTopAndBottomTextureID = 102;
    private static final int m_iRopeSidesTextureID = 103;
    private static final int m_iFlintTextureID = 120;
    private static final int m_iNetherrackWithGrowthTopTextureID = 124;
    private static final int m_iNetherrackWithGrowthSidesTextureID = 125;
    private static final int m_iNetherrackWithGrowthBottomTextureID = 126;
    private static final float m_fDefaultHardness = 2.0F;
    private static final float m_fWickerHardness = 1.5F;
    private static final float m_fDungHardness = 0.6F;
    private static final float m_fSteelHardness = 7.5F;
    private static final float m_fHellfireHardness = 2.0F;
    private static final float m_fPaddingHardness = 0.6F;
    private static final float m_fSoapHardness = 0.6F;
    private static final float m_fRopeHardness = 0.6F;
    private static final float m_fFilntHardness = 0.6F;

    public FCBlockAestheticOpaque(int var1)
    {
        super(var1, Material.ground);
        this.blockIndexInTexture = 0;
        this.setHardness(2.0F);
        this.setStepSound(soundStoneFootstep);
        this.setBlockName("fcAestheticOpaque");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        switch (var2)
        {
            case 0:
                return 95;

            case 1:
                return 96;

            case 2:
                return 97;

            case 3:
                return 98;

            case 4:
                return 99;

            case 5:
                if (var1 == 1)
                {
                    return 100;
                }

                return 101;

            case 6:
                if (var1 < 2)
                {
                    return 102;
                }

                return 103;

            case 7:
                return 120;

            case 8:
                if (var1 == 0)
                {
                    return 126;
                }
                else
                {
                    if (var1 == 1)
                    {
                        return 124;
                    }

                    return 125;
                }

            default:
                return this.blockIndexInTexture;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return var1 == 2 ? 0 : (var1 == 7 ? 0 : (var1 == 8 ? 0 : var1));
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return var1 == 2 ? mod_FCBetterThanWolves.fcSoulforgedSteelBlock.blockID : (var1 == 7 ? Item.flint.shiftedIndex : (var1 == 8 ? Block.netherrack.blockID : this.blockID));
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
    {
        if (var5 == 7)
        {
            if (var1.isRemote)
            {
                return;
            }

            byte var8 = 9;

            for (int var9 = 0; var9 < var8; ++var9)
            {
                int var10 = this.idDropped(var5, var1.rand, var7);

                if (var10 > 0)
                {
                    this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(var10, 1, this.damageDropped(var5)));
                }
            }
        }
        else
        {
            super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, var7);
        }
    }

    /**
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean canSilkHarvest()
    {
        return false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var6 == 8)
        {
            int var7 = var1.getBlockId(var2, var3 + 1, var4);

            if (var7 != mod_FCBetterThanWolves.fcBlockBloodMoss.blockID)
            {
                var1.setBlock(var2, var3, var4, Block.netherrack.blockID);
            }
        }
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

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }
}
