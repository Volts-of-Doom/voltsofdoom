package vision.voltsofdoom.voltsofdoom.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileResourcePack implements IResourcePack {

  private ZipInputStream zipInputStream;

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

    ZipEntry entry;
    try {

      // For every zip entry
      while ((entry = zipInputStream.getNextEntry()) != null) {
        if (entry.getName().equals(ResourcePackInfoFileResource.PACK_INFO_JSON)) {
          
          //GET THE CONTENTS
        }
      }
    } catch (IOException io) {
      io.printStackTrace();
    }

    return null;
  }

  @Override
  public IResource getResource() {
    return null;
  }

}
