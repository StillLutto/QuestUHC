package me.lutto.treasurebattle.commands

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enums.GameState
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ArenaCommand(private val treasureBattle: TreasureBattle) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender is Player) {
            val player = sender

            if (!player.isOp) return false

            when (args.size == 1) {
                args[0].equals("list", ignoreCase = true) -> {
                    player.sendRichMessage("<green>These are the available arenas:")
                    for (arena in treasureBattle.arenaManager.getArenas()) {
                        player.sendRichMessage("<gray>- ${arena.getId()} (${arena.getState().name})")
                    }
                }

                args[0].equals("leave", ignoreCase = true) -> {
                    val arena = treasureBattle.arenaManager.getArena(player)
                    if (arena != null) {
                        player.sendRichMessage("<red>You left the arena.")
                        arena.removePlayer(player)
                    } else {
                        player.sendRichMessage("<red>You are not in an arena.")
                    }
                }

                else -> {
                    player.sendRichMessage("<red>Invalid usage! Please use /arena <list/leave/join>!")
                }
            }

            if (args.size == 2 && args[0].equals("join", ignoreCase = true)) {

            if (treasureBattle.arenaManager.getArena(player) != null) {
                player.sendRichMessage("<red>You are already in an arena!")
                return false
            }

            val id: Int
            try {
                id = args[1].toInt()
            } catch (e: NumberFormatException) {
                player.sendRichMessage("<red>You specified an invalid arena ID!")
                return false
            }

            if (id >= 0 && id < treasureBattle.arenaManager.getArenas().size) {
                val arena = treasureBattle.arenaManager.getArena(id)
                if (arena!!.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                    player.sendRichMessage("<green>You are now playing in Arena $id!")
                    arena.addPlayer(player)
                } else {
                    player.sendRichMessage("<red>You cannot join this Arena right now!")
                }
            }
        }
        }


        return false

    }

}
