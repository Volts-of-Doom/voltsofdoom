package vision.voltsofdoom.client.draftclient.voltsofdoom.game;


import vision.voltsofdoom.client.silverspark.graphic.Spark;
import vision.voltsofdoom.client.silverspark.graphic.VODColor;
import vision.voltsofdoom.client.silverspark.xnotsilverspark.game.IRenderableEntity;

public class GreenBlob extends Entity implements IRenderableEntity {


  public GreenBlob(VODColor color, Spark texture, float x, float y, float speed, int width,
      int height, int tx, int ty) {
    super(color, texture, x, y, speed, width, height, tx, ty);
  }

  @Override
  public void input(Entity entity) {

  }
}
