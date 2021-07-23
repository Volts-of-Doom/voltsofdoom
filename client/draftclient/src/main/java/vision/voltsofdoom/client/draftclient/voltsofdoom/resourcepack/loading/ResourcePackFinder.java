package vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.loading;

import java.util.List;
import vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.structure.IResourcePack;
import vision.voltsofdoom.zapbyte.registry.RegistryEntry;

public abstract class ResourcePackFinder extends RegistryEntry<ResourcePackFinder> {
  public abstract List<IResourcePack> find();
}
