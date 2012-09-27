package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
    /** Initialises the CrashReport to have current Minecraft Version. */
    final CrashReport minecraftCrashReports;

    CallableMinecraftVersion(CrashReport par1CrashReport)
    {
        this.minecraftCrashReports = par1CrashReport;
    }

    /**
     * The current version of Minecraft
     */
    public String minecraftVersion()
    {
        return "1.3.2";
    }

    public Object call()
    {
        return this.minecraftVersion();
    }
}
