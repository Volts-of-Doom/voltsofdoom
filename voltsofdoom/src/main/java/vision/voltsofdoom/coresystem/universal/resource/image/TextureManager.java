package vision.voltsofdoom.coresystem.universal.resource.image;

import java.util.ArrayList;
import java.util.List;
import vision.voltsofdoom.zapbyte.resource.IResource;

public class TextureManager {
  
  private final TextureAtlas atlas;
  
  public TextureManager(IResource parentFile) {
    this.atlas = new TextureAtlas(createAtlasEntries());
  }
  
  private List<ITextureAtlasEntry> createAtlasEntries() {
    ArrayList<ITextureAtlasEntry> images = new ArrayList<ITextureAtlasEntry>();
    
    return images;
  }

  public TextureAtlas getAtlas() {
    return atlas;
  }
}
