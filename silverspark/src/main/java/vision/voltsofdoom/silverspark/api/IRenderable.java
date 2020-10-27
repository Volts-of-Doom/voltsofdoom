package vision.voltsofdoom.silverspark.api;


import vision.voltsofdoom.silverspark.graphic.SparkTexture;

public interface IRenderable {
  float getX();

  float getY();

  float getWidth();

  float getHeight();

  SparkTexture getTexture();

  int getTextureX();

  int getTextureY();

}
