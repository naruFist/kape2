package io.github.naruFist.kape2.command

import org.bukkit.command.CommandSender

data class CommandExecutor(val sender: CommandSender, val cmdText: String, val args: Array<out String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CommandExecutor

        if (sender != other.sender) return false
        if (cmdText != other.cmdText) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        super.hashCode()
        var result = sender.hashCode()
        result = 31 * result + cmdText.hashCode()
        result = 31 * result + args.contentHashCode()
        return result
    }
}