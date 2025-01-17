package qsided.quesmod.skills;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import qsided.quesmod.PlayerData;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.events.PlayerHurtByEntityCallback;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

public class EnduranceSkill {
    public static void register() {
        PlayerHurtByEntityCallback.EVENT.register((player, source, dmgDealt, dmgTaken, wasBlocked) -> {
            PlayerData state = StateSaverAndLoader.getPlayerState(player);
            
            if (!wasBlocked) {
                IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp((ServerPlayerEntity) player, state, "endurance", 3 + (dmgTaken));
            } else {
                IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp((ServerPlayerEntity) player, state, "endurance", 6 + (dmgDealt));
            }
            
            return ActionResult.PASS;
        });
    }
}
