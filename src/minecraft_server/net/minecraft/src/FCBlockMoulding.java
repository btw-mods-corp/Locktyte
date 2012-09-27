package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class FCBlockMoulding extends Block implements FCIBlock
{
    private static final float fMouldingWidth = 0.5F;
    private static final float fMouldingLength = 1.0F;
    private int m_iMatchingCornerBlockID;

    protected FCBlockMoulding(int var1, Material var2, int var3, int var4, float var5, float var6, StepSound var7, String var8)
    {
        super(var1, var2);
        this.blockIndexInTexture = var3;
        this.setHardness(var5);
        this.setResistance(var6);
        this.setStepSound(var7);
        this.setBlockName(var8);
        this.m_iMatchingCornerBlockID = var4;
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
        return mod_FCBetterThanWolves.iCustomMouldingRenderID;
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        int var8 = this.GetMouldingAlignment(var1, var2, var3, var4);
        this.SetBlockBoundsForAlignment(var8);
        super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);

        for (int var9 = 0; var9 <= 2; ++var9)
        {
            int var10 = this.GetAlignmentOffsetAlongAxis(var8, var9);

            if (var10 != 0)
            {
                FCUtilsBlockPos var11 = new FCUtilsBlockPos(var2, var3, var4);
                AddOffsetAlongAxis(var11, var9, -var10);
                int var12 = this.GetAlignmentOfConnectingMouldingAtLocation(var1, var11.i, var11.j, var11.k, var8, var9);

                if (var12 >= 0)
                {
                    this.SetBoundingBoxForConnectingMoulding(var9, var10, var12);
                    super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                }
                else
                {
                    int var13 = this.GetFacingOfConnectingCornerAtLocation(var1, var11.i, var11.j, var11.k, var8, var9);

                    if (var13 >= 0)
                    {
                        this.SetBoundingBoxForConnectingCorner(var9, var10, var13);
                        super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                    }
                }
            }
        }

        this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 1.0F);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetMouldingAlignment(var1, var2, var3, var4);
        this.SetBlockBoundsForAlignment(var5);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3 var5, Vec3 var6)
    {
        MovingObjectPosition var7 = null;
        MovingObjectPosition[] var8 = new MovingObjectPosition[8];
        int var9 = 0;
        int var10 = this.GetMouldingAlignment(var1, var2, var3, var4);
        Vec3 var11 = new Vec3(0.0D, 0.0D, 0.0D);
        Vec3 var12 = new Vec3(0.0D, 0.0D, 0.0D);
        this.GetBlockBoundsForAlignment(var10, var11, var12);
        var8[var9] = RayTraceWithBox(var1, var2, var3, var4, var11, var12, var5, var6);

        if (var8[var9] != null)
        {
            ++var9;
        }

        for (int var13 = 0; var13 <= 2; ++var13)
        {
            int var14 = this.GetAlignmentOffsetAlongAxis(var10, var13);

            if (var14 != 0)
            {
                FCUtilsBlockPos var15 = new FCUtilsBlockPos(var2, var3, var4);
                AddOffsetAlongAxis(var15, var13, -var14);
                int var16 = this.GetAlignmentOfConnectingMouldingAtLocation(var1, var15.i, var15.j, var15.k, var10, var13);
                Vec3 var18;

                if (var16 >= 0)
                {
                    Vec3 var17 = new Vec3(0.0D, 0.0D, 0.0D);
                    var18 = new Vec3(0.5D, 0.5D, 0.5D);
                    this.GetBlockBoundsForAlignment(var16, var17, var18);
                    var8[var9] = RayTraceWithBox(var1, var2, var3, var4, var17, var18, var5, var6);

                    if (var8[var9] != null)
                    {
                        ++var9;
                    }
                }
                else
                {
                    int var22 = this.GetFacingOfConnectingCornerAtLocation(var1, var15.i, var15.j, var15.k, var10, var13);

                    if (var22 >= 0)
                    {
                        var18 = new Vec3(0.0D, 0.0D, 0.0D);
                        Vec3 var19 = new Vec3(0.5D, 0.5D, 0.5D);
                        this.OffsetBoundingBoxForConnectingCorner(var13, var14, var22, var18, var19);
                        var8[var9] = RayTraceWithBox(var1, var2, var3, var4, var18, var19, var5, var6);

                        if (var8[var9] != null)
                        {
                            ++var9;
                        }
                    }
                }
            }
        }

        if (var9 > 0)
        {
            --var9;

            for (double var20 = 0.0D; var9 >= 0; --var9)
            {
                double var21 = var8[var9].hitVec.squareDistanceTo(var6);

                if (var21 > var20)
                {
                    var7 = var8[var9];
                    var20 = var21;
                }
            }
        }

        return var7;
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        boolean var9 = false;
        float var10 = Math.abs(var6 - 0.5F);
        float var11 = Math.abs(var7 - 0.5F);
        float var12 = Math.abs(var8 - 0.5F);
        byte var13;

        switch (var5)
        {
            case 0:
                if (var10 > var12)
                {
                    if (var6 > 0.5F)
                    {
                        var13 = 9;
                    }
                    else
                    {
                        var13 = 11;
                    }
                }
                else if (var8 > 0.5F)
                {
                    var13 = 10;
                }
                else
                {
                    var13 = 8;
                }

                break;

            case 1:
                if (var10 > var12)
                {
                    if (var6 > 0.5F)
                    {
                        var13 = 1;
                    }
                    else
                    {
                        var13 = 3;
                    }
                }
                else if (var8 > 0.5F)
                {
                    var13 = 2;
                }
                else
                {
                    var13 = 0;
                }

                break;

            case 2:
                if (var10 > var11)
                {
                    if (var6 > 0.5F)
                    {
                        var13 = 6;
                    }
                    else
                    {
                        var13 = 7;
                    }
                }
                else if (var7 > 0.5F)
                {
                    var13 = 10;
                }
                else
                {
                    var13 = 2;
                }

                break;

            case 3:
                if (var10 > var11)
                {
                    if (var6 > 0.5F)
                    {
                        var13 = 5;
                    }
                    else
                    {
                        var13 = 4;
                    }
                }
                else if (var7 > 0.5F)
                {
                    var13 = 8;
                }
                else
                {
                    var13 = 0;
                }

                break;

            case 4:
                if (var12 > var11)
                {
                    if (var8 > 0.5F)
                    {
                        var13 = 6;
                    }
                    else
                    {
                        var13 = 5;
                    }
                }
                else if (var7 > 0.5F)
                {
                    var13 = 9;
                }
                else
                {
                    var13 = 1;
                }

                break;

            default:
                if (var12 > var11)
                {
                    if (var8 > 0.5F)
                    {
                        var13 = 7;
                    }
                    else
                    {
                        var13 = 4;
                    }
                }
                else if (var7 > 0.5F)
                {
                    var13 = 11;
                }
                else
                {
                    var13 = 3;
                }
        }

        this.SetMouldingAlignment(var1, var2, var3, var4, var13);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        ItemStack var10 = var5.getCurrentEquippedItem();

        if (var10 != null)
        {
            return false;
        }
        else
        {
            if (!var1.isRemote)
            {
                this.ToggleFacing(var1, var2, var3, var4, false);
                FCUtilsMisc.PlayPlaceSoundForBlock(var1, var2, var3, var4);
            }

            return true;
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return this.blockID != mod_FCBetterThanWolves.fcMoulding.blockID && this.blockID != mod_FCBetterThanWolves.fcBlockWoodSpruceMoulding.blockID && this.blockID != mod_FCBetterThanWolves.fcBlockWoodBirchMoulding.blockID && this.blockID != mod_FCBetterThanWolves.fcBlockWoodJungleMoulding.blockID ? super.idDropped(var1, var2, var3) : mod_FCBetterThanWolves.fcBlockWoodMouldingItemStubID;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int var1)
    {
        return this.blockID == mod_FCBetterThanWolves.fcBlockWoodSpruceMoulding.blockID ? 1 : (this.blockID == mod_FCBetterThanWolves.fcBlockWoodBirchMoulding.blockID ? 2 : (this.blockID == mod_FCBetterThanWolves.fcBlockWoodJungleMoulding.blockID ? 3 : super.damageDropped(var1)));
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
        int var5 = this.GetMouldingAlignment(var1, var2, var3, var4);
        return var5 < 8;
    }

    public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        var6 = this.RotateMetadataAroundJAxis(var6, var5);
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
    }

    public int RotateMetadataAroundJAxis(int var1, boolean var2)
    {
        int var3 = this.GetMouldingAlignmentFromMetadata(var1);

        if (var2)
        {
            ++var3;

            if (var3 == 4)
            {
                var3 = 0;
            }
            else if (var3 == 8)
            {
                var3 = 4;
            }
            else if (var3 >= 12)
            {
                var3 = 8;
            }
        }
        else
        {
            --var3;

            if (var3 < 0)
            {
                var3 = 3;
            }
            else if (var3 == 3)
            {
                var3 = 7;
            }
            else if (var3 == 7)
            {
                var3 = 11;
            }
        }

        var1 = this.SetMouldingAlignmentInMetadata(var1, var3);
        return var1;
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetMouldingAlignment(var1, var2, var3, var4);

        if (!var5)
        {
            ++var6;

            if (var6 > 11)
            {
                var6 = 0;
            }
        }
        else
        {
            --var6;

            if (var6 < 0)
            {
                var6 = 11;
            }
        }

        this.SetMouldingAlignment(var1, var2, var3, var4, var6);
        var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
        return true;
    }

    public int GetMouldingAlignment(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetMouldingAlignmentFromMetadata(var1.getBlockMetadata(var2, var3, var4));
    }

    public int GetMouldingAlignmentFromMetadata(int var1)
    {
        return var1;
    }

    public void SetMouldingAlignment(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        var6 = this.SetMouldingAlignmentInMetadata(var6, var5);
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    public int SetMouldingAlignmentInMetadata(int var1, int var2)
    {
        return var2;
    }

    private float ClickOffsetFromCenter(float var1)
    {
        return Math.abs(var1 - 0.5F);
    }

    public int GetAlignmentOffsetAlongAxis(int var1, int var2)
    {
        if (var2 == 0)
        {
            switch (var1)
            {
                case 0:
                    return 0;

                case 1:
                    return 1;

                case 2:
                    return 0;

                case 3:
                    return -1;

                case 4:
                    return -1;

                case 5:
                    return 1;

                case 6:
                    return 1;

                case 7:
                    return -1;

                case 8:
                    return 0;

                case 9:
                    return 1;

                case 10:
                    return 0;

                default:
                    return -1;
            }
        }
        else if (var2 == 1)
        {
            switch (var1)
            {
                case 0:
                    return -1;

                case 1:
                    return -1;

                case 2:
                    return -1;

                case 3:
                    return -1;

                case 4:
                    return 0;

                case 5:
                    return 0;

                case 6:
                    return 0;

                case 7:
                    return 0;

                case 8:
                    return 1;

                case 9:
                    return 1;

                case 10:
                    return 1;

                default:
                    return 1;
            }
        }
        else
        {
            switch (var1)
            {
                case 0:
                    return -1;

                case 1:
                    return 0;

                case 2:
                    return 1;

                case 3:
                    return 0;

                case 4:
                    return -1;

                case 5:
                    return -1;

                case 6:
                    return 1;

                case 7:
                    return 1;

                case 8:
                    return -1;

                case 9:
                    return 0;

                case 10:
                    return 1;

                default:
                    return 0;
            }
        }
    }

    public void SetBlockBoundsForAlignment(int var1)
    {
        Vec3 var2 = new Vec3(0.0D, 0.0D, 0.0D);
        Vec3 var3 = new Vec3(0.0D, 0.0D, 0.0D);
        this.GetBlockBoundsForAlignment(var1, var2, var3);
        this.setBlockBounds((float)var2.xCoord, (float)var2.yCoord, (float)var2.zCoord, (float)var3.xCoord, (float)var3.yCoord, (float)var3.zCoord);
    }

    public void GetBlockBoundsForAlignment(int var1, Vec3 var2, Vec3 var3)
    {
        var2.xCoord = 0.0D;
        var3.xCoord = var2.xCoord + 1.0D;
        var2.yCoord = 0.0D;
        var3.yCoord = var2.yCoord + 1.0D;
        var2.zCoord = 0.0D;
        var3.zCoord = var2.zCoord + 1.0D;

        if (var1 == 0)
        {
            var3.yCoord = var2.yCoord + 0.5D;
            var3.zCoord = var2.zCoord + 0.5D;
        }
        else if (var1 == 1)
        {
            var2.xCoord += 0.5D;
            var3.yCoord = var2.yCoord + 0.5D;
        }
        else if (var1 == 2)
        {
            var3.yCoord = var2.yCoord + 0.5D;
            var2.zCoord += 0.5D;
        }
        else if (var1 == 3)
        {
            var3.xCoord = var2.xCoord + 0.5D;
            var3.yCoord = var2.yCoord + 0.5D;
        }
        else if (var1 == 4)
        {
            var3.xCoord = var2.xCoord + 0.5D;
            var3.zCoord = var2.zCoord + 0.5D;
        }
        else if (var1 == 5)
        {
            var2.xCoord += 0.5D;
            var3.zCoord = var2.zCoord + 0.5D;
        }
        else if (var1 == 6)
        {
            var2.xCoord += 0.5D;
            var2.zCoord += 0.5D;
        }
        else if (var1 == 7)
        {
            var3.xCoord = var2.xCoord + 0.5D;
            var2.zCoord += 0.5D;
        }
        else if (var1 == 8)
        {
            var2.yCoord += 0.5D;
            var3.zCoord = var2.zCoord + 0.5D;
        }
        else if (var1 == 9)
        {
            var2.xCoord += 0.5D;
            var2.yCoord += 0.5D;
        }
        else if (var1 == 10)
        {
            var2.yCoord += 0.5D;
            var2.zCoord += 0.5D;
        }
        else
        {
            var3.xCoord = var2.xCoord + 0.5D;
            var2.yCoord += 0.5D;
        }
    }

    private static void AddOffsetAlongAxis(FCUtilsBlockPos var0, int var1, int var2)
    {
        if (var1 == 0)
        {
            var0.i += var2;
        }
        else if (var1 == 1)
        {
            var0.j += var2;
        }
        else
        {
            var0.k += var2;
        }
    }

    private static void OffsetCornerBoundingBoxAlongAxis(int var0, int var1, Vec3 var2, Vec3 var3)
    {
        if (var1 > 0)
        {
            if (var0 == 0)
            {
                var2.xCoord += 0.5D;
                var3.xCoord += 0.5D;
            }
            else if (var0 == 1)
            {
                var2.yCoord += 0.5D;
                var3.yCoord += 0.5D;
            }
            else
            {
                var2.zCoord += 0.5D;
                var3.zCoord += 0.5D;
            }
        }
    }

    private int GetAlignmentOfConnectingMouldingAtLocation(IBlockAccess var1, int var2, int var3, int var4, int var5, int var6)
    {
        int var7 = var1.getBlockId(var2, var3, var4);

        if (var7 == this.blockID)
        {
            int var8 = this.GetMouldingAlignment(var1, var2, var3, var4);

            if (this.GetAlignmentOffsetAlongAxis(var8, var6) == 0)
            {
                for (int var9 = 0; var9 <= 2; ++var9)
                {
                    if (var9 != var6 && this.GetAlignmentOffsetAlongAxis(var8, var9) == this.GetAlignmentOffsetAlongAxis(var5, var9))
                    {
                        return var8;
                    }
                }
            }
        }

        return -1;
    }

    private int GetFacingOfConnectingCornerAtLocation(IBlockAccess var1, int var2, int var3, int var4, int var5, int var6)
    {
        int var7 = var1.getBlockId(var2, var3, var4);
        int var8 = var1.getBlockMetadata(var2, var3, var4);
        int var9 = this.GetMatchingCornerFacing(var7, var8);

        if (var9 >= 0 && this.GetAlignmentOffsetAlongAxis(var5, var6) == this.GetCornerAlignmentOffsetAlongAxis(var9, var6))
        {
            int var10 = -1;

            for (int var11 = 0; var11 <= 2; ++var11)
            {
                if (var11 != var6)
                {
                    if (this.GetAlignmentOffsetAlongAxis(var5, var11) == 0)
                    {
                        var10 = var9;
                    }
                    else if (this.GetAlignmentOffsetAlongAxis(var5, var11) != this.GetCornerAlignmentOffsetAlongAxis(var9, var11))
                    {
                        return -1;
                    }
                }
            }

            return var10;
        }
        else
        {
            return -1;
        }
    }

    private int GetCornerAlignmentOffsetAlongAxis(int var1, int var2)
    {
        return (var1 & 4 >> var2) > 0 ? 1 : -1;
    }

    private int GetMatchingCornerFacing(int var1, int var2)
    {
        if (var1 == this.m_iMatchingCornerBlockID)
        {
            if (this.blockID == mod_FCBetterThanWolves.fcStoneMoulding.blockID)
            {
                if ((var2 & 8) > 0)
                {
                    return var2 & 7;
                }
            }
            else if (this.blockID == mod_FCBetterThanWolves.fcMoulding.blockID)
            {
                if ((var2 & 8) == 0)
                {
                    return var2 & 7;
                }
            }
            else if ((var2 & 1) > 0)
            {
                return var2 >> 1;
            }
        }

        return -1;
    }

    public static MovingObjectPosition RayTraceWithBox(World var0, int var1, int var2, int var3, Vec3 var4, Vec3 var5, Vec3 var6, Vec3 var7)
    {
        var6 = var6.addVector((double)(-var1), (double)(-var2), (double)(-var3));
        var7 = var7.addVector((double)(-var1), (double)(-var2), (double)(-var3));
        Vec3 var8 = var6.getIntermediateWithXValue(var7, var4.xCoord);
        Vec3 var9 = var6.getIntermediateWithXValue(var7, var5.xCoord);
        Vec3 var10 = var6.getIntermediateWithYValue(var7, var4.yCoord);
        Vec3 var11 = var6.getIntermediateWithYValue(var7, var5.yCoord);
        Vec3 var12 = var6.getIntermediateWithZValue(var7, var4.zCoord);
        Vec3 var13 = var6.getIntermediateWithZValue(var7, var5.zCoord);

        if (!isVecInsideYZBounds(var8, var4, var5))
        {
            var8 = null;
        }

        if (!isVecInsideYZBounds(var9, var4, var5))
        {
            var9 = null;
        }

        if (!isVecInsideXZBounds(var10, var4, var5))
        {
            var10 = null;
        }

        if (!isVecInsideXZBounds(var11, var4, var5))
        {
            var11 = null;
        }

        if (!isVecInsideXYBounds(var12, var4, var5))
        {
            var12 = null;
        }

        if (!isVecInsideXYBounds(var13, var4, var5))
        {
            var13 = null;
        }

        Vec3 var14 = null;

        if (var8 != null && (var14 == null || var6.squareDistanceTo(var8) < var6.squareDistanceTo(var14)))
        {
            var14 = var8;
        }

        if (var9 != null && (var14 == null || var6.squareDistanceTo(var9) < var6.squareDistanceTo(var14)))
        {
            var14 = var9;
        }

        if (var10 != null && (var14 == null || var6.squareDistanceTo(var10) < var6.squareDistanceTo(var14)))
        {
            var14 = var10;
        }

        if (var11 != null && (var14 == null || var6.squareDistanceTo(var11) < var6.squareDistanceTo(var14)))
        {
            var14 = var11;
        }

        if (var12 != null && (var14 == null || var6.squareDistanceTo(var12) < var6.squareDistanceTo(var14)))
        {
            var14 = var12;
        }

        if (var13 != null && (var14 == null || var6.squareDistanceTo(var13) < var6.squareDistanceTo(var14)))
        {
            var14 = var13;
        }

        if (var14 == null)
        {
            return null;
        }
        else
        {
            byte var15 = -1;

            if (var14 == var8)
            {
                var15 = 4;
            }

            if (var14 == var9)
            {
                var15 = 5;
            }

            if (var14 == var10)
            {
                var15 = 0;
            }

            if (var14 == var11)
            {
                var15 = 1;
            }

            if (var14 == var12)
            {
                var15 = 2;
            }

            if (var14 == var13)
            {
                var15 = 3;
            }

            return new MovingObjectPosition(var1, var2, var3, var15, var14.addVector((double)var1, (double)var2, (double)var3));
        }
    }

    private static boolean isVecInsideYZBounds(Vec3 var0, Vec3 var1, Vec3 var2)
    {
        return var0 == null ? false : var0.yCoord >= var1.yCoord && var0.yCoord <= var2.yCoord && var0.zCoord >= var1.zCoord && var0.zCoord <= var2.zCoord;
    }

    private static boolean isVecInsideXZBounds(Vec3 var0, Vec3 var1, Vec3 var2)
    {
        return var0 == null ? false : var0.xCoord >= var1.xCoord && var0.xCoord <= var2.xCoord && var0.zCoord >= var1.zCoord && var0.zCoord <= var2.zCoord;
    }

    private static boolean isVecInsideXYBounds(Vec3 var0, Vec3 var1, Vec3 var2)
    {
        return var0 == null ? false : var0.xCoord >= var1.xCoord && var0.xCoord <= var2.xCoord && var0.yCoord >= var1.yCoord && var0.yCoord <= var2.yCoord;
    }

    private static void ExpandBoundingBoxToIncludeAnother(Vec3 var0, Vec3 var1, Vec3 var2, Vec3 var3)
    {
        if (var2.xCoord < var0.xCoord)
        {
            var0.xCoord = var2.xCoord;
        }

        if (var3.xCoord > var1.xCoord)
        {
            var1.xCoord = var3.xCoord;
        }

        if (var2.yCoord < var0.yCoord)
        {
            var0.yCoord = var2.yCoord;
        }

        if (var3.yCoord > var1.yCoord)
        {
            var1.yCoord = var3.yCoord;
        }

        if (var2.zCoord < var0.zCoord)
        {
            var0.zCoord = var2.zCoord;
        }

        if (var3.zCoord > var1.zCoord)
        {
            var1.zCoord = var3.zCoord;
        }
    }

    private void SetBoundingBoxForConnectingMoulding(int var1, int var2, int var3)
    {
        Vec3 var4 = new Vec3(0.0D, 0.0D, 0.0D);
        Vec3 var5 = new Vec3(0.5D, 0.5D, 0.5D);

        for (int var6 = 0; var6 <= 2; ++var6)
        {
            if (var6 == var1)
            {
                OffsetCornerBoundingBoxAlongAxis(var6, -var2, var4, var5);
            }
            else
            {
                OffsetCornerBoundingBoxAlongAxis(var6, this.GetAlignmentOffsetAlongAxis(var3, var6), var4, var5);
            }
        }

        this.setBlockBounds((float)var4.xCoord, (float)var4.yCoord, (float)var4.zCoord, (float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
    }

    private void SetBoundingBoxForConnectingCorner(int var1, int var2, int var3)
    {
        Vec3 var4 = new Vec3(0.0D, 0.0D, 0.0D);
        Vec3 var5 = new Vec3(0.5D, 0.5D, 0.5D);
        this.OffsetBoundingBoxForConnectingCorner(var1, var2, var3, var4, var5);
        this.setBlockBounds((float)var4.xCoord, (float)var4.yCoord, (float)var4.zCoord, (float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
    }

    private void OffsetBoundingBoxForConnectingCorner(int var1, int var2, int var3, Vec3 var4, Vec3 var5)
    {
        for (int var6 = 0; var6 <= 2; ++var6)
        {
            if (var6 == var1)
            {
                OffsetCornerBoundingBoxAlongAxis(var6, -var2, var4, var5);
            }
            else
            {
                OffsetCornerBoundingBoxAlongAxis(var6, this.GetCornerAlignmentOffsetAlongAxis(var3, var6), var4, var5);
            }
        }
    }
}
