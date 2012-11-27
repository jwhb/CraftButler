package org.jwhy.craftbutler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.bukkit.plugin.Plugin;

public class BehaviorLoader {
	
	private int loaded;
	private File behavior_directory;
	private Plugin plugin;

	/**
	 * @param dir Directory containing the behavior classes
	 */
	public BehaviorLoader(File dir, Plugin plugin){
		dir.mkdirs();
		this.behavior_directory = dir;
		this.plugin = plugin;
	}
	
	public void loadBehaviors(){
		URL[] url_list = CraftButlerUtils.getDirectoryFilesAsURL(behavior_directory, ".jar");
		CraftButlerUtils.logDebug(url_list.toString(), this.plugin);
		
		for(URL url : url_list){
			String classname = CraftButlerUtils.removeExtension(url.getFile());
			try{
		        URLClassLoader loader = new URLClassLoader(new URL[]{url});
				Class cl = Class.forName(classname, true, loader);
			}catch(ClassNotFoundException e){
				CraftButlerUtils.logDebug("Class from " + url.getFile() + " could not be loaded!", this.plugin);
			}catch (Exception e) {
				CraftButlerUtils.logDebug(url.getFile() + " could not be loaded!", this.plugin);
				e.printStackTrace();
			}
		}
	}
}
