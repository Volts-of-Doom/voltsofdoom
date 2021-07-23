package vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.structure;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.google.gson.Gson;

/**
 * A resource, typically something like an image or sound file in a resource pack.
 * 
 * @author GenElectrovise
 *
 */
public interface IResource {

  /**
   * @return A {@link ByteBuffer} of the relevant contents of this resource - normally a copy to
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
  ByteBuffer getBytes();


  public static IResource generateResourceFromBytes(byte[] bytes) {
    return generateResourceFromByteBuffer(ByteBuffer.wrap(bytes));
  }
  
  public static IResource generateResourceFromByteBuffer(ByteBuffer buffer) {
    
    return new IResource() {      
      @Override
      public ByteBuffer getBytes() {
        return buffer;
      }
    };
  }
  
  public static void generateResourceAndAddToList(ByteBuffer buffer, List<IResource> list) {
    list.add(generateResourceFromByteBuffer(buffer));
  }
}
