package qsided.quesmod.skills;

import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import qsided.quesmod.PlayerData;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.events.EnchantItemCallback;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

public class EnchantingSkill {
    
    public static void register() {
        EnchantItemCallback.EVENT.register((player, stack, levelsSpent) -> {
            
            PlayerData state = StateSaverAndLoader.getPlayerState(player);
            float enchantingExp = state.skillExperience.getOrDefault("enchanting", 0F);
            int enchantingLevel = state.skillLevels.getOrDefault("enchanting", 1);
            
            if (enchantingLevel < 100) {
                IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(player, state, "enchanting", (float) (24 * levelsSpent));
            }
            
            return ActionResult.PASS;
        });
    }
}
