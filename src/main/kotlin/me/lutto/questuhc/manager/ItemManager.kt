package me.lutto.questuhc.manager

import me.lutto.questuhc.instance.CustomItem
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

class ItemManager {

    private var itemList: MutableList<CustomItem> = mutableListOf()

    init {
        createInstaFurnace()
    }

    private fun createInstaFurnace() {
        val item = ItemStack(Material.FURNACE, 1)
        val meta = item.itemMeta
        meta.displayName(MiniMessage.miniMessage().deserialize("<obfuscated>A</obfuscated> <gray>InstaFurnace<white> <obfuscated>A"))
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
