package org.jwhy.craftbutler;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener {

	private Plugin plugin;
	private BehaviorManager bm;
	private String cmdPrefix;
	private HashMap<String, Method> prefixedCmds;
	@SuppressWarnings("unused")
	private HashMap<String, Method> regex;
	
	public ChatListener(Plugin plugin){
		this.plugin = plugin;
		this.cmdPrefix = this.plugin.getConfig().getString("system.command-prefix", ".");
	}
	
	public void setBehaviorManager(BehaviorManager bm){
		this.bm = bm;
	}

	public void setCommandPrefix(String cmdPrefix) {
		this.cmdPrefix = cmdPrefix;
	}

	public void setPrefixedCommands(HashMap<String, Method> prefixedCommands) {
		this.prefixedCmds = prefixedCommands;
	}

	public void setRegex(HashMap<String, Method> regex) {
		this.regex = regex;
	}
	

	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
		String msg = event.getMessage();
		if(msg.startsWith(cmdPrefix)){
			this.dispatchPrefixedCommand(msg.substring(cmdPrefix.length()), event);
		}
		//TODO: Implement regex dispatching
	}
	
	private void dispatchPrefixedCommand(String cmd, AsyncPlayerChatEvent event) {
		Method method = this.prefixedCmds.get(cmd);
		if(method == null){
			CraftButlerUtils.logDebug("Not a handled command: " + cmd, this.plugin);
		} else {
			Behavior behavior = this.bm.getBehavior(method.getDeclaringClass().getSimpleName());
			try {
				Object result = method.invoke(behavior, event);
				if(result != null) this.plugin.getServer().broadcastMessage(result.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
