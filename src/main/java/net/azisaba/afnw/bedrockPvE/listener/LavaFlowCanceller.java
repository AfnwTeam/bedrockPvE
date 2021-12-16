package net.azisaba.afnw.bedrockPvE.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class LavaFlowCanceller implements Listener {
    @EventHandler
    public void onFlowing(BlockFromToEvent event) {
        if (event.getBlock().getWorld() == Bukkit.getServer().getWorld("bedrockpve") &&
            event.getBlock().getType() == Material.LAVA)
                event.setCancelled(true);
    }
}
