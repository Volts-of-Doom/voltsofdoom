package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * A resource, typically something like an image or sound file in a resource pack.
 * 
 * @author GenElectrovise
 *
 */
public interface IResource {

  /**
   * @return An {@link InputStream} of the relevant contents of this resource - normally a copy to
   *         prevent race conditions and unwanted editing. The stream should comprise all data
   *         required to reproduce this resource. <br>
   *         <br>
   *         Examples of formatting:
   * 
   *         <ul>
   *         <li>Image: bytes (i.e {@link ByteArrayInputStream})
   *         <li>Text: bytes (i.e. {@link ByteArrayInputStream}) {@link ByteArrayInputStream}
   *         <li>JSON: convert to text, then stream bytes (i.e. {@link ByteArrayInputStream})
   *         <li>Object: convert to JSON, then stream bytes (i.e. {@link Gson#toJson()} then
   *         {@link ByteArrayInputStream})
   *         </ul>
   */
  InputStream getInputStream();
}
