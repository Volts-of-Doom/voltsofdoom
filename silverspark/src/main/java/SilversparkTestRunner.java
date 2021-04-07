import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;

public class SilversparkTestRunner implements Runnable {

  public static final int WINDOW_WIDTH = 640;
  public static final int WINDOW_HEIGHT = 380;

  public SilversparkTestRunner() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    SilversparkTestRunner runner = new SilversparkTestRunner();
    runner.run();
  }

  @Override
  public void run() {
    System.out.println("Running Silverspark manual test application");
    Silverspark spark = new Silverspark(WINDOW_WIDTH, WINDOW_HEIGHT, "Silverspark Test Window", true);
    spark.start();
  }

}
