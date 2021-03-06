/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2015, Heiko Brumme
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
package vision.voltsofdoom.silverspark;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

/**
 * This class represents a GLFW window.
 *
 * @author Heiko Brumme
 */
public class Silverspark {

  /**
   * Stores the window handle.
   */
  private final long id;

  /**
   * Key callback for the window.
   */
  private final GLFWKeyCallback keyCallback;

  /**
   * Shows if vsync is enabled.
   */
  private boolean vsync;

  /**
   * Creates a GLFW window and its OpenGL context with the specified width, height and title.
   *
   * @param width Width of the drawing area
   * @param height Height of the drawing area
   * @param title Title of the window
   * @param vsync Set to true, if you want v-sync
   */
  public Silverspark(int width, int height, CharSequence title, boolean vsync) {
    this.vsync = vsync;

    /* Creating a temporary window for getting the available OpenGL version */
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
    glfwMakeContextCurrent(temp);
    GL.createCapabilities();
    GLCapabilities caps = GL.getCapabilities();
    glfwDestroyWindow(temp);

    /* Reset and set window hints */
    glfwDefaultWindowHints();
    if (caps.OpenGL32) {
      /* Hints for OpenGL 3.2 core profile */
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
      glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    } else if (caps.OpenGL21) {
      /* Hints for legacy OpenGL 2.1 */
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
    } else {
      throw new RuntimeException("Neither OpenGL 3.2 nor OpenGL 2.1 is "
          + "supported, you may want to update your graphics driver.");
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

    /* Create window with specified OpenGL context */
    id = glfwCreateWindow(width, height, title, NULL, NULL);
    if (id == NULL) {
      glfwTerminate();
      throw new RuntimeException("Failed to create the GLFW window!");
    }

    /* Center window on screen */
    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    glfwSetWindowPos(id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

    /* Create OpenGL context */
    glfwMakeContextCurrent(id);
    GL.createCapabilities();

    /* Enable v-sync */
    if (vsync) {
      glfwSwapInterval(1);
    }

    /* Set key callback */
    keyCallback = new GLFWKeyCallback() {
      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
          glfwSetWindowShouldClose(window, true);
        }
      }
    };
    glfwSetKeyCallback(id, keyCallback);

    // glfwSetInputMode(id, GLFW_STICKY_MOUSE_BUTTONS, GLFW_TRUE);



  }

  /**
   * Returns if the window is closing.
   *
   * @return true if the window should close, else false
   */
  public boolean isClosing() {
    return glfwWindowShouldClose(id);
  }

  /**
   * Sets the window title
   *
   * @param title New window title
   */
  public void setTitle(CharSequence title) {
    glfwSetWindowTitle(id, title);
  }

  /**
   * Updates the screen.
   */
  public void update() {
    glfwSwapBuffers(id);
    glfwPollEvents(); // TODO - is this the right place to poll for events?
  }

  /**
   * Destroys the window and releases its callbacks.
   */
  public void destroy() {
    glfwDestroyWindow(id);
    keyCallback.free();
  }

  /**
   * Setter for v-sync.
   *
   * @param vsync Set to true to enable v-sync
   */
  public void setVSync(boolean vsync) {
    this.vsync = vsync;
    if (vsync) {
      glfwSwapInterval(1);
    } else {
      glfwSwapInterval(0);
    }
  }

  /**
   * Determines if the OpenGL context supports version 3.2.
   *
   * @return true, if OpenGL context supports version 3.2, else false
   */
  public static boolean isDefaultContext() {
    boolean isDefault = GL.getCapabilities().OpenGL32;
    // Logger.getLogger(Game.class.getName()).log(Level.INFO, "Is default context? " + isDefault);
    return isDefault;
  }

  /**
   * Check if v-sync is enabled.
   *
   * @return true if v-sync is enabled
   */
  public boolean isVSyncEnabled() {
    return this.vsync;
  }

  public long getId() {
    return id;
  }

}
