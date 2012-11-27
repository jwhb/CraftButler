package org.jwhy.craftbutler;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.jwhy.craftbutler.ChatListener;

public class CraftButler extends JavaPlugin implements Listener {
	
	private File configFile;
    private FileConfiguration config;
	private ChatListener cl;
	private BehaviorLoader bl;
	private Logger log;
	public static boolean debug = false;

    
    public void onDisable() {
    	
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
    	this.log = this.getLogger(); 
    	String slash = java.io.File.separator;
    	
    	//Init configuration
    	this.config = new YamlConfiguration();
        this.configFile = new File(getDataFolder(), "config.yml");
        CraftButlerUtils.runFirstSetup(this, configFile);
        CraftButlerUtils.loadConfigs(configFile, config);
        
        //Register listener
        this.cl = new ChatListener((Plugin) this);
        
        //Load behaviors
        File behavior_directory = new File(
        	getDataFolder() + slash + config.getString("behaviors.location", "behaviors")
        );
        this.bl = new BehaviorLoader(behavior_directory, this);
        this.bl.loadBehaviors();
        
        CraftButlerUtils.logDebug("Debug mode enabled", this);
        if(this.config.getBoolean("debug")){
        	log.info("Debug mode enabled");
        }
    }
    
} 