package org.jwhy.craftbutler;

import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class Behavior {

	public String name;
	@SuppressWarnings("unused")
	private CraftButler butler;
	
	public Behavior(CraftButler butler){
		this.butler = butler;
	}

	public final String getName(){
		return(this.name);
	}

	public HashMap<String, Method> mapPrefixedCommands() {
		return(new HashMap<String, Method>());
	}

	public HashMap<String, Method> mapRegex() {
		return(new HashMap<String, Method>());
	}
	
}
