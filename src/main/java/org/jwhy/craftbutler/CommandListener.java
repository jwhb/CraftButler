package org.jwhy.craftbutler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {

	private CraftButler plugin;

	public CommandListener(CraftButler plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    if(cmd.getName().equalsIgnoreCase("craftbutler") && args.length > 0){
	    	if(args[0].equalsIgnoreCase("reload")){
		    	CraftButlerUtils.loadConfigs(this.plugin.getConfigFile(), this.plugin.getConfig());
		    	sender.sendMessage("CraftButler configuration reloaded.");
		        return true;
	    	} else {
	    		sender.sendMessage("Unknown option: " + args[0]);
	    	}
	    }
	    return false;
	}
	
}
