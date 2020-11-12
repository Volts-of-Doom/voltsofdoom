package vision.voltsofdoom.zapbyte.log;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;

/**
 * {@link Logger}s
 * 
 * @author GenElectrovise
 *
 */
public class ZBLoggers {

  public static Logger ZAPBYTE;

  // Configuration
  static {
    try {
      System.setProperty("logback.configurationFile", ZapByteReference.getConfig() + "logback.xml");
      System.setProperty("vision.voltsofdoom.zapbyte.log.outputFile",
          ZapByteReference.getLogs() + getFormattedDate() + ".log");

      ZAPBYTE = LoggerFactory.getLogger(ZapByte.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String getFormattedDate() {
    String date = Calendar.getInstance().getTime().toString();
    return date.replace(" ", "_").replace(":", "-");
  }

}
