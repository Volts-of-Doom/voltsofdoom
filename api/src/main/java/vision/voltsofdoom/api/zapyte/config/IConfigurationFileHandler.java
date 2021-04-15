package vision.voltsofdoom.api.zapyte.config;

/**
 * NOTE: SHOULD BUILD A MASTER CONFIGURATION FILE OUT OF ALL OF THE INPUT FILES. I.E. MAKE ONE BIG
 * JSON, WHICH CAN THEN BE QUERIED! THIS SEEMS LIKE A FAR MORE LOGICAL APPROACH!
 * 
 * @author GenElectrovise
 *
 */
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
