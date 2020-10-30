package vision.voltsofdoom.zapbyte.resource;

import java.io.File;

/**
 * Handles {@link ISystemResource}s so you don't have to!
 * 
 * @author GenElectrovise
 *
 */
public interface ISystemResourceHandler {
  /**
   * Retrieves a {@link File} from the file system.
   * 
   * @param resource An {@link IResource} denoting where to find the desired {@link File}.
   * @return The {@link File}.
   */
  public File getFile(IResource resource);
}
