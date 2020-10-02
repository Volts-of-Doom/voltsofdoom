package genelectrovise.voltsofdoom;

import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;

public class LoopTest {

  /**
   * Main function.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Game game = new VariableTimestepGame();
    game.start();
  }
}
