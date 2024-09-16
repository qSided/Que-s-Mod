package qsided.quesmod.skills;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import qsided.quesmod.PlayerData;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

import java.util.Random;

import static qsided.quesmod.StateSaverAndLoader.getPlayerState;

public class MiningSkill {
    
    public static void register() {
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;
        
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, blockState, blockEntity) -> {
            
            PlayerData state = getPlayerState(player);
            float miningExp = state.skillExperience.getOrDefault("mining", 0F);
            int miningLevel = state.skillLevels.getOrDefault("mining", 1);
            
            //Mining skill leveling logic
            IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp((ServerPlayerEntity) player, state, "mining", miningExp + (5 + (blockState.getBlock().getHardness() / 3)));
            
            if (player.getEquippedStack(EquipmentSlot.MAINHAND).isIn(ItemTags.PICKAXES) || player.getEquippedStack(EquipmentSlot.MAINHAND).isIn(ItemTags.SHOVELS)
                    && player.getEquippedStack(EquipmentSlot.MAINHAND).isDamaged() && randomInt <= miningLevel) {
                player.getEquippedStack(EquipmentSlot.MAINHAND).setDamage(Math.max(player.getEquippedStack(EquipmentSlot.MAINHAND).getDamage() - 1, 0));
            }
        });
    }
}
