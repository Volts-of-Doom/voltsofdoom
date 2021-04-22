package vision.voltsofdoom.zapbyte.bandwagon.event;

import vision.voltsofdoom.zapbyte.ZapByte;
import vision.voltsofdoom.zapbyte.bandwagon.BandWagon;
import vision.voltsofdoom.zapbyte.registry2.IRegistryStatus;
import vision.voltsofdoom.zapbyte.window.ILoadingWindowDetailedStatus;

/**
 * Called when the {@link BandWagon} is being created.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryStatusEvent implements IEvent {

  private ZapByte zapbyte;
  private IRegistryStatus status;

  public RegistryStatusEvent(ZapByte zapbyte, IRegistryStatus status) {
    this.zapbyte = zapbyte;
    this.status = status;
  }
  
  public ZapByte getZapbyte() {
    return zapbyte;
  }

  public IRegistryStatus getStatus() {
    return status;
  }

  public ILoadingWindowDetailedStatus getDetailedStatus() {
    return () -> "Updated registry status to: \" + status";
  }

}
