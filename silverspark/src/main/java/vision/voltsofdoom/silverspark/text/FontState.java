/*
 * The MIT License (MIT)
 *
 * Copyright © 2015-2017, Heiko Brumme
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
package vision.voltsofdoom.silverspark.text;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.awt.Font.TRUETYPE_FONT;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.system.MemoryUtil;
import vision.voltsofdoom.silverspark.graphic.Texture;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.render.Renderer;

/**
 * This class contains a font texture for drawing text.
 *
 * @author Heiko Brumme
 */
public class FontState {

    /**
     * Contains the glyphs for each char.
     */
    private final Map<Character, Glyph> glyphs = new HashMap<>();
    /**
     * Contains the font texture.
     */
    private final Texture texture;

    public String getKey() {
        return key;
    }

    private final String key;

    /**
     * Height of the font.
     */
    private int fontHeight;

    /**
     * Colour of the font.
     */
    private VODColor fontColor;

    /**
     * Creates a default antialiased font with monospaced glyphs and default
     * size 16.
     */
    public FontState() {
        key = "Default";
        Font font = new Font(MONOSPACED, PLAIN, 16);
        texture = createFontTexture(font, true);
        this.fontColor = VODColor.BLACK;
    }


    /**
     * Creates a Font from an input stream.
     *
     * @param in        The input stream
     * @param size      Font size
     * @param antiAlias Wheter the font should be antialiased or not
     *
     * @throws FontFormatException if fontFile does not contain the required
     *                             font tables for the specified format
     * @throws IOException         If font can't be read
     */
    public FontState(String key, InputStream in, int size, VODColor fontColor, boolean antiAlias) throws FontFormatException, IOException {
        this.key = key;
        Font font = Font.createFont(TRUETYPE_FONT, in).deriveFont(PLAIN, size);
        texture = createFontTexture(font, antiAlias);
        this.fontColor = fontColor;
    }

    /**
     * Creates a font texture from specified AWT font.
     *
     * @param font      The AWT font
     * @param antiAlias Wheter the font should be antialiased or not
     *
     * @return font texture - a texture containing the concatenated images of all characters for the font
     */
    private Texture createFontTexture(Font font, boolean antiAlias) {
        /* Loop through the characters to get charWidth and charHeight */
        int imageWidth = 0;
        int imageHeight = 0;

        /* Start at char #32, because ASCII 0 to 31 are just control codes */
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                /* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;

        /* Image for the texture */
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        generateGlyphs(font, antiAlias, image);

        /* Flip image Horizontal to get the origin to bottom left */
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform,
                                                            AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        /* Get charWidth and charHeight of image */
        int width = image.getWidth();
        int height = image.getHeight();

        /* Get pixel data of image */
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        ByteBuffer buffer = loadByteBuffer(width, height, pixels);

        /* Create texture */
        Texture fontTexture = Texture.createTexture(width, height, buffer);
        MemoryUtil.memFree(buffer);
        return fontTexture;
    }

    private void generateGlyphs(Font font, boolean antiAlias, BufferedImage image) {
        int x = 0;
        Graphics2D g = image.createGraphics();
        /* Create image for the standard chars, again we omit ASCII 0 to 31
         * because they are just control codes */
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                /* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            /* Create glyph and draw char on image */
            Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0f);
            g.drawImage(charImage, x, 0, null);
            x += ch.width;
            glyphs.put(c, ch);
        }
    }

    private ByteBuffer loadByteBuffer(int width, int height, int[] pixels) {
        /* Put pixel data into a ByteBuffer */
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[i * width + j];
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        /* Do not forget to flip the buffer! */
        buffer.flip();
        return buffer;
    }

    /**
     * Creates a char image from specified AWT font and char.
     *
     * @param font      The AWT font
     * @param c         The char
     * @param antiAlias Wheter the char should be antialiased or not
     *
     * @return Char image
     */
    private BufferedImage createCharImage(Font font, char c, boolean antiAlias) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get char charWidth and charHeight */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        /* Check if charWidth is 0 */
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    /**
     * Gets the width of the specified text.
     *
     * @param text The text
     *
     * @return Width of text
     */
    public int getWidth(CharSequence text) {
        int width = 0;
        int lineWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                /* Line end, set width to maximum from line width and stored
                 * width */
                width = Math.max(width, lineWidth);
                lineWidth = 0;
                continue;
            }
            if (c == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(c);
            lineWidth += g.width;
        }
        width = Math.max(width, lineWidth);
        return width;
    }

    /**
     * Gets the height of the specified text.
     *
     * @param text The text
     *
     * @return Height of text
     */
    public int getHeight(CharSequence text) {
        int height = 0;
        int lineHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                /* Line end, add line height to stored height */
                height += lineHeight;
                lineHeight = 0;
                continue;
            }
            if (c == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(c);
            lineHeight = Math.max(lineHeight, g.height);
        }
        height += lineHeight;
        return height;
    }

    /**
     * Draw text at the specified position and color.
     *
     * @param renderer The renderer to use
     * @param text     Text to draw
     * @param x        X coordinate of the text position
     * @param y        Y coordinate of the text position
     * @param c        Color to use
     */
    public void drawText(Renderer renderer, CharSequence text, float x, float y, VODColor c) {
        int textHeight = getHeight(text);

        float drawX = x;
        float drawY = y;
        if (textHeight > fontHeight) {
            drawY += textHeight - fontHeight;
        }

        texture.bind();
        renderer.begin();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                /* Line feed, set x and y to draw at the next line */
                drawY -= fontHeight;
                drawX = x;
                continue;
            }
            if (ch == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(ch);
            renderer.drawTextureRegion(texture, drawX, drawY, g.x, g.y, g.width, g.height, c);
            drawX += g.width;
        }
        renderer.end();
    }

    /**
     * Draw text at the specified position.
     *
     * @param renderer The renderer to use
     * @param text     Text to draw
     * @param x        X coordinate of the text position
     * @param y        Y coordinate of the text position
     */
    public void drawText(Renderer renderer, CharSequence text, float x, float y) {
        drawText(renderer, text, x, y, VODColor.WHITE);
    }

    /**
     * Disposes the font.
     */
    public void dispose() {
        texture.delete();
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(int fontHeight) {
        this.fontHeight = fontHeight;
    }

    public VODColor getFontColor() {
        return fontColor;
    }

    public void setFontColor(VODColor fontColor) {
        this.fontColor = fontColor;
    }


}
