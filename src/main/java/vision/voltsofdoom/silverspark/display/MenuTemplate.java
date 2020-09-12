package vision.voltsofdoom.silverspark.display;

import vision.voltsofdoom.silverspark.controls.MenuButton;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.math.Vector2f;

import java.util.List;

public class MenuTemplate {

    private final Vector2f menuXY;
    private final Vector2f buttonXY;
    private final Vector2f textXY;
    private final double lineHeight;
    private final String fontKey;
    private final VODColor color;

    public MenuTemplate(Vector2f menuXY, Vector2f buttonXY, Vector2f textXY, double lineHeight, String fontKey, VODColor color) {
        this.menuXY = menuXY;
        this.buttonXY = buttonXY;
        this.textXY = textXY;
        this.lineHeight = lineHeight;
        this.fontKey = fontKey;
        this.color = color;
    }

    public void filterButton(IRenderable item, int i) {
        if (item instanceof MenuButton) { // todo need setPosition in the interface?
            float x = menuXY.x + buttonXY.x;
            float y = (float) (Game.WINDOW_HEIGHT - (menuXY.y + i*lineHeight));
            ((MenuButton)item).setPosition(new Vector2f(x, y));
        }
    }


    public List<IRenderableText> filterText(IRenderable item, int i, List<IRenderableText> renderList) {
        if (item instanceof MenuButton) { // todo need setPosition in the interface?
            //MenuButton btn = (MenuButton)item;
            float x = menuXY.x + textXY.x;
            float y =  (float) (Game.WINDOW_HEIGHT - (menuXY.y + i*lineHeight));
            Vector2f textPosn = new Vector2f(x,y);
            Label l = ((MenuButton)item).getLabel();

            DisplayText dt = new DisplayText(l.getFont(), l.getMainText(), textPosn, color);
            renderList.add(dt);

        }
        return renderList;
    }

    public String getFontKey() {
        return fontKey;
    }
}
