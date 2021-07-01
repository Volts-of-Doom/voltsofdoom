import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.api.IRenderable;
import vision.voltsofdoom.silverspark.graphic.SparkAtlas;
import vision.voltsofdoom.silverspark.guice.GuiceModule;
import vision.voltsofdoom.silverspark.render.BasicRenderable;
import vision.voltsofdoom.silverspark.state.RenderState;

public class SilversparkTestRunner implements Runnable {

  private static final String DEFAULT_PATH = "C:/Users/admin/AppData/Roaming/zapbyte/voltsofdoom/resources/";
  private static final String DEFAULT_IMAGE = "testrunnerexampleimage.png";
  private static final String DEFAULT_CATALOGUE = "testrunnerexamplecatalogue.json";
  public static final int WINDOW_WIDTH = 640;
  public static final int WINDOW_HEIGHT = 380;

  @Inject
  Silverspark silverspark;
  @Inject
  private SparkAtlas sparkAtlas;
  
  private String atlasPath;
  
  private String atlasImage;
  
  private String exampleCatalogueJson;

  /**
   * SilversparkTestRunner allows easy verification of changes to Silverspark rendering by 
   * displaying a test window with known contents.
   * 
   */
  
  public SilversparkTestRunner() {}

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new GuiceModule());
    SilversparkTestRunner runner = injector.getInstance(SilversparkTestRunner.class);
    runner.setAtlasPath(args.length > 0 ? args[0] : DEFAULT_PATH);
    runner.setAtlasImage(args.length > 1 ? args[1] : DEFAULT_IMAGE);
    runner.setExampleCatalogueJson(args.length > 2 ? args[2] : DEFAULT_CATALOGUE);
    
    runner.run();
  }

  @Override
  public void run() {
    System.out.println("Running Silverspark manual test application");
    silverspark.setTitle("Silverspark Test Window");

    populateAtlas();

    RenderState renderState = new RenderState();
    ArrayList<IRenderable> renderables = populateRenderState();
    renderState.setRenderables(renderables);
    
    Gson gson = new Gson();
    String rs = gson.toJson(renderState);
    
    System.out.println("Render state as Json: " + rs);


    silverspark.setTextureAtlas(sparkAtlas);
    silverspark.setRenderState(renderState);
    silverspark.start();
  }

  private ArrayList<IRenderable> populateRenderState() {
    ArrayList<IRenderable> renderables = new ArrayList<IRenderable>();

    for (int i = 0; (i * 32) < WINDOW_WIDTH; i++) {
      for (int j = 0; (j * 32) < WINDOW_HEIGHT; j++) {
        BasicRenderable br = new BasicRenderable("COBBLE_TILE", i * 32, j * 32);
        renderables.add(br);
      }
    }
    return renderables;
  }

  private void populateAtlas() {
    try {

      sparkAtlas.loadTexture(atlasPath + atlasImage);

      sparkAtlas.loadCatalogue(atlasPath + exampleCatalogueJson);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setAtlasImage(String atlasImage) {
    this.atlasImage = atlasImage;
  }

  private void setAtlasPath(String atlasPath) {
    this.atlasPath = atlasPath;
  }
  
  private void setExampleCatalogueJson(String catalogueJson) {
    this.exampleCatalogueJson = catalogueJson;
  }


}
