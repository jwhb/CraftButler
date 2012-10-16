package org.jwhy.craftbutler;

import java.io.File;

public class BehaviorLoader {
	
	private int loaded;

	public void loadBehaviors(File dir){
		File[] fileArray = dir.listFiles();
		for(int i = 0; i<fileArray.length;i++){
		  fileArray[i].exists();
		}
	}
	
	public void loadBehavior(){
		
	}
	
	public int getNumLoadedBehaviour(){
		return(this.loaded);
	}
}
