package vision.voltsofdoom.silverspark.render;

import vision.voltsofdoom.silverspark.game.IRenderableEntity;
import vision.voltsofdoom.silverspark.graphic.Spark;
import java.util.List;

public class SparkRenderer extends AbstractRenderer {

  public void drawSparks(List<IRenderableEntity> entitiesList, Spark entitiesTexture) {
    /* Draw game objects */
    entitiesTexture.bind();
    begin();

    for (IRenderableEntity entity : entitiesList) {
      drawSparks(entitiesTexture, entity);
    }

    end();
  }


  public void drawSparks(Spark spark, IRenderableEntity entity) {
    drawTextureRegion(spark, entity.getX(), entity.getY(), 0, 0, entity.getHeight(), entity.getImageWidth());
  }



}
