package net.minecraft.src;

public class FCBlockCauldron extends FCBlockCookingVessel
{
    private final int m_iContentsTextureID = 17;

    public FCBlockCauldron(int var1)
    {
        super(var1, Material.iron);
        this.blockIndexInTexture = this.m_iTopTextureID;
        this.m_iTopTextureID = 19;
        this.m_iSideTextureID = 18;
        this.m_iBottomTextureID = 19;
        this.setHardness(3.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundMetalFootstep);
        this.setBlockName("fcCauldron");
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomCauldronRenderID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityCauldron();
    }

    protected void ValidateFireUnderState(World var1, int var2, int var3, int var4)
    {
        if (!var1.isRemote)
        {
            TileEntity var5 = var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof FCTileEntityCauldron)
            {
                FCTileEntityCauldron var6 = (FCTileEntityCauldron)var5;
                var6.ValidateFireUnderType();
            }
        }
    }

    protected int GetContainerID()
    {
        return mod_FCBetterThanWolves.fcCauldronContainerID;
    }
}
