package vision.voltsofdoom.coresystem.universal.util;

import static vision.voltsofdoom.zapbyte.main.ZapByteReference.*;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;

/**
 * Holds variables which must be visible to the rest of the game. This primarily includes directory
 * paths. Builds off of {@link ZapByteReference}.
 * 
 * @author GenElectrovise
 *
 */
public class Reference {

  /**
   * Gets the location of the textures folder
   */
  public static String getTexturesDir() {
    return getResources() + "textures" + seperator();
  }

  /**
   * Gets the location of the textures folder
   */
  public static String getAdventuresDir() {
    return getResources() + "adventure" + seperator();
  }

}
