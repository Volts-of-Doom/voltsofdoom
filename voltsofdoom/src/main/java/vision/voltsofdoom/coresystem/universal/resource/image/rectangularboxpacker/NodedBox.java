package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

public class NodedBox {
  private final String name;
  private final int height;
  private final int width;

  private final int x;
  private final int y;

  public NodedBox(String name, int height, int width, int x, int y) {
    this.name = name;
    this.height = height;
    this.width = width;
    this.x = x;
    this.y = y;
  }

  public String getName() {
    return name;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
