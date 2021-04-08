package vision.voltsofdoom.silverspark.state;

import java.util.ArrayList;
import java.util.List;
import vision.voltsofdoom.silverspark.api.IRenderState;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.api.IRenderableText;

public class RenderState implements IRenderState {

  private List<IRenderable> renderables;
  private List<IRenderableText> renderableTexts;

  
  public RenderState()  {
    renderables = new ArrayList<IRenderable>();
    renderableTexts = new ArrayList<IRenderableText>();
    
  }

  @Override
  public List<IRenderable> getRenderables() {
    return renderables;
  }

  @Override
  public List<IRenderableText> getRenderableTexts() {
    return renderableTexts;
  }

  @Override
  public void setRenderables(List<IRenderable> renderables) {
    this.renderables = renderables;
   }

  @Override
  public void setRenderableTexts(List<IRenderableText> renderableTexts) {
    this.renderableTexts = renderableTexts;
  }

}
