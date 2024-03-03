package me.lutto.questuhc.kit

import me.lutto.questuhc.QuestUHC
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import java.util.*

abstract class Kit(questUHC: QuestUHC, var type: KitType, var uuid: UUID) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, questUHC)
    }

    abstract fun onStart(player: Player?)

    fun remove() {
        HandlerList.unregisterAll(this)
    }

}
