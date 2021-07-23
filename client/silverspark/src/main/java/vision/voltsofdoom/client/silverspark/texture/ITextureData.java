package vision.voltsofdoom.client.silverspark.texture;

/**
 * Data used by an {@link ITextureAtlas} to read a texture off of the master texture atlas image.
 * 
 * @author GenElectrovise
 *
 */
public interface ITextureData {
  String getName();

  int getX();

  int getY();

  int getWidth();

  int getHeight();
}
