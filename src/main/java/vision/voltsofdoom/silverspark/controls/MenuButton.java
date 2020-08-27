package vision.voltsofdoom.silverspark.controls;

import org.lwjgl.glfw.GLFW;
import vision.voltsofdoom.silverspark.display.BoundingBox;
import vision.voltsofdoom.silverspark.display.ICollidable;
import vision.voltsofdoom.silverspark.display.IRenderable;
import vision.voltsofdoom.silverspark.display.Label;
import vision.voltsofdoom.silverspark.graphic.MouseEventMenuHandler;
import vision.voltsofdoom.silverspark.graphic.Texture;
import vision.voltsofdoom.silverspark.math.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MenuButton implements ICollidable, IRenderable {
    protected Vector2f position;

    protected final BoundingBox boundingBox;

    protected final Texture texture;

    protected final int width;
    protected final int height;


    protected final int textureX; // X and Y co-ordinates of entity image on texture
    protected final int textureY;

    private final Label label;
    private final MouseEventMenuHandler handler;

    public MenuButton(Label label, Texture texture, float x, float y, float speed, int width, int height, int textureX, int textureY,
                      MouseEventMenuHandler handler) {

        this.label = label;
        position = new Vector2f(x, y);

        this.texture = texture;

        this.width = width;
        this.height = height;

        this.textureX = textureX;
        this.textureY = textureY;
        this.handler = handler;

        boundingBox = new BoundingBox(this);
        System.out.println("Creating button:" + label.getKey());
        System.out.println("Bounding box min.x=" + boundingBox.min.x + ", min.y = "  + boundingBox.min.y );
        System.out.println("Bounding box max.x=" + boundingBox.max.x + ", max.y = "  + boundingBox.max.y );
        System.out.println("Width in = " + width + ", height in = "  + height);

    }

    /**
     * Handles input of the entity.
     */
    public String input() {
        long window = GLFW.glfwGetCurrentContext();
        int state = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT);
        if (state == GLFW_PRESS )
        {
            Vector2f mousePosn = handler.getMousePositon();
            if (isContainsPoint(mousePosn)) {
                System.out.println("Mouse click on menu button " + label.getKey());
                System.out.println("... so returning: " + label.getKey());
                return label.getKey();
            }
        }
        //System.out.println("No input so returning null");
        return null;
    }

    /**
     * Updates the button.
     *
     * @param delta Time difference in seconds
     */
    public void update(float delta) {

        boundingBox.min.x = position.x;
        boundingBox.min.y = position.y;
        boundingBox.max.x = position.x + width;
        boundingBox.max.y = position.y + height;
    }

    public boolean isContainsPoint(Vector2f posn) {// todo make posn a vector2f
        System.out.println("Bounds: x " + boundingBox.min.x + ", to " + boundingBox.max.x + ", y " + boundingBox.min.y + ", to " + boundingBox.max.y);
        return ((posn.x >= boundingBox.min.x && posn.x <= boundingBox.max.x) && (posn.y >= boundingBox.min.y && posn.y <= boundingBox.max.y));
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public int getTextureX() {
        return textureX;
    }

    public int getTextureY() {
        return textureY;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

}
