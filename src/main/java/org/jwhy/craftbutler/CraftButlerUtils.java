package org.jwhy.craftbutler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
            CraftButlerUtils.copy(plugin.getResource("config.yml"), configFile);
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
}
