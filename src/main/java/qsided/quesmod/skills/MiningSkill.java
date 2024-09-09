package qsided.quesmod.skills;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.networking.LevelUpPayload;

import static qsided.quesmod.QuesMod.sendSkillData;
import static qsided.quesmod.StateSaverAndLoader.getPlayerState;

public class MiningSkill {
    
    public static void registerMining() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            //StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
            
            PlayerData playerState = getPlayerState(player);
            
            //Mining skill leveling logic
            playerState.skillExperience.put("miningExp", (playerState.skillExperience.getOrDefault("miningExp", 0F) + (5 + (state.getBlock().getHardness() / 3))));
            if (playerState.skillExperience.get("miningExp") >= 120 * (playerState.skillLevels.get("miningLevel") * 3.4)) {
                Identifier miningModifier = Identifier.of(QuesMod.MOD_ID, "mining_modifier");
                
                playerState.skillExperience.put("miningExp", 0F);
                playerState.skillLevels.put("miningLevel", playerState.skillLevels.getOrDefault("miningLevel", 1) + 1);
                
                player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).overwritePersistentModifier(
                        new EntityAttributeModifier(miningModifier, playerState.skillLevels.get("miningLevel") * .25, EntityAttributeModifier.Operation.ADD_VALUE));
                
                ServerPlayNetworking.send((ServerPlayerEntity) player, new LevelUpPayload("Mining", playerState.skillLevels.get("miningLevel")));
                
                if (playerState.skillLevels.getOrDefault("miningLevel", 1) == 33) {
                    player.getAttributeInstance(EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED).overwritePersistentModifier(
                            new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "miningGoalOne"), 10, EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                }
            }
            
            sendSkillData(playerState, (ServerPlayerEntity) player);
        });
    }
}
