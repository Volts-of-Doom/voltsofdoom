package vision.voltsofdoom.voltsofdoom.play.display;

import vision.voltsofdoom.silverspark.api.IRenderableText;
import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.math.Vector2f;
import vision.voltsofdoom.silverspark.text.FontState;

public class DisplayText implements IRenderableText {

  private final FontState displayFont;
  private final CharSequence text;
  private final Vector2f posn;
  private VODColor color;


  public DisplayText(FontState fs, CharSequence text, Vector2f posn, VODColor c) {
    this.displayFont = fs;
    this.text = text;
    this.posn = posn;
    this.color = c;
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
