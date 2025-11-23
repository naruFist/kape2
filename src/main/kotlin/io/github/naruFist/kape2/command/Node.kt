package io.github.naruFist.kape2.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.command.CommandSender

@DslMarker
annotation class CmdNodeDSL

@CmdNodeDSL
interface Node {
    val rootCmd: LiteralArgumentBuilder<CommandSourceStack>
    val sender: CommandSender
    val params: Array<out String>
    val level: Int

    fun arg(pair: Pair<String, ArgumentType<*>>, block: ParamNode.() -> Unit) {
        val (string, argType) = pair

        rootCmd.then(Commands.argument(string, argType)).apply {
            block(ParamNode(this, sender, params, level + 1, params[level]))
        }
    }

    fun literal(string: String, block: CommandNode.() -> Unit) {
        rootCmd.then(Commands.literal(string)).apply {
            block(CommandNode(this, sender, params, level + 1))
        }
    }

    fun execute(block: (CommandContext<CommandSourceStack>) -> Unit) {
        rootCmd.executes { ctx ->
            block(ctx)

            return@executes Command.SINGLE_SUCCESS
        }
    }
}