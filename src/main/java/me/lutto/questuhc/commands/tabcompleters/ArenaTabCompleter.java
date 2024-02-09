package me.lutto.questuhc.commands.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArenaTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("list", "join", "leave"), new ArrayList<>());
        } else if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], Arrays.asList("Type arena id here..."), new ArrayList<>());
        }

        return new ArrayList<>();

    }

}
