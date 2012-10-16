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
	BehaviourLoader bl;
	private Logger logger;
    
    public void onDisable() {
    	
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
    	this.logger = this.getLogger(); 
    	
    	//Configuration
    	this.config = new YamlConfiguration();
        this.configFile = new File(getDataFolder(), "config.yml");
        CraftButlerUtils.runFirstSetup(this, configFile);
        CraftButlerUtils.loadConfigs(configFile, config);
        
        this.cl = new ChatListener((Plugin) this);
        this.bl = new BehaviourLoader();
        
        CraftButlerUtils.logDebug("Debug mode enabled");
        if(this.config.getBoolean("debug")){
        	logger.log(Level.INFO, "Debug mode enabled");
        }
    }
    
} 