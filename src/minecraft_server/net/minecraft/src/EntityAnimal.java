package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
    /**
     * This is representation of a counter for reproduction progress. (Note that this is different from the inLove which
     * represent being in Love-Mode)
     */
    private int breeding = 0;
    private static final int m_iInLoveDataWatcherID = 22;
    private static final int m_iWearingBreedingHarnessDataWatcherID = 23;

    public EntityAnimal(World par1World)
    {
        super(par1World);
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        if (this.getGrowingAge() != 0)
        {
            this.resetInLove();
        }

        super.updateAITick();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.getGrowingAge() != 0)
        {
            this.resetInLove();
        }

        if (this.isInLove())
        {
            this.setInLove(this.getInLove() - 1);
            String var1 = "heart";

            if (this.getInLove() % 10 == 0)
            {
                double var2 = this.rand.nextGaussian() * 0.02D;
                double var4 = this.rand.nextGaussian() * 0.02D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle(var1, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var2, var4, var6);
            }
        }
        else
        {
            this.breeding = 0;
        }

        if (!this.worldObj.isRemote && !this.isDead)
        {
            this.CheckForLooseFood();
            this.CheckForIntersectingBreedingHarnesses();
        }

        if (this.isInLove() && this.entityToAttack != null && this.entityToAttack instanceof EntityAnimal)
        {
            EntityAnimal var8 = (EntityAnimal)this.entityToAttack;

            if (!var8.isInLove())
            {
                this.entityToAttack = null;
            }
        }
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity par1Entity, float par2)
    {
        if (par1Entity instanceof EntityPlayer)
        {
            if (par2 < 3.0F)
            {
                double var3 = par1Entity.posX - this.posX;
                double var5 = par1Entity.posZ - this.posZ;
                this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
                this.hasAttacked = true;
            }

            EntityPlayer var7 = (EntityPlayer)par1Entity;

            if (var7.getCurrentEquippedItem() == null || !this.isWheat(var7.getCurrentEquippedItem()))
            {
                this.entityToAttack = null;
            }
        }
        else if (par1Entity instanceof EntityAnimal)
        {
            EntityAnimal var8 = (EntityAnimal)par1Entity;

            if (this.getGrowingAge() > 0 && var8.getGrowingAge() < 0)
            {
                if ((double)par2 < 2.5D)
                {
                    this.hasAttacked = true;
                }
            }
            else if (this.isInLove() && var8.isInLove())
            {
                if (var8.entityToAttack == null)
                {
                    var8.entityToAttack = this;
                }

                if (var8.entityToAttack == this && (double)par2 < 3.5D)
                {
                    this.setInLove(this.getInLove() + 1);
                    var8.setInLove(var8.getInLove() + 1);
                    ++this.breeding;

                    if (this.breeding % 4 == 0)
                    {
                        this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D);
                    }

                    if (this.breeding == 60)
                    {
                        this.procreate((EntityAnimal)par1Entity);
                    }
                }
                else
                {
                    this.breeding = 0;
                }
            }
            else
            {
                this.breeding = 0;
                this.entityToAttack = null;
            }
        }
    }

    /**
     * Creates a baby animal according to the animal type of the target at the actual position and spawns 'love'
     * particles.
     */
    private void procreate(EntityAnimal par1EntityAnimal)
    {
        EntityAnimal var2 = this.spawnBabyAnimal(par1EntityAnimal);

        if (var2 != null)
        {
            this.setGrowingAge(6000);
            par1EntityAnimal.setGrowingAge(6000);
            this.resetInLove();
            this.breeding = 0;
            this.entityToAttack = null;
            par1EntityAnimal.entityToAttack = null;
            par1EntityAnimal.breeding = 0;
            par1EntityAnimal.resetInLove();
            var2.setGrowingAge(-24000);

            if (this.getWearingBreedingHarness())
            {
                var2.setLocationAndAngles((this.posX + par1EntityAnimal.posX) / 2.0D, (this.posY + par1EntityAnimal.posY) / 2.0D, (this.posZ + par1EntityAnimal.posZ) / 2.0D, this.rotationYaw, this.rotationPitch);
            }
            else
            {
                var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            }

            for (int var3 = 0; var3 < 7; ++var3)
            {
                double var4 = this.rand.nextGaussian() * 0.02D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                double var8 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var4, var6, var8);
            }

            this.worldObj.spawnEntityInWorld(var2);
            this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.worldObj.playAuxSFX(2222, MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ), 0);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public abstract EntityAnimal spawnBabyAnimal(EntityAnimal var1);

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        this.fleeingTick = 60;
        this.entityToAttack = null;
        this.resetInLove();
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float getBlockPathWeight(int par1, int par2, int par3)
    {
        return this.worldObj.getBlockId(par1, par2 - 1, par3) == Block.grass.blockID ? 10.0F : this.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("InLove", this.getInLove());
        par1NBTTagCompound.setBoolean("BreedingHarness", this.getWearingBreedingHarness());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setInLove(par1NBTTagCompound.getInteger("InLove"));

        if (par1NBTTagCompound.hasKey("BreedingHarness"))
        {
            this.setWearingBreedingHarness(par1NBTTagCompound.getBoolean("BreedingHarness"));
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        if (this.fleeingTick > 0)
        {
            return null;
        }
        else
        {
            float var1 = 8.0F;
            EntityAnimal var11;

            if (this.isInLove())
            {
                double var10 = 0.0D;
                var11 = null;
                List var5 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand((double)var1, (double)var1, (double)var1));

                for (int var6 = 0; var6 < var5.size(); ++var6)
                {
                    EntityAnimal var7 = (EntityAnimal)var5.get(var6);

                    if (var7 != this && var7.isInLove())
                    {
                        double var8 = this.getDistanceSqToEntity(var7);

                        if (var11 == null || var8 < var10)
                        {
                            var10 = var8;
                            var11 = var7;
                        }
                    }
                }

                return var11;
            }
            else
            {
                List var2;
                Iterator var3;

                if (this.getGrowingAge() == 0)
                {
                    var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand((double)var1, (double)var1, (double)var1));
                    var3 = var2.iterator();

                    while (var3.hasNext())
                    {
                        EntityPlayer var4 = (EntityPlayer)var3.next();

                        if (var4.getCurrentEquippedItem() != null && this.isWheat(var4.getCurrentEquippedItem()))
                        {
                            return var4;
                        }
                    }
                }
                else if (this.getGrowingAge() > 0)
                {
                    var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand((double)var1, (double)var1, (double)var1));
                    var3 = var2.iterator();

                    while (var3.hasNext())
                    {
                        var11 = (EntityAnimal)var3.next();

                        if (var11 != this && var11.getGrowingAge() < 0)
                        {
                            return var11;
                        }
                    }
                }

                return null;
            }
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere();
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 120;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
    {
        return 1 + this.worldObj.rand.nextInt(3);
    }

    /**
     * Checks if the parameter is an wheat item.
     */
    public boolean isWheat(ItemStack par1ItemStack)
    {
        return par1ItemStack.itemID == Item.wheat.shiftedIndex;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

        if (var2 != null && this.isWheat(var2) && this.getGrowingAge() == 0)
        {
            if (!par1EntityPlayer.capabilities.isCreativeMode)
            {
                --var2.stackSize;

                if (var2.stackSize <= 0)
                {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }

            this.setInLove(600);
            this.entityToAttack = null;

            for (int var3 = 0; var3 < 7; ++var3)
            {
                double var4 = this.rand.nextGaussian() * 0.02D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                double var8 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var4, var6, var8);
            }

            return true;
        }
        else
        {
            return super.interact(par1EntityPlayer);
        }
    }

    /**
     * Returns if the entity is currently in 'love mode'.
     */
    public boolean isInLove()
    {
        return this.getInLove() > 0;
    }

    public void resetInLove()
    {
        this.setInLove(0);
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
        return par1EntityAnimal == this ? false : (par1EntityAnimal.getClass() != this.getClass() ? false : this.isInLove() && par1EntityAnimal.isInLove());
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(22, new Integer(0));
        this.dataWatcher.addObject(23, Byte.valueOf((byte)0));
    }

    public int getInLove()
    {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public void setInLove(int var1)
    {
        this.dataWatcher.updateObject(22, Integer.valueOf(var1));
    }

    public boolean getWearingBreedingHarness()
    {
        return this.dataWatcher.getWatchableObjectByte(23) > 0;
    }

    public void setWearingBreedingHarness(boolean var1)
    {
        Byte var2 = Byte.valueOf((byte)0);

        if (var1)
        {
            var2 = Byte.valueOf((byte)1);
        }

        this.dataWatcher.updateObject(23, Byte.valueOf(var2.byteValue()));
    }

    public void CheckForLooseFood()
    {
        if (this.getGrowingAge() == 0 && !this.isInLove())
        {
            List var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.posX - 2.5D, this.posY - 1.0D, this.posZ - 2.5D, this.posX + 2.5D, this.posY + 1.0D, this.posZ + 2.5D));

            if (!var1.isEmpty())
            {
                for (int var2 = 0; var2 < var1.size(); ++var2)
                {
                    EntityItem var3 = (EntityItem)var1.get(var2);

                    if (var3.delayBeforeCanPickup == 0 && !var3.isDead)
                    {
                        ItemStack var4 = var3.item;

                        if (var4 != null && this.isWheat(var4))
                        {
                            --var4.stackSize;

                            if (var4.stackSize <= 0)
                            {
                                var3.setDead();
                            }

                            this.setInLove(600);
                            this.entityToAttack = null;

                            for (int var5 = 0; var5 < 7; ++var5)
                            {
                                double var6 = this.rand.nextGaussian() * 0.02D;
                                double var8 = this.rand.nextGaussian() * 0.02D;
                                double var10 = this.rand.nextGaussian() * 0.02D;
                                this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var6, var8, var10);
                            }

                            this.worldObj.playSoundAtEntity(this, "random.pop", 0.25F, ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void CheckForIntersectingBreedingHarnesses()
    {
        if (this.getWearingBreedingHarness())
        {
            AxisAlignedBB var1 = this.boundingBox.copy();
            var1.contract(0.1D, 0.1D, 0.1D);
            List var2 = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, var1);

            if (!var2.isEmpty())
            {
                for (int var3 = 0; var3 < var2.size(); ++var3)
                {
                    EntityAnimal var4 = (EntityAnimal)var2.get(var3);

                    if (var4 != this && var4.getWearingBreedingHarness() && !var4.isDead)
                    {
                        this.attackEntityFrom(DamageSource.inWall, 1);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump()
    {
        if (this.isChild())
        {
            this.motionY = 0.21D;
            this.isAirBorne = true;
        }
        else
        {
            super.jump();
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource var1)
    {
        super.onDeath(var1);

        if (!this.worldObj.isRemote && this.getWearingBreedingHarness())
        {
            this.dropItem(mod_FCBetterThanWolves.fcBreedingHarness.shiftedIndex, 1);
        }
    }

    protected void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (this.getWearingBreedingHarness())
        {
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
        }
    }

    public void CheckForScrollDrop() {}
}
