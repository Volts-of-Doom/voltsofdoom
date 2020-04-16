package genelectrovise.voltsofdoom_coresystem.main;

public class VODCoreSystemStart {

	private static SystemControl System_Control;

	public static void main(String[] args) {
		initialiseGame();
	}

	private static void initialiseGame() {
		try {
			System_Control = new SystemControl();
		} catch (Exception e) {

		}
	}

	public static SystemControl getSystemControl() {
		return System_Control;
	}
}
