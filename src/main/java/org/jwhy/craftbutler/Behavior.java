package org.jwhy.craftbutler;

public abstract class Behavior {

	public String name;
	@SuppressWarnings("unused")
	private CraftButler butler;
	
	public Behavior(CraftButler butler){
		this.butler = butler;
	}
	
	public String getName(){
		return(this.name);
	}
	
}
