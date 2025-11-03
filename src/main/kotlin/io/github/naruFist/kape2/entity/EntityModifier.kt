package io.github.naruFist.kape2.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

fun LivingEntity.modify(block: EntityModifier.() -> Unit) {
    EntityModifier(this).run(block)
}

fun <T: Entity> Entity.modify(clazz: Class<T>, block: (T) -> Unit): T {
    val entity = clazz.cast(this)

    return entity.apply { run(block) }
}

class EntityModifier(private val entity: LivingEntity) {
    fun equipment(block: EquipState.() -> Unit) {
        val state = EquipState().apply(block)
        entity.equipment?.apply {
            setItemInMainHand(state.itemInMainHand)
            setItemInOffHand(state.itemInOffHand)

            helmet = state.helmet
            chestplate = state.chestplate
            leggings = state.leggings
            boots = state.boots
        }
    }

    fun potion(block: PotionStatus.() -> Unit) {
        val status = PotionStatus().apply(block)
        entity.addPotionEffects(status.potionEffects())
    }
}