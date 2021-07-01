package vision.voltsofdoom.voltsofdoom.resourcepack.loading;

import java.util.List;
import vision.voltsofdoom.voltsofdoom.resourcepack.indexing.IIndex;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.IResourcePack;
import vision.voltsofdoom.zapbyte.registry.RegistryEntry;
import vision.voltsofdoom.zapbyte.resource.IID;

public abstract class ResourcePackIndexer extends RegistryEntry<ResourcePackIndexer> {

  /**
   * Thought experiment: There are 2 resource packs which define "textures/cobble". There can only be
   * one. The index loads the priority from configuration, and uses that to select which
   * "textures/cobble" is used. It associates "textures/cobble" with an IID
   * "resourcepack:textures/cobble" where "resourcepack" is the pack whose "textures/cobble" should be
   * used.
   * 
   * @param packs
   * @return An {@link IIndex} mapping a texture name to a fully qualified texture name. For example
   *         "textures/cobble" should be mapped to "resourcepack:textures/cobble"
   */
  public abstract IIndex<String, IID> index(List<IResourcePack> packs);
}
