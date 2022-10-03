package me.chrisx97.mobhuntevent.game;

import me.chrisx97.mobhuntevent.MobHuntEventPlugin;
import me.chrisx97.mobhuntevent.tasks.EventRunningTask;
import me.chrisx97.mobhuntevent.utils.HexFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class MobHuntGame {
    private final Map<UUID, Integer> playerList = new HashMap<>();
    private Map<UUID, Integer> sortedPlayerList = new HashMap<>();

    public MobHuntGame() {
        new EventRunningTask(120).runTaskTimer(MobHuntEventPlugin.getPlugin(MobHuntEventPlugin.class), 0, 20);
    }

    public void addPlayer(Player player) {
        playerList.put(player.getUniqueId(), 0);
    }

    public void addPoints(Player player, int points) {
        playerList.put(player.getUniqueId(), playerList.get(player.getUniqueId()) + points);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(HexFormat.format("&a&l+" + points + " points")));
        Bukkit.broadcastMessage(HexFormat.format("&e" + player.getName() + " &7has gained " + "&a" + points + " points"));
    }

    public int getPoints(UUID uuid) {
        return playerList.get(uuid);
    }

    public Map<UUID, Integer> getPlayerList() {
        return playerList;
    }

    public Map<UUID, Integer> getSortedPlayerList() {
        return sortedPlayerList;
    }

    public void setSortedPlayerList(Map<UUID, Integer> newSortedPlayerList) {
        this.sortedPlayerList = newSortedPlayerList;
    }
}
