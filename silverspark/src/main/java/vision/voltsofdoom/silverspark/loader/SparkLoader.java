package vision.voltsofdoom.silverspark.loader;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class SparkLoader {

  /**
   * Should be used to load image data from source.
   */
  public InputStream loadResourceAsStream(String fileName) {

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.

    // The class loader that loaded the class
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }
  }

  public ByteBuffer inputStreamAsByteBuffer(InputStream is) throws IOException {

    byte[] targetArray = ByteStreams.toByteArray(is);
    return ByteBuffer.wrap(targetArray);

  }

}
