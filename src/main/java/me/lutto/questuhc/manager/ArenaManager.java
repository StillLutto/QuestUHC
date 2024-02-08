package me.lutto.questuhc.manager;

import me.lutto.questuhc.instance.Arena;
import me.lutto.questuhc.QuestUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(QuestUHC questUHC) {

        FileConfiguration config = questUHC.getConfig();
        for (String arenaConfig : config.getConfigurationSection("arenas.").getKeys(false)) {
            arenas.add(new Arena(questUHC, Integer.parseInt(arenaConfig), new Location(
                    Bukkit.getWorld(config.getString("arenas." + arenaConfig + ".world")),
                    config.getDouble("arenas." + arenaConfig + ".x"),
                    config.getDouble("arenas." + arenaConfig + ".y"),
                    config.getDouble("arenas." + arenaConfig + ".z"),
                    (float) config.getDouble("arenas." + arenaConfig + ".yaw"),
                    (float) config.getDouble("arenas." + arenaConfig + ".pitch"))));
        }

    }

    public List<Arena> getArenas() { return arenas; }

    public Arena getArena(Player player) {

        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return arena;
            }
        }

        return null;

    };

    public Arena getArena(int id) {

        for (Arena arena : arenas) {
            if (arena.getId() == id) {
                return arena;
            }
        }

        return null;

    };

}
