package net.minecraft.src;

import java.util.List;

public class FCUtilsMisc
{
    public static int GetOppositeFacing(int var0)
    {
        return var0 ^ 1;
    }

    public static int RotateFacingAroundJ(int var0, boolean var1)
    {
        if (var0 >= 2)
        {
            switch (var0)
            {
                case 2:
                    var0 = 5;
                    break;

                case 3:
                    var0 = 4;
                    break;

                case 4:
                    var0 = 2;
                    break;

                case 5:
                    var0 = 3;
            }

            if (!var1)
            {
                var0 = GetOppositeFacing(var0);
            }
        }

        return var0;
    }

    public static int CycleFacing(int var0, boolean var1)
    {
        if (var1)
        {
            switch (var0)
            {
                case 0:
                    var0 = 1;
                    break;

                case 1:
                    var0 = 2;
                    break;

                case 2:
                    var0 = 5;
                    break;

                case 3:
                    var0 = 4;
                    break;

                case 4:
                    var0 = 0;
                    break;

                case 5:
                    var0 = 3;
            }
        }
        else
        {
            switch (var0)
            {
                case 0:
                    var0 = 4;
                    break;

                case 1:
                    var0 = 0;
                    break;

                case 2:
                    var0 = 1;
                    break;

                case 3:
                    var0 = 5;
                    break;

                case 4:
                    var0 = 3;
                    break;

                case 5:
                    var0 = 2;
            }
        }

        return var0;
    }

    public static int ConvertPlacingEntityOrientationToBlockFacing(EntityLiving var0)
    {
        float var1 = var0.rotationPitch;
        return var1 > 60.0F ? 1 : (var1 < -60.0F ? 0 : ConvertPlacingEntityOrientationToFlatBlockFacing(var0));
    }

