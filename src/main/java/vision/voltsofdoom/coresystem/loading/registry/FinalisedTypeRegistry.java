package vision.voltsofdoom.coresystem.loading.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * The finalised and immutable version of a {@link TypeRegistry} which will be
 * added to the {@link Registry}. Still contains an {@link IRegistryState},
 * which is {@value IRegistryState#ACTIVE} whilst being created, but is changed
 * to {@value IRegistryState#FROZEN} once creation has finished.
 */
public class FinalisedTypeRegistry<T extends IRegistryEntry<T>> implements IFinalisedRegistry<T> {

	private final ResourceLocation identifier;
	private final RegistryType<?> type;
	private IRegistryState state;
	private boolean finalised;

	private final LinkedHashMap<ResourceLocation, Supplier<T>> entries;

	public FinalisedTypeRegistry(RegistryType<?> type, ResourceLocation identifier,
			LinkedHashMap<ResourceLocation, Supplier<T>> entries) {
		this.state = IRegistryState.ACTIVE;
		this.finalised = false;
		this.identifier = identifier;
		this.type = type;
		this.entries = entries;
	}

	@Override
	public ResourceLocation getRegistryIdentifier() {
		return identifier;
	}

	@Override
	public RegistryType<?> getType() {
		return type;
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

	@Override
	public void lock() {
		this.finalised = true;
		this.state = IRegistryState.FROZEN;
	}

	public boolean isFinalised() {
		return finalised;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inject(IFinalisedRegistry<?> finalisedRegistry) {
		for (ResourceLocation location : finalisedRegistry.getEntries().keySet()) {
			this.entries.put(location, (Supplier<T>) finalisedRegistry.getEntries().get(location));
		}
	}

	@Override
	public Map<ResourceLocation, Supplier<T>> getEntries() {
		return entries;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinalisedTypeRegistry: " + identifier);

		entries.forEach((id, sup) -> {
			builder.append("\n" + id + " = " + sup.get().toString());
		});

		builder.append("\n");
		return builder.toString();
	}
}
