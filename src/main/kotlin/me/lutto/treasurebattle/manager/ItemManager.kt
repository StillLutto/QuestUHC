package me.lutto.treasurebattle.manager

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.instance.CustomItem
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.persistence.PersistentDataType

class ItemManager(private val treasureBattle: TreasureBattle) {

    private var itemList: MutableList<CustomItem> = mutableListOf()

    init {
        createInstaFurnace()

        createEnchantmentItem(
            "agility_boots",
            Material.DIAMOND_BOOTS,
            Enchantment.PROTECTION_ENVIRONMENTAL,
            1,
            "Agility",
            "Agility Boots"
        )
        createEnchantmentItem(
            "vitality_chestplate",
            Material.DIAMOND_CHESTPLATE,
            Enchantment.PROTECTION_ENVIRONMENTAL,
            1,
            "Vitality",
            "Vitality Chestplate"
        )
        createEnchantmentItem(
            "evader_helmet",
            Material.DIAMOND_HELMET,
            Enchantment.PROTECTION_ENVIRONMENTAL,
            1,
            "Evader",
            "Evader Helmet"
        )
        createEnchantmentItem(
            "boomstrike_shield",
            Material.SHIELD,
            Enchantment.DURABILITY,
            3,
            "Boomstrike",
            "Boomstrike Shield"
        )
    }

    private fun createEnchantmentItem(
        itemId: String,
        material: Material,
        otherEnchantment: Enchantment,
        enchantmentAmount: Int,
        enchantmentName: String,
        itemDisplayName: String
    ) {
        val item = ItemStack(material, 1)
        val itemMeta = item.itemMeta

        val key = NamespacedKey(treasureBattle, "custom_enchantment")
        itemMeta.persistentDataContainer[key, PersistentDataType.STRING] = enchantmentName

        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<aqua>$itemDisplayName").decoration(TextDecoration.ITALIC, false))
        itemMeta.lore(listOf(MiniMessage.miniMessage().deserialize("<gray>${itemMeta.persistentDataContainer[key, PersistentDataType.STRING] ?: return}").decoration(TextDecoration.ITALIC, false)))
        itemMeta.addEnchant(otherEnchantment, enchantmentAmount, false)
        item.setItemMeta(itemMeta)

        val customItem = CustomItem(itemId, item)
        itemList.add(customItem)
    }

    private fun createInstaFurnace() {
        val item = ItemStack(Material.FURNACE, 1)
        val meta = item.itemMeta
        meta.displayName(MiniMessage.miniMessage().deserialize("<obfuscated>A</obfuscated> <gold>InstaFurnace <white><obfuscated>A"))
        item.setItemMeta(meta)

        val customItem = CustomItem("insta_furnace", item)
        itemList.add(customItem)

        val shapedRecipe = ShapedRecipe(NamespacedKey.minecraft(customItem.getId()), item)
        if (Bukkit.getServer().getRecipe(NamespacedKey.minecraft(customItem.getId())) != null) {
            Bukkit.getServer().removeRecipe(NamespacedKey.minecraft(customItem.getId()))
        }
        shapedRecipe.shape(
            "SSS",
            "SCS",
            "SSS"
        )
        shapedRecipe.setIngredient('S', Material.COBBLESTONE)
        shapedRecipe.setIngredient('C', Material.COAL)
        Bukkit.getServer().addRecipe(shapedRecipe)
    }

    fun getItem(id: String): CustomItem? {
        for (customItem in itemList) {
            if (customItem.getId() == id) return customItem
        }
        return null
    }

    fun getItemList(): MutableList<CustomItem> = itemList

}
