package net.minecraft.src;

import java.util.List;

public class EntityWolf extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;

    /** true is the wolf is wet else false */
    private boolean isShaking;
    private boolean field_70928_h;

    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private static final int m_iIsFedDataWatcherID = 24;

    public EntityWolf(World par1World)
    {
        super(par1World);
        this.texture = "/mob/wolf.png";
        this.setSize(0.6F, 0.8F);
        this.moveSpeed = 0.3F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 16.0F, 200, false));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLiving par1EntityLiving)
    {
        super.setAttackTarget(par1EntityLiving);

        if (par1EntityLiving instanceof EntityPlayer)
        {
            this.setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
    }

    public int getMaxHealth()
    {
        return !this.isTamed() ? 8 : 20;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(24, new Byte((byte)0));
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
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
        par1NBTTagCompound.setBoolean("bIsFed", this.isFed());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));

        if (par1NBTTagCompound.hasKey("bIsFed"))
        {
            this.setFed(par1NBTTagCompound.getBoolean("bIsFed"));
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.isAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) != 0 ? "mob.wolf.bark" : (this.isTamed() && (this.dataWatcher.getWatchableObjectInt(18) < 10 || !this.isFed()) ? "mob.wolf.whine" : "mob.wolf.panting"));
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.wolf.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return !this.worldObj.isRemote ? (this.isBurning() ? mod_FCBetterThanWolves.fcWolfCooked.shiftedIndex : mod_FCBetterThanWolves.fcWolfRaw.shiftedIndex) : -1;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround)
        {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte)8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv())
        {
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        }
        else
        {
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
        }

        if (this.func_70922_bv())
        {
            this.numTicksToChaseTarget = 10;
        }

        if (this.isWet())
        {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else if ((this.isShaking || this.field_70928_h) && this.field_70928_h)
        {
            if (this.timeWolfIsShaking == 0.0F)
            {
                this.worldObj.playSoundAtEntity(this, "mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F)
            {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F)
            {
                float var1 = (float)this.boundingBox.minY;
                int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int var3 = 0; var3 < var2; ++var3)
                {
                    float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle("splash", this.posX + (double)var4, (double)(var1 + 0.8F), this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
                }
            }
        }

        if (!this.worldObj.isRemote && !this.isDead && this.isFed())
        {
            int var6 = 1;

            if (this.IsDarkEnoughToAffectShitting())
            {
                var6 *= 2;
            }

            if (this.worldObj.rand.nextInt(24000) < var6)
            {
                this.AttemptToShit();
            }
        }
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        Entity var3 = par1DamageSource.getEntity();
        this.aiSit.setSitting(false);

        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
        {
            par2 = (par2 + 1) / 2;
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        byte var2 = (byte)(this.isTamed() ? 4 : 2);
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

        if (this.isTamed())
        {
            if (var2 != null && Item.itemsList[var2.itemID] instanceof ItemFood)
            {
                ItemFood var3 = (ItemFood)Item.itemsList[var2.itemID];

                if (var3.isWolfsFavoriteMeat() && var2.itemID != Item.rottenFlesh.shiftedIndex && (this.dataWatcher.getWatchableObjectInt(18) < 20 || !this.isFed()))
                {
                    if (!par1EntityPlayer.capabilities.isCreativeMode)
                    {
                        --var2.stackSize;
                    }

                    this.heal(var3.getHealAmount());

                    if (var2.stackSize <= 0)
                    {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }

                    this.setFed(true);
                    return true;
                }

                if (var3.shiftedIndex == mod_FCBetterThanWolves.fcWolfRaw.shiftedIndex || var3.shiftedIndex == mod_FCBetterThanWolves.fcWolfCooked.shiftedIndex)
                {
                    this.worldObj.playSoundAtEntity(this, "mob.wolf.growl", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                    this.setAngry(true);
                    this.setTamed(false);
                    this.setOwner("");
                    this.setAttackTarget(par1EntityPlayer);
                }
            }

            if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isWheat(var2))
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
            }
        }
        else if (var2 != null && var2.itemID == Item.bone.shiftedIndex && !this.isAngry())
        {
            if (!par1EntityPlayer.capabilities.isCreativeMode)
            {
                --var2.stackSize;
            }

            if (var2.stackSize <= 0)
            {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            }

            if (!this.worldObj.isRemote)
            {
                if (this.rand.nextInt(3) == 0)
                {
                    this.setTamed(true);
                    this.setPathToEntity((PathEntity)null);
                    this.setAttackTarget((EntityLiving)null);
                    this.aiSit.setSitting(true);
                    this.setEntityHealth(20);
                    this.setOwner(par1EntityPlayer.username);
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        return super.interact(par1EntityPlayer);
    }

    /**
     * Checks if the parameter is an wheat item.
     */
    public boolean isWheat(ItemStack par1ItemStack)
    {
        return par1ItemStack == null ? false : (!(Item.itemsList[par1ItemStack.itemID] instanceof ItemFood) ? false : ((ItemFood)Item.itemsList[par1ItemStack.itemID]).isWolfsFavoriteMeat() && par1ItemStack.itemID != Item.rottenFlesh.shiftedIndex);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
            this.setSitting(false);
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        EntityWolf var2 = new EntityWolf(this.worldObj);
        var2.setOwner(this.getOwnerName());
        var2.setTamed(true);
        return var2;
    }

    public void func_70918_i(boolean par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(19);

        if (par1)
        {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
        }
        else
        {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
        }
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
        if (par1EntityAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(par1EntityAnimal instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf var2 = (EntityWolf)par1EntityAnimal;
            return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
        }
    }

    public boolean func_70922_bv()
    {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    public boolean isFed()
    {
        return this.dataWatcher.getWatchableObjectByte(24) > 0;
    }

    public void setFed(boolean var1)
    {
        Byte var2 = Byte.valueOf((byte)0);

        if (var1)
        {
            var2 = Byte.valueOf((byte)1);
        }

        this.dataWatcher.updateObject(24, Byte.valueOf(var2.byteValue()));
    }

    public boolean IsDarkEnoughToAffectShitting()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);
        int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
        return var4 <= 5;
    }

    public void CheckForLooseFood()
    {
        if (!this.isFed() || this.dataWatcher.getWatchableObjectInt(18) < 20)
        {
            List var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.posX - 2.5D, this.posY - 1.0D, this.posZ - 2.5D, this.posX + 2.5D, this.posY + 1.0D, this.posZ + 2.5D));

            if (!var1.isEmpty())
            {
                for (int var2 = 0; var2 < var1.size(); ++var2)
                {
                    EntityItem var3 = (EntityItem)var1.get(var2);

                    if (var3.delayBeforeCanPickup == 0 && !var3.isDead)
                    {
                        int var4 = var3.item.itemID;
                        Item var5 = Item.itemsList[var4];

                        if (var5 instanceof ItemFood && ((ItemFood)var5).isWolfsFavoriteMeat() && var4 != Item.rottenFlesh.shiftedIndex)
                        {
                            this.worldObj.playSoundAtEntity(this, "random.pop", 0.25F, ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            this.heal(((ItemFood)var5).getHealAmount());
                            this.setFed(true);
                            --var3.item.stackSize;

                            if (var3.item.stackSize <= 0)
                            {
                                var3.setDead();
                            }

                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean AttemptToShit()
    {
        float var1 = MathHelper.sin(this.rotationYawHead / 180.0F * (float)Math.PI);
        float var2 = -MathHelper.cos(this.rotationYawHead / 180.0F * (float)Math.PI);
        double var3 = this.posX + (double)var1;
        double var5 = this.posY + 0.25D;
        double var7 = this.posZ + (double)var2;
        int var9 = MathHelper.floor_double(var3);
        int var10 = MathHelper.floor_double(var5);
        int var11 = MathHelper.floor_double(var7);

        if (!this.IsPathToBlockOpenToShitting(var9, var10, var11))
        {
            return false;
        }
        else
        {
            EntityItem var12 = new EntityItem(this.worldObj, var3, var5, var7, new ItemStack(mod_FCBetterThanWolves.fcDung));
            float var13 = 0.05F;
            var12.motionX = (double)(var1 * 10.0F * var13);
            var12.motionZ = (double)(var2 * 10.0F * var13);
            var12.motionY = (double)((float)this.worldObj.rand.nextGaussian() * var13 + 0.2F);
            var12.delayBeforeCanPickup = 10;
            this.worldObj.spawnEntityInWorld(var12);
            this.worldObj.playSoundAtEntity(this, "random.explode", 0.2F, 1.25F);
            this.worldObj.playSoundAtEntity(this, "mob.wolf.growl", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

            for (int var14 = 0; var14 < 5; ++var14)
            {
                double var15 = this.posX + (double)(var1 * 0.5F) + this.worldObj.rand.nextDouble() * 0.25D;
                double var17 = this.posY + this.worldObj.rand.nextDouble() * 0.5D + 0.25D;
                double var19 = this.posZ + (double)(var2 * 0.5F) + this.worldObj.rand.nextDouble() * 0.25D;
                this.worldObj.spawnParticle("smoke", var15, var17, var19, 0.0D, 0.0D, 0.0D);
            }

            this.setFed(false);
            return true;
        }
    }

    private boolean IsPathToBlockOpenToShitting(int var1, int var2, int var3)
    {
        if (!this.IsBlockOpenToShitting(var1, var2, var3))
        {
            return false;
        }
        else
        {
            int var4 = MathHelper.floor_double(this.posX);
            int var5 = MathHelper.floor_double(this.posZ);
            int var6 = var1 - var4;
            int var7 = var3 - var5;
            return var6 == 0 || var7 == 0 || this.IsBlockOpenToShitting(var4, var2, var3) || this.IsBlockOpenToShitting(var1, var2, var5);
        }
    }

    private boolean IsBlockOpenToShitting(int var1, int var2, int var3)
    {
        Block var4 = Block.blocksList[this.worldObj.getBlockId(var1, var2, var3)];

        if (var4 != null && (var4 == Block.waterMoving || var4 == Block.waterStill || var4 == Block.lavaMoving || var4 == Block.lavaStill || var4 == Block.fire || var4.blockMaterial.isGroundCover() || var4 == mod_FCBetterThanWolves.fcBlockDetectorLogic || var4 == mod_FCBetterThanWolves.fcBlockDetectorGlowingLogic || var4 == mod_FCBetterThanWolves.fcStokedFire))
        {
            var4 = null;
        }

        return var4 == null;
    }
}
