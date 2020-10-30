package vision.voltsofdoom.zapbyte.resource;

import java.io.File;
import java.io.FileNotFoundException;
import vision.voltsofdoom.zapbyte.log.Loggers;

public class ZBSystemResourceHandler implements ISystemResourceHandler {

  public static ZBSystemResourceHandler instance = new ZBSystemResourceHandler();

  @Override
  public File getFile(IResource resource) {
    return getFile_canIgnoreMissing(resource, false);
  }

  /**
   * {@link #getFile(IResource)}, but can specify whether to ignore whether the {@link File} exists
   * or not.
   * 
   * @param resource The location of the {@link File} to retrieve.
   * @param ignoreMissing If the {@link File} doesn't exist, setting this to <code>true</code> will
   *        prevent throwing an exception.
   * @return The {@link File}.
   * @throws FileNotFoundException If shouldn't <code>ignoreMissing</code>, and the {@link File}
   *         doesn't exist.
   */
  public File getFile_canIgnoreMissing(IResource resource, boolean ignoreMissing) {
    try {

      File file = new File(resource.getPath());
      boolean exists = file.exists();

      // If should ignore missing
      if (ignoreMissing) {
        return file;
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
