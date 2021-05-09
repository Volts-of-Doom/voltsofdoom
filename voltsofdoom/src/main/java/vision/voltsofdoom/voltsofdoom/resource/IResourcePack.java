package vision.voltsofdoom.voltsofdoom.resource;

import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

/**
 * A resource pack of any kind. This could be loaded from a mod file, or through the file system:
 * this interface doesn't care.
 * 
 * @author GenElectrovise
 *
 */
public interface IResourcePack extends IResource {

  String getDisplayName();
  
  ResourceLocation getIdentifier();
  
  int getLoadingPriority();
  
  ResourcePackInfoFileResource getPackInfo();

  IResource getResource(String path);

}
