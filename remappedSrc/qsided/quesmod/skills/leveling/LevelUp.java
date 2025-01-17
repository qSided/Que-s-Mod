package qsided.quesmod.skills.leveling;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;
import qsided.quesmod.events.IncreaseSkillLevelCallback;
import qsided.quesmod.networking.LevelUpPayload;

import static qsided.quesmod.QuesMod.sendSkillData;

public class LevelUp {
    public static void onLevelUp() {
        IncreaseSkillLevelCallback.EVENT.register((player, state, skill, value) -> {
            
            state.skillExperience.put(skill, (float) Math.max((state.skillExperience.getOrDefault(skill, 0F) - (60 * (state.skillLevels.getOrDefault(skill, 1) * 3.4))), 0));
            state.skillLevels.put(skill, state.skillLevels.getOrDefault(skill, 1) + value);
            ServerPlayNetworking.send(player, new LevelUpPayload(skill, state.skillLevels.get(skill)));
            
            switch (skill) {
                case "mining" -> {
                    if (state.skillLevels.getOrDefault(skill, 1) == 33) {
                        player.getAttributeInstance(EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED).overwritePersistentModifier(
                                new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "mining_one"), 10,
                                        EntityAttributeModifier.Operation.ADD_VALUE)
                        );
                    }
                }
                case "combat" -> {
                    Identifier combatModifier = Identifier.of(QuesMod.MOD_ID, "combat_modifier");
                    
                    player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).overwritePersistentModifier(
                            new EntityAttributeModifier(combatModifier, state.skillLevels.getOrDefault(skill, 1) * .18,
                                    EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                    player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED).overwritePersistentModifier(
                            new EntityAttributeModifier(combatModifier, state.skillLevels.getOrDefault(skill, 1) * .03,
                                    EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                }
                case "endurance" -> {
                    Identifier enduranceModifier = Identifier.of(QuesMod.MOD_ID, "endurance_modifier");
                    
                    player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).overwritePersistentModifier(
                            new EntityAttributeModifier(enduranceModifier, state.skillLevels.getOrDefault(skill, 1) * 2,
                                    EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                }
                case "agility" -> {
                    Identifier agilityModifier = Identifier.of(QuesMod.MOD_ID, "agility_modifier");
                    
                    player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).overwritePersistentModifier(
                            new EntityAttributeModifier(agilityModifier, state.skillLevels.getOrDefault(skill, 1) * 0.001, EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                    player.getAttributeInstance(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE).overwritePersistentModifier(
                            new EntityAttributeModifier(agilityModifier, state.skillLevels.getOrDefault(skill, 1) * 0.1, EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                    player.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).overwritePersistentModifier(
                            new EntityAttributeModifier(agilityModifier, state.skillLevels.getOrDefault(skill, 1) * 0.0058, EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                }
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
    }
}
