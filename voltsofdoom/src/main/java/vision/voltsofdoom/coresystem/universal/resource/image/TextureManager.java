package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.coresystem.universal.resource.zip.ZipFileReader;
import vision.voltsofdoom.coresystem.universal.util.Reference;
import vision.voltsofdoom.zapbyte.main.ZapByteExceptionFormatter;
import vision.voltsofdoom.zapbyte.misc.util.StringUtils;
import vision.voltsofdoom.zapbyte.resource.IResource;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

public class TextureManager {

  private final TextureAtlas atlas;

  public TextureManager(IResource parentFile) {
    VoltsOfDoomCoreSystem
        .easyInfo("Creating TextureManager with root directory " + parentFile.getPath());
    this.atlas = new TextureAtlas(createAtlasEntries(parentFile));
  }

  /**
   * @param parentFile
   * @return Entries for the {@link TextureAtlas}, read from the {@link Reference#getTexturesDir()}
   *         directory.
   */
  private List<ITextureAtlasEntry> createAtlasEntries(IResource parentFile) {

    // Setup
    VoltsOfDoomCoreSystem.easyDebug("Creating TextureAtlasEntries");

    ArrayList<ITextureAtlasEntry> images = new ArrayList<ITextureAtlasEntry>();

    // Get the relevant ZIP files
    BiMap<String, ZipFile> zipFiles = getRelevantZipFiles(parentFile);

    BiMap<String, JsonObject> manifests = obtainManifestFiles(zipFiles);

    readManifestsAndBindEntries(images, zipFiles, manifests);

    return images;
  }

  /**
   * Reads the manifests, and binds their entries to images.
   * 
   * @param images
   * @param zipFiles
   * @param manifests
   */
  private void readManifestsAndBindEntries(ArrayList<ITextureAtlasEntry> images,
      BiMap<String, ZipFile> zipFiles, BiMap<String, JsonObject> manifests) {

    // Log new process
    VoltsOfDoomCoreSystem.easyDebug("Reading manifests...");

    // For each manifest file, read and bind all listed images
    for (String name : manifests.keySet()) {

      VoltsOfDoomCoreSystem.easyDebug("Reading manifest: " + name);

      JsonObject manifest = manifests.get(name);

      /////////////
      // For each entry in the manifest, obtain the denoted image, and bind
      /////////////
      manifest.entrySet().forEach((entry) -> {

        VoltsOfDoomCoreSystem.easyDebug("Reading manifest (JSONObject) JSONEntry: " + entry);

        // Get the key and value
        String key = entry.getKey();
        JsonElement rawValue = entry.getValue();
        String readValue = readValueOfManifestJsonEntry(key, rawValue);

        // Fetch a stream of the image, and form a texture atlas entry.
        try {
          // Get the file name
          String zipName = manifests.inverse().get(manifest);
          VoltsOfDoomCoreSystem.easyDebug("Hooking into file: " + zipName);

          // Get a reader
          VoltsOfDoomCoreSystem.easyDebug("Getting reader from 'zipFiles': " + zipName);
          ZipFileReader reader = new ZipFileReader(zipFiles.get(zipName));

          final String finalValue = resolveFinalNameOfEntry(readValue);

          final InputStream stream = resolveStream(zipName, reader, finalValue);

          // Bind
          bindNewITextureAtlasEntry(images, key, finalValue, stream);

          // Terminate and tidy up
          stream.close();

          // Repeat

        } catch (IOException i) {
          i.printStackTrace();
        }
      });


    }
  }

  /**
   * Gets the stream of the finalValue's imaged
   * 
   * @param zipName
   * @param reader
   * @param finalValue
   * @return
   */
  private InputStream resolveStream(String zipName, ZipFileReader reader, final String finalValue) {
    final InputStream stream = finalValue != "default"
        ? reader.getStream(Reference.getTexturePackInternalTextureDir() + finalValue,
            "Unable to get stream for file " + finalValue + " from ZIP file " + zipName)
        : getDefaultImageAsStream();
    return stream;
  }

  /**
   * Makes a binding for a {@link ITextureAtlasEntry} on the {@link TextureAtlas}.
   * 
   * @param images
   * @param key
   * @param finalValue
   * @param stream
   */
  private void bindNewITextureAtlasEntry(ArrayList<ITextureAtlasEntry> images, String key,
      final String finalValue, final InputStream stream) {
    images.add(new ITextureAtlasEntry() {

      @Override
      public String getName() {
        return key;
      }

      @Override
      public BufferedImage getImage() {
        try {
          return ImageIO.read(stream);
        } catch (IOException e) {
          e.printStackTrace();
        }

        throw new IllegalStateException("Unable to read image file stream " + finalValue
            + " (assigned and default values failed)");
      }
    });
  }

