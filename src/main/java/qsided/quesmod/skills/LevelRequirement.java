package qsided.quesmod.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import qsided.quesmod.PlayerData;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.config.requirements.ItemWithRequirements;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LevelRequirement {
    
    public static void register() {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, ItemWithRequirements.class);
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, equipmentSlot, previousStack, currentStack) -> {
            if (livingEntity instanceof PlayerEntity player && !player.isCreative()) {
                PlayerData state = StateSaverAndLoader.getPlayerState(player);
                try {
                    List<ItemWithRequirements> items = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/reqs.json"), typeReference);
                    items.forEach(item -> {
                        if (currentStack.getItem().toString().equals(item.getItemId())
                                && state.skillLevels.getOrDefault(item.getRequirements().getSkill(), 1) < item.getRequirements().getLevel()) {
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, -1,10));
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, -1,10));
                        } else if (previousStack.getItem().toString().equals(item.getItemId()) && player.hasStatusEffect(StatusEffects.SLOWNESS) || player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
                            player.removeStatusEffect(StatusEffects.SLOWNESS);
                            player.removeStatusEffect(StatusEffects.MINING_FATIGUE);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
            }
        });
    }
}
