package vision.voltsofdoom.silverspark.render;


import java.util.List;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.graphic.SparkTexture;

public class ListRenderer extends Renderer {

    public void drawContents(List<IRenderable> renderableList, SparkTexture texture) {
        /* Draw game objects */
        texture.bind();
        begin();

        for (IRenderable renderable: renderableList) {
            drawItem(texture, renderable);
        }

        end();
    }


    private void drawItem(SparkTexture texture, IRenderable renderable) {
        drawTextureRegion(texture, renderable.getX(), renderable.getY(), 0, 0, renderable.getHeight(), renderable.getWidth());
    }



}
