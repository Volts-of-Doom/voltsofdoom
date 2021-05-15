package vision.voltsofdoom.zapbyte.registry;

import vision.voltsofdoom.zapbyte.resource.IID;
import vision.voltsofdoom.zapbyte.resource.ID;

public interface IRegistryEntry2<T> {

  /**
   * @return The {@link ID} identifier of this {@link IRegistryEntry}.
   */
  public IID getIdentifier();

  /**
   * Sets the identifier of this {@link IRegistryEntry}. If called more than once, should throw an
   * {@link Exception}.<br>
   * <br>
   * Should only be called once during initialisation. Modders should <b><i>never</i></b> need to call
   * this, as this will be handled during registration by the {@link VoltsOfDoomCoreSystem}. The
   * exception to this is if you are writing your own registry system <i>completely disregarding the
   * built in system</i>.
   */
  public void setIdentifier(IID identifier);

}
