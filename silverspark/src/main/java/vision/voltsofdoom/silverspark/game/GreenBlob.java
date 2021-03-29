package vision.voltsofdoom.silverspark.game;


import vision.voltsofdoom.silverspark.graphic.SparkTexture;
import vision.voltsofdoom.silverspark.graphic.VODColor;

public class GreenBlob extends AbstractEntity implements IRenderableEntity {
  public GreenBlob(VODColor color, SparkTexture texture, float x, float y, float speed, int width, int height, int tx, int ty) {
    super(color, texture, x, y, speed, width, height, tx, ty);
  }

  @Override
  public void input(AbstractEntity entity) {

  }
}
