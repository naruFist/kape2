package io.github.naruFist.kape2.util

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


fun stacker(type: Material, amount: Int = 1, block: (ItemMeta) -> Unit = {}) =
    ItemStack(type, amount).apply { editMeta(block) }

fun ItemStack.enchant(type: Enchantment, level: Int = 1) =
    apply { addUnsafeEnchantment(type, level) }

fun <T: ItemMeta> ItemStack.meta(clazz: Class<T>, block: (T) -> Unit) =
    apply {
        val meta = clazz.cast(itemMeta)
        meta.apply(block)
        itemMeta = meta
    }