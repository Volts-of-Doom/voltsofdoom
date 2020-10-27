package vision.voltsofdoom.zapbyte.main;

import java.io.File;

public class ZapByteReference {

  /**
   * Populated at startup by {@link ZapByte#run()}
   */
  public static String APPLICATION_NAMESPACE = null;

  public static String getSep() {
    return File.separator;
  }

  public static String getApplicationNamespace() {
    return APPLICATION_NAMESPACE;
  }

  /**
   * i.e. C:/Users/Username on windows, or home/username on Linux
   */
  public static String getUserHome() {
    return System.getProperty("user.home");
  }

  /**
   * The game's %App Data%/Roaming directory
   */
  public static String getZapByteRoaming() {
    return getUserHome() + getSep() + "AppData" + getSep() + "Roaming" + getSep() + ZapByte.ZAPBYTE
        + getSep();
  }

  /**
   * The roaming directory of this particular application.
   */
  public static String getApplicationRoaming() {
    return getZapByteRoaming() + getSep() + getApplicationNamespace() + getSep();
  }

  /**
   * The output location of log files
   */
  public static String getLogs() {
    return getApplicationRoaming() + getSep() + "logs" + getSep();
  }

  /**
   * The location of this application's configuration files' folder
   */
  public static String getConfig() {
    return getApplicationRoaming() + "config" + getSep();
  }

  /**
   * The resources directory within the roaming folder.
   */
  public static String getResources() {
    return getApplicationRoaming() + getSep() + "resources" + getSep();
  }

  /**
   * Gets the mods directory's location.
   */
  public static String getModsDir() {
    return getResources() + "mods" + getSep();
  }
}
