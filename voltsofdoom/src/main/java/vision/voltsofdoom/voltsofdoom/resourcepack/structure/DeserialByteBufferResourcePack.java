package vision.voltsofdoom.voltsofdoom.resourcepack.structure;

import java.nio.ByteBuffer;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import vision.voltsofdoom.zapbyte.resource.ID;
import vision.voltsofdoom.zapbyte.resource.IID;

/**
 * Implementation of {@link IResourcePack} which uses a {@link Map} of {@link ByteBuffer}s to store
 * {@link IResource}s.
 * 
 * @author GenElectrovise
 *
 */
public class DeserialByteBufferResourcePack implements IResourcePack {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(DeserialByteBufferResourcePack.class);

  protected Map<String, ByteBuffer> map;
  protected Map<IID, IResource> resources;
  protected int rHash = 0;

  public DeserialByteBufferResourcePack(Map<String, ByteBuffer> map) {
    this.map = (map == null ? Maps.newHashMap() : Map.copyOf(map));
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
    byte[] bytes = buf.array();
    return new Gson().fromJson(new String(bytes), ResourcePackInfoFileResource.class);
  }

  @Override
  public IResource getResource(String path) {

    if (map == null) {
      LOGGER.error(DeserialByteBufferResourcePack.class.getSimpleName() + "#map is null (de-nulling)");
      map = Maps.newHashMap();
    }

    ByteBuffer buf = map.get(path);

    if (buf == null)
      throw new NullPointerException("No such resource as " + path + " could be found (null)");

    return IResource.generateResourceFromByteBuffer(buf);
  }

  @Override
  public Map<IID ,IResource> getResources() {
    Map<IID, IResource> resources = Maps.newHashMap();
    map.forEach((str, buffer) -> {
      resources.put(ID.fromString(str), IResource.generateResourceFromByteBuffer(buffer));
    });
    
    this.resources = resources;

    return this.resources;
  }

}
