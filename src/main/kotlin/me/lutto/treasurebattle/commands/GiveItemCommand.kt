package me.lutto.treasurebattle.commands

import me.lutto.treasurebattle.TreasureBattle
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveItemCommand(val treasureBattle: TreasureBattle) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender is Player) {

            if (args.size == 0) {
                sender.sendMessage("You did not provide any arguments. Try again.")
                sender.sendMessage("Example: /shardgive <item>")
            } else {
                if (!sender.isOp()) {
                    sender.sendRichMessage("<red>You need to be an operator to use this command!")
                    return false
                }

                if (treasureBattle.itemManager.getItem(args[0]) != null) {
                    sender.inventory.addItem(
                        treasureBattle.itemManager.getItem(args[0])!!.getItemStack()
                    )
                } else {
                    sender.sendRichMessage("<red>Item does not exist!")
                    return false
                }

                sender.sendRichMessage("<gold>You have been given ${args[0]}!")
                return true
            }
        } else {
            Bukkit.getLogger().info("Command must be run by player!")
            return false
        }

        return false

    }

}