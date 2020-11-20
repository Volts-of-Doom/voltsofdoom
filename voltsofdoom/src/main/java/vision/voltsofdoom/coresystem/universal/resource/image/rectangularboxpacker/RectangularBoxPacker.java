package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
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
    VoltsOfDoomCoreSystem.easyDebug("RectangularBoxPacker is go!");
    VoltsOfDoomCoreSystem.easyDebug("boxes=" + boxes);

    sortNodeListByWidth(boxes);
    VoltsOfDoomCoreSystem.easyDebug("sortedBoxes=" + boxes);

    this.boxes = boxes;
  }

  public void pack() {

    for (Box box : boxes) {
      findNextUnusedFittingNode(box).insertBox(box);
    }
  }

  private Node findNextUnusedFittingNode(Box box) {

    Box rootBox = this.getBoxes().get(0);
    this.root = new Node(rootBox.getName(), true, rootBox.getHeight(), rootBox.getWidth());

    // Start with the root
    Node nextVacantFittingNode = root;
    VoltsOfDoomCoreSystem.easyDebug(nextVacantFittingNode.toString());

    // While the bestChild is a Node
    while (nextVacantFittingNode.getBestChild() != null) {
      VoltsOfDoomCoreSystem.easyDebug("nextVacantFittingNode=" + nextVacantFittingNode);
      VoltsOfDoomCoreSystem.easyDebug("bestChild=" + nextVacantFittingNode.getBestChild());

      nextVacantFittingNode = nextVacantFittingNode.getBestChild();
    }

    return nextVacantFittingNode;
  }

  /**
   * Sorts the nodes by width, largest first.
   */
  public static List<Box> sortNodeListByWidth(List<Box> boxes2) {
    Collections.sort(boxes2, new Comparator<Box>() {
      @Override
      public int compare(Box a, Box b) {
        return (Double.compare(b.getWidth(), a.getWidth()));
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
