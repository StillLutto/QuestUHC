package me.lutto.questuhc.manager

import me.lutto.questuhc.QuestUHC
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration

object ConfigManager {

    private lateinit var config: FileConfiguration

    fun setupConfig(questUHC: QuestUHC) {
        config = questUHC.config
        questUHC.saveDefaultConfig()
    }

    fun getMinRequiredPlayers(): Int = config.getInt("min-required-players")
    fun getMaxRequiredPlayers(): Int = config.getInt("max-required-players")

    fun getCountdownSeconds(): Int = config.getInt("countdown-seconds")

    fun getLobbySpawn(): Location =
        Location(
            Bukkit.getWorld(config.getString("lobby-spawn.world")!!),
            config.getDouble("lobby-spawn.x"),
            config.getDouble("lobby-spawn.y"),
            config.getDouble("lobby-spawn.z"),
            config.getDouble("lobby-spawn.yaw").toFloat(),
            config.getDouble("lobby-spawn.pitch").toFloat()
        )

}
