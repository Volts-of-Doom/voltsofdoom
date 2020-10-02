package vision.voltsofdoom.silverspark.graphic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderCreater {

  /**
   * Creates a shader with specified type and source and compiles it. The type in the tutorial
   * should be either <code>GL_VERTEX_SHADER</code> or <code>GL_FRAGMENT_SHADER</code>.
   *
   * @param type Type of the shader
   * @param source Source of the shader
   *
   * @return Compiled Shader from the specified source
   */
  public static Shader createShader(int type, CharSequence source) {
    Shader shader = new Shader(type);
    shader.source(source);
    shader.compile();

    return shader;
  }

  /**
   * Loads a shader from a file.
   *
   * @param type Type of the shader
   * @param fileName File path of the shader
   *
   * @return Compiled Shader from specified file
   */
  public Shader loadShader(int type, String fileName) {
    StringBuilder builder = new StringBuilder();

    try (InputStream in = getFileFromResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line).append("\n");
      }
    } catch (IOException ex) {
      throw new RuntimeException(
          "Failed to load a shader file!" + System.lineSeparator() + ex.getMessage());
    }
    CharSequence source = builder.toString();

    return createShader(type, source);
  }

  // get a file from the resources folder
  // works everywhere, IDEA, unit test and JAR file.
  private InputStream getFileFromResourceAsStream(String fileName) {

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



}
