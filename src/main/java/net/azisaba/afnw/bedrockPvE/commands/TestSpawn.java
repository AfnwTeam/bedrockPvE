package net.azisaba.afnw.bedrockPvE.commands;

//import io.lumine.xikage.mythicmobs.MythicMobs;
//import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class TestSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args){
        if(command.getName().equalsIgnoreCase("bpetestspawn")){
            String name;
            String prefix = "[BedrockPvE] ";
            Player player = (Player)sender;
            Location loc = player.getLocation();
            World world = player.getWorld();

            // エラー処理: argsがnull
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + prefix + "MOB名が指定されていません．");
                return true;
            }
            else name = args[0];

            // 指定されたMOBをSenderの地点に召喚する
            if (name.startsWith("MM.")) {
                // MMのMOBの場合
//                name = name.substring(3);
//                // getMythicMob(String s)は見つからなければnullを返す
//                // と思ったけど，先に存在チェックをしてMythicMobインスタンスを渡してもtry-catchしないと怒られるので
//                // 存在チェックをせずにtry-catchする
//                try {
//                    MythicMobs.inst().getAPIHelper().spawnMythicMob(name,loc);
//                } catch (InvalidMobTypeException e) {
//                    sender.sendMessage(ChatColor.RED + prefix + name + "は存在しません．");
//                }
            } else {
                // バニラのMOBの場合
                EntityType type = EntityType.fromName(name);
                if (!Objects.isNull(type)) {
                    // テスト機能PosionEffectを付与してみる
                    Entity ent = world.spawnEntity(loc, type);
                    PotionEffect pe;
                    // 体力20UP
                    pe = new PotionEffect(PotionEffectType.HEALTH_BOOST, 3600, 19);
                    if(!pe.apply((LivingEntity) ent)) sender.sendMessage(ent.getName() + "に" + pe.getType().toString() + "付与失敗しました．");
                    // ダメージ上昇3
                    pe = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 2);
                    if(!pe.apply((LivingEntity) ent)) sender.sendMessage(ent.getName() + "に" + pe.getType().toString() + "付与失敗しました．");
                    // ダメージ耐性2
                    pe = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 1);
                    if(!pe.apply((LivingEntity) ent)) sender.sendMessage(ent.getName() + "に" + pe.getType().toString() + "付与失敗しました．");
                    // 火炎耐性
                    pe = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 0);
                    if(!pe.apply((LivingEntity) ent)) sender.sendMessage(ent.getName() + "に" + pe.getType().toString() + "付与失敗しました．");
                    sender.sendMessage(ChatColor.YELLOW + prefix + name + "をSpawnさせました．");
                } else sender.sendMessage(ChatColor.RED + prefix + name + "は存在しません．");
            }
            return true;
        }
        return false;
    }
}
