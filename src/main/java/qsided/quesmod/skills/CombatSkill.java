package qsided.quesmod.skills;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;
import qsided.quesmod.tags.entitytypes.QuesEntityTypeTags;

public class CombatSkill {
    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof ServerPlayerEntity player) {
                
                PlayerData state = StateSaverAndLoader.getPlayerState(player);
                float combatExp = state.skillExperience.getOrDefault("combat", 0F);
                int combatLevel = state.skillLevels.getOrDefault("combat", 1);
                
                if (combatLevel < 100) {
                    IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(player, state, "combat", combatExp + (12 + (killedEntity.getMaxHealth() / 4)));
                }
            }
        });
        
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
           if (entity instanceof LivingEntity livingEntity && !(entity instanceof PlayerEntity) && world.getClosestPlayer(livingEntity, 120) instanceof PlayerEntity player) {
               PlayerData state = StateSaverAndLoader.getPlayerState(player);
               
               if (!livingEntity.getType().isIn(QuesEntityTypeTags.NON_HOSTILE)) {
                   livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).overwritePersistentModifier(
                           new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "health_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 2), EntityAttributeModifier.Operation.ADD_VALUE));
                   livingEntity.setHealth(livingEntity.getMaxHealth());
                   if (livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                       livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).overwritePersistentModifier(
                               new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "damage_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 1.2), EntityAttributeModifier.Operation.ADD_VALUE));
                   }
                   livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).overwritePersistentModifier(
                           new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "armor_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 0.3), EntityAttributeModifier.Operation.ADD_VALUE));
                   livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).overwritePersistentModifier(
                           new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "armor_toughness_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 0.18), EntityAttributeModifier.Operation.ADD_VALUE));
                   livingEntity.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).overwritePersistentModifier(
                           new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "knockback_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 0.1), EntityAttributeModifier.Operation.ADD_VALUE));
               } else {
                   livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).overwritePersistentModifier(
                           new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "health_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 0.8), EntityAttributeModifier.Operation.ADD_VALUE));
                   livingEntity.setHealth(livingEntity.getMaxHealth());
                   if (livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
                       livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).overwritePersistentModifier(
                               new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "damage_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 0.4), EntityAttributeModifier.Operation.ADD_VALUE));
                   }
               }
           }
        });
    }
}
