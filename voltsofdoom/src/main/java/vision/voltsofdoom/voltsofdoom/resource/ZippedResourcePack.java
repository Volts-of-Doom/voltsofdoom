package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.lwjgl.BufferUtils;
import com.google.common.collect.Maps;

/**
 * A {@link DeserialByteBufferResourcePack} with additional functionality to allow loading from a
 * {@link ZipFile} or {@link ZipInputStream}.
 * 
 * @author GenElectrovise
 *
 */
public class ZippedResourcePack extends DeserialByteBufferResourcePack {

  // private static final Logger LOGGER = LoggerFactory.getLogger(ZippedResourcePack.class);

  public ZippedResourcePack(ZipFile zip) {
    this(deriveBuffers(zip));
  }

  public ZippedResourcePack(InputStream stream) {
    this(deriveBuffers(new ZipInputStream(stream)));
  }

  public ZippedResourcePack(Map<String, ByteBuffer> streamMap) {
    super(streamMap);
  }

  public static Map<String, ByteBuffer> deriveBuffers(ZipInputStream zis) {

    Map<String, ByteBuffer> map = Maps.newHashMap();
    byte[] buffer = new byte[2048];

    ZipEntry e;
    try {
      while ((e = zis.getNextEntry()) != null) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // read contents of entry into baos
        int len;
        while ((len = zis.read(buffer)) > 0) {
          baos.write(buffer, 0, len);
        }
        
        map.put(e.getName(), BufferUtils.createByteBuffer(baos.toByteArray().length).put(baos.toByteArray()));
      }
    } catch (IOException io) {
      io.printStackTrace();
    }

    return map;
  }

  public static Map<String, ByteBuffer> deriveBuffers(ZipFile zip) {

    Map<String, ByteBuffer> map = Maps.newHashMap();

    while (zip.entries().hasMoreElements()) {
      try {
        ZipEntry entry = (ZipEntry) zip.entries().nextElement();
        
        InputStream stream = zip.getInputStream(entry);
        byte[] bytes = stream.readAllBytes();
        stream.close();

        map.put(entry.getName(), BufferUtils.createByteBuffer(bytes.length).put(bytes));

      } catch (IOException io) {
        io.printStackTrace();
      }
    }

    return map;
  }

  @Override
  public String toString() {
    return "ZipFileResourcePack[" + getPackInfo().toString() + "]";
  }

}
