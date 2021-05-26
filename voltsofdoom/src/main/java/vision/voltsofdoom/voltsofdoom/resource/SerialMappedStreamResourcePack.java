package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import com.google.gson.Gson;

/**
 * Implementation of {@link IResourcePack} which uses
 * 
 * @author GenElectrovise
 *
 */
public class SerialMappedStreamResourcePack implements IResourcePack {

  protected Map<String, InputStream> map;

  public SerialMappedStreamResourcePack(Map<String, InputStream> map) {
    map = Map.copyOf(map);
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(new Gson().toJson(map).getBytes());
  }

  @Override
  public ResourcePackInfoFileResource getPackInfo() {
    InputStream stream = getResource(ResourcePackInfoFileResource.PACK_INFO_JSON).getInputStream();
    return new Gson().fromJson(new InputStreamReader(stream), ResourcePackInfoFileResource.class);
  }

  @Override
  public IResource getResource(String path) {

    InputStream stream = map.get(path);

    return new IResource() {

      @Override
      public InputStream getInputStream() {
        return stream;
      }
      
    };
  }

}
