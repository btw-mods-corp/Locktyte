package net.minecraft.src;

import java.util.Iterator;

public class EntityItem extends Entity
{
    /** The item stack of this EntityItem. */
    public ItemStack item;

    /**
     * The age of this EntityItem (used to animate it up and down as well as expire it)
     */
    public int age = 0;
    public int delayBeforeCanPickup;

    /** The health of this EntityItem. (For example, damage for tools) */
    private int health = 5;

    /** The EntityItem's random initial float height. */
    public float hoverStart = (float)(Math.random() * Math.PI * 2.0D);
    private static final int m_iNumVisualItemsDataWatcherID = 22;

    public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
    {
        super(par1World);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(par2, par4, par6);
        this.item = par8ItemStack;
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.setNumVisualItems(this.CalcNumVisualItems(par8ItemStack));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public EntityItem(World par1World)
    {
        super(par1World);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(22, new Byte((byte)0));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.delayBeforeCanPickup > 0)
        {
            --this.delayBeforeCanPickup;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.04D;

        if (mod_FCBetterThanWolves.IsHardcoreBuoyEnabled(this.worldObj))
        {
            byte var1 = 10;
            double var2 = 0.0D;
            double var4 = 0.1D;

            for (int var6 = 0; var6 < var1; ++var6)
            {
                double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var6 + 0) * 0.375D + var4;
                double var9 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var6 + 1) * 0.375D + var4;
                AxisAlignedBB var11 = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.boundingBox.minX, var7, this.boundingBox.minZ, this.boundingBox.maxX, var9, this.boundingBox.maxZ);

                if (!this.worldObj.isAABBInMaterial(var11, Material.water))
                {
                    break;
                }

                var2 += 1.0D / (double)var1;
            }

