package sh.miles.voidworld.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.block.Biome
import org.bukkit.command.CommandSender
import org.bukkit.util.StringUtil
import sh.miles.pineapple.PineappleLib
import sh.miles.pineapple.command.Command
import sh.miles.pineapple.command.CommandLabel
import sh.miles.voidworld.Messages
import sh.miles.voidworld.VoidWorldPlugin
import sh.miles.voidworld.VoidWorlds
import sh.miles.voidworld.generator.SingleBiomeProvider
import sh.miles.voidworld.generator.VoidGenerator

class CreateCommand : Command(CommandLabel("create", "voidworlds.command.create")) {
    private val COMMAND_FORMAT = "/voidworld create <name> <biome>"
    private val BIOMES: List<String> = Biome.entries.map { it.toString() }

    // Command = /voidworld create name biome
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 2) {
            sender.spigot().sendMessage(Messages.INVALID_COMMAND.component(mapOf("command" to COMMAND_FORMAT)))
            return true
        }

        val name = args[0]
        if (Bukkit.getWorld(name) != null) {
            sender.spigot().sendMessage(Messages.INVALID_WORLD.component(mapOf("world" to name)))
            return true
        }

        val biome: Biome
        try {
            biome = Biome.valueOf(args[1].uppercase())
        } catch (ex: IllegalArgumentException) {
            sender.spigot().sendMessage(Messages.INVALID_WORLD.component(mapOf("biome" to args[1])))
            return true
        }

        val world = WorldCreator(name).environment(World.Environment.NORMAL).generator(VoidGenerator()).biomeProvider(SingleBiomeProvider(biome)).generateStructures(false).createWorld()!!

        val spawn = Location(world, 0.0, 0.0, 0.0)
        spawn.block.type = Material.GLASS
        world.setSpawnLocation(spawn)

        VoidWorlds.WORLDS.add(name)
        VoidWorldPlugin.instance.saveVoidWorlds()

        // Handle multiverse
        if (VoidWorldPlugin.multiverseEnabled) {
            val mvWorldManager = VoidWorldPlugin.multiverseWorldManager
            mvWorldManager.loadWorld(name)
            mvWorldManager.addWorld(name, World.Environment.NORMAL, null, WorldType.NORMAL, false, null)
            val mvWorld = mvWorldManager.getMVWorld(world)
            mvWorld.spawnLocation = spawn
        }

        return true
    }

    override fun complete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        if (args.size == 2) {
            return StringUtil.copyPartialMatches(args[1], BIOMES, mutableListOf())
        }
        return mutableListOf()
    }
}