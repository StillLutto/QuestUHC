package me.lutto.questuhc.kit.type

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.kit.Kit
import me.lutto.questuhc.kit.KitType
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

class WarriorKit(questUHC: QuestUHC, uuid: UUID) : Kit(questUHC, KitType.WARRIOR, uuid) {

    override fun onStart(player: Player?) {
        if (player == null) return

        val mainItem = ItemStack(Material.IRON_SWORD)
        val mainItemMeta = mainItem.itemMeta
        mainItemMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, false)
        mainItem.setItemMeta(mainItemMeta)

        val secondaryItem = ItemStack(Material.STONE_AXE)

        player.inventory.addItem(mainItem)
        player.inventory.addItem(secondaryItem)

        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 100, 1, false))
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {

        if (event.player.killer == null) return
        val player = event.player.killer
        if (uuid != player!!.uniqueId) return

        player.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, 1200, 0))
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 100, 0))
    }

}
