package vision.voltsofdoom.silverspark.graphic;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import vision.voltsofdoom.silverspark.math.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseEventMenuHandler {

    private final GLFWMouseButtonCallback mouseCallback;
    private final GLFWCursorPosCallback posCallback;
    private double mouseXPos;
    private double mouseYPos;

    private Vector2f windowSize;


    public MouseEventMenuHandler(long windowId) {
        glfwSetMouseButtonCallback(windowId, mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_1 ) {
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
                System.out.println("Cursor X = " + xpos);
                System.out.println("Cursor Y = " + ypos);
                mouseXPos = xpos;
                mouseYPos = ypos;
            }
        });

    }

    public double getMouseXPos() {
        return mouseXPos;
    }

    public double getMouseYPos() {
        return mouseYPos;
    }

    public double[] getMousePositon() {
        System.out.println("Getting position");
        double[] posn = new double[2];
        posn[0] = mouseXPos;
        System.out.println("windowSize.y= " + windowSize.y);
    // todo - this is a horrible hack - rework if possible
        posn[1] = windowSize.y - mouseYPos;
        System.out.println("Mouse X =" + posn[0]);
        System.out.println("Mouse Y =" + posn[1]);
        return posn;
    }

    public void setWindowSize(Vector2f windowSize) {
        this.windowSize = windowSize;
    }





}
