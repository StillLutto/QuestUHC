package me.lutto.questuhc.manager

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.instance.Arena
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

class ArenaManager(questUHC: QuestUHC) {

    private val arenas: MutableList<Arena> = ArrayList()

    init {
        val config = questUHC.config
        for (arenaConfig in config.getConfigurationSection("arenas.")!!.getKeys(false)) {
            arenas.add(Arena(
                    questUHC, arenaConfig.toInt(),
                    Location(
                        Bukkit.getWorld(config.getString("arenas.$arenaConfig.world")!!),
                        config.getDouble("arenas.$arenaConfig.spawn-location.x"),
                        config.getDouble("arenas.$arenaConfig.spawn-location.y"),
                        config.getDouble("arenas.$arenaConfig.spawn-location.z"),
                        config.getDouble("arenas.$arenaConfig.spawn-location.yaw").toFloat(),
                        config.getDouble("arenas.$arenaConfig.spawn-location.pitch").toFloat()
                    ),
                    Location(
                        Bukkit.getWorld(config.getString("arenas.$arenaConfig.world")!!),
                        config.getDouble("arenas.$arenaConfig.first-corner.x"),
                        config.getDouble("arenas.$arenaConfig.first-corner.y"),
                        config.getDouble("arenas.$arenaConfig.first-corner.z")
                    ),
                    Location(
                        Bukkit.getWorld(config.getString("arenas.$arenaConfig.world")!!),
                        config.getDouble("arenas.$arenaConfig.second-corner.x"),
                        config.getDouble("arenas.$arenaConfig.second-corner.y"),
                        config.getDouble("arenas.$arenaConfig.second-corner.z")
                    )
            ))
        }
    }

    fun getArenas(): List<Arena> = arenas

    fun getArena(player: Player): Arena? {
        for (arena in arenas) {
            if (arena.getPlayers().contains(player.uniqueId)) {
                return arena
            }
        }

        return null
    }

    fun getArena(id: Int): Arena? {
        for (arena in arenas) {
            if (arena.getId() == id) {
                return arena
            }
        }

        return null
    }

}
