package net.azisaba.afnw.bedrockPvE.commands;

//import io.lumine.xikage.mythicmobs.MythicMobs;
//import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;

public class BPESpawn implements CommandExecutor {
    private final JavaPlugin plugin;
    private Random rnd = new Random();

    public BPESpawn(JavaPlugin plugin){
        this.plugin = plugin;
    }

    private Location locRandomizor(Location locorg) {
        Location loc = locorg.clone();
        // 湧く場所を乱数で揺らす
        double rvalue = rnd.nextDouble() - rnd.nextDouble();
        loc.setX(loc.getX()+3*rvalue);
        rvalue = rnd.nextDouble() - rnd.nextDouble();
        loc.setZ(loc.getZ()+3*rvalue);
        // locの位置にブロックがあるとき，Yを1追加し続ける
        while((!(loc.getBlock().isEmpty()))||(!(loc.add(0,1,0).getBlock().isEmpty()))) loc.setY(loc.getY()+1);
        return loc;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args){
        if(command.getName().equalsIgnoreCase("bpespawn")){
            String mode;
            String prefix = "[BedrockPvE] ";
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

            // エフェクト指定があるかを判断
            double effectRate = 1.0;
            if(args.length < 2) {
                sender.sendMessage(ChatColor.YELLOW + prefix + "Effect Rate指定がないため 1.0 とします");
            }
            else {
                try {
                    effectRate = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + prefix + "Effect Rateは実数で指定してください");
                }
                if (effectRate > 0)
                    sender.sendMessage(ChatColor.YELLOW + prefix + "Effect Rateを" + effectRate + "とします");
                else {
                    sender.sendMessage(ChatColor.RED + prefix + "Effect Rateに負数が指定されたため 0.0 に設定します");
                    effectRate = 0.0;
                }
            }

            ConfigurationSection cfgsec = config.getConfigurationSection(mode);

            World bpeworld = Bukkit.getServer().getWorld("bedrockpve");
            for (String key: cfgsec.getKeys(false)){
                // いい感じに座標を3つにわけつつIntegerにする
                List<Integer> locarr = Arrays.stream(key.split(",",3)).map(Integer::parseInt).collect(Collectors.toList());
                for (Map<?,?> list: cfgsec.getMapList(key)){
                    Location locorg = new Location(bpeworld,Double.valueOf(locarr.get(0)),Double.valueOf(locarr.get(1)),Double.valueOf(locarr.get(2)));
                    if (((String)(list.get("type"))).startsWith("MM.")) {
                        // MMのMOBの場合
//                        for (int i=0;i<conf.get(key).get(name);i++) {
//                            Location loc = locRandomizor(locorg);
//                            // getMythicMob(String s)は見つからなければnullを返す
//                            // と思ったけど，先に存在チェックをしてMythicMobインスタンスを渡してもtry-catchしないと怒られるので
//                            // 存在チェックをせずにtry-catchする
//                            try {
//                                MythicMobs.inst().getAPIHelper().spawnMythicMob(name.substring(3),loc);
//                            } catch (InvalidMobTypeException e) {
//                                sender.sendMessage(ChatColor.RED + prefix + name + "は存在しません．");
//                                break;
//                            }
//                        }
                    } else {
                        // バニラのMOBの場合
                        EntityType type = EntityType.fromName((String)(list.get("type")));
                        if(!Objects.isNull(type)){
                            for (int i=0;i<((int)(list.get("count")));i++) {
                                Location loc = locRandomizor(locorg);
                                assert bpeworld != null;
                                Entity ent = bpeworld.spawnEntity(loc,type);
                                PotionEffect pe;
                                for (Map<?,?> eflist: (List<Map<?,?>>)(list.get("effect"))) {
                                    pe = new PotionEffect(PotionEffectType.getByName((String)(eflist.get("type"))),(int)(eflist.get("duration")),(int)((int)(eflist.get("amplifier"))*effectRate));
                                    if(!pe.apply((LivingEntity) ent)) sender.sendMessage(ChatColor.RED + prefix +ent.getName() + "に" + pe.getType().toString() + "付与失敗しました．");
                                }
                            }
                        } else{
                            sender.sendMessage(ChatColor.RED + prefix + (String)(list.get("type")) + "は存在しません．");
                        }
                    }
                }
            }
            sender.sendMessage(ChatColor.YELLOW + prefix + "モンスターをSpawnさせました．");
            return true;
        }
        return false;
    }
}
