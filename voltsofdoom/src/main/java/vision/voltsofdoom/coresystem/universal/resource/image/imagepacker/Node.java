/*
 * Copyright (c) 2017, alexbonilla All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer. * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package vision.voltsofdoom.coresystem.universal.resource.image.imagepacker;

/**
 * This code is used under the attached licence, which is originally owned by alexbonilla, though
 * has been heavily modified by GenElectrovise for Volts of Doom.
 * 
 * @author alexbonilla
 * @author GenElectrovise
 */
public class Node {
  private boolean root = false;
  private String name;
  private double xPos;
  private double yPos;
  private double width;
  private double height;
  private boolean used = false;
  private Node rightNode = null;
  private Node downNode = null;
  private Node fit = null;

  public Node(String name, double width, double height) {
    this.name = name;
    this.width = width;
    this.height = height;
  }

  public Node(double xPos, double yPos, double width, double height) {
    setxPos(xPos);
    setyPos(yPos);
    setWidth(width);
    setHeight(height);

    if (getxPos() == 0 && getyPos() == 0) {
      setRoot(true);
      // this is only necessary for me to print 'Pack Starts Here' in the example
      // code
    }
  }

  /**
   * Populates the given {@link Node}s children.
   * 
   * @param packer
   * @param width
   * @param height
   */
  public void activateChildNodes(Packer packer, double width, double height) {
    this.setUsed(true);

    // Set the nodes below and right in the puzzle
    this.setRightNode(new Node(this.getxPos(), this.getyPos() + height, this.getWidth(),
        this.getHeight() - height));
    this.setRightNode(
        new Node(this.getxPos() + width, this.getyPos(), this.getWidth() - width, height));
  }


  // Get and set

  public boolean isRoot() {
    return root;
  }

  public void setRoot(boolean root) {
    this.root = root;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getxPos() {
    return xPos;
  }

  public void setxPos(double xPos) {
    this.xPos = xPos;
  }

  public double getyPos() {
    return yPos;
  }

  public void setyPos(double yPos) {
    this.yPos = yPos;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public boolean isUsed() {
    return used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public Node getRightNode() {
    return rightNode;
  }

  public void setRightNode(Node rightNode) {
    this.rightNode = rightNode;
  }

  public Node getDownNode() {
    return downNode;
  }

  public void setDownNode(Node downNode) {
    this.downNode = downNode;
  }

  public Node getFit() {
    return fit;
  }

  public void setParent(Node fit) {
    this.fit = fit;
  }

}
