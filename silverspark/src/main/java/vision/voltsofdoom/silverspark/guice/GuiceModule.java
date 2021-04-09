package vision.voltsofdoom.silverspark.guice;

import com.google.inject.AbstractModule;
import vision.voltsofdoom.silverspark.api.IRenderState;
import vision.voltsofdoom.silverspark.api.ITextureAtlas;
import vision.voltsofdoom.silverspark.state.RenderState;
import vision.voltsofdoom.silverspark.core.ITimer;
import vision.voltsofdoom.silverspark.core.Timer;
import vision.voltsofdoom.silverspark.graphic.SparkAtlas;

public class GuiceModule extends AbstractModule {

  public GuiceModule() {
  }

  @Override
  protected void configure() {

    bind(ITimer.class).to(Timer.class);
    bind(IRenderState.class).to(RenderState.class);
    bind(ITextureAtlas.class).to(SparkAtlas.class); // SparkAtlas is default implementation of ITextureAtlas
  }

}
