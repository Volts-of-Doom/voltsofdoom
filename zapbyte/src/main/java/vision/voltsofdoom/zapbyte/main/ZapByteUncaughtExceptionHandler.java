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
    ZapByteExceptionFormatter.onError(e, logger);
  }

}
