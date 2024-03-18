package me.lutto.treasurebattle.listeners

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.manager.ConfigManager
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectListener(private val treasureBattle: TreasureBattle) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.teleport(ConfigManager.getLobbySpawn())
        event.player.isInvulnerable = true
        event.player.gameMode = GameMode.ADVENTURE
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val arena = treasureBattle.arenaManager.getArena(event.player)
        arena?.removePlayer(event.player)
    }

}
