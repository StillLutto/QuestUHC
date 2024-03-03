package me.lutto.questuhc.kit

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

enum class KitType(val display: String, val material: Material, val description: String) {

    WARRIOR(Component.text("Warrior", NamedTextColor.GOLD).content(), Material.DIAMOND_SWORD, "Fight like a warrior!"),
    MINER(Component.text("Miner", NamedTextColor.GOLD).content(), Material.DIAMOND_PICKAXE, "Get ready for battle!"),
    ARCHER(Component.text("Archer", NamedTextColor.GOLD).content(), Material.BOW, "Snipe for afar!")

}