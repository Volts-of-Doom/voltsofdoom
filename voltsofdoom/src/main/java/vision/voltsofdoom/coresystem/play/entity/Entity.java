package vision.voltsofdoom.coresystem.play.entity;

import vision.voltsofdoom.coresystem.play.data.IPropertyHandler;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.zapbyte.loading.registry.RegistryEntry;

/**
 * A thing in a level which isn't a Tile is probably an Entity.
 * 
 * @author GenElectrovise
 *
 * @see Tile
 */
public class Entity extends RegistryEntry<Entity> {
  private IPropertyHandler<Entity> propertyHandler;
  
  public IPropertyHandler<Entity> getPropertyHandler() {
    return propertyHandler;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    try {
      builder.append("Entity : [");

      builder.append("Properties {");
      builder.append("}");

      builder.append("]");
    } catch (Exception e) {
      e.printStackTrace();
    }

    return builder.toString();
  }
}
