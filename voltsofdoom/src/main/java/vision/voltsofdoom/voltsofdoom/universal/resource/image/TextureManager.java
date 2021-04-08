package vision.voltsofdoom.voltsofdoom.universal.resource.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.silverspark.texture.ITextureAtlas;
import vision.voltsofdoom.voltsofdoom.universal.resource.json.JsonSerialisation;
import vision.voltsofdoom.voltsofdoom.universal.resource.zip.ZipFileReader;

public class TextureManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);

  private String rootDirectoryPath;
  private ITextureAtlas atlas;
  private boolean built;
  private final Gson GSON = new Gson();
  private File rootDirectoryFile;

  public TextureManager(String rootDirectory) {
    this.rootDirectoryPath = rootDirectory;
    this.built = false;
  }

  public void build() {
    build(false);
  }

  /**
   * Conceals a call to {@link #inner_build(boolean)} and handles errors for that call.
   * 
   * @param forceRebuild
   */
  public void build(boolean forceRebuild) {
    try {

      inner_build(false);

    } catch (FileNotFoundException fi) {
      LOGGER.error("An error has occurred building the TextureManager! (An expected file cannot be found)");
      fi.printStackTrace();
    } catch (IllegalStateException il) {
      LOGGER.error("An error has occurred building the TextureManager! (Something was in the wrong state)");
      il.printStackTrace();
    } catch (NullPointerException nu) {
      LOGGER.error("An error has occurred building the TextureManager! (An object was null)");
      nu.printStackTrace();
    } catch (ZipException zi) {
      LOGGER.error("An error has occurred building the TextureManager! (A ZIP file could not be read)");
      zi.printStackTrace();
    } catch (IOException io) {
      LOGGER.error("An error has occurred building the TextureManager! (An I/O operation failed)");
      io.printStackTrace();
    }
  }

  /**
   * Builds the {@link TextureManager}. Private so that it can handle its own errors.
   * 
   * @param forceRebuild Sometimes a rebuild is not expected or desired. Use this option to force a
   *        rebuild whether the program likes it or not. <i>"Be wary, adventurer..."</i>
   * @throws IOException
   * @throws ZipException
   */
  private void inner_build(boolean forceRebuild) throws FileNotFoundException, IllegalStateException, ZipException, IOException {

    // Messages for the developer
    if (!forceRebuild && built) {
      LOGGER.warn("An attempt to call the TextureManager#build(..) was made when the target TextureManager is already built. The attempt was denied because the forceRebuild flag was not set to true.");
      return;
    }
    if (forceRebuild && built) {
      LOGGER.warn("TextureManager#build(..) has been called with the forceRebuild flag when the target TextureManager is already built. A TextureManager rebuild will start.");
    }
    if (!built) {
      LOGGER.warn("Building the target TextureManager for the first time.");
    }
    LOGGER.warn("Building TextureManager for directory " + rootDirectoryPath + " with flags: forceRebuild=" + forceRebuild);

    // Change the built flag
    built = true;

    // Get an instance of the root file. Check exists and is a directory.
    rootDirectoryFile = new File(rootDirectoryPath);
    if (!rootDirectoryFile.exists())
      throw new FileNotFoundException("Cannot find root directory file for TextureManager " + rootDirectoryPath);
    if (!rootDirectoryFile.isDirectory())
      throw new IllegalStateException("The TextureManager root file " + rootDirectoryPath + " is not a directory.");

    // Get a list of files in the directory
    File[] children = rootDirectoryFile.listFiles((file, name) -> name.endsWith(".zip"));

    // Make a map of the manifests
    Map<String, TexturePackManifest> manifests = new HashMap<>();

    // Populate the map
    getListOfJavaObjectTexturePackManifests(children, manifests);

    return;
  }

  private void getListOfJavaObjectTexturePackManifests(File[] children, Map<String, TexturePackManifest> manifests) throws ZipException, IOException {
    for (File child : children) {

      // Get a new ZIP reader
      ZipFileReader reader = new ZipFileReader(new ZipFile(child));

      // If there is no manifest, skip this file
      if (reader.getZipFile().getEntry("manifest.json") == null) {
        LOGGER.error("manifest.json is null for file " + child + " so it will not be loaded");
        continue;
      }

      // Get a stream of the contents
      InputStream manifestStream = reader.getStream("manifest.json", "Error reading manifest for ZIP file " + child);
      TexturePackManifest manifest = GSON.fromJson(new InputStreamReader(manifestStream), TexturePackManifest.class);

      manifests.put(child.getAbsolutePath(), manifest);
    }
  }

  public boolean isBuilt() {
    return built;
  }

  public String getRootDirectory() {
    return rootDirectoryPath;
  }

  public ITextureAtlas getAtlas() {
    return atlas;
  }

}
