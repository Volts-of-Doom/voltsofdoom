package vision.voltsofdoom.zapbyte.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * The home of Google {@link Guice} in Volts of Doom. <br>
 * <br>
 * To use {@link Guice} to create objects (i.e. like you ran a constructor),
 * call {@link Guicer#getInjector()}, then call
 * {@link Injector#getInstance(Class)}.<br>
 * You can call on a bound and {@link Named} instance by annotating the
 * constructor with an {@link Named} annotation - the argument being the name of
 * the instance you would like to invoke.
 * 
 * @author GenElectrovise
 *
 */
@Singleton
public class Guicer {

	private static final Guicer GUICER = new Guicer();
	private Injector injector;

	public static Guicer getInstance() {
		return GUICER;
	}

	public Guicer() {
		this.injector = Guice.createInjector(new ZapByteGuiceBindingModule());
	}

	public Injector getInjector() {
		return injector;
	}

	public static void main(String[] args) {
		GuiceTest.main(null);
	}

	public static class GuiceTest {

		String str;

		public static void main(String[] args) {
			Guicer guicer = new Guicer();
			GuiceTest test = guicer.injector.getInstance(GuiceTest.class);
			test.test();
		}

		@Inject
		public GuiceTest(String str) {
			this.str = str;
			System.out.println("GuiceTest init successful? " + test());
		}

		private boolean test() {
			return str == "thisisastring";
		}

	}

}