    public static int ConvertPlacingEntityOrientationToFlatBlockFacing(EntityLiving var0)
    {
        int var2 = MathHelper.floor_double((double)(var0.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        byte var1;

        if (var2 == 0)
        {
            var1 = 2;
        }
        else if (var2 == 1)
        {
            var1 = 5;
        }
        else if (var2 == 2)
        {
            var1 = 3;
        }
        else
        {
            var1 = 4;
        }

        return var1;
    }

    public static int ConvertPlacingEntityOrientationToBlockFlatFacing(EntityLiving var0)
    {
        int var2 = MathHelper.floor_double((double)(var0.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        byte var1;

        if (var2 == 0)
        {
            var1 = 2;
        }
        else if (var2 == 1)
        {
            var1 = 5;
        }
        else if (var2 == 2)
        {
            var1 = 3;
        }
        else
        {
            var1 = 4;
        }

        return var1;
    }

    static boolean IsBlockBeingPrecipitatedOn(World var0, int var1, int var2, int var3)
    {
        if (!var0.isRaining())
        {
            return false;
        }
        else if (!var0.canBlockSeeTheSky(var1, var2, var3))
        {
            return false;
        }
        else if (var0.getTopSolidOrLiquidBlock(var1, var3) > var2)
        {
            return false;
        }
        else
        {
            BiomeGenBase var4 = var0.getWorldChunkManager().getBiomeGenAt(var1, var3);
            return var4.getEnableSnow() ? true : var4.canSpawnLightningBolt();
        }
    }

    static boolean IsIKBeingPrecipitatedOn(World var0, int var1, int var2)
    {
        if (!var0.isRaining())
        {
            return false;
        }
        else
        {
            BiomeGenBase var3 = var0.getWorldChunkManager().getBiomeGenAt(var1, var2);
            return var3.getEnableSnow() ? true : var3.canSpawnLightningBolt();
        }
    }

    static boolean IsIKInColdBiome(World var0, int var1, int var2)
    {
        BiomeGenBase var3 = var0.getBiomeGenForCoords(var1, var2);
        float var4 = var3.getFloatTemperature();
        return var4 <= 0.15F;
    }

    public static void PositionAllNonPlayerMoveableEntitiesOutsideOfLocation(World var0, int var1, int var2, int var3)
    {
        List var4 = var0.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var1, (double)var2, (double)var3, (double)var1 + 1.0D, (double)var2 + 1.0D, (double)var3 + 1.0D));

        if (var4 != null && var4.size() > 0)
        {
            for (int var5 = 0; var5 < var4.size(); ++var5)
            {
                Entity var6 = (Entity)var4.get(var5);

                if ((var6.canBePushed() || var6 instanceof EntityItem) && !(var6 instanceof EntityPlayer))
                {
                    PositionEntityOutsideOfLocation(var0, var6, var1, var2, var3);
                }
            }
        }
    }

    private static void PositionEntityOutsideOfLocation(World var0, Entity var1, int var2, int var3, int var4)
    {
        double var5 = (double)((float)var2);
        double var7 = (double)((float)var3);
        double var9 = (double)((float)var4);
        double var11 = (double)((float)(var2 + 1));
        double var13 = (double)((float)(var3 + 1));
        double var15 = (double)((float)(var4 + 1));
        boolean var17 = false;
        boolean var18 = false;
        boolean var19 = false;
        double var20 = 0.0D;
        double var22 = 0.0D;
        double var24 = 0.0D;

        if (var1.boundingBox.minX <= var11 && var1.boundingBox.maxX >= var5)
        {
            var17 = true;

            if (Math.abs(var11 - var1.boundingBox.minX) < Math.abs(var5 - var1.boundingBox.maxX))
            {
                var20 = var11 - var1.boundingBox.minX + 0.01D;
            }
            else
            {
                var20 = var5 - var1.boundingBox.maxX - 0.01D;
            }
        }

        if (var1.boundingBox.minY <= var13 && var1.boundingBox.maxY >= var7)
        {
            var18 = true;

            if (Math.abs(var13 - var1.boundingBox.minY) < Math.abs(var7 - var1.boundingBox.maxY))
            {
                var22 = var13 - var1.boundingBox.minY + 0.01D;
            }
            else
            {
                var22 = var7 - var1.boundingBox.maxY - 0.01D;
            }
        }

        if (var1.boundingBox.minZ <= var15 && var1.boundingBox.maxZ >= var9)
        {
            var19 = true;

            if (Math.abs(var15 - var1.boundingBox.minZ) < Math.abs(var9 - var1.boundingBox.maxZ))
            {
                var24 = var15 - var1.boundingBox.minZ + 0.01D;
            }
            else
            {
                var24 = var9 - var1.boundingBox.maxZ - 0.01D;
            }
        }

        double var26 = var1.posX;
        double var28 = var1.posY;
        double var30 = var1.posZ;

        if (var17 && Math.abs(var20) < 0.2D && (!var18 || Math.abs(var20) < Math.abs(var22)) && (!var19 || Math.abs(var20) < Math.abs(var24)))
        {
            var26 += var20;
        }
        else if (var18 && Math.abs(var22) < 0.2D && (!var19 || Math.abs(var22) < Math.abs(var24)))
        {
            var28 += var22;
        }
        else if (var19 && Math.abs(var24) < 0.2D)
        {
            var30 += var24;
        }

        var1.setPosition(var26, var28, var30);

        if (var1 instanceof EntityPlayerMP)
        {
            EntityPlayerMP var32 = (EntityPlayerMP)var1;
            var32.playerNetServerHandler.sendPacket(new Packet13PlayerLookMove(var26, var28 + 1.6200000047683716D, var28, var30, var32.rotationYaw, var32.rotationPitch, false));
        }
    }

    public static void ServerPositionAllPlayerEntitiesOutsideOfLocation(World var0, int var1, int var2, int var3)
    {
        List var4 = var0.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var1, (double)var2, (double)var3, (double)var1 + 1.0D, (double)var2 + 1.0D, (double)var3 + 1.0D));

        if (var4 != null && var4.size() > 0)
        {
            for (int var5 = 0; var5 < var4.size(); ++var5)
            {
                EntityPlayerMP var6 = (EntityPlayerMP)var4.get(var5);
                ServerPositionPlayerEntityOutsideOfLocation(var0, var6, var1, var2, var3);
            }
        }
    }

