package me.lutto.questuhc.listeners.items

import me.lutto.questuhc.QuestUHC
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.FurnaceStartSmeltEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class InstaFurnaceListener(val questUHC: QuestUHC) : Listener {

    private var isFurnace: Boolean = false

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.view.title() != questUHC.itemManager.getItem("insta_furnace")!!.getItemStack().displayName()) return
        isFurnace = true
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.view.title() != questUHC.itemManager.getItem("insta_furnace")!!.getItemStack().displayName()) return
        isFurnace = false
    }

    @EventHandler
    fun on(event: FurnaceStartSmeltEvent) {
        event.totalCookTime = 1
    }

}