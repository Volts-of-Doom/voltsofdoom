package vision.voltsofdoom.api.guice;

import com.google.inject.AbstractModule;

import vision.voltsofdoom.api.guice.Guicer.GuiceTest;

public class ZapByteGuiceBindingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GuiceTest.class).toInstance(new GuiceTest("thisisastring"));
	}
}
