package vision.voltsofdoom.zapbyte.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * A wrapper class for a {@link URLClassLoader}, in which the input URLs are exposed.
 */
public class VisibleURLClassLoader extends URLClassLoader {
  private final URL[] urls;

  /**
   * @param urls An array of {@link URL}s which point to jar files to load.
   */
  public VisibleURLClassLoader(URL[] urls) {
    super(urls);
    this.urls = urls;
  }

  // Get and set

  public URL[] getUrls() {
    return urls;
  }



}
