package vision.voltsofdoom.coresystem.loading;

import java.util.Iterator;

import vision.voltsofdoom.coresystem.loading.mod.Mods;
import vision.voltsofdoom.coresystem.loading.reflectory.Reflectories;
import vision.voltsofdoom.coresystem.loading.registry.RegistryTypes;
import vision.voltsofdoom.coresystem.loading.registry.CollectedRegistries;
import vision.voltsofdoom.coresystem.loading.registry.IFinalisedRegistry;
import vision.voltsofdoom.coresystem.loading.registry.IRegistry;
import vision.voltsofdoom.coresystem.loading.registry.IRegistryEntry;
import vision.voltsofdoom.coresystem.loading.registry.Registry;
import vision.voltsofdoom.coresystem.loading.registry.RegistryType;
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
			BandWagon.playEvent(new RegistryEvent.CreateRegistryTypesEvent()); // Done
			setDetailedStatus(RegistryEvent.CreateAndSubmitRegistriesEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.CreateAndSubmitRegistriesEvent()); // Done
			setDetailedStatus(RegistryEvent.PopulateTypeRegistriesEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.PopulateTypeRegistriesEvent()); // Done
			setDetailedStatus(RegistryEvent.PollRegistryTypeEventsEvent.DETAILED_STATUS);
			BandWagon.playEvent(new RegistryEvent.PollRegistryTypeEventsEvent()); // See below!

			// Finally terminate the loading window
			setStatus(ILoadingWindowStatus.DONE);
			BandWagon.playEvent(new RegistryEvent.LoadingDoneEvent());
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
	private static void pollRegistryTypeEvents(PollRegistryTypeEventsEvent event) {
		try {

			// Prioritised types first
			for (int i = 0; i < RegistryTypes.prioritisedTypes.size(); i++) {
				Iterator<IRegistry<? extends IRegistryEntry<?>>> registryI = CollectedRegistries.getIterator();
				while (registryI.hasNext()) {
					IRegistry<? extends IRegistryEntry<?>> registry = registryI.next();

					RegistryType<?> type = registry.getType();
					RegistryType<?> comparedType = RegistryTypes.prioritisedTypes.get(i);
					
					if (type.equals(comparedType)) {
						System.out.println("TODO FINALISE 95 LoadingManager");

						IFinalisedRegistry<? extends IRegistryEntry<?>> finalisedRegistry = registry.genFinalised();
						Registry.register(registry.getRegistryIdentifier(), finalisedRegistry);
					}
				}
			}

			// Then do all of the others
			Iterator<IRegistry<? extends IRegistryEntry<?>>> registryI = CollectedRegistries.getIterator();
			while (registryI.hasNext()) {
				IRegistry<?> registry = registryI.next();
				IFinalisedRegistry<? extends IRegistryEntry<?>> finalisedRegistry = registry.genFinalised();
				Registry.register(registry.getRegistryIdentifier(), finalisedRegistry);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry.iceAge();

		Registry.dump(System.out);
	}

}
