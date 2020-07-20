package vision.voltsofdoom.coresystem.loading.registry;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * Handles the registration of {@link IRegistryEntry}s of type T.
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of {@link IRegistryEntry} that this {@link TypeRegistry}
 *        holds.
 */
public class TypeRegistry<T extends IRegistryEntry<T>> implements IRegistry<T> {

	private final ResourceLocation identifier;
	private final RegistryType<T> type;
	private final LinkedHashMap<ResourceLocation, Supplier<T>> entries = new LinkedHashMap<ResourceLocation, Supplier<T>>();
	private IRegistryState state;

	public TypeRegistry(ResourceLocation identifier, RegistryType<T> type) {
		this.identifier = identifier;
		this.type = type;
		this.state = IRegistryState.ACTIVE;
		TypeRegistries.submit(this);
	}

	@Override
	public ResourceLocation getRegistryIdentifier() {
		return this.identifier;
	}

	@Override
	public RegistryType<T> getType() {
		return type;
	}

	@Override
	public RegistryObjectRetriever<T> register(ResourceLocation identifier, Supplier<T> instanceSupplier) {

		if (!this.state.isMutable()) {
			throw new IllegalStateException("Registry is not mutable at the moment!");
		}

		entries.put(identifier, instanceSupplier);
		return new RegistryObjectRetriever<T>(identifier, instanceSupplier, this);
	}

	@Override
	public Supplier<T> retrieveSupplier(ResourceLocation identifier) {
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

	public boolean isFinal() {
		System.out.println("TODO isFinal TypeRegistry");
		return false;
	}

}