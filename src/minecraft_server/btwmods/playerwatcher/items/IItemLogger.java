package btwmods.playerwatcher.items;

import btwmods.playerwatcher.PlayerWatcher;
import btwmods.playerwatcher.items.ContainerType;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface IItemLogger {
	public void containerPlaced(EntityPlayer player, Container container, World world, int x, int y, int z);
	public void containerOpened(EntityPlayer player, Container container, World world, int x, int y, int z);
	public void containerClosed(EntityPlayer player, Container container);
	public void containerDestroyed(EntityPlayer player, Container container, int x, int y, int z);
	public void itemDropped(EntityPlayer player, ItemStack itemStack, int quantity);
	public void itemWithdrawn(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity);
	public void itemWithdrawn(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed);
	public void itemDeposited(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity);
	public void itemDeposited(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed);
}
