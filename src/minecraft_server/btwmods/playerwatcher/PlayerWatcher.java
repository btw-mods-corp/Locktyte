package btwmods.playerwatcher;

import btwmods.BTWMod;
import btwmods.ModProperties;
import btwmods.playerwatcher.items.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class PlayerWatcher extends BTWMod {
	
	public static Properties GetDefaultProperties() {
		Properties defaults = new Properties();
		defaults.setProperty("PlayerWatcher.ContainerLogger", "console");
		defaults.setProperty("PlayerWatcher.ContainerLogger.File", "containerlogger.log");
		defaults.setProperty("PlayerWatcher.ContainerLogger.FileFailsToConsole", "1");
		return defaults;
	}
	
	private EntityPlayer player;
	private IItemLogger items = null;
	
	public PlayerWatcher(EntityPlayer player) {
		this.player = player;
		
		/* ===================================
		 *             ITEM LOGGER
		 * =================================== */
		
		String loggerName = ModProperties.Get("PlayerWatcher.ContainerLogger").toLowerCase();
		
		if (loggerName == "file") {
			String file = ModProperties.Get("PlayerWatcher.ContainerLogger.File").toLowerCase();
			boolean failToConsole = ModProperties.Get("PlayerWatcher.ContainerLogger.FileFailsToConsole") == "1";
			
			try {
				items = new FileItemLogger(new File(file));
				
			} catch (IOException e) {
				MinecraftServer.logger.warning("BTWMod failed to open 'PlayerWatcher.ContainerLogger.File' for writing." + (failToConsole ? " Logging to console instead." : ""));
				items = failToConsole ? new ConsoleItemLogger() : new NullItemLogger();
			}
		}
		else if (loggerName == "console") {
			items = new ConsoleItemLogger();
		}
		else {
			items = new NullItemLogger();
		}
	}
	
	public void activatedBlock(World world, ItemStack heldItemStack, Block block, int x, int y, int z) {
		if (block instanceof BlockContainer) {
			containerOpened(this.player.craftingInventory, world, x, y, z);
		}
		else {
			MinecraftServer.logger.info("Activated non-container block " + block.getBlockName() + "(" + block.blockID + ")");
		}
	}
	

	public void containerPlaced(Container container, World world, int x, int y, int z) {
		this.items.containerPlaced(player, container, world, x, y, z);
	}
	
	public void containerOpened(Container container, World world, int x, int y, int z) {
		this.items.containerOpened(player, container, world, x, y, z);
	}
	
	public void containerClosed(Container container) {
		this.items.containerClosed(player, container);
	}
	
	public void containerDestroyed(Container container, int x, int y, int z) {
		this.items.containerDestroyed(player, container, x, y, z);
	}
	
	public void itemDropped(ItemStack itemStack, int quantity) {
		this.items.itemDropped(player, itemStack, quantity);
	}
	
	public void itemWithdrawn(Container container, int slotId, ItemStack itemStack, int quantity) {
		this.items.itemWithdrawn(player, container, slotId, itemStack, quantity);
	}
	
	public void itemWithdrawn(Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed) {
		this.items.itemWithdrawn(player, container, slotId, itemStack, quantity, isShiftPressed);
	}
	
	public void itemDeposited(Container container, int slotId, ItemStack itemStack, int quantity) {
		this.items.itemDeposited(player, container, slotId, itemStack, quantity);
	}
	
	public void itemDeposited(Container container, int slotId, ItemStack itemStack, int quantity, boolean isShiftPressed) {
		this.items.itemDeposited(player, container, slotId, itemStack, quantity, isShiftPressed);
	}
}
