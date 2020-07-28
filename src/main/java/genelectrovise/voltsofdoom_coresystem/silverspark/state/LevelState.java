/*
 * The MIT License (MIT)
 *
 * Copyright © 2015-2016, Heiko Brumme
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
package genelectrovise.voltsofdoom_coresystem.silverspark.state;

import genelectrovise.voltsofdoom_coresystem.silverspark.core.Game;
import genelectrovise.voltsofdoom_coresystem.silverspark.game.GreenBlob;
import genelectrovise.voltsofdoom_coresystem.silverspark.game.RenderableEntity;
import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.EntityRenderer;
import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.Renderer;
import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.TextRenderer;
import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.Texture;
import genelectrovise.voltsofdoom_coresystem.silverspark.graphic.VODColor;
import genelectrovise.voltsofdoom_coresystem.silverspark.text.FontState;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains a simple game.
 *
 * @author Heiko Brumme
 */
public class LevelState implements State {

    private Texture backgroundTexture;
    private Texture entitiesTexture;
    private final EntityRenderer entityRenderer;
    private final TextRenderer textRenderer;

    private GreenBlob greenBlob1;
    private GreenBlob greenBlob2;
    private List<RenderableEntity> entitiesList = new ArrayList<>();
    private Map<String, FontState> availableFonts;

    private String[] permittedFonts = {"Inconsolata:16:WHITE", "Inconsolata:20:WHITE", "Inconsolata:50:WHITE"};

    private int playerScore;
    private int opponentScore;
    private int gameWidth;
    private int gameHeight;

    public LevelState(EntityRenderer entityRenderer, TextRenderer textRenderer) {
        this.entityRenderer = entityRenderer;
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
                FileInputStream fis = new FileInputStream("resources/" + bits[0] + ".ttf");
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
    public void input() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float alpha) {
        /* Clear drawing area */
        entityRenderer.clear();

        drawBackground();

        entityRenderer.drawEntities(entitiesList, entitiesTexture);

        drawText();

        /* Draw FPS, UPS and Context version */
        int height = textRenderer.getDebugTextHeight(availableFonts.get("Default"),"Context");
        textRenderer.drawDebugText(availableFonts.get("Default"), "FPS: XXX" + " | UPS: XXX", 5, 5 + height);
        textRenderer.drawDebugText(availableFonts.get("Default"), "Context: " + (Game.isDefaultContext() ? "3.2 core" : "2.1"), 5, 5);


    }

    private void drawText() {
        /* Draw score */
        String scoreText = "Score";
        int scoreTextWidth = textRenderer.getTextWidth(availableFonts.get("Inconsolata:50:WHITE"), scoreText);
        int scoreTextHeight = textRenderer.getTextHeight(availableFonts.get("Inconsolata:50:WHITE"),scoreText);
        float scoreTextX = (gameWidth - scoreTextWidth) / 2f;
        float scoreTextY = gameHeight - scoreTextHeight - 5;
        textRenderer.drawText(availableFonts.get("Inconsolata:50:WHITE"), scoreText, scoreTextX, scoreTextY, VODColor.WHITE);

        String playerText = "Player | " + playerScore;
        int playerTextWidth = textRenderer.getTextWidth(availableFonts.get("Default"), playerText);
        int playerTextHeight = textRenderer.getTextHeight(availableFonts.get("Default"),playerText);
        float playerTextX = gameWidth / 2f - playerTextWidth - 50;
        float playerTextY = scoreTextY - playerTextHeight;
        textRenderer.drawText(availableFonts.get("Default"), playerText, playerTextX, playerTextY, VODColor.WHITE);

        String opponentText = opponentScore + " | Opponent";
        int opponentTextWidth = textRenderer.getDebugTextWidth(availableFonts.get("Default"),playerText);
        int opponentTextHeight = textRenderer.getTextHeight(availableFonts.get("Default"), playerText);
        float opponentTextX = gameWidth / 2f + 50;
        float opponentTextY = scoreTextY - opponentTextHeight;
        textRenderer.drawText(availableFonts.get("Default"), opponentText, opponentTextX, opponentTextY, VODColor.WHITE);
    }


    private void drawBackground() {
        /* Draw background */
        backgroundTexture.bind();
        entityRenderer.begin();

        entityRenderer.drawTextureRegion(backgroundTexture, 0, 0, 0, 0, gameWidth, gameHeight);

        entityRenderer.end();
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
        backgroundTexture = Texture.loadTexture("resources/cobbleandwoodlog_stitchedLevel.png");
        entitiesTexture = Texture.loadTexture("resources/greenblob.png");

        /* Initialize game objects */
        float speed = 250f;
        greenBlob1 = new GreenBlob(null, entitiesTexture, 100, 100, 0, 50, 50, 0, 00);
        greenBlob2 = new GreenBlob(null, entitiesTexture, 200, 200, 0, 50, 50, 0, 00);
        entitiesList.add(greenBlob1);
        entitiesList.add((greenBlob2));

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
