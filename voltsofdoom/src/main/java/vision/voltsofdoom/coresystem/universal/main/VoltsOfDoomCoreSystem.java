package vision.voltsofdoom.coresystem.universal.main;

import java.io.IOException;

import vision.voltsofdoom.zapbyte.main.ZapBit;
import vision.voltsofdoom.zapbyte.main.ZapByte;

/**
 * The main class for Volts of Doom's Core System. The game starts running here.
 * In the case that you need it, the game should be run from
 * {@link #main(String[])}. The arguments can be passed either from the runner
 * (e.g. command line arguments), or from the voltsofdoom/config.json file,
 * which can be easily read using {@link #getVMArgs()}.
 * 
 * @author GenElectrovise
 *
 */
public class VoltsOfDoomCoreSystem extends ZapByte {

	public VoltsOfDoomCoreSystem() {
		super(ID);
	}

	public static final GameController GAME_CONTROLLER = new GameController();

	public static final String ID = "voltsofdoom";

	public static volatile boolean launched = false;

	/**
	 * Begins the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		mainStepIn();
	}

	public static void mainStepIn() {
		VoltsOfDoomCoreSystem vodcs = new VoltsOfDoomCoreSystem();

		vodcs.run();
	}

	@Override
	public void run() {
		super.run();
	}

	@Override
	public void collectZapbits() {
		addZapBit(new ZapBit(0, () -> System.out.println("Starting Volts of Doom!")));
		
		addZapBit(new ZapBit(10, () -> {
			try {
				GAME_CONTROLLER.initialiseAll();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
	}
}
