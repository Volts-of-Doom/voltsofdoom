package vision.voltsofdoom.voltsofdoom.game;


import vision.voltsofdoom.silverspark.game.IRenderableEntity;
import vision.voltsofdoom.silverspark.graphic.Spark;
import vision.voltsofdoom.silverspark.graphic.VODColor;

public class GreenBlob extends Entity implements IRenderableEntity {


  public GreenBlob(VODColor color, Spark texture, float x, float y, float speed, int width,
      int height, int tx, int ty) {
    super(color, texture, x, y, speed, width, height, tx, ty);
  }

  @Override
  public void input(Entity entity) {

  }
}
