package me.chrisx97.mobhuntevent.tasks;

import me.chrisx97.mobhuntevent.game.GameState;
import me.chrisx97.mobhuntevent.managers.GameManager;
import me.chrisx97.mobhuntevent.utils.HexFormat;
import me.chrisx97.mobhuntevent.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class EventRunningTask extends BukkitRunnable {
    private int duration;
    private final int totalDuration;
    private final GameManager gameManager = GameManager.getInstance();

    public EventRunningTask(int duration) {
        this.duration = duration;
        totalDuration = duration;
    }

    @Override
    public void run() { //runs every second while a game is active
        gameManager.getBossBar().setProgress(duration/(double)totalDuration);
        gameManager.getBossBar().setTitle(HexFormat.format("&a&lMob Hunt: &b" + duration + " seconds remaining"));
        gameManager.updateBoards();

        switch (duration) {
            case 0:
                Bukkit.broadcastMessage(HexFormat.format(Messages.prefix + "&c>GAME OVER<"));
                cancel();
                gameManager.setState(GameState.ENDING);
                return;
            case 120:
                Bukkit.broadcastMessage(HexFormat.format(Messages.prefix + "&c2 minutes remaining"));
                break;
            case 60:
                Bukkit.broadcastMessage(HexFormat.format(Messages.prefix + "&c1 minute remaining"));
                break;
            case 45:
            case 30:
            case 15:
            case 10:
                Bukkit.broadcastMessage(HexFormat.format(Messages.prefix + "&c" + duration + " seconds remaining"));
                break;
        }
        if (duration <= 5) {
            Bukkit.broadcastMessage(HexFormat.format(Messages.prefix + "&c" + duration + " seconds remaining"));
        }
        duration--;
    }
}
