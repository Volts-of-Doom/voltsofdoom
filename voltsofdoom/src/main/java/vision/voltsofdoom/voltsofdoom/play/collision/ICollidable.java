package vision.voltsofdoom.voltsofdoom.play.collision;

public interface ICollidable {
  CollidableBoundingBox getBoundingBox();

  float getX();

  float getY();

  float getWidth();

  float getHeight();
}
