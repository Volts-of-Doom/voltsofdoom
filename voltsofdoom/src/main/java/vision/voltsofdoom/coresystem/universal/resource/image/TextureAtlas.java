package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import vision.voltsofdoom.zapbyte.log.Loggers;

public class TextureAtlas {

  private BufferedImage image;
  private HashMap<String, ICoordinateAlignedImageDataProvider> bindings =
      new HashMap<String, ICoordinateAlignedImageDataProvider>();

  public TextureAtlas(List<ITextureAtlasEntry> rawAtlasEntries) {
    this.image = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
    Loggers.ZAPBYTE.severe("We have some raw atlas entries, but no atlas is being stitched!");
  }

  protected BufferedImage getAtlasImage() {
    return image;
  }

  public HashMap<String, ICoordinateAlignedImageDataProvider> getBindings() {
    return bindings;
  }

  public BufferedImage fetch(String key) {
    ICoordinateAlignedImageDataProvider provider = bindings.get(key);
    
    return image.getSubimage((int) Math.round(provider.getCoordinate().getX()),
        (int) Math.round(provider.getCoordinate().getY()), provider.getWidth(),
        provider.getHeight());
  }
}