    private static void ServerPositionPlayerEntityOutsideOfLocation(World var0, EntityPlayerMP var1, int var2, int var3, int var4)
    {
        double var5 = (double)((float)var2);
        double var7 = (double)((float)var3);
        double var9 = (double)((float)var4);
        double var11 = (double)((float)(var2 + 1));
        double var13 = (double)((float)(var3 + 1));
        double var15 = (double)((float)(var4 + 1));
        boolean var17 = false;
        boolean var18 = false;
        boolean var19 = false;
        double var20 = 0.0D;
        double var22 = 0.0D;
        double var24 = 0.0D;

        if (var1.boundingBox.minX <= var11 && var1.boundingBox.maxX >= var5)
        {
            var17 = true;

            if (Math.abs(var11 - var1.boundingBox.minX) < Math.abs(var5 - var1.boundingBox.maxX))
            {
                var20 = var11 - var1.boundingBox.minX + 0.01D;
            }
            else
            {
                var20 = var5 - var1.boundingBox.maxX - 0.01D;
            }
        }

        if (var1.boundingBox.minY <= var13 && var1.boundingBox.maxY >= var7)
        {
            var18 = true;

            if (Math.abs(var13 - var1.boundingBox.minY) < Math.abs(var7 - var1.boundingBox.maxY))
            {
                var22 = var13 - var1.boundingBox.minY + 0.01D;
            }
            else
            {
                var22 = var7 - var1.boundingBox.maxY - 0.01D;
            }
        }

        if (var1.boundingBox.minZ <= var15 && var1.boundingBox.maxZ >= var9)
        {
            var19 = true;

            if (Math.abs(var15 - var1.boundingBox.minZ) < Math.abs(var9 - var1.boundingBox.maxZ))
            {
                var24 = var15 - var1.boundingBox.minZ + 0.01D;
            }
            else
            {
                var24 = var9 - var1.boundingBox.maxZ - 0.01D;
            }
        }

        double var26 = var1.posX;
        double var28 = var1.posY;
        double var30 = var1.posZ;

        if (var17 && Math.abs(var20) < 0.2D && (!var18 || Math.abs(var20) < Math.abs(var22)) && (!var19 || Math.abs(var20) < Math.abs(var24)))
        {
            var26 += var20;
        }
        else if (var18 && Math.abs(var22) < 0.2D && (!var19 || Math.abs(var22) < Math.abs(var24)))
        {
            var28 += var22;
        }
        else if (var19 && Math.abs(var24) < 0.2D)
        {
            var30 += var24;
        }

        var1.setPosition(var26, var28, var30);
        var1.playerNetServerHandler.sendPacket(new Packet13PlayerLookMove(var26, var28 + 1.6200000047683716D, var28, var30, var1.rotationYaw, var1.rotationPitch, false));
    }

    public static boolean CanPlantGrowOnBlock(World var0, int var1, int var2, int var3, Block var4)
    {
        int var5 = var0.getBlockId(var1, var2, var3);
        Block var6 = Block.blocksList[var5];
        return var6 != null && var6 instanceof FCISoil ? ((FCISoil)var6).CanPlantGrowOnBlock(var0, var1, var2, var3, var4) : false;
    }

    public static boolean DoesBlockMaximizeGrowthRate(World var0, int var1, int var2, int var3, Block var4)
    {
        if (CanPlantGrowOnBlock(var0, var1, var2, var3, var4))
        {
            int var5 = var0.getBlockId(var1, var2, var3);
            Block var6 = Block.blocksList[var5];

            if (var6 != null && var6 instanceof FCISoil)
            {
                return ((FCISoil)var6).IsPlantGrowthMaximizedOnBlock(var0, var1, var2, var3, var4);
            }
        }

        return false;
    }

