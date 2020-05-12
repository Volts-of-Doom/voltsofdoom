package genelectrovise.voltsofdoom_coresystem.main;

public class VODCoreSystemStart extends Thread {
	private static GameController gamecontroller = new GameController();

	public static void main(String[] args) {
		initialiseGame();
	}

	private static void initialiseGame() {
		try {
			gamecontroller.initialiseAll();
		} catch (Exception e) {

		}
	}
}
