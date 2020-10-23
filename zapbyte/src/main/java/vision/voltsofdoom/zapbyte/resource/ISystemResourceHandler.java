package vision.voltsofdoom.zapbyte.resource;

import java.io.File;

/**
 * Handles {@link ISystemResource}s so you don't have to!
 * 
 * @author GenElectrovise
 *
 */
public interface ISystemResourceHandler {
  public File getFile(IResource resource);
}
