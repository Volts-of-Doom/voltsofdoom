package vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.loading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.structure.IResourcePack;
import vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.structure.ZippedResourcePack;
import vision.voltsofdoom.zapbyte.ZapByteReference;

public class RoamingZipFileResourcePackFinder extends ResourcePackFinder {
  
  public static final Logger LOGGER = LoggerFactory.getLogger(RoamingZipFileResourcePackFinder.class);

  @Override
  public List<IResourcePack> find() {

    File rootDirFile = new File(ZapByteReference.getResourcePacks());
    List<File> zipPacks = Lists.newArrayList(rootDirFile.listFiles((file) -> file.getName().endsWith(".zip")));

    List<IResourcePack> zipRPList = Lists.newArrayList();

    zipPacks.forEach((file) -> {
      try {

        ZippedResourcePack zipRP = new ZippedResourcePack(new FileInputStream(file));
        zipRPList.add(zipRP);
        return;

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

      LOGGER.error("Unable to load an IResourcePack for the file: " + file.getAbsolutePath());
    });

    return zipRPList;
  }

}
