package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
        .info("Creating TextureManager with root directory " + parentFile.getPath());
    this.atlas = new TextureAtlas(createAtlasEntries(parentFile));
  }

  /**
   * @param parentFile
   * @return Entries for the {@link TextureAtlas}, read from the {@link Reference#getTexturesDir()}
   *         directory.
   */
  private List<ITextureAtlasEntry> createAtlasEntries(IResource parentFile) {

    // Setup
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
        .debug("Creating TextureAtlasEntries");

    ArrayList<ITextureAtlasEntry> images = new ArrayList<ITextureAtlasEntry>();

    // Get relevant texture ZIP files
    /**
     * String = name of ZIP <br>
     * ZipFile = the texture pack ZIP
     */
    BiMap<String, ZipFile> zipFiles = HashBiMap.create();
    try {

      // Get all of the files in the directory
      File[] listings = ZBSystemResourceHandler.instance.getFile(parentFile).listFiles();

      // Log
      VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
          .debug("Found texture ZIP files: " + StringUtils.arrayToString(listings));

      // For each
      for (File file : listings) {

        // If is a ZIP
        if (file.getName().endsWith(".zip")) {

          // Log
          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
              .debug("Creating new ZipFile binding for file: " + file.getName());

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
              parentFile.getPath(), //
              "images ArrayList", //
              images != null ? "non-null" : "null", //
              "endofargs"//
          });
    }

    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
        .debug("Using texture ZIPs: " + zipFiles.keySet());

    // Get manifest files
    /**
     * String = name of parent ZIP <br>
     * JsonObject = that ZIP's manifest file
     */
    BiMap<String, JsonObject> manifests = HashBiMap.create();
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
        .debug("Created manifest files list: " + manifests);

    try {

      // For each ZIP file
      for (ZipFile zip : zipFiles.values()) {

        // Log
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .debug("Attempting to load ZIP file: " + zip.getName());

        // Make a reader
        ZipFileReader reader = new ZipFileReader(zip);
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .debug("Successfully generated ZipFileReader");

        // Read a JSON from it
        JsonObject obj = new Gson().fromJson(
            ZipFileReader.asJsonReader(reader.getStream("manifest.json")), JsonObject.class);
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .debug("Found JSONObject: " + obj);

        // Put the manifest into the array
        manifests.put(zip.getName(), obj);
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .debug("Bound! (" + zip.getName() + ") : (" + obj + ")");
      }
    } catch (IOException i) {
      ZapByteExceptionFormatter.onError_withMessage(i, //
          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger(), //
          "Error whilst loading texture pack ZIP file manifests" //
      );
    }

    // Log success
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
        .info("Successfully loaded texture pack ZIP file manifests!");

    // Log new process
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().debug("Reading manifests...");

    // For each manifest file, read and bind all listed images
    for (String name : manifests.keySet()) {

      VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().debug("Reading manifest: " + name);

      JsonObject manifest = manifests.get(name);

      // For each entry in the manifest
      manifest.entrySet().forEach((entry) -> {

        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .debug("Reading manifest (JSONObject) JSONEntry: " + entry);

        // Get the key and value
        String key = entry.getKey();
        JsonElement rawValue = entry.getValue();
        String readValue = "default";

        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().debug("Entry key: " + key);
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
            .debug("Entry rawValue: " + rawValue);

        try {
          readValue = rawValue.getAsString();

          VoltsOfDoomCoreSystem.getInstance().getApplicationLogger()
              .debug("readValue: default >> " + readValue);
          
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

        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().debug("Successfully loaded readValue of JSONEntry! : " + readValue);

        // Fetch a stream of the image, and form a texture atlas entry.
        try {
          // Get a hook into the file
          String zipName = manifests.inverse().get(manifest);
          ZipFileReader reader = new ZipFileReader(zipFiles.get(zipName));

          // Read
          final String finalValue = readValue.endsWith(".png") ? readValue : readValue + ".png";
          final InputStream stream = finalValue != "default"
              ? reader.getStream(Reference.getTexturePackInternalTextureDir() + finalValue,
                  "Unable to get stream for file " + finalValue + " from ZIP file " + zipName)
              : getDefaultImageAsStream();

          // Bind
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

              throw new IllegalStateException("Unable to read image file " + finalValue
                  + " (assigned and default values failed)");
            }
          });

          // Terminate and tidy up
          stream.close();

          // Repeat

        } catch (IOException i) {
          i.printStackTrace();
        }
      }); // <<<< This is the end of forEach


    }

    return images;
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
