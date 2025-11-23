package io.github.naruFist.kape2.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender

class ParamNode: Node {
    override val rootCmd: LiteralArgumentBuilder<CommandSourceStack>
    override val sender: CommandSender
    override val params: Array<out String>
    override val level: Int
    val lastParameter: String

    constructor(rootCmd: LiteralArgumentBuilder<CommandSourceStack>,
                sender: CommandSender, params: Array<out String>, level: Int, string: String) {
        this.rootCmd = rootCmd
        this.sender = sender
        this.params = params
        this.level = level

        lastParameter = string
    }
}