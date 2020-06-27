package vision.voltsofdoom.coresystem.universal.main;

public class VoltsOfDoomCoreSystem extends Thread {
	public static final GameController GAME_CONTROLLER = new GameController();

	public static void main(String[] args) {
		new VoltsOfDoomCoreSystem().start();
	}
	
	public void start() {
		initialiseGame();
	}

	private void initialiseGame() {
		try {
			GAME_CONTROLLER.initialiseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
