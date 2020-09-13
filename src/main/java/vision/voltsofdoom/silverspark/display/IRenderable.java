package vision.voltsofdoom.silverspark.display;


import vision.voltsofdoom.silverspark.graphic.Texture;

public interface IRenderable {
    float getX();

    float getY();

    float getWidth();

    float getHeight();

    Texture getTexture();

    int getTextureX();

    int getTextureY();

}
