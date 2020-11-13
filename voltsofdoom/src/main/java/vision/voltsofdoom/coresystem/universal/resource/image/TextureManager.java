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
import vision.voltsofdoom.coresystem.universal.resource.zip.ZipFileReader;
import vision.voltsofdoom.coresystem.universal.util.Reference;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.resource.IResource;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

public class TextureManager {

  private final TextureAtlas atlas;

  public TextureManager(IResource parentFile) {
    this.atlas = new TextureAtlas(createAtlasEntries());
  }

  /**
   * @return Entries for the {@link TextureAtlas}, read from the {@link Reference#getTexturesDir()}
   *         directory.
   */
  private List<ITextureAtlasEntry> createAtlasEntries() {
    ArrayList<ITextureAtlasEntry> images = new ArrayList<ITextureAtlasEntry>();

    // Get the directory
    File texturesDirectory =
        ZBSystemResourceHandler.instance.getFile(() -> Reference.getTexturesDir(), false, true);

    // Get relevant texture ZIP files
    /**
     * String = name of ZIP <br>
     * ZipFile = the texture pack ZIP
     */
    BiMap<String, ZipFile> zipFiles = HashBiMap.create();
    try {
      for (File file : texturesDirectory.listFiles()) {
        if (file.getName().endsWith(".zip")) {
          zipFiles.put(file.getName(), new ZipFile(file));
        }
      }
    } catch (IOException i) {
      i.printStackTrace();
    }

    // Get manifest files
    /**
     * String = name of parent ZIP <br>
     * JsonObject = that ZIP's manifest file
     */
    BiMap<String, JsonObject> manifests = HashBiMap.create();
    try {
      for (ZipFile zip : zipFiles.values()) {
        ZipFileReader reader = new ZipFileReader(zip);
        JsonObject obj = new Gson().fromJson(
            ZipFileReader.asJsonReader(reader.getStream("manifest.json")), JsonObject.class);

        manifests.put(zip.getName(), obj);
      }
    } catch (IOException i) {
      i.printStackTrace();
    }

    // For each manifest file, read and bind all listed images
    for (String name : manifests.keySet()) {
      JsonObject manifest = manifests.get(name);

      // For each entry in the manifest
      manifest.entrySet().forEach((entry) -> {

        // Get the key and value
        String key = entry.getKey();
        JsonElement rawValue = entry.getValue();
        String readValue = "default";

        try {
          readValue = rawValue.getAsString();
        } catch (Exception e) {
          e.printStackTrace();
        }

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
      });


    }

    return images;
  }

  /**
   * @return The default image for a non-functioning texture.
   */
  private InputStream getDefaultImageAsStream() {
    ZapByte.LOGGER.error("NO DEFAULT IMAGE TextureManager#getDefaultImageAsStream");
    return null;
  }

  public TextureAtlas getAtlas() {
    return atlas;
  }
}
