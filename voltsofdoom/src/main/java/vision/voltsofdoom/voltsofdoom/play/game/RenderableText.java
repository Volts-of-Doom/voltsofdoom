package vision.voltsofdoom.voltsofdoom.play.game;

import vision.voltsofdoom.silverspark.api.IRenderableText;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.math.Vector2f;
import vision.voltsofdoom.silverspark.text.FontState;

public class RenderableText implements IRenderableText {

  private FontState displayFont;
  private CharSequence text;
  private Vector2f posn;
  private VODColor color;

  public RenderableText(FontState displayFont, CharSequence text, Vector2f posn, VODColor color) {
    this.displayFont = displayFont;
    this.text = text;
    this.posn = posn;
    this.color = color;
  }

  @Override
  public FontState getDisplayFont() {
    return displayFont;
  }

  @Override
  public CharSequence getText() {
    return text;
  }

  @Override
  public Vector2f getPosn() {
    return posn;
  }

  @Override
  public VODColor getColor() {
    return color;
  }

}
