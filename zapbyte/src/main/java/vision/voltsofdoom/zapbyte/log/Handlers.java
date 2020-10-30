package vision.voltsofdoom.zapbyte.log;

import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

/**
 * Logging {@link Handler}s.
 * 
 * @author GenElectrovise
 *
 */
public class Handlers {
  private static String formattedDate = genFormattedDate();
  private static String baseFilePath =
      ZapByteReference.getLogs() + formattedDate + ZapByteReference.getSep();

  public static FileHandler FILE_HANDLER_ONE;
  public static ConsoleHandler CONSOLE_HANDLER_ONE;
  private static String TIER_ONE_PATH = baseFilePath + "tier_1_info";

  public static FileHandler FILE_HANDLER_TWO;
  public static ConsoleHandler CONSOLE_HANDLER_TWO;
  private static String TIER_TWO_PATH = baseFilePath + "tier_2_info";

  public static FileHandler FILE_HANDLER_THREE;
  public static ConsoleHandler CONSOLE_HANDLER_THREE;
  private static String TIER_THREE_PATH = baseFilePath + "tier_3_info";

  public static FileHandler FILE_HANDLER_FOUR;
  public static ConsoleHandler CONSOLE_HANDLER_FOUR;
  private static String TIER_FOUR_PATH = baseFilePath + "tier_4_info";

  static {
    try {

      ZBSystemResourceHandler.instance.getFile_canIgnoreMissing(() -> baseFilePath, true).mkdirs();

      FILE_HANDLER_ONE = new FileHandler(TIER_ONE_PATH, 51200, 1, false);
      CONSOLE_HANDLER_ONE = new ConsoleHandler();
      FILE_HANDLER_ONE.setFormatter(Formatters.DEFAULT);
      CONSOLE_HANDLER_ONE.setFormatter(Formatters.DEFAULT);

      FILE_HANDLER_TWO = new FileHandler(TIER_TWO_PATH, 51200, 1, false);
      CONSOLE_HANDLER_TWO = new ConsoleHandler();
      FILE_HANDLER_TWO.setFormatter(Formatters.DEFAULT);
      CONSOLE_HANDLER_TWO.setFormatter(Formatters.DEFAULT);

      FILE_HANDLER_THREE = new FileHandler(TIER_THREE_PATH, 51200, 1, false);
      CONSOLE_HANDLER_THREE = new ConsoleHandler();
      FILE_HANDLER_THREE.setFormatter(Formatters.DEFAULT);
      CONSOLE_HANDLER_THREE.setFormatter(Formatters.DEFAULT);

      FILE_HANDLER_FOUR = new FileHandler(TIER_FOUR_PATH, 51200, 1, false);
      CONSOLE_HANDLER_FOUR = new ConsoleHandler();
      FILE_HANDLER_FOUR.setFormatter(Formatters.DEFAULT);
      CONSOLE_HANDLER_FOUR.setFormatter(Formatters.DEFAULT);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String genFormattedDate() {
    String date = Calendar.getInstance().getTime().toString();
    return date.replace(" ", "_").replace(":", "-");
  }

}
