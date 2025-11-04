package io.github.naruFist.kape2.scheduler

import io.github.naruFist.kape2.Kape.Companion.plugin

class Scheduler {
    companion object {
        @JvmStatic
        fun later(tick: Long, block: () -> Unit) =
            plugin.server.scheduler.runTaskLater(plugin, Runnable(block), tick)

        @JvmStatic
        fun loop(tick: Long, n: Int = 0, block: () -> Unit) {
            val id = plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, Runnable(block), 0L, tick)

            if (n != 0) later(tick * n) { plugin.server.scheduler.cancelTask(id) }
        }
    }
}