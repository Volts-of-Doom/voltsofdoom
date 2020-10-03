package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.coresystem.play.entity.Coordinate;

public class EntityKeyPlacementNode {
  private String key;
  private Coordinate coordinate;
  private DataTagMap data;

  public String getKey() {
    return key;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  public DataTagMap getData() {
    return data;
  }
}
