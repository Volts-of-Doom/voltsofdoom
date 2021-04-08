import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.guice.GuiceModule;

public class SilversparkTestRunner implements Runnable {

  public static final int WINDOW_WIDTH = 640;
  public static final int WINDOW_HEIGHT = 380;
  
  @Inject
  Silverspark silverspark;

  public SilversparkTestRunner() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new GuiceModule());
    SilversparkTestRunner runner = injector.getInstance(SilversparkTestRunner.class);
    runner.run();
  }

  @Override
  public void run() {
    System.out.println("Running Silverspark manual test application");
    //silverspark.setTitle("Silverspark Test Window");
    silverspark.start();
  }

}
