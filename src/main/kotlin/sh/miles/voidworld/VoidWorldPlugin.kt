package sh.miles.voidworld

import com.onarandombox.MultiverseCore.MultiverseCore
import com.onarandombox.MultiverseCore.api.MVWorldManager
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import sh.miles.pineapple.PineappleLib
import sh.miles.pineapple.config.ConfigWrapper
import sh.miles.voidworld.commands.VoidWorldCommands
import java.io.File

class VoidWorldPlugin : JavaPlugin() {

    private lateinit var voidWorldConfig: ConfigWrapper
    private lateinit var messagesConfig: ConfigWrapper

    override fun onEnable() {
        PineappleLib.initialize(this)
        instance = this

        this.voidWorldConfig = PineappleLib.getConfigurationManager()
            .createDefault(File(this.dataFolder, "voidworlds.yml"), VoidWorlds.javaClass)

        this.messagesConfig = PineappleLib.getConfigurationManager()
            .createDefault(File(this.dataFolder, "messages.yml"), Messages.javaClass)

        PineappleLib.getCommandRegistry().register(VoidWorldCommands())
        PineappleLib.getCommandRegistry().registerInternalCommands()

        multiverseEnabled = Bukkit.getPluginManager().isPluginEnabled("Multiverse-Core")
        if (multiverseEnabled) {
            multiverseWorldManager =
                (Bukkit.getPluginManager().getPlugin("Multiverse-Core") as MultiverseCore).mvWorldManager
        } else {
            for (name: String in VoidWorlds.WORLDS) {
                WorldCreator.name(name).createWorld()
            }
        }
    }


    fun saveVoidWorlds() {
        this.voidWorldConfig.save(true)
    }

    fun reloadVoidWorlds() {
        this.voidWorldConfig.load()
    }

    fun reloadMessages() {
        this.voidWorldConfig.load()
    }

    companion object {
        var multiverseEnabled: Boolean = false
        lateinit var multiverseWorldManager: MVWorldManager
        lateinit var instance: VoidWorldPlugin
    }
}