package vision.voltsofdoom.zapbyte.resource;

import java.io.File;

public class ZBSystemResourceHandler implements ISystemResourceHandler {
  
  public static ISystemResourceHandler instance = new ZBSystemResourceHandler();

  @Override
  public File getFile(IResource resource) {
    /*
     * if (!IResource.isValid(resource)) { Loggers.ZAPBYTE_LOADING_RESOURCE.severe("Invalid " +
     * IResource.class.getSimpleName() + " passed to " + getClass().getSimpleName() +
     * "#getFile()!"); }
     */
    return new File(resource.getPath());
  }

}