    public static float GetBlockGrowthMultiplier(World var0, int var1, int var2, int var3, Block var4)
    {
        int var5 = var0.getBlockId(var1, var2, var3);
        Block var6 = Block.blocksList[var5];
        return var6 != null && var6 instanceof FCISoil ? ((FCISoil)var6).GetGrowthMultiplier(var0, var1, var2, var3, var4) : 1.0F;
    }

    public static void NotifySoilOfPlantGrowth(World var0, int var1, int var2, int var3, Block var4)
    {
        int var5 = var0.getBlockId(var1, var2, var3);
        Block var6 = Block.blocksList[var5];

        if (var6 != null && var6 instanceof FCISoil)
        {
            ((FCISoil)var6).NotifyOfPlantGrowth(var0, var1, var2, var3, var4);
        }
    }

    public static void PlayPlaceSoundForBlock(World var0, int var1, int var2, int var3)
    {
        int var4 = var0.getBlockId(var1, var2, var3);
        Block var5 = Block.blocksList[var4];

        if (var5 != null)
        {
            var0.playSoundEffect((double)((float)var1 + 0.5F), (double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), var5.stepSound.getStepSound(), (var5.stepSound.getVolume() + 1.0F) / 2.0F, var5.stepSound.getPitch() * 0.8F);
        }
    }

    public static boolean IsCreatureWearingBreedingHarness(EntityCreature var0)
    {
        if (var0 instanceof EntityAnimal)
        {
            EntityAnimal var1 = (EntityAnimal)var0;
            return var1.getWearingBreedingHarness();
        }
        else
        {
            return false;
        }
    }

