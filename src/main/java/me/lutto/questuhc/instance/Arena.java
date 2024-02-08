package me.lutto.questuhc.instance;

import me.lutto.questuhc.QuestUHC;
import me.lutto.questuhc.enums.GameState;
import me.lutto.questuhc.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private QuestUHC questUHC;

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    public Arena(QuestUHC questUHC, int id, Location spawn) {
        this.questUHC = questUHC;

        this.id = id;
        this.spawn = spawn;

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

        if (state.equals(GameState.RECRUITING)) return;
        if (players.size() >= ConfigManager.getMinRequiredPlayers() && players.size() <= ConfigManager.getMaxRequiredPlayers()) {
            countdown.start();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        Bukkit.getScheduler().runTaskLaterAsynchronously(questUHC, () -> {
            player.teleport(ConfigManager.getLobbySpawn());
        }, 100);
        player.sendTitle("", "");

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getMinRequiredPlayers()) {
            sendMessage(ChatColor.GREEN + "There's not enough players to start the game. Resetting the count.");
            reset(false);
            return;
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getMinRequiredPlayers()) {
            sendMessage(ChatColor.GREEN + "The game has ended because too many players left.");
            reset(false);
        }
    }

    public int getId() { return id; }

    public GameState getState() { return state; }
    public List<UUID> getPlayers() { return players; }

    public Game getGame() { return game; }

    public void setState(GameState state) { this.state = state; }

}
