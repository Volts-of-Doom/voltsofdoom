import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;

public class SilversparkTestRunner implements Runnable {



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
    Game game = new VariableTimestepGame("Silverspark Test Window");
    game.start();
  }

}
