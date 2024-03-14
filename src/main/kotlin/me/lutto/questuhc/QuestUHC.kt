package me.lutto.questuhc

import io.github.cdimascio.dotenv.Dotenv
import me.lutto.questuhc.commands.ArenaCommand
import me.lutto.questuhc.commands.tabcompleters.ArenaTabCompleter
import me.lutto.questuhc.listeners.ConnectListener
import me.lutto.questuhc.listeners.GameListener
import me.lutto.questuhc.listeners.QuestListener
import me.lutto.questuhc.manager.ArenaManager
import me.lutto.questuhc.manager.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class QuestUHC : JavaPlugin() {

    lateinit var arenaManager: ArenaManager
    lateinit var env: Dotenv

    override fun onEnable() {
        env = Dotenv.configure().load()
        setupLogger()

        ConfigManager.setupConfig(this)
        arenaManager = ArenaManager(this)

        Bukkit.getPluginManager().registerEvents(ConnectListener(this), this)
        Bukkit.getPluginManager().registerEvents(GameListener(this), this)
        Bukkit.getPluginManager().registerEvents(QuestListener(this), this)

        getCommand("arena")?.setExecutor(ArenaCommand(this))
        getCommand("arena")?.tabCompleter = ArenaTabCompleter()
    }

    private fun setupLogger() {
        if (Level.parse(env["LOG_LEVEL"]) != null) {
            logger.level = Level.FINE
        } else {
            logger.level = Level.INFO
        }
    }

}
