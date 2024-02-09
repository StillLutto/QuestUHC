package me.lutto.questuhc.instance;

import me.lutto.questuhc.QuestUHC;
import me.lutto.questuhc.enums.GameState;
import me.lutto.questuhc.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private QuestUHC questUHC;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(QuestUHC questUHC, Arena arena) {
        this.questUHC = questUHC;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start() {
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(questUHC, 0, 20);
    }

    @Override
    public void run() {

        if (countdownSeconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (countdownSeconds % 10 == 0 || countdownSeconds <= 5) {
            arena.sendMessage(ChatColor.GREEN + "Game starts in " + countdownSeconds + " second" + (countdownSeconds == 1 ? "." : "s."));
            arena.sendTitle(ChatColor.GREEN.toString() + countdownSeconds + " second" + (countdownSeconds == 1 ? "." : "s."), ChatColor.GRAY + "until game starts.");
        }

        countdownSeconds--;

    }

}
