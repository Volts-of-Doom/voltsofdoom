package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;

/**
 * A {@link SerialMappedStreamResourcePack} with additional functionality to allow loading from a
 * {@link ZipFile} or {@link ZipInputStream}.
 * 
 * @author GenElectrovise
 *
 */
public class ZippedResourcePack extends SerialMappedStreamResourcePack {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZippedResourcePack.class);

  public ZippedResourcePack(ZipFile zip) {
    this(deriveStreams(zip));
  }

  public ZippedResourcePack(InputStream stream) {
    this(deriveStreams(new ZipInputStream(stream)));
  }

  public ZippedResourcePack(Map<String, InputStream> streamMap) {
    super(streamMap);
  }

  public static Map<String, InputStream> deriveStreams(ZipInputStream zis) {

    Map<String, InputStream> map = Maps.newHashMap();
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
        
        map.put(e.getName(), new ByteArrayInputStream(baos.toByteArray()));
      }
    } catch (IOException io) {
      io.printStackTrace();
    }

    return map;
  }

  public static Map<String, InputStream> deriveStreams(ZipFile zip) {

    Map<String, InputStream> map = Maps.newHashMap();

    while (zip.entries().hasMoreElements()) {
      try {
        ZipEntry entry = (ZipEntry) zip.entries().nextElement();
        Scanner scanner = new Scanner(zip.getInputStream(entry));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (scanner.hasNextLine()) {
          baos.write(scanner.nextLine().getBytes());
        }

        map.put(entry.getName(), new ByteArrayInputStream(baos.toByteArray()));

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
