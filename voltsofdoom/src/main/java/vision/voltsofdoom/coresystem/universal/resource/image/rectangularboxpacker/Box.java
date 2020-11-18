package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import vision.voltsofdoom.gamebase.collision.BoundingBox;

public class Box {
  private final BoundingBox bounds;

  public Box(BoundingBox bounds) {
    this.bounds = bounds;
  }
  
  public BoundingBox getBounds() {
    return bounds;
  }
}
