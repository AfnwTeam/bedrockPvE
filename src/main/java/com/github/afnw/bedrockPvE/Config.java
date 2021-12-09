package com.github.afnw.bedrockPvE;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class Config {
    private final Plugin plugin;
    private FileConfiguration config = null;

    public Config(Plugin plugin){
        this.plugin = plugin;
        load();
    }

    public void load(){
        plugin.saveDefaultConfig();
        if (config != null) {
            plugin.reloadConfig();
        }
        config = plugin.getConfig();
    }
}
