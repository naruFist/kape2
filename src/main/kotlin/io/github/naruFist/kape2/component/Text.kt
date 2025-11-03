package io.github.naruFist.kape2.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

fun text(string: String) = Component.text(string)

fun text(string: String, color: Color) = text(string).color(color)

operator fun Component.plus(component: Component) = append(component)
operator fun TextComponent.plus(component: TextComponent) = append(component)
