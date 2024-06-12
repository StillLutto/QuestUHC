package me.lutto.treasurebattle.enchantment.armorer

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enchantment.CustomEnchantmentItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import kotlin.random.Random

class EvaderEnchantment(private val treasureBattle: TreasureBattle) : CustomEnchantmentItem(
    "evader",
    "Evader",
    EnchantmentTarget.ARMOR_HEAD) {

    private var abilityUsed: MutableSet<String> = mutableSetOf()

    init {
        treasureBattle.itemManager.registerEnchantmentItem(this)
    }

    private fun teleportPlayer(player: Player) {
        val radius = 15

        var randomX: Double = Random.nextInt(1, radius).toDouble() + player.x
        var randomZ: Double = Random.nextInt(1, radius).toDouble() + player.z
        var y: Int = player.world.getHighestBlockAt(randomX.toInt(), randomZ.toInt()).y

        var tries = 1
        while (player.world.getHighestBlockAt(randomX.toInt(), randomZ.toInt()).isEmpty) {
            if (tries == 100) {
                player.sendRichMessage("<red>Could not find a block to teleport you to!")
                return
            }
            randomX = Random.nextInt(1, radius).toDouble() + player.x
            randomZ = Random.nextInt(1, radius).toDouble() + player.z
            y = player.world.getHighestBlockAt(randomX.toInt(), randomZ.toInt()).y
            tries++
        }

        val randomLocation = Location(player.world, randomX, y.toDouble() + 1, randomZ, player.yaw, player.pitch)
        player.teleport(randomLocation)
        player.sendRichMessage("<gold>Your evader's ability has been used!")
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) return
        val player: Player = event.entity as Player
        if (player.health > 4.0) return
        val helmet: ItemStack = player.inventory.helmet ?: return
        val helmetMeta: ItemMeta = helmet.itemMeta

        if (!treasureBattle.itemManager.isItem(helmet, this.id)) return

        val isUsedKey = NamespacedKey(treasureBattle, "isUsed")
        if (!helmetMeta.persistentDataContainer.has(isUsedKey)) helmetMeta.persistentDataContainer[isUsedKey, PersistentDataType.BOOLEAN] = false
        if (helmetMeta.persistentDataContainer[isUsedKey, PersistentDataType.BOOLEAN] == true) return

        teleportPlayer(player)
        helmetMeta.persistentDataContainer[isUsedKey, PersistentDataType.BOOLEAN] = true
        helmet.itemMeta = helmetMeta
    }

    override fun getMaterial(): Material = Material.DIAMOND_HELMET
    override fun getPairedEnchantment(): Enchantment = Enchantment.PROTECTION_ENVIRONMENTAL
    override fun getItemName(): Component = MiniMessage.miniMessage().deserialize("<gradient:#8d00c9:#d900ff>ᴇᴠᴀᴅᴇʀ'ѕ ʜᴇʟᴍᴇᴛ").decoration(TextDecoration.ITALIC, false)
    override fun getItemLore(): Component = MiniMessage.miniMessage().deserialize("Swift retreat!").color(NamedTextColor.DARK_GRAY)

}