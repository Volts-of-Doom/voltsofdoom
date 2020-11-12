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
import vision.voltsofdoom.zapbyte.log.ZBLoggers;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;
import vision.voltsofdoom.zapbyte.misc.util.StacktraceUtils;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

public class ConfigHandler implements IConfigHandler {

  private static final String CONFIG_FILE = "config.json";
  private static String[] defaultArgs = {"argument"};
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

    File configFile = ZBSystemResourceHandler.instance.getFile_canIgnoreMissing(() -> ZapByteReference.getConfig() + CONFIG_FILE, true);

    // If the configuration file does not exist...
    if (!configFile.exists()) {
      ZBLoggers.ZAPBYTE.info("Configuration file does not exist at: " + configFile);

      ZBSystemResourceHandler.instance.getFile_canIgnoreMissing(() -> ZapByteReference.getConfig(), true).mkdirs();

      // Make new file and write default values.
      try {
        ZBLoggers.ZAPBYTE.info("Trying to write a new configuration file...");
        BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));

        StringBuilder builder = new StringBuilder();
        for (String string : defaultArgs) {
          builder.append(string);
        }

        writer.write("{\"vmargs\":[\"" + builder.toString() + "\"]}");
        writer.close();
        ZBLoggers.ZAPBYTE.info("Written!");
      } catch (FileNotFoundException f) {
        f.printStackTrace();
      } catch (IOException i) {
        i.printStackTrace();
      }
    }

    try {

      // Read config file
      ZBLoggers.ZAPBYTE.info("Reading configuration file: " + configFile);

      configurationFile = ConfigurationFile
          .fromJson(new Gson().fromJson(new FileReader(configFile), JsonObject.class));

      ZBLoggers.ZAPBYTE.info("Read configuration: " + configurationFile.toString());

      setConfigurationFile(configurationFile);
    } catch (FileNotFoundException f) {
      f.printStackTrace();
      ZBLoggers.ZAPBYTE.error(StacktraceUtils.stacktraceToString(f.getStackTrace()));
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
