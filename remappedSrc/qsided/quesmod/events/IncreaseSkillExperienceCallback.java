package qsided.quesmod.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import qsided.quesmod.PlayerData;

public interface IncreaseSkillExperienceCallback {
    
    Event<IncreaseSkillExperienceCallback> EVENT = EventFactory.createArrayBacked(IncreaseSkillExperienceCallback.class,
            (listeners) -> (player, state, skill, value) -> {
                for (IncreaseSkillExperienceCallback listener : listeners) {
                    ActionResult result = listener.increaseExp(player, state, skill, value);
                    
                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }
                
                return ActionResult.PASS;
            });
    
    ActionResult increaseExp(ServerPlayerEntity player, PlayerData state, String skill, Float value);
}
