package me.lutto.questuhc.instance

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import me.lutto.questuhc.manager.ConfigManager.getCountdownSeconds
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable

class Countdown(private val questUHC: QuestUHC, private val arena: Arena) : BukkitRunnable() {

    private var countdownSeconds = getCountdownSeconds()

    fun start() {
        arena.setState(GameState.COUNTDOWN)
        runTaskTimer(questUHC, 0, 20)
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
                "${ChatColor.GREEN}$countdownSeconds second${if (countdownSeconds == 1) "." else "s."}",
                "${ChatColor.GRAY}until game starts."
            )
        }

        countdownSeconds--
    }

}
