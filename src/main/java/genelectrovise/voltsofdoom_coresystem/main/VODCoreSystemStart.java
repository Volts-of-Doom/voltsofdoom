package genelectrovise.voltsofdoom_coresystem.main;

public class VODCoreSystemStart {

	private static SystemControl SYSTEM_CONTROL;

	public static void main(String[] args) {
		initialiseGame();
	}

	private static void initialiseGame() {
		try {
			SYSTEM_CONTROL = new SystemControl();
		} catch (Exception e) {

		}
	}

	public static SystemControl getSYSTEM_CONTROL() {
		return SYSTEM_CONTROL;
	}
}
