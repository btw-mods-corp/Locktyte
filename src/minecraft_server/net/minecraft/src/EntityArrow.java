package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityArrow extends Entity
{
    protected int xTile = -1;
    protected int yTile = -1;
    protected int zTile = -1;
    protected int inTile = 0;
    protected int inData = 0;
    protected boolean inGround = false;

    /** 1 if the player can pick up the arrow */
    public int canBePickedUp = 0;

    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake = 0;

    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir = 0;
    private double damage = 2.0D;

    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;

    public EntityArrow(World par1World)
    {
        super(par1World);
        this.setSize(0.5F, 0.5F);
    }

    public EntityArrow(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.setSize(0.5F, 0.5F);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0F;
    }

    public EntityArrow(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
    {
        super(par1World);
        this.shootingEntity = par2EntityLiving;

        if (par2EntityLiving instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.posY = par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
        double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
        double var8 = par3EntityLiving.posY + (double)par3EntityLiving.getEyeHeight() - 0.699999988079071D - this.posY;
        double var10 = par3EntityLiving.posZ - par2EntityLiving.posZ;
        double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var10 * var10);

        if (var12 >= 1.0E-7D)
        {
            float var14 = (float)(Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
            float var15 = (float)(-(Math.atan2(var8, var12) * 180.0D / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            this.setLocationAndAngles(par2EntityLiving.posX + var16, this.posY, par2EntityLiving.posZ + var18, var14, var15);
            this.yOffset = 0.0F;
            float var20 = (float)var12 * 0.2F;
            this.setArrowHeading(var6, var8 + (double)var20, var10, par4, par5);
        }
    }

    public EntityArrow(World par1World, EntityLiving par2EntityLiving, float par3)
    {
        super(par1World);
        this.shootingEntity = par2EntityLiving;

        if (par2EntityLiving instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setArrowHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Uses the provided coordinates as a heading and determines the velocity from it with the set force and random
     * variance. Args: x, y, z, force, forceVariation
     */
    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI);
        }

        int var16 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

        if (var16 > 0)
        {
            Block.blocksList[var16].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB var2 = Block.blocksList[var16].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (var2 != null && var2.isVecInside(Vec3.getVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            int var18 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            int var19 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

            if (var18 == this.inTile && (var19 == this.inData || var18 == mod_FCBetterThanWolves.fcBlockDetector.blockID))
            {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            ++this.ticksInAir;
            Vec3 var17 = Vec3.getVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 var3 = Vec3.getVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var4 = this.worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
            var17 = Vec3.getVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            var3 = Vec3.getVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (var4 != null)
            {
                var3 = Vec3.getVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
            }

            Entity var5 = null;
            List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double var7 = 0.0D;
            Iterator var9 = var6.iterator();
            float var11;
            AxisAlignedBB var12;

            while (var9.hasNext())
            {
                Entity var10 = (Entity)var9.next();

                if (var10.canBeCollidedWith() && (var10 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    var11 = 0.3F;
                    var12 = var10.boundingBox.expand((double)var11, (double)var11, (double)var11);
                    MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);

                    if (var13 != null)
                    {
                        double var14 = var17.distanceTo(var13.hitVec);

                        if (var14 < var7 || var7 == 0.0D)
                        {
                            var5 = var10;
                            var7 = var14;
                        }
                    }
                }
            }

            if (var5 != null)
            {
                var4 = new MovingObjectPosition(var5);
            }

            float var22;

            if (var4 != null)
            {
                if (var4.entityHit != null)
                {
                    var22 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int var21 = MathHelper.ceiling_double_int((double)var22 * this.damage);

                    if (this.getIsCritical())
                    {
                        var21 += this.rand.nextInt(var21 / 2 + 2);
                    }

                    var12 = null;
                    DamageSource var23;

                    if (this.shootingEntity == null)
                    {
                        var23 = DamageSource.causeArrowDamage(this, this);
                    }
                    else
                    {
                        var23 = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning())
                    {
                        var4.entityHit.setFire(5);
                    }

                    if (var4.entityHit.attackEntityFrom(var23, var21))
                    {
                        if (var4.entityHit.attackEntityFrom(var23, (int)((float)var21 * this.GetDamageMultiplier())))
                        {
                            if (var4.entityHit instanceof EntityLiving)
                            {
                                ++((EntityLiving)var4.entityHit).arrowHitTempCounter;

                                if (this.knockbackStrength > 0)
                                {
                                    float var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                    if (var26 > 0.0F)
                                    {
                                        var4.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)var26, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)var26);
                                    }
                                }
                            }

                            this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                            this.setDead();
                        }
                        else
                        {
                            this.motionX *= -0.10000000149011612D;
                            this.motionY *= -0.10000000149011612D;
                            this.motionZ *= -0.10000000149011612D;
                            this.rotationYaw += 180.0F;
                            this.prevRotationYaw += 180.0F;
                            this.ticksInAir = 0;
                        }
                    }
                }
                else
                {
                    this.xTile = var4.blockX;
                    this.yTile = var4.blockY;
                    this.zTile = var4.blockZ;
                    this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (double)((float)(var4.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(var4.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(var4.hitVec.zCoord - this.posZ));
                    var22 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)var22 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)var22 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)var22 * 0.05000000074505806D;
                    this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                }
            }

            if (this.getIsCritical())
            {
                for (int var20 = 0; var20 < 4; ++var20)
                {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)var20 / 4.0D, this.posY + this.motionY * (double)var20 / 4.0D, this.posZ + this.motionZ * (double)var20 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            var22 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var22) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
            var11 = 0.99F;
            float var24 = 0.05F;

            if (this.isInWater())
            {
                for (int var25 = 0; var25 < 4; ++var25)
                {
                    float var27 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var27, this.posY - this.motionY * (double)var27, this.posZ - this.motionZ * (double)var27, this.motionX, this.motionY, this.motionZ);
                }

                var11 = 0.8F;
            }

            this.motionX *= (double)var11;
            this.motionY *= (double)var11;
            this.motionZ *= (double)var11;
            this.motionY -= (double)var24;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();

            if (this.inGround && this.inTile == mod_FCBetterThanWolves.fcBlockDetector.blockID)
            {
                mod_FCBetterThanWolves.fcBlockDetector.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
            }
            else if (this.inGround && this.inTile == mod_FCBetterThanWolves.fcVase.blockID)
            {
                ((FCBlockVase)((FCBlockVase)mod_FCBetterThanWolves.fcVase)).BreakVase(this.worldObj, this.xTile, this.yTile, this.zTile);
            }
            else if (this.inGround && this.inTile == mod_FCBetterThanWolves.fcHopper.blockID)
            {
                this.CollideWithHopper();
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
        par1NBTTagCompound.setByte("inData", (byte)this.inData);
        par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
        par1NBTTagCompound.setDouble("damage", this.damage);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
        this.inData = par1NBTTagCompound.getByte("inData") & 255;
        this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

        if (par1NBTTagCompound.hasKey("damage"))
        {
            this.damage = par1NBTTagCompound.getDouble("damage");
        }

        if (par1NBTTagCompound.hasKey("pickup"))
        {
            this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
        }
        else if (par1NBTTagCompound.hasKey("player"))
        {
            this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
        {
            boolean var2 = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !this.AddArrowToPlayerInv(par1EntityPlayer))
            {
                var2 = false;
            }

            if (var2)
            {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    public void setDamage(double par1)
    {
        this.damage = par1;
    }

    public double getDamage()
    {
        return this.damage;
    }

    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void setKnockbackStrength(int par1)
    {
        this.knockbackStrength = par1;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void setIsCritical(boolean par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical()
    {
        byte var1 = this.dataWatcher.getWatchableObjectByte(16);
        return (var1 & 1) != 0;
    }

    protected float GetDamageMultiplier()
    {
        return 1.0F;
    }

    protected void CollideWithHopper()
    {
        ((FCBlockHopper)((FCBlockHopper)mod_FCBetterThanWolves.fcHopper)).OnEntityArrowCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
    }

    protected boolean AddArrowToPlayerInv(EntityPlayer var1)
    {
        return var1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1));
    }
}
