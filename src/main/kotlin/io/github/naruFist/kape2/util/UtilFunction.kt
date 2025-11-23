package io.github.naruFist.kape2.util

import org.bukkit.Bukkit
import java.util.UUID

fun String.isPlayer() = Bukkit.getPlayer(this) != null

fun String.toPlayer() = Bukkit.getPlayer(this)!!

fun UUID.isPlayer() = Bukkit.getPlayer(this) != null

fun UUID.toPlayer() = Bukkit.getPlayer(this)!!