package genelectrovise.voltsofdoom_coresystem.loading.registry;

import java.util.HashMap;

import genelectrovise.voltsofdoom_coresystem.universal.annotation.Mod;

/**
 * A system inspired by the Minecraft Forge Deferred Registry.
 * 
 * @author adam_
 *
 * @param <T> The class of the registered type.
 */
public class GenericRegistry<T> {
	private final Class<T> type;
	public boolean isNative = false;
	public int overridePriority = -1;
	private String modid;
	private HashMap<String, RegistryObject<T>> map = new HashMap<String, RegistryObject<T>>();;

	/**
	 * @param type      The type of this GenericRegistry. Should match the
	 *                  parameterised type of this object. Should be expressed here
	 *                  as : <code>TYPE.class"</code> OR
	 *                  <code>TYPE.getClass()</code>. In short, should put a class
	 *                  object in.
	 * 
	 * @param parentMod The @Mod this GenericRegistry is associated with. Likely
	 *                  will need to use a variation on
	 *                  <code>YOURCLASS.class.getAnnotation(Mod.class)</code> to
	 *                  obtain this, as it must be an instance of the @Mod
	 *                  Annotation of the class, not the class itself.
	 */
	public GenericRegistry(Class<T> type, Mod parentMod) {
		this.modid = parentMod.modid();
		this.type = type;
	}

	/**
	 * @return The parameterised type of this GenericRegistry
	 */
	public Class<T> getType() {
		return type;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(isNative ? "Native" : "").append("GenericRegistry<").append(getType().getSimpleName()).append("> from Mod '")
				.append(modid).append("'").toString();
	}

	/**
	 * @return The map of RegistryObjects in this GenericRegistry
	 */
	public HashMap<String, RegistryObject<T>> getMap() {
		return map;
	}

	/**
	 * Registers something with this GenericRegistry.
	 * 
	 * @param key A key from which you will obtain the registered object from the
	 *            GameRegistry.
	 * @param obj The object to register, of the type of this GenericRegistry.
	 * @return The registered RegistryObject.
	 */
	public RegistryObject<T> register(String key, RegistryObject<T> obj) {
		return getMap().put(key, obj);
	}

	/**
	 * Adds this GenericRegistry to the queue of GenericRegistries to register to
	 * the GameRegistry. Should be called in the constructor of the class this
	 * object is contained in.
	 * 
	 * @param mod The @Mod to associate this with in the GameRegistry.
	 * @return This.
	 */
	public GenericRegistry<T> addToGenericRegisteringQueue(Mod mod) {
		RegistryQueue.getInstance().add(this, mod);
		return this;
	}

	/**
	 * WARNING! This will seriously mess with the registering process! This should
	 * only return true if this is from one of the native Volts Of Doom registeries.
	 * This is to give them priority in the RegistryQueue, so the game doesn't crash
	 * when other mods try to access a native VOD Object and fail because it isn't
	 * in the Registry yet! If you are modding, DO NOT set call this method to
	 * 
	 * @return Whether this GenericRegistry is native to the game, ie IS NOT A MOD!
	 */
	public void setIsNative() {
		isNative = true;
	}

	/**
	 * Sets an overwritten priority for this GR in the RegistryQueue. DON'T TOUCH
	 * THIS UNLESS YOU KNOW WHAT YOU ARE DOING!! (Plz)
	 * 
	 * @param overridePriority
	 */
	public void setOverridePriority(int overridePriority) {
		this.overridePriority = overridePriority;
	}

	/**
	 * @return the modid
	 */
	public String getModid() {
		return modid;
	}
}
