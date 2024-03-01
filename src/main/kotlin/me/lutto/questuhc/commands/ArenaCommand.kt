package me.lutto.questuhc.commands

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.enums.GameState
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ArenaCommand(private val questUHC: QuestUHC) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender is Player) {
            val player = sender

            if (!player.isOp) return false

            if (args.size == 1 && args[0].equals("list", ignoreCase = true)) {

                player.sendMessage("${ChatColor.GREEN}These are the available arenas:")
                for (arena in questUHC.arenaManager.getArenas()) {
                    player.sendMessage("${ChatColor.GRAY}- ${arena.getId()} (${arena.getState().name})")
                }

            } else if (args.size == 1 && args[0].equals("leave", ignoreCase = true)) {

                val arena = questUHC.arenaManager.getArena(player)
                if (arena != null) {
                    player.sendMessage("${ChatColor.RED}You left the arena.")
                    arena.removePlayer(player)
                } else {
                    player.sendMessage("${ChatColor.RED}You are not in an arena.")
                }

            } else if (args.size == 2 && args[0].equals("join", ignoreCase = true)) {

                if (questUHC.arenaManager.getArena(player) != null) {
                    player.sendMessage("${ChatColor.RED}You are already in an arena!")
                    return false
                }

                val id: Int
                try {
                    id = args[1].toInt()
                } catch (e: NumberFormatException) {
                    player.sendMessage("${ChatColor.RED}You specified an invalid arena ID!")
                    return false
                }

                if (id >= 0 && id < questUHC.arenaManager.getArenas().size) {
                    val arena = questUHC.arenaManager.getArena(id)
                    if (arena!!.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                        player.sendMessage("${ChatColor.GREEN}You are now playing in Arena " + id + "!")
                        arena.addPlayer(player)
                    } else {
                        player.sendMessage("${ChatColor.RED}You cannot join this Arena right now!")
                    }
                } else {
                    player.sendMessage("${ChatColor.RED}You specified an invalid arena ID!")
                }
            } else {
                player.sendMessage("${ChatColor.RED}Invalid usage! Please use /arena <list/leave/join>!")
            }
        }


        return false
    }

}
