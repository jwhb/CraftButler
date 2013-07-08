package org.jwhy.craftbutler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jwhy.craftbutler.ChatListener;

public class CraftButler extends JavaPlugin {

	private File configFile;
	private Logger logger;
	private FileConfiguration config;
	private BehaviorManager bm;
	private ChatListener cl;
	private CommandListener col;
	public static boolean debug = false;

	public void onDisable() {
		try {
			this.config.save(this.configFile);
		} catch (Exception e) {
			this.logger.warning("Couldn't save config file '" + this.configFile + "': "
				+ e.getMessage());
		}
	}

	public void onEnable() {

		//Set references
		this.logger = this.getLogger();
		this.config = new YamlConfiguration();
		this.cl = new ChatListener(this);
		this.bm = new BehaviorManager(this, this.cl);
		this.cl.setBehaviorManager(this.bm);
		this.col = new CommandListener(this);
		
		// Init configuration
		this.configFile = new File(getDataFolder(), "config.yml");
		CraftButlerUtils.runFirstSetup(this, this.configFile);
		CraftButlerUtils.loadConfigs(this.configFile, config);

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
        
        //Register commands
        this.getCommand("craftbutler").setExecutor(this.col);
	}
	
	public File getConfigFile(){
		return(this.configFile);
	}
	
	public void log(String msg){
		this.logger.log(Level.INFO, msg);
	}
	
	public void logDebug(String msg){
		CraftButlerUtils.logDebug(msg, this);
	}

	public BehaviorManager getBehaviorManager() {
		return(this.bm);
	}

}