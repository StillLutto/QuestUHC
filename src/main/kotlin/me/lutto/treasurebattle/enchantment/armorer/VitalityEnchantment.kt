package me.lutto.treasurebattle.enchantment.armorer

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
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
import org.bukkit.event.EventHandler

class VitalityEnchantment(private val treasureBattle: TreasureBattle) : CustomEnchantmentItem(
    "vitality",
    "Vitality",
    EnchantmentTarget.ARMOR_TORSO) {

    init {
        treasureBattle.itemManager.registerEnchantmentItem(this)
    }

    @EventHandler
    fun onPlayerArmorChange(event: PlayerArmorChangeEvent) {
        if (treasureBattle.itemManager.isItem(event.oldItem, this.id)) {
            event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = 20.0
            return
        }

        if (!treasureBattle.itemManager.isItem(event.newItem, this.id)) return
        event.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = 22.0
    }

    override fun getMaterial(): Material = Material.DIAMOND_CHESTPLATE
    override fun getPairedEnchantment(): Enchantment = Enchantment.PROTECTION_ENVIRONMENTAL
    override fun getItemName(): Component = MiniMessage.miniMessage().deserialize("<gradient:#822f2c:#b52d00>ᴠɪᴛᴀʟɪᴛʏ ᴄʜᴇѕᴛᴘʟᴀᴛᴇ").decoration(TextDecoration.ITALIC, false)
    override fun getItemLore(): Component = MiniMessage.miniMessage().deserialize("Heart of Steel").color(NamedTextColor.DARK_GRAY)

}