package qsided.quesmod.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import qsided.quesmod.PlayerData;

public interface RoleplayClassSelectedCallback {
    
    Event<RoleplayClassSelectedCallback> EVENT = EventFactory.createArrayBacked(RoleplayClassSelectedCallback.class,
            (listeners) -> (player, state, rpClass) -> {
                for (RoleplayClassSelectedCallback listener : listeners) {
                    ActionResult result = listener.selectClass(player, state, rpClass);
                    
                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }
                
                return ActionResult.PASS;
            });
    
    ActionResult selectClass(ServerPlayerEntity player, PlayerData state, String rpClass);
}
