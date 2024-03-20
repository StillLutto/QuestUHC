package me.lutto.treasurebattle.listeners.enchantments.armorer

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import me.lutto.treasurebattle.TreasureBattle
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.persistence.PersistentDataType

class VitalityEnchantmentListener(private val treasureBattle: TreasureBattle) : Listener {

    @EventHandler
    fun onPlayerArmorChange(event: PlayerArmorChangeEvent) {
        val key = NamespacedKey(treasureBattle, "custom_enchantment")
        if (event.oldItem.itemMeta != null && event.oldItem.itemMeta.persistentDataContainer.has(key) && event.oldItem.itemMeta.persistentDataContainer[key, PersistentDataType.STRING] == "Vitality") {
            event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = 20.0
            return
        }
        if (event.newItem.itemMeta != null && !event.newItem.itemMeta.persistentDataContainer.has(key) && event.oldItem.itemMeta.persistentDataContainer[key, PersistentDataType.STRING] == "Vitality") return

        event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = 22.0
    }

}