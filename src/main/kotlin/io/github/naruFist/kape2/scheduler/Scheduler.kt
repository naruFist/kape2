package io.github.naruFist.kape2.scheduler

import io.github.naruFist.kape2.Kape.Companion.plugin


class Scheduler(private val taskId: Int) {
    fun cancel() = plugin.server.scheduler.cancelTask(taskId)

    companion object {
        @JvmStatic
        fun later(tick: Long, block: () -> Unit) =
            plugin.server.scheduler.runTaskLater(plugin, Runnable(block), tick)

        @JvmStatic
        fun loop(tick: Long, n: Int = 0, block: Scheduler.() -> Unit) {
            var tempId = -1

            val id = plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, Runnable {
                val scheduler = Scheduler(tempId)
                scheduler.block()
            }, 0L, tick)

            tempId = id

            if (n != 0) later(tick * n) { plugin.server.scheduler.cancelTask(id) }
        }
    }
}