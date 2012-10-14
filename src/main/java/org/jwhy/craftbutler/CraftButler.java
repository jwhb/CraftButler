package org.jwhy.craftbutler;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.jwhy.craftbutler.ChatListener;

public class CraftButler extends JavaPlugin implements Listener {
	
    File configFile;
    FileConfiguration config;
	ChatListener cl;
	BehaviourLoader bl;
    
    public void onDisable() {
    	
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
        this.configFile = new File(getDataFolder(), "config.yml");
        CraftButlerUtils.runFirstSetup(this, configFile);
        CraftButlerUtils.loadConfigs(configFile, config);
        this.cl = new ChatListener((Plugin) this);
        this.bl = new BehaviourLoader();
    }
    
} 