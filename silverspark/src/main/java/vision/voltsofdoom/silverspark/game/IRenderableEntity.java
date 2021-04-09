package vision.voltsofdoom.silverspark.game;


import vision.voltsofdoom.silverspark.graphic.Spark;

public interface IRenderableEntity {
  float getX();

  float getY();

  float getWidth();

  float getHeight();

  Spark getTexture();

  int getTextureX();

  int getTextureY();

}
