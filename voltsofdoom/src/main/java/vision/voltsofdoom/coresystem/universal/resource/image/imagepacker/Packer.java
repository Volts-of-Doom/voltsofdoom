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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This code is used under the attached licence, which is originally owned by alexbonilla, though
 * has been heavily modified by GenElectrovise for Volts of Doom.
 * 
 * @author alexbonilla
 * @author GenElectrovise
 */
public class Packer {

  private final ArrayList<Node> inputNodes;

  /**
   * The basic width and height should be that of the largest node you wish to pack.
   * 
   * @param numofpackets The number of packets to assign
   * @param width The basic width of the image.
   * @param height The basic height of the image.
   */
  public Packer(ArrayList<Node> inputNodes) {
    this.inputNodes = inputNodes;
  }

  /**
   * @return The fit {@link ArrayList} of {@link Node}s.
   */
  public ArrayList<Node> fitBlocks() {
    int nodeSearchDepth = 0;

    // For each unfitted block
    for (Node childNode : inputNodes) {

      // Set node to a foundNode of the right size. T
      Node parentNode = this.findFittingNode(this.inputNodes.get(nodeSearchDepth),
          childNode.getWidth(), childNode.getHeight());

      // If node isn't null...
      if (parentNode != null) {

        // Fit the block in.
        parentNode.activateChildNodes(this, childNode.getWidth(), childNode.getHeight());

        childNode.setParent(parentNode);

        // If node is the root, set this block's isRoot property to true.
        if (parentNode.isRoot()) {
          childNode.getFit().setRoot(true);
        }
      } else {
        nodeSearchDepth++;
      }
    }

    return inputNodes;
  }

  /**
   * Finds an empty child node with the minimum size W and H.
   * 
   * @param root The root {@link Node}.
   * @param width
   * @param height
   * @return A vacant child {@link Node}.
   */
  public Node findFittingNode(Node root, double width, double height) {

    // If the root node is taken
    if (root.isUsed()) {
      Node right = findFittingNode(root.getRightNode(), width, height);

      // Return recursively, calling this method to cycle through other nodes.
      return (right != null ? right : findFittingNode(root.getDownNode(), width, height));

      // If the w is bigger than the root's width, or similar with height
    } else if ((width <= root.getWidth()) && (height <= root.getHeight())) {
      return root;
    } else {

      // No valid node
      return null;
    }
  }

  /**
   * Sorts the nodes by width, largest first.
   */
  public static ArrayList<Node> sortNodeListByWidth(ArrayList<Node> nodes) {
    Collections.sort(nodes, new Comparator<Node>() {
      @Override
      public int compare(Node a, Node b) {
        return (Double.compare(b.getWidth(), a.getWidth()));
      }
    });

    return nodes;
  }

}
