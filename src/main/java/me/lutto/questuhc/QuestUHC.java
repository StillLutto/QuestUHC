package me.lutto.questuhc;

import me.lutto.questuhc.commands.ArenaCommand;
import me.lutto.questuhc.commands.tabcompleters.ArenaTabCompleter;
import me.lutto.questuhc.listeners.ConnectListener;
import me.lutto.questuhc.listeners.GameListener;
import me.lutto.questuhc.manager.ArenaManager;
import me.lutto.questuhc.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuestUHC extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);

        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getCommand("arena").setExecutor(new ArenaCommand(this));
        getCommand("arena").setTabCompleter(new ArenaTabCompleter());

    }

    public ArenaManager getArenaManager() { return arenaManager; }

}
