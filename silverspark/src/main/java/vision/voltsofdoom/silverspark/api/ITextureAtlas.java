package vision.voltsofdoom.silverspark.api;

import java.awt.image.BufferedImage;
import vision.voltsofdoom.silverspark.graphic.Catalogue;
import vision.voltsofdoom.silverspark.graphic.Spark;

public interface ITextureAtlas {

  void addEntry(IRenderable renderable);
  void setImage(BufferedImage image);
  Spark getMainSpark();
  void setMainSpark(Spark mainSpark);
  Catalogue getCatalogue();
  void setCatalogue(Catalogue catalogue);

}
