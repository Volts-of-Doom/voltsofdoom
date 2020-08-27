package vision.voltsofdoom.silverspark.render;

import vision.voltsofdoom.silverspark.display.IRenderable;
import vision.voltsofdoom.silverspark.graphic.Texture;

import java.util.List;

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
