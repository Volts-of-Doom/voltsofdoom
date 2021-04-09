package vision.voltsofdoom.silverspark.render;

import vision.voltsofdoom.silverspark.game.IRenderableEntity;
import vision.voltsofdoom.silverspark.graphic.Spark;
import java.util.List;

public class EntityRenderer extends AbstractRenderer {

  public void drawEntities(List<IRenderableEntity> entitiesList, Spark entitiesTexture) {
    /* Draw game objects */
    entitiesTexture.bind();
    begin();

    for (IRenderableEntity entity : entitiesList) {
      drawEntity(entitiesTexture, entity);
    }

    end();
  }


  public void drawEntity(Spark entityTexture, IRenderableEntity entity) {
    drawTextureRegion(entityTexture, entity.getX(), entity.getY(), 0, 0, entity.getHeight(), entity.getWidth());
  }



}
