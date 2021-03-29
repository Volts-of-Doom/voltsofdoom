package vision.voltsofdoom.silverspark.game;


import vision.voltsofdoom.silverspark.graphic.SparkTexture;

public interface IRenderableEntity {
  float getX();

  float getY();

  float getWidth();

  float getHeight();

  SparkTexture getTexture();

  int getTextureX();

  int getTextureY();

}
