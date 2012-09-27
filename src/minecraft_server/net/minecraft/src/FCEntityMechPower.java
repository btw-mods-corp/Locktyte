package net.minecraft.src;

public abstract class FCEntityMechPower extends Entity implements FCIEntityPacketHandler
{
    private static final int m_iRotationSpeedDataWatcherID = 22;
    public boolean m_bIAligned;
    public float m_fRotation;
    public int m_iCurrentDamage;
    public int m_iTimeSinceHit;
    public int m_iRockDirection;
    protected boolean m_bProvidingPower;
    protected int m_iFullUpdateTickCount;

    public FCEntityMechPower(World var1)
    {
        super(var1);
        this.m_bIAligned = true;
        this.m_bProvidingPower = false;
        this.m_iCurrentDamage = 0;
        this.m_iTimeSinceHit = 0;
        this.m_iRockDirection = 1;
        this.m_fRotation = 0.0F;
        this.m_iFullUpdateTickCount = 0;
        this.preventEntitySpawning = true;
        this.setSize(this.GetWidth(), this.GetHeight());
        this.yOffset = this.height / 2.0F;
    }

    public FCEntityMechPower(World var1, double var2, double var4, double var6, boolean var8)
    {
        this(var1);
        this.setPosition(var2, var4, var6);
        this.m_bIAligned = var8;
        this.AlignBoundingBoxWithAxis();
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(22, new Integer(0));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity var1)
    {
        return var1.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void moveEntity(double var1, double var3, double var5)
    {
        if (!this.isDead)
        {
            this.DestroyWithDrop();
        }
    }

    /**
     * Sets entity to burn for x amount of seconds, cannot lower amount of existing fire.
     */
    public void setFire(int var1) {}

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        if (this.isDead)
        {
            return true;
        }
        else
        {
            this.m_iCurrentDamage += var2 * 5;
            this.m_iRockDirection = -this.m_iRockDirection;
            this.m_iTimeSinceHit = 10;

            if (!this.worldObj.isRemote)
            {
                Entity var3 = var1.getEntity();

                if (var3 instanceof EntityPlayer && ((EntityPlayer)var3).capabilities.isCreativeMode)
                {
                    this.DestroyWithDrop();
                }
                else
                {
                    this.setBeenAttacked();

                    if (this.m_iCurrentDamage > this.GetMaxDamage())
                    {
                        this.DestroyWithDrop();
                    }
                }
            }

            return true;
        }
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (this.m_bProvidingPower)
        {
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.posY);
            int var3 = MathHelper.floor_double(this.posZ);
            int var4 = this.worldObj.getBlockId(var1, var2, var3);

            if (var4 == mod_FCBetterThanWolves.fcAxleBlock.blockID)
            {
                FCBlockAxle var5 = (FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock;
                int var6 = var5.GetAxisAlignment(this.worldObj, var1, var2, var3);
                this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
                this.worldObj.setBlockWithNotify(var1, var2, var3, var5.blockID);
                var5.SetAxisAlignment(this.worldObj, var1, var2, var3, var6);
            }
        }

        super.setDead();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (!this.isDead)
        {
            if (!this.worldObj.isRemote)
            {
                --this.m_iFullUpdateTickCount;

                if (this.m_iFullUpdateTickCount <= 0)
                {
                    this.m_iFullUpdateTickCount = this.GetTicksPerFullUpdate();
                    this.OnFullUpdateServer();
                }
            }

            this.m_fRotation += this.getRotationSpeed();

            if (this.m_fRotation > 360.0F)
            {
                this.m_fRotation -= 360.0F;
            }
            else if (this.m_fRotation < -360.0F)
            {
                this.m_fRotation += 360.0F;
            }

            if (this.m_iTimeSinceHit > 0)
            {
                --this.m_iTimeSinceHit;
            }

            if (this.m_iCurrentDamage > 0)
            {
                --this.m_iCurrentDamage;
            }
        }
    }

