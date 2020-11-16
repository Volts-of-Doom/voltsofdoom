package vision.voltsofdoom.coresystem.universal.resource.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.google.gson.stream.JsonReader;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.main.ZapByteExceptionFormatter;

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

    pathToEntry.replace("\\", "/");

    InputStream stream = null;

    try {
      ZipEntry entry = zipFile.getEntry(pathToEntry);

      // In case of null entry
      if (entry == null) {
        ZapByteExceptionFormatter.onError_withMessage_withArgs(
            new NullPointerException(
                "ZipEntry null from path. This will result in a nulled stream."),
            VoltsOfDoomCoreSystem.getInstance().getApplicationLogger(), failureMessage,
            new String[] {"path to entry", pathToEntry});

        // Log entries
        Enumeration<? extends ZipEntry> allEntries = zipFile.entries();
        VoltsOfDoomCoreSystem.easyDebug("ZipFile entries are as follows:");
        while (allEntries.hasMoreElements()) {
          ZipEntry zipEntry = (ZipEntry) allEntries.nextElement();
          VoltsOfDoomCoreSystem.easyDebug("> " + zipEntry.getName());
        }
      }

      stream = zipFile.getInputStream(entry);

    } catch (Exception e) {

      ZapByteExceptionFormatter.onError_withMessage_withArgs(e,
          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger(),
          "Error whilst fetching stream from ZipFile!", new String[] {"ZipFile",
              zipFile != null ? zipFile.getName() : "null", "Path to entry", pathToEntry});
    }

    return stream;
  }

  public static JsonReader asJsonReader(InputStream stream) {
    return new JsonReader(new InputStreamReader(stream));
  }
}
