package net.azisaba.afnw.bedrockPvE.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Reload implements CommandExecutor {
    private final JavaPlugin plugin;

    public Reload(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args){
        String prefix = "[BedrockPvE] ";
        sender.sendMessage(prefix +"config.ymlを読み込み中");
        plugin.reloadConfig();
        sender.sendMessage(prefix +"読み込み完了");
        sender.sendMessage(prefix +"Default Keys: "+ Objects.requireNonNull(plugin.getConfig().getConfigurationSection("Default")).getKeys(false));
        return true;
    }
}
