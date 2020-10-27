package vision.voltsofdoom.silverspark.api;

import vision.voltsofdoom.silverspark.graphic.VODColor;
import vision.voltsofdoom.silverspark.math.Vector2f;
import vision.voltsofdoom.silverspark.text.FontState;

public interface IRenderableText {
  FontState getDisplayFont();

  CharSequence getText();

  Vector2f getPosn();

  VODColor getColor();
}
