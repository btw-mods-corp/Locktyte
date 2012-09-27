package net.minecraft.src;

import forge.ITextureProvider;

public class FCItemBattleAxe extends ItemSword implements ITextureProvider
{
    private final int m_iWeaponDamage = 8;
    private final int m_iEnchantability = 0;
    private final int maxUses = 2250;

    protected FCItemBattleAxe(int var1)
    {
        super(var1, EnumToolMaterial.EMERALD);
        this.setMaxDamage(2250);
        this.setIconIndex(30);
        this.setItemName("fcBattleAxe");
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack var1, Block var2)
    {
        return var2.blockID == Block.web.blockID ? 15.0F : ((FCItemRefinedAxe)((FCItemRefinedAxe)mod_FCBetterThanWolves.fcRefinedAxe)).getStrVsBlock(var1, var2);
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity var1)
    {
        return 8;
    }

    public boolean onBlockDestroyed(ItemStack var1, World var2, int var3, int var4, int var5, int var6, EntityLiving var7)
    {
        if ((double)Block.blocksList[var3].getBlockHardness(var2, var4, var5, var6) != 0.0D)
        {
            var1.damageItem(1, var7);
        }

        return true;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block var1)
    {
        return var1.blockID == Block.web.blockID ? true : ((FCItemRefinedAxe)((FCItemRefinedAxe)mod_FCBetterThanWolves.fcRefinedAxe)).canHarvestBlock(var1);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwitems01.png";
    }
}
