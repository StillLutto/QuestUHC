package me.lutto.questuhc.kit

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class KitUI(player: Player) {
    init {
        val gui = Bukkit.createInventory(
            null,
            27,
            MiniMessage.miniMessage()
                .deserialize("<gradient:#1f9fb5:#1b8b9e>Kit Pi</gradient><gradient:#1b8b9e:#1f9fb5>cker</gradient>")
        )

        for (type in KitType.entries) {
            val item = ItemStack(type.material)
            val itemMeta = item.itemMeta
            itemMeta.displayName(Component.text(type.display))
            itemMeta.lore = listOf(type.description)
            itemMeta.setLocalizedName(type.name)
            item.setItemMeta(itemMeta)

            gui.addItem(item)
        }

        player.openInventory(gui)
    }
}
