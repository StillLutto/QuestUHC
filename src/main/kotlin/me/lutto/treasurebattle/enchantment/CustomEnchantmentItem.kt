package me.lutto.treasurebattle.enchantment

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget

abstract class CustomEnchantmentItem(id: String, name: String, enchantmentTarget: EnchantmentTarget) : CustomEnchantment(id, name, enchantmentTarget) {

    abstract fun getMaterial(): Material
    abstract fun getPairedEnchantment(): Enchantment
    fun getPairedEnchantmentLevel(): Int = 1
    abstract fun getItemName(): Component
    abstract fun getItemLore(): Component

}