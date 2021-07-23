package vision.voltsofdoom.client.silverspark.xnotsilverspark.game;


import vision.voltsofdoom.client.silverspark.graphic.Spark;
import vision.voltsofdoom.client.silverspark.graphic.VODColor;

public class GreenBlob extends AbstractEntity implements IRenderableEntity {
  public GreenBlob(VODColor color, Spark texture, float x, float y, float speed, int width, int height, int tx, int ty) {
    super(color, texture, x, y, speed, width, height, tx, ty);
  }

  @Override
  public void input(AbstractEntity entity) {

  }
}
