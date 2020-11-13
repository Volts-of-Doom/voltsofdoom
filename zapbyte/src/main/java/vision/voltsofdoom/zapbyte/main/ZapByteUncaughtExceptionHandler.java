package vision.voltsofdoom.zapbyte.main;

import java.lang.Thread.UncaughtExceptionHandler;
import org.slf4j.Logger;

public class ZapByteUncaughtExceptionHandler implements UncaughtExceptionHandler {

  private final Logger logger;

  public ZapByteUncaughtExceptionHandler(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    logger.error(
        "AN UNCAUGHT EXCEPTION HAS OCCURRED! THIS RESPONSE IS FROM THE ZapByteUncaughtExceptionHandler!");
    logger.error(e.getMessage());
    for (StackTraceElement elem : e.getStackTrace()) {
      logger.error(elem.getClassName() + " - " + elem.getMethodName() + " #" + elem.getLineNumber());
    }
  }

}
