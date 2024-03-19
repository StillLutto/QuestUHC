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
import java.util.UUID

class ItemManager(private val treasureBattle: TreasureBattle) {

    private var itemList: MutableList<CustomItem> = mutableListOf()

    init {
        createInstaFurnace()
        createAgilityBoots()
        createVilalityChestplate()
        createEvaderShield()
        createBoomstrikeShield()
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

    private fun createAgilityBoots() {
        val item = ItemStack(Material.DIAMOND_BOOTS, 1)
        val meta = item.itemMeta

        val key = NamespacedKey(treasureBattle, "custom_enchantment")
        meta.persistentDataContainer[key, PersistentDataType.STRING] = "Agility"

        meta.displayName(MiniMessage.miniMessage().deserialize("<aqua>Agility Boots").decoration(TextDecoration.ITALIC, false))
        meta.lore(listOf(MiniMessage.miniMessage().deserialize("<gray>${meta.persistentDataContainer[key, PersistentDataType.STRING] ?: return}").decoration(TextDecoration.ITALIC, false)))
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)
        item.setItemMeta(meta)

        val customItem = CustomItem("agility_boots", item)
        itemList.add(customItem)
    }

    private fun createVilalityChestplate() {
        val item = ItemStack(Material.DIAMOND_CHESTPLATE, 1)
        val meta = item.itemMeta

        val key = NamespacedKey(treasureBattle, "custom_enchantment")
        meta.persistentDataContainer[key, PersistentDataType.STRING] = "Vitality"

        meta.displayName(MiniMessage.miniMessage().deserialize("<aqua>Vitality Chestplate").decoration(TextDecoration.ITALIC, false))
        meta.lore(listOf(MiniMessage.miniMessage().deserialize("<gray>${meta.persistentDataContainer[key, PersistentDataType.STRING] ?: return}").decoration(TextDecoration.ITALIC, false)))
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)
        item.setItemMeta(meta)

        val customItem = CustomItem("vitality_chestplate", item)
        itemList.add(customItem)
    }

    private fun createEvaderShield() {
        val item = ItemStack(Material.SHIELD, 1)
        val meta = item.itemMeta

        val enchantmentKey = NamespacedKey(treasureBattle, "custom_enchantment")
        meta.persistentDataContainer[enchantmentKey, PersistentDataType.STRING] = "Boomstrike"

        val uuidKey = NamespacedKey(treasureBattle, "uuid")
        meta.persistentDataContainer[uuidKey, PersistentDataType.STRING] = UUID.randomUUID().toString()

        meta.displayName(MiniMessage.miniMessage().deserialize("<aqua>Boomstrike Shield").decoration(TextDecoration.ITALIC, false))
        meta.lore(listOf(MiniMessage.miniMessage().deserialize("<gray>${meta.persistentDataContainer[enchantmentKey, PersistentDataType.STRING] ?: return}").decoration(TextDecoration.ITALIC, false)))
        meta.addEnchant(Enchantment.DURABILITY, 3, false)
        item.setItemMeta(meta)

        val customItem = CustomItem("boomstrike_shield", item)
        itemList.add(customItem)
    }

    private fun createBoomstrikeShield() {
        val item = ItemStack(Material.DIAMOND_HELMET, 1)
        val meta = item.itemMeta

        val enchantmentKey = NamespacedKey(treasureBattle, "custom_enchantment")
        meta.persistentDataContainer[enchantmentKey, PersistentDataType.STRING] = "Evader"

        val uuidKey = NamespacedKey(treasureBattle, "uuid")
        meta.persistentDataContainer[uuidKey, PersistentDataType.STRING] = UUID.randomUUID().toString()

        meta.displayName(MiniMessage.miniMessage().deserialize("<aqua>Evader Helmet").decoration(TextDecoration.ITALIC, false))
        meta.lore(listOf(MiniMessage.miniMessage().deserialize("<gray>${meta.persistentDataContainer[enchantmentKey, PersistentDataType.STRING] ?: return}").decoration(TextDecoration.ITALIC, false)))
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)
        item.setItemMeta(meta)

        val customItem = CustomItem("evader_helmet", item)
        itemList.add(customItem)
    }

    fun getItem(id: String): CustomItem? {
        for (customItem in itemList) {
            if (customItem.getId() == id) return customItem
        }
        return null
    }

    fun getItemList(): MutableList<CustomItem> = itemList

}
