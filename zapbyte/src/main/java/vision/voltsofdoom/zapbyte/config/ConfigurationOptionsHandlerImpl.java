package vision.voltsofdoom.zapbyte.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionsHandler;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionProvider;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;
import vision.voltsofdoom.zapbyte.util.StacktraceUtils;

public class ConfigurationOptionsHandlerImpl implements IConfigurationOptionsHandler {

  @Override
  public JsonElement getOption(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void add(InputStream source) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void add(JsonElement source) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public JsonElement remove(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JsonObject getOptions() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
