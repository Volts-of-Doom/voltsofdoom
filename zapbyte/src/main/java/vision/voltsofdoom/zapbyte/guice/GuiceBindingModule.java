package vision.voltsofdoom.zapbyte.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class GuiceBindingModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("applicationNamespace")).toInstance(new String(""));
	}
}
