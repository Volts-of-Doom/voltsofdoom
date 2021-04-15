package vision.voltsofdoom.api.zapyte.config;

public interface IConfigurationFileHandler {

  /**
   * Loads the configuration file.
   */
  void loadConfigurationFile();

  /**
   * Sets the internal {@link IConfigurationFile} object.
   */
  void setConfigurationFile(IConfigurationFile configurationFile);

  /**
   * @return The {@link IConfigurationFile}
   */
  IConfigurationFile getConfigurationFile();

  /**
   * @return Whether the contained {@link IConfigurationFile} is blank.
   */
  boolean isBlank();

}
