package vision.voltsofdoom.zapbyte.loading.registry;

import vision.voltsofdoom.zapbyte.resource.IResourceLocation;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public interface IRegistryEntry<T> {

  /**
   * @return The {@link ResourceLocation} identifier of this {@link IRegistryEntry}.
   */
  public IResourceLocation getIdentifier();

  /**
   * Sets the identifier of this {@link IRegistryEntry}. If called more than once, should throw an
   * {@link Exception}.<br>
   * <br>
   * Should only be called once during initialisation. Modders should <b><i>never</i></b> need to
   * call this, as this will be handled during registration by the {@link VoltsOfDoomCoreSystem}.
   * The exception to this is if you are writing your own registry system <i>completely disregarding
   * the built in system</i>.
   */
  public void setIdentifier(IResourceLocation identifier);
}
