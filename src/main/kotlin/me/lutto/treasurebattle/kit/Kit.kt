package me.lutto.treasurebattle.kit

import me.lutto.treasurebattle.TreasureBattle
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import java.util.*

abstract class Kit(treasureBattle: TreasureBattle, var type: KitType, var uuid: UUID) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, treasureBattle)
    }

    abstract fun onStart(player: Player?)

    fun remove() {
        HandlerList.unregisterAll(this)
    }

}
