package io.github.naruFist.kape2.util

import org.bukkit.Bukkit
import java.util.UUID

fun String.toPlayer() = Bukkit.getPlayer(this)

fun UUID.toPlayer() = Bukkit.getPlayer(this)