package me.lutto.questuhc.instance

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import me.lutto.questuhc.manager.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

import java.util.UUID

class Arena(private val questUHC: QuestUHC, private val id: Int, private val spawn: Location, private val firstCorner: Location, private val secondCorner: Location) {

    private var state: GameState
    private var players: MutableList<UUID>
    private var countdown: Countdown
    private var game: Game

    init {
        state = GameState.RECRUITING
        players = mutableListOf()
        countdown = Countdown(questUHC, this)
        game = Game(this)
    }

    fun start() {
        game.start()
    }

    fun reset(kickPlayers: Boolean) {
        if (kickPlayers) {
            val lobbySpawn = ConfigManager.getLobbySpawn()
            for (uuid in players) {
                Bukkit.getPlayer(uuid)?.teleport(lobbySpawn)
            }
            players.clear()
        }

        sendTitle("", "")
        state = GameState.RECRUITING
        countdown.cancel()
        countdown = Countdown(questUHC, this)
        game = Game(this)
    }

    fun sendMessage(message: String) {
        for (playerUUID in players) {
            Bukkit.getPlayer(playerUUID)?.sendMessage(message)
        }
    }

    fun sendTitle(title: String, subtitle: String) {
        for (playerUUID in players) {
            Bukkit.getPlayer(playerUUID)?.sendTitle(title, subtitle)
        }
    }

    fun addPlayer(player: Player) {
        players.add(player.getUniqueId())
        player.teleport(spawn)

        if (state != GameState.RECRUITING) return
        if (players.size >= ConfigManager.getMinRequiredPlayers()) {
            countdown.start()
        }
    }

    fun removePlayer(player: Player) {
        players.remove(player.getUniqueId())
        player.teleport(ConfigManager.getLobbySpawn())
        player.sendTitle("", "")

        if (state == GameState.COUNTDOWN && players.size < ConfigManager.getMinRequiredPlayers()) {
            sendMessage("${ChatColor.GREEN}There's not enough players to start the game. Resetting the count.")
            reset(false)
            return
        }

        if (state == GameState.LIVE && players.size <= 1) {
            win(Bukkit.getPlayer(players.first())!!)
        }
    }

    fun win(player: Player) {
        players.remove(player.getUniqueId())

        sendTitle("${ChatColor.RED}You lost the game!", "${ChatColor.GRAY}Care to try again?")
        player.sendTitle("${ChatColor.GREEN}You won the game!", "Congratulations!", 10, 80, 10)

        player.setInvulnerable(true)
        Bukkit.getScheduler().runTaskLater(questUHC, Runnable {
            player.teleport(ConfigManager.getLobbySpawn())
            reset(true)
        }, 100)

    }

    fun getId(): Int = id

    fun getState(): GameState = state
    fun getPlayers(): MutableList<UUID> = players

    fun getGame(): Game = game

    fun getFirstCorner(): Location = firstCorner
    fun getSecondCorner(): Location = secondCorner

    fun setState(state: GameState) { this.state = state; }

}
