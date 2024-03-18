package me.lutto.treasurebattle.kit.type

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.kit.Kit
import me.lutto.treasurebattle.kit.KitType
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class MinerKit(treasureBattle: TreasureBattle, uuid: UUID) : Kit(treasureBattle, KitType.MINER, uuid) {

    override fun onStart(player: Player?) {
        if (player == null) return

        val mainItem = ItemStack(Material.IRON_PICKAXE)
        val mainItemMeta = mainItem.itemMeta
        mainItemMeta.addEnchant(Enchantment.DIG_SPEED, 2, false)
        mainItem.setItemMeta(mainItemMeta)

        val secondaryItem = ItemStack(Material.WATER_BUCKET)

        player.inventory.addItem(mainItem)
        player.inventory.addItem(secondaryItem)
    }

}
