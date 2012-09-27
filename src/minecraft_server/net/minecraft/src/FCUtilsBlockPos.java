package net.minecraft.src;

public class FCUtilsBlockPos
{
    public int i;
    public int j;
    public int k;

    public FCUtilsBlockPos()
    {
        this.i = this.j = this.k = 0;
    }

    public FCUtilsBlockPos(int var1, int var2, int var3)
    {
        this.i = var1;
        this.j = var2;
        this.k = var3;
    }

    public FCUtilsBlockPos(int var1)
    {
        this.i = this.j = this.k = 0;
        this.AddFacingAsOffset(var1);
    }

    public void AddFacingAsOffset(int var1)
    {
        switch (var1)
        {
            case 0:
                this.j += -1;
                break;

            case 1:
                ++this.j;
                break;

            case 2:
                --this.k;
                break;

            case 3:
                ++this.k;
                break;

            case 4:
                --this.i;
                break;

            default:
                ++this.i;
        }
    }

    public void Invert()
    {
        this.i = -this.i;
        this.j = -this.j;
        this.k = -this.k;
    }

    public void AddPos(FCUtilsBlockPos var1)
    {
        this.i += var1.i;
        this.j += var1.j;
        this.k += var1.k;
    }
}
