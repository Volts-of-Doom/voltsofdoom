package vision.voltsofdoom.voltsofdoom.universal.resource.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.silverspark.texture.ITextureAtlas;

public class TextureManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);
  private String rootDirectoryPath;
  private ITextureAtlas atlas;
  private boolean built;

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
    }
  }

  /**
   * Builds the {@link TextureManager}. Private so that it can handle its own errors.
   * 
   * @param forceRebuild Sometimes a rebuild is not expected or desired. Use this option to force a
   *        rebuild whether the program likes it or not. <i>"Be wary, adventurer..."</i>
   * @throws FileNotFoundException
   */
  private void inner_build(boolean forceRebuild) throws FileNotFoundException, IllegalStateException {

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

    return;
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
