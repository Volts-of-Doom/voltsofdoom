package vision.voltsofdoom.silverspark.core;

public interface ITimer {

  double getLastLoopTime();

  int getUPS();

  int getFPS();

  void update();

  void updateUPS();

  void updateFPS();

  float getDelta();

  double getTime();

  void init();

}
