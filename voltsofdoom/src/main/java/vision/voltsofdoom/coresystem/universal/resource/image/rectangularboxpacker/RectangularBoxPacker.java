package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import vision.voltsofdoom.gamebase.collision.BoundingBox;

/**
 * The packer will always try to pack to the right of the latest node if possible. It will always
 * pack in the top left corner.
 * 
 * @author GenElectrovise
 *
 */
public class RectangularBoxPacker {

  private final List<Box> boxes;
  private Node root = null;
  private BoundingBox bounds;

  public RectangularBoxPacker(List<Box> boxes) {
    sortNodeListByWidth(boxes);
    this.boxes = boxes;
  }

  public void pack() {

    for (Box box : boxes) {
      if (root == null) {

        // Set the root to a new node
        root = new Node(//
            this, //
            new BoundingBox( //
                0, //
                0, //
                box.getBounds().getWidth(), //
                box.getBounds().getHeight())//
        );

        this.bounds = root.getBounds();
      }

      Node node = findNextVacantFittingNode();
    }
  }

  private Node findNextVacantFittingNode() {

    // Start with the root
    Node nextVacantFittingNode = root;

    return nextVacantFittingNode;
  }

  /**
   * Sorts the nodes by width, largest first.
   */
  public static List<Box> sortNodeListByWidth(List<Box> boxes2) {
    Collections.sort(boxes2, new Comparator<Box>() {
      @Override
      public int compare(Box a, Box b) {
        return (Double.compare(a.getBounds().getWidth(), b.getBounds().getWidth()));
      }
    });

    return boxes2;
  }

  public List<Box> getBoxes() {
    return boxes;
  }

  public Node getRoot() {
    return root;
  }

  public BoundingBox getBounds() {
    return bounds;
  }
  
  public static enum Direction {
    RIGHT, DOWN;
  }
}
