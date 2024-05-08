package sh.miles.voidworld

import sh.miles.pineapple.config.annotation.Comment
import sh.miles.pineapple.config.annotation.ConfigPath

object VoidWorlds {

    @ConfigPath("worlds")
    @Comment("World names to load, If multiverse is found this is will not load any worlds as that is handled by multiverse")
    var WORLDS: MutableList<String> = mutableListOf()
}