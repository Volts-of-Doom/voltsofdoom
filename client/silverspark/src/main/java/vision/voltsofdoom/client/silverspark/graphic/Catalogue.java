package vision.voltsofdoom.client.silverspark.graphic;

import java.util.HashMap;

// TODO - this class is just a hashmap, is it even needed?
public class Catalogue {

  private HashMap<String, CatalogueEntry> entries = new HashMap<String, CatalogueEntry>();
  
  public void addEntry(CatalogueEntry entry) {
    entries.put(entry.getName(), entry);
  }
  
  public CatalogueEntry getEntry(String name) {
    return entries.get(name); // TODO what if missing?
  }  
 
}
