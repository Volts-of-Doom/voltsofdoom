package vision.voltsofdoom.zapbyte.resource;

import java.io.File;
import java.io.FileNotFoundException;
import vision.voltsofdoom.zapbyte.log.Loggers;

public class ZBSystemResourceHandler implements ISystemResourceHandler {

  public static ZBSystemResourceHandler instance = new ZBSystemResourceHandler();

  @Override
  public File getFile(IResource resource) {
    return getFile(resource, false);
  }

  public File getFile(IResource resource, boolean ignoreMissing) {
    return getFile(resource, ignoreMissing, false);
  }

  /**
   * Gets a file on the file system.
   * 
   * @param resource The location of the {@link File} to retrieve.
   * @param ignoreMissing If the {@link File} doesn't exist, setting this to <code>true</code> will
   *        prevent throwing an exception.
   * @param createIfNotPresent Should make the directory if the resource points to a directory which
   *        is not present? This is applied AFTER ignoreMissing, so if a file should ignoreMissing,
   *        but doesn't exist, it will NOT be made.
   * @return The {@link File}.
   * @throws FileNotFoundException If shouldn't <code>ignoreMissing</code>, and the {@link File}
   *         doesn't exist.
   */
  public File getFile(IResource resource, boolean ignoreMissing, boolean createIfNotPresent) {
    try {

      File file = new File(resource.getPath());
      boolean exists = file.exists();

      // If should ignore missing
      if (ignoreMissing) {
        return file;
      }

      // If should create
      if (createIfNotPresent) {
        file.mkdirs();
        exists = file.exists();
      }

      // If the file doesn't exist (and isn't ignored)
      if (!exists) {

        throw new FileNotFoundException(
            "Could not return File denoted by IResource path " + resource.getPath());
      }

      // If the file exists, return it.
      return file;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      Loggers.ZAPBYTE_LOADING_RESOURCE.throwing(getClass().getSimpleName(), "getFile", e);
      return null;
    }
  }

}
