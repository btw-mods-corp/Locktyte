package btwmods.playerwatcher.items;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;

public class FileItemLogger extends ReadableItemLogger {
	public FileItemLogger(File outfile) throws IOException {
		this.writer = new FileWriter(outfile);
	}

	@Override
	protected void handleWriteException(String message, IOException e) {
		// TODO: How should we handle this? Write to console instead?
		MinecraftServer.logger.warning("Failed to write the following to the item logger because of: " + e.getMessage());
		MinecraftServer.logger.warning(message);
	}
}
