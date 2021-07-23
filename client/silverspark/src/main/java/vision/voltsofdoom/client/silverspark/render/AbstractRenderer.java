/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2018, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package vision.voltsofdoom.client.silverspark.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import vision.voltsofdoom.client.silverspark.graphic.ShaderProgram;
import vision.voltsofdoom.client.silverspark.graphic.Spark;
import vision.voltsofdoom.client.silverspark.graphic.VODColor;

/**
 * This class is performing the rendering process.
 *
 * @author Heiko Brumme
 * @author GenElectrovise
 * @author Richard Spencer
 */
public abstract class AbstractRenderer {

  private ShaderProgram program;


  private boolean drawing;


  /**
   * Initialises the renderer.
   */
  public void init() {

    // Set up shader programs
    drawing = false;
    program = new ShaderProgram();
    program.setupShaderProgram();


    // Enable blending
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

  }

  /**
   * Clears the drawing area.
   */
  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  /**
   * Begin rendering. (reset vertex count)
   */
  public void begin() {
    if (drawing) {
      throw new IllegalStateException("Renderer is already drawing!");
    }
    drawing = true;
    program.setNumVertices(0);
  }

  /**
   * End rendering. (flush contents)
   */
  public void end() {
    if (!drawing) {
      throw new IllegalStateException("Renderer isn't drawing!");
    }
    drawing = false;
    program.flush();
  }

  /**
   * Draws a texture region with the currently bound texture on specified coordinates.
   *
   * @param texture Used for getting width and height of the texture
   * @param x X position of the texture
   * @param y Y position of the texture
   * @param regX X position of the texture region
   * @param regY Y position of the texture region
   * @param regWidth Width of the texture region
   * @param regHeight Height of the texture region
   */
  public void drawTextureRegion(Spark texture, float x, float y, float regX, float regY, float regWidth, float regHeight) {
    drawTextureRegion(texture, x, y, regX, regY, regWidth, regHeight, VODColor.WHITE);
  }

  /**
   * Draws a texture region with the currently bound texture on specified coordinates.
   *
   * @param texture Used for getting width and height of the texture
   * @param x X position of the texture
   * @param y Y position of the texture
   * @param regX X position of the texture region
   * @param regY Y position of the texture region
   * @param regWidth Width of the texture region
   * @param regHeight Height of the texture region
   * @param c The color to use
   */
  public void drawTextureRegion(Spark texture, float x, float y, float regX, float regY, float regWidth, float regHeight, VODColor c) {
    // Vertex positions
    float x1 = x;
    float y1 = y;
    float x2 = x + regWidth;
    float y2 = y + regHeight;

    // Texture coordinates
    float s1 = regX / texture.getWidth();
    float t1 = regY / texture.getHeight();
    float s2 = (regX + regWidth) / texture.getWidth();
    float t2 = (regY + regHeight) / texture.getHeight();

    drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c);
  }

  /**
   * Draws a texture region with the currently bound texture on specified coordinates.
   *
   * @param x1 Bottom left x position
   * @param y1 Bottom left y position
   * @param x2 Top right x position
   * @param y2 Top right y position
   * @param s1 Bottom left s coordinate
   * @param t1 Bottom left t coordinate
   * @param s2 Top right s coordinate
   * @param t2 Top right t coordinate
   */
  public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2) {
    drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, VODColor.WHITE);
  }

  /**
   * Draws a texture region with the currently bound texture on specified coordinates.
   *
   * @param x1 Bottom left x position
   * @param y1 Bottom left y position
   * @param x2 Top right x position
   * @param y2 Top right y position
   * @param s1 Bottom left s coordinate
   * @param t1 Bottom left t coordinate
   * @param s2 Top right s coordinate
   * @param t2 Top right t coordinate
   * @param c The colour to use
   */
  public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, VODColor c) {

    // If we need more space in the buffer, flush it
    if (program.getVertices().remaining() < 7 * 6) {
      program.flush();
    }

    // Get colour intensities.
    float r = c.getRed();
    float g = c.getGreen();
    float b = c.getBlue();
    float a = c.getAlpha();

    // Add vertices for each corner of the first triangle
    program.getVertices().put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
    program.getVertices().put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
    program.getVertices().put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
    // Add vertices for the second half of the triangle.
    program.getVertices().put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
    program.getVertices().put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
    program.getVertices().put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);

    // Update the program's vertex count.
    int numVertices = program.getNumVertices();
    program.setNumVertices(numVertices + 6);
  }

  /**
   * Dispose (delete) renderer and clean up its used data.
   */
  public void dispose() {
    program.delete();
  }

}
