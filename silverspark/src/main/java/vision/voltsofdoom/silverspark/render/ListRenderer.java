package vision.voltsofdoom.silverspark.render;

import java.util.List;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.graphic.SparkTexture;

/**
 * Renders each item from a {@link List} of {@link IRenderable}s.
 * 
 * @author GenElectrovise
 *
 */
public class ListRenderer extends AbstractRenderer {

  /**
   * Draws the contents of the given {@link List} of {@link IRenderable}s.
   * 
   * @param renderableList
   * @param texture
   */
  public void drawContents(List<IRenderable> renderableList, SparkTexture texture) {
    // Bind the texture
    texture.bind();

    // Begin drawing
    begin();

    // Draw every item
    for (IRenderable renderable : renderableList) {
      drawItem(texture, renderable);
    }

    // End drawing
    end();
  }

  /**
   * Draws a single item.
   * 
   * @param texture
   * @param renderable
   */
  private void drawItem(SparkTexture texture, IRenderable renderable) {
    drawTextureRegion(texture, renderable.getX(), renderable.getY(), 0, 0, renderable.getHeight(), renderable.getWidth());
  }

}
