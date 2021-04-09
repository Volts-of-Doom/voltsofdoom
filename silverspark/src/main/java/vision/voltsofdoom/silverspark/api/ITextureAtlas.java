package vision.voltsofdoom.silverspark.api;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import vision.voltsofdoom.silverspark.graphic.Spark;
import vision.voltsofdoom.silverspark.api.IRenderable;

public interface ITextureAtlas {

  void addEntry(IRenderable renderable);
  void setImage(BufferedImage image);

}
