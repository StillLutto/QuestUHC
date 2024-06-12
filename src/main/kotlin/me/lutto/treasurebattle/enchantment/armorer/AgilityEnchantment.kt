package me.lutto.treasurebattle.enchantment.armorer

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enchantment.CustomEnchantmentItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.event.EventHandler
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class AgilityEnchantment(private val treasureBattle: TreasureBattle) : CustomEnchantmentItem(
    "agility",
    "Agility",
    EnchantmentTarget.ARMOR_FEET) {

    init {
        treasureBattle.itemManager.registerEnchantmentItem(this)
    }

    @EventHandler
    fun onPlayerArmorChange(event: PlayerArmorChangeEvent) {
        if (treasureBattle.itemManager.isItem(event.oldItem, id)) {
            if (event.player.getPotionEffect(PotionEffectType.SPEED) != null && !event.player.getPotionEffect(PotionEffectType.SPEED)!!.isInfinite) return
            event.player.removePotionEffect(PotionEffectType.SPEED)
        }

        if (!treasureBattle.itemManager.isItem(event.newItem, id)) return
        event.player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1))
    }

    override fun getMaterial(): Material = Material.DIAMOND_BOOTS
    override fun getPairedEnchantment(): Enchantment = Enchantment.PROTECTION_ENVIRONMENTAL
    override fun getItemName(): Component = MiniMessage.miniMessage().deserialize("<gradient:#5890d1:#3ba6d1>ᴀɢɪʟᴇ ʙᴏᴏᴛѕ").decoration(TextDecoration.ITALIC, false)
    override fun getItemLore(): Component = MiniMessage.miniMessage().deserialize("Very speedy boots!").color(NamedTextColor.DARK_GRAY)

}