package me.lutto.questuhc.instance

import me.lutto.questuhc.enums.GameState
import org.bukkit.*
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Game(private val arena: Arena) {

    private val points = HashMap<UUID, Int>()

    private val blockedArea: MutableSet<Location> = HashSet()
    private val playersTeleported: MutableSet<UUID> = HashSet()

    fun start() {
        arena.setState(GameState.LIVE)

        arena.sendTitle("<blue>Game has started!", "Complete your objective!")
        arena.sendMessage(
            """
            <green>Game has started!
            <blue>Your objective is to kill 4 animals and you will get a secret weapon!
            """.trimIndent()
        )

        for (uuid in arena.getKits().keys) {
            arena.getKits()[uuid]?.onStart(Bukkit.getPlayer(uuid))
        }

        for (uuid: UUID in arena.getPlayers()) {
            points[uuid] = 0
            val player: Player = Bukkit.getPlayer(uuid) ?: return
            player.closeInventory()
            player.gameMode = GameMode.SURVIVAL
        }

        teleportPlayers()
    }

    fun addPoint(player: Player) {
        val playerPoints = (points[player.uniqueId] ?: return) + 1
        if (playerPoints == 4) {
            arena.win(player)
            return
        }

        player.sendRichMessage("<green>+1 Point!")
        points.replace(player.uniqueId, playerPoints)
    }

    private fun teleportPlayers() {
        for (uuid in arena.getPlayers()) {
            val player = Bukkit.getPlayer(uuid) ?: return
            player.isInvulnerable = false

            while (!playersTeleported.contains(uuid)) {
                val firstCorner = arena.getFirstCorner()
                val secondCorner = arena.getSecondCorner()

                val randomLocation = getRandomLocation(firstCorner, secondCorner, 61.0)

                if (blockedArea.contains(randomLocation)) continue

                player.teleport(randomLocation)
                randomLocation.world.strikeLightningEffect(randomLocation)
                player.playSound(player, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1f, 1f)

                addBlockedArea(randomLocation)
                playersTeleported.add(uuid)
            }
        }
    }

    private fun addBlockedArea(centerLocation: Location) {
        val radius = 10
        for (x in -radius..radius) {
            for (z in -radius..radius) {
                val currentLocation = centerLocation.clone().add(x.toDouble(), 0.0, z.toDouble())
                blockedArea.add(currentLocation)
            }
        }
    }

    fun getRandomLocation(firstCorner: Location, secondCorner: Location, yLevel: Double): Location {
        val minX = min(firstCorner.blockX.toDouble(), secondCorner.blockX.toDouble())
        val minZ = min(firstCorner.blockZ.toDouble(), secondCorner.blockZ.toDouble())
        val maxX = max(firstCorner.blockX.toDouble(), secondCorner.blockX.toDouble())
        val maxZ = max(firstCorner.blockZ.toDouble(), secondCorner.blockZ.toDouble())

        val randomX = floor(minX + ThreadLocalRandom.current().nextDouble(maxX - minX)) + 0.5
        val randomZ = floor(minZ + ThreadLocalRandom.current().nextDouble(maxZ - minZ)) + 0.5

        return Location(firstCorner.world, randomX, yLevel, randomZ)
    }

}
