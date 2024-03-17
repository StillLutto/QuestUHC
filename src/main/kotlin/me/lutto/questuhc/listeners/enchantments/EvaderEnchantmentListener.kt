package me.lutto.questuhc.listeners.enchantments

import me.lutto.questuhc.QuestUHC
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.random.Random

class EvaderEnchantmentListener(private val questUHC: QuestUHC): Listener {

    private var abilityUsed: MutableSet<String> = mutableSetOf()

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) return
        val player: Player = event.entity as Player
        if (player.health > 4.0) return
        if (player.inventory.helmet == null) return
        val helmet: ItemStack = player.inventory.helmet!!

        val enchantmentKey = NamespacedKey(questUHC, "custom_enchantment")
        if (helmet.itemMeta != null && !helmet.itemMeta.persistentDataContainer.has(enchantmentKey)) return
        if (helmet.itemMeta.persistentDataContainer[enchantmentKey, PersistentDataType.STRING] != "Evader") return

        val uuidKey = NamespacedKey(questUHC, "uuid")
        if (abilityUsed.contains(helmet.itemMeta.persistentDataContainer[uuidKey, PersistentDataType.STRING])) return

        val radius = 10
        val randomX: Double = Random.nextInt(1, radius).toDouble() + player.x
        val randomZ: Double = Random.nextInt(1, radius).toDouble() + player.z
        val y: Int = player.world.getHighestBlockAt(randomX.toInt(), randomZ.toInt()).y
        val randomLocation = Location(player.world, randomX, y.toDouble() + 1, randomZ)
        player.teleportAsync(randomLocation)

        abilityUsed.add(helmet.itemMeta.persistentDataContainer[uuidKey, PersistentDataType.STRING] ?: return)
    }

}