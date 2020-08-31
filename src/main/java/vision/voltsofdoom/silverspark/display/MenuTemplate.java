package vision.voltsofdoom.silverspark.display;

import vision.voltsofdoom.silverspark.controls.MenuButton;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.math.Vector2f;

public class MenuTemplate {

    private final Vector2f menuXY;
    private final Vector2f buttonXY;
    private final Vector2f textXY;
    private final double lineHeight;
    private final String fontKey;

    public MenuTemplate(Vector2f menuXY, Vector2f buttonXY, Vector2f textXY, double lineHeight, String fontKey) {
        this.menuXY = menuXY;
        this.buttonXY = buttonXY;
        this.textXY = textXY;
        this.lineHeight = lineHeight;
        this.fontKey = fontKey;
    }

    public void filterButton(IRenderable item, int i) {
        if (item instanceof MenuButton) { // todo need setPosition in the interface?
            float x = menuXY.x + buttonXY.x;
            float y = (float) (Game.WINDOW_HEIGHT - (menuXY.y + i*lineHeight)); // todo - but it has to start at the top and work down :(
            ((MenuButton)item).setPosition(new Vector2f(x, y));
        }
    }


    public void filterText(IRenderable item, int i) {
        if (item instanceof MenuButton) { // todo need setPosition in the interface?
            float x = menuXY.x + textXY.x;
            float y = (float) (Game.WINDOW_HEIGHT - (menuXY.y + i*lineHeight)); // todo - but it has to start at the top and work down :(
            ((MenuButton)item).setPosition(new Vector2f(x, y));
        }
    }

    public String getFontKey() {
        return fontKey;
    }
}
