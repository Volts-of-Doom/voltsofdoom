package vision.voltsofdoom.coresystem.universal.event;

import vision.voltsofdoom.coresystem.universal.band_wagon.BandWagon;
import vision.voltsofdoom.coresystem.universal.band_wagon.Event;

/**
 * Contains {@link Event}s called during loading.
 * 
 * @author GenElectrovise
 *
 */
public class LoadingEvent {
	
	public static class TestEvent extends Event {
		
	}

	/**
	 * Called when the {@link BandWagon} is being created.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class BandWagonCreation extends Event {

	}
}
