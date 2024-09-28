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
            
            state.skillExperience.put(skill, (float) Math.max((state.skillExperience.getOrDefault(skill, 0F) - (120 * (state.skillLevels.getOrDefault(skill, 1) * 3.4))), 0));
            state.skillLevels.put(skill, state.skillLevels.getOrDefault(skill, 1) + value);
            ServerPlayNetworking.send(player, new LevelUpPayload(skill, state.skillLevels.get(skill)));
            
            if (skill.equals("mining")) {
                Identifier miningModifier = Identifier.of(QuesMod.MOD_ID, "mining_modifier");
                
                player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).overwritePersistentModifier(
                        new EntityAttributeModifier(miningModifier, state.skillLevels.getOrDefault(skill, 1) * .5, EntityAttributeModifier.Operation.ADD_VALUE));
                
                if (state.skillLevels.getOrDefault(skill, 1) == 33) {
                    player.getAttributeInstance(EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED).overwritePersistentModifier(
                            new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "mining_one"), 10, EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                }
            }
            
            if (skill.equals("combat")) {
                Identifier combatModifier = Identifier.of(QuesMod.MOD_ID, "combat_modifier");
                
                player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).overwritePersistentModifier(
                        new EntityAttributeModifier(combatModifier, state.skillLevels.getOrDefault(skill, 1) * .18, EntityAttributeModifier.Operation.ADD_VALUE)
                );
                player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED).overwritePersistentModifier(
                        new EntityAttributeModifier(combatModifier, state.skillLevels.getOrDefault(skill, 1) * .03, EntityAttributeModifier.Operation.ADD_VALUE)
                );
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
    }
}
