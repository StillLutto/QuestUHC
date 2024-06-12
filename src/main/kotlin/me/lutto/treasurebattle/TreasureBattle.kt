package me.lutto.treasurebattle

import io.github.cdimascio.dotenv.Dotenv
import me.lutto.treasurebattle.commands.ArenaCommand
import me.lutto.treasurebattle.commands.GiveItemCommand
import me.lutto.treasurebattle.commands.tabcompleters.ArenaTabCompleter
import me.lutto.treasurebattle.commands.tabcompleters.GiveItemTabCompleter
import me.lutto.treasurebattle.enchantment.armorer.*
import me.lutto.treasurebattle.enchantment.warrior.*
import me.lutto.treasurebattle.listeners.ConnectListener
import me.lutto.treasurebattle.listeners.GameListener
import me.lutto.treasurebattle.listeners.QuestListener
import me.lutto.treasurebattle.listeners.items.InstaFurnaceListener
import me.lutto.treasurebattle.manager.ArenaManager
import me.lutto.treasurebattle.manager.ConfigManager
import me.lutto.treasurebattle.manager.ItemManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class TreasureBattle : JavaPlugin() {

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

        // armorer enchantment listeners
        Bukkit.getPluginManager().registerEvents(VampiricEnchantment(this), this)

        // armorer enchantment listeners
        Bukkit.getPluginManager().registerEvents(AgilityEnchantment(this), this)
        Bukkit.getPluginManager().registerEvents(VitalityEnchantment(this), this)
        Bukkit.getPluginManager().registerEvents(EvaderEnchantment(this), this)
        Bukkit.getPluginManager().registerEvents(BoomstrikeEnchantment(this), this)

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
