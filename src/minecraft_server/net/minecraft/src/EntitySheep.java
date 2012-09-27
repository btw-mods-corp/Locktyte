package net.minecraft.src;

import java.util.Random;

public class EntitySheep extends EntityAnimal
{
    /**
     * Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece.
     */
    public static final float[][] fleeceColorTable = new float[][] {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int sheepTimer;

    /** The eat grass AI task for this mob. */
    private EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
    private int m_iOriginalFleeceColor;

    public EntitySheep(World par1World)
    {
        super(par1World);
        this.texture = "/mob/sheep.png";
        this.setSize(0.9F, 1.3F);
        float var2 = 0.23F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAIMate(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.shiftedIndex, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.m_iOriginalFleeceColor = 0;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return !this.getWearingBreedingHarness();
    }

    protected void updateAITasks()
    {
        this.sheepTimer = this.aiEatGrass.func_75362_f();
        super.updateAITasks();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.worldObj.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    public int getMaxHealth()
    {
        return 8;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        if (!this.getSheared())
        {
            this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 0.0F);
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Block.cloth.blockID;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

        if (var2 != null && var2.itemID == Item.shears.shiftedIndex && !this.getSheared() && !this.isChild())
        {
            if (!this.worldObj.isRemote)
            {
                this.setSheared(true);
                int var3 = 1 + this.rand.nextInt(3);

                for (int var4 = 0; var4 < var3; ++var4)
                {
                    EntityItem var5 = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0F);
                    var5.motionY += (double)(this.rand.nextFloat() * 0.05F);
                    var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                    var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                }
            }

            var2.damageItem(1, par1EntityPlayer);
        }

        return super.interact(par1EntityPlayer);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
        par1NBTTagCompound.setByte("Color", (byte)this.getFleeceColor());
        par1NBTTagCompound.setByte("fcOrgClr", (byte)this.m_iOriginalFleeceColor);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(par1NBTTagCompound.getByte("Color"));

        if (par1NBTTagCompound.hasKey("fcOrgClr"))
        {
            this.m_iOriginalFleeceColor = par1NBTTagCompound.getByte("fcOrgClr");
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.sheep";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.sheep";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.sheep";
    }

    public int getFleeceColor()
    {
        return this.dataWatcher.getWatchableObjectByte(16) & 15;
    }

    public void setFleeceColor(int par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | par1 & 15)));
        this.m_iOriginalFleeceColor = par1;
    }

