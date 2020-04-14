package genelectrovise.voltsofdoom_coresystem.loader;

import java.net.URL;
import java.net.URLClassLoader;

public class VODClassLoader {
	private URLClassLoader loader;
	private URL[] urls;

	/**
	 * A storage and utility class for the game's mod ClassLoader to load mod jar files.
	 * 
	 * @param loader A new instance of a URLClassLoader
	 */
	public VODClassLoader(URL[] urls) {
		this.loader = new URLClassLoader(urls);
		this.urls = urls;
	}
	
	// Get and set

	public URLClassLoader getLoader() {
		return loader;
	}

	public URL[] getUrls() {
		return urls;
	}

}
