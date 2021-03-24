package vision.voltsofdoom.silverspark.texture;

import java.awt.Image;
import java.awt.Point;

public interface ITextureAtlas {

  void addEntry(Image image, Point point);

  Image getImage();
}
