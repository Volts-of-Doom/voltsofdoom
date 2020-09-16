package vision.voltsofdoom.zapbyte.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

/**
 * 
 * Don't use this for an actual implementation, as this is only for my own
 * sanity in working {@link Guice} out.
 * <a href="https://www.baeldung.com/guice">Baeldung on Guice</a>
 * 
 * @author GenElectrovise
 *
 */
public class ExampleBasicModule extends AbstractModule {

	// 2 (See configure#2)
	//@SuppressWarnings("rawtypes")
	//@Inject
	//@Named("ArrayList")
	//List list;

	@Override
	protected void configure() {
		try {

			// 1
			// Suggest that wherever an @Inject of type List is found, inject an instance of
			// ArrayList
			
			//bind(List.class).to(ArrayList.class);

			// 2 (See field example)
			// Suggests that, whenever an @Inject of type List is found, IF it is @Named
			// "ArrayList", inject an instance of ArrayList
			
			//bind(List.class).annotatedWith(Names.named("ArrayList")).to(List.class);

			// 3
			// #1, but inject an instance using the supplied constructor, either with an
			// untargeted (A) binding, or targeted (B) one.
			
			//bind(Boolean.class).toInstance(true);
			//bind(List.class).toConstructor(ArrayList.class.getConstructor(Integer.TYPE));

			// 4
			// Inject the given instance.
			
			//bind(Object.class).toInstance(new Object());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
