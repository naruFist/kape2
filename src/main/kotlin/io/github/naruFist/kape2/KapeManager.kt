package io.github.naruFist.kape2

import io.github.naruFist.kape2.command.CommandNode
import io.github.naruFist.kape2.component.text
import io.github.naruFist.kape2.inventory.InventoryManager
import io.github.naruFist.kape2.scheduler.Scheduler
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.java.JavaPlugin
import kotlin.collections.forEach


val JavaPlugin.kapeManager: KapeManager
    get() = KapeManager(this)

fun JavaPlugin.kape(block: KapeManager.() -> Unit) = block(kapeManager)


class KapeManager {
    // Plugin Manager Part
    val plugin: JavaPlugin

    constructor(plugin: JavaPlugin) {
        this.plugin = plugin
    }

    fun disable() {
        // Memory Leak Prevent

        listeners.forEach {
            HandlerList.unregisterAll(it)
        }
    }


    // Listener Part
    val listeners: MutableList<Listener> = mutableListOf()


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
    fun log(text: Component) = plugin.server.sendMessage(text)

    fun log(string: String) = log(text = text(string))


    // Inventory Manager Part
    fun inventory(title: Component, line: Int, block: InventoryManager.() -> Unit = {}) =
        InventoryManager(plugin.server.createInventory(null, 9 * line, title)).apply { block(this) }.build()

    fun inv(title: Component, line: Int,  block: InventoryManager.() -> Unit = {}) =
        inventory(title, line, block)


    // Command Manager Part
    fun command(string: String, cmdManager: CommandNode.() -> Unit) {

        val rootCmd = Commands.literal(string)

        plugin.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            commands.registrar().register(rootCmd.build())
        }
        // 등록 될까요?
    }


    fun later(tick: Long, block: () -> Unit) = Scheduler.later(tick, block)

    fun loop(tick: Long, n: Int = 0, block: Scheduler.() -> Unit) = Scheduler.loop(tick, n, block)
}
