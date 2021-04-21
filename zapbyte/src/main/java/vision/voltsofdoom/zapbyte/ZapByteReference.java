package vision.voltsofdoom.zapbyte;

import java.io.File;

public class ZapByteReference {

  /**
   * Populated at startup by {@link ZapByte#run()}
   */
  public static String APPLICATION_NAMESPACE = null;

  public static String seperator() {
    return File.separator;
  }

  public static String getApplicationNamespace() {
    return APPLICATION_NAMESPACE;
  }

  /**
   * i.e. C:/Users/Username on windows, or home/username on Linux
   */
  public static String getUserHome() {
    return System.getProperty("user.home") + seperator();
  }

  /**
   * The game's %App Data%/Roaming directory
   */
  public static String getZapByteRoaming() {
    return getUserHome() + "AppData" + seperator() + "Roaming" + seperator() + ZapByte.getZapbyte()
        + seperator();
  }

  /**
   * The roaming directory of this particular application.
   */
  public static String getApplicationRoaming() {
    return getZapByteRoaming() + getApplicationNamespace() + seperator();
  }

  /**
   * The output location of log files
   */
  public static String getLogs() {
    return getApplicationRoaming() + "logs" + seperator();
  }

  /**
   * The location of this application's configuration files' folder
   */
  public static String getConfig() {
    return getApplicationRoaming() + "config" + seperator();
  }

  /**
   * The resources directory within the roaming folder.
   */
  public static String getResources() {
    return getApplicationRoaming() + "resources" + seperator();
  }

  /**
   * Gets the mods directory's location.
   */
  public static String getModsDir() {
    return getResources() + "mods" + seperator();
  }
}
