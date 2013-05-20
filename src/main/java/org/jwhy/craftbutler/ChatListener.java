package org.jwhy.craftbutler;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener {

	private Plugin plugin;
	
	public ChatListener(Plugin plugin){
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event){
		event.getPlayer().sendMessage(event.getMessage());
		CraftButlerUtils.getDirectoryFilesAsURL(new File("behaviors"), ".jar");
	}
	
}
