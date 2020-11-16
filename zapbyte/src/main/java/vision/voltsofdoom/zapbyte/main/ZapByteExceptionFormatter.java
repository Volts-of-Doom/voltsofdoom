package vision.voltsofdoom.zapbyte.main;

import org.slf4j.Logger;

public class ZapByteExceptionFormatter {

  public static void onError(Throwable e, Logger logger) {
    onError_withMessage(e, logger, "An unexpected exception has occurred!");
  }

  public static void onError_withMessage(Throwable e, Logger logger, String message) {
    onError_withMessage_withArgs(e, logger, message, new String[] {});
  }

  public static void onError_withMessage_withArgs(Throwable e, Logger logger, String message,
      String... args) {

    logger.error("");
    
    // Log message
    logger.error("- " + message);

    // Log additional information
    logger.error("- Additional information available:");
    for (int i = 0; i < args.length; i++) {

      // While there's one more space ahead (pair available)
      if (args.length > i + 1) {

        // Log a pair
        logger.error("- (" + args[i] + ") : (" + args[i + 1] + ")");

        // Skip the next one
        i++;
      } else {

        // Just log the value
        logger.error("- " + args[i]);
      }
    }

    // Log the exception details
    logger.error("- " + e.getMessage());

    // Log the stacktrace
    for (StackTraceElement elem : e.getStackTrace()) {
      logger.error(
          "- " + elem.getClassName() + " - " + elem.getMethodName() + " #" + elem.getLineNumber());
    }
    
    logger.error("");
  }

}
