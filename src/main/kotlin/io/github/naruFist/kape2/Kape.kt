package io.github.naruFist.kape2

import io.github.naruFist.kape2.util.KapePluginNotDefinedException
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.java.JavaPlugin

annotation class KapeDSL

private var _plugin: JavaPlugin? = null

class Kape {
    companion object {
        @JvmStatic
        var plugin: JavaPlugin
            get() = _plugin ?: throw KapePluginNotDefinedException()
            set(value) {
                _plugin = value
            }


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
