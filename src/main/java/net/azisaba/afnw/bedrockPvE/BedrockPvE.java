package net.azisaba.afnw.bedrockPvE;

import net.azisaba.afnw.bedrockPvE.commands.*;
import net.azisaba.afnw.bedrockPvE.listener.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BedrockPvE extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Default Keys: "+ Objects.requireNonNull(getConfig().getConfigurationSection("Default")).getKeys(false));

        Objects.requireNonNull(getCommand("bpereload")).setExecutor(new Reload(this));
        Objects.requireNonNull(getCommand("bpespawn")).setExecutor(new BPESpawn(this));
        Objects.requireNonNull(getCommand("bpetestspawn")).setExecutor(new TestSpawn());
        Objects.requireNonNull(getCommand("bpe")).setExecutor(new BPE());

        getServer().getPluginManager().registerEvents(new LavaFlowCanceller(), this);
        getServer().getPluginManager().registerEvents(new IronGolemSpawnCanceller(), this);
    }
}
