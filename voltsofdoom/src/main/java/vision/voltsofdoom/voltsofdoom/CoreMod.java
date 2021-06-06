package vision.voltsofdoom.voltsofdoom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.voltsofdoom.resourcepack.loading.AdventureResourceLoader;
import vision.voltsofdoom.voltsofdoom.resourcepack.loading.RegisterableResourceLoader;
import vision.voltsofdoom.voltsofdoom.resourcepack.loading.TextureResourceLoader;
import vision.voltsofdoom.zapbyte.ZapByteReference;
import vision.voltsofdoom.zapbyte.mod.Mod;
import vision.voltsofdoom.zapbyte.registry.IRegistryMessenger;
import vision.voltsofdoom.zapbyte.resource.ID;

/**
 * A mod 'owned' by the Volts of Doom game itself which is used for adding to game registries etc
 * like a normal mod would.
 * 
 * @author GenElectrovise
 *
 */
@Mod(modid = CoreMod.MODID)
public class CoreMod {
  public static final String MODID = "voltsofdoom";
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CoreMod.class);

  public CoreMod() {

  }

  public static final IRegistryMessenger<RegisterableResourceLoader> TEXTURES =
      VoltsOfDoom.getInstance().getRegistry().register(new ID(MODID, "textures"), () -> new TextureResourceLoader(ZapByteReference.getResourcePacks()), RegisterableResourceLoader.class);
  public static final IRegistryMessenger<RegisterableResourceLoader> ADVENTURES =
      VoltsOfDoom.getInstance().getRegistry().register(new ID(MODID, "adventures"), () -> new AdventureResourceLoader(ZapByteReference.getResourcePacks()), RegisterableResourceLoader.class);
}
