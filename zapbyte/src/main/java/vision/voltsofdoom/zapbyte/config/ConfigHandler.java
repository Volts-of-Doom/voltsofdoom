package vision.voltsofdoom.zapbyte.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vision.voltsofdoom.api.zapyte.config.IConfigHandler;
import vision.voltsofdoom.api.zapyte.config.IConfigurationFile;
import vision.voltsofdoom.zapbyte.log.Loggers;
import vision.voltsofdoom.zapbyte.misc.ZapByteReference;
import vision.voltsofdoom.zapbyte.misc.util.StacktraceUtils;

public class ConfigHandler implements IConfigHandler {

	private static String[] defaultArgs = { "argument" };
	private IConfigurationFile configurationFile;

	public ConfigHandler() {
		this.configurationFile = IConfigurationFile.BLANK;
		loadConfigurationFile();
	}
	
	@Override
	public IConfigurationFile loadIfConfigurationFileBlank() {
		if (configurationFile == IConfigurationFile.BLANK) {
			loadConfigurationFile();
		}

		return configurationFile;
	}
	
	@Override
	public void loadConfigurationFile() {

		File configFile = new File(ZapByteReference.getConfig() + "vmconfig.json");

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

	@Override
	public void setConfigurationFile(IConfigurationFile configurationFile2) {
		this.configurationFile = configurationFile2;
	}

	@Override
	public IConfigurationFile getConfigurationFile() {
		return configurationFile;
	}
}
