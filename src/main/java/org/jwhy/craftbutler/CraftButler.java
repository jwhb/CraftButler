package org.jwhy.craftbutler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jwhy.craftbutler.ChatListener;

public class CraftButler extends JavaPlugin implements Listener {

	private Logger logger;
	private FileConfiguration config;
	@SuppressWarnings("unused")
	private ChatListener cl;
	private BehaviorManager bm;
	public static boolean debug = false;

	public void onDisable() {

	}

	public void onEnable() {

		//Set references
		this.logger = this.getLogger();
		this.config = new YamlConfiguration();
		this.bm = new BehaviorManager(this);
		this.cl = new ChatListener(this);
		
		// Init configuration
		File configFile = new File(getDataFolder(), "config.yml");
		CraftButlerUtils.runFirstSetup(this, configFile);
		CraftButlerUtils.loadConfigs(configFile, config);

		// Enable debug mode (if defined in config)
		if (this.config.getBoolean("debug"))
			CraftButler.debug = true;
		CraftButlerUtils.logDebug("Debug mode enabled", this);
		
		this.bm.loadFromDir(new File(this.getDataFolder().toString()
				+ File.separator
				+ this.config.getString("behaviors.directory", "behaviors")));
	}
	
	public void log(String msg){
		this.logger.log(Level.INFO, msg);
	}

}