package vision.voltsofdoom.silverspark.graphic;

public class ManifestEntry {

  private String name;
  private int[] coords = new int[2];
  private int width;
  private int height;

  public ManifestEntry(String name, int[] coords, int width, int height) {
    this.name = name;
    this.coords = coords;
    this.width = width;
    this.height = height;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int[] getCoords() {
    return coords;
  }

  public void setCoords(int[] coords) {
    this.coords = coords;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }


}
