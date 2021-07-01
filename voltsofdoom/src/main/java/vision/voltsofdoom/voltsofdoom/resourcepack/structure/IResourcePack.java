package vision.voltsofdoom.voltsofdoom.resourcepack.structure;

import java.util.Map;
import vision.voltsofdoom.zapbyte.resource.IID;

/**
 * A resource pack of any kind. This could be loaded from a mod file, or through the file system:
 * this interface doesn't care.
 * 
 * @author GenElectrovise
 *
 */
public interface IResourcePack extends IResource {

  ResourcePackInfoFileResource getPackInfo();

  IResource getResource(String path);
  
  Map<IID, IResource> getResources();

}
