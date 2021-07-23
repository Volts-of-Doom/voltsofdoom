package vision.voltsofdoom.client.silverspark.api;

import java.awt.image.BufferedImage;
import vision.voltsofdoom.client.silverspark.graphic.Catalogue;
import vision.voltsofdoom.client.silverspark.graphic.Spark;

public interface ITextureAtlas {

  void addEntry(IRenderable renderable);
  void setImage(BufferedImage image);
  Spark getMainSpark();
  void setMainSpark(Spark mainSpark);
  Catalogue getCatalogue();
  void setCatalogue(Catalogue catalogue);

}
