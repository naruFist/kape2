package io.github.naruFist.kape2.command

import org.bukkit.command.CommandSender

class ParamNode: Node {
    override val sender: CommandSender
    override val params: Array<out String>
    override val level: Int
    val lastParameter: String

    constructor(sender: CommandSender, params: Array<out String>, level: Int, string: String) {
        this.sender = sender
        this.params = params
        this.level = level

        lastParameter = string
    }
}