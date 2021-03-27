package vision.voltsofdoom.silverspark.render;

import vision.voltsofdoom.silverspark.game.RenderableEntity;
import vision.voltsofdoom.silverspark.graphic.SparkTexture;
import java.util.List;

public class EntityRenderer extends Renderer {

  public void drawEntities(List<RenderableEntity> entitiesList, SparkTexture entitiesTexture) {
    /* Draw game objects */
    entitiesTexture.bind();
    begin();

    for (RenderableEntity entity : entitiesList) {
      drawEntity(entitiesTexture, entity);
    }

    end();
  }


  public void drawEntity(SparkTexture entityTexture, RenderableEntity entity) {
    drawTextureRegion(entityTexture, entity.getX(), entity.getY(), 0, 0, entity.getHeight(), entity.getWidth());
  }



}
