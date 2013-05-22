package org.jwhy.craftbutler.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedBroadcastTask extends BukkitRunnable {

	private Plugin plugin;
	private String[] msgs;

	//Constructor for single message
	public DelayedBroadcastTask(Plugin plugin, String msg) {
        this.plugin = plugin;
        this.msgs = new String[]{msg};
	}
	
	//Constructor for multiple messages
	public DelayedBroadcastTask(Plugin plugin, String[] msgs) {
        this.plugin = plugin;
        this.msgs = msgs;
	}

	//Start broadcasting
	public void run() {
		for(int i=0; i<this.msgs.length; i++){
			this.plugin.getServer().broadcastMessage(msgs[i]);
		}
	}

}
