package qsided.quesmod.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import qsided.quesmod.PlayerData;
import qsided.quesmod.config.experience_values.BlockExperience;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static qsided.quesmod.StateSaverAndLoader.getPlayerState;

public class WoodcuttingSkill {
    public static void register() {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, BlockExperience.class);
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            
            try {
                List<BlockExperience> experience = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir()
                        + "/ques-mod/skills/woodcutting.json"), typeReference);
                experience.forEach(block -> {
                    if (state.getBlock().asItem().toString().equals(block.getId())) {
                        PlayerData data = getPlayerState(player);
                        
                        block.getExperience().forEach((skill, value) -> {
                            IncreaseSkillExperienceCallback.EVENT.invoker()
                                    .increaseExp((ServerPlayerEntity) player, data, skill, data.skillExperience.getOrDefault(skill, 0F) + value);
                        });
                        
                    }
                });
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}