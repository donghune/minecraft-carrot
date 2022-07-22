package io.github.donghune

import io.github.donghune.api.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.event.*
import org.bukkit.event.entity.*
import org.bukkit.event.world.PortalCreateEvent

fun Entity.setOnAreaEffectCloudApplyEvent(function: (AreaEffectCloudApplyEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onAreaEffectCloudApplyEvent(event: AreaEffectCloudApplyEvent) {
            if (event.entity != this@setOnAreaEffectCloudApplyEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnArrowBodyCountChangeEvent(function: (ArrowBodyCountChangeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onArrowBodyCountChangeEvent(event: ArrowBodyCountChangeEvent) {
            if (event.entity != this@setOnArrowBodyCountChangeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnBatToggleSleepEvent(function: (BatToggleSleepEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onBatToggleSleepEvent(event: BatToggleSleepEvent) {
            if (event.entity != this@setOnBatToggleSleepEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnCreatureSpawnEvent(function: (CreatureSpawnEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onCreatureSpawnEvent(event: CreatureSpawnEvent) {
            if (event.entity != this@setOnCreatureSpawnEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnCreeperPowerEvent(function: (CreeperPowerEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onCreeperPowerEvent(event: CreeperPowerEvent) {
            if (event.entity != this@setOnCreeperPowerEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEnderDragonChangePhaseEvent(function: (EnderDragonChangePhaseEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEnderDragonChangePhaseEvent(event: EnderDragonChangePhaseEvent) {
            if (event.entity != this@setOnEnderDragonChangePhaseEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityAirChangeEvent(function: (EntityAirChangeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityAirChangeEvent(event: EntityAirChangeEvent) {
            if (event.entity != this@setOnEntityAirChangeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityBreakDoorEvent(function: (EntityBreakDoorEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityBreakDoorEvent(event: EntityBreakDoorEvent) {
            if (event.entity != this@setOnEntityBreakDoorEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityBreedEvent(function: (EntityBreedEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityBreedEvent(event: EntityBreedEvent) {
            if (event.entity != this@setOnEntityBreedEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityChangeBlockEvent(function: (EntityChangeBlockEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityChangeBlockEvent(event: EntityChangeBlockEvent) {
            if (event.entity != this@setOnEntityChangeBlockEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityCombustByBlockEvent(function: (EntityCombustByBlockEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityCombustByBlockEvent(event: EntityCombustByBlockEvent) {
            if (event.entity != this@setOnEntityCombustByBlockEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityCombustByEntityEvent(function: (EntityCombustByEntityEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityCombustByEntityEvent(event: EntityCombustByEntityEvent) {
            if (event.entity != this@setOnEntityCombustByEntityEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityCombustEvent(function: (EntityCombustEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityCombustEvent(event: EntityCombustEvent) {
            if (event.entity != this@setOnEntityCombustEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPortalCreateEvent(function: (PortalCreateEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPortalCreateEvent(event: PortalCreateEvent) {
            if (event.entity != this@setOnPortalCreateEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityDamageByEntityEvent(function: (EntityDamageByEntityEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
            if (event.entity != this@setOnEntityDamageByEntityEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityDamageEvent(function: (EntityDamageEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityDamageEvent(event: EntityDamageEvent) {
            if (event.entity != this@setOnEntityDamageEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityDeathEvent(function: (EntityDeathEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityDeathEvent(event: EntityDeathEvent) {
            if (event.entity != this@setOnEntityDeathEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityDropItemEvent(function: (EntityDropItemEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityDropItemEvent(event: EntityDropItemEvent) {
            if (event.entity != this@setOnEntityDropItemEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityEnterBlockEvent(function: (EntityEnterBlockEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityEnterBlockEvent(event: EntityEnterBlockEvent) {
            if (event.entity != this@setOnEntityEnterBlockEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityEnterLoveModeEvent(function: (EntityEnterLoveModeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityEnterLoveModeEvent(event: EntityEnterLoveModeEvent) {
            if (event.entity != this@setOnEntityEnterLoveModeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityEvent(function: (EntityEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityEvent(event: EntityEvent) {
            if (event.entity != this@setOnEntityEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityExhaustionEvent(function: (EntityExhaustionEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityExhaustionEvent(event: EntityExhaustionEvent) {
            if (event.entity != this@setOnEntityExhaustionEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityExplodeEvent(function: (EntityExplodeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityExplodeEvent(event: EntityExplodeEvent) {
            if (event.entity != this@setOnEntityExplodeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityInteractEvent(function: (EntityInteractEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityInteractEvent(event: EntityInteractEvent) {
            if (event.entity != this@setOnEntityInteractEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPickupItemEvent(function: (EntityPickupItemEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPickupItemEvent(event: EntityPickupItemEvent) {
            if (event.entity != this@setOnEntityPickupItemEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPlaceEvent(function: (EntityPlaceEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPlaceEvent(event: EntityPlaceEvent) {
            if (event.entity != this@setOnEntityPlaceEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPortalEnterEvent(function: (EntityPortalEnterEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPortalEnterEvent(event: EntityPortalEnterEvent) {
            if (event.entity != this@setOnEntityPortalEnterEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPortalEvent(function: (EntityPortalEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPortalEvent(event: EntityPortalEvent) {
            if (event.entity != this@setOnEntityPortalEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPortalExitEvent(function: (EntityPortalExitEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPortalExitEvent(event: EntityPortalExitEvent) {
            if (event.entity != this@setOnEntityPortalExitEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPoseChangeEvent(function: (EntityPoseChangeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPoseChangeEvent(event: EntityPoseChangeEvent) {
            if (event.entity != this@setOnEntityPoseChangeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityPotionEffectEvent(function: (EntityPotionEffectEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityPotionEffectEvent(event: EntityPotionEffectEvent) {
            if (event.entity != this@setOnEntityPotionEffectEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityRegainHealthEvent(function: (EntityRegainHealthEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityRegainHealthEvent(event: EntityRegainHealthEvent) {
            if (event.entity != this@setOnEntityRegainHealthEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityResurrectEvent(function: (EntityResurrectEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityResurrectEvent(event: EntityResurrectEvent) {
            if (event.entity != this@setOnEntityResurrectEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityShootBowEvent(function: (EntityShootBowEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityShootBowEvent(event: EntityShootBowEvent) {
            if (event.entity != this@setOnEntityShootBowEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntitySpawnEvent(function: (EntitySpawnEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntitySpawnEvent(event: EntitySpawnEvent) {
            if (event.entity != this@setOnEntitySpawnEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntitySpellCastEvent(function: (EntitySpellCastEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntitySpellCastEvent(event: EntitySpellCastEvent) {
            if (event.entity != this@setOnEntitySpellCastEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityTameEvent(function: (EntityTameEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityTameEvent(event: EntityTameEvent) {
            if (event.entity != this@setOnEntityTameEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityTargetEvent(function: (EntityTargetEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityTargetEvent(event: EntityTargetEvent) {
            if (event.entity != this@setOnEntityTargetEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityTargetLivingEntityEvent(function: (EntityTargetLivingEntityEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityTargetLivingEntityEvent(event: EntityTargetLivingEntityEvent) {
            if (event.entity != this@setOnEntityTargetLivingEntityEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityTeleportEvent(function: (EntityTeleportEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityTeleportEvent(event: EntityTeleportEvent) {
            if (event.entity != this@setOnEntityTeleportEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityToggleGlideEvent(function: (EntityToggleGlideEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityToggleGlideEvent(event: EntityToggleGlideEvent) {
            if (event.entity != this@setOnEntityToggleGlideEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityToggleSwimEvent(function: (EntityToggleSwimEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityToggleSwimEvent(event: EntityToggleSwimEvent) {
            if (event.entity != this@setOnEntityToggleSwimEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityTransformEvent(function: (EntityTransformEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityTransformEvent(event: EntityTransformEvent) {
            if (event.entity != this@setOnEntityTransformEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnEntityUnleashEvent(function: (EntityUnleashEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onEntityUnleashEvent(event: EntityUnleashEvent) {
            if (event.entity != this@setOnEntityUnleashEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnExpBottleEvent(function: (ExpBottleEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onExpBottleEvent(event: ExpBottleEvent) {
            if (event.entity != this@setOnExpBottleEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnExplosionPrimeEvent(function: (ExplosionPrimeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onExplosionPrimeEvent(event: ExplosionPrimeEvent) {
            if (event.entity != this@setOnExplosionPrimeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnFireworkExplodeEvent(function: (FireworkExplodeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onFireworkExplodeEvent(event: FireworkExplodeEvent) {
            if (event.entity != this@setOnFireworkExplodeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnFoodLevelChangeEvent(function: (FoodLevelChangeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onFoodLevelChangeEvent(event: FoodLevelChangeEvent) {
            if (event.entity != this@setOnFoodLevelChangeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnHorseJumpEvent(function: (HorseJumpEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onHorseJumpEvent(event: HorseJumpEvent) {
            if (event.entity != this@setOnHorseJumpEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnItemDespawnEvent(function: (ItemDespawnEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onItemDespawnEvent(event: ItemDespawnEvent) {
            if (event.entity != this@setOnItemDespawnEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnItemMergeEvent(function: (ItemMergeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onItemMergeEvent(event: ItemMergeEvent) {
            if (event.entity != this@setOnItemMergeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnItemSpawnEvent(function: (ItemSpawnEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onItemSpawnEvent(event: ItemSpawnEvent) {
            if (event.entity != this@setOnItemSpawnEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnLingeringPotionSplashEvent(function: (LingeringPotionSplashEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onLingeringPotionSplashEvent(event: LingeringPotionSplashEvent) {
            if (event.entity != this@setOnLingeringPotionSplashEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPiglinBarterEvent(function: (PiglinBarterEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPiglinBarterEvent(event: PiglinBarterEvent) {
            if (event.entity != this@setOnPiglinBarterEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPigZapEvent(function: (PigZapEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPigZapEvent(event: PigZapEvent) {
            if (event.entity != this@setOnPigZapEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPigZombieAngerEvent(function: (PigZombieAngerEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPigZombieAngerEvent(event: PigZombieAngerEvent) {
            if (event.entity != this@setOnPigZombieAngerEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPlayerDeathEvent(function: (PlayerDeathEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPlayerDeathEvent(event: PlayerDeathEvent) {
            if (event.entity != this@setOnPlayerDeathEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPlayerLeashEntityEvent(function: (PlayerLeashEntityEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPlayerLeashEntityEvent(event: PlayerLeashEntityEvent) {
            if (event.entity != this@setOnPlayerLeashEntityEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnPotionSplashEvent(function: (PotionSplashEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onPotionSplashEvent(event: PotionSplashEvent) {
            if (event.entity != this@setOnPotionSplashEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnProjectileHitEvent(function: (ProjectileHitEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onProjectileHitEvent(event: ProjectileHitEvent) {
            if (event.entity != this@setOnProjectileHitEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnProjectileLaunchEvent(function: (ProjectileLaunchEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onProjectileLaunchEvent(event: ProjectileLaunchEvent) {
            if (event.entity != this@setOnProjectileLaunchEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnSheepDyeWoolEvent(function: (SheepDyeWoolEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onSheepDyeWoolEvent(event: SheepDyeWoolEvent) {
            if (event.entity != this@setOnSheepDyeWoolEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnSheepRegrowWoolEvent(function: (SheepRegrowWoolEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onSheepRegrowWoolEvent(event: SheepRegrowWoolEvent) {
            if (event.entity != this@setOnSheepRegrowWoolEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnSlimeSplitEvent(function: (SlimeSplitEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onSlimeSplitEvent(event: SlimeSplitEvent) {
            if (event.entity != this@setOnSlimeSplitEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnSpawnerSpawnEvent(function: (SpawnerSpawnEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onSpawnerSpawnEvent(event: SpawnerSpawnEvent) {
            if (event.entity != this@setOnSpawnerSpawnEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnStriderTemperatureChangeEvent(function: (StriderTemperatureChangeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onStriderTemperatureChangeEvent(event: StriderTemperatureChangeEvent) {
            if (event.entity != this@setOnStriderTemperatureChangeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnVillagerAcquireTradeEvent(function: (VillagerAcquireTradeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onVillagerAcquireTradeEvent(event: VillagerAcquireTradeEvent) {
            if (event.entity != this@setOnVillagerAcquireTradeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnVillagerCareerChangeEvent(function: (VillagerCareerChangeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onVillagerCareerChangeEvent(event: VillagerCareerChangeEvent) {
            if (event.entity != this@setOnVillagerCareerChangeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}

fun Entity.setOnVillagerReplenishTradeEvent(function: (VillagerReplenishTradeEvent) -> Unit) {
    Bukkit.getPluginManager().registerEvents(object : Listener {
        @EventHandler
        fun onVillagerReplenishTradeEvent(event: VillagerReplenishTradeEvent) {
            if (event.entity != this@setOnVillagerReplenishTradeEvent) {
                return
            }
            function(event)
        }
    }, plugin)
}