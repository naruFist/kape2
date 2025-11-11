package io.github.naruFist.kape2.command.node

import io.github.naruFist.kape2.command.CommandExecutor
import io.github.naruFist.kape2.command.CommandHelper

class CommandNode(private val executor: CommandExecutor) {

    fun parameter(string: String, block: CommandNode.() -> Unit): CommandHelper {

        TODO("CommandHelper 작성 후 완성")
    }

    fun execute(block: CommandExecutor.() -> Unit): CommandHelper {
        block.invoke(executor)

        TODO("CommandHelper 작성 후 완성")
    }
}