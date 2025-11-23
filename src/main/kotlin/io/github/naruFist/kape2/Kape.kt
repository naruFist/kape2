package io.github.naruFist.kape2

import io.github.naruFist.kape2.command.CommandNode
import io.github.naruFist.kape2.component.text
import io.github.naruFist.kape2.inventory.InventoryManager
import io.github.naruFist.kape2.util.KapePluginUndefinedException
import io.github.naruFist.kape2.util.toPlayer
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
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

        @JvmStatic
        fun disable() {
            listeners.forEach {
                HandlerList.unregisterAll(it)
            }

            _plugin = null
        }


        // Listener Part
        @JvmStatic
        val listeners: MutableList<Listener> = mutableListOf()


        @KapeDSL
        @JvmStatic
        inline fun <reified E : Event> listener(priority: EventPriority = EventPriority.NORMAL, noinline block: (E) -> Unit): Listener {
            val dummyListener = object : Listener {}

            val executor = EventExecutor { _, event ->
                if (event is E) block(event)
            }

            plugin.server.pluginManager.registerEvent(
                E::class.java, dummyListener, priority, executor, plugin, false
            )

            listeners += dummyListener
            return dummyListener
        }

        @KapeDSL
        @JvmStatic
        fun <E: Event> listener(clazz: Class<E>, priority: EventPriority = EventPriority.NORMAL, block: (E) -> Unit): Listener {
            val dummyListener = object : Listener {}

            val executor = EventExecutor { _, event ->
                if (clazz.isInstance(event)) block(clazz.cast(event))
            }

            plugin.server.pluginManager.registerEvent(
                clazz, dummyListener, priority, executor, plugin, false
            )

            listeners += dummyListener
            return dummyListener
        }


        // Server Logger Part
        @JvmStatic
        fun log(text: Component) = plugin.server.sendMessage(text)

        @JvmStatic
        fun log(string: String) = log(text = text(string))


        // Inventory Manager Part
        @JvmStatic
        fun inventory(line: Int, title: Component, block: InventoryManager.() -> Unit = {}) =
            InventoryManager(plugin.server.createInventory(null, 9 * line, title)).apply { block(this) }.build()

        @JvmStatic
        fun inv(line: Int, title: Component, block: InventoryManager.() -> Unit = {}) =
            inventory(line, title, block)


        // Command Manager Part
        @KapeDSL
        @JvmStatic
        fun command(string: String, cmdManager: CommandNode.() -> Unit) {

            val rootCmd = Commands.literal(string)

            val cmd = object : Command(string) {
                override fun execute(p0: CommandSender, p1: String, p2: Array<out String>): Boolean {
                    CommandNode(rootCmd, p0, p2, 0).cmdManager()

                    return false
                }
            }

            cmd.register(plugin.server.commandMap)
        }
    }
}

fun a() {
    Kape.command("do") {
        literal("send") {
            arg("player" to ArgumentTypes.player()) {
                execute {
                    val player = lastParameter.toPlayer()
                    arg("message" to ArgumentTypes.component()) {
                        player.sendMessage(lastParameter)
                    }
                }
            }
        }
    }
}
