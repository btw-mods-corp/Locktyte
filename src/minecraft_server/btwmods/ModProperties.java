package btwmods;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Properties;

import btwmods.playerwatcher.PlayerWatcher;

import net.minecraft.server.MinecraftServer;

public class ModProperties {
	private static Properties properties = null;
	private static File propertiesFile = null;
	
	private static String[] BTWMods = { "btwmods.playerwatcher.PlayerWatcher" };
	
	private static void Load() {
		if (properties == null) {
			properties = new Properties();
			
			// Defaults
			//properties.setProperty(key, value);
			
			propertiesFile = new File(".", "btwmod.properties");
			
			// Load default values from the mods.
			for (int i = 0; i < BTWMods.length; i++) {
				try {
					Properties defaults = (Properties)Class.forName(BTWMods[i]).getMethod("GetDefaultProperties", (Class<?>[]) null).invoke(null, new Object[0]);
					if (defaults != null) {
						for (Enumeration e = defaults.propertyNames(); e.hasMoreElements(); ) {
							String key = (String)e.nextElement();
							properties.setProperty(key, defaults.getProperty(key));
						}
					}
				} catch (ReflectiveOperationException e) {
					MinecraftServer.logger.warning("Failed loading default BTWMod config for '" + BTWMods[i] + "' (" + e.getClass().getName() + "): " + e.getMessage());
				} catch (IllegalArgumentException e) {
					MinecraftServer.logger.warning("Failed loading default BTWMod config for '" + BTWMods[i] + "' (" + e.getClass().getName() + "): " + e.getMessage());
				} catch (SecurityException e) {
					MinecraftServer.logger.warning("Failed loading default BTWMod config for '" + BTWMods[i] + "' (" + e.getClass().getName() + "): " + e.getMessage());
				} catch (ClassCastException e) {
					MinecraftServer.logger.warning("Failed loading default BTWMod config for '" + BTWMods[i] + "' (" + e.getClass().getName() + "): " + e.getMessage());
				}
			}
			
			// Save the default properties, if the file does not exist.
			if (!propertiesFile.isFile()) {
				try {
					properties.store(new FileWriter(propertiesFile), null);
					MinecraftServer.logger.info("Saved default BTWMod config to btwmod.properties.");
				} catch (IOException e) {
					MinecraftServer.logger.warning("Failed to save default BTWMod config to btwmod.properties: " + e.getMessage());
				}
			}
			
			try {
				properties.load(new FileReader(propertiesFile));
				MinecraftServer.logger.info("Loaded BTWMod config.");
			}
			catch (IOException e) {
				MinecraftServer.logger.warning("Failed to load BTWMod config: " + e.getMessage());
			}
		}
	}
	
	public static String Get(String key) {
		Load();
		return properties.getProperty(key);
	}
}
