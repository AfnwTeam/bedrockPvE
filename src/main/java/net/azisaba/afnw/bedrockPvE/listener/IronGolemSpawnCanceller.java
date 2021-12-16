package net.azisaba.afnw.bedrockPvE.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class IronGolemSpawnCanceller implements Listener {
    @EventHandler
    public void onBuild(CreatureSpawnEvent event) {
        if (event.getLocation().getWorld() == Bukkit.getServer().getWorld("bedrockpve") &&
            event.getSpawnReason()== CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)
                event.setCancelled(true);
    }
}
