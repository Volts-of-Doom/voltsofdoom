package vision.voltsofdoom.coresystem.loading.registry;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;
import vision.voltsofdoom.coresystem.universal.band_wagon.Event;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;

/**
 * The {@link Registry} is a central repository of {@link Supplier}s of game
 * objects (characterised by {@link IRegistryEntry}). These objects are divided
 * into {@link IRegistry}s by their type (Parameterised of
 * {@link IRegistryEntry} and {@link IRegistry}) <br>
 * <br>
 * How the {@link Registry} is created: <br>
 * <ol>
 * <li>New {@link RegistryTypes} are created with the
 * {@link RegistryEvent.CreateRegistryTypesEvent}
 * <li>{@link TypeRegistry}s are collected with the
 * {@link RegistryEvent.CreateAndSubmitTypeRegistriesEvent}. These should be created in
 * the course of this {@link Event}.
 * <li>{@link TypeRegistry}s are populated during the
 * {@link RegistryEvent.PopulateTypeRegistriesEvent}. They are not
 * <i>necessarily required</i> to be populated in this {@link Event}, though for
 * simplicity and organisation's sake, and to prevent any loading errors with
 * custom, uncreated {@link RegistryType}s, and because it is the convention,
 * {@link TypeRegistry}s should be populated during this {@link Event}.
 * <li>Every unique type of {@link TypeRegistry} registered is collected into a
 * {@link Set} of unique {@link IRegistryEntry}s.
 * <li>A {@link RegistryEvent.RegisterTypeEvent} event is called for each collected
 * type. A {@link FinalisedTypeRegistry} is created for the {@link Event}'s
 * type, and every {@link TypeRegistry} of that type registers/adds its contents
 * to that {@link FinalisedTypeRegistry}. This results in there being one
 * {@link FinalisedTypeRegistry} for each type of {@link IRegistryEntry} that is
 * being registered.
 * </ol>
 * 
 * @author GenElectrovise
 *
 */
public class Registry {

	// Management

	public static final LinkedHashMap<ResourceLocation, IFinalisedRegistry<? extends IRegistryEntry<?>>> registry = new LinkedHashMap<ResourceLocation, IFinalisedRegistry<? extends IRegistryEntry<?>>>();

	@SuppressWarnings("unchecked")
	public static <T extends IRegistryEntry<T>> IRegistry<T> getTyped(RegistryType<T> registryType) {
		IRegistry<T> out = null;

		try {
			for (IFinalisedRegistry<?> registry : registry.values()) {
				if (registry.getType() == registryType) {

					// Unchecked cast
					out = (IRegistry<T>) registry.getType();
					break;
				}
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (ClassCastException c) {
			c.printStackTrace();
		}

		return out;
	}

}
