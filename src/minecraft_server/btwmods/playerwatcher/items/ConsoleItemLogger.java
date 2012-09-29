package btwmods.playerwatcher.items;

import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.server.MinecraftServer;

/**
 * Write human readable log entries to the server's logger.
 */
public class ConsoleItemLogger extends ReadableItemLogger {
	public ConsoleItemLogger() {
		writer = new ServerWriter(Level.INFO);
	}

	@Override
	protected void handleWriteException(String message, IOException e) {
		// Should never happen. What would we do anyway?
	}
}
