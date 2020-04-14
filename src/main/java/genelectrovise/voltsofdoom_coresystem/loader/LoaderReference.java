package genelectrovise.voltsofdoom_coresystem.loader;

import java.io.File;
import java.util.ArrayList;

import org.reflections.Reflections;

/**
 * Because having all of these variables in LoaderMissionControl was driving me
 * mad. Stores all of those variables.
 * 
 * @author adam_
 *
 */
public class LoaderReference {
	private Reflections modsReflections;
	private Reflections systemReflections;

	private VODClassLoader loader;

	private ArrayList<File> jarsInDir = new ArrayList<File>();
	private ArrayList<Class<?>> modsClasses = new ArrayList<Class<?>>();

	private ArrayList<Object> modInstances = new ArrayList<Object>();

	// jarsInDir
	public ArrayList<File> getJarsInDir() {
		return jarsInDir;
	}

	public void setJarsInDir(ArrayList<File> jarsInDir) {
		this.jarsInDir = jarsInDir;
	}

	// Mods
	/**
	 * @return the mods
	 */
	public ArrayList<Class<?>> getMods() {
		return modsClasses;
	}

	/**
	 * @param mods the mods to set
	 */
	public void setMods(ArrayList<Class<?>> mods) {
		this.modsClasses = mods;
	}

	// ClassLoaders and Reflections

	public void setModsClassLoader(VODClassLoader loader) {
		this.loader = loader;
	}

	public VODClassLoader getLoader() {
		return loader;
	}

	public Reflections getSystemReflections() {
		return systemReflections;
	}

	public void setSystemReflections(Reflections systemReflections) {
		this.systemReflections = systemReflections;
	}

	public Reflections getModsReflections() {
		return modsReflections;
	}

	public void setModsReflections(Reflections modsReflections) {
		this.modsReflections = modsReflections;
	}

	public ArrayList<Object> getModInstances() {
		return modInstances;
	}

	public void setModInstances(ArrayList<Object> arrayList) {
		this.modInstances = arrayList;
	}
}
