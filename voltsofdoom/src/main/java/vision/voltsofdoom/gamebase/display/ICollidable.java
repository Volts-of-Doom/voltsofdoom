package vision.voltsofdoom.gamebase.display;

public interface ICollidable {
  BoundingBox getBoundingBox();

  float getX();

  float getY();

  float getWidth();

  float getHeight();
}
