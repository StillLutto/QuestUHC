package me.lutto.treasurebattle.listeners.enchantments.warrior

import me.lutto.treasurebattle.TreasureBattle
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.random.Random

class VampiricEnchantmentListener(val treasureBattle: TreasureBattle) : Listener {

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player) return
        if (event.entity !is Player) return
        val player: Player = event.damager as Player
        val sword: ItemStack = if (player.inventory.itemInMainHand.type == Material.DIAMOND_SWORD) player.inventory.itemInMainHand else return

        val enchantmentKey = NamespacedKey(treasureBattle, "custom_enchantment")
        if (sword.itemMeta != null && !sword.itemMeta.persistentDataContainer.has(enchantmentKey)) return
        if (sword.itemMeta.persistentDataContainer[enchantmentKey, PersistentDataType.STRING] != "Vampiric") return

        val randomInt: Int = Random.nextInt(1, 100)
        if (randomInt < 90) return

        if ((player.health + event.finalDamage) > player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value) {
            player.health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
            return
        }
        player.health += event.finalDamage
    }

}