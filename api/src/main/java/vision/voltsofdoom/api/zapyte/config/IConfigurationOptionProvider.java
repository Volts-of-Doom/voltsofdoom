package vision.voltsofdoom.api.zapyte.config;

import java.io.InputStream;
import com.google.gson.JsonObject;

public interface IConfigurationOptionProvider {

  /**
   * @return A container object for all contained options.
   */
  JsonObject getOptions();

  /**
   * {@link #getOptions()}
   */
  InputStream getInputStream();

}
