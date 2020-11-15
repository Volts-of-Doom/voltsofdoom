package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.coresystem.universal.resource.image.imagepacker.Node;
import vision.voltsofdoom.coresystem.universal.resource.image.imagepacker.Packer;

public class TextureAtlas {

  private BufferedImage image;
  private HashMap<String, ICoordinateAlignedImageDataProvider> bindings =
      new HashMap<String, ICoordinateAlignedImageDataProvider>();

  public TextureAtlas(List<ITextureAtlasEntry> rawAtlasEntries) {
    VoltsOfDoomCoreSystem.easyInfo("Constructing TextureAtlas");
    this.image = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
    stitch(rawAtlasEntries, image);
  }

  protected BufferedImage getAtlasImage() {
    return image;
  }

  public HashMap<String, ICoordinateAlignedImageDataProvider> getBindings() {
    return bindings;
  }

  public BufferedImage fetch(String key) {
    ICoordinateAlignedImageDataProvider provider = bindings.get(key);

    return image.getSubimage((int) Math.round(provider.getCoordinate().getX()),
        (int) Math.round(provider.getCoordinate().getY()), provider.getWidth(),
        provider.getHeight());
  }

  public BufferedImage stitch(List<ITextureAtlasEntry> rawAtlasEntries, BufferedImage baseImage) {
    VoltsOfDoomCoreSystem.easyDebug("Stitching TextureAtlas");

    // Create the list of nodes
    ArrayList<Node> nodes = new ArrayList<Node>();
    rawAtlasEntries.forEach((entry) -> nodes
        .add(new Node(entry.getName(), entry.getImage().getWidth(), entry.getImage().getHeight())));
    VoltsOfDoomCoreSystem.easyDebug("Node list compiled");

    // Sort the nodes by size, largest width first
    Collections.sort(nodes, new Comparator<Node>() {
      @Override
      public int compare(Node a, Node b) {
        return (Double.compare(b.width, a.width));
      }
    });
    VoltsOfDoomCoreSystem.easyDebug("Node list sorted");

    // Pack the blocks
    VoltsOfDoomCoreSystem.easyDebug("Packing images...");
    Packer packer = new Packer(1, 64, 64);
    packer.fit(nodes);
    VoltsOfDoomCoreSystem.easyDebug("Packed!");
    
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().error("Oops! No image is returned! Passing on...");

    return baseImage;
  }
}
