package vision.voltsofdoom.voltsofdoom.play.tile;

import vision.voltsofdoom.voltsofdoom.play.data.IPropertyHandler;
import vision.voltsofdoom.zapbyte.registry.RegistryEntry;

/**
 * A very important thing.
 * 
 * @author GenElectrovise
 *
 */
public class Tile extends RegistryEntry<Tile> {
  private IPropertyHandler<Tile> propertyHandler;
  
  public IPropertyHandler<Tile> getPropertyHandler() {
    return propertyHandler;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    try {
      builder.append("Tile : [");

      builder.append("Properties {");
      builder.append("}");

      builder.append("]");
    } catch (Exception e) {
      e.printStackTrace();
    }

    return builder.toString();
  }

}
