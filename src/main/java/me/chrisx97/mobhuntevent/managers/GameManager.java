package me.chrisx97.mobhuntevent.managers;

import me.chrisx97.mobhuntevent.MobHuntEventPlugin;
import me.chrisx97.mobhuntevent.fastboard.FastBoard;
import me.chrisx97.mobhuntevent.game.GameState;
import me.chrisx97.mobhuntevent.game.MobHuntGame;
import me.chrisx97.mobhuntevent.listeners.MobHuntListener;
import me.chrisx97.mobhuntevent.utils.HexFormat;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GameManager {
    private GameState state;
    private MobHuntGame game;

    private BossBar bossBar;

    private MobHuntListener mobHuntListener;

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    private static GameManager instance;
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void setState(GameState newState) {
        this.state = newState;
        switch (state) {
            case ACTIVE:
                mobHuntListener = new MobHuntListener(this);
                Bukkit.getServer().getPluginManager().registerEvents(mobHuntListener, MobHuntEventPlugin.getPlugin(MobHuntEventPlugin.class));
                bossBar = Bukkit.createBossBar(HexFormat.format("&aTesting bar"), BarColor.GREEN, BarStyle.SOLID);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    bossBar.addPlayer(player);
                    FastBoard board = new FastBoard(player);
                    board.updateTitle(HexFormat.format("&a&lMob Hunt"));
                }
                break;

            case ENDING:
                //remove the boss bar
                for (Player player : Bukkit.getOnlinePlayers()) bossBar.removePlayer(player);

                //some weird shit to sort the scores
                Map<UUID, Integer> sortedPlayers = game.getPlayerList().entrySet()
                        .stream().sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                game.setSortedPlayerList(sortedPlayers);

                //witchcraft to figure out the last entry in a hashmap...
                List<Map.Entry<UUID, Integer>> entryList = new ArrayList<>(game.getSortedPlayerList().entrySet());
                Map.Entry<UUID, Integer> lastEntry = entryList.get(entryList.size() - 1);

                Bukkit.broadcastMessage(HexFormat.format("&aThe winner of the &lMob Hunt&r &aevent was &b" + Bukkit
                        .getPlayer(lastEntry.getKey()).getName() + " &awith &e" + game
                        .getPoints(lastEntry.getKey()) + " points!"));

                game = null;
                state = null;
                bossBar = null;
                mobHuntListener.cleanup();
                mobHuntListener = null;
                break;
        }
    }

    public void startGame() {
        if (this.game == null) {
            this.game = new MobHuntGame();
        }
        setState(GameState.ACTIVE);

    }

    public MobHuntGame getGame() {
        return game;
    }

    public GameState getState() {
        return state;
    }

    public void updateBoards() {
        for (FastBoard fastBoard : boards.values()) {
            updateBoard(fastBoard);
        }
    }

    private void updateBoard(FastBoard board) {
        board.updateLines(
                "",
                HexFormat.format("&aPlayers: " + game.getPlayerList().size()),
                "",
                HexFormat.format("&ePoints: " + game.getPoints(board.getPlayer().getUniqueId()))
        );
    }

    public BossBar getBossBar() {return bossBar;}

    public Map<UUID, FastBoard> getBoards() {return boards;}
}
