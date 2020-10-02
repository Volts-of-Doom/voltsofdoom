package vision.voltsofdoom.zapbyte.event;

import vision.voltsofdoom.zapbyte.bandwagon.BandWagon;
import vision.voltsofdoom.zapbyte.bandwagon.Event;
import vision.voltsofdoom.zapbyte.loading.window.ILoadingWindowDetailedStatus;

/**
 * Contains {@link Event}s called during loading.
 * 
 * @author GenElectrovise
 *
 */
public class LoadingEvent {

  public static class TestEvent extends Event {

    public static final ILoadingWindowDetailedStatus DETAILED_STATUS =
        new ILoadingWindowDetailedStatus() {

          @Override
          public String getDetailedMessage() {
            return "Testing BandWagon Event playing...";
          }
        };

  }

  /**
   * Called when the {@link BandWagon} is being created.
   * 
   * @author GenElectrovise
   *
   */
  public static class BandWagonCreation extends Event {

    public static final ILoadingWindowDetailedStatus DETAILED_STATUS =
        new ILoadingWindowDetailedStatus() {

          @Override
          public String getDetailedMessage() {
            return "BandWagon has been created...";
          }
        };

  }
}
