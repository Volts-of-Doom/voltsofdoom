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
import java.util.Iterator;

/**
 * @author alexbonilla
 */
public class Packer {

  private final ArrayList<Node> root = new ArrayList<Node>();

  /**
   * The basic width and height should be that of the largest node you wish to pack.
   * 
   * @param numofpackets The number of packets to assign
   * @param width The basic width of the image.
   * @param height The basic height of the image.
   */
  public Packer(int numofpackets, double width, double height) {
    for (int i = 0; i < numofpackets; i++) {
      this.root.add(new Node(0, 0, width, height));
    }
  }

  /**
   * 
   * @param blocks
   */
  public void fit(ArrayList<Node> blocks) {
    Node node;
    Node block;
    Iterator<Node> blockItr = blocks.iterator();
    int n = 0;

    // For each unfitted block
    while (blockItr.hasNext()) {
      block = blockItr.next();

      // Set node to a foundNode of the right size. If node isn't null.
      if ((node = this.findNode(this.root.get(n), block.width, block.height)) != null) {

        // Fit the block in.
        block.fit = this.splitNode(node, block.width, block.height);
        
        //If node is the root, set this block's isRoot property to true.
        if (node.isRoot) {
          block.fit.isRoot = true;
        }
      } else {
        n++;
      }
    }
  }

  /**
   * Finds an empty child node with the minimum size W and H.
   * 
   * @param root The root {@link Node}.
   * @param width
   * @param height
   * @return A vacant child {@link Node}.
   */
  public Node findNode(Node root, double width, double height) {

    // If the root node is taken
    if (root.used) {
      Node right = findNode(root.right, width, height);

      // Return recursively, calling this method to cycle through other nodes.
      return (right != null ? right : findNode(root.down, width, height));

      // If the w is bigger than the root's width, or similar with height
    } else if ((width <= root.width) && (height <= root.height)) {
      return root;
    } else {

      // No valid node
      return null;
    }
  }

  /**
   * Creates two children {@link Node}s of the given {@link Node}.
   * 
   * @param node
   * @param width
   * @param height
   * @return The split {@link Node}.
   */
  public Node splitNode(Node node, double width, double height) {
    node.used = true;

    // Set the nodes below and right in the puzzle
    node.down = new Node(node.xPos, node.yPos + height, node.width, node.height - height);
    node.right = new Node(node.xPos + width, node.yPos, node.width - width, height);
    return node;
  }

}
