package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.primitives.Bytes;
import com.google.gson.Gson;

public class ZipFileResourcePack implements IResourcePack {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZipFileResourcePack.class);
  private ZipInputStream zipInputStream;

  @Nullable
  private ResourcePackInfoFileResource packInfo;

  /**
   * 
   * @param fileInputStream A stream of the ZIP file. You can use
   *        <code>new FileInputStream(File)</code> or similar to obtain a stream.
   */
  public ZipFileResourcePack(InputStream inputStream) {
    this.zipInputStream = new ZipInputStream(inputStream);
  }

  @Override
  public InputStream getInputStream() {
    return zipInputStream;
  }

  @Override
  public ResourcePackInfoFileResource getPackInfo() {

    if (packInfo != null) {
      return packInfo;
    }
    
    IResource infoResource = getResource(ResourcePackInfoFileResource.PACK_INFO_JSON);
    this.packInfo = new Gson().fromJson(new InputStreamReader(infoResource.getInputStream()), ResourcePackInfoFileResource.class);

    /*
     * ZipEntry entry; try {
     * 
     * // For every zip entry // getNextEntry positions the pointer at the start of the next entry while
     * ((entry = zipInputStream.getNextEntry()) != null) {
     * 
     * // If the entry is the one that we want if
     * (entry.getName().equals(ResourcePackInfoFileResource.PACK_INFO_JSON)) {
     * 
     * // Get all lines of the file try (Scanner scanner = new Scanner(zipInputStream)) {
     * 
     * byte[] bytes = new byte[0];
     * 
     * while (scanner.hasNextLine()) { Bytes.concat(bytes, scanner.nextLine().getBytes()); }
     * 
     * this.packInfo = new Gson().fromJson(new InputStreamReader(new ByteArrayInputStream(bytes)),
     * ResourcePackInfoFileResource.class); return packInfo; } } } } catch (IOException io) {
     * io.printStackTrace(); }
     */

    LOGGER.warn("Unable to get additional information for a ZipFileResourcePack (cannot provide additional information)");
    return null;
  }

  @Override
  @Nullable
  public IResource getResource(String path) {

    try {
      for (ZipEntry e; (e = zipInputStream.getNextEntry()) != null;) {
        if (e.getName().equals(path)) {
          return new IResource() {

            @Override
            public InputStream getInputStream() {
              return zipInputStream;
            }
          };
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public String toString() {
    return "ZipFileResourcePack[" + getPackInfo().toString() + "]";
  }

}
