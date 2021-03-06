package vision.voltsofdoom.zapbyte.loading.registry;

import java.util.Map;
import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IResourceLocation;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

/**
 * A mutable (non finalised) registry.
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of {@link IRegistryEntry} being registered.
 */
public interface IRegistry<T extends IRegistryEntry<T>> {

  /**
   * @return The contents of this {@link IFinalisedRegistry}.
   */
  public Map<String, Supplier<T>> getEntries();

  /**
   * @return The {@link ResourceLocation} identifier of this {@link IRegistry}.
   */
  public IResourceLocation getRegistryIdentifier();

  /**
   * @return The {@link RegistryType} of this {@link IRegistry}
   */
  public RegistryType<?> getType();

  /**
   * @return the supplier of the {@link IRegistryEntry} identified by the given
   *         {@link ResourceLocation}.
   */
  public Supplier<T> retrieveSupplier(IResourceLocation identifier);

  /**
   * @return The current {@link IRegistryState} of this {@link IRegistry}
   */
  public IRegistryState getState();

  /**
   * Sets the {@link IRegistryState} of this {@link IRegistry}
   * 
   * @param state
   */
  public void setState(IRegistryState state);

  /**
   * Registers the {@link Supplier} to this {@link IRegistry}, binding it to the given
   * {@link ResourceLocation}
   * 
   * @return
   */
  public RegistryMessenger<T> register(IResourceLocation identifier, Supplier<T> item);

  /**
   * 
   * @return A finalised version of this registry.
   */
  public IFinalisedRegistry<T> genFinalised();
}
