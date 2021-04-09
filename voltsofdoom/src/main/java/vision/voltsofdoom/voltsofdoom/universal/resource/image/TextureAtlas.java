package vision.voltsofdoom.voltsofdoom.universal.resource.image;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.api.ITextureAtlas;

public class TextureAtlas implements ITextureAtlas {

  private Image image;

  public TextureAtlas(Image image) {
    this.image = image;
  }

  @Override
  public void addEntry(IRenderable renderable) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setImage(BufferedImage image) {
    // TODO Auto-generated method stub
    
  }

}
