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
package vision.voltsofdoom.silverspark.core;

import vision.voltsofdoom.coresystem.play.adventure.Adventure;
import vision.voltsofdoom.coresystem.universal.registry.RegistryTypes;
import vision.voltsofdoom.silverspark.graphic.EntityRenderer;
import vision.voltsofdoom.silverspark.graphic.TextRenderer;
import vision.voltsofdoom.silverspark.graphic.Window;
import vision.voltsofdoom.silverspark.state.LevelState;
import vision.voltsofdoom.silverspark.state.StateMachine;
import vision.voltsofdoom.zapbyte.loading.registry.Registry;
import vision.voltsofdoom.zapbyte.loading.registry.RegistryType;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;

/**
 * The game class just initializes the game and starts the game loop. After
 * ending the loop it will get disposed.
 *
 * @author Heiko Brumme
 */
public abstract class Game {

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

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
    protected Window window;
    /**
     * Used for timing calculations.
     */
    protected Timer timer;
    /**
     * Used for rendering.
     */
    protected EntityRenderer entityRenderer;
    /**
     * Used for rendering text.
     */
    protected TextRenderer textRenderer;
    /**
     * Stores the current state.
     */
    protected StateMachine state;

    /**
     * Default contructor for the game.
     */
    public Game() {
        timer = new Timer();
        entityRenderer = new EntityRenderer();
        textRenderer = new TextRenderer();
        state = new StateMachine();
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
        window = new Window(640, 380, "Simple Game - Pong", true);

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
        state.add("game", new LevelState(entityRenderer, textRenderer));
        state.change("game");
        
        Adventure adventure = (Adventure) Registry.getTyped(RegistryTypes.ADVENTURES).retrieveSupplier(new ResourceLocation("", "")).get();
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
    public void input() {
        state.input();
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

    /**
     * Determines if the OpenGL context supports version 3.2.
     *
     * @return true, if OpenGL context supports version 3.2, else false
     */
    public static boolean isDefaultContext() {
        boolean isDefault = GL.getCapabilities().OpenGL32;
        //Logger.getLogger(Game.class.getName()).log(Level.INFO, "Is default context? " + isDefault);
        return isDefault;
    }

}
