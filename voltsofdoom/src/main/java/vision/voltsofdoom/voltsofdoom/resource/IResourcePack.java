package vision.voltsofdoom.voltsofdoom.resource;

/**
 * A resource pack of any kind. This could be loaded from a mod file, or through the file system:
 * this interface doesn't care.
 * 
 * @author GenElectrovise
 *
 */
public interface IResourcePack extends IResource {
  
  ResourcePackInfoFileResource getPackInfo();

  IResource getResource();

}
