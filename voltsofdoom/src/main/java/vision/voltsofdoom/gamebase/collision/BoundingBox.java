package vision.voltsofdoom.gamebase.collision;

import vision.voltsofdoom.silverspark.math.Vector2f;

public class BoundingBox implements IMeasuredBoundingBox {

  // The min and max are opposite corners
  public Vector2f min;
  public Vector2f max;

  public BoundingBox(float x, float y, float width, float height) {
    min = new Vector2f(x, y);
    max = new Vector2f(x + width, y + height);
  }

  @Override
  public Vector2f getMin() {
    return min;
  }

  @Override
  public Vector2f getMax() {
    return max;
  }

  @Override
  public float getWidth() {
    return Math.max(min.x, max.x) - Math.max(min.x, max.x);
  }

  @Override
  public float getHeight() {
    return Math.max(min.y, max.y) - Math.max(min.y, max.y);
  }

}
