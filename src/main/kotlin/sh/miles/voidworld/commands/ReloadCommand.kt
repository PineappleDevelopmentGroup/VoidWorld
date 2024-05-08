package sh.miles.voidworld.commands

import org.bukkit.command.CommandSender
import org.bukkit.util.StringUtil
import sh.miles.pineapple.chat.PineappleComponent
import sh.miles.pineapple.command.Command
import sh.miles.pineapple.command.CommandLabel
import sh.miles.voidworld.Messages
import sh.miles.voidworld.VoidWorldPlugin

class ReloadCommand : Command(CommandLabel("reload", "voidworlds.command.reload")) {

    private val COMMAND_FORMAT: String = "/voidworlds reload <all, messages, worlds>"

    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {

        if (args.isEmpty()) {
            sender.spigot().sendMessage(Messages.INVALID_COMMAND.component(mapOf("command" to COMMAND_FORMAT)))
            return true
        }

        val reloadType: ReloadTypes
        try {
            reloadType = ReloadTypes.valueOf(args[0].uppercase())
        } catch (ex: IllegalArgumentException) {
            sender.spigot().sendMessage(Messages.INVALID_RELOAD_TYPE.component(mapOf("reload" to args[0])))
            return true
        }

        when (reloadType) {
            ReloadTypes.ALL -> {
                VoidWorldPlugin.instance.reloadVoidWorlds()
                sender.spigot().sendMessage(Messages.RELOADED.component(mapOf("reload" to "Worlds")))
                VoidWorldPlugin.instance.reloadMessages()
                sender.spigot().sendMessage(Messages.RELOADED.component(mapOf("reload" to "Messages")))
            }
            ReloadTypes.MESSAGES -> {
                VoidWorldPlugin.instance.reloadMessages()
                sender.spigot().sendMessage(Messages.RELOADED.component(mapOf("reload" to "Messages")))
            }
            ReloadTypes.WORLDS -> {
                VoidWorldPlugin.instance.reloadVoidWorlds()
                sender.spigot().sendMessage(Messages.RELOADED.component(mapOf("reload" to "Worlds")))
            }
        }

        return true
    }

    override fun complete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            return StringUtil.copyPartialMatches(args[0], listOf("all", "messages", "worlds"), mutableListOf())
        }
        return mutableListOf()
    }

    enum class ReloadTypes {
        ALL, MESSAGES, WORLDS
    }
}