package me.lutto.questuhc.instance;

import me.lutto.questuhc.QuestUHC;
import me.lutto.questuhc.enums.GameState;
import me.lutto.questuhc.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private QuestUHC questUHC;

    private int id;
    private Location spawn;
    private Location firstCorner;
    private Location secondCorner;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    public Arena(QuestUHC questUHC, int id, Location spawn, Location firstCorner, Location secondCorner) {
        this.questUHC = questUHC;

        this.id = id;
        this.spawn = spawn;

        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(questUHC, this);
        this.game = new Game(this);
    }

    public void start() {
        game.start();
    }

    public void reset(boolean kickPlayers) {
        if (kickPlayers) {
            Location lobbySpawn = ConfigManager.getLobbySpawn();
            for (UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(lobbySpawn);
            }
            players.clear();
        }

        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(questUHC, this);
        game = new Game(this);
    }

    public void sendMessage(String message) {
        for (UUID playerUUID : players) {
            Bukkit.getPlayer(playerUUID).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (UUID playerUUID : players) {
            Bukkit.getPlayer(playerUUID).sendTitle(title, subtitle);
        }
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (!state.equals(GameState.RECRUITING)) return;
        if (players.size() >= ConfigManager.getMinRequiredPlayers()) {
            countdown.start();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("", "");

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getMinRequiredPlayers()) {
            sendMessage(ChatColor.GREEN + "There's not enough players to start the game. Resetting the count.");
            reset(false);
            return;
        }

        if (state == GameState.LIVE && players.size() <= 1) {
            win(Bukkit.getPlayer(players.getFirst()));
        }
    }

    public void win(Player player) {
        players.remove(player.getUniqueId());

        sendTitle(ChatColor.RED + "You lost the game!", ChatColor.GRAY + "Care to try again?");
        player.sendTitle(ChatColor.GOLD + "You won the game!", "Congratulations!", 10, 80, 10);

        player.setInvulnerable(true);
        Bukkit.getScheduler().runTaskLater(questUHC, () -> {
            player.teleport(ConfigManager.getLobbySpawn());
            reset(true);
        }, 100);

    }

    public int getId() { return id; }

    public GameState getState() { return state; }
    public List<UUID> getPlayers() { return players; }

    public Game getGame() { return game; }

    public Location getFirstCorner() { return firstCorner; }
    public Location getSecondCorner() { return secondCorner; }

    public void setState(GameState state) { this.state = state; }

}
