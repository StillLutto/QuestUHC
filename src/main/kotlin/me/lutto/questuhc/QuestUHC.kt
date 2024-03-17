package me.lutto.questuhc

import io.github.cdimascio.dotenv.Dotenv
import me.lutto.questuhc.commands.ArenaCommand
import me.lutto.questuhc.commands.GiveItemCommand
import me.lutto.questuhc.commands.tabcompleters.ArenaTabCompleter
import me.lutto.questuhc.commands.tabcompleters.GiveItemTabCompleter
import me.lutto.questuhc.listeners.ConnectListener
import me.lutto.questuhc.listeners.GameListener
import me.lutto.questuhc.listeners.QuestListener
import me.lutto.questuhc.listeners.items.InstaFurnaceListener
import me.lutto.questuhc.manager.ArenaManager
import me.lutto.questuhc.manager.ConfigManager
import me.lutto.questuhc.manager.ItemManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.rmi.registry.Registry
import java.util.logging.Level

class QuestUHC : JavaPlugin() {

    lateinit var arenaManager: ArenaManager
    lateinit var itemManager: ItemManager
    lateinit var env: Dotenv

    override fun onEnable() {
        env = Dotenv.configure().load()
        setupLogger()

        ConfigManager.setupConfig(this)
        arenaManager = ArenaManager(this)
        itemManager = ItemManager(this)

        Bukkit.getPluginManager().registerEvents(ConnectListener(this), this)
        Bukkit.getPluginManager().registerEvents(GameListener(this), this)
        Bukkit.getPluginManager().registerEvents(QuestListener(this), this)
        Bukkit.getPluginManager().registerEvents(InstaFurnaceListener(this), this)

        getCommand("arena")?.setExecutor(ArenaCommand(this))
        getCommand("arena")?.tabCompleter = ArenaTabCompleter()
        getCommand("giveitem")?.setExecutor(GiveItemCommand(this))
        getCommand("giveitem")?.tabCompleter = GiveItemTabCompleter(this)
    }

    private fun setupLogger() {
        if (Level.parse(env["LOG_LEVEL"]) != null) {
            logger.level = Level.parse(env["LOG_LEVEL"])
        } else {
            logger.level = Level.INFO
        }
    }

}
