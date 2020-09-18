package vision.voltsofdoom.zapbyte.loading.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import vision.voltsofdoom.api.zapyte.misc.IResourceLocation;

/**
 * Handles the registration of {@link IRegistryEntry}s of type T.
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of {@link IRegistryEntry} that this {@link TypeRegistry}
 *        holds.
 */
public class TypeRegistry<T extends IRegistryEntry<T>> implements IRegistry<T> {

	private final IResourceLocation identifier;
	private final RegistryType<T> type;
	private final LinkedHashMap<IResourceLocation, Supplier<T>> entries = new LinkedHashMap<IResourceLocation, Supplier<T>>();
	private IRegistryState state;

	public TypeRegistry(IResourceLocation identifier, RegistryType<T> type) {
		this.identifier = identifier;
		this.type = type;
		this.state = IRegistryState.ACTIVE;
		CollectedRegistries.submit(this);
	}

	@Override
	public IResourceLocation getRegistryIdentifier() {
		return this.identifier;
	}

	@Override
	public RegistryType<T> getType() {
		return type;
	}

	@Override
	public RegistryMessenger<T> register(IResourceLocation iResourceLocation, Supplier<T> instanceSupplier) {

		if (!this.state.isMutable()) {
			throw new IllegalStateException("Registry is not mutable at the moment!");
		}

		entries.put(iResourceLocation, instanceSupplier);
		return new RegistryMessenger<T>(iResourceLocation, instanceSupplier, this);
	}

	@Override
	public Supplier<T> retrieveSupplier(IResourceLocation identifier) {
		Objects.requireNonNull(identifier,
				() -> "If the identifier is null, how do you expect to retrieve anything!? The identifier cannot be null!");
		return entries.get(identifier);
	}

	@Override
	public void setState(IRegistryState state) {
		this.state = state;
	}

	@Override
	public IRegistryState getState() {
		return state;
	}

	@Override
	public IFinalisedRegistry<T> genFinalised() {
		return new FinalisedTypeRegistry<T>(type, identifier, entries);
	}

	@Override
	public Map<IResourceLocation, Supplier<T>> getEntries() {
		return entries;
	}

}