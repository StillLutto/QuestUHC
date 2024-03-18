package me.lutto.treasurebattle.commands.tabcompleters

import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.instance.CustomItem
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil

class GiveItemTabCompleter(val treasureBattle: TreasureBattle) : TabCompleter {

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String>? {

        if (args.size == 1) {
            val itemList: MutableList<CustomItem> = treasureBattle.itemManager.getItemList()
            var itemNameList: MutableList<String> = mutableListOf()
            for (item in itemList) {
                itemNameList.add(item.getId())
            }
            return StringUtil.copyPartialMatches(args[0], itemNameList, ArrayList())
        }

        return ArrayList()

    }

}
