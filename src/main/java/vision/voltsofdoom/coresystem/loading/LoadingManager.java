package vision.voltsofdoom.coresystem.loading;

import vision.voltsofdoom.coresystem.loading.mod.Mods;
import vision.voltsofdoom.coresystem.loading.reflectory.Reflectories;
import vision.voltsofdoom.coresystem.loading.window.LoadingWindow;
import vision.voltsofdoom.coresystem.loading.window.LoadingWindowStatus;
import vision.voltsofdoom.coresystem.universal.band_wagon.BandWagon;
import vision.voltsofdoom.coresystem.universal.event.LoadingEvent;

public class LoadingManager {

	private static LoadingWindow loadingWindow;

	public static void load() {

		try {

			// 1) Create loading window in new thread.
			loadingWindow = new LoadingWindow();
			loadingWindow.run();
			setStatus(LoadingWindowStatus.OPENING_WINDOW);

			// 2) Create reflectories
			// a. find jars
			// b. create Reflectory for each
			setStatus(LoadingWindowStatus.GENERATING_REFLECTORIES);
			Reflectories.generate();

			// 3) Scan for @Mods
			setStatus(LoadingWindowStatus.LOCATING_MODS);
			Mods.generate(Reflectories.values());

			// 4) Scan for BandWagon subscribers (@Stowaway)
			setStatus(LoadingWindowStatus.LOCATING_BAND_WAGON_SUBSCRIBERS);
			BandWagon.collectSubscribers(Reflectories.values());

			// 5) Create BandWagon
			// a. subscribe all valid @Stowaway methods
			// b. scan @Stowaway types for valid methods
			// c. subscribe found valid methods
			setStatus(LoadingWindowStatus.CREATING_BAND_WAGON);
			BandWagon.playEvent(new LoadingEvent.TestEvent());
			BandWagon.playEvent(new LoadingEvent.BandWagonCreation());

			// 6) Begin Registry creation by firing registry events
			setStatus(LoadingWindowStatus.CREATING_REGISTRY);

			// Finally terminate the loading window
			setStatus(LoadingWindowStatus.DONE);
			loadingWindow.disableAndDispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void setStatus(LoadingWindowStatus status) {
		loadingWindow.setStatus(status);
	}

}
