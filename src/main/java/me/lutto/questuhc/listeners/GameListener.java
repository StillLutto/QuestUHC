package me.lutto.questuhc.listeners;

import me.lutto.questuhc.QuestUHC;
import me.lutto.questuhc.enums.GameState;
import me.lutto.questuhc.instance.Arena;
import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class GameListener implements Listener {

    private QuestUHC questUHC;

    public GameListener(QuestUHC questUHC) {
        this.questUHC = questUHC;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity().getKiller() == null) return;
        if (!(event.getEntity() instanceof Animals)) return;

        Arena arena = questUHC.getArenaManager().getArena(event.getEntity().getKiller());
        if (arena == null) return;
        if (!arena.getState().equals(GameState.LIVE));

        arena.getGame().addPoint(event.getEntity().getKiller());
    }

}
