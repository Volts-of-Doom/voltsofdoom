package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.misc.util.StringUtils;

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
  private int width;
  private int height;

  public RectangularBoxPacker(List<Box> boxes) {
    VoltsOfDoomCoreSystem.easyDebug("RectangularBoxPacker is go!");
    VoltsOfDoomCoreSystem.easyDebug("boxes=" + boxes);

    sortNodeListByWidth(boxes);
    VoltsOfDoomCoreSystem.easyDebug("sortedBoxes=" + boxes);

    this.boxes = boxes;
  }

  public void pack() {

    for (Box box : boxes) {
      walkToFindFittingNode(box).insertBox(root, box);
    }
  }

  public Node walkToFindFittingNode(Box box) {

    if (root == null || !root.isUsed()) {
      root = new Node(box.getName(), 0, 0, true, box.getHeight(), box.getWidth());
      root.insertBox(root, box);
      this.height = root.getHeight();
      this.width = root.getWidth();
      return root;
    }

    // We have a root!

    Integer[] layerWidths = getLayersAndWidths();
    // We now know the depth of the tree and the width of each layer

    // We now go through all of the layers, and walk to the ends of all of them, to check if a node
    // will fit the box
    // For each layer depth
    for (int targetDepth = 0; targetDepth < layerWidths.length + 1; targetDepth++) {

      // Get the root node of that layer
      Node layerRoot = root;
      for (int depth = 0; depth < targetDepth; depth++) {
        layerRoot = layerRoot.getDownNode();
      }

      // If the box is too tall for the layer, next layer
      if (layerRoot.getHeight() <= box.getHeight()) {

        // If the layer root isn't used, then it must be the bottom layer
        if (!layerRoot.isUsed()) {

          // If it's the bottom layer, we can expand it!
          layerRoot.setHeight(box.getHeight());
        } else {

          // If it's too small, and isn't the bottom layer, then move on
          continue;
        }
      }

      // Otherwise, we know the box is short enough, so we can find the farthest along node on the
      // layer, and see if it is long enough for this box

      Node farthestNode = layerRoot;
      // Get the farthest along node on this layer
      for (int currentWidth = 0; currentWidth < layerWidths[targetDepth]; currentWidth++) {
        farthestNode = farthestNode.getRightNode();
      }

      // If the box is too wide, next layer
      if (!(farthestNode.getWidth() <= box.getWidth())) {
        continue;
      }

      // The farthest node is big enough!!
      return farthestNode;
    }

    // No current fitting node was found at the end of a layer, so we'll make a new layer
    Node lowestLayer = root;
    while (lowestLayer.getDownNode().isUsed()) {
      lowestLayer = lowestLayer.getDownNode();
    }
    lowestLayer.setDownNode(new Node(box.getName(), 0, lowestLayer.getY() + lowestLayer.getHeight(),
        false, box.getHeight(), root.getWidth()));

    return lowestLayer.getDownNode();
  }

  private Integer[] getLayersAndWidths() {

    // Depth
    Node branchOfTreeRoot = root;
    int treeDepth = 0;

    while (branchOfTreeRoot.getDownNode() != null) {
      branchOfTreeRoot = branchOfTreeRoot.getDownNode();
      treeDepth++;
    }

    VoltsOfDoomCoreSystem.easyDebug("Down node count = " + treeDepth);

    Integer[] widths = new Integer[treeDepth];

    // For each layer of the tree, find the width
    for (int layer = 0; layer < treeDepth; layer++) {
      Node rootOfLayer = root;
      int layerWidth = 0;

      // Get to the right layer
      for (int depthOfLayerRoot = 0; depthOfLayerRoot <= layer; depthOfLayerRoot++) {
        rootOfLayer = rootOfLayer.getDownNode();
      }

      while (rootOfLayer.getRightNode() != null) {
        layerWidth++;
      }

      widths[layer] = layerWidth;
      VoltsOfDoomCoreSystem.easyDebug("Tree layer:" + layer + " Layer of width:" + layerWidth);
    }

    VoltsOfDoomCoreSystem.easyDebug("Layer widths: " + StringUtils.arrayToString(widths));

    return widths;
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

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
