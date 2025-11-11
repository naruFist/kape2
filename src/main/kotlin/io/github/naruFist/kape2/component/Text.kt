package io.github.naruFist.kape2.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

fun text(string: String) = Component.text(string)

operator fun Component.plus(component: Component) = append(component)
operator fun TextComponent.plus(component: TextComponent) = append(component)
