package net.minecraft.src;

import java.util.List;

public class EntityChicken extends EntityAnimal
{
    public boolean field_70885_d = false;
    public float field_70886_e = 0.0F;
    public float destPos = 0.0F;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    /** The time until the next egg is spawned. */
    public int timeUntilNextEgg;
    public boolean m_bIsFed;

    public EntityChicken(World par1World)
    {
        super(par1World);
        this.texture = "/mob/chicken.png";
        this.setSize(0.3F, 0.7F);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        float var2 = 0.25F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMate(this, var2));
        this.tasks.addTask(3, new FCEntityAIMultiTempt(this, 0.25F, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
        this.tasks.addTask(5, new EntityAIWander(this, var2));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.m_bIsFed = false;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 4;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F)
        {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F)
        {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F)
        {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;

        if (!this.isChild() && !this.worldObj.isRemote && --this.timeUntilNextEgg <= 0 && this.m_bIsFed)
        {
            this.worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Item.egg.shiftedIndex, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
            this.m_bIsFed = false;
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1) {}

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.chicken";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.chickenhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.chickenhurt";
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.feather.shiftedIndex;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.feather.shiftedIndex, 1);
        }

        if (this.isBurning())
        {
            this.dropItem(Item.chickenCooked.shiftedIndex, 1);
        }
        else
        {
            this.dropItem(Item.chickenRaw.shiftedIndex, 1);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        return new EntityChicken(this.worldObj);
    }

    /**
     * Checks if the parameter is an wheat item.
     */
    public boolean isWheat(ItemStack var1)
    {
        return var1.itemID == Item.seeds.shiftedIndex || var1.itemID == mod_FCBetterThanWolves.fcHempSeeds.shiftedIndex || var1.itemID == Item.pumpkinSeeds.shiftedIndex || var1.itemID == Item.melonSeeds.shiftedIndex;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        return false;
    }

    public void CheckForLooseFood()
    {
        if (this.getGrowingAge() == 0 && !this.m_bIsFed)
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

                            this.m_bIsFed = true;
                            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
                            this.worldObj.playSoundAtEntity(this, "random.pop", 0.25F, ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), this.rand.nextFloat() * 0.2F + 1.5F);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setBoolean("fcFed", this.m_bIsFed);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);

        if (var1.hasKey("fcFed"))
        {
            this.m_bIsFed = var1.getBoolean("fcFed");
        }
        else
        {
            this.m_bIsFed = false;
        }
    }
}
