package vision.voltsofdoom.zapbyte.loading;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * A storage and utility class for the game's mod ClassLoader to load mod jar
 * files.
 */
public class VODClassLoader extends URLClassLoader {
	private URL[] urls;

	/**
	 * @param urls An array of {@link URL}s which point to jar files to load.
	 */
	public VODClassLoader(URL[] urls) {
		super(urls);
		this.urls = urls;
	}

	// Get and set

	public URL[] getUrls() {
		return urls;
	}

}
