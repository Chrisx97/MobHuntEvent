package me.chrisx97.mobhuntevent.listeners;

import me.chrisx97.mobhuntevent.MobHuntEventPlugin;
import me.chrisx97.mobhuntevent.managers.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MobHuntListener implements Listener {
    private GameManager gameManager;
    public MobHuntListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    @EventHandler
    void onMobKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        Player player = e.getEntity().getKiller();
        if (MobHuntEventPlugin.ignoreSpawnerMobs()) {
            if (e.getEntity().hasMetadata("SpawnerMob")) return;
        }
        if (MobHuntEventPlugin.getMobValues().containsKey(e.getEntityType())) {
            if (!gameManager.getGame().getPlayerList().containsKey(player.getUniqueId())) {
                gameManager.getGame().addPlayer(player);
            }
            gameManager.getGame().addPoints(player, MobHuntEventPlugin.getMobValues().get(e.getEntityType()));
        }
    }

    @EventHandler
    void onPlayerLeave(PlayerQuitEvent e) {
        if (gameManager.getState() != null) {
            gameManager.getBossBar().removePlayer(e.getPlayer());
        }
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        if (gameManager.getState() != null) {
            gameManager.getBossBar().addPlayer(e.getPlayer());
        }
    }

    public void cleanup() {
        EntityDeathEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
        gameManager = null;
    }
}
