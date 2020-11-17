package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;
import vision.voltsofdoom.coresystem.universal.util.Coordinate;

/**
 * Provides a {@link Coordinate} and the width and height of a {@link BufferedImage} for use in the
 * {@link TextureAtlas}.
 * 
 * @author GenElectrovise
 *
 */
public interface ICoordinateAlignedImageDataProvider extends ITextureDimensionsProvider {
  public Coordinate getCoordinate();
}
