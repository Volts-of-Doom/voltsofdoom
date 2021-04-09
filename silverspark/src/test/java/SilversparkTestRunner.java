import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.graphic.Manifest;
import vision.voltsofdoom.silverspark.graphic.ManifestEntry;
import vision.voltsofdoom.silverspark.graphic.Spark;
import vision.voltsofdoom.silverspark.graphic.SparkAtlas;
import vision.voltsofdoom.silverspark.guice.GuiceModule;

public class SilversparkTestRunner implements Runnable {

  private static final String DEFAULT_PATH = 
      "C:/Users/admin/AppData/Roaming/zapbyte/voltsofdoom/resources/";
  private static final String DEFAULT_IMAGE = "testrunnerexampleimage.png";
  private static final String DEFAULT_MANIFEST = "testrunnerexamplemanifest.json";
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
    
    Manifest manifest = new Manifest();
    
    try {
      sparkAtlas.loadTexture(DEFAULT_PATH + DEFAULT_IMAGE);
      sparkAtlas.loadManifest(DEFAULT_PATH + DEFAULT_MANIFEST);
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  
    silverspark.setTextureAtlas(sparkAtlas);
    silverspark.start();
  }
 
}
