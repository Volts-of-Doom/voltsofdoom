package genelectrovise.voltsofdoom_coresystem.universal.main;

import java.io.IOException;

public class GameController {
	private static SystemControl systemcontrol;
	
	public void initialiseAll() throws IOException {
		systemcontrol = new SystemControl();
	}

	public static SystemControl getSystemControl() {
		return systemcontrol;
	}
}
