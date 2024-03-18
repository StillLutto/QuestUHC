package me.lutto.treasurebattle.instance

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enums.GameState
import me.lutto.treasurebattle.manager.ConfigManager.getCountdownSeconds
import org.bukkit.scheduler.BukkitRunnable

class Countdown(private val treasureBattle: TreasureBattle, private val arena: Arena) : BukkitRunnable() {

    private var countdownSeconds = getCountdownSeconds()

    fun start() {
        arena.setState(GameState.COUNTDOWN)
        runTaskTimer(treasureBattle, 0, 20)
    }

    override fun run() {
        if (countdownSeconds == 0) {
            cancel()
            arena.start()
            return
        }

        if (countdownSeconds % 10 == 0 || countdownSeconds <= 5) {
            arena.sendMessage("<green>Game starts in $countdownSeconds second${if (countdownSeconds == 1) "." else "s."}")
            arena.sendTitle(
                "<green>$countdownSeconds second${if (countdownSeconds == 1) "." else "s."}",
                "<green>until game starts."
            )
        }

        countdownSeconds--
    }

}
