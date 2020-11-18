package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

import vision.voltsofdoom.gamebase.collision.BoundingBox;

public class Node {

  private final BoundingBox bounds;
  private Node rightNode;
  private Node downNode;
  private RectangularBoxPacker packer;

  public Node(RectangularBoxPacker packer, BoundingBox bounds) {
    this.packer = packer;
    this.bounds = bounds;
  }

  public BoundingBox getBounds() {
    return bounds;
  }

  public void resolveChildren() {
    this.rightNode = new Node(packer, new BoundingBox( //
        bounds.max.x, // Bottom left corner
        bounds.min.y, // Bottom left corner
        packer.getBounds().getWidth() - (bounds.max.x), // Distance between the edge of the packer,
                                                        // and the closest edge of the node
        packer.getBounds().getHeight() - ( // Height of the packer
        (packer.getBounds().max.y - bounds.max.y) // The distance from the node to the top
            - // Take
            (bounds.min.y - packer.getBounds().min.y)))); // From the node to the bottom

    this.downNode = new Node(packer, new BoundingBox( //
        bounds.min.x, //
        packer.getBounds().min.y, // Bottom of the page
        bounds.min.y, //
        bounds.getWidth()) //
    );
  }

  // Get and set

  public Node getDownNode() {
    return downNode;
  }

  public Node getRightNode() {
    return rightNode;
  }

  public void setDownNode(Node downNode) {
    this.downNode = downNode;
  }

  public void setRightNode(Node rightNode) {
    this.rightNode = rightNode;
  }
}
