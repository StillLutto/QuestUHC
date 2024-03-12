package me.lutto.questuhc.kit

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

enum class KitType(val display: String, val description: String) {

    WARRIOR(Component.text("Warrior", NamedTextColor.GOLD).content(), "Fight like a warrior!"),
    MINER(Component.text("Miner", NamedTextColor.GOLD).content(), "Get ready for battle!"),
    ARCHER(Component.text("Archer", NamedTextColor.GOLD).content(), "Snipe for afar!")

}