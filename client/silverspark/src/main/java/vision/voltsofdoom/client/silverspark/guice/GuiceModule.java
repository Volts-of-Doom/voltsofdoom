package vision.voltsofdoom.client.silverspark.guice;

import com.google.inject.AbstractModule;
import vision.voltsofdoom.client.silverspark.api.IRenderState;
import vision.voltsofdoom.client.silverspark.api.ITextureAtlas;
import vision.voltsofdoom.client.silverspark.core.ITimer;
import vision.voltsofdoom.client.silverspark.core.Timer;
import vision.voltsofdoom.client.silverspark.graphic.SparkAtlas;
import vision.voltsofdoom.client.silverspark.state.RenderState;

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
