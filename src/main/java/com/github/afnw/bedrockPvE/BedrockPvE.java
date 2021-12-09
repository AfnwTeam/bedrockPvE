package com.github.afnw.bedrockPvE;

import com.github.afnw.bedrockPvE.*;
import com.github.afnw.bedrockPvE.commands.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import java.util.Objects;

public class BedrockPvE extends JavaPlugin {
    @Getter private FileConfiguration conf;

    @Override
    public void onEnable() {
        getLogger().info("config.ymlを読み込み中");
        saveDefaultConfig();
        this.conf = getConfig();
        getLogger().info("読み込み完了");
//        for (String key: config.getKeys(false)) getLogger().info(key);

        Objects.requireNonNull(getCommand("bpespawn")).setExecutor(new BPESpawn(conf));
    }
}
