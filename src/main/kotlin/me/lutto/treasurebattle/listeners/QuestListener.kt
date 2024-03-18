package me.lutto.treasurebattle.listeners

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enums.GameState
import me.lutto.treasurebattle.enums.quests.QuestType
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

class QuestListener(private val treasureBattle: TreasureBattle) : Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity.killer == null && event.entity.killer !is Player) return

        val player: Player = event.entity.killer!!
        val arena = treasureBattle.arenaManager.getArena(event.entity.killer!!) ?: return
        val playerQuest = arena.getQuests().getPlayerQuest(player.uniqueId)

        if (arena.getState() != GameState.LIVE) return
        if ((playerQuest?.type ?: return) != QuestType.KILL) return
        if (event.entity.type != EntityType.valueOf(playerQuest.name)) return

        arena.getGame().addPoint(player)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val arena = treasureBattle.arenaManager.getArena(event.player) ?: return
        val playerQuest = arena.getQuests().getPlayerQuest(event.player.uniqueId) ?: return

        if (arena.getState() != GameState.LIVE) return
        if (event.block.type != Material.valueOf(playerQuest.name)) return

        arena.getGame().addPoint(event.player)
    }

    @EventHandler
    fun onPlayerEat(event: PlayerItemConsumeEvent) {
        val arena = treasureBattle.arenaManager.getArena(event.player) ?: return
        val playerQuest = arena.getQuests().getPlayerQuest(event.player.uniqueId) ?: return

        if (arena.getState() != GameState.LIVE) return
        if (event.item.type != Material.valueOf(playerQuest.name)) return

        arena.getGame().addPoint(event.player)
    }

    @EventHandler
    fun onPlayerCraftPrepare(event: CraftItemEvent) {
        if (event.view.player !is Player) return
        val player: Player = event.view.player as Player

        val arena = treasureBattle.arenaManager.getArena(player) ?: return
        val playerQuest = arena.getQuests().getPlayerQuest(player.uniqueId) ?: return

        if (arena.getState() != GameState.LIVE) return
        if (event.recipe.result.type != Material.valueOf(playerQuest.name)) return

        arena.getGame().addPoint(player)
    }

}