package genelectrovise.voltsofdoom_coresystem.silverspark.game;


import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.Texture;

public interface RenderableEntity {
    float getX();

    float getY();

    float getWidth();

    float getHeight();

    Texture getTexture();

    int getTextureX();

    int getTextureY();

}
