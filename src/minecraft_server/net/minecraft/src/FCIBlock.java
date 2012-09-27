package net.minecraft.src;

public interface FCIBlock
{
    int GetFacing(IBlockAccess var1, int var2, int var3, int var4);

    void SetFacing(World var1, int var2, int var3, int var4, int var5);

    int GetFacingFromMetadata(int var1);

    int SetFacingInMetadata(int var1, int var2);

    boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4);

    boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4);

    boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4);

    void RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5);

    int RotateMetadataAroundJAxis(int var1, boolean var2);

    boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5);
}
