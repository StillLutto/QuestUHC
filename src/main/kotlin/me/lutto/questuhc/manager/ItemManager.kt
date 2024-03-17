package me.lutto.questuhc.manager

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.instance.CustomItem
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.persistence.PersistentDataType
import java.util.*

class ItemManager(private val questUHC: QuestUHC) {

    private var itemList: MutableList<CustomItem> = mutableListOf()

    init {
        createInstaFurnace()
        createAgilityBoots()
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

        val key = NamespacedKey(questUHC, "custom_enchantment")
        meta.persistentDataContainer.set(key, PersistentDataType.STRING, "Agility")

        meta.displayName(MiniMessage.miniMessage().deserialize("<aqua>Agility Boots").decoration(TextDecoration.ITALIC, false))
        meta.lore(Arrays.asList(MiniMessage.miniMessage().deserialize("<gray>${meta.persistentDataContainer.get(key, PersistentDataType.STRING) ?: return}").decoration(TextDecoration.ITALIC, false)))
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)
        item.setItemMeta(meta)

        val customItem = CustomItem("agility_boots", item)
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
