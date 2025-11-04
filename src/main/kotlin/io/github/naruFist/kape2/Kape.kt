package io.github.naruFist.kape2

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

annotation class KapeDSL

internal lateinit var plugin: JavaPlugin


class Kape {
    companion object {
        @JvmStatic
        fun setPlugin(javaPlugin: JavaPlugin) {
            plugin = javaPlugin
        }

        @JvmStatic
        fun plugin() = plugin

        @KapeDSL
        @JvmStatic
        fun <E: Event>listener(clazz: Class<E>, block: (event: E) -> Unit) {
            val listenerBlock = object : Listener {
                @EventHandler
                fun a(event: E) {
                    block.invoke(event)
                }
            }

            plugin.server.pluginManager.registerEvents(listenerBlock, plugin)
        }

        @KapeDSL
        @JvmStatic
        inline fun <reified E : Event> listener(noinline block: (E) -> Unit) {
            listener(E::class.java, block)
        }
    }
}
