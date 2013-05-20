package org.jwhy.craftbutler;

import java.io.File;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import org.bukkit.plugin.Plugin;

public class BehaviorManager {

	private Plugin butler;
	private Logger logger;
	private  List<Behavior> behaviors = new ArrayList<Behavior>();

	public BehaviorManager(CraftButler butler) {
		this.butler = butler;
		this.logger = butler.getLogger();
	}

	public List<Behavior> loadFromDir(File dir) {
		
		dir.mkdirs();

		if (!dir.exists()) {
			this.logger.log(Level.WARNING, "Behavior directory not found: '" + dir
					+ "'");
			return this.behaviors;
		}

		ClassLoader loader;
		try {
			loader = new URLClassLoader(new URL[] { dir.toURI().toURL() },
					Behavior.class.getClassLoader());
		} catch (MalformedURLException ex) {
			this.logger.log(Level.SEVERE,
					"Error while configuring behavior class loader", ex);
			return this.behaviors;
		}
		for (File file : dir.listFiles()) {
			String name = file.getName();
			if (!name.endsWith(".class")) {
				continue;
			}
			
			//Remove extension
			name = name.substring(0, name.lastIndexOf("."));

			try {
				Class<?> clazz = loader.loadClass(name);
				
				Object object = clazz.getDeclaredConstructor(CraftButler.class)
						.newInstance(this.butler);
				if (!(object instanceof Behavior)) {
					this.logger.info("Not a valid behavior: "
							+ clazz.getSimpleName());
					continue;
				}
				Behavior behavior = (Behavior) object;
				this.behaviors.add(behavior);
				
				if(behavior.getName() != null) name = behavior.getName();
				this.logger.info("Loaded behavior: " + name);
			} catch (Exception ex) {
				this.logger.log(Level.WARNING, "Error loading '" + name + "'");
				ex.printStackTrace();
			}
		}
		return this.behaviors;
	}
    
    public void registerBehavior(Behavior behavior){
    	this.behaviors.add(behavior);
    }
}
