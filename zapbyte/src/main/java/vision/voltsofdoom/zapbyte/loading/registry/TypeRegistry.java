package vision.voltsofdoom.zapbyte.loading.registry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.event.Stowaway;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.event.RegistryEvent.PollRegistryTypeEventsEvent;
import vision.voltsofdoom.zapbyte.resource.IResourceLocation;

/**
 * Handles the registration of {@link IRegistryEntry}s of type T.
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of {@link IRegistryEntry} that this {@link TypeRegistry} holds.
 */
public class TypeRegistry<T extends IRegistryEntry<T>> implements IRegistry<T> {

  private final IResourceLocation identifier;
  private final RegistryType<T> type;
  private final LinkedHashMap<String, Supplier<T>> entries =
      new LinkedHashMap<String, Supplier<T>>();
  private IRegistryState state;

  public TypeRegistry(IResourceLocation identifier, RegistryType<T> type) {
    this.identifier = identifier;
    this.type = type;
    this.state = IRegistryState.UNPOPULATED;
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
  public RegistryMessenger<T> register(IResourceLocation iResourceLocation,
      Supplier<T> instanceSupplier) {

    if (this.state == IRegistryState.UNPOPULATED) {
      this.setState(IRegistryState.ACTIVE);
    }

    if (!this.getState().isMutable()) {
      throw new IllegalStateException("Registry is not mutable at the moment!");
    }

    entries.put(iResourceLocation.stringify(), instanceSupplier);
    return new RegistryMessenger<T>(iResourceLocation, instanceSupplier, this);
  }

  @Override
  public Supplier<T> retrieveSupplier(IResourceLocation identifier) {

    if (this.getState() == IRegistryState.UNPOPULATED) {
      throw new IllegalStateException(
          "Steady on there! This TypeRegistry hasn't even been populated yet!");
    }

    Objects.requireNonNull(identifier,
        () -> "If the identifier is null, how do you expect to retrieve anything!? The identifier cannot be null!");
    Supplier<T> supp = entries.get(identifier.stringify());
    Objects.requireNonNull(supp, "IResourceLocation '" + identifier.stringify()
        + "' has no bound Supplier<T> (Value was null when queried)");
    return supp;
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
  public Map<String, Supplier<T>> getEntries() {
    return entries;
  }

  @Stowaway
  private static void pollRegistryTypeEvents(PollRegistryTypeEventsEvent event) {
    try {

      // Prioritised types first
      for (int i = 0; i < RegistryTypes.prioritisedTypes.size(); i++) {
        Iterator<IRegistry<? extends IRegistryEntry<?>>> registryI =
            CollectedRegistries.getIterator();
        while (registryI.hasNext()) {
          IRegistry<? extends IRegistryEntry<?>> registry = registryI.next();

          RegistryType<?> type = registry.getType();
          RegistryType<?> comparedType = RegistryTypes.prioritisedTypes.get(i);

          if (type.equals(comparedType)) {
            IFinalisedRegistry<? extends IRegistryEntry<?>> finalisedRegistry =
                registry.genFinalised();
            Registry.register(registry.getRegistryIdentifier(), finalisedRegistry);
            
            ZapByte.LOGGER.debug("Registered " + registry.getRegistryIdentifier());
          }
        }
      }

      // Then do all of the others
      Iterator<IRegistry<? extends IRegistryEntry<?>>> registryI =
          CollectedRegistries.getIterator();
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
