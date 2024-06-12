package me.lutto.treasurebattle.enchantment.warrior

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enchantment.CustomEnchantmentItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class VampiricEnchantment(private val treasureBattle: TreasureBattle) : CustomEnchantmentItem(
    "vampiric",
    "Vampiric",
    EnchantmentTarget.WEAPON) {

    init {
        treasureBattle.itemManager.registerEnchantmentItem(this)
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player) return
        if (event.entity !is Player) return
        val player: Player = event.damager as Player
        val sword: ItemStack = player.inventory.itemInMainHand

        if (!treasureBattle.itemManager.isItem(sword, this.id)) return

        val randomInt: Int = Random.nextInt(1, 100)
        if (randomInt < 85) return

        if ((player.health + event.finalDamage) > player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value) {
            player.health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
            return
        }
        player.health += event.finalDamage
    }

    override fun getMaterial(): Material = Material.DIAMOND_SWORD
    override fun getPairedEnchantment(): Enchantment = Enchantment.DAMAGE_ALL
    override fun getItemName(): Component = MiniMessage.miniMessage().deserialize("<gradient:#5b00a1:#8800f0>ᴠᴀᴍᴘɪʀɪᴄ ʙʟᴀᴅᴇ").decoration(TextDecoration.ITALIC, false)
    override fun getItemLore(): Component = MiniMessage.miniMessage().deserialize("Life leech!").color(NamedTextColor.DARK_GRAY)

}