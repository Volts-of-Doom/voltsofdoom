package vision.voltsofdoom.coresystem.universal.resource.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

/**
 * Does some of the "grunt work" for reading from ZIP files.
 * 
 * @author GenElectrovise
 *
 */
public class ZipFileReader {

  private ZipFile zipFile;

  public ZipFileReader(ZipFile zipFile) {
    this.zipFile = zipFile;
  }

  public ZipFile getZipFile() {
    return zipFile;
  }

  public InputStream getStream(String pathToEntry) throws IOException, NullPointerException {
    ZipEntry entry = zipFile.getEntry(pathToEntry);
    Objects.requireNonNull(entry, "ZipEntry null from path: " + pathToEntry);
    
    InputStream stream = zipFile.getInputStream(entry);
    Objects.requireNonNull(stream, "Stream null from ZipEntry: " + entry.getName());
    
    return stream;
  }

  /**
   * Gets a queryable {@link JsonObject} from an {@link InputStream}.
   * 
   * @param stream
   * @return
   */
  public static JsonObject asJson(InputStream stream) {
    return new Gson().fromJson(asJson(stream), JsonObject.class);
  }

  public static JsonReader asJsonReader(InputStream stream) {
    return new JsonReader(new InputStreamReader(stream));
  }
}
