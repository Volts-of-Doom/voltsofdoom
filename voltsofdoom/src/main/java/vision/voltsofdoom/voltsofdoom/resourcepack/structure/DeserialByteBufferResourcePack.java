package vision.voltsofdoom.voltsofdoom.resourcepack.structure;

import java.nio.ByteBuffer;
import java.util.Map;
import org.lwjgl.BufferUtils;
import com.google.gson.Gson;

/**
 * Implementation of {@link IResourcePack} which uses
 * 
 * @author GenElectrovise
 *
 */
public class DeserialByteBufferResourcePack implements IResourcePack {

  protected Map<String, ByteBuffer> map;

  public DeserialByteBufferResourcePack(Map<String, ByteBuffer> map) {
    map = Map.copyOf(map);
  }

  @Override
  public ByteBuffer getBytes() {
    byte[] bytes = new Gson().toJson(map).getBytes();
    int size = bytes.length;
    return BufferUtils.createByteBuffer(size).put(bytes);
  }

  @Override
  public ResourcePackInfoFileResource getPackInfo() {
    ByteBuffer buf = getResource(ResourcePackInfoFileResource.PACK_INFO_JSON).getBytes();
    return new Gson().fromJson(new String(buf.array()), ResourcePackInfoFileResource.class);
  }

  @Override
  public IResource getResource(String path) {

    ByteBuffer buf = map.get(path);

    return new IResource() {

      @Override
      public ByteBuffer getBytes() {
        return buf;
      }

    };
  }

}
