package io.github.naruFist.kape2.command

import io.github.naruFist.kape2.Kape
import io.github.naruFist.kape2.command.node.CommandNode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class CommandManager {
    companion object {
        @JvmStatic
        fun register(command: String, block: (CommandSender, String, Array<out String>) -> Boolean) {
            val cmd = object : Command(command) {
                override fun execute(p0: CommandSender, p1: String, p2: Array<out String>)
                = block.invoke(p0, p1, p2)
            }

            Kape.plugin.server.commandMap.register(command, Kape.plugin.name, cmd)
        }

        @JvmStatic
        fun register(command: String, block: CommandNode.() -> Unit) {
            block.invoke(TODO("뭐 어떻게든 잘 하겠죠..."))
        }
    }
}

// 귀찮아ㅏ서 테스트용으로 여기 적음
//fun a() {
//    CommandManager.register("hi") { sender, _, _ ->
//        sender.sendMessage("아", "나")
//        return@register false
//    }
//
//    CommandManager.register("send") {
//        parameter("player") {  }
//    }
//}