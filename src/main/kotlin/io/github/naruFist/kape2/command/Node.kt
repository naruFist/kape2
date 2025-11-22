package io.github.naruFist.kape2.command

import org.bukkit.command.CommandSender

@DslMarker
annotation class CmdNodeDSL

@CmdNodeDSL
interface Node {
    val sender: CommandSender
    val params: Array<out String>
    val level: Int

    fun param(block: ParamNode.() -> Unit) {
        if (params.size > level)
            block(ParamNode(sender, params, level + 1, params[level]))
    }

    fun param(string: String, block: CommandNode.() -> Unit) {
        if (params.size > level && params[level] == string)
            block(CommandNode(sender, params, level + 1))
    }
}