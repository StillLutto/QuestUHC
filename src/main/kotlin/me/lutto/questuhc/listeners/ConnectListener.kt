package me.lutto.questuhc.listeners

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.manager.ConfigManager
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectListener(private val questUHC: QuestUHC) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.teleport(ConfigManager.getLobbySpawn())
        event.player.isInvulnerable = true
        event.player.gameMode = GameMode.ADVENTURE
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val arena = questUHC.arenaManager.getArena(event.player)
        arena?.removePlayer(event.player)
    }

}
