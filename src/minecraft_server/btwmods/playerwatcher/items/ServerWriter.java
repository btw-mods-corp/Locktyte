package btwmods.playerwatcher.items;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.MinecraftServer;

public class ServerWriter extends Writer {
	
	private Logger logger;
	private Level level;
	
	public ServerWriter(Level level) {
		logger = MinecraftServer.logger;
		this.level = level;
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		logger.log(level, String.copyValueOf(cbuf, off, len));
	}

	@Override
	public void flush() throws IOException {
		
	}

	@Override
	public void close() throws IOException {
		
	}

}
