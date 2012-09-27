package net.minecraft.src;

public class ItemHoe extends Item
{
    protected EnumToolMaterial theToolMaterial;

    public ItemHoe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.theToolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }
        else
        {
            int var11 = par3World.getBlockId(par4, par5, par6);
            int var12 = par3World.getBlockId(par4, par5 + 1, par6);

            if ((par7 == 0 || var12 != 0 || var11 != Block.grass.blockID) && var11 != Block.dirt.blockID)
            {
                return false;
            }
            else
            {
                Block var13 = Block.tilledField;
                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

                if (par3World.isRemote)
                {
                    return true;
                }
                else
                {
                    this.CheckForSeedDropOnItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7);
                    par3World.setBlockWithNotify(par4, par5, par6, var13.blockID);
                    par1ItemStack.damageItem(1, par2EntityPlayer);
                    return true;
                }
            }
        }
    }

    public String func_77842_f()
    {
        return this.theToolMaterial.toString();
    }

    public void CheckForSeedDropOnItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7)
    {
        int var8 = var3.getBlockId(var4, var5, var6);

        if (var8 == Block.grass.blockID)
        {
            byte var9 = 0;
            int var10 = var1.itemID;

            if (var10 == Item.hoeWood.shiftedIndex)
            {
                var9 = 1;
            }
            else if (var10 == Item.hoeStone.shiftedIndex)
            {
                var9 = 2;
            }
            else if (var10 == Item.hoeSteel.shiftedIndex)
            {
                var9 = 4;
            }
            else if (var10 == Item.hoeDiamond.shiftedIndex)
            {
                var9 = 8;
            }
            else if (var10 == Item.hoeGold.shiftedIndex)
            {
                var9 = 16;
            }

            if (var3.rand.nextInt(100) < var9)
            {
                float var11 = 0.7F;
                float var12 = var3.rand.nextFloat() * var11 + (1.0F - var11) * 0.5F;
                float var13 = 1.2F;
                float var14 = var3.rand.nextFloat() * var11 + (1.0F - var11) * 0.5F;
                EntityItem var15 = new EntityItem(var3, (double)((float)var4 + var12), (double)((float)var5 + var13), (double)((float)var6 + var14), new ItemStack(mod_FCBetterThanWolves.fcHempSeeds));
                var15.delayBeforeCanPickup = 10;
                var3.spawnEntityInWorld(var15);
            }
        }
    }
}
