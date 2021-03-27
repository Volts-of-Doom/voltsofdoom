package vision.voltsofdoom.gamebase.game;


import vision.voltsofdoom.silverspark.game.RenderableEntity;
import vision.voltsofdoom.silverspark.graphic.SparkTexture;
import vision.voltsofdoom.silverspark.graphic.VODColor;

public class GreenBlob extends Entity implements RenderableEntity {


  public GreenBlob(VODColor color, SparkTexture texture, float x, float y, float speed, int width,
      int height, int tx, int ty) {
    super(color, texture, x, y, speed, width, height, tx, ty);
  }

  @Override
  public void input(Entity entity) {

  }
}
