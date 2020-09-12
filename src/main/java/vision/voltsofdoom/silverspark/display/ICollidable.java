package vision.voltsofdoom.silverspark.display;

import vision.voltsofdoom.silverspark.display.BoundingBox;

public interface ICollidable {
    BoundingBox getBoundingBox();
    float getX();
    float getY();
    float getWidth();
    float getHeight();
}
