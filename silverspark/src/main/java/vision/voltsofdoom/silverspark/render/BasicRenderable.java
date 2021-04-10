package vision.voltsofdoom.silverspark.render;

import vision.voltsofdoom.silverspark.api.IRenderable;

public class BasicRenderable implements IRenderable {
  
  private String keyName; // look-up name of renderable in texture atlas
  private float x; // location of this renderable on map (in game units)
  private float y; // location of this renderable on map (in game units)

  public BasicRenderable(String keyName, float x, float y) {
    this.keyName = keyName;
    this.x = x;
    this.y = y;
  }
  
  @Override
  public String getKeyName() {
    return keyName;
  }

  @Override
  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  @Override
  public void setX(float x) {
    this.x = x;
  }

  @Override
  public float getX() {
    return x;
  }
  
  @Override
  public void setY(float y) {
    this.y = y;
  }

  @Override
  public float getY() {
    return y;
  }

}
