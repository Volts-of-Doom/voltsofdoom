package vision.voltsofdoom.coresystem.universal.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.log.ZBLoggers;

/**
 * Holds additional {@link Logger}s, extending list from {@link ZBLoggers}.
 * 
 * @author GenElectrovise
 *
 */
public class VODLoggers extends ZBLoggers {
  public static Logger VOLTSOFDOOM;
  
  static {
    VOLTSOFDOOM = LoggerFactory.getLogger(VoltsOfDoomCoreSystem.class);
  }
}
