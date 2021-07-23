package vision.voltsofdoom.client.silverspark;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import com.google.inject.Inject;
import vision.voltsofdoom.client.silverspark.api.IRenderState;
import vision.voltsofdoom.client.silverspark.api.IRenderable;
import vision.voltsofdoom.client.silverspark.api.IRenderableText;
import vision.voltsofdoom.client.silverspark.api.ITextureAtlas;
import vision.voltsofdoom.client.silverspark.core.ITimer;
import vision.voltsofdoom.client.silverspark.graphic.CatalogueEntry;
import vision.voltsofdoom.client.silverspark.graphic.MouseEventMenuHandler;
import vision.voltsofdoom.client.silverspark.graphic.Spark;
import vision.voltsofdoom.client.silverspark.graphic.SparkAtlas;
import vision.voltsofdoom.client.silverspark.render.SparkRenderer;
import vision.voltsofdoom.client.silverspark.render.TextRenderer;
import vision.voltsofdoom.client.silverspark.xnotsilverspark.state.StateMachine;

/**
 * Top level class of Silverspark window manager/renderer
 *
 * @author Richard Spencer (with thanks to Heiko Brumme/SilverTiger)
 */
public class Silverspark {

  public static final int TARGET_FPS = 75;
  public static final int TARGET_UPS = 30;
  public static final int WINDOW_WIDTH = 640;
  public static final int WINDOW_HEIGHT = 380;

  /**
   * Stores the window handle.
   */
  private long id; // TODO - should be final, but constructor makes that difficult

  /**
   * Key callback for the window.
   */
  private GLFWKeyCallback keyCallback; // TODO - should be final, but constructor makes that difficult

  /**
   * Shows if vsync is enabled.
   */
  private boolean vsync;

  // Stuff imported from Game.class
  /**
   * The error callback for GLFW.
   */
  private GLFWErrorCallback errorCallback;

  /**
   * Shows if the game is running.
   */
  protected boolean running;

  /**
   * Used for timing calculations.
   */
  @Inject
  protected ITimer timer;

  @Inject
  private SparkRenderer sparkRenderer;

  /**
   * Used for rendering text.
   */
  @Inject
  private TextRenderer textRenderer;

  @Inject
  private MouseEventMenuHandler mouseEventMenuHandler;

  @Inject
  private IRenderState renderState;

  /**
   * Stores the current state.
   */
  @Inject
  protected StateMachine stateMachine;

  @Inject
  private ITextureAtlas textureAtlas;

  // ----- import from Game ends
  /**
   * Creates a GLFW window and its OpenGL context with the specified width, height and title.
   *
   * @param title Title of the window
   */
  public Silverspark() {
    constructSilverspark(WINDOW_WIDTH, WINDOW_HEIGHT, "Window provided by Silverspark!!", true);
  }

  /**
   * Creates a GLFW window and its OpenGL context with the specified width, height and title.
   *
   * @param width Width of the drawing area
   * @param height Height of the drawing area
   * @param title Title of the window
   * @param vsync Set to true, if you want v-sync
   */
  public Silverspark(int width, int height, CharSequence title, boolean vsync) {
    constructSilverspark(width, height, title, vsync);
  }

  private void constructSilverspark(int width, int height, CharSequence title, boolean vsync) {
    this.vsync = vsync;

    errorCallback = GLFWErrorCallback.createPrint();
    GLFW.glfwSetErrorCallback(errorCallback);

    /* Initialise GLFW */
    if (!GLFW.glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW!");
    }
    GLCapabilities caps = createTempWindow();
    setWindowHints(caps);
    id = createActiveWindow(width, height, title);
    centerWindow(width, height);
    createOpenGlContext();
    enableVsync(vsync);
    keyCallback = createKeyCallback();

    // glfwSetInputMode(id, GLFW_STICKY_MOUSE_BUTTONS, GLFW_TRUE);

  }

  private void enableVsync(boolean vsync) {
    /* Enable v-sync */
    if (vsync) {
      glfwSwapInterval(1);
    }
  }

  private void createOpenGlContext() {
    /* Create OpenGL context */
    glfwMakeContextCurrent(id);
    GL.createCapabilities();
  }

