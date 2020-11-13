package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;

/**
 * A basic implementation of {@link ITextureAtlasEntry}.
 * 
 * @author GenElectrovise
 *
 */
public class TextureAtlasEntry implements ITextureAtlasEntry {

  public final BufferedImage image;
  public final String name;

  public TextureAtlasEntry(BufferedImage image, String name) {
    this.image = image;
    this.name = name;
  }

  @Override
  public BufferedImage getImage() {
    return image;
  }

  @Override
  public String getName() {
    return name;
  }

}
