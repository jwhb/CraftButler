package org.jwhy.craftbutler;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.jwhy.craftbutler.task.DelayedBroadcastTask;

public class ChatListener implements Listener {

	private Plugin plugin;
	private FileConfiguration config;
	private BehaviorManager bm;
	private String cmdPrefix;
	private HashMap<String, Method> prefixedCmds;
	@SuppressWarnings("unused")
	private HashMap<String, Method> regex;
	
	public ChatListener(Plugin plugin){
		this.plugin = plugin;
		this.config = plugin.getConfig();
		this.cmdPrefix = this.config.getString("system.prefixed-commands.prefix", ".");
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
				//Check if behavior is disabled
				String behavior_name = behavior.getClass().getSimpleName();
				if(this.config.getBoolean("behaviors." + behavior_name.toLowerCase() + ".enabled", true)){
					Object result = method.invoke(behavior, event);
			        new DelayedBroadcastTask(
		        		this.plugin,
		        		result.toString()
			        ).runTaskLater(
		        		this.plugin, (int) 20 * (long) this.config.
		        		getDouble("system.prefixed-commands.delay", 0.1d)
			        );
				} else {
					CraftButlerUtils.logDebug("Disabled behavior queried: " + behavior_name, this.plugin);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
