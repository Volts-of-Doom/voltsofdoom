package vision.voltsofdoom.coresystem.universal.util;

import static vision.voltsofdoom.zapbyte.main.ZapByteReference.*;

/**
 * Holds static final variables which must be visible to the rest of the game. This primarily
 * includes directory paths.
 * 
 * @author GenElectrovise
 *
 */
public class Reference {

  // Roaming
  public static final String ADVENTURE = getResources() + getSep() + "adventure" + getSep();
  public static final String MODS_DIRECTORY = getApplicationRoaming() + "mods" + getSep();

  // Internal
  public static final String INTERNAL_RESOURCES =
      "src" + getSep() + "main" + getSep() + "resources" + getSep();

}
