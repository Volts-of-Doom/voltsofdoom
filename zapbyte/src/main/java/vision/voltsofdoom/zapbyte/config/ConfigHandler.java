package vision.voltsofdoom.zapbyte.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vision.voltsofdoom.zapbyte.log.Loggers;
import vision.voltsofdoom.zapbyte.misc.ZapByteReference;
import vision.voltsofdoom.zapbyte.misc.util.StacktraceUtils;

public class ConfigHandler {

	private static String[] defaultArgs = { "argument" };
	private ConfigurationFile configurationFile;
	
	public ConfigHandler() {
		this.configurationFile = ConfigurationFile.BLANK;
		loadConfigurationFile();
	}
	
	public ConfigurationFile loadIfConfigurationFileBlank() {
		if (configurationFile == ConfigurationFile.BLANK) {
			loadConfigurationFile();
		}
		
		return configurationFile;
	}

	/**
	 * Reads the VM configuration in {@link ZapByteReference#CONFIG}/config.json
	 * 
	 * @return A {@link String}[] of the configuration options.
	 */
	public void loadConfigurationFile() {

		File configFile = new File(ZapByteReference.CONFIG + "vmconfig.json");

		// If the configuration file does not exist...
		if (!configFile.exists()) {
			Loggers.ZAPBYTE.info("Configuration file does not exist at: " + configFile);

			// Make new file and write default values.
			try {
				Loggers.ZAPBYTE.info("Trying to write a new configuration file...");
				BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));

				StringBuilder builder = new StringBuilder();
				for (String string : defaultArgs) {
					builder.append(string);
				}

				writer.write("{\"vmargs\":[\"" + builder.toString() + "\"]}");
				writer.close();
				Loggers.ZAPBYTE.info("Written!");
			} catch (FileNotFoundException f) {
				f.printStackTrace();
			} catch (IOException i) {
				i.printStackTrace();
			}
		}

		try {

			// Read config file
			Loggers.ZAPBYTE.info("Reading configuration file: " + configFile);

			configurationFile = ConfigurationFile.fromJson(new Gson().fromJson(new FileReader(configFile), JsonObject.class));

			Loggers.ZAPBYTE.info("Read configuration: " + configurationFile.toString());

			setConfigurationFile(configurationFile);
		} catch (FileNotFoundException f) {
			f.printStackTrace();
			Loggers.ZAPBYTE.severe(StacktraceUtils.stacktraceToString(f.getStackTrace()));
		}
	}

	/**
	 * Sets this object's arguments to the arguments given to the VM at launch so
	 * they may be queried later.
	 * 
	 * @param configurationFile2 The {@link String}[] of arguments in.
	 * @return This {@link VoltsOfDoomCoreSystem} object.
	 */
	public void setConfigurationFile(ConfigurationFile configurationFile2) {
		this.configurationFile = configurationFile2;
	}

	/**
	 * @return The current program arguments.
	 */
	public ConfigurationFile getConfigurationFile() {
		return configurationFile;
	}
}