            if (var2 > 0.001D)
            {
                if (!this.IsInUndertow())
                {
                    float var18 = this.item.getItem().GetBuoyancy(this.item.getItemDamage()) + 1.0F;
                    this.motionY += 0.04D * (double)var18 * var2;
                }

                this.motionX *= 0.8999999761581421D;
                this.motionY *= 0.8999999761581421D;
                this.motionZ *= 0.8999999761581421D;
            }
        }

        this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        boolean var12 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;

        if (var12)
        {
            if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava)
            {
                this.motionY = 0.20000000298023224D;
                this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                this.worldObj.playSoundAtEntity(this, "random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
            }

            if (!this.worldObj.isRemote)
            {
                Iterator var3 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();

                while (var3.hasNext())
                {
                    EntityItem var13 = (EntityItem)var3.next();
                    this.func_70289_a(var13);
                }
            }
        }

        if (!this.worldObj.isRemote)
        {
            byte var14 = this.getNumVisualItems();
            byte var15 = this.CalcNumVisualItems(this.item);

            if (var14 != var15)
            {
                this.setNumVisualItems(var15);
            }
        }

        float var16 = 0.98F;

        if (this.onGround)
        {
            var16 = 0.5880001F;
            int var17 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

            if (var17 > 0)
            {
                var16 = Block.blocksList[var17].slipperiness * 0.98F;
            }
        }

        this.motionX *= (double)var16;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)var16;

        if (this.onGround)
        {
            this.motionY *= -0.5D;
        }

        ++this.age;

        if (this.age >= 6000)
        {
            this.setDead();
        }
    }

    public boolean func_70289_a(EntityItem par1EntityItem)
    {
        if (par1EntityItem == this)
        {
            return false;
        }
        else if (par1EntityItem.isEntityAlive() && this.isEntityAlive())
        {
            if (par1EntityItem.item.getItem() != this.item.getItem())
            {
                return false;
            }
            else if (par1EntityItem.item.getItem().getHasSubtypes() && par1EntityItem.item.getItemDamage() != this.item.getItemDamage())
            {
                return false;
            }
            else if (par1EntityItem.item.stackSize < this.item.stackSize)
            {
                return par1EntityItem.func_70289_a(this);
            }
            else if (par1EntityItem.item.stackSize + this.item.stackSize > par1EntityItem.item.getMaxStackSize())
            {
                return false;
            }
            else
            {
                par1EntityItem.item.stackSize += this.item.stackSize;
                par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
                par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
                this.setDead();
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public void func_70288_d()
    {
        this.age = 4800;
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    /**
     * Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    protected void dealFireDamage(int par1)
    {
        this.attackEntityFrom(DamageSource.inFire, par1);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        this.setBeenAttacked();
        this.health -= par2;

        if (this.health <= 0)
        {
            this.setDead();
        }

        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("Health", (short)((byte)this.health));
        par1NBTTagCompound.setShort("Age", (short)this.age);

        if (this.item != null)
        {
            par1NBTTagCompound.setCompoundTag("Item", this.item.writeToNBT(new NBTTagCompound()));
        }

        par1NBTTagCompound.setByte("fcVisCount", this.getNumVisualItems());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.health = par1NBTTagCompound.getShort("Health") & 255;
        this.age = par1NBTTagCompound.getShort("Age");
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        this.item = ItemStack.loadItemStackFromNBT(var2);

        if (this.item == null)
        {
            this.setDead();
        }
        else if (var2.hasKey("fcVisCount"))
        {
            this.setNumVisualItems(var2.getByte("fcVisCount"));
        }
        else
        {
            this.setNumVisualItems(this.CalcNumVisualItems(this.item));
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote)
        {
            int var2 = this.item.stackSize;

            if (this.delayBeforeCanPickup == 0 && par1EntityPlayer.inventory.addItemStackToInventory(this.item))
            {
                if (this.item.itemID == Block.wood.blockID)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }

                if (this.item.itemID == Item.leather.shiftedIndex)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.killCow);
                }

                if (this.item.itemID == Item.diamond.shiftedIndex)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
                }

                if (this.item.itemID == Item.blazeRod.shiftedIndex)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
                }

                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, var2);

                if (this.item.stackSize <= 0)
                {
                    this.setDead();
                }
            }
        }
    }

    /**
     * Gets the username of the entity.
     */
    public String getEntityName()
    {
        return StatCollector.translateToLocal("item." + this.item.getItemName());
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    /**
     * Checks for block collisions, and calls the associated onBlockCollided method for the collided block.
     */
    protected void doBlockCollisions()
    {
        int var1 = MathHelper.floor_double(this.boundingBox.minX + 0.001D);
        int var2 = MathHelper.floor_double(this.boundingBox.minY - 0.01D);
        int var3 = MathHelper.floor_double(this.boundingBox.minZ + 0.001D);
        int var4 = MathHelper.floor_double(this.boundingBox.maxX - 0.001D);
        int var5 = MathHelper.floor_double(this.boundingBox.maxY - 0.001D);
        int var6 = MathHelper.floor_double(this.boundingBox.maxZ - 0.001D);

        if (this.worldObj.checkChunksExist(var1, var2, var3, var4, var5, var6))
        {
            for (int var7 = var1; var7 <= var4; ++var7)
            {
                for (int var8 = var2; var8 <= var5; ++var8)
                {
                    for (int var9 = var3; var9 <= var6; ++var9)
                    {
                        int var10 = this.worldObj.getBlockId(var7, var8, var9);

                        if (var10 > 0)
                        {
                            Block.blocksList[var10].onEntityCollidedWithBlock(this.worldObj, var7, var8, var9, this);
                        }
                    }
                }
            }
        }
    }

    public byte getNumVisualItems()
    {
        return this.dataWatcher.getWatchableObjectByte(22);
    }

    public void setNumVisualItems(byte var1)
    {
        this.dataWatcher.updateObject(22, Byte.valueOf(var1));
    }

    public byte CalcNumVisualItems(ItemStack var1)
    {
        int var2 = var1.stackSize;
        byte var3 = 1;

        if (var2 > 1)
        {
            if (var2 > 5)
            {
                if (var2 > 20)
                {
                    var3 = 4;
                }
                else
                {
                    var3 = 3;
                }
            }
            else
            {
                var3 = 2;
            }
        }

        return var3;
    }

    private boolean IsInUndertow()
    {
        int var1 = MathHelper.floor_double(this.boundingBox.minX);
        int var2 = MathHelper.floor_double(this.boundingBox.maxX + 1.0D);
        int var3 = MathHelper.floor_double(this.boundingBox.minY);
        int var4 = MathHelper.floor_double(this.boundingBox.maxY + 1.0D);
        int var5 = MathHelper.floor_double(this.boundingBox.minZ);
        int var6 = MathHelper.floor_double(this.boundingBox.maxZ + 1.0D);

        for (int var7 = var1; var7 < var2; ++var7)
        {
            for (int var8 = var3; var8 < var4; ++var8)
            {
                for (int var9 = var5; var9 < var6; ++var9)
                {
                    if (this.DoesBlockHaveUndertow(var7, var8, var9))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean DoesBlockHaveUndertow(int var1, int var2, int var3)
    {
        int var4 = this.worldObj.getBlockId(var1, var2, var3);

        if (var4 == Block.waterMoving.blockID || var4 == Block.waterStill.blockID)
        {
            int var5 = this.worldObj.getBlockMetadata(var1, var2, var3);

            if (var5 >= 8)
            {
                return true;
            }

            var4 = this.worldObj.getBlockId(var1, var2 - 1, var3);

            if (var4 == Block.waterMoving.blockID || var4 == Block.waterStill.blockID)
            {
                var5 = this.worldObj.getBlockMetadata(var1, var2 - 1, var3);

                if (var5 >= 8)
                {
                    return true;
                }
            }

            var4 = this.worldObj.getBlockId(var1, var2 + 1, var3);

            if (var4 == Block.waterMoving.blockID || var4 == Block.waterStill.blockID)
            {
                var5 = this.worldObj.getBlockMetadata(var1, var2 + 1, var3);

                if (var5 >= 8)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
