package net.azisaba.afnw.bedrockPvE.commands;

import net.azisaba.afnw.bedrockPvE.BedrockPvE;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class BPE implements CommandExecutor {
    private final String prefix = "[BedrockPvE] ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
        if (command.getName().equals("bpe")){
            Player player = (Player) sender;
            World bpe = Bukkit.getServer().getWorld("bedrockpve");
            Location point = Objects.requireNonNull(bpe).getSpawnLocation();
            if (player.getWorld() == bpe) {
                player.sendMessage(ChatColor.RED + prefix + "既にBedrockPvEに接続しています。");
                return true;
            }
            if(player.getWorld() != Bukkit.getServer().getWorld("world")){
                if(player.hasPermission("afnw.op.commands")) {
                    player.sendTitle(ChatColor.YELLOW + "オペレーターテレポート", "制限を回避してBedrock PvEに移動しました。", 3, 60, 1);
                    player.teleport(point);
                    return true;
                }
                player.sendMessage(ChatColor.RED + prefix + "AFNW通常ワールドからのみBedrockPvEに接続できます。");
                return true;
            }
            if (player.hasPermission("bpe.op")) {
                player.sendTitle(ChatColor.YELLOW + "オペレーターテレポート", "制限を回避してBedrock PvEに移動しました。", 3, 60, 1);
                player.teleport(point);
                return true;
            }
            player.sendMessage(ChatColor.AQUA + prefix + "BedrockPvEへ移動します......5秒待機してください。");
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendTitle(ChatColor.YELLOW + "Screaaaam!!!", "Bedrock PvEに移動しました。", 3, 60, 1);
                    player.sendMessage(ChatColor.YELLOW + prefix + "Bedrock PvEに移動しました。");
                    player.teleport(point);
                }
            }.runTaskLater(JavaPlugin.getPlugin(BedrockPvE.class), 20 * 5);
            return true;
        }
        return false;
    }
}
