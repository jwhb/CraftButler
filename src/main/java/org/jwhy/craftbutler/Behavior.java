package org.jwhy.craftbutler;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.event.player.AsyncPlayerChatEvent;

public abstract class Behavior {

	public String name;
	protected CraftButler butler;
	protected HashMap<String, Method> pc = new HashMap<String, Method>();
	
	public Behavior(CraftButler butler){
		this.butler = butler;
	}

	public final String getName(){
		return(this.name);
	}
	
	protected void linkMethod(String command, String method){
		try {
			this.pc.put(command, this.getClass().getMethod(method, AsyncPlayerChatEvent.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Method> mapPrefixedCommands() {
		return(new HashMap<String, Method>());
	}

	public HashMap<String, Method> mapRegex() {
		return(new HashMap<String, Method>());
	}
	
}
