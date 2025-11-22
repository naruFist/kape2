package io.github.naruFist.kape2.command

import org.bukkit.command.CommandSender

class CommandNode: Node {
    override val sender: CommandSender
    override val params: Array<out String>
    override val level: Int

    constructor(sender: CommandSender, params: Array<out String>, level: Int) {
        this.sender = sender
        this.params = params
        this.level = level
    }
}