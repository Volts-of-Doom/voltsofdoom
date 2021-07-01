package vision.voltsofdoom.voltsofdoom.resourcepack.loading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventureResourceLoader extends RegisterableResourceLoader {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AdventureResourceLoader.class);

  public AdventureResourceLoader(String rootDirectory) {
    super(rootDirectory, false);
  }

  @Override
  public void load(boolean forceReload) {
    LOGGER.warn("Impl required!!");
  }

}
