package vision.voltsofdoom.silverspark.game;


import vision.voltsofdoom.silverspark.display.IRenderable;
import vision.voltsofdoom.silverspark.graphic.Texture;
import vision.voltsofdoom.silverspark.graphic.VODColor;

public class GreenBlob extends Entity implements IRenderable {


    public GreenBlob(VODColor color, Texture texture, float x, float y, float speed, int width, int height, int tx, int ty) {
        super(color, texture, x, y, speed, width, height, tx, ty);
    }

    @Override
    public void input(Entity entity) {

    }
}
