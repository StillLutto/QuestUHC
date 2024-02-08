package me.lutto.questuhc.instance;

import me.lutto.questuhc.enums.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendTitle(ChatColor.GREEN + "Game has started!", "Your objective is to kill 4 animals and you will get a secret weapon!");
        arena.sendMessage(ChatColor.GREEN + "Game has started!\n" + ChatColor.GRAY + "Your objective is to kill 4 animals and you will get a secret weapon!");

        for (UUID uuid : arena.getPlayers()) {
            points.put(uuid, 0);
        }
    }

    public void addPoint(Player player) {
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if (playerPoints == 4) {
            arena.sendTitle(ChatColor.GOLD + player.getName() + " has won!", "");
            arena.sendMessage(ChatColor.GREEN + player.getName() + " has won!");
            arena.reset(true);
            return;
        }

        player.sendMessage(ChatColor.GREEN + "+1 Point!");
        points.replace(player.getUniqueId(), playerPoints);
    }

}