  /**
   * Gets the true name of the {@link ZipEntry} to fetch
   * 
   * @param readValue
   * @return
   */
  private String resolveFinalNameOfEntry(String readValue) {
    final String finalValue = readValue.endsWith(".png") ? readValue : readValue + ".png";
    VoltsOfDoomCoreSystem.easyDebug("Entry's end value has been resolved! (readValue) " + readValue
        + " >> (finalValue) " + finalValue);
    return finalValue;
  }

  /**
   * Gets the value of the current {@link JsonElement}
   * 
   * @param key
   * @param rawValue
   * @return
   */
  private String readValueOfManifestJsonEntry(String key, JsonElement rawValue) {
    String readValue = "default";

    VoltsOfDoomCoreSystem.easyDebug("Entry key: " + key);
    VoltsOfDoomCoreSystem.easyDebug("Entry rawValue: " + rawValue);

    try {
      readValue = rawValue.getAsString();

      VoltsOfDoomCoreSystem.easyDebug("readValue: default >> " + readValue);

    } catch (Exception e) {
      ZapByteExceptionFormatter.onError_withMessage_withArgs(e, //
          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger(), //
          "Error reading value of manifest file entry", //
          new String[] {"readValue", //
              readValue, //
              "rawValue", //
              rawValue.toString() //
          });
    }

    VoltsOfDoomCoreSystem.easyDebug("Successfully loaded readValue of JSONEntry! : " + readValue);
    return readValue;
  }

  /**
   * Gets the manifest files from each ZIP files
   * 
   * @param zipFiles
   * @return
   */
  private BiMap<String, JsonObject> obtainManifestFiles(BiMap<String, ZipFile> zipFiles) {

    BiMap<String, JsonObject> manifests = HashBiMap.create();
    VoltsOfDoomCoreSystem.easyDebug("Created manifest files list: " + manifests);

    try {

      // For each ZIP file
      for (ZipFile zip : zipFiles.values()) {

        // Log
        VoltsOfDoomCoreSystem.easyDebug("Attempting to load ZIP file: " + zip.getName());

        // Make a reader
        ZipFileReader reader = new ZipFileReader(zip);
        VoltsOfDoomCoreSystem.easyDebug("Successfully generated ZipFileReader");

        // Read a JSON from it
        JsonObject obj = new Gson().fromJson(
            ZipFileReader.asJsonReader(reader.getStream("manifest.json")), JsonObject.class);
        VoltsOfDoomCoreSystem.easyDebug("Found JSONObject: " + obj);

        // Put the manifest into the array
        manifests.put(zip.getName(), obj);
        VoltsOfDoomCoreSystem.easyDebug("Bound! (" + zip.getName() + ") : (" + obj + ")");
      }
    } catch (IOException i) {
      ZapByteExceptionFormatter.onError_withMessage(i, //
          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger(), //
          "Error whilst loading texture pack ZIP file manifests" //
      );
    }

    // Log success
    VoltsOfDoomCoreSystem.easyInfo("Successfully loaded texture pack ZIP file manifests!");
    return manifests;
  }

  /**
   * Gets the relevant ZIP files from the texture packs directory.
   * 
   * @param parentFile The texture packs directory.
   * @param images The {@link ArrayList}
   * @return
   */
  private BiMap<String, ZipFile> getRelevantZipFiles(IResource parentFile) {

    BiMap<String, ZipFile> zipFiles = HashBiMap.create();
    try {

      // Get all of the files in the directory
      File[] listings = ZBSystemResourceHandler.instance.getFile(parentFile).listFiles();

      // Log
      VoltsOfDoomCoreSystem
          .easyDebug("Found texture ZIP files: " + StringUtils.arrayToString(listings));

      // For each
      for (File file : listings) {

        // If is a ZIP
        if (file.getName().endsWith(".zip")) {

          // Log
          VoltsOfDoomCoreSystem
              .easyDebug("Creating new ZipFile binding for file: " + file.getName());

          // Bind file name to a new ZIP file
          zipFiles.put(file.getName(), new ZipFile(file));
        }
      }
    } catch (IOException i) {
      ZapByteExceptionFormatter.onError_withMessage_withArgs(i, // Throwable
          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger(), // Logger
          "An exception has occurred during TexureManger loading, #createAtlasEntries", // Message
          new String[] { // Additional arguments, in pairs
              "parentFile", //
              parentFile.getPath()//
          });
    }

    VoltsOfDoomCoreSystem.easyDebug("Using texture ZIPs: " + zipFiles.keySet());
    return zipFiles;
  }

  /**
   * @return The default image for a non-functioning texture.
   */
  private InputStream getDefaultImageAsStream() {
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
        .error("NO DEFAULT IMAGE TextureManager#getDefaultImageAsStream");
    return null;
  }

  public TextureAtlas getAtlas() {
    return atlas;
  }
}
