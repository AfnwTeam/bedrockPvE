package com.github.afnw.bedrockPvE.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class BPESpawn implements CommandExecutor {
    private final JavaPlugin plugin;

    public BPESpawn(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args){
        if(command.getName().equalsIgnoreCase("bpespawn")){
            String mode;
            String prefix = "[BedrockPvE] ";
            Random rnd = new Random();
            FileConfiguration config = plugin.getConfig();
            // エラー処理: argsがnull
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + prefix + "設定が指定されなかったのでDefaultとしました．");
                mode = "Default";
            }
            else mode = args[0];

            // エラー処理: config.yml に含まれていない設定項目
            if(!config.contains(mode)){
                sender.sendMessage(ChatColor.RED + prefix + "その設定は設定ファイルに含まれていません．");
                return true;
            }

            HashMap<String, HashMap<String, Integer>> conf = new HashMap<>();
            ConfigurationSection cfgsec = config.getConfigurationSection(mode);

            /*
              plugin.conf から順番に読み出して this.conf に詰めておく
              {"100,1,100":[{"type":ZOMBIE,"count":10},{"type":SKELETON,"count":10}]}
              to
              {"100,1,100":{"ZOMBIE":10,"SKELTON":10}}
             */
            assert cfgsec != null;
            for (String key: cfgsec.getKeys(false)){
                conf.put(key, new HashMap<>());
                for (Map<?,?> tuples: cfgsec.getMapList(key)){
                    conf.get(key).put(String.valueOf(tuples.get("type")),
                            Integer.parseInt(String.valueOf(tuples.get("count"))));
                }
            }
            sender.sendMessage(ChatColor.YELLOW + prefix + "設定を読み込みました．");
            sender.sendMessage(prefix + "Keys: "+ cfgsec.getKeys(false));
            // sender.sendMessage(conf.toString());

            World bpeworld = Bukkit.getServer().getWorld("bedrockpve");
            for (String key: conf.keySet()){
                // いい感じに座標を3つにわけつつIntegerにする
                List<Integer> locarr = Arrays.stream(key.split(",",3)).map(Integer::parseInt).collect(Collectors.toList());
                for (String key2: conf.get(key).keySet()){
                    EntityType type = EntityType.fromName(key2);
                    if(!Objects.isNull(type)){
                        Location loc = new Location(bpeworld,Double.valueOf(locarr.get(0)),Double.valueOf(locarr.get(1)),Double.valueOf(locarr.get(2)));
                        for (int i=0;i<conf.get(key).get(key2);i++) {
                            // 湧く場所を乱数で揺らす
                            double rvalue = rnd.nextDouble() - rnd.nextDouble();
                            loc.setX(loc.getX()+3*rvalue);
                            rvalue = rnd.nextDouble() - rnd.nextDouble();
                            loc.setZ(loc.getZ()+3*rvalue);
                            assert bpeworld != null;
                            bpeworld.spawnEntity(loc,type);
                        }
                    }
                }
            }
        }
        return true;
    }
}
