package btwmods.playerwatcher.items;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import btwmods.playerwatcher.items.ContainerType;

public class NullItemLogger implements IItemLogger {

	@Override
	public void containerPlaced(EntityPlayer player, Container container, World world, int x, int y, int z) {
	}

	@Override
	public void containerOpened(EntityPlayer player, Container container, World world, int x, int y, int z) {
	}

	@Override
	public void containerClosed(EntityPlayer player, Container container) {
	}

	@Override
	public void containerDestroyed(EntityPlayer player, Container container, int x, int y, int z) {
	}

	@Override
	public void itemDropped(EntityPlayer player, ItemStack itemStack, int quantity) {
	}

	@Override
	public void itemWithdrawn(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity) {
	}

	@Override
	public void itemWithdrawn(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed) {
	}

	@Override
	public void itemDeposited(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity) {
	}

	@Override
	public void itemDeposited(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed) {
	}

}
