package vision.voltsofdoom.coresystem.universal.resource.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.google.gson.stream.JsonReader;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;

/**
 * Does some of the "grunt work" for reading from ZIP files.
 * 
 * @author GenElectrovise
 *
 */
public class ZipFileReader {

  private ZipFile zipFile;

  public ZipFileReader(ZipFile zipFile) {
    Objects.requireNonNull(zipFile, () -> "ZipFile cannot be null for ZipFileReader constructor");
    this.zipFile = zipFile;
  }

  public ZipFile getZipFile() {
    return zipFile;
  }

  public InputStream getStream(String pathToEntry) throws IOException, NullPointerException {
    return getStream(pathToEntry, "Unable to reach file " + pathToEntry);
  }

  public InputStream getStream(String pathToEntry, String failureMessage) {

    InputStream stream = null;

    try {
      ZipEntry entry = zipFile.getEntry(pathToEntry);
      Objects.requireNonNull(entry, "ZipEntry null from path: " + pathToEntry);

      stream = zipFile.getInputStream(entry);
      Objects.requireNonNull(stream, "Stream null from ZipEntry: " + entry.getName());

    } catch (Exception e) {
      // Log error
      VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
          .error("Error whilst fetching a stream from a ZipFile: " + failureMessage);
      // Exception message
      VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
          .trace("Exception message:" + e.getMessage());
      // File
      VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
          .trace("ZipFile:" + zipFile != null ? zipFile.getName() : "null");
      // Expected entry
      VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
          .trace("Path to entry:" + pathToEntry);
      // Stack trace
      for (StackTraceElement elem : e.getStackTrace()) {
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .trace("-" + elem.getMethodName() + "#" + elem.getLineNumber());
      }
    }

    return stream;
  }

  public static JsonReader asJsonReader(InputStream stream) {
    return new JsonReader(new InputStreamReader(stream));
  }
}
