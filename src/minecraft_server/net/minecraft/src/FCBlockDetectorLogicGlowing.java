package net.minecraft.src;

import forge.ITextureProvider;

public class FCBlockDetectorLogicGlowing extends FCBlockDetectorLogic implements FCIBlock, ITextureProvider
{
    private static final int m_iLitFaceTextureID = 115;
    private static final float m_fLitFaceThickness = 0.01F;

    public FCBlockDetectorLogicGlowing(int var1)
    {
        super(var1);
        this.setLightValue(1.0F);
        this.setRequiresSelfNotify();
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_FCBetterThanWolves.iCustomDetectorLogicGlowingRenderID;
    }

    public String getTextureFile()
    {
        return "/btwmodtex/btwterrain01.png";
    }

    public void SetBlockBoundsToRenderLitFace(int var1)
    {
        switch (var1)
        {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
                break;

            case 1:
                this.setBlockBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
                break;

            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
                break;

            case 4:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
                break;

            default:
                this.setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
