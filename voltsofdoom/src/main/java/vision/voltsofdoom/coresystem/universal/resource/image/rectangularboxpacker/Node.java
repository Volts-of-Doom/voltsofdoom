package vision.voltsofdoom.coresystem.universal.resource.image.rectangularboxpacker;

public class Node {

  private String name;
  private boolean used;
  private int height;
  private int width;
  private Node rightNode;
  private Node downNode;
  private int x;
  private int y;

  public Node(String name, int x, int y, boolean used, int height, int width) {
    this.name = name;
    this.x = x;
    this.y = y;
    this.used = used;
    this.height = height;
    this.width = width;
    this.rightNode = null;
    this.downNode = null;
  }

  /**
   * Puts the given {@link Box} into this {@link Node}, and creates surrounding vacant
   * {@link Node}s.
   * @param root 
   * 
   * @param box
   */
  public void insertBox(Node root, Box box) {
    this.name = box.getName();
    this.used = true;

    // vacant node, not used, same height, smaller width
    this.rightNode = new Node("vacant", x + getWidth(), y, false, this.getHeight(), root.getWidth() - (x + width));
    // vacant node, not used, smaller height, same width
    this.downNode = new Node("vacant", x, y + getHeight(), false, 0, this.getWidth());

    this.height = box.getHeight();
    this.width = box.getWidth();
  }

  public Node nextNode() {

    if (!this.isUsed()) {
      return this;
    }

    if (!rightNode.isUsed()) {
      return rightNode;
    }

    if (!downNode.isUsed()) {
      return downNode;
    }

    return this;
  }

  /**
   * @return The right node if vacant, else the down node.
   */
  public Node getBestChild() {
    return !rightNode.isUsed() ? rightNode : downNode;
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

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
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
}
