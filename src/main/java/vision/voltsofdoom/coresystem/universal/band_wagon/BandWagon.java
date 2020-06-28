package vision.voltsofdoom.coresystem.universal.band_wagon;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Sick of hearing about event buses? Well Volts of Doom has a
 * {@link BandWagon}!
 * 
 * @author GenElectrovise
 *
 */
public class BandWagon {

	/** The methods found to be annotated with {@link Stowaway} */
	public static ArrayList<Consumer<Event>> stowawayEventConsumerMethods = new ArrayList<Consumer<Event>>();

	public static void stowawayMethod(Consumer<Event> eventConsumer) {
		stowawayEventConsumerMethods.add(eventConsumer);
	}

	public static void playEvent(Event event) {
		stowawayEventConsumerMethods.forEach((eventConsumer) -> {
			eventConsumer.accept(event);
		});
	}

}
