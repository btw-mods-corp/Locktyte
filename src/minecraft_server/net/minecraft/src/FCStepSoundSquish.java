package net.minecraft.src;

class FCStepSoundSquish extends StepSound
{
    FCStepSoundSquish(String var1, float var2, float var3)
    {
        super(var1, var2, var3);
    }

    /**
     * Used when a entity walks over, or otherwise interacts with the block.
     */
    public String getStepSound()
    {
        return "mob.slimeattack";
    }
}
