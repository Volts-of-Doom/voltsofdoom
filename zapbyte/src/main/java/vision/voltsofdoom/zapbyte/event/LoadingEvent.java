package vision.voltsofdoom.zapbyte.event;

import vision.voltsofdoom.zapbyte.window.ILoadingWindowDetailedStatus;

/**
 * Contains {@link IEvent}s called during loading.
 * 
 * @author GenElectrovise
 *
 */
public class LoadingEvent {

  public static class TestEvent implements IEvent {

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
  public static class BandWagonCreation implements IEvent {

    public static final ILoadingWindowDetailedStatus DETAILED_STATUS =
        new ILoadingWindowDetailedStatus() {

          @Override
          public String getDetailedMessage() {
            return "BandWagon has been created...";
          }
        };

  }
}
