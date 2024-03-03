package me.lutto.questuhc.listeners

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import me.lutto.questuhc.instance.Arena
import me.lutto.questuhc.kit.KitType
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Animals
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent

class GameListener(private val questUHC: QuestUHC) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        val player: Player = event.whoClicked as Player

        if (event.view.title() == MiniMessage.miniMessage()
            .deserialize("<gradient:#1f9fb5:#1b8b9e>Kit Pi</gradient><gradient:#1b8b9e:#1f9fb5>cker</gradient>")
            && event.currentItem != null
            ) {
            event.isCancelled = true
            println("after")

            val type: KitType = KitType.valueOf(event.currentItem!!.itemMeta.localizedName)

            val arena: Arena? = questUHC.arenaManager.getArena(player)
            val activatedType: KitType? = arena?.getKit(player)
            if (activatedType != null && activatedType == type) {
                player.sendRichMessage("<red>You already have this kit equipped!")
            } else {
                arena?.setKit(player.uniqueId, type)
                player.sendRichMessage("<green>You have equipped the ${type.display} kit!")
                player.closeInventory()
            }
        }

    }

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
