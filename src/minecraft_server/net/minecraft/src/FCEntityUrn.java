package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

public class FCEntityUrn extends Entity implements FCIEntityPacketHandler
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public int shake;
    private EntityLiving field_20051_g;
    private int field_20050_h;
    private int field_20049_i;
    public int m_iItemShiftedIndex;

    public FCEntityUrn(World var1)
    {
        super(var1);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.shake = 0;
        this.field_20049_i = 0;
        this.field_20050_h = 0;
        this.setSize(0.25F, 0.25F);
        this.yOffset = 0.0F;
        this.m_iItemShiftedIndex = 0;
    }

    public FCEntityUrn(World var1, int var2)
    {
        this(var1);
        this.m_iItemShiftedIndex = var2;
    }

    public FCEntityUrn(World var1, EntityLiving var2, int var3)
    {
        this(var1, var3);
        this.field_20051_g = var2;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        float var4 = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
        this.SetUrnHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
    }

    public FCEntityUrn(World var1, double var2, double var4, double var6, int var8)
    {
        this(var1, var8);
        this.setPosition(var2, var4, var6);
    }

    protected void entityInit() {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        var1.setShort("xTile", (short)this.xTile);
        var1.setShort("yTile", (short)this.yTile);
        var1.setShort("zTile", (short)this.zTile);
        var1.setByte("inTile", (byte)this.inTile);
        var1.setByte("shake", (byte)this.shake);
        var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        var1.setInteger("m_iItemShiftedIndex", this.m_iItemShiftedIndex);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        this.xTile = var1.getShort("xTile");
        this.yTile = var1.getShort("yTile");
        this.zTile = var1.getShort("zTile");
        this.inTile = var1.getByte("inTile") & 255;
        this.shake = var1.getByte("shake") & 255;
        this.inGround = var1.getByte("inGround") == 1;
        this.m_iItemShiftedIndex = var1.getInteger("m_iItemShiftedIndex");
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        if (this.shake > 0)
        {
            --this.shake;
        }

        if (this.inGround)
        {
            int var1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

            if (var1 == this.inTile)
            {
                ++this.field_20050_h;

                if (this.field_20050_h == 1200)
                {
                    this.setDead();
                }

                return;
            }

            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
            this.field_20050_h = 0;
            this.field_20049_i = 0;
        }
        else
        {
            ++this.field_20049_i;
        }

        Vec3 var15 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 var2 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var15, var2);
        var15 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        var2 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (var3 != null)
        {
            var2 = Vec3.createVectorHelper(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote)
        {
            Entity var4 = null;
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double var6 = 0.0D;

            for (int var8 = 0; var8 < var5.size(); ++var8)
            {
                Entity var9 = (Entity)var5.get(var8);

                if (var9.canBeCollidedWith() && (var9 != this.field_20051_g || this.field_20049_i >= 5))
                {
                    float var10 = 0.3F;
                    AxisAlignedBB var11 = var9.boundingBox.expand((double)var10, (double)var10, (double)var10);
                    MovingObjectPosition var12 = var11.calculateIntercept(var15, var2);

                    if (var12 != null)
                    {
                        double var13 = var15.distanceTo(var12.hitVec);

                        if (var13 < var6 || var6 == 0.0D)
                        {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

            if (var4 != null)
            {
                var3 = new MovingObjectPosition(var4);
            }
        }

        if (var3 != null)
        {
            if (var3.entityHit != null && !var3.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.field_20051_g), 0))
            {
                ;
            }

            if (!this.worldObj.isRemote)
            {
                if (this.m_iItemShiftedIndex == mod_FCBetterThanWolves.fcSoulUrn.shiftedIndex)
                {
                    this.worldObj.playAuxSFX(2225, MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 0);
                    this.SpawnGhast();
                }

                this.worldObj.playSoundAtEntity(this, "random.glass", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            }

            for (int var16 = 0; var16 < 8; ++var16)
            {
                this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }

            this.setDead();
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var18) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float var17 = 0.99F;
        float var19 = 0.03F;

        if (this.isInWater())
        {
            for (int var7 = 0; var7 < 4; ++var7)
            {
                float var20 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var20, this.posY - this.motionY * (double)var20, this.posZ - this.motionZ * (double)var20, this.motionX, this.motionY, this.motionZ);
            }

            var17 = 0.8F;
        }

        this.motionX *= (double)var17;
        this.motionY *= (double)var17;
        this.motionZ *= (double)var17;
        this.motionY -= (double)var19;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public int GetTrackerViewDistance()
    {
        return 64;
    }

    public int GetTrackerUpdateFrequency()
    {
        return 10;
    }

    public boolean GetTrackMotion()
    {
        return true;
    }

    public boolean ShouldServerTreatAsOversized()
    {
        return false;
    }

    public Packet GetSpawnPacketForThisEntity()
    {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(var1);

        try
        {
            var2.writeInt(7);
            var2.writeInt(this.entityId);
            var2.writeInt(MathHelper.floor_double(this.posX * 32.0D));
            var2.writeInt(MathHelper.floor_double(this.posY * 32.0D));
            var2.writeInt(MathHelper.floor_double(this.posZ * 32.0D));
            var2.writeInt(this.m_iItemShiftedIndex);
            var2.writeByte((byte)((int)(this.motionX * 128.0D)));
            var2.writeByte((byte)((int)(this.motionY * 128.0D)));
            var2.writeByte((byte)((int)(this.motionZ * 128.0D)));
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }

        return new Packet250CustomPayload("FC|SE", var1.toByteArray());
    }

    public void SetUrnHeading(double var1, double var3, double var5, float var7, float var8)
    {
        float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
        var1 /= (double)var9;
        var3 /= (double)var9;
        var5 /= (double)var9;
        var1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
        var1 *= (double)var7;
        var3 *= (double)var7;
        var5 *= (double)var7;
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;
        float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)var10) * 180.0D / Math.PI);
        this.field_20050_h = 0;
    }

    private boolean SpawnGhast()
    {
        EntityGhast var1 = new EntityGhast(this.worldObj);

        for (int var2 = 0; var2 < 200; ++var2)
        {
            double var3 = this.posX + (this.worldObj.rand.nextDouble() - 0.5D) * 20.0D;
            double var5 = this.posY + (this.worldObj.rand.nextDouble() - 0.5D) * 20.0D;
            double var7 = this.posZ + (this.worldObj.rand.nextDouble() - 0.5D) * 20.0D;
            var1.setLocationAndAngles(var3, var5, var7, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);

            if (var1.getCanSpawnHere())
            {
                this.worldObj.spawnEntityInWorld(var1);
                return true;
            }
        }

        return false;
    }
}
