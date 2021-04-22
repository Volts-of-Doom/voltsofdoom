package vision.voltsofdoom.zapbyte.registry2;

public interface IRegistryStatus {

  boolean isMutable();

  String getName();

  public static final IRegistryStatus IMMUTABLE = new IRegistryStatus() {

    @Override
    public boolean isMutable() {
      return false;
    }

    @Override
    public String getName() {
      return "IMMUTABLE";
    }
  };
  
  public static final IRegistryStatus MUTABLE = new IRegistryStatus() {

    @Override
    public boolean isMutable() {
      return true;
    }

    @Override
    public String getName() {
      return "MUTABLE";
    }
  };

}
