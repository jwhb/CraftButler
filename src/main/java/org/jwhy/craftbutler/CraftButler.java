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
	private ChatListener cl;
	private BehaviorManager bm;
	public static boolean debug = false;

	public void onDisable() {

	}

	public void onEnable() {

		//Set references
		this.logger = this.getLogger();
		this.config = new YamlConfiguration();
		this.cl = new ChatListener(this);
		this.bm = new BehaviorManager(this, this.cl);
		
		// Init configuration
		File configFile = new File(getDataFolder(), "config.yml");
		CraftButlerUtils.runFirstSetup(this, configFile);
		CraftButlerUtils.loadConfigs(configFile, config);

		// Enable debug mode (if defined in config)
		if (this.config.getBoolean("system.debug", false))
			CraftButler.debug = true;
		CraftButlerUtils.logDebug("Debug mode enabled", this);
		
		//Load behaviors
		this.bm.loadFromDir(new File(this.getDataFolder().toString()
				+ File.separator
				+ this.config.getString("system.directory", "behaviors")));
		
		//Register ChatListener
        this.getServer().getPluginManager().registerEvents(this.cl, this);
	}
	
	public void log(String msg){
		this.logger.log(Level.INFO, msg);
	}

	public BehaviorManager getBehaviorManager() {
		return(this.bm);
	}

}