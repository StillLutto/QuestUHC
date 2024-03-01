package me.lutto.questuhc

import me.lutto.questuhc.commands.ArenaCommand
import me.lutto.questuhc.commands.tabcompleters.ArenaTabCompleter
import me.lutto.questuhc.listeners.ConnectListener
import me.lutto.questuhc.listeners.GameListener
import me.lutto.questuhc.manager.ArenaManager
import me.lutto.questuhc.manager.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class QuestUHC : JavaPlugin() {

    lateinit var arenaManager: ArenaManager

    override fun onEnable() {
        ConfigManager.setupConfig(this)
        arenaManager = ArenaManager(this)

        Bukkit.getPluginManager().registerEvents(ConnectListener(this), this)
        Bukkit.getPluginManager().registerEvents(GameListener(this), this)

        getCommand("arena")?.setExecutor(ArenaCommand(this))
        getCommand("arena")?.tabCompleter = ArenaTabCompleter()
    }

}
