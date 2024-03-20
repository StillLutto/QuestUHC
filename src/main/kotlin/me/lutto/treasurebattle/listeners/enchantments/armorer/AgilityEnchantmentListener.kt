package me.lutto.treasurebattle.listeners.enchantments.armorer

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import me.lutto.treasurebattle.TreasureBattle
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class AgilityEnchantmentListener(private val treasureBattle: TreasureBattle) : Listener {

    @EventHandler
    fun onPlayerArmorChange(event: PlayerArmorChangeEvent) {
        val key = NamespacedKey(treasureBattle, "custom_enchantment")
        if (event.oldItem.itemMeta != null && event.oldItem.itemMeta.persistentDataContainer.has(key) && event.oldItem.itemMeta.persistentDataContainer[key, PersistentDataType.STRING] == "Agility") {
            event.player.removePotionEffect(PotionEffectType.SPEED)
            return
        }
        if (event.newItem.itemMeta == null) return
        if (!event.newItem.itemMeta.persistentDataContainer.has(key)) return
        if (event.newItem.itemMeta.persistentDataContainer[key, PersistentDataType.STRING] != "Agility") return

        event.player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 1))
    }

}