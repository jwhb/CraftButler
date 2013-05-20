package org.jwhy.craftbutler;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener {

	private Plugin plugin;
	private String cmdPrefix;
	private HashMap<String, Method> prefixedCommands;
	private HashMap<String, Method> regex;
	
	public ChatListener(Plugin plugin){
		this.plugin = plugin;
		this.cmdPrefix = this.plugin.getConfig().getString("system.command-prefix", ".");
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	public void setCommandPrefix(String cmdPrefix) {
		this.cmdPrefix = cmdPrefix;
	}

	public void setPrefixedCommands(HashMap<String, Method> prefixedCommands) {
		this.prefixedCommands = prefixedCommands;
	}

	public void setRegex(HashMap<String, Method> regex) {
		this.regex = regex;
	}
	


	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
		String msg = event.getMessage();
		if(msg.startsWith(cmdPrefix)){
			this.dispatchPrefixedCommand(msg.substring(cmdPrefix.length()));
		}
		//TODO: Implement regex dispatching
	}
	
	private void dispatchPrefixedCommand(String prefixed_cmd) {
		this.plugin.getLogger().info("Cmmand: " + prefixed_cmd);
	}
}
