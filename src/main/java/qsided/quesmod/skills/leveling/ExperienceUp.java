package qsided.quesmod.skills.leveling;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.ActionResult;
import qsided.quesmod.PlayerData;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;
import qsided.quesmod.events.IncreaseSkillLevelCallback;
import qsided.quesmod.networking.SendPlayerFallPayload;
import qsided.quesmod.networking.SendPlayerJumpPayload;

import java.text.DecimalFormat;

import static qsided.quesmod.QuesMod.sendSkillData;

public class ExperienceUp {
    public static void onExperienceUp() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        IncreaseSkillExperienceCallback.EVENT.register((player, state, skill, value) -> {
            if (state.skillLevels.getOrDefault(skill, 1) < 100) {
                state.skillExperience.put(skill, state.skillExperience.getOrDefault(skill, 0F) + value);
            }
            
            //player.sendMessage(Text.translatable("skills.ques-mod." + skill).append(Text.of(" +" + df.format(value) + "XP")).formatted(Formatting.BOLD, Formatting.GRAY), true);
            if (state.skillExperience.getOrDefault(skill, 0F) >= 60 * (state.skillLevels.getOrDefault(skill, 1) * 3.4)) {
                IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1);
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
    }
}
