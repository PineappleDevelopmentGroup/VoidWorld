package sh.miles.voidworld.commands

import sh.miles.pineapple.command.Command
import sh.miles.pineapple.command.CommandLabel

class VoidWorldCommands : Command(CommandLabel("voidworlds", "voidworlds.command")) {
    init {
        registerSubcommand(CreateCommand())
        registerSubcommand(ReloadCommand())
    }
}