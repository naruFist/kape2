package io.github.naruFist.kape2

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.java.JavaPlugin

annotation class KapeDSL

private var _plugin: JavaPlugin? = null

var plugin: JavaPlugin
    get() = _plugin ?: throw IllegalStateException("Plugin not initialized yet!")
    set(value) {
        _plugin = value
    }


class Kape {
    companion object {

        @KapeDSL
        @JvmStatic
        inline fun <reified E : Event> listener(noinline block: (E) -> Unit) {
            val dummyListener = object : Listener {}

            val executor = EventExecutor { _, event ->
                if (event is E) block(event)
            }

            plugin.server.pluginManager.registerEvent(
                E::class.java, dummyListener, EventPriority.NORMAL, executor, plugin, false
            )
        }

        @KapeDSL
        @JvmStatic
        inline fun <reified E : Event> listener(priority: EventPriority, noinline block: (E) -> Unit) {
            val dummyListener = object : Listener {}

            val executor = EventExecutor { _, event ->
                if (event is E) block(event)
            }

            plugin.server.pluginManager.registerEvent(
                E::class.java, dummyListener, priority, executor, plugin, false
            )
        }
    }
}