    public static boolean OnItemBlockUseWithBlockSubstitution(ItemStack var0, EntityPlayer var1, World var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        int var9 = var2.getBlockId(var3, var4, var5);

        if (var9 == Block.snow.blockID)
        {
            var6 = 1;
        }
        else if (var9 != Block.vine.blockID && var9 != Block.tallGrass.blockID && var9 != Block.deadBush.blockID)
        {
            FCUtilsBlockPos var10 = new FCUtilsBlockPos(var3, var4, var5);
            var10.AddFacingAsOffset(var6);
            var3 = var10.i;
            var4 = var10.j;
            var5 = var10.k;
        }

        if (var0.stackSize == 0)
        {
            return false;
        }
        else if (!var1.canPlayerEdit(var3, var4, var5))
        {
            return false;
        }
        else
        {
            Block var11 = Block.blocksList[var7];

            if (var4 == 255 && var11.blockMaterial.isSolid())
            {
                return false;
            }
            else if (var2.canPlaceEntityOnSide(var7, var3, var4, var5, false, var6, var1))
            {
                if (var2.setBlockAndMetadataWithNotify(var3, var4, var5, var7, var8))
                {
                    if (var2.getBlockId(var3, var4, var5) == var7)
                    {
                        var11.updateBlockMetadata(var2, var3, var4, var5, var6, 0.0F, 0.0F, 0.0F);
                        var11.onBlockPlacedBy(var2, var3, var4, var5, var1);
                    }

                    var2.playSoundEffect((double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), var11.stepSound.getStepSound(), (var11.stepSound.getVolume() + 1.0F) / 2.0F, var11.stepSound.getPitch() * 0.8F);
                    --var0.stackSize;
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public static boolean StandardRotateAroundJ(FCIBlock var0, World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        int var7 = StandardRotateMetadataAroundJ(var0, var6, var5);

        if (var7 != var6)
        {
            var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static int StandardRotateMetadataAroundJ(FCIBlock var0, int var1, boolean var2)
    {
        int var3 = var0.GetFacingFromMetadata(var1);
        int var4 = RotateFacingAroundJ(var3, var2);
        var1 = var0.SetFacingInMetadata(var1, var4);
        return var1;
    }

    public static Vec3 ConvertBlockFacingToVector(int var0)
    {
        Vec3 var1 = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);

        switch (var0)
        {
            case 0:
                var1.yCoord += -1.0D;
                break;

            case 1:
                ++var1.yCoord;
                break;

            case 2:
                --var1.zCoord;
                break;

            case 3:
                ++var1.zCoord;
                break;

            case 4:
                --var1.xCoord;
                break;

            default:
                ++var1.xCoord;
        }

        return var1;
    }

    public static void PlaceNonPersistantWater(World var0, int var1, int var2, int var3)
    {
        var0.setBlockAndMetadataWithNotify(var1, var2, var3, Block.waterMoving.blockID, 1);
        FlowWaterIntoBlockIfPossible(var0, var1 + 1, var2, var3, 2);
        FlowWaterIntoBlockIfPossible(var0, var1 - 1, var2, var3, 2);
        FlowWaterIntoBlockIfPossible(var0, var1, var2, var3 + 1, 2);
        FlowWaterIntoBlockIfPossible(var0, var1, var2, var3 - 1, 2);
    }

    public static void FlowWaterIntoBlockIfPossible(World var0, int var1, int var2, int var3, int var4)
    {
        if (CanWaterDisplaceBlock(var0, var1, var2, var3))
        {
            int var5 = var0.getBlockId(var1, var2, var3);

            if (var5 > 0)
            {
                Block.blocksList[var5].dropBlockAsItem(var0, var1, var2, var3, var0.getBlockMetadata(var1, var2, var3), 0);
            }

            var0.setBlockAndMetadataWithNotify(var1, var2, var3, Block.waterMoving.blockID, var4);
        }
    }

    public static boolean CanWaterDisplaceBlock(World var0, int var1, int var2, int var3)
    {
        Material var4 = var0.getBlockMaterial(var1, var2, var3);
        return var4 == Block.waterMoving.blockMaterial ? false : (var4 == Material.lava ? false : !DoesBlockStopFlow(var0, var1, var2, var3));
    }

    public static boolean DoesBlockStopFlow(World var0, int var1, int var2, int var3)
    {
        int var4 = var0.getBlockId(var1, var2, var3);

        if (var4 != Block.doorWood.blockID && var4 != Block.doorSteel.blockID && var4 != Block.signPost.blockID && var4 != Block.ladder.blockID && var4 != Block.reed.blockID)
        {
            if (var4 == 0)
            {
                return false;
            }
            else
            {
                Material var5 = Block.blocksList[var4].blockMaterial;
                return var5 == Material.portal ? true : var5.blocksMovement();
            }
        }
        else
        {
            return true;
        }
    }

    public static MovingObjectPosition GetMovingObjectPositionFromPlayerHitWaterAndLava(World var0, EntityPlayer var1, boolean var2)
    {
        float var3 = 1.0F;
        float var4 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var3;
        float var5 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var3;
        double var6 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var3;
        double var8 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var3 + 1.62D - (double)var1.yOffset;
        double var10 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var3;
        Vec3 var12 = Vec3.getVec3Pool().getVecFromPool(var6, var8, var10);
        float var13 = MathHelper.cos(-var5 * 0.01745329F - (float)Math.PI);
        float var14 = MathHelper.sin(-var5 * 0.01745329F - (float)Math.PI);
        float var15 = -MathHelper.cos(-var4 * 0.01745329F);
        float var16 = MathHelper.sin(-var4 * 0.01745329F);
        float var17 = var14 * var15;
        float var19 = var13 * var15;
        double var20 = 5.0D;
        Vec3 var22 = var12.addVector((double)var17 * var20, (double)var16 * var20, (double)var19 * var20);
        return FCUtilsWorld.RayTraceBlocksAlwaysHitWaterAndLava(var0, var12, var22, var2, !var2);
    }
}
