package qsided.quesmod.skills;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

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
           if (entity instanceof LivingEntity livingEntity && !(entity instanceof PlayerEntity) && world.getClosestPlayer(livingEntity, 30) instanceof PlayerEntity player) {
               PlayerData state = StateSaverAndLoader.getPlayerState(player);
               
               livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).overwritePersistentModifier(
                       new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "player_health_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 4), EntityAttributeModifier.Operation.ADD_VALUE));
               livingEntity.setHealth(livingEntity.getMaxHealth());
               livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).overwritePersistentModifier(
                       new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "player_damage_scaling"), (state.skillLevels.getOrDefault("combat", 1)), EntityAttributeModifier.Operation.ADD_VALUE));
               livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).overwritePersistentModifier(
                       new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "player_armor_scaling"), (state.skillLevels.getOrDefault("combat", 1) * 1.2), EntityAttributeModifier.Operation.ADD_VALUE));
               livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).overwritePersistentModifier(
                       new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "player_armor_toughness_scaling"), (state.skillLevels.getOrDefault("combat", 1)), EntityAttributeModifier.Operation.ADD_VALUE));
           }
        });
    }
}
