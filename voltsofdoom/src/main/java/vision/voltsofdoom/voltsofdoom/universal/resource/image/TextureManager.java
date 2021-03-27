package vision.voltsofdoom.voltsofdoom.universal.resource.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.silverspark.texture.ITextureAtlas;

public class TextureManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);
  private String rootDirectory;
  private ITextureAtlas atlas;
  private boolean built;

  public TextureManager(String rootDirectory) {
    this.rootDirectory = rootDirectory;
    this.built = false;
  }
  
  public void build() {
    build(false);
  }
  
  public void build(boolean forceRebuild) {
    
    //Messages for the developer
    if (!forceRebuild && built) {
      LOGGER.warn("An attempt to call the TextureManager#build(..) was made when the target TextureManager is already built. The attempt was denied because the forceRebuild flag was not set to true.");
      return;
    }
    if(forceRebuild && built) {
      LOGGER.warn("TextureManager#build(..) has been called with the forceRebuild flag when the target TextureManager is already built. A TextureManager rebuild will start.");
    }
    if(!built) {
      LOGGER.warn("Building the target TextureManager for the first time.");
    }
    LOGGER.warn("Building TextureManager for directory " + rootDirectory + " with flags: forceRebuild=" + forceRebuild);
    
    // Change the built flag
    built = true;
    
    //TODO Do the building bit
    
    return;
  }
  
  public boolean isBuilt() {
    return built;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  public ITextureAtlas getAtlas() {
    return atlas;
  }

}
