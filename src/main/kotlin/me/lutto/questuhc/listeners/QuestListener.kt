package me.lutto.questuhc.listeners

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent

class QuestListener(private val questUHC: QuestUHC) : Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity.killer == null && event.entity.killer !is Player) return
        val arena = questUHC.arenaManager.getArena(event.entity.killer!!) ?: return
        if (arena.getState() != GameState.LIVE) return

        val player: Player = event.entity.killer!!

        val playerQuest = questUHC.questManager.getPlayerQuests()[player.uniqueId] ?: return
        if (event.entity.type != playerQuest.first) return
        arena.getGame().addPoint(player)
    }

}