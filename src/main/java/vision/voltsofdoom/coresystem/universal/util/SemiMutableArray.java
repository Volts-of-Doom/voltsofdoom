package vision.voltsofdoom.coresystem.universal.util;

import java.util.Iterator;

import vision.voltsofdoom.coresystem.universal.log.Loggers;

/**
 * An array which can only be added to and cleared.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public class SemiMutableArray<T> implements Iterable<T> {
	private T[] contents;

	public static void main(String[] args) {

		Loggers.CORESYSTEM_MISCELLANEOUS_MATH_PICKY.info("Starting");

		SemiMutableArray<String> smarr = new SemiMutableArray<String>();

		smarr.clear();
		smarr.append("first");
		smarr.append("second");
		smarr.append("third");
		smarr.clear();
		smarr.append("fourth");
		smarr.append("fifth");
		smarr.append("sixth");
		smarr.append("seventh");
		smarr.append("eighth");

		StringBuilder builder = new StringBuilder();
		for (String str : smarr) {
			builder.append(str + " ");
		}
		Loggers.CORESYSTEM_MISCELLANEOUS_MATH_PICKY.info(builder.toString());
	}

	@SuppressWarnings("unchecked")
	public SemiMutableArray() {
		try {
			contents = (T[]) new Object[1];
		} catch (ClassCastException c) {
			Loggers.CORESYSTEM_MISCELLANEOUS_MATH.severe("Illegal cast from Object[] to T[] in contents: " + contents);
		}
	}

	@SuppressWarnings("unchecked")
	public SemiMutableArray<T> insertAt(T item, int index) {
		T[] copy = (T[]) new Object[contents.length + 1];

		// Copy first section
		for (int i = 0; i < index; i++) {
			copy[i] = contents[i];
		}

		// Insert item
		copy[index] = item;

		// Copy second half
		for (int i = index + 1; i < contents.length + 1; i++) {
			copy[i] = contents[i - 1];
		}

		contents = copy.clone();

		return this;
	}

	/**
	 * Append an item to the backing array.
	 * 
	 * @param item
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SemiMutableArray<T> append(T item) {
		try {

			T[] copy = (T[]) new Object[contents.length + 1];

			for (int i = 0; i < contents.length; i++) {
				copy[i] = contents[i];
			}

			copy[copy.length - 1] = item;

			contents = copy.clone();

		} catch (ClassCastException c) {
			Loggers.CORESYSTEM_MISCELLANEOUS_MATH.severe("Illegal cast from Object[] to T[] in contents: " + contents);
		}

		return this;
	}

	@SuppressWarnings("unchecked")
	public SemiMutableArray<T> clear() {
		try {
			contents = (T[]) new Object[] {};
		} catch (ClassCastException c) {
			Loggers.CORESYSTEM_MISCELLANEOUS_MATH.severe("Illegal cast from Object[] to T[] in contents: " + contents);
		}
		return this;
	}

	@Override
	public Iterator<T> iterator() {
		return new SemiMutableArrayIterator();
	}

	public class SemiMutableArrayIterator implements Iterator<T> {

		int index;

		public SemiMutableArrayIterator() {
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < contents.length && index > -1;
		}

		@Override
		public T next() {
			int i = index++;
			return contents[i];
		}

	}
}
