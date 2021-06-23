package vision.voltsofdoom.voltsofdoom.resourcepack.indexing;

import java.nio.ByteBuffer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import vision.voltsofdoom.voltsofdoom.resourcepack.loading.ResourcePackIndexer;
import vision.voltsofdoom.voltsofdoom.resourcepack.loading.TextureResourceLoader;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.IResource;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.IResourcePack;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.ResourcePackManifestFileResource;
import vision.voltsofdoom.zapbyte.resource.ID;
import vision.voltsofdoom.zapbyte.resource.IID;

public class ResourcePackTextureIndexer extends ResourcePackIndexer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourcePackTextureIndexer.class);
  private final Gson GSON = new Gson();

  @Override
  public IIndex<String, IID> index(List<IResourcePack> packs) {
    
    IIndex<String, IID> index = IIndex.create(); // Map the name of the texture to the pack it should be read from

    // Start at last index, iterate through 0
    for (IResourcePack pack : packs) {

      // Get the manifest
      IResource resource = pack.getResource(TextureResourceLoader.TEXTURE_MANIFEST_LOCATION);
      ByteBuffer bytes = resource.getBytes();
      ResourcePackManifestFileResource manifest = GSON.fromJson(new String(bytes.array()), ResourcePackManifestFileResource.class);

      // For each mapping in the manifest
      for (String texturePath : manifest.getMappings().values()) {

        String workingWith = texturePath;

        if (texturePath.startsWith("textures/")) {
          workingWith = workingWith.replaceFirst("textures/", "");
        }

        // Does the specified texture exist?
        if (pack.getResource(TextureResourceLoader.TEXTURE_INTERNAL_PATH_PREFIX + workingWith) != null) {

          // Create an inverted view of the mappings
          BiMap<String, String> invertedManifestMappings = HashBiMap.create(manifest.getMappings()).inverse();

          // Put the newly generated mapping into the map
          String mappedName = invertedManifestMappings.get(texturePath);
          String internalGameMapping = invertedManifestMappings.get(texturePath);
          index.associate(mappedName, ID.fromString(internalGameMapping));
        }
      }

      LOGGER.debug("Done creating texture mappings for " + pack.getPackInfo().getPackDisplayName());

    }
    return null;
  }

}
