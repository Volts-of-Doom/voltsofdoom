package vision.voltsofdoom.silverspark.api;

import java.util.List;

public interface IRenderState {
  List<IRenderable> getRenderables();
  List<IRenderableText> getRenderableTexts();
  void setRenderables(List<IRenderable> renderables);
  void setRenderableTexts(List<IRenderableText> renderableTexts);
}
