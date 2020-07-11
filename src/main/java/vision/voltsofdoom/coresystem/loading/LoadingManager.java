package vision.voltsofdoom.coresystem.loading;

import vision.voltsofdoom.coresystem.loading.mod.Mods;
import vision.voltsofdoom.coresystem.loading.reflectory.Reflectories;
import vision.voltsofdoom.coresystem.loading.window.ILoadingWindowDetailedStatus;
import vision.voltsofdoom.coresystem.loading.window.ILoadingWindowStatus;
import vision.voltsofdoom.coresystem.loading.window.LoadingWindow;
import vision.voltsofdoom.coresystem.universal.band_wagon.BandWagon;
import vision.voltsofdoom.coresystem.universal.band_wagon.Stowaway;
import vision.voltsofdoom.coresystem.universal.event.LoadingEvent;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent.PollRegistryTypeEventsEvent;

public class LoadingManager {

	private static LoadingWindow loadingWindow;

	public static void load() {

		try {

			// 1) Create loading window in new thread.
			loadingWindow = new LoadingWindow();
			loadingWindow.run();
			setStatus(ILoadingWindowStatus.OPENING_WINDOW);

			// 2) Create reflectories
			// a. find jars
			// b. create Reflectory for each
			setStatus(ILoadingWindowStatus.GENERATING_REFLECTORIES);
			Reflectories.generate();

			// 3) Scan for @Mods
			setStatus(ILoadingWindowStatus.LOCATING_MODS);
			Mods.generate(Reflectories.values());

			// 4) Scan for BandWagon subscribers (@Stowaway)
			setStatus(ILoadingWindowStatus.LOCATING_BAND_WAGON_SUBSCRIBERS);
			BandWagon.collectSubscribers(Reflectories.values());

			// 5) Create BandWagon
			// a. subscribe all valid @Stowaway methods
			// b. scan @Stowaway types for valid methods
			// c. subscribe found valid methods
			setStatus(ILoadingWindowStatus.CREATING_BAND_WAGON);
			BandWagon.playEvent(new LoadingEvent.TestEvent());
			setDetailedStatus(LoadingEvent.TestEvent.DETAILED_STATUS);
			BandWagon.playEvent(new LoadingEvent.BandWagonCreation());
			setDetailedStatus(LoadingEvent.BandWagonCreation.DETAILED_STATUS);

			// 6) Begin Registry creation by firing registry events
			setStatus(ILoadingWindowStatus.CREATING_REGISTRY);
			setDetailedStatus(RegistryEvent.CreateRegistryTypesEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.CreateRegistryTypesEvent());
			setDetailedStatus(RegistryEvent.CreateAndSubmitTypeRegistriesEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.CreateAndSubmitTypeRegistriesEvent());
			setDetailedStatus(RegistryEvent.PopulateTypeRegistriesEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.PopulateTypeRegistriesEvent());
			setDetailedStatus(RegistryEvent.PollRegistryTypeEventsEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.PollRegistryTypeEventsEvent());

			// Finally terminate the loading window
			setStatus(ILoadingWindowStatus.DONE);
			loadingWindow.disableAndDispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void setStatus(ILoadingWindowStatus status) {
		loadingWindow.setStatus(status);
	}

	private static void setDetailedStatus(ILoadingWindowDetailedStatus detailedStatus) {
		loadingWindow.setDetailedStatus(detailedStatus);
	}

	@Stowaway
	private void pollRegistryTypeEvents(PollRegistryTypeEventsEvent event) {
		try {

			throw new IllegalStateException("RegisterTypeEvent polling does not exist yet!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
