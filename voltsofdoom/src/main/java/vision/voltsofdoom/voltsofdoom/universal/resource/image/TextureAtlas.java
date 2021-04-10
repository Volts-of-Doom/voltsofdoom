package vision.voltsofdoom.voltsofdoom.universal.resource.image;

import vision.voltsofdoom.silverspark.texture.ITextureAtlas;
import vision.voltsofdoom.silverspark.texture.ITextureData;

/**
 * Implementation of {@link ITextureAtlas}. Regurgitates textures from a master image when given a
 * key.
 * 
 * @author GenElectrovise
 *
 */
public class TextureAtlas implements ITextureAtlas {

  public TextureAtlas() {}

  @Override
  public ITextureData getData(String name) {
    return null;
  }

}