    public void setSuperficialFleeceColor(int var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | var1 & 15)));
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 16)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -17)));
        }
    }

    /**
     * This method is called when a sheep spawns in the world to select the color of sheep fleece.
     */
    public static int getRandomFleeceColor(Random par0Random)
    {
        int var1 = par0Random.nextInt(100);
        return var1 < 5 ? 15 : (var1 < 10 ? 7 : (var1 < 15 ? 8 : (var1 < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 3 : (par0Random.nextInt(500) == 0 ? 5 : (par0Random.nextInt(500) != 0 ? 0 : 6))))));
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        EntitySheep var2 = (EntitySheep)par1EntityAnimal;
        EntitySheep var3 = new EntitySheep(this.worldObj);
        int var4 = this.rand.nextInt(100);
        int var5;

        if (var4 == 0 && mod_FCBetterThanWolves.fcEnableHardcoreSheep)
        {
            var5 = this.getMutantColor(this, var2);
            var3.setFleeceColor(var5);
        }
        else if (var4 <= 3 && mod_FCBetterThanWolves.fcEnableHardcoreSheep)
        {
            byte var6 = 15;
            var3.setFleeceColor(var6);
        }
        else if (var4 <= 23 && mod_FCBetterThanWolves.fcEnableHardcoreSheep)
        {
            var5 = this.blendParentColors(this, var2);
            var3.setFleeceColor(var5);
        }
        else if (this.rand.nextBoolean())
        {
            if (!mod_FCBetterThanWolves.fcEnableHardcoreSheep)
            {
                var3.setFleeceColor(this.getFleeceColor());
            }
            else
            {
                var3.setFleeceColor(this.m_iOriginalFleeceColor);
            }
        }
        else if (!mod_FCBetterThanWolves.fcEnableHardcoreSheep)
        {
            var3.setFleeceColor(var2.getFleeceColor());
        }
        else
        {
            var3.setFleeceColor(var2.m_iOriginalFleeceColor);
        }

        return var3;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    public void eatGrassBonus()
    {
        if (!this.getWearingBreedingHarness())
        {
            if (mod_FCBetterThanWolves.fcEnableHardcoreSheep && this.getSheared())
            {
                this.setFleeceColor(this.m_iOriginalFleeceColor);
            }

            this.setSheared(false);

            if (this.isChild())
            {
                int var1 = this.getGrowingAge() + 1200;

                if (var1 > 0)
                {
                    var1 = 0;
                }

                this.setGrowingAge(var1);
            }
        }
    }

    public int blendParentColors(EntitySheep var1, EntitySheep var2)
    {
        int var3;
        int var4;
        int var5;
        var3 = BlockCloth.getBlockFromDye(var1.m_iOriginalFleeceColor);
        var4 = BlockCloth.getBlockFromDye(var2.m_iOriginalFleeceColor);
        var5 = var3;
        label131:

        switch (var3)
        {
            case 0:
                switch (var4)
                {
                    case 7:
                        var5 = 8;

                    case 8:
                    case 11:
                    case 14:
                    default:
                        break label131;

                    case 9:
                        var5 = 1;
                        break label131;

                    case 10:
                        var5 = 2;
                        break label131;

                    case 12:
                        var5 = 4;
                        break label131;

                    case 13:
                        var5 = 5;
                        break label131;

                    case 15:
                        var5 = 7;
                        break label131;
                }

            case 1:
                switch (var4)
                {
                    case 2:
                        var5 = 11;
                        break label131;

                    case 3:
                        var5 = 14;
                        break label131;

                    case 4:
                        var5 = 5;

                    case 5:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 13:
                    case 14:
                    default:
                        break label131;

                    case 6:
                        var5 = 3;
                        break label131;

                    case 11:
                        var5 = 14;
                        break label131;

                    case 12:
                        var5 = 13;
                        break label131;

                    case 15:
                        var5 = 9;
                        break label131;
                }

            case 2:
                switch (var4)
                {
                    case 1:
                        var5 = 11;
                        break label131;

                    case 4:
                        var5 = 6;
                        break label131;

                    case 14:
                        var5 = 11;
                        break label131;

                    case 15:
                        var5 = 10;

                    default:
                        break label131;
                }

            case 3:
                switch (var4)
                {
                    case 1:
                        var5 = 14;

                    default:
                        break label131;
                }

            case 4:
                switch (var4)
                {
                    case 1:
                        var5 = 5;
                        break label131;

                    case 2:
                        var5 = 6;

                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                    case 13:
                    case 14:
                    default:
                        break label131;

                    case 9:
                        var5 = 13;
                        break label131;

                    case 11:
                        var5 = 2;
                        break label131;

                    case 15:
                        var5 = 12;
                        break label131;
                }

            case 5:
                switch (var4)
                {
                    case 15:
                        var5 = 13;

                    default:
                        break label131;
                }

            case 6:
                switch (var4)
                {
                    case 1:
                        var5 = 3;

                    default:
                        break label131;
                }

            case 7:
                switch (var4)
                {
                    case 0:
                        var5 = 8;

                    default:
                        break label131;
                }

            case 8:
                switch (var4)
                {
                    case 15:
                        var5 = 7;

                    default:
                        break label131;
                }

            case 9:
                switch (var4)
                {
                    case 0:
                        var5 = 1;
                        break label131;

                    case 4:
                        var5 = 13;

                    default:
                        break label131;
                }

            case 10:
                switch (var4)
                {
                    case 0:
                        var5 = 2;

                    default:
                        break label131;
                }

            case 11:
                switch (var4)
                {
                    case 1:
                        var5 = 14;
                        break label131;

                    case 4:
                        var5 = 2;
                        break label131;

                    case 12:
                        var5 = 10;

                    default:
                        break label131;
                }

            case 12:
                switch (var4)
                {
                    case 0:
                        var5 = 4;
                        break label131;

                    case 1:
                        var5 = 13;
                        break label131;

                    case 9:
                        var5 = 13;
                        break label131;

                    case 11:
                        var5 = 13;

                    default:
                        break label131;
                }

            case 13:
                switch (var4)
                {
                    case 0:
                        var5 = 5;
                }

            case 14:
            default:
                break;

            case 15:
                switch (var4)
                {
                    case 0:
                        var5 = 8;
                        break;

                    case 1:
                        var5 = 9;
                        break;

                    case 2:
                        var5 = 10;

                    case 3:
                    case 6:
                    case 7:
                    default:
                        break;

                    case 4:
                        var5 = 12;
                        break;

                    case 5:
                        var5 = 13;
                        break;

                    case 8:
                        var5 = 7;
                }
        }

        if (var5 == var3 && this.rand.nextBoolean())
        {
            var5 = var4;
        }

        return BlockCloth.getBlockFromDye(var5);
    }

    public int getMutantColor(EntitySheep var1, EntitySheep var2)
    {
        int var3 = this.rand.nextInt(3);

        switch (var3)
        {
            case 0:
                return 3;

            case 1:
                return 5;

            default:
                return 6;
        }
    }
}
