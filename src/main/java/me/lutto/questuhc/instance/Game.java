package me.lutto.questuhc.instance;

import me.lutto.questuhc.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    private Set<Location> blockedArea = new HashSet<>();
    private Set<UUID> playersTeleported = new HashSet<>();

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

        for (UUID uuid : arena.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);

            while (!playersTeleported.contains(uuid)) {

                Location firstCorner = arena.getFirstCorner();
                Location secondCorner = arena.getSecondCorner();

                Location randomLocation = getRandomLocation(firstCorner, secondCorner, 61);

                if (blockedArea.contains(randomLocation)) continue;

                player.teleport(randomLocation);
                randomLocation.getWorld().strikeLightningEffect(randomLocation);
                player.playSound(player, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1, 1);

                addBlockedArea(randomLocation);
                playersTeleported.add(uuid);
            }
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

    private void addBlockedArea(Location centerLocation) {
        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {

                Location currentLocation = centerLocation.add(x, 0, z);
                blockedArea.add(currentLocation);

            }
        }
    }

    public Location getRandomLocation(Location firstCorner, Location secondCorner, double yLevel) {
        double minX = Math.min(firstCorner.getBlockX(), secondCorner.getBlockX());
        double minZ = Math.min(firstCorner.getBlockZ(), secondCorner.getBlockZ());
        double maxX = Math.max(firstCorner.getBlockX(), secondCorner.getBlockX());
        double maxZ = Math.max(firstCorner.getBlockZ(), secondCorner.getBlockZ());

        double randomX = Math.floor(minX + ThreadLocalRandom.current().nextDouble(maxX - minX)) + 0.5;
        double randomZ = Math.floor(minZ + ThreadLocalRandom.current().nextDouble(maxZ - minZ)) + 0.5;

        return new Location(firstCorner.getWorld(), randomX, yLevel, randomZ);
    }

}
