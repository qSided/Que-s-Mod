package qsided.quesmod.skills.leveling;

import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;
import qsided.quesmod.events.IncreaseSkillLevelCallback;

import static qsided.quesmod.QuesMod.sendSkillData;

public class ExperienceUp {
    public static void onExperienceUp() {
        IncreaseSkillExperienceCallback.EVENT.register((player, state, skill, value) -> {
            if (state.skillLevels.getOrDefault(skill, 1) < 100) {
                state.skillExperience.put(skill, value);
            }
            player.sendMessage(Text.of("text"), true);
            if (state.skillExperience.getOrDefault(skill, 0F) >= 120 * (state.skillLevels.getOrDefault(skill, 1) * 3.4)) {
                IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1);
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
    }
}
