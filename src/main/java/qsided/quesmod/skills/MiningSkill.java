package qsided.quesmod.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.config.experience_values.BlockExperience;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static qsided.quesmod.StateSaverAndLoader.getPlayerState;

public class MiningSkill {
    
    public static void register() {
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;
        ObjectMapper mapper = new ObjectMapper();
        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, BlockExperience.class);
        
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, blockState, blockEntity) -> {
            
            PlayerData state = getPlayerState(player);
            int miningLevel = state.skillLevels.getOrDefault("mining", 1);
            
            try {
                List<BlockExperience> experience = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir()
                        + "/ques-mod/skills/mining.json"), typeReference);
                experience.forEach(block -> {
                    if (blockState.getBlock().asItem().toString().equals(block.getId())) {
                        
                        block.getExperience().forEach((skill, value) -> {
                            IncreaseSkillExperienceCallback.EVENT.invoker()
                                    .increaseExp((ServerPlayerEntity) player, state, skill, value);
                        });
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
            if (player.getEquippedStack(EquipmentSlot.MAINHAND).isIn(ItemTags.PICKAXES) || player.getEquippedStack(EquipmentSlot.MAINHAND).isIn(ItemTags.SHOVELS)
                    && player.getEquippedStack(EquipmentSlot.MAINHAND).isDamaged() && randomInt <= miningLevel) {
                player.getEquippedStack(EquipmentSlot.MAINHAND).setDamage(Math.max(player.getEquippedStack(EquipmentSlot.MAINHAND).getDamage() - 1, 0));
            }
        });
    }
}
