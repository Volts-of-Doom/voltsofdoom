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
import java.util.Iterator;

/**
 * This code is used under the attached licence, which is originally owned by alexbonilla, though
 * has been heavily modified by GenElectrovise for Volts of Doom.
 * 
 * @author alexbonilla
 * @author GenElectrovise
 */
public final class ExampleJavaBinPacker {

  @SuppressWarnings("unused")
  private static void example() {
    // 2 available packets to fill, 600x800 each
    ArrayList<Node> blocks = new ArrayList<Node>();

    blocks.add(new Node("Figure1", 300, 400));
    blocks.add(new Node("Figure2", 300, 400));
    blocks.add(new Node("Figure3", 300, 400));
    blocks.add(new Node("Figure4", 300, 400));
    blocks.add(new Node("Figure5", 300, 400));
    blocks.add(new Node("Figure6", 300, 400));
    blocks.add(new Node("Figure7", 300, 400));
    blocks.add(new Node("Figure8", 300, 400));
    blocks.add(new Node("Figure9", 300, 400));

    Collections.sort(blocks, new Comparator<Node>() {
      @Override
      public int compare(Node a, Node b) {

        return (Double.compare(b.getWidth(), a.getWidth())); // doing the sort based on the width, you can change it
                                           // accordingly to your needs.
      }
    });
    
    Packer packer = new Packer(blocks);

    packer.fitBlocks();
    Iterator<Node> blocksItr = blocks.iterator();
    while (blocksItr.hasNext()) {
      Node block = blocksItr.next();
      if (block.getFit() != null) {
        if (block.getFit().isRoot()) {
          System.out.format("%32s", "Pack Starts Here");
          System.out.println("");
          System.out.format("%32s%24s%16s%16s%16s", "Display name", "x", "y", "w", "h");
          System.out.println("");
        }
        System.out.format("%32s%24s%16s%16s%16s", block.getName(), block.getFit().getxPos(), block.getFit().getyPos(), block.getWidth(),
            block.getHeight());
        System.out.println("");
      }
    }

    System.out.println("");
  }

}
