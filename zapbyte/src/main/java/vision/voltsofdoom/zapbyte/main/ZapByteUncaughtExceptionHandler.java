package vision.voltsofdoom.zapbyte.main;

import java.lang.Thread.UncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZapByteUncaughtExceptionHandler implements UncaughtExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZapByteUncaughtExceptionHandler.class);

  public ZapByteUncaughtExceptionHandler() {}

  @Override
  public void uncaughtException(Thread t, Throwable e) {

    LOGGER.error("The thread " + t + " has encountered an uncaught error, which the ZapByteUncaughtExceptionHandler will deem to be fatal. A stacktrace will follow this message, then the program will exit.");

    e.printStackTrace();
    System.exit(-1000);
  }

}