  private void centerWindow(int width, int height) {
    /* Center window on screen */
    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    glfwSetWindowPos(id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
  }

  private long createActiveWindow(int width, int height, CharSequence title) {
    /* Create window with specified OpenGL context */
    long id = glfwCreateWindow(width, height, title, NULL, NULL);
    if (id == NULL) {
      glfwTerminate();
      throw new RuntimeException("Failed to create the GLFW window!");
    }
    return id;
  }

  private void setWindowHints(GLCapabilities caps) {
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
      throw new RuntimeException("Neither OpenGL 3.2 nor OpenGL 2.1 is " + "supported, you may want to update your graphics driver.");
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
  }

  private GLCapabilities createTempWindow() {
    /* Creating a temporary window for getting the available OpenGL version */
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
    glfwMakeContextCurrent(temp);
    GL.createCapabilities();
    GLCapabilities caps = GL.getCapabilities();
    glfwDestroyWindow(temp);
    return caps;
  }

  private GLFWKeyCallback createKeyCallback() {
    /* Set key callback */
    GLFWKeyCallback callback = new GLFWKeyCallback() {
      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
          glfwSetWindowShouldClose(window, true);
        }
      }
    };
    glfwSetKeyCallback(id, keyCallback);
    return callback;
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

  // Imports from Game start here

  /**
   * This should be called to initialise and start the game.
   */
  public void start() {
    init();
    renderLoop();
    dispose();
  }

  /**
   * Releases resources that where used by the game.
   */
  public void dispose() {

    /* Dispose renderer */
    sparkRenderer.dispose();

    /* Dispose renderer */
    textRenderer.dispose();

    /* Set empty state to trigger the exit method in the current state */
    stateMachine.change(null);

    /* Release window and its call-backs */
    this.destroy();

    /* Terminate GLFW and release the error callback */
    GLFW.glfwTerminate();
    errorCallback.free();
  }

  /**
   * Initialises the game window.
   */
  public void init() {

    mouseEventMenuHandler.setWindowId(getId());
    mouseEventMenuHandler.init();

    /* Initialise timer */
    timer.init();

    /* Initialise text renderer */
    sparkRenderer.init();

    /* Initialising done, set running to true */
    running = true;
  }

  /**
   * The rendering loop.
   */

  public void renderLoop() {
    float delta;

    while (running) {
      /* Check if window should close */
      if (this.isClosing()) {
        running = false;
      }

      /* Get delta time */
      delta = timer.getDelta();

      /* Handle input */
      input();

      /* Update window and timer UPS */
      update(delta);
      timer.updateUPS();

      /* Render window and update timer FPS */
      render();
      timer.updateFPS();

      /* Update timer */
      timer.update();

      /* Update window to show the new screen */
      this.update();

      /* Synchronize if v-sync is disabled */
      if (!this.isVSyncEnabled()) {
        sync(TARGET_FPS);
      }
    }
  }

  /**
   * Handles input.
   */
  public void input() {
    stateMachine.input();
  }

  /**
   * Updates the game (variable time step).
   *
   * @param delta Time difference in seconds
   */
  public void update(float delta) {
    stateMachine.update(delta);
  }

  /**
   * Renders the window (no interpolation).
   */
  public void render() {

    // TODO - figure out which need to be floats/ints. Also, renderables will need a velocity vector
    Spark texture = textureAtlas.getMainSpark();
    for (IRenderable renderable : renderState.getRenderables()) {

      float x = renderable.getX();
      float y = renderable.getY();
      CatalogueEntry entry = textureAtlas.getCatalogue().getEntry(renderable.getKeyName());
      float regX = entry.getCoords()[0];
      float regY = entry.getCoords()[1];
      float regWidth = entry.getWidth();
      float regHeight = entry.getHeight();

      sparkRenderer.drawTextureRegion(texture, x, y, regX, regY, regWidth, regHeight);
    } ;

    for (IRenderableText renderableText : renderState.getRenderableTexts()) {
      // render the text
    } ;

  }

  /**
   * Renders the game (with interpolation).
   *
   * @param alpha Alpha value, needed for interpolation
   */
  public void render(float alpha) {
    stateMachine.render(alpha);
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
        Logger.getLogger(Silverspark.class.getName()).log(Level.SEVERE, null, ex);
      }

      now = timer.getTime();
    }
  }

  /**
   * @return the mouseHandler
   */
  public MouseEventMenuHandler getMouseHandler() {
    return mouseEventMenuHandler;
  }

  /**
   * @param mouseHandler the mouseHandler to set
   */
  public void setMouseHandler(MouseEventMenuHandler mouseHandler) {
    this.mouseEventMenuHandler = mouseHandler;
  }

  public ITextureAtlas getTextureAtlas() {
    return textureAtlas;
  }

  public void setTextureAtlas(SparkAtlas textureAtlas) {
    this.textureAtlas = textureAtlas;
  }


  public IRenderState getRenderState() {
    return renderState;
  }

  public void setRenderState(IRenderState renderState) {
    this.renderState = renderState;
  }



}
