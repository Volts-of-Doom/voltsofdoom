package vision.voltsofdoom.client.draftclient.voltsofdoom.adventure;

import vision.voltsofdoom.zapbyte.bandwagon.event.IEvent;
import vision.voltsofdoom.zapbyte.window.ILoadingWindowDetailedStatus;

/**
 * Signifies when adventures should be generated.
 * 
 * @author GenElectrovise
 *
 */
public class GenerateAdventuresEvent implements IEvent {
  public static final ILoadingWindowDetailedStatus DETAILED_STATUS =
      new ILoadingWindowDetailedStatus() {
        @Override
        public String getDetailedMessage() {
          return "Loading Adventures...";
        }
      };
}
