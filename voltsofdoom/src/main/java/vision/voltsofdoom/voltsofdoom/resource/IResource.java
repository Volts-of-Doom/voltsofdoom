package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.google.gson.JsonObject;

/**
 * A resource, typically something like an image or sound file in a resource pack.
 * 
 * @author GenElectrovise
 *
 */
public interface IResource {

  /**
   * @return An {@link InputStream} of the relevant contents of this resource. For an image this is
   *         raw bytes. For text files, this is normally a {@link ByteArrayInputStream} (or similar)
   *         of their raw contents. For JSON files, this should be a stringified {@link JsonObject}.
   *         The stream should comprise ALL DATA required to reproduce this resource.
   */
  InputStream getInputStream();
}
