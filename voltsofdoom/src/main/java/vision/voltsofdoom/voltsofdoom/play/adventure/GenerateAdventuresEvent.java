package vision.voltsofdoom.voltsofdoom.play.adventure;

import vision.voltsofdoom.zapbyte.event.Event;
import vision.voltsofdoom.zapbyte.loading.window.ILoadingWindowDetailedStatus;

/**
 * Signifies when adventures should be generated.
 * 
 * @author GenElectrovise
 *
 */
public class GenerateAdventuresEvent extends Event {
  public static final ILoadingWindowDetailedStatus DETAILED_STATUS =
      new ILoadingWindowDetailedStatus() {
        @Override
        public String getDetailedMessage() {
          return "Loading Adventures...";
        }
      };
}
