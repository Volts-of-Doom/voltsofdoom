package vision.voltsofdoom.zapbyte.loading.registry;

/**
 * The state of an {@link IRegistry} or {@link IFinalisedRegistry}, or some other kind of
 * registry... <br>
 * s *
 * 
 * @author GenElectrovise
 *
 */
public interface IRegistryState {

  public static final IRegistryState ACTIVE = new IRegistryState() {
    @Override
    public boolean isMutable() {
      return true;
    }

    @Override
    public boolean isQueriable() {
      return true;
    }
  };

  public static final IRegistryState UNPOPULATED = new IRegistryState() {
    @Override
    public boolean isMutable() {
      return true;
    }

    @Override
    public boolean isQueriable() {
      return true;
    }
  };

  public static final IRegistryState FROZEN = new IRegistryState() {

    @Override
    public boolean isMutable() {
      return false;
    }

    @Override
    public boolean isQueriable() {
      return false;
    }
  };

  /**
   * In the case of {@link IRegistry}:<br>
   * Can items be added to it?<br>
   * 
   * <br>
   * In the case of {@link IFinalisedRegistry}:<br>
   * Is it being constructed?
   * 
   * @return Whether the registry can be changed.
   */
  public boolean isMutable();

  /**
   * @return Can the {@link IRegistry} be queried?
   */
  public boolean isQueriable();
}
