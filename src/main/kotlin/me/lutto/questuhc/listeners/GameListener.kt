package me.lutto.questuhc.listeners

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import me.lutto.questuhc.instance.Arena
import me.lutto.questuhc.kit.KitType
import me.lutto.questuhc.kit.KitUI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent

class GameListener(private val questUHC: QuestUHC) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {

        if ((event.item?.itemMeta?.localizedName ?: return) != "KitsItem") return
        KitUI(event.player)

    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        val player: Player = event.whoClicked as Player

        if (event.view.title() == MiniMessage.miniMessage()
            .deserialize("<white>月月月月月月月月日")
            && event.currentItem != null
            ) {
            event.isCancelled = true

            if (event.currentItem!!.itemMeta.localizedName == "EXIT") {
                player.closeInventory()
                return
            }

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
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val arena = questUHC.arenaManager.getArena(event.player) ?: return
        if (arena.getState() != GameState.LIVE) return

        val player: Player = event.player
        player.gameMode = GameMode.SPECTATOR
        val playerDeathLocation = Location(player.world, player.x, player.y, player.z)
        player.setRespawnLocation(playerDeathLocation, true)

        arena.removePlayer(event.player)
    }

}
