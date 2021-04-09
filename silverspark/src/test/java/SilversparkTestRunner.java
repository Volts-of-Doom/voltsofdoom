import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.graphic.Spark;
import vision.voltsofdoom.silverspark.graphic.SparkAtlas;
import vision.voltsofdoom.silverspark.guice.GuiceModule;

public class SilversparkTestRunner implements Runnable {

  private static final String TEST_IMAGE_NAME = "images/cobble.png";
  private static final String DEFAULT_PATH = 
      "C:/Users/admin/AppData/Roaming/zapbyte/voltsofdoom/resources/cobble.png";
  public static final int WINDOW_WIDTH = 640;
  public static final int WINDOW_HEIGHT = 380;
  
  @Inject
  Silverspark silverspark;
  
  @Inject
  private SparkAtlas sparkAtlas;

  public SilversparkTestRunner() {
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new GuiceModule());
    
    SilversparkTestRunner runner = injector.getInstance(SilversparkTestRunner.class);
    
 
    runner.run();
  }

  @Override
  public void run() {
    System.out.println("Running Silverspark manual test application");
    silverspark.setTitle("Silverspark Test Window");
    
    try {
      sparkAtlas.loadTexture(DEFAULT_PATH);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  
    silverspark.setTextureAtlas(sparkAtlas);
    silverspark.start();
  }
 
}
