package io.github.naruFist.kape2.entity

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity

fun Location.summonEntity(entityType: EntityType, block: EntityModifier.() -> Unit = {}): Entity {
    val entity = world.spawnEntity(this, entityType)

    if (entity is LivingEntity) EntityModifier(entity).run(block)

    return entity
}

fun <T: Entity>Location.summon(clazz: Class<T>) = world.spawn(this, clazz)

fun Entity.forward(distance: Double) = location.add(location.direction.multiply(distance))
