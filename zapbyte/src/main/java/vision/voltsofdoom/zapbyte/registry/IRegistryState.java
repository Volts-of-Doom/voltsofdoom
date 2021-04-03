package vision.voltsofdoom.zapbyte.registry;

/**
 * The state of an {@link IRegistry} or {@link IFinalisedRegistry}, or some other kind of
 * registry... <br>
 * s *
 * 
 * @author GenElectrovise
 *
 */
public interface IRegistryState {

  // Constant registry states

  public static final IRegistryState ACTIVE = new IRegistryState() {
    @Override
    public boolean isMutable() {
      return true;
    }

    @Override
    public boolean isQueriable() {
      return true;
    }

    @Override
    public String getName() {
      return "ACTIVE";
    }
  };

  public static final IRegistryState UNPOPULATED = new IRegistryState() {
    @Override
    public boolean isMutable() {
      return true;
    }

    @Override
    public boolean isQueriable() {
      return false;
    }

    @Override
    public String getName() {
      return "UNPOPULATED";
    }
  };

  public static final IRegistryState FROZEN = new IRegistryState() {

    @Override
    public boolean isMutable() {
      return false;
    }

    @Override
    public boolean isQueriable() {
      return true;
    }

    @Override
    public String getName() {
      return "FROZEN";
    }
  };

  // Interface methods

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

  /**
   * @return The name of this {@link IRegistryState}
   */
  public String getName();

  // Static methods

  public static String stringify(IRegistryState state) {
    StringBuilder builder = new StringBuilder(state.getName() + "{IRegistryState:");

    builder.append(" queriable=" + state.isQueriable());
    builder.append(" mutable=" + state.isMutable());

    builder.append("}");
    return builder.toString();
  }
}
