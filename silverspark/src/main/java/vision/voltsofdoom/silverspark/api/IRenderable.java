package vision.voltsofdoom.silverspark.api;


import vision.voltsofdoom.silverspark.graphic.Spark;

public interface IRenderable {
  float getX();

  float getY();

  float getWidth();

  float getHeight();

  Spark getTexture();

  int getTextureX();

  int getTextureY();

}
