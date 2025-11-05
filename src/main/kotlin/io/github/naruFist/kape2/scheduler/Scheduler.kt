package io.github.naruFist.kape2.scheduler

import io.github.naruFist.kape2.Kape.Companion.plugin
import io.github.naruFist.kape2.util.KapeSchedulerTagNotFoundException


class Scheduler {
    companion object {
        private var tagMap = mutableMapOf<String, Int>()

        @JvmStatic
        fun later(tick: Long, block: () -> Unit) =
            plugin.server.scheduler.runTaskLater(plugin, Runnable(block), tick)

        @JvmStatic
        fun loop(tag: String, tick: Long, n: Int = 0, block: () -> Unit) {
            val id = plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, Runnable(block), 0L, tick)

            tagMap[tag] = id

            if (n != 0) later(tick * n) { plugin.server.scheduler.cancelTask(id) }
        }

        @JvmStatic
        fun returnTask(tag: String) {
            if (tagMap[tag] != null) plugin.server.scheduler.cancelTask(tagMap[tag]!!)
            else throw KapeSchedulerTagNotFoundException()
        }
    }
}