package me.lutto.questuhc.commands.tabcompleters

import me.lutto.questuhc.QuestUHC
import me.lutto.questuhc.instance.CustomItem
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil

class GiveItemTabCompleter(val questUHC: QuestUHC) : TabCompleter {

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String>? {

        if (args.size == 1) {
            val itemList: MutableList<CustomItem> = questUHC.itemManager.getItemList()
            var itemNameList: MutableList<String> = mutableListOf()
            for (item in itemList) {
                itemNameList.add(item.getId())
            }
            return StringUtil.copyPartialMatches(args[0], itemNameList, ArrayList())
        }

        return ArrayList()

    }

}
