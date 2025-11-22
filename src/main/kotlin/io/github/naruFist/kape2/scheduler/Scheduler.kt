package io.github.naruFist.kape2.scheduler

import io.github.naruFist.kape2.Kape.Companion.plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask


class Scheduler(private val taskId: Int) {
    fun cancel() = plugin.server.scheduler.cancelTask(taskId)

    companion object {
        @JvmStatic
        fun later(tick: Long, block: () -> Unit) =
            plugin.server.scheduler.runTaskLater(plugin, Runnable(block), tick)

        @JvmStatic
        fun loop(tick: Long, n: Int = 0, block: Scheduler.() -> Unit) {
            var i = 0

            val runnable = object : BukkitRunnable() {
                override fun run() {
                    if (n != 0 && n < ++i) cancel()

                    block(Scheduler(this.taskId))
                }
            }

            runnable.runTaskTimer(plugin, 0L, tick)
        }
    }
}