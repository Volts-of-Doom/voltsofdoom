package vision.voltsofdoom.silverspark.graphic;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import vision.voltsofdoom.silverspark.math.Vector2f;

public class MouseEventMenuHandler {

  private final GLFWMouseButtonCallback mouseCallback;
  private final GLFWCursorPosCallback posCallback;
  private double mouseXPos;
  private double mouseYPos;

  private Vector2f windowSize;


  public MouseEventMenuHandler(long windowId) {

    System.out.println("Constructing MouseHandler");

    glfwSetMouseButtonCallback(windowId, mouseCallback = new GLFWMouseButtonCallback() {
      @Override
      public void invoke(long window, int button, int action, int mods) {
        if (button == GLFW_MOUSE_BUTTON_1) {
          if (action == GLFW_PRESS) {
            System.out.println("Mouse pressed!");
          }
          if (action == GLFW_RELEASE) {
            System.out.println("Mouse released!");
          }
        }

      }
    });

    glfwSetCursorPosCallback(windowId, posCallback = new GLFWCursorPosCallback() {
      @Override
      public void invoke(long window, double xpos, double ypos) {
        mouseXPos = xpos;
        mouseYPos = ypos;
        System.out.println("Cursor X = " + mouseXPos);
        System.out.println("Cursor Y = " + mouseYPos);

      }
    });

  }

  public double getMouseXPos() {
    return mouseXPos;
  }

  public double getMouseYPos() {
    return mouseYPos;
  }

  public Vector2f getMousePositon() {
    System.out.println("Getting position");
    // todo - this is a horrible hack - rework if possible
    Vector2f posn = new Vector2f(new Float(mouseXPos), new Float(windowSize.y - mouseYPos));
    System.out.println("X = " + posn.x + ", Y = " + posn.y);
    return posn;
  }

  public void setWindowSize(Vector2f windowSize) {
    this.windowSize = windowSize;
  }



}
