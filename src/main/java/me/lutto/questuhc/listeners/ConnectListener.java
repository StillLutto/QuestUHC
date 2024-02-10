package me.lutto.questuhc.listeners;

import me.lutto.questuhc.QuestUHC;
import me.lutto.questuhc.instance.Arena;
import me.lutto.questuhc.manager.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private QuestUHC questUHC;

    public ConnectListener(QuestUHC questUHC) {
        this.questUHC = questUHC;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(ConfigManager.getLobbySpawn());
        event.getPlayer().setInvulnerable(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Arena arena = questUHC.getArenaManager().getArena(event.getPlayer());
        if (arena != null) {
            arena.removePlayer(event.getPlayer());
        };

    }

}
