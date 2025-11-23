package io.github.naruFist.kape2.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender

class CommandNode: Node {
    override val rootCmd: LiteralArgumentBuilder<CommandSourceStack>
    override val sender: CommandSender
    override val params: Array<out String>
    override val level: Int

    constructor(rootCmd: LiteralArgumentBuilder<CommandSourceStack>,
                sender: CommandSender, params: Array<out String>, level: Int) {
        this.rootCmd = rootCmd
        this.sender = sender
        this.params = params
        this.level = level
    }
}