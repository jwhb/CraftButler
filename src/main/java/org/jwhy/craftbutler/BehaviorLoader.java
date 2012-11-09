package org.jwhy.craftbutler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class BehaviorLoader {
	
	private int loaded;
	private File behavior_directory;

	/**
	 * @param dir Directory containing the behavior classes
	 */
	public BehaviorLoader(File dir){
		dir.mkdirs();
		this.behavior_directory = dir;
	}
	
	public void loadBehaviors(){
		File[] class_list = CraftButlerUtils.getDirectoryFiles(behavior_directory, ".class");
		CraftButlerUtils.logDebug(class_list.toString());
		URL[] urls = new URL[class_list.length];
		for(int i = 0; i < class_list.length; i++){
			try {
				urls[i] = class_list[i].toURL();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ClassLoader cl = new URLClassLoader(urls);
		cl.close();
	}
	
	public int getNumLoadedBehaviour(){
		return(this.loaded);
	}
	
	@SuppressWarnings("rawtypes")
	public void close() {
		try {
		   Class clazz = java.net.URLClassLoader.class;
		   java.lang.reflect.Field ucp = clazz.getDeclaredField("ucp");
		   ucp.setAccessible(true);
		   Object sun_misc_URLClassPath = ucp.get(this);
		   java.lang.reflect.Field loaders = 
		      sun_misc_URLClassPath.getClass().getDeclaredField("loaders");
		   loaders.setAccessible(true);
		   Object java_util_Collection = loaders.get(sun_misc_URLClassPath);
		   for (Object sun_misc_URLClassPath_JarLoader :
		        ((java.util.Collection) java_util_Collection).toArray()) {
		      try {
		         java.lang.reflect.Field loader = 
		            sun_misc_URLClassPath_JarLoader.getClass().getDeclaredField("jar");
		         loader.setAccessible(true);
		         Object java_util_jar_JarFile = 
		            loader.get(sun_misc_URLClassPath_JarLoader);
		         ((java.util.jar.JarFile) java_util_jar_JarFile).close();
		      } catch (Throwable t) {
		         // if we got this far, this is probably not a JAR loader so skip it
		      }
		   }
		} catch (Throwable t) {
		   // probably not a SUN VM
		}
		return;
	}
}
