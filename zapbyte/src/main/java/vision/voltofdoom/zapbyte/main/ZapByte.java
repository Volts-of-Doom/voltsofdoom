package vision.voltofdoom.zapbyte.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main class of the {@link ZapByte} module. Any application wishing to use
 * {@link ZapByte} should extends this class. Its <code>main</code> method
 * should create a new instance of itself, run the {@link #collectZapbits()}
 * method, adding each found {@link ZapBit} to the {@link #zapBits} {@link Set},
 * then should call {@link #run()} to start the application.
 */
public abstract class ZapByte implements Runnable {
	
	public String[] args = null;
	private static String[] defaultArgs = {};
	private boolean launched = false;
	
	private Set<ZapBit> zapBits;

	public ZapByte() {
		this.zapBits = new HashSet<ZapBit>();
	}

	public Set<ZapBit> getZapBits() {
		return zapBits;
	}

	public abstract void collectZapbits();

	@Override
	public void run() {
		
		if (launched) {
			throw new IllegalStateException("This instance of <? extends ZapByte> has already been launched!");
		}
		
		launched = true;
		if (this.args == null)
			args = defaultArgs;

		// Get all into map
		Map<Integer, ZapBit> bits = new HashMap<Integer, ZapBit>();
		zapBits.forEach((bit) -> bits.put(bit.getPriority(), bit));

		// Sort
		List<Integer> ints = new ArrayList<Integer>(bits.keySet());
		Collections.sort(ints);

		// Run
		for (Integer integer : ints) {
			bits.get(integer).getTask().run();
		}
	}
}
