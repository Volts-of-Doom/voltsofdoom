package vision.voltsofdoom.voltsofdoom.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Nullable;
import vision.voltsofdoom.zapbyte.resource.ID;

public class ZipFileResourcePack implements IResourcePack {

  private ZipInputStream zipInputStream;
  private int loadingPriority;
  private String displayName;
  private ID identifier;

  /**
   * 
   * @param fileInputStream A stream of the ZIP file. You can use
   *        <code>new FileInputStream(File)</code> or similar to obtain a stream.
   */
  public ZipFileResourcePack(InputStream inputStream, int loadingPriority, String displayName, ID identifier) {
    this.zipInputStream = new ZipInputStream(inputStream);
    this.loadingPriority = loadingPriority;
    this.displayName = displayName;
    this.identifier = identifier;
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
      // getNextEntry positions the pointer at the start of the next entry
      while ((entry = zipInputStream.getNextEntry()) != null) {

        // If the entry is the one that we want
        if (entry.getName().equals(ResourcePackInfoFileResource.PACK_INFO_JSON)) {

          // Get all lines of the file
          try (Scanner scanner = new Scanner(zipInputStream)) {
            while (scanner.hasNextLine()) {
              System.err.println("READ LINES NOT IMPL ZipFResourPack #43");
              System.out.println(scanner.nextLine());
            }
          }
        }
      }
    } catch (IOException io) {
      io.printStackTrace();
    }

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
  public int getLoadingPriority() {
    return loadingPriority;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public ID getIdentifier() {
    return identifier;
  }

}
