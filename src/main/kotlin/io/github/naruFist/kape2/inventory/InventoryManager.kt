package io.github.naruFist.kape2.inventory

import io.github.naruFist.kape2.Kape
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


fun Player.showInv(line: Int, title: Component) = openInventory(Kape.inventory(line, title))

class InventoryManager(private val inventory: Inventory) {
    fun set(slot: Int, item: ItemStack) = inventory.setItem(slot, item)

    fun build() = inventory
}
