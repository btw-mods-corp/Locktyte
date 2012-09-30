package btwmods.playerwatcher.items;

import java.io.IOException;
import java.io.Writer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import btwmods.playerwatcher.items.ContainerType;

/**
 * Write human readable log entries.
 */
public abstract class ReadableItemLogger implements IItemLogger {
	
	protected Writer writer = null;
	
	protected abstract void handleWriteException(String message, IOException e);
	
	private void write(String message) {
		try {
			writer.write(message);
		} catch (IOException e) {
			handleWriteException(message, e);
		}
	}

	@Override
	public final void containerPlaced(EntityPlayer player, Container container, World world, int x, int y, int z) {
		
	}

	@Override
	public final void containerOpened(EntityPlayer player, Container container, World world, int x, int y, int z) {
		MinecraftServer.logger.info("Closed container " + container.getClass().getName() + " at ");
	}

	@Override
	public final void containerClosed(EntityPlayer player, Container container) {
		MinecraftServer.logger.info("Closed container " + container.getClass().getName());
	}

	@Override
	public final void containerDestroyed(EntityPlayer player, Container container, int x, int y, int z) {

	}

	@Override
	public final void itemDropped(EntityPlayer player, ItemStack itemStack, int quantity) {

	}

	@Override
	public final void itemWithdrawn(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity) {
		this.itemWithdrawn(player, container, slotId, itemStack, quantity, false);
	}

	@Override
	public final void itemWithdrawn(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed) {
		this.itemMoved(player, container, slotId, itemStack, quantity, "withdrew", "from", isShiftPressed);
	}

	@Override
	public final void itemDeposited(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity) {
		this.itemDeposited(player, container, slotId, itemStack, quantity, false);
	}

	@Override
	public final void itemDeposited(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed) {
		this.itemMoved(player, container, slotId, itemStack, quantity, "deposited", "to", isShiftPressed);
	}
	
	private void itemMoved(EntityPlayer player, Container container, int slotId, ItemStack itemStack, int quantity, String direction, String preposition, boolean isShiftPressed) {
		Slot slot = container.getSlot(slotId);
		
		String coordinates = "";
		if (slot.inventory instanceof TileEntity || (slot = container.getSlot(0)).inventory instanceof TileEntity) {
			TileEntity tile = (TileEntity)slot.inventory;
			coordinates = " at x" + tile.xCoord + " y" + tile.yCoord + " z" + tile.zCoord;
		}
		
		write(
				player.username + " " + direction + " " + quantity + " x " + itemStack.getItemName() +
			" (" + itemStack.itemID + (itemStack.isItemDamaged() ? ":" + itemStack.getItemDamage() : "") + ")" +
			" " + preposition + " " + container.getClass().getName() +
			coordinates);
	}

}
