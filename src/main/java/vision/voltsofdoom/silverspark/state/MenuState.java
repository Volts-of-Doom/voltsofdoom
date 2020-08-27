package vision.voltsofdoom.silverspark.state;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import vision.voltsofdoom.silverspark.controls.MenuButton;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.display.IRenderable;
import vision.voltsofdoom.silverspark.display.Label;
import vision.voltsofdoom.silverspark.graphic.MouseEventMenuHandler;
import vision.voltsofdoom.silverspark.graphic.Texture;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.math.Vector2f;
import vision.voltsofdoom.silverspark.render.ListRenderer;
import vision.voltsofdoom.silverspark.render.Renderer;
import vision.voltsofdoom.silverspark.render.TextRenderer;
import vision.voltsofdoom.silverspark.text.FontState;

import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuState implements State {

    private MouseEventMenuHandler mouseEventHandler;

    private Texture backgroundTexture;
    private Texture contentsTexture;
    private final ListRenderer listRenderer;
    private final TextRenderer textRenderer;

    private MenuButton button1;
    private MenuButton button2;
    private List<IRenderable> controlList = new ArrayList<>();
    private Map<String, FontState> availableFonts;

    private String[] permittedFonts = {"Inconsolata:16:WHITE", "Inconsolata:20:WHITE", "Inconsolata:50:WHITE"};

    private int gameWidth;
    private int gameHeight;
    private long windowId;

    public MenuState(long windowId, MouseEventMenuHandler mouseEventHandler, ListRenderer listRenderer, TextRenderer textRenderer) {
        this.windowId = windowId;
        this.mouseEventHandler = mouseEventHandler;
        this.listRenderer = listRenderer;
        this.textRenderer = textRenderer;
        availableFonts = loadFonts();
    }

    private Map<String, FontState> loadFonts() {

        availableFonts = new HashMap<>();

        FontState defaultFont = new FontState();
        availableFonts.put(defaultFont.getKey(), defaultFont);

        for (String key: permittedFonts) {
            try {
                String[] bits = key.split(":");
                FileInputStream fis = new FileInputStream("src/test/resources/" + bits[0] + ".ttf");
                int size = Integer.parseInt(bits[1]);
                // todo - should get the colour dynamically,from key
                FontState thisFS = new FontState(key, fis, size, VODColor.WHITE, true);
                availableFonts.put(key, thisFS);
            } catch (IOException | FontFormatException e) {
                Logger.getLogger(Renderer.class.getName()).log(Level.CONFIG, "Font " + key + " not created - will use default", e);
            }
        }
        return availableFonts;

    }


    @Override
    public String input() {
        String stateChange = null;
        for (IRenderable control : controlList) {
            stateChange = ((MenuButton)control).input();
            if (stateChange != null) {
                break;
            }
        }
        //System.out.println("Returning new state: " + stateChange);
        return stateChange;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float alpha) {
        /* Clear drawing area */
        listRenderer.clear();

        drawBackground();

        listRenderer.drawContents(controlList, contentsTexture);

        drawText();

        /* Draw FPS, UPS and Context version */
        int height = textRenderer.getDebugTextHeight(availableFonts.get("Default"),"Context");
        textRenderer.drawDebugText(availableFonts.get("Default"), "FPS: XXX" + " | UPS: XXX", 5, 5 + height);
        textRenderer.drawDebugText(availableFonts.get("Default"), "Context: " + (Game.isDefaultContext() ? "3.2 core" : "2.1"), 5, 5);


    }

    private void drawText() {
        /* Draw score */
        String scoreText = "Adventure Menu";
        int scoreTextWidth = textRenderer.getTextWidth(availableFonts.get("Inconsolata:50:WHITE"), scoreText);
        int scoreTextHeight = textRenderer.getTextHeight(availableFonts.get("Inconsolata:50:WHITE"),scoreText);
        float scoreTextX = (gameWidth - scoreTextWidth) / 2f;
        float scoreTextY = gameHeight - scoreTextHeight - 5;
        textRenderer.drawText(availableFonts.get("Inconsolata:50:WHITE"), scoreText, scoreTextX, scoreTextY, VODColor.WHITE);

    }


    private void drawBackground() {
        /* Draw background */
        backgroundTexture.bind();
        listRenderer.begin();

        listRenderer.drawTextureRegion(backgroundTexture, 0, 0, 0, 0, gameWidth, gameHeight);

        listRenderer.end();
    }

    @Override
    public void enter() {
        /* Get width and height of framebuffer */
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            long window = GLFW.glfwGetCurrentContext();
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
            width = widthBuffer.get();
            height = heightBuffer.get();
        }

        /* Load backgroundTexture */
        backgroundTexture = Texture.loadTexture("src/test/resources/cobbleandwoodlog_stitchedLevel.png");
        contentsTexture = Texture.loadTexture("src/test/resources/greenblob.png");

        /* Initialize game objects */
        Label label1 = new Label("Btn1", availableFonts.get(permittedFonts[1]), "Adventure No. 1");
        button1 = new MenuButton(label1, contentsTexture, 100, 200, 0, 50, 50, 0, 00, mouseEventHandler);
        Label label2 = new Label("Btn2", availableFonts.get(permittedFonts[1]), "Adventure No. 2");
        button2 = new MenuButton(label2, contentsTexture, 100, 100, 0, 50, 50, 0, 00, mouseEventHandler);

        controlList.add(button1);
        controlList.add(button2);
        /* Initialize variables */
        gameWidth = width;
        gameHeight = height;
        Vector2f windowSize = new Vector2f(width, height);
        mouseEventHandler.setWindowSize(windowSize);

    }

    @Override
    public void exit() {

        backgroundTexture.delete();
        contentsTexture.delete();
        availableFonts = null;
    }

}

