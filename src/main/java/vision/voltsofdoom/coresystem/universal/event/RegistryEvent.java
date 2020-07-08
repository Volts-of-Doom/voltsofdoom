package vision.voltsofdoom.coresystem.universal.event;

import vision.voltsofdoom.coresystem.loading.registry.IRegistryEntry;
import vision.voltsofdoom.coresystem.universal.band_wagon.Event;

public class RegistryEvent {

	public static class CreateRegistryTypesEvent extends Event {

	}

	public static class CollectTypeRegistriesEvent extends Event {

	}

	public static class PopulateTypeRegistriesEvent extends Event {

	}

	public static class RegisterType<T extends IRegistryEntry<T>> extends Event {

	}

}
