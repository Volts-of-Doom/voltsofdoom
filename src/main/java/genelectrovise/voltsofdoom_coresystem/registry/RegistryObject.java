package genelectrovise.voltsofdoom_coresystem.registry;

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
}
