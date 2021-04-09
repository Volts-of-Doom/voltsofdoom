package vision.voltsofdoom.silverspark.graphic;

import java.util.ArrayList;

public class Manifest {

  private ArrayList<ManifestEntry> entries = new ArrayList<ManifestEntry>();
  
  public void addEntry(ManifestEntry entry) {
    entries.add(entry);
  }
 
}
