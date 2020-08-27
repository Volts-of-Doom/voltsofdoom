package vision.voltsofdoom.silverspark.render;

import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.render.Renderer;
import vision.voltsofdoom.silverspark.text.FontState;

public class TextRenderer extends Renderer {


    /** Initializes the renderer. */
    public void init() {

        super.init();

    }


    /**
     * Calculates total width of a text.
     *
     * @param text The text
     *
     * @return Total width of the text
     */
    public int getTextWidth(FontState fs, CharSequence text) {
        return fs.getWidth(text);
    }

    /**
     * Calculates total height of a text.
     *
     * @param text The text
     *
     * @return Total width of the text
     */
    public int getTextHeight(FontState fs, CharSequence text) {
        return fs.getHeight(text);
    }

    /**
     * Calculates total width of a debug text.
     *
     * @param text The text
     *
     * @return Total width of the text
     */
    public int getDebugTextWidth(FontState fs, CharSequence text) {
        return fs.getWidth(text);
    }

    /**
     * Calculates total height of a debug text.
     *
     * @param text The text
     *
     * @return Total width of the text
     */
    public int getDebugTextHeight(FontState fs, CharSequence text) {
        return fs.getHeight(text);
    }

    /**
     * Draw debug text at the specified position.
     *
     * @param text Text to draw
     * @param x    X coordinate of the text position
     * @param y    Y coordinate of the text position
     */
    public void drawDebugText(FontState fs, CharSequence text, float x, float y) {
        fs.drawText(this, text, x, y);
    }

    /**
     * Draw text at the specified position and color.
     *
     * @param text Text to draw
     * @param x    X coordinate of the text position
     * @param y    Y coordinate of the text position
     * @param c    Color to use
     */
    public void drawText(FontState fs, CharSequence text, float x, float y, VODColor c) {
        fs.drawText(this, text, x, y, c);
    }

}
