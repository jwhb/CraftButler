package org.jwhy.craftbutler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.jwhy.craftbutler.ChatListener;

public class CraftButler extends JavaPlugin implements Listener {
	
    File configFile;
    FileConfiguration config;
	ChatListener cl;
	BehaviorLoader bl;
	private Logger logger;
    
    public void onDisable() {
    	
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
    	this.logger = this.getLogger(); 
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
        this.bl = new BehaviorLoader(behavior_directory);
        this.bl.loadBehaviors();
        
        CraftButlerUtils.logDebug("Debug mode enabled");
        if(this.config.getBoolean("debug")){
        	logger.log(Level.INFO, "Debug mode enabled");
        }
    }
    
} 