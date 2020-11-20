package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

public class Box {
  private final String name;
  private final int width;
  private final int height;

  public Box(String name, int width, int height) {
    this.name = name;
    this.width = width;
    this.height = height;
  }

  public String getName() {
    return name;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Box{");

    builder.append("name=" + name);
    builder.append(" height=" + height);
    builder.append(" width=" + width);

    builder.append("}");
    return builder.toString();
  }
}
