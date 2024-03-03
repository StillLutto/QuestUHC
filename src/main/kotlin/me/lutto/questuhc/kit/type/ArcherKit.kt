package me.lutto.questuhc.kit.type

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.kit.Kit
import me.lutto.questuhc.kit.KitType
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class ArcherKit(questUHC: QuestUHC, uuid: UUID) : Kit(questUHC, KitType.WARRIOR, uuid) {

    override fun onStart(player: Player?) {
        if (player == null) return

        val mainItem = ItemStack(Material.BOW)
        val mainItemMeta = mainItem.itemMeta
        mainItemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, false)
        mainItem.setItemMeta(mainItemMeta)

        val secondaryItem = ItemStack(Material.ARROW, 64)

        player.inventory.addItem(mainItem)
        player.inventory.addItem(secondaryItem)
    }

}
