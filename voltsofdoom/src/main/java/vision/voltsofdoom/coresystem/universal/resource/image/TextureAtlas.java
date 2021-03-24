package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.Image;
import java.awt.Point;
import vision.voltsofdoom.silverspark.texture.ITextureAtlas;

public class TextureAtlas implements ITextureAtlas {

  private Image image;

  public TextureAtlas(Image image) {
    this.image = image;
  }

  @Override
  public void addEntry(Image image, Point point) {

  }

  @Override
  public Image getImage() {
    return image;
  }

}
