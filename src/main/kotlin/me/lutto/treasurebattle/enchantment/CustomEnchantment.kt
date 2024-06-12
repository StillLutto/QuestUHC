package me.lutto.treasurebattle.enchantment

import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.event.Listener

open class CustomEnchantment(val id: String,
                             val name: String,
                             val enchantmentTarget: EnchantmentTarget) : Listener {

    fun minLevel() = 1
    fun maxLevel() = 3

}