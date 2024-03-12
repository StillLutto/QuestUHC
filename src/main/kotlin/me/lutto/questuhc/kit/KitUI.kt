package me.lutto.questuhc.kit

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class KitUI(player: Player) {
    init {
        val gui = Bukkit.createInventory(
            null,
            54,
            MiniMessage.miniMessage()
                .deserialize("<white>月月月月月月月月日")
        )

        val warriorType = KitType.WARRIOR
        val warriorItem = ItemStack(Material.MAP)
        val warriorMeta = warriorItem.itemMeta
        warriorMeta.displayName(Component.text(warriorType.display, NamedTextColor.GOLD))
        warriorMeta.setLocalizedName(warriorType.name)
        warriorMeta.lore = listOf(warriorType.description)
        warriorMeta.setCustomModelData(0)
        warriorItem.setItemMeta(warriorMeta)
        gui.setItem(19, warriorItem)
        gui.setItem(20, warriorItem)
        gui.setItem(28, warriorItem)
        gui.setItem(29, warriorItem)

        val archerType = KitType.ARCHER
        val archerItem = ItemStack(Material.MAP)
        val archerMeta = archerItem.itemMeta
        archerMeta.displayName(Component.text(archerType.display, NamedTextColor.AQUA))
        archerMeta.setLocalizedName(archerType.name)
        archerMeta.lore = listOf(archerType.description)
        archerMeta.setCustomModelData(0)
        archerItem.setItemMeta(archerMeta)
        gui.setItem(39, archerItem)
        gui.setItem(40, archerItem)
        gui.setItem(41, archerItem)
        gui.setItem(48, archerItem)
        gui.setItem(49, archerItem)
        gui.setItem(50, archerItem)

        val minerType = KitType.MINER
        val minerItem = ItemStack(Material.MAP)
        val minerMeta = minerItem.itemMeta
        minerMeta.displayName(Component.text(minerType.display, NamedTextColor.AQUA))
        minerMeta.setLocalizedName(minerType.name)
        minerMeta.lore = listOf(minerType.description)
        minerMeta.setCustomModelData(0)
        minerItem.setItemMeta(minerMeta)
        gui.setItem(24, minerItem)
        gui.setItem(25, minerItem)
        gui.setItem(33, minerItem)
        gui.setItem(34, minerItem)

        val exitItem = ItemStack(Material.BARRIER)
        val exitMeta = exitItem.itemMeta
        exitMeta.displayName(Component.text("Exit", NamedTextColor.RED))
        exitMeta.setLocalizedName("EXIT")
        exitMeta.setCustomModelData(0)
        exitItem.setItemMeta(exitMeta)
        gui.setItem(22, exitItem)

        player.openInventory(gui)
    }
}
