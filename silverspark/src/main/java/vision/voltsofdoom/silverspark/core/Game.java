/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2016, Heiko Brumme
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
package vision.voltsofdoom.silverspark.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.graphic.MouseEventMenuHandler;
import vision.voltsofdoom.silverspark.render.TextRenderer;
import vision.voltsofdoom.silverspark.xnotsilverspark.state.EmptyState;
import vision.voltsofdoom.silverspark.xnotsilverspark.state.StateMachine;

/**
 * Initialises the game and starts the game loop. After ending the loop it will
 * get disposed.
 *
 * @author Heiko Brumme
 */

@Deprecated // Render loop now managed by Silverspark, game loop not implemented yet. This class retained for reference only
public class Game {

  public static final int TARGET_FPS = 75;
  public static final int TARGET_UPS = 30;
  public static final int WINDOW_WIDTH = 640;
  public static final int WINDOW_HEIGHT = 380;

  /**
   * The error callback for GLFW.
   */
  private GLFWErrorCallback errorCallback;

  /**
   * Shows if the game is running.
   */
  protected boolean running;

  /**
   * The GLFW window used by the game.
   */
  protected Silverspark window;
  /**
   * Used for timing calculations.
   */
  protected ITimer timer;
  /**
   * Used for rendering text.
   */
  private TextRenderer textRenderer;

  private MouseEventMenuHandler mouseHandler;

  /**
   * Stores the current state.
   */
  protected StateMachine state;
  
  private String name;

  /**
   * Default constructor for the game.
   */
  public Game(String name) { 
    this.name = name;
    
    timer = new Timer();
    textRenderer = new TextRenderer();
    state = new StateMachine();
  }

  /**
   * This should be called to initialise and start the game.
   */
  public void start() {
    init();
    gameLoop();
    dispose();
  }

  /**
   * Releases resources that where used by the game.
   */
  public void dispose() {


    /* Dispose renderer */
    textRenderer.dispose();

    /* Set empty state to trigger the exit method in the current state */
    state.change(null);

    /* Release window and its call-backs */
    window.destroy();

    /* Terminate GLFW and release the error callback */
    GLFW.glfwTerminate();
    errorCallback.free();
  }

  /**
   * Initialises the game.
   */
  public void init() {
    /* Set error callback */
    errorCallback = GLFWErrorCallback.createPrint();
    GLFW.glfwSetErrorCallback(errorCallback);

    /* Initialise GLFW */
    if (!GLFW.glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW!");
    }

    /* Create Silverspark to manage the window window */
    window = new Silverspark(WINDOW_WIDTH, WINDOW_HEIGHT, name, true);

    //setMouseHandler(new MouseEventMenuHandler(window.getId()));

    /* Initialise timer */
    timer.init();

     /* Initialise text renderer */
    textRenderer.init();
    /* Initialise states */
    initStates();

    /* Initialising done, set running to true */
    running = true;
  }

  /**
   * Initialises the states.
   */
  public void initStates() {
    state.add("empty", new EmptyState());
    state.change("empty");
  }

  /**
   * The game loop. <br>
   * For implementation take a look at <code>VariableDeltaGame</code> and
   * <code>FixedTimestepGame</code>.
   */

  public void gameLoop() {
    float delta;

    while (running) {
      /* Check if game should close */
      if (window.isClosing()) {
        running = false;
      }

      /* Get delta time */
      delta = timer.getDelta();

      /* Handle input */
      input();

      /* Update game and timer UPS */
      update(delta);
      timer.updateUPS();

      /* Render game and update timer FPS */
      render();
      timer.updateFPS();

      /* Update timer */
      timer.update();

      /* Update window to show the new screen */
      window.update();

      /* Synchronise if v-sync is disabled */
      if (!window.isVSyncEnabled()) {
        sync(TARGET_FPS);
      }
    }
  }

  /**
   * Handles input.
   */
  public void input() {
    state.input();
  }

  /**
   * Updates the game (fixed time step).
   */
  public void update() {
    state.update();
  }

  /**
   * Updates the game (variable time step).
   *
   * @param delta Time difference in seconds
   */
  public void update(float delta) {
    state.update(delta);
  }

  /**
   * Renders the game (no interpolation).
   */
  public void render() {
    state.render();
  }

  /**
   * Renders the game (with interpolation).
   *
   * @param alpha Alpha value, needed for interpolation
   */
  public void render(float alpha) {
    state.render(alpha);
  }

  /**
   * Synchronises the game at specified frames per second.
   *
   * @param fps Frames per second
   */
  public void sync(int fps) {
    double lastLoopTime = timer.getLastLoopTime();
    double now = timer.getTime();
    float targetTime = 1f / fps;

    while (now - lastLoopTime < targetTime) {
      Thread.yield();

      /*
       * This is optional if you want your game to stop consuming too much CPU, but you will loose some
       * accuracy because Thread.sleep(1) could sleep longer than 1 millisecond
       */
      try {
        Thread.sleep(1);
      } catch (InterruptedException ex) {
        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
      }

      now = timer.getTime();
    }
  }

  public static boolean isDefaultContext() {
    System.err.println("default context game #285 is hardcode true");
    return true;
  }

  /**
   * @return the mouseHandler
   */
  public MouseEventMenuHandler getMouseHandler() {
    return mouseHandler;
  }

  /**
   * @param mouseHandler the mouseHandler to set
   */
  public void setMouseHandler(MouseEventMenuHandler mouseHandler) {
    this.mouseHandler = mouseHandler;
  }

}
