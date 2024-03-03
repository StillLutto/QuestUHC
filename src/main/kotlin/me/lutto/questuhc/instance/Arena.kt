package me.lutto.questuhc.instance

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import me.lutto.questuhc.kit.Kit
import me.lutto.questuhc.kit.KitType
import me.lutto.questuhc.kit.KitUI
import me.lutto.questuhc.kit.type.ArcherKit
import me.lutto.questuhc.kit.type.MinerKit
import me.lutto.questuhc.kit.type.WarriorKit
import me.lutto.questuhc.manager.ConfigManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player

import java.util.UUID

class Arena(private val questUHC: QuestUHC, private val id: Int, private val spawn: Location, private val firstCorner: Location, private val secondCorner: Location) {

    private var state: GameState
    private var players: MutableList<UUID>
    private var kits: MutableMap<UUID, Kit>
    private var countdown: Countdown
    private var game: Game

    init {
        state = GameState.RECRUITING
        players = mutableListOf()
        kits = mutableMapOf()
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
                removeKit(uuid)
            }
            players.clear()
        }

        kits.clear()
        sendTitle("", "")
        state = GameState.RECRUITING
        countdown.cancel()
        countdown = Countdown(questUHC, this)
        game = Game(this)
    }

    fun sendMessage(message: String) {
        for (playerUUID in players) {
            Bukkit.getPlayer(playerUUID)?.sendRichMessage(message)
        }
    }

    fun sendTitle(title: String, subtitle: String) {
        for (playerUUID in players) {
            Bukkit.getPlayer(playerUUID)?.showTitle(Title.title(MiniMessage.miniMessage().deserialize(title), MiniMessage.miniMessage().deserialize(subtitle)))
        }
    }

    fun addPlayer(player: Player) {
        players.add(player.getUniqueId())
        player.teleport(spawn)
        KitUI(player)

        if (state != GameState.RECRUITING) return
        if (players.size >= ConfigManager.getMinRequiredPlayers()) {
            countdown.start()
        }
    }

    fun removePlayer(player: Player) {
        players.remove(player.uniqueId)
        player.teleport(ConfigManager.getLobbySpawn())
        player.clearTitle()

        removeKit(player.uniqueId)

        if (state == GameState.COUNTDOWN && players.size < ConfigManager.getMinRequiredPlayers()) {
            sendMessage("<green>There's not enough players to start the game. Resetting the count.")
            reset(false)
            return
        }

        if (state == GameState.LIVE && players.size <= 1) {
            win(Bukkit.getPlayer(players.first())!!)
        }
    }

    fun win(player: Player) {
        players.clear()

        sendTitle("<red>You lost the game!", "<gray>Care to try again?")
        player.showTitle(Title.title(
            Component.text("You won the game!", NamedTextColor.GREEN),
            Component.text("Congratulations!")))

        player.gameMode = GameMode.ADVENTURE
        player.isInvulnerable = true
        Bukkit.getScheduler().runTaskLater(questUHC, Runnable {
            player.teleport(ConfigManager.getLobbySpawn())
            reset(true)
        }, 100)

    }

    fun getId(): Int = id

    fun getState(): GameState = state
    fun getPlayers(): MutableList<UUID> = players
    fun getKits(): MutableMap<UUID, Kit> = kits

    fun getGame(): Game = game

    fun getFirstCorner(): Location = firstCorner
    fun getSecondCorner(): Location = secondCorner

    fun setState(state: GameState) { this.state = state; }

    fun removeKit(uuid: UUID) {
        if (kits.contains(uuid)) {
            kits[uuid]?.remove()
            kits.remove(uuid)
        }
    }

    fun setKit(uuid: UUID, type: KitType) {
        removeKit(uuid)
        when (type) {
            KitType.WARRIOR -> kits[uuid] = WarriorKit(questUHC, uuid)
            KitType.MINER -> kits[uuid] = MinerKit(questUHC, uuid)
            KitType.ARCHER -> kits[uuid] = ArcherKit(questUHC, uuid)
        }
    }

    fun getKit(player: Player): KitType? {
        return if (kits.contains(player.uniqueId)) kits[player.uniqueId]?.type else null
    }

}
