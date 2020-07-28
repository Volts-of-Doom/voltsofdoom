package genelectrovise.voltsofdoom_coresystem.silverspark.game;


import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.VODColor;
import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.Texture;

public class GreenBlob extends Entity implements RenderableEntity {
    public GreenBlob(VODColor color, Texture texture, float x, float y, float speed, int width, int height, int tx, int ty) {
        super(color, texture, x, y, speed, width, height, tx, ty);
    }

    @Override
    public void input(Entity entity) {

    }
}
