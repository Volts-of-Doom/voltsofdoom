package vision.voltsofdoom.zapbyte.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Logger}s
 * 
 * @author GenElectrovise
 *
 */
public class Loggers {

  // Logger tiers
  // Main Systems
  public static final Logger ZAPBYTE = Logger.getLogger("zapbyte");
  public static final Logger MOD = Logger.getLogger("mod");

  // Sub Systems
  public static final Logger ZAPBYTE_LOADING = Logger.getLogger("zapbyte.loading");
  public static final Logger ZAPBYTE_MISCELLANEOUS = Logger.getLogger("zapbyte.miscellaneous");

  public static final Logger MOD_DETAIL = Logger.getLogger("mod.detail");

  // Sub System Parts
  public static final Logger ZAPBYTE_LOADING_REGISTRY =
      Logger.getLogger("zapbyte.loading.registry");
  public static final Logger ZAPBYTE_LOADING_BANDWAGON =
      Logger.getLogger("zapbyte.loading.bandwagon");
  public static final Logger ZAPBYTE_LOADING_RESOURCE =
      Logger.getLogger("zapbyte.loading.resource");

  public static final Logger ZAPBYTE_MISCELLANEOUS_MATH =
      Logger.getLogger("zapbyte.miscellaneous.math");

  // Really fine details
  public static final Logger ZAPBYTE_MISCELLANEOUS_MATH_PICKY =
      Logger.getLogger("zapbyte.miscellaneous.math.picky");

  // Configuration
  static {
    try {
      // 1
      ZAPBYTE.setLevel(Level.INFO);
      MOD.setLevel(Level.INFO);

      ZAPBYTE.addHandler(Handlers.FILE_HANDLER_ONE);
      ZAPBYTE.addHandler(Handlers.CONSOLE_HANDLER_ONE);
      MOD.addHandler(Handlers.FILE_HANDLER_ONE);
      MOD.addHandler(Handlers.CONSOLE_HANDLER_ONE);

      // 2
      ZAPBYTE_LOADING.setLevel(Level.FINE);
      ZAPBYTE_MISCELLANEOUS.setLevel(Level.FINE);
      MOD_DETAIL.setLevel(Level.FINEST);

      ZAPBYTE_LOADING.addHandler(Handlers.FILE_HANDLER_TWO);
      ZAPBYTE_LOADING.addHandler(Handlers.CONSOLE_HANDLER_TWO);
      ZAPBYTE_MISCELLANEOUS.addHandler(Handlers.FILE_HANDLER_TWO);
      ZAPBYTE_MISCELLANEOUS.addHandler(Handlers.CONSOLE_HANDLER_TWO);
      MOD_DETAIL.addHandler(Handlers.FILE_HANDLER_TWO);
      MOD_DETAIL.addHandler(Handlers.CONSOLE_HANDLER_TWO);

      // 3
      ZAPBYTE_LOADING_REGISTRY.setLevel(Level.FINEST);
      ZAPBYTE_LOADING_BANDWAGON.setLevel(Level.FINEST);
      ZAPBYTE_LOADING_RESOURCE.setLevel(Level.FINEST);
      ZAPBYTE_MISCELLANEOUS_MATH.setLevel(Level.FINER);

      ZAPBYTE_LOADING_REGISTRY.addHandler(Handlers.FILE_HANDLER_THREE);
      ZAPBYTE_LOADING_REGISTRY.addHandler(Handlers.CONSOLE_HANDLER_THREE);
      ZAPBYTE_LOADING_BANDWAGON.addHandler(Handlers.FILE_HANDLER_THREE);
      ZAPBYTE_LOADING_BANDWAGON.addHandler(Handlers.CONSOLE_HANDLER_THREE);
      ZAPBYTE_LOADING_RESOURCE.addHandler(Handlers.FILE_HANDLER_THREE);
      ZAPBYTE_LOADING_RESOURCE.addHandler(Handlers.CONSOLE_HANDLER_THREE);
      ZAPBYTE_MISCELLANEOUS_MATH.addHandler(Handlers.FILE_HANDLER_THREE);
      ZAPBYTE_MISCELLANEOUS_MATH.addHandler(Handlers.CONSOLE_HANDLER_THREE);

      // 4
      ZAPBYTE_MISCELLANEOUS_MATH_PICKY.setLevel(Level.FINEST);

      ZAPBYTE_MISCELLANEOUS_MATH_PICKY.addHandler(Handlers.FILE_HANDLER_FOUR);
      ZAPBYTE_MISCELLANEOUS_MATH_PICKY.addHandler(Handlers.CONSOLE_HANDLER_FOUR);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
