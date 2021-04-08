package vision.voltsofdoom.silverspark.guice;

import com.google.inject.AbstractModule;
import vision.voltsofdoom.silverspark.api.IRenderState;
import vision.voltsofdoom.silverspark.state.RenderState;
import vision.voltsofdoom.silverspark.core.ITimer;
import vision.voltsofdoom.silverspark.core.Timer;

public class GuiceModule extends AbstractModule {

  public GuiceModule() {
    // TODO Auto-generated constructor stub
  }

  @Override
  protected void configure() {

    bind(ITimer.class).to(Timer.class);
    bind(IRenderState.class).to(RenderState.class);

  }

}
