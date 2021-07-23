package vision.voltsofdoom.client.draftclient.voltsofdoom.collision;

public interface ICollidable {
  CollidableBoundingBox getBoundingBox();

  float getX();

  float getY();

  float getImageWidth();

  float getHeight();
}
