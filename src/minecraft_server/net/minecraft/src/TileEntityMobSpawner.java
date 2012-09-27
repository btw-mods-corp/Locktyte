package net.minecraft.src;

import java.util.Iterator;

public class TileEntityMobSpawner extends TileEntity
{
    /** The stored delay before a new spawn. */
    public int delay = -1;

    /**
     * The string ID of the mobs being spawned from this spawner. Defaults to pig, apparently.
     */
    private String mobID = "Pig";

    /** The extra NBT data to add to spawned entities */
    private NBTTagCompound spawnerTags = null;
    public double yaw;
    public double yaw2 = 0.0D;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;
    private int spawnCount = 4;

    public TileEntityMobSpawner()
    {
        this.delay = 20;
    }

    public void setMobID(String par1Str)
    {
        this.mobID = par1Str;
    }

    /**
     * Returns true if there is a player in range (using World.getClosestPlayer)
     */
    public boolean anyPlayerInRange()
    {
        return this.worldObj.getClosestPlayer((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, 16.0D) != null;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        int var1;
        int var3;

        if (!this.worldObj.isRemote && this.worldObj.rand.nextInt(1200) == 0)
        {
            var1 = this.worldObj.rand.nextInt(9);
            int var2 = this.worldObj.rand.nextInt(6);
            var3 = this.worldObj.rand.nextInt(9);
            int var4 = this.xCoord - 4 + var1;
            int var5 = this.yCoord - 1 + var2;
            int var6 = this.zCoord - 4 + var3;

            if (this.worldObj.getBlockId(var4, var5, var6) == Block.cobblestone.blockID)
            {
                this.worldObj.setBlockWithNotify(var4, var5, var6, Block.cobblestoneMossy.blockID);
            }
        }

        if (this.anyPlayerInRange())
        {
            if (this.worldObj.isRemote)
            {
                double var11 = (double)((float)this.xCoord + this.worldObj.rand.nextFloat());
                double var13 = (double)((float)this.yCoord + this.worldObj.rand.nextFloat());
                double var15 = (double)((float)this.zCoord + this.worldObj.rand.nextFloat());
                this.worldObj.spawnParticle("smoke", var11, var13, var15, 0.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("flame", var11, var13, var15, 0.0D, 0.0D, 0.0D);
                this.yaw2 = this.yaw % 360.0D;
                this.yaw += 4.545454502105713D;
            }
            else
            {
                if (this.delay == -1)
                {
                    this.updateDelay();
                }

                if (this.delay > 0)
                {
                    --this.delay;
                    return;
                }

                for (var1 = 0; var1 < this.spawnCount; ++var1)
                {
                    Entity var12 = EntityList.createEntityByName(this.mobID, this.worldObj);

                    if (var12 == null)
                    {
                        return;
                    }

                    var3 = this.worldObj.getEntitiesWithinAABB(var12.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand(8.0D, 4.0D, 8.0D)).size();

                    if (var3 >= 6)
                    {
                        this.updateDelay();
                        return;
                    }

                    if (var12 != null)
                    {
                        double var14 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
                        double var16 = (double)(this.yCoord + this.worldObj.rand.nextInt(3) - 1);
                        double var8 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
                        EntityLiving var10 = var12 instanceof EntityLiving ? (EntityLiving)var12 : null;
                        var12.setLocationAndAngles(var14, var16, var8, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);

                        if (var10 == null || var10.getCanSpawnHere())
                        {
                            this.writeNBTTagsTo(var12);
                            this.worldObj.spawnEntityInWorld(var12);
                            this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);

                            if (var10 != null)
                            {
                                var10.spawnExplosionParticle();
                            }

                            this.updateDelay();
                        }
                    }
                }
            }

            super.updateEntity();
        }
    }

    public void writeNBTTagsTo(Entity par1Entity)
    {
        if (this.spawnerTags != null)
        {
            NBTTagCompound var2 = new NBTTagCompound();
            par1Entity.addEntityID(var2);
            Iterator var4 = this.spawnerTags.getTags().iterator();

            while (var4.hasNext())
            {
                NBTBase var3 = (NBTBase)var4.next();
                var2.setTag(var3.getName(), var3.copy());
            }

            par1Entity.readFromNBT(var2);
        }
    }

    /**
     * Sets the delay before a new spawn (base delay of 200 + random number up to 600).
     */
    private void updateDelay()
    {
        this.delay = this.minSpawnDelay + this.worldObj.rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.mobID = par1NBTTagCompound.getString("EntityId");
        this.delay = par1NBTTagCompound.getShort("Delay");

        if (par1NBTTagCompound.hasKey("SpawnData"))
        {
            this.spawnerTags = par1NBTTagCompound.getCompoundTag("SpawnData");
        }
        else
        {
            this.spawnerTags = null;
        }

        if (par1NBTTagCompound.hasKey("MinSpawnDelay"))
        {
            this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
            this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
            this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("EntityId", this.mobID);
        par1NBTTagCompound.setShort("Delay", (short)this.delay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);

        if (this.spawnerTags != null)
        {
            par1NBTTagCompound.setCompoundTag("SpawnData", this.spawnerTags);
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
}
