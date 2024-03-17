package me.lutto.questuhc.listeners.items

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import me.lutto.questuhc.QuestUHC
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class AgilityEnchantmentListener(private val questUHC: QuestUHC) : Listener {

    @EventHandler
    fun onPlayerArmorChange(event: PlayerArmorChangeEvent) {
        val key = NamespacedKey(questUHC, "custom_enchantment")
        if (event.oldItem.itemMeta != null && event.oldItem.itemMeta.persistentDataContainer.has(key)) {
            event.player.removePotionEffect(PotionEffectType.SPEED)
            return
        }
        if (event.newItem.itemMeta != null && !event.newItem.itemMeta.persistentDataContainer.has(key)) return

        event.player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 1))
    }

}