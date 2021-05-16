package vision.voltsofdoom.voltsofdoom.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import vision.voltsofdoom.silverspark.api.ITextureAtlas;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;

public class TextureResourceLoader extends RegisterableResourceLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextureResourceLoader.class);

  private static final String TEXTURE_MANIFEST_LOCATION = "manifest.json";
  private static final String TEXTURE_INTERNAL_PATH_PREFIX = "textures/";

  private ITextureAtlas atlas;
  private final Gson GSON = new GsonBuilder().registerTypeAdapter(ResourcePackManifestFileResource.class, new ResourcePackManifestFileResource.Serializer()).setPrettyPrinting().create();
  private File rootDirectoryFile;

  public TextureResourceLoader(String rootDirectory) {
    super(rootDirectory, false);
  }

  public void load() {
    load(false);
  }

  /**
   * Conceals a call to {@link #inner_load(boolean)} and handles errors for that call.
   * 
   * @param forceReload
   */
  @Override
  public void load(boolean forceReload) {
    try {

      inner_load(forceReload);

    } catch (FileNotFoundException fi) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (An expected file cannot be found)");
      fi.printStackTrace();
    } catch (IllegalStateException il) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (Something was in the wrong state)");
      il.printStackTrace();
    } catch (NullPointerException nu) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (An object was null)");
      nu.printStackTrace();
    } catch (ZipException zi) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (A ZIP file could not be read)");
      zi.printStackTrace();
    } catch (IOException io) {
      LOGGER.error("An error has occurred building the TextureResourceLoader! (An I/O operation failed)");
      io.printStackTrace();
    }
  }

  /**
   * Builds the {@link TextureResourceLoader}. Private so that it can handle its own errors.
   * 
   * @param forceReload Sometimes a rebuild is not expected or desired. Use this option to force a
   *        rebuild whether the program likes it or not. <i>"Be wary, adventurer..."</i>
   * @throws IOException
   * @throws ZipException
   */
  private void inner_load(boolean forceReload) throws FileNotFoundException, IllegalStateException, ZipException, IOException {

    // Messages for the developer
    if ((!forceReload) && loaded) {
      LOGGER.warn("An attempt to call the TextureResourceLoader#build(..) was made when the target TextureResourceLoader is already built. The attempt was denied because the forceReload flag was not set to true.");
      return;
    }
    if (forceReload && loaded) {
      LOGGER.warn("TextureResourceLoader#build(..) has been called with the forceReload flag when the target TextureResourceLoader is already built. A TextureResourceLoader rebuild will start.");
    }
    if (!loaded) {
      LOGGER.warn("Building the target TextureResourceLoader for the first time.");
    }
    LOGGER.warn("Building TextureResourceLoader for directory " + rootDirectory + " with flags: forceReload=" + forceReload);

    // Change the built flag
    loaded = true;

    // Get an instance of the root file. Check exists and is a directory.
    rootDirectoryFile = new File(rootDirectory);
    if (!rootDirectoryFile.exists())
      throw new FileNotFoundException("Cannot find root directory file for TextureResourceLoader " + rootDirectory);
    if (!rootDirectoryFile.isDirectory())
      throw new IllegalStateException("The TextureResourceLoader root file " + rootDirectory + " is not a directory.");

    // Get a list of files in the directory
    // File[] children = findAndMapTexturePackZipFileObjectsToFileNames();

    // Make a list of the manifests
    // List<TexturePackManifest> manifests = new ArrayList<>();
    // getListOfJavaObjectTexturePackManifests(children, manifests);

    // Combine and override textures
    // a) Get priorities
    JsonArray texturePackPriorityJsonArray = VoltsOfDoom.getInstance().getConfigurationHandler().getOption("textures.texture_pack_priority").getAsJsonArray();
    String[] prioritisedRawTexturePackNames = getArrayOfPrioritisedTexturePacks(texturePackPriorityJsonArray);
    List<IResourcePack> packs = findResourcePacks();
    // b) Load <String textureName, String packName> in order of last -> first priority
    // (so that higher priorities overwrite lower ones)
    Map<String, ResourceMapping> textureNameToPackName = new HashMap<String, ResourceMapping>();
    loadTextureNameMapPackNameInLastToFirstPriority(packs, textureNameToPackName);
    writePrioritiesBackToConfiguration(prioritisedRawTexturePackNames);

    // Read each image. Make a list of nodes with their dimensions.

    // Order nodes by width

    // Fit into layout

    // For each closed node: get name -> get PNG -> add to atlas image -> add coordinate reference

    // Write atlas image to system

    return;
  }

  private void writePrioritiesBackToConfiguration(String[] prioritisedRawTexturePackNames) {
    LOGGER.error("Not writing the pack names back to configuration. This isn't an issue because this hasn't been implemented yet.");
    return;
  }

  private List<IResourcePack> findResourcePacks() {
    return Lists.newArrayList();
  }

  private void loadTextureNameMapPackNameInLastToFirstPriority(List<IResourcePack> packs, Map<String, ResourceMapping> textureNameToPackName) {

    // Start at last index, iterate through 0
    for (IResourcePack pack : packs) {

      // Get the manifest
      ResourcePackManifestFileResource manifest = GSON.fromJson(new InputStreamReader(pack.getResource(TEXTURE_MANIFEST_LOCATION).getInputStream()), ResourcePackManifestFileResource.class);

      // For each mapping in the manifest
      for (String texturePath : manifest.getMappings().values()) {

        String workingWith = texturePath;

        if (texturePath.startsWith("textures/")) {
          workingWith = workingWith.replaceFirst("textures/", "");
        }

        // Does the specified texture exist?
        if (pack.getResource(TEXTURE_INTERNAL_PATH_PREFIX + workingWith) != null) {

          // Create an inverted view of the mappings
          BiMap<String, String> invertedManifestMappings = HashBiMap.create(manifest.getMappings()).inverse();

          // Put the newly generated mapping into the map
          String mappedName = invertedManifestMappings.get(texturePath);
          String internalGameMapping = invertedManifestMappings.get(texturePath);
          ResourceMapping mappedResource = new ResourceMapping(texturePath, pack.getIdentifier().toString(), internalGameMapping);
          textureNameToPackName.put(mappedName, mappedResource);
        }
      }

      LOGGER.debug("Done creating texture mappings for " + pack.getDisplayName());

    }

    LOGGER.debug("Done creating all texture mappings.");

  }

  /**
   * Given a {@link JsonArray}, gets a list of all strings in the {@link JsonArray} and converts them
   * into a String[].
   * 
   * @param texturePackPriorityJsonArray
   */
  private String[] getArrayOfPrioritisedTexturePacks(JsonArray texturePackPriorityJsonArray) {
    String[] texturePackPriorityStrings = new String[texturePackPriorityJsonArray.size()];
    int i = 0;

    for (JsonElement element : texturePackPriorityJsonArray) {

      // Ensure is primitive
      if (!element.isJsonPrimitive()) {
        LOGGER.warn("JsonElement is not a primitive: " + element);
        continue;
      }
      JsonPrimitive primitive = (JsonPrimitive) element;

      // Ensure is string
      if (!primitive.isString()) {
        LOGGER.warn("JsonPrimitive is not a string: " + primitive);
        continue;
      }
      String content = primitive.getAsString();

      texturePackPriorityStrings[i] = content;
      i++;
    }

    return texturePackPriorityStrings;
  }

  public boolean isBuilt() {
    return loaded;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  public ITextureAtlas getAtlas() {
    return atlas;
  }

}
