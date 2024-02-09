package me.lutto.questuhc.commands;

import me.lutto.questuhc.QuestUHC;
import me.lutto.questuhc.enums.GameState;
import me.lutto.questuhc.instance.Arena;
import me.lutto.questuhc.instance.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private QuestUHC questUHC;

    public ArenaCommand(QuestUHC questUHC) {
        this.questUHC = questUHC;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.isOp()) return false;

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.GREEN + "These are the available arenas:");
                for (Arena arena : questUHC.getArenaManager().getArenas()) {
                    player.sendMessage(ChatColor.GRAY + "- " + arena.getId() + " (" + arena.getState().name() + ")");
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                Arena arena = questUHC.getArenaManager().getArena(player);
                if (arena != null) {
                    player.sendMessage(ChatColor.RED + "You left the arena.");
                    arena.removePlayer(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in an arena.");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                if (questUHC.getArenaManager().getArena(player) != null) {
                    player.sendMessage(ChatColor.RED + "You are already in an arena!");
                    return false;
                }

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "You specified an invalid arena ID!");
                    return false;
                }

                if (id >= 0 && id < questUHC.getArenaManager().getArenas().size()) {
                    Arena arena = questUHC.getArenaManager().getArena(id);
                    if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                        player.sendMessage(ChatColor.GREEN + "You are now playing in Arena " + id + "!");
                        arena.addPlayer(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You cannot join this Arena right now!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You specified an invalid arena ID!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage! Please use /arena <list/leave/join>!");
            }
        }


        return false;
    }

}
