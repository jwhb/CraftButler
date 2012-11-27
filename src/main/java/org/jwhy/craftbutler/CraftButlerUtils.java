package org.jwhy.craftbutler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class CraftButlerUtils {
    public static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void runFirstSetup(Plugin plugin, File configFile){
        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            InputStream config_res = plugin.getResource("config.yml");
            if(config_res == null){
            	runEmergency(plugin, "Config doesn't exist, sample file not available.");
            }
            plugin.getLogger().log(Level.INFO, "Created new config.yml");
            CraftButlerUtils.copy(config_res, configFile);
        }
    }
    
    public static void saveConfigs(File configFile, FileConfiguration config) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadConfigs(File configFile, FileConfiguration config) {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void runEmergency(Plugin plugin, String reason){
    	plugin.getLogger().log(Level.SEVERE, "S" + reason);
    	plugin.getPluginLoader().disablePlugin(plugin);
    }


	public static void logDebug(String msg, Plugin plugin) {
		if(CraftButler.debug){
			String prefix = "Debug: ";
			if(plugin == null){
				//Anonymous logging
				Bukkit.getConsoleSender().sendMessage(prefix + msg);
			}else{
				plugin.getLogger().log(Level.INFO, prefix + msg);
			}
		}
	}
	
	public static String removeExtension(String filename) {
		int ext = filename.lastIndexOf(".");
		return(filename.substring(0, ext));
	}
	
	public static String getExtension(String filename) {
		int ext = filename.lastIndexOf(".");
		return(filename.substring(ext + 1));
	}
	
	public static String getExtension(File file){
		return(getExtension(file.getName()));
	}
	
	public static File[] getDirectoryFiles(File dir, String ext){
		File[] contents = dir.listFiles();
		for(int i = 0; i<contents.length;i++){
		  //cf => current file
		  File cf = contents[i];
		  logDebug(getExtension(cf), null);
		  if(cf instanceof File && getExtension(cf) == ext){
			  //Filter everything but files
			  contents[i] = cf;
		  }
		}
		return(contents);		
	}


	public static URL[] getDirectoryFilesAsURL(File behavior_directory, String ext) {
		File[] file_list = CraftButlerUtils.getDirectoryFiles(behavior_directory, ext);
		URL[] url_list = new URL[file_list.length];
		int i = 0;
		for(File file : file_list){
		  try {
			url_list[i] = file.toURI().toURL();
		  } catch (MalformedURLException e){e.printStackTrace();}
		}
		
		return(url_list);
	}
	
}
