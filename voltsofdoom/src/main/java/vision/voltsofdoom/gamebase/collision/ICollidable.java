package vision.voltsofdoom.gamebase.collision;

public interface ICollidable {
  CollidableBoundingBox getBoundingBox();

  float getX();

  float getY();

  float getWidth();

  float getHeight();
}
