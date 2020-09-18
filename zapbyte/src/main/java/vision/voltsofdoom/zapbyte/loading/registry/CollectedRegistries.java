package vision.voltsofdoom.zapbyte.loading.registry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

import vision.voltsofdoom.api.zapyte.misc.IResourceLocation;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
/**
 * Holds the {@link IRegistry}s submitted during
 * {@link RegistryEvent.CreateAndSubmitRegistriesEvent}
 * 
 * @author GenElectrovise
 *
 */
public class CollectedRegistries {

	private static final LinkedHashMap<IResourceLocation, IRegistry<? extends IRegistryEntry<?>>> typeRegistries = new LinkedHashMap<IResourceLocation, IRegistry<? extends IRegistryEntry<?>>>();

	public static <T extends IRegistryEntry<T>> void submit(IRegistry<T> typeRegistry) {
		Objects.requireNonNull(typeRegistry, () -> "Cannot submit a null TypeRegistry");
		typeRegistries.put(typeRegistry.getRegistryIdentifier(), typeRegistry);
	}

	public static IRegistry<? extends IRegistryEntry<?>> get(IResourceLocation identifier) {
		return typeRegistries.get(identifier);
	}

	public static Iterator<IRegistry<? extends IRegistryEntry<?>>> getIterator() {
		return typeRegistries.values().iterator();
	}

}
