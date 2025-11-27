package io.github.naruFist.kape2

import io.github.naruFist.kape2.command.CommandNode
import io.github.naruFist.kape2.component.text
import io.github.naruFist.kape2.inventory.InventoryManager
import io.github.naruFist.kape2.scheduler.Scheduler
import io.github.naruFist.kape2.util.KapePluginUndefinedException
import io.github.naruFist.kape2.util.toPlayer
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.java.JavaPlugin

@DslMarker
private annotation class KapeDSL

annotation class KapeListener

private var _plugin: JavaPlugin? = null

class Kape {
    companion object {

        // Plugin Manager Part
        @JvmStatic
        var plugin: JavaPlugin
            get() = _plugin ?: throw KapePluginUndefinedException()
            set(value) {
                _plugin = value
            }

        val manager
            get() = plugin.kapeManager


        @JvmStatic
        fun disable() {
            // Memory Leak Prevent

            plugin.kapeManager.listeners.forEach {
                HandlerList.unregisterAll(it)
            }

            _plugin = null
        }

        fun manager(block: KapeManager.() -> Unit) {
            block(plugin.kapeManager)
        }
    }
}
