/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2017, Heiko Brumme
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
package vision.voltsofdoom.silverspark.graphic;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

/**
 * This class represents a shader.
 *
 * @author Heiko Brumme
 */
public class Shader {

  /**
   * Stores the handle of the shader.
   */
  private final int id;

  /**
   * Creates a shader with specified type. The type in the tutorial should be either
   * <code>GL_VERTEX_SHADER</code> or <code>GL_FRAGMENT_SHADER</code>.
   *
   * @param type Type of the shader
   */
  public Shader(int type) {
    id = glCreateShader(type);
  }

  /**
   * Sets the source code of this shader.
   *
   * @param source GLSL Source Code for the shader
   */
  public void source(CharSequence source) {
    glShaderSource(id, source);
  }

  /** Compiles the shader and checks it's status afertwards. */
  public void compile() {
    glCompileShader(id);

    checkStatus();
  }

  /**
   * Checks if the shader was compiled successfully.
   */
  private void checkStatus() {
    int status = glGetShaderi(id, GL_COMPILE_STATUS);
    if (status != GL_TRUE) {
      throw new RuntimeException(glGetShaderInfoLog(id));
    }
  }

  /**
   * Deletes the shader.
   */
  public void delete() {
    glDeleteShader(id);
  }

  /**
   * Getter for the shader ID.
   *
   * @return Handle of this shader
   */
  public int getID() {
    return id;
  }


}
