package vision.voltsofdoom.api.zapyte.config;

public interface IConfigHandler {

  IConfigurationFile loadIfConfigurationFileBlank();

  /**
   * Should the a configuration file.
   * 
   * @return A {@link String}[] of the configuration options.
   */
  void loadConfigurationFile();

  /**
   * Sets this object's arguments to the arguments given to the VM at launch so they may be queried
   * later.
   * 
   * @param configurationFile The {@link String}[] of arguments in.
   * @return This {@link VoltsOfDoomCoreSystem} object.
   */
  void setConfigurationFile(IConfigurationFile configurationFile);

  /**
   * @return The current program arguments.
   */
  IConfigurationFile getConfigurationFile();

}
