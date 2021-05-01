package vision.voltsofdoom.voltsofdoom.resource.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import vision.voltsofdoom.silverspark.api.ITextureAtlas;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.resource.zip.ZipFileReader;
import vision.voltsofdoom.voltsofdoom.util.Reference;

public class TextureResourceLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextureResourceLoader.class);

  private static final String TEXTURE_MANIFEST_LOCATION = "manifest.json";
  private static final String TEXTURE_INTERNAL_PATH_PREFIX = "textures/";

  private String rootDirectoryPath;
  private ITextureAtlas atlas;
  private boolean built;
  private final Gson GSON = new GsonBuilder().registerTypeAdapter(TexturePackManifest.class, new TexturePackManifest.Serializer()).setPrettyPrinting().create();
  private File rootDirectoryFile;

  public TextureResourceLoader(String rootDirectory) {
    this.rootDirectoryPath = rootDirectory;
    this.built = false;
  }

  public void build() {
    build(false);
  }

  /**
   * Conceals a call to {@link #inner_build(boolean)} and handles errors for that call.
   * 
   * @param forceRebuild
   */
  public void build(boolean forceRebuild) {
    try {

      inner_build(forceRebuild);

    } catch (FileNotFoundException fi) {
      LOGGER.error("An error has occurred building the TextureManager! (An expected file cannot be found)");
      fi.printStackTrace();
    } catch (IllegalStateException il) {
      LOGGER.error("An error has occurred building the TextureManager! (Something was in the wrong state)");
      il.printStackTrace();
    } catch (NullPointerException nu) {
      LOGGER.error("An error has occurred building the TextureManager! (An object was null)");
      nu.printStackTrace();
    } catch (ZipException zi) {
      LOGGER.error("An error has occurred building the TextureManager! (A ZIP file could not be read)");
      zi.printStackTrace();
    } catch (IOException io) {
      LOGGER.error("An error has occurred building the TextureManager! (An I/O operation failed)");
      io.printStackTrace();
    }
  }

  /**
   * Builds the {@link TextureResourceLoader}. Private so that it can handle its own errors.
   * 
   * @param forceRebuild Sometimes a rebuild is not expected or desired. Use this option to force a
   *        rebuild whether the program likes it or not. <i>"Be wary, adventurer..."</i>
   * @throws IOException
   * @throws ZipException
   */
  private void inner_build(boolean forceRebuild) throws FileNotFoundException, IllegalStateException, ZipException, IOException {

    // Messages for the developer
    if ((!forceRebuild) && built) {
      LOGGER.warn("An attempt to call the TextureManager#build(..) was made when the target TextureManager is already built. The attempt was denied because the forceRebuild flag was not set to true.");
      return;
    }
    if (forceRebuild && built) {
      LOGGER.warn("TextureManager#build(..) has been called with the forceRebuild flag when the target TextureManager is already built. A TextureManager rebuild will start.");
    }
    if (!built) {
      LOGGER.warn("Building the target TextureManager for the first time.");
    }
    LOGGER.warn("Building TextureManager for directory " + rootDirectoryPath + " with flags: forceRebuild=" + forceRebuild);

    // Change the built flag
    built = true;

    // Get an instance of the root file. Check exists and is a directory.
    rootDirectoryFile = new File(rootDirectoryPath);
    if (!rootDirectoryFile.exists())
      throw new FileNotFoundException("Cannot find root directory file for TextureManager " + rootDirectoryPath);
    if (!rootDirectoryFile.isDirectory())
      throw new IllegalStateException("The TextureManager root file " + rootDirectoryPath + " is not a directory.");

    // Get a list of files in the directory
    File[] children = findAndMapTexturePackZipFileObjectsToFileNames();

    // Make a list of the manifests
    // List<TexturePackManifest> manifests = new ArrayList<>();
    // getListOfJavaObjectTexturePackManifests(children, manifests);

    // Combine and override textures
    // a) Get priorities
    JsonArray texturePackPriorityJsonArray = VoltsOfDoom.getInstance().getConfigurationHandler().getOption("texture_manager.texture_pack_priority").getAsJsonArray();
    String[] prioritisedRawTexturePackNames = getArrayOfPrioritisedTexturePacks(texturePackPriorityJsonArray);
    // b) Load <String textureName, String packName> in order of last -> first priority
    // (so that higher priorities overwrite lower ones)
    Map<String, ResourceMapping> textureNameToPackName = new HashMap<String, ResourceMapping>();
    loadTextureNameMapPackNameInLastToFirstPriority(prioritisedRawTexturePackNames, textureNameToPackName);
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

  private void loadTextureNameMapPackNameInLastToFirstPriority(String[] prioritisedRawTexturePackNames, Map<String, ResourceMapping> textureNameToPackName) {

    // Start at last index, iterate through 0
    for (int i = prioritisedRawTexturePackNames.length - 1; i > -1; i--) {
      File packFile = new File(Reference.getTexturesDir() + prioritisedRawTexturePackNames[i]);

      // Make sure the file exists. If not ignore it forever.
      if (!packFile.exists()) {
        LOGGER.warn("Texture pack " + packFile + " does not exist. References will be nulled internally, and will be removed from configuration.");
        prioritisedRawTexturePackNames[i] = null;
        continue;
      }

      // Make a ZIP file of the resource pack, and make it close automatically on exiting ZIP logic.
      try (ZipFile zipFile = new ZipFile(packFile)) {

        // Get the manifest
        ZipFileReader reader = new ZipFileReader(zipFile);
        TexturePackManifest manifest = GSON.fromJson(new InputStreamReader(reader.getStream(TEXTURE_MANIFEST_LOCATION)), TexturePackManifest.class);
        manifest.setTexturePackName(packFile.getName());

        // For each mapping in the manifest
        for (String texturePath : manifest.getMappings().values()) {

          String workingWith = texturePath;

          if (texturePath.startsWith("textures/")) {
            workingWith = workingWith.replaceFirst("textures/", "");
          }

          // Does the specified texture exist?
          if (reader.getStream(TEXTURE_INTERNAL_PATH_PREFIX + workingWith) != null) {

            // Create an inverted view of the mappings
            BiMap<String, String> invertedManifestMappings = HashBiMap.create(manifest.getMappings()).inverse();

            // Put the newly generated mapping into the map
            String mappedName = invertedManifestMappings.get(texturePath);
            String texturePackName = manifest.getTexturePackName();
            String internalGameMapping = invertedManifestMappings.get(texturePath);
            ResourceMapping mappedResource = new ResourceMapping(texturePath, texturePackName, internalGameMapping);
            textureNameToPackName.put(mappedName, mappedResource);
          }
        }

      } catch (ZipException zi) {
        LOGGER.error("ZipException occured while reading the ZIP of resource pack file " + packFile + ". It will be ignored.");
        zi.printStackTrace();
        continue;
      } catch (IOException io) {
        LOGGER.error("IOException occured while reading the ZIP of resource pack file " + packFile + ". It will be ignored.");
        io.printStackTrace();
        continue;
      }
      
      LOGGER.debug("Done creating texture mappings for " + packFile);

    }
    
    LOGGER.debug("Done creating all texture mappings.");

  }

  private File[] findAndMapTexturePackZipFileObjectsToFileNames() {
    return rootDirectoryFile.listFiles((file, name) -> name.endsWith(".zip"));
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

  private void getListOfJavaObjectTexturePackManifests(File[] children, List<TexturePackManifest> manifests) throws ZipException, IOException {
    for (File child : children) {

      // Get a new ZIP reader
      ZipFileReader reader = new ZipFileReader(new ZipFile(child));

      // If there is no manifest, skip this file
      if (reader.getZipFile().getEntry("manifest.json") == null) {
        LOGGER.error("manifest.json is null for file " + child + " so it will not be loaded");
        continue;
      }

      // Get a stream of the contents
      InputStream manifestStream = reader.getStream("manifest.json", "Error reading manifest for ZIP file " + child, true);
      TexturePackManifest manifest = GSON.fromJson(new InputStreamReader(manifestStream), TexturePackManifest.class);
      manifest.setPathToZip(child.getAbsolutePath());

      manifests.add(manifest);
    }
  }

  public boolean isBuilt() {
    return built;
  }

  public String getRootDirectory() {
    return rootDirectoryPath;
  }

  public ITextureAtlas getAtlas() {
    return atlas;
  }

}
