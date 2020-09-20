package vision.voltsofdoom.silverspark.render;


import java.util.List;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.graphic.Texture;

public class ListRenderer extends Renderer {

    public void drawContents(List<IRenderable> renderableList, Texture texture) {
        /* Draw game objects */
        texture.bind();
        begin();

        for (IRenderable renderable: renderableList) {
            drawItem(texture, renderable);
        }

        end();
    }


    private void drawItem(Texture texture, IRenderable renderable) {
        drawTextureRegion(texture, renderable.getX(), renderable.getY(), 0, 0, renderable.getHeight(), renderable.getWidth());
    }



}
