package sh.miles.voidworld

import sh.miles.pineapple.chat.PineappleChat
import sh.miles.pineapple.chat.PineappleComponent
import sh.miles.pineapple.config.annotation.Comment
import sh.miles.pineapple.config.annotation.ConfigPath

object Messages {

    @ConfigPath("invalid-command")
    @Comment("Use <\$command> to show the correct format")
    var INVALID_COMMAND: PineappleComponent = PineappleChat.component("<red>Invalid command, use <\$command>")

    @ConfigPath("invalid-world")
    @Comment("Use <\$world> to show the world name")
    var INVALID_WORLD: PineappleComponent = PineappleChat.component("<red>World <\$world> already exists, use another name")

    @ConfigPath("invalid-biome")
    @Comment("Use <\$biome> to show the biome submitted")
    var INVALID_BIOME: PineappleComponent = PineappleChat.component("<red>Invalid biome <\$biome>, use tab complete to choose a valid biome")

    @ConfigPath("invalid-reload")
    @Comment("Use <\$reload> to show the reload type submitted")
    var INVALID_RELOAD_TYPE: PineappleComponent = PineappleChat.component("<red>Invalid reload type <\$reload>, use tab complete to choose a valid reload")

    @ConfigPath("reloaded-type")
    @Comment("Use <\$reload> to show the reloaded config")
    var RELOADED: PineappleComponent = PineappleChat.component("<green>Reloaded <reload>!")
}