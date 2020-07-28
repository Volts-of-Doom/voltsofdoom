package genelectrovise.voltsofdoom_coresystem.silverspark.graphic;

import genelectrovise.voltsofdoom_coresystem.silverspark.game.RenderableEntity;

import java.util.List;

public class EntityRenderer extends Renderer {

    public void drawEntities(List<RenderableEntity> entitiesList, Texture entitiesTexture) {
        /* Draw game objects */
        entitiesTexture.bind();
        begin();

        for (RenderableEntity entity: entitiesList) {
            drawEntity(entitiesTexture, entity);
        }

        end();
    }


    public void drawEntity (Texture entityTexture, RenderableEntity entity) {
        drawTextureRegion(entityTexture, entity.getX(), entity.getY(), 0, 0, entity.getHeight(), entity.getWidth());
    }



}
