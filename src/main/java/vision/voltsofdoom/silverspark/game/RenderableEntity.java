package vision.voltsofdoom.silverspark.game;


import vision.voltsofdoom.silverspark.graphic.Texture;

public interface RenderableEntity {
    float getX();

    float getY();

    float getWidth();

    float getHeight();

    Texture getTexture();

    int getTextureX();

    int getTextureY();

}
