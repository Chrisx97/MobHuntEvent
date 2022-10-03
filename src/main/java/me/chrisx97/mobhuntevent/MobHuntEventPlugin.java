package me.chrisx97.mobhuntevent;

import me.chrisx97.mobhuntevent.commands.MobHuntCommand;
import me.chrisx97.mobhuntevent.listeners.SpawnerListener;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class MobHuntEventPlugin extends JavaPlugin
{
    private static final Map<EntityType, Integer> mobValues = new HashMap<>();
    private static boolean ignoreSpawners = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        for (String key : getConfig().getConfigurationSection("Mobs").getKeys(false)) {
            try {
                mobValues.put(EntityType.valueOf(key), getConfig().getInt("Mobs." + key));
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        ignoreSpawners = getConfig().getBoolean("ignore-spawner-mobs");
        if (ignoreSpawners) getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getCommand("mobhunt").setExecutor(new MobHuntCommand());
    }

    public static boolean ignoreSpawnerMobs() {return ignoreSpawners;}

    public static Map<EntityType, Integer> getMobValues() {return mobValues;}
}
