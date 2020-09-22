/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2016, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package vision.voltsofdoom.gamebase.core;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.glfw.GLFWErrorCallback;
import vision.voltsofdoom.gamebase.display.MenuTemplate;
import vision.voltsofdoom.gamebase.state.LevelState;
import vision.voltsofdoom.gamebase.state.StateMachine;
import vision.voltsofdoom.silverspark.graphic.MouseEventMenuHandler;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.SilverSpark;
import vision.voltsofdoom.silverspark.math.Vector2f;
import vision.voltsofdoom.gamebase.state.MenuState;
import vision.voltsofdoom.silverspark.render.ListRenderer;
import vision.voltsofdoom.silverspark.render.TextRenderer;

/**
 * The game class just initializes the game and starts the game loop. After
 * ending the loop it will get disposed.
 *
 * @author Heiko Brumme
 */
public abstract class Game {

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
    protected SilverSpark window;
    /**
     * Used for timing calculations.
     */
    protected Timer timer;
    /**
     * Used for rendering.
     */
    private ListRenderer entityRenderer;
    /**
     * Used for rendering text.
     */
    private TextRenderer textRenderer;

    private MouseEventMenuHandler mouseHandler;

    private final MenuTemplate template;

    private final String resourceRoot;
    /**
     * Stores the current state.
     */
    protected StateMachine state;

    /**
     * Default contructor for the game.
     */
    public Game(String resourceRoot) {
      this.resourceRoot = resourceRoot;
      timer = new Timer();
      entityRenderer = new ListRenderer();
      textRenderer = new TextRenderer();
      state = new StateMachine();
      template = new MenuTemplate(new Vector2f(50,100), new Vector2f(10,10), new Vector2f(60, 10), 60, "Inconsolata:50:WHITE", VODColor.WHITE);
    }

    /**
     * This should be called to initialize and start the game.
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
        entityRenderer.dispose();

        /* Dispose renderer */
        textRenderer.dispose();

        /* Set empty state to trigger the exit method in the current state */
        state.change(null);

        /* Release window and its callbacks */
        window.destroy();

        /* Terminate GLFW and release the error callback */
        glfwTerminate();
        errorCallback.free();
    }

    /**
     * Initializes the game.
     */
    public void init() {
        /* Set error callback */
        errorCallback = GLFWErrorCallback.createPrint();
        glfwSetErrorCallback(errorCallback);

        /* Initialize GLFW */
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        /* Create GLFW window */
        window = new SilverSpark( WINDOW_WIDTH, WINDOW_HEIGHT, "Vaults of Doom", true);

        mouseHandler = new MouseEventMenuHandler(window.getId());

        /* Initialize timer */
        timer.init();

        /* Initialize entity renderer */
        entityRenderer.init();

        /* Initialize text renderer */
        textRenderer.init();
        /* Initialize states */
        initStates();

        /* Initializing done, set running to true */
        running = true;
    }

    /**
     * Initializes the states.
     */
    public void initStates() {
        state.add("menu", new MenuState(window.getId(), mouseHandler, entityRenderer, textRenderer, template, resourceRoot));
        state.add("Btn1", new LevelState(window.getId(), mouseHandler, entityRenderer, textRenderer, resourceRoot));
        state.add("Btn2", new LevelState(window.getId(), mouseHandler, entityRenderer, textRenderer, resourceRoot));
        state.change("menu");
    }

    /**
     * The game loop. <br>
     * For implementation take a look at <code>VariableDeltaGame</code> and
     * <code>FixedTimestepGame</code>.
     */
    public abstract void gameLoop();

    /**
     * Handles input.
     */
    public String input() {
        return state.input();
    }

    /**
     * Updates the game (fixed timestep).
     */
    public void update() {
        state.update();
    }

    /**
     * Updates the game (variable timestep).
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
     * Synchronizes the game at specified frames per second.
     *
     * @param fps Frames per second
     */
    public void sync(int fps) {
        double lastLoopTime = timer.getLastLoopTime();
        double now = timer.getTime();
        float targetTime = 1f / fps;

        while (now - lastLoopTime < targetTime) {
            Thread.yield();

            /* This is optional if you want your game to stop consuming too much
             * CPU but you will loose some accuracy because Thread.sleep(1)
             * could sleep longer than 1 millisecond */
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            now = timer.getTime();
        }
    }

  public String getResourceRoot() {
    return resourceRoot;
  }


}
