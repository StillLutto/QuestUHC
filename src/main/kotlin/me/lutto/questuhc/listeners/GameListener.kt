package me.lutto.questuhc.listeners

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import org.bukkit.entity.Animals
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent

class GameListener(private val questUHC: QuestUHC) : Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity.killer == null) return
        if (event.entity !is Animals) return

        val arena = questUHC.arenaManager.getArena(event.entity.killer!!) ?: return
        if (arena.getState() != GameState.LIVE) return

        arena.getGame().addPoint(event.entity.killer!!)
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val arena = questUHC.arenaManager.getArena(event.entity) ?: return
        if (arena.getState() != GameState.LIVE) return

        arena.removePlayer(event.entity)
    }

}
