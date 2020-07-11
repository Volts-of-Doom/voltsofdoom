package vision.voltsofdoom.coresystem.loading.registry;

import java.util.LinkedHashMap;
import java.util.Objects;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;

/**
 * Holds the {@link TypeRegistry}s submitted during
 * {@link RegistryEvent.CreateAndSubmitTypeRegistriesEvent}
 * 
 * @author GenElectrovise
 *
 */
public class TypeRegistries {

	private static final LinkedHashMap<ResourceLocation, TypeRegistry<?>> typeRegistries = new LinkedHashMap<ResourceLocation, TypeRegistry<?>>();

	public static <T extends IRegistryEntry<T>> void submit(TypeRegistry<T> typeRegistry) {
		Objects.requireNonNull(typeRegistry, () -> "Cannot submit a null TypeRegistry");
		typeRegistries.put(typeRegistry.getRegistryIdentifier(), typeRegistry);
	}

	public static TypeRegistry<?> get(ResourceLocation identifier) {
		return typeRegistries.get(identifier);
	}

}
