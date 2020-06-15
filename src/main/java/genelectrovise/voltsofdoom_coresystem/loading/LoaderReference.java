package genelectrovise.voltsofdoom_coresystem.loading;

import java.io.File;
import java.util.ArrayList;

/**
 * Because having all of these variables in LoaderMissionControl was driving me
 * mad. Stores all of those variables.
 * 
 * @author GenElectrovise
 *
 */
public class LoaderReference {

	/**
	 * Contains the mods vars
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class Mods {

		private static ArrayList<Class<?>> modsClasses = new ArrayList<Class<?>>();

		private static ArrayList<Object> modInstances = new ArrayList<Object>();

		public static ArrayList<Class<?>> getMods() {
			return modsClasses;
		}

		public static void setMods(ArrayList<Class<?>> mods) {
			modsClasses = mods;
		}

		public static ArrayList<Object> getModInstances() {
			return modInstances;
		}

		public static void setModInstances(ArrayList<Object> arrayList) {
			LoaderReference.Mods.modInstances = arrayList;
		}
	}

	/**
	 * Contains reflection utils.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class Reflections {

		private static Reflections modsReflections;
		private static Reflections systemReflections;

		private static VODClassLoader loader;

		public static void setModsClassLoader(VODClassLoader loader) {
			LoaderReference.Reflections.loader = loader;
		}

		public static VODClassLoader getLoader() {
			return loader;
		}

		public static Reflections getSystemReflections() {
			return systemReflections;
		}

		public static void setSystemReflections(Reflections systemReflections) {
			LoaderReference.Reflections.systemReflections = systemReflections;
		}

		public static Reflections getModsReflections() {
			return modsReflections;
		}

		public static void setModsReflections(Reflections modsReflections) {
			LoaderReference.Reflections.modsReflections = modsReflections;
		}
	}

	public static class Jars {
		private static ArrayList<File> jarsInDir = new ArrayList<File>();

		public static ArrayList<File> getJarsInDir() {
			return jarsInDir;
		}

		public static void setJarsInDir(ArrayList<File> jarsInDir) {
			LoaderReference.Jars.jarsInDir = jarsInDir;
		}
	}

}
