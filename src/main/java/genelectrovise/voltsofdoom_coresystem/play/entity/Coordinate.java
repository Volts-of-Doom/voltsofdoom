package genelectrovise.voltsofdoom_coresystem.play.entity;

/**
 * A coordinate on a grid.
 * 
 * @author GenElectrovise
 *
 */
public class Coordinate {
	private int x;
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Coordinate get() {
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Coordinate [");
		builder.append("{x=" + x + ",y=" + y + "}");
		builder.append("]");

		return builder.toString();
	}

}
