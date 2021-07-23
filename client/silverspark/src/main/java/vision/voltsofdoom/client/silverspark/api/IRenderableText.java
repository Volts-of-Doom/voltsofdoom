package vision.voltsofdoom.client.silverspark.api;

import vision.voltsofdoom.client.silverspark.graphic.VODColor;
import vision.voltsofdoom.client.silverspark.math.Vector2f;
import vision.voltsofdoom.client.silverspark.text.FontState;

public interface IRenderableText {
  FontState getDisplayFont();

  CharSequence getText();

  Vector2f getPosn();

  VODColor getColor();
}
