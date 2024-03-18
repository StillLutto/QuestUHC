package me.lutto.treasurebattle.commands.tabcompleters

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil

class ArenaTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String>? {

        if (args.size == 1) {
            return StringUtil.copyPartialMatches(args[0], mutableListOf("list", "join", "leave"), ArrayList())
        } else if (args.size == 2) {
            return StringUtil.copyPartialMatches(args[1], mutableListOf("Type arena id here..."), ArrayList())
        }

        return ArrayList()

    }
}
