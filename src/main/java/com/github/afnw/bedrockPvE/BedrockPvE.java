package com.github.afnw.bedrockPvE;

import com.github.afnw.bedrockPvE.commands.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BedrockPvE extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("bpereload")).setExecutor(new Reload(this));
        Objects.requireNonNull(getCommand("bpespawn")).setExecutor(new BPESpawn(this));
        Objects.requireNonNull(getCommand("bpe")).setExecutor(new BPE());
    }
}
