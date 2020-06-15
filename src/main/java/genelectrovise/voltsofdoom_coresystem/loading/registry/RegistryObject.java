package genelectrovise.voltsofdoom_coresystem.loading.registry;

public class RegistryObject<T> {
	private T t;
	private Class<T> child;

	public RegistryObject(Class<T> child) {
		this.child = child;
	}

	@SuppressWarnings("unchecked")
	public T getChild() {
		return (T) child;
	}

	public T getT() {
		return t;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistryObject{");

		builder.append("Type=" + t + " Class=" + child);

		builder.append("}");
		return builder.toString();
	}
}
