package net.minecraft.src;

public class FCBlockCrucible extends FCBlockCookingVessel
{
    private final int m_iContentsTextureID = 45;

    public FCBlockCrucible(int var1)
    {
        super(var1, Material.glass);
        this.blockIndexInTexture = this.m_iTopTextureID;
        this.m_iTopTextureID = 42;
        this.m_iSideTextureID = 43;
        this.m_iBottomTextureID = 44;
        this.setHardness(0.6F);
        this.setResistance(3.0F);
        this.setStepSound(soundGlassFootstep);
        this.setBlockName("fcCrucible");
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomCrucibleRenderID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new FCTileEntityCrucible();
    }

    protected void ValidateFireUnderState(World var1, int var2, int var3, int var4)
    {
        if (!var1.isRemote)
        {
            TileEntity var5 = var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof FCTileEntityCrucible)
            {
                FCTileEntityCrucible var6 = (FCTileEntityCrucible)var5;
                var6.ValidateFireUnderType();
            }
        }
    }

    protected int GetContainerID()
    {
        return mod_FCBetterThanWolves.fcCrucibleContainerID;
    }
}
