package net.minecraft.src;

import forge.ITextureProvider;
import java.util.Random;

public class FCBlockDirtSlab extends FCBlockSlab implements ITextureProvider
{
    public static final int m_iSubtypeDirt = 0;
    public static final int m_iSubtypeGrass = 1;
    public static final int m_iSubtypeMycelium = 2;
    public static final int m_iSubtypeSnow = 3;
    private static final int m_iDefaultTexture = 2;
    private static final int m_iGrassSideTexture = 131;
    public static final int m_iGrassSidePatchTexture = 132;
    private static final int m_iGrassTopTexture = 133;
    private static final int m_iGrassHalfSideTexture = 134;
    public static final int m_iGrassHalfSidePatchTexture = 135;
    public static final int m_iNumSubtypes = 3;

    public FCBlockDirtSlab(int var1)
    {
        super(var1, 2, Material.ground);
        this.setHardness(0.5F);
        this.setStepSound(soundGrassFootstep);
        this.setBlockName("fcDirtSlab");
        this.setTickRandomly(true);
        this.setRequiresSelfNotify();
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomDirtSlabRenderID;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        int var3 = (var2 & -2) >> 1;

        if (var3 == 1 && var1 != 0)
        {
            if (var1 != 1)
            {
                boolean var4 = (var2 & 1) > 0;
                return var4 ? 131 : 134;
            }
            else
            {
                return 133;
            }
        }
        else
        {
            return 2;
        }
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
        return (var1.getBlockMetadata(var2, var3, var4) & 1) > 0;
    }

    public void SetIsUpsideDown(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & -2;

        if (var5)
        {
            var6 |= 1;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public int GetSubtype(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & -2) >> 1;
    }

    public void SetSubtype(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 1;
        var6 |= var5 << 1;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!var1.isRemote)
        {
            boolean var6 = this.GetIsUpsideDown(var1, var2, var3, var4);
            boolean var7 = false;
            int var8;

            if (!var6)
            {
                var8 = var1.getBlockId(var2, var3 - 1, var4);

                if (var8 == Block.grass.blockID)
                {
                    var1.setBlockWithNotify(var2, var3 - 1, var4, Block.dirt.blockID);
                }
            }
            else
            {
                var8 = var1.getBlockId(var2, var3 + 1, var4);

                if (var8 == this.blockID && !this.GetIsUpsideDown(var1, var2, var3 + 1, var4))
                {
                    var7 = true;
                }
            }

            var8 = this.GetSubtype(var1, var2, var3, var4);
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;
            int var14;

            if (var8 == 1)
            {
                if ((var1.getBlockLightValue(var2, var3 + 1, var4) >= 4 || Block.lightOpacity[var1.getBlockId(var2, var3 + 1, var4)] <= 2) && !var7)
                {
                    if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9)
                    {
                        for (var9 = 0; var9 < 4; ++var9)
                        {
                            var10 = var2 + var5.nextInt(3) - 1;
                            var11 = var3 + var5.nextInt(5) - 3;
                            var12 = var4 + var5.nextInt(3) - 1;
                            var13 = var1.getBlockId(var10, var11, var12);
                            var14 = var1.getBlockId(var10, var11 + 1, var12);
                            boolean var15 = false;

                            if (var14 == mod_FCBetterThanWolves.fcBlockDirtSlab.blockID)
                            {
                                var15 = !this.GetIsUpsideDown(var1, var10, var11 + 1, var12);
                            }

                            if (var13 == Block.dirt.blockID && var1.getBlockLightValue(var10, var11 + 1, var12) >= 4 && Block.lightOpacity[var14] <= 2)
                            {
                                if (!var15)
                                {
                                    var1.setBlockWithNotify(var10, var11, var12, Block.grass.blockID);
                                }
                            }
                            else if (var13 == mod_FCBetterThanWolves.fcBlockDirtSlab.blockID && var1.getBlockLightValue(var10, var11 + 1, var12) >= 4 && Block.lightOpacity[var14] <= 2)
                            {
                                int var16 = this.GetSubtype(var1, var10, var11, var12);

                                if (var16 == 0 && (!var15 || !this.GetIsUpsideDown(var1, var10, var11, var12)))
                                {
                                    this.SetSubtype(var1, var10, var11, var12, 1);
                                }
                            }
                        }
                    }
                }
                else
                {
                    this.SetSubtype(var1, var2, var3, var4, 0);
                }
            }
            else if (var8 == 0 && var5.nextInt(5) == 0 && !var7 && var1.getBlockLightValue(var2, var3 + 1, var4) >= 4 && Block.lightOpacity[var1.getBlockId(var2, var3 + 1, var4)] <= 2)
            {
                for (var9 = 0; var9 < 4; ++var9)
                {
                    var10 = var2 + var5.nextInt(3) - 1;
                    var11 = var3 + var5.nextInt(5) - 3;
                    var12 = var4 + var5.nextInt(3) - 1;
                    var13 = var1.getBlockId(var10, var11, var12);
                    var14 = var1.getBlockId(var10, var11 + 1, var12);

                    if (var13 == Block.grass.blockID && var1.getBlockLightValue(var10, var11 + 1, var12) >= 9 && Block.lightOpacity[var14] <= 2)
                    {
                        this.SetSubtype(var1, var2, var3, var4, 1);
                    }
                }
            }
        }
    }
}
