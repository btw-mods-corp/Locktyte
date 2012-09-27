package net.minecraft.src;

public interface FCIMechanicalDevice
{
    boolean CanOutputMechanicalPower();

    boolean CanInputMechanicalPower();

    boolean IsInputtingMechanicalPower(World var1, int var2, int var3, int var4);

    boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4);

    void Overpower(World var1, int var2, int var3, int var4);
}
