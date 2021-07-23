package vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.loading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.structure.ResourcePackManifestFileResource;
import vision.voltsofdoom.client.silverspark.api.ITextureAtlas;

public class TextureResourceLoader extends RegisterableResourceLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextureResourceLoader.class);
  public static final String TEXTURE_MANIFEST_LOCATION = "textures/textures.json";
  public static final String TEXTURE_INTERNAL_PATH_PREFIX = "textures/";

  private final Gson GSON = new GsonBuilder().registerTypeAdapter(ResourcePackManifestFileResource.class, new ResourcePackManifestFileResource.Serializer()).setPrettyPrinting().create();

  private ITextureAtlas atlas;
  private File rootDirectoryFile;

  public TextureResourceLoader(String rootDirectory) {
    super(rootDirectory, false);
  }

  public void load() {
    load(false);
  }

  /**
   * Conceals a call to {@link #inner_load(boolean)} and handles errors for that call.
   * 
   * @param forceReload
   */
  @Override
  public void load(boolean forceReload) {
    try {

      inner_load(forceReload);

    } catch (FileNotFoundException fi) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (An expected file cannot be found)");
      fi.printStackTrace();
    } catch (IllegalStateException il) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (Something was in the wrong state)");
      il.printStackTrace();
    } catch (NullPointerException nu) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (An object was null)");
      nu.printStackTrace();
    } catch (ZipException zi) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (A ZIP file could not be read)");
      zi.printStackTrace();
    } catch (IOException io) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (An I/O operation failed)");
      io.printStackTrace();
    }
  }

  /**
   * Builds the {@link TextureResourceLoader}. Private so that it can handle exceptions more easily.
   * 
   * @param forceReload Sometimes a rebuild is not expected or desired. Use this option to force a
   *        rebuild whether the program likes it or not. <i>"Be wary, adventurer..."</i>
   * @throws IOException
   * @throws ZipException
   */
  private void inner_load(boolean forceReload) throws FileNotFoundException, IllegalStateException, ZipException, IOException {

    // Messages for the developer
    if ((!forceReload) && loaded) {
      LOGGER.warn("An attempt to call the TextureResourceLoader#load(..) was made when the target TextureResourceLoader is already loaded. The attempt was denied because the forceReload flag was not set to true.");
      return;
    }
    if (forceReload && loaded) {
      LOGGER.warn("TextureResourceLoader#load(..) has been called with the forceReload flag when the target TextureResourceLoader is already loaded. A TextureResourceLoader rebuild will start.");
    }
    if (!loaded) {
      LOGGER.warn("Loading the target TextureResourceLoader for the first time.");
    }
    LOGGER.warn("Loading TextureResourceLoader for directory " + rootDirectory + " with flags: forceReload=" + forceReload);

    // Change the built flag
    loaded = true;

    // Get an instance of the root file. Check exists and is a directory.
    rootDirectoryFile = new File(rootDirectory);
    if (!rootDirectoryFile.exists())
      throw new FileNotFoundException("Cannot find root directory file for TextureResourceLoader " + rootDirectory);
    if (!rootDirectoryFile.isDirectory())
      throw new IllegalStateException("The TextureResourceLoader root file " + rootDirectory + " is not a directory.");

    // Read each image. Make a list of nodes with their dimensions.

    // Order nodes by width

    // Fit into layout

    // For each closed node: get name -> get PNG -> add to atlas image -> add coordinate reference

    // Write atlas image to system

    return;
  }

  public boolean isBuilt() {
    return loaded;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  public ITextureAtlas getAtlas() {
    return atlas;
  }

}
