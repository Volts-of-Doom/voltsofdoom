package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

public class Node {

  private String name;
  private boolean used;
  private int height;
  private int width;
  private Node rightNode;
  private Node downNode;

  public Node(String name, boolean used, int height, int width) {
    this.name = name;
    this.used = used;
    this.height = height;
    this.width = width;
    this.rightNode = null;
    this.downNode = null;
  }

  /**
   * Puts the given {@link Box} into this {@link Node}, and creates surrounding vacant
   * {@link Node}s.
   * 
   * @param box
   */
  public void insertBox(Box box) {
    this.name = box.getName();
    this.used = true;

    // vacant node, not used, same height, smaller width
    this.rightNode = this.getWidth() < box.getWidth()
        ? new Node("vacant", false, this.getHeight(), this.getWidth() - box.getWidth())
        : null;
    // vacant node, not used, smaller height, same width
    this.downNode = this.getHeight() < box.getHeight()
        ? new Node("vacant", false, this.getHeight() - box.getHeight(), this.getWidth())
        : null;

    this.height = box.getHeight();
    this.width = box.getWidth();
  }

  /**
   * @return The right node if vacant, else the down node.
   */
  public Node getBestChild() {
    return rightNode == null ? rightNode : downNode;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Node{");

    builder.append("name=" + name);
    builder.append(" used=" + used);
    builder.append(" height=" + height);
    builder.append(" width=" + width);
    builder.append(" rightNode=" + (rightNode != null));
    builder.append(" downNode=" + (downNode != null));

    builder.append("}");
    return builder.toString();
  }

  // Get and set

  public String getName() {
    return name;
  }

  public boolean isUsed() {
    return used;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

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

  public boolean canFit(Box box) {
    return box.getWidth() >= this.getWidth() && box.getHeight() >= this.getHeight();
  }
}
