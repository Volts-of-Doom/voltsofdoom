package vision.voltsofdoom.zapbyte.loading.registry;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import vision.voltsofdoom.zapbyte.bandwagon.Event;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.resource.IResourceLocation;

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
 * {@link RegistryEvent.CreateAndSubmitRegistriesEvent}. These should be created
 * in the course of this {@link Event}.
 * <li>{@link TypeRegistry}s are populated during the
 * {@link RegistryEvent.PopulateTypeRegistriesEvent}. They are not
 * <i>necessarily required</i> to be populated in this {@link Event}, though for
 * simplicity and organisation's sake, and to prevent any loading errors with
 * custom, uncreated {@link RegistryType}s, and because it is the convention,
 * {@link TypeRegistry}s should be populated during this {@link Event}.
 * <li>Every unique type of {@link TypeRegistry} registered is collected into a
 * {@link Set} of unique {@link IRegistryEntry}s.
 * <li>A {@link RegistryEvent.RegisterTypeEvent} event is called for each
 * collected type. A {@link FinalisedTypeRegistry} is created for the
 * {@link Event}'s type, and every {@link TypeRegistry} of that type
 * registers/adds its contents to that {@link FinalisedTypeRegistry}. This
 * results in there being one {@link FinalisedTypeRegistry} for each type of
 * {@link IRegistryEntry} that is being registered.
 * </ol>
 * 
 * @author GenElectrovise
 *
 */
public class Registry {

	private static IRegistryState state = IRegistryState.ACTIVE;

	private static boolean finallyFrozen = false;

	private static final LinkedHashMap<IResourceLocation, IFinalisedRegistry<? extends IRegistryEntry<?>>> registry = new LinkedHashMap<IResourceLocation, IFinalisedRegistry<? extends IRegistryEntry<?>>>();

	/**
	 * Locks and adds the {@link IFinalisedRegistry} to the
	 * {@link Registry#registry}
	 * 
	 * @param identifier
	 * @param registry
	 * @throws IllegalAccessException
	 */
	public static void register(IResourceLocation identifier, IFinalisedRegistry<? extends IRegistryEntry<?>> registry)
			throws IllegalAccessException {
		Objects.requireNonNull(identifier, () -> "Identifier cannot be null!");
		Objects.requireNonNull(registry, () -> "Registry cannot be null!");

		if (state.equals(IRegistryState.FROZEN)) {
			throw new IllegalAccessException("Cannot add to a frozen registry. State = " + state);
		}

		if (containsType(registry.getType())) {
			getTyped(registry.getType()).inject(registry);
		} else {
			Registry.registry.put(identifier, registry);
		}

		registry.lock();
	}

	/**
	 * @param type
	 * @return Whether the registry contains a {@link IFinalisedRegistry} of the
	 *         given {@link RegistryType}.
	 */
	public static boolean containsType(RegistryType<? extends IRegistryEntry<?>> type) {

		Objects.requireNonNull(type, () -> "Type cannot be null!");

		for (IFinalisedRegistry<?> finalisedRegistry : registry.values()) {
			if (finalisedRegistry.getType().equals(type)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param registryType
	 * @return The {@link IFinalisedRegistry} of the type, or null if no such
	 *         {@link IFinalisedRegistry} exists.
	 */
	public static IFinalisedRegistry<? extends IRegistryEntry<?>> getTyped(
			RegistryType<? extends IRegistryEntry<?>> registryType) {

		Objects.requireNonNull(registryType, () -> "Type cannot be null!");

		for (IFinalisedRegistry<? extends IRegistryEntry<?>> finalisedRegistry : registry.values()) {
			if (finalisedRegistry.getType().equals(registryType)) {
				return finalisedRegistry;
			}
		}

		return null;
	}

	protected static void setState(IRegistryState state) {

		if (isFinallyFrozen()) {
			Registry.state = IRegistryState.FROZEN;
			return;
		}

		Registry.state = state;
	}

	public static IRegistryState getState() {
		return state;
	}

	/**
	 * Puts the {@link Registry} into permafrost.
	 */
	public static void iceAge() {
		setState(IRegistryState.FROZEN);
		finallyFrozen = true;
	}

	public static boolean isFinallyFrozen() {
		return finallyFrozen;
	}

	public static void setFinallyFrozen(boolean finallyFrozen) {
		Registry.finallyFrozen = finallyFrozen;
	}

	public static String asStaticStringRepresentation() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nBEGIN REGISTRY DUMP!\n");

		registry.forEach((id, fReg) -> {
			builder.append(fReg.toString() + "\n");
		});

		builder.append("END REGISTRY DUMP!\n");
		return builder.toString();
	}

	public static void dump(PrintStream out) {
		out.println(asStaticStringRepresentation());
	}
}
