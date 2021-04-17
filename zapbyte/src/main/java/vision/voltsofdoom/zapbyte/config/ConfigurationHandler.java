package vision.voltsofdoom.zapbyte.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.api.zapyte.config.IConfigurationHandler;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionProvider;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;
import vision.voltsofdoom.zapbyte.util.StacktraceUtils;

public class ConfigurationHandler implements IConfigurationHandler {

  private static final String CONFIG_FILE = "config.json";
  private static String[] defaultArgs = {"argument"};
  private IConfigurationOptionProvider configurationFile;

  public ConfigurationHandler() {
    this.configurationFile = IConfigurationOptionProvider.BLANK;
    loadConfigurationFile();
  }

  @Override
  public void loadConfigurationFile() {

    File configFile = ZBSystemResourceHandler.instance.getFile(() -> ZapByteReference.getConfig() + CONFIG_FILE, true);

    // If the configuration file does not exist...
    if (!configFile.exists()) {
      ZapByte.LOGGER.warn("Configuration file does not exist at: " + configFile);

      ZBSystemResourceHandler.instance.getFile(() -> ZapByteReference.getConfig(), true).mkdirs();

      // Make new file and write default values.
      try {
        ZapByte.LOGGER.debug("Trying to write a new configuration file...");
        BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));

        StringBuilder builder = new StringBuilder();
        for (String string : defaultArgs) {
          builder.append(string);
        }

        writer.write("{\"vmargs\":[\"" + builder.toString() + "\"]}");
        writer.close();
        ZapByte.LOGGER.debug("Written!");
      } catch (FileNotFoundException f) {
        f.printStackTrace();
      } catch (IOException i) {
        i.printStackTrace();
      }
    }

    try {

      // Read config file
      ZapByte.LOGGER.debug("Reading configuration file: " + configFile);

      configurationFile = ConfigurationFile.fromJson(new Gson().fromJson(new FileReader(configFile), JsonObject.class));

      ZapByte.LOGGER.debug("Read configuration: " + configurationFile.toString());

      setConfigurationFile(configurationFile);
    } catch (FileNotFoundException f) {
      f.printStackTrace();
      ZapByte.LOGGER.error(StacktraceUtils.stacktraceToString(f.getStackTrace()));
    }
  }

  @Override
  public void setConfigurationFile(IConfigurationOptionProvider configurationFile2) {
    this.configurationFile = configurationFile2;
  }

  @Override
  public IConfigurationOptionProvider getConfigurationFile() {
    return configurationFile;
  }

  @Override
  public boolean isBlank() {
    return configurationFile == IConfigurationOptionProvider.BLANK || configurationFile == null;
  }
}
