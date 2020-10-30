package vision.voltsofdoom.zapbyte.resource;

/**
 * Could be anything from a PNG to a JAR or a JSON.
 * 
 * @author GenElectrovise
 *
 */
@FunctionalInterface
public interface IResource {
  public String getPath();
}
