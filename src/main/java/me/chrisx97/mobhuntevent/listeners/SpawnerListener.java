package me.chrisx97.mobhuntevent.listeners;

import me.chrisx97.mobhuntevent.MobHuntEventPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class SpawnerListener implements Listener {
    @EventHandler
    void onMobSpawn(CreatureSpawnEvent e) {
        if (!(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER)) return;
        e.getEntity().setMetadata("SpawnerMob", new FixedMetadataValue(MobHuntEventPlugin.getPlugin(MobHuntEventPlugin.class), "1"));
    }
}
