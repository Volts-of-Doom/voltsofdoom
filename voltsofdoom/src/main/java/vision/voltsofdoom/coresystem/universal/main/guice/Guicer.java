package vision.voltsofdoom.coresystem.universal.main.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The home of Google {@link Guice} in Volts of Doom.
 * 
 * @author GenElectrovise
 *
 */
public class Guicer {

	private static final Guicer GUICER = new Guicer();
	private Injector injector;

	public static Guicer getInstance() {
		return GUICER;
	}

	public Guicer() {
		this.injector = Guice.createInjector(new ExampleBasicModule());
	}

	public Injector getInjector() {
		return injector;
	}

}
