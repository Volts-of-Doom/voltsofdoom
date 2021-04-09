package vision.voltsofdoom.silverspark.graphic;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import org.lwjgl.system.MemoryStack;
import com.google.gson.Gson;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.api.ITextureAtlas;

public class SparkAtlas implements ITextureAtlas {
  
  private Spark mainSpark;
  private Manifest manifest;

  public SparkAtlas() {}
  
  

  @Override
  public void addEntry(IRenderable renderable) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setImage(BufferedImage image) {
    // TODO Auto-generated method stub
  }
  
  public Spark loadTexture(String path) throws IOException {
    /*
     * SparkLoader loader = new SparkLoader();
     * 
     * InputStream in = loader.loadResourceAsStream(path); ByteBuffer imageBuffer =
     * loader.inputStreamAsByteBuffer(in);
     */

    ByteBuffer image;
    int width, height;
    try (MemoryStack stack = MemoryStack.stackPush()) {
      /* Prepare image buffers */
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer comp = stack.mallocInt(1);

      /* Load image */
      stbi_set_flip_vertically_on_load(true);
      image = stbi_load(path, w, h, comp, 4);
      if (image == null) {
        throw new RuntimeException(
            "Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
      }

      /* Get width and height of image */
      width = w.get();
      height = h.get();
    }
    
    this.mainSpark = createTexture(width, height, image);
    return mainSpark;
  }
  
  static Spark createTexture(int width, int height, ByteBuffer data) {
    Spark texture = new Spark();
    texture.setWidth(width);
    texture.setHeight(height);

    texture.bind();

    texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
    texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

    return texture;
  }
  

  public Manifest loadManifest(String fileName) {

    Gson gson = new Gson();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      
      manifest = gson.fromJson(new FileReader(fileName), Manifest.class);
      System.out.println();

    } catch (IOException e) {
        e.printStackTrace();
    }
    
    System.out.println("Manifest as string: " + gson.toJson(manifest));
    
    return manifest;
  }


  public Spark getMainSpark() {
    return mainSpark;
  }

  public void setMainSpark(Spark mainSpark) {
    this.mainSpark = mainSpark;
  }



  public void setManifest(Manifest manifest) {
    this.manifest = manifest;
    
  }

}
