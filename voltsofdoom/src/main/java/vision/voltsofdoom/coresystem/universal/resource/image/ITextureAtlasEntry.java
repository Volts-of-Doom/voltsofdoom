package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;

/**
 * An entry in the {@link TextureAtlas}.
 * 
 * @author GenElectrovise
 *
 */
public interface ITextureAtlasEntry {
  public BufferedImage getImage();

  public String getName();
}
