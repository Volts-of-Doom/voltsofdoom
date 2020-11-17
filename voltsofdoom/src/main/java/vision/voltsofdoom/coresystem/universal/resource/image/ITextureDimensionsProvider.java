package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;

/**
 * Provides the dimensions of a {@link BufferedImage} in primarily the {@link TextureAtlas}.
 * 
 * @author GenElectrovise
 *
 */
public interface ITextureDimensionsProvider {
  public int getWidth();

  public int getHeight();
}
