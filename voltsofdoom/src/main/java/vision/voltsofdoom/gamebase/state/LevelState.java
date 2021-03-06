/*
 * The MIT License (MIT)
 *
 * Copyright © 2015-2016, Heiko Brumme
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
package vision.voltsofdoom.gamebase.state;

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
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.graphic.MouseEventMenuHandler;
import vision.voltsofdoom.silverspark.graphic.SparkTexture;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.math.Vector2f;
import vision.voltsofdoom.gamebase.display.DisplayText;
import vision.voltsofdoom.gamebase.game.GreenBlob;
import vision.voltsofdoom.silverspark.render.ListRenderer;
import vision.voltsofdoom.silverspark.render.Renderer;
import vision.voltsofdoom.silverspark.render.TextRenderer;
import vision.voltsofdoom.silverspark.text.FontState;

/**
 * This class contains a simple game.
 *
 * @author Heiko Brumme
 */
public class LevelState implements State {

  private final MouseEventMenuHandler mouseEventHandler;

  private SparkTexture backgroundTexture;
  private SparkTexture entitiesTexture;
  private final ListRenderer listRenderer;
  private final TextRenderer textRenderer;

  private GreenBlob greenBlob1;
  private GreenBlob greenBlob2;
  private List<IRenderable> entitiesList = new ArrayList<>();
  private Map<String, FontState> availableFonts;

  private String[] permittedFonts =
      {"Inconsolata:16:WHITE", "Inconsolata:20:WHITE", "Inconsolata:50:WHITE"};

  private int playerScore;
  private int opponentScore;
  private int gameWidth;
  private int gameHeight;
  private long windowId;
  private final String resourceRoot;

  public LevelState(long windowId, MouseEventMenuHandler mouseHandler, ListRenderer entityRenderer,
      TextRenderer textRenderer, String resourceRoot) {
    this.resourceRoot = resourceRoot;
    this.windowId = windowId;
    this.mouseEventHandler = mouseHandler;
    this.listRenderer = entityRenderer;
    this.textRenderer = textRenderer;
    availableFonts = loadFonts();
  }

  private Map<String, FontState> loadFonts() {

    availableFonts = new HashMap<>();

    FontState defaultFont = new FontState();
    availableFonts.put(defaultFont.getKey(), defaultFont);

    for (String key : permittedFonts) {
      try {
        String[] bits = key.split(":");
        FileInputStream fis = new FileInputStream(resourceRoot + bits[0] + ".ttf");
        int size = Integer.parseInt(bits[1]);
        // todo - should get the colour dynamically,from key
        FontState thisFS = new FontState(key, fis, size, VODColor.WHITE, true);
        availableFonts.put(key, thisFS);
      } catch (IOException | FontFormatException e) {
        Logger.getLogger(Renderer.class.getName()).log(Level.CONFIG,
            "Font " + key + " not created - will use default", e);
      }
    }
    return availableFonts;

  }


  @Override
  public String input() {
    return null;
  }

  @Override
  public void update(float delta) {

  }

  @Override
  public void render(float alpha) {
    /* Clear drawing area */
    listRenderer.clear();

    drawBackground();

    listRenderer.drawContents(entitiesList, entitiesTexture);

    drawText();

    /* Draw FPS, UPS and Context version */
    int height = textRenderer.getDebugTextHeight(availableFonts.get("Default"), "Context");
    textRenderer.drawDebugText(availableFonts.get("Default"), "FPS: XXX" + " | UPS: XXX", 5,
        5 + height);
    textRenderer.drawDebugText(availableFonts.get("Default"),
        "Context: " + (Silverspark.isDefaultContext() ? "3.2 core" : "2.1"), 5, 5);

  }

  private void drawText() {
    /* Draw score */
    String scoreText = "Score";
    int scoreTextWidth =
        textRenderer.getTextWidth(availableFonts.get("Inconsolata:50:WHITE"), scoreText);
    int scoreTextHeight =
        textRenderer.getTextHeight(availableFonts.get("Inconsolata:50:WHITE"), scoreText);
    float scoreTextX = (gameWidth - scoreTextWidth) / 2f;
    float scoreTextY = gameHeight - scoreTextHeight - 5;
    DisplayText text = new DisplayText(availableFonts.get("Inconsolata:50:WHITE"), scoreText,
        new Vector2f(scoreTextX, scoreTextY), VODColor.WHITE);

    textRenderer.drawText(text);

    String playerText = "Player | " + playerScore;
    int playerTextWidth = textRenderer.getTextWidth(availableFonts.get("Default"), playerText);
    int playerTextHeight = textRenderer.getTextHeight(availableFonts.get("Default"), playerText);
    float playerTextX = gameWidth / 2f - playerTextWidth - 50;
    float playerTextY = scoreTextY - playerTextHeight;
    text = new DisplayText(availableFonts.get("Default"), playerText,
        new Vector2f(playerTextX, playerTextY), VODColor.WHITE);

    textRenderer.drawText(text);

    String opponentText = opponentScore + " | Opponent";
    // int opponentTextWidth =
    // textRenderer.getDebugTextWidth(availableFonts.get("Default"),playerText);
    int opponentTextHeight = textRenderer.getTextHeight(availableFonts.get("Default"), playerText);
    float opponentTextX = gameWidth / 2f + 50;
    float opponentTextY = scoreTextY - opponentTextHeight;
    text = new DisplayText(availableFonts.get("Default"), opponentText,
        new Vector2f(opponentTextX, opponentTextY), VODColor.WHITE);

    textRenderer.drawText(text);
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

    try {
      /* Load backgroundTexture */
      backgroundTexture =
          SparkTexture.loadTexture(resourceRoot + "cobbleandwoodlog_stitchedLevel.png");
      entitiesTexture = SparkTexture.loadTexture(resourceRoot + "greenblob.png");
    } catch (IOException e) {
      // todo - do something
    }

    /* Initialize game objects */
    // float speed = 250f;
    greenBlob1 = new GreenBlob(null, entitiesTexture, 100, 100, 0, 50, 50, 0, 00);
    greenBlob2 = new GreenBlob(null, entitiesTexture, 200, 200, 0, 50, 50, 0, 00);
    entitiesList.add(greenBlob1);
    entitiesList.add(greenBlob2);
    /* Initialize variables */
    playerScore = 0;
    opponentScore = 0;
    gameWidth = width;
    gameHeight = height;

  }

  @Override
  public void exit() {

    backgroundTexture.delete();
    entitiesTexture.delete();
    availableFonts = null;
  }

}
