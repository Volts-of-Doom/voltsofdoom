package vision.voltsofdoom.coresystem.universal.event;

import vision.voltsofdoom.api.zapyte.misc.IResourceLocation;
import vision.voltsofdoom.coresystem.loading.LoadingManager;
import vision.voltsofdoom.coresystem.loading.registry.CollectedRegistries;
import vision.voltsofdoom.coresystem.loading.registry.IRegistryEntry;
import vision.voltsofdoom.coresystem.loading.registry.RegistryType;
import vision.voltsofdoom.coresystem.loading.registry.RegistryTypes;
import vision.voltsofdoom.coresystem.loading.registry.TypeRegistry;
import vision.voltsofdoom.coresystem.loading.window.ILoadingWindowDetailedStatus;
import vision.voltsofdoom.coresystem.universal.band_wagon.Event;
import vision.voltsofdoom.zapbyte.misc.ResourceLocation;

/**
 * Nested classes hold the {@link Event}s played during registry loading.
 * 
 * @see LoadingManager
 * @author GenElectrovise
 *
 */
public class RegistryEvent {

	/**
	 * Called to signify the point at which all mods should create new
	 * {@link RegistryType}s. Contains a helper method so that that creation can
	 * take place in the event, rather than from the {@link RegistryTypes} class,
	 * partly so that all of the calls are executed from the main thread, reducing
	 * the chance for synchronisation issues between threads with disparate,
	 * unsynchronised {@link RegistryType}s later.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class CreateRegistryTypesEvent extends Event {

		public static final ILoadingWindowDetailedStatus DETAILED_STATUS = new ILoadingWindowDetailedStatus() {
			@Override
			public String getDetailedMessage() {
				return "Creating RegistryTypes...";
			}
		};

		public <T extends IRegistryEntry<T>> RegistryType<T> createRegistryType(ResourceLocation identifier,
				Class<T> clazzType) {
			return RegistryTypes.create(identifier, clazzType);
		}
	}

	/**
	 * Called when mods should <b>both <u>create</u> <i>and</i> <u>submit</u></b>
	 * all of their {@link TypeRegistry}s, though they should not be populated until
	 * the {@link PopulateTypeRegistriesEvent} is played.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class CreateAndSubmitRegistriesEvent extends Event {
		public static final ILoadingWindowDetailedStatus DETAILED_STATUS = new ILoadingWindowDetailedStatus() {
			@Override
			public String getDetailedMessage() {
				return "Creating and submitting TypeRegistries...";
			}
		};

		public <T extends IRegistryEntry<T>> TypeRegistry<T> create(IResourceLocation identifier, RegistryType<T> type) {
			return new TypeRegistry<T>(identifier, type);
		}

		public void submit(TypeRegistry<?> typeRegistry) {
			CollectedRegistries.submit(typeRegistry);
		}
	}

	/**
	 * Signifies when objects should be registered to their respective
	 * {@link TypeRegistry}s.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class PopulateTypeRegistriesEvent extends Event {
		public static final ILoadingWindowDetailedStatus DETAILED_STATUS = new ILoadingWindowDetailedStatus() {
			@Override
			public String getDetailedMessage() {
				return "Populating TypeRegistries...";
			}
		};
	}

	public static class GenerateAdventuresEvent extends Event {
		public static final ILoadingWindowDetailedStatus DETAILED_STATUS = new ILoadingWindowDetailedStatus() {
			@Override
			public String getDetailedMessage() {
				return "Loading Adventures...";
			}
		};
	}

	/**
	 * Primarily for internal systems, though modders may listen for it as well.
	 * Signifies when the {@link RegisterTypeEvent} events will begin playing.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class PollRegistryTypeEventsEvent extends Event {
		public static final ILoadingWindowDetailedStatus DETAILED_STATUS = new ILoadingWindowDetailedStatus() {
			@Override
			public String getDetailedMessage() {
				return "Polling RegistryTypeEvents...";
			}
		};
	}
	
	public static class LoadingDoneEvent extends Event {
		
	}

}