    public int GetTrackerViewDistance()
    {
        return 160;
    }

    public int GetTrackerUpdateFrequency()
    {
        return 3;
    }

    public boolean GetTrackMotion()
    {
        return false;
    }

    public boolean ShouldServerTreatAsOversized()
    {
        return true;
    }

    public abstract float GetWidth();

    public abstract float GetHeight();

    public abstract float GetDepth();

    protected abstract int GetMaxDamage();

    protected abstract int GetTicksPerFullUpdate();

    protected abstract void DestroyWithDrop();

    protected abstract boolean ValidateAreaAroundDevice();

    protected abstract float ComputeRotation(int var1, int var2, int var3);

    public float getRotationSpeed()
    {
        return (float)this.dataWatcher.getWatchableObjectInt(22) / 100.0F;
    }

    public void setRotationSpeed(float var1)
    {
        this.dataWatcher.updateObject(22, Integer.valueOf((int)(var1 * 100.0F)));
    }

    public int getRotationSpeedScaled()
    {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public void setRotationSpeedScaled(int var1)
    {
        this.dataWatcher.updateObject(22, Integer.valueOf(var1));
    }

    protected void AlignBoundingBoxWithAxis()
    {
        if (this.m_bIAligned)
        {
            this.boundingBox.setBounds(this.posX - (double)(this.GetDepth() * 0.5F), this.posY - (double)(this.GetHeight() * 0.5F), this.posZ - (double)(this.GetWidth() * 0.5F), this.posX + (double)(this.GetDepth() * 0.5F), this.posY + (double)(this.GetHeight() * 0.5F), this.posZ + (double)(this.GetWidth() * 0.5F));
        }
        else
        {
            this.boundingBox.setBounds(this.posX - (double)(this.GetWidth() * 0.5F), this.posY - (double)(this.GetHeight() * 0.5F), this.posZ - (double)(this.GetDepth() * 0.5F), this.posX + (double)(this.GetWidth() * 0.5F), this.posY + (double)(this.GetHeight() * 0.5F), this.posZ + (double)(this.GetDepth() * 0.5F));
        }
    }

    protected void OnFullUpdateServer()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);
        int var4 = this.worldObj.getBlockId(var1, var2, var3);

        if (var4 != mod_FCBetterThanWolves.fcAxleBlock.blockID)
        {
            this.DestroyWithDrop();
        }
        else
        {
            int var5 = ((FCBlockAxle)((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock)).GetAxisAlignment(this.worldObj, var1, var2, var3);

            if (var5 != 0 && (var5 != 1 || !this.m_bIAligned) && (var5 != 2 || this.m_bIAligned))
            {
                if (!this.ValidateAreaAroundDevice())
                {
                    this.DestroyWithDrop();
                }
                else
                {
                    if (!this.m_bProvidingPower)
                    {
                        if (((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock).GetPowerLevel(this.worldObj, var1, var2, var3) > 0)
                        {
                            this.DestroyWithDrop();
                            return;
                        }
                    }
                    else if (((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock).GetPowerLevel(this.worldObj, var1, var2, var3) != 3)
                    {
                        ((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock).SetPowerLevel(this.worldObj, var1, var2, var3, 3);
                    }

                    this.setRotationSpeed(this.ComputeRotation(var1, var2, var3));
                    float var6 = this.getRotationSpeed();

                    if (var6 <= 0.01F && var6 >= -0.01F)
                    {
                        if (this.m_bProvidingPower)
                        {
                            this.m_bProvidingPower = false;
                            ((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock).SetPowerLevel(this.worldObj, var1, var2, var3, 0);
                        }
                    }
                    else if (!this.m_bProvidingPower)
                    {
                        this.m_bProvidingPower = true;
                        ((FCBlockAxle)mod_FCBetterThanWolves.fcAxleBlock).SetPowerLevel(this.worldObj, var1, var2, var3, 3);
                    }
                }
            }
            else
            {
                this.DestroyWithDrop();
            }
        }
    }
}
