package qsided.quesmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import qsided.quesmod.PlayerData;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

@Mixin(ServerPlayerEntity.class)
public class IncreaseTravelMotionStatsMixin {
    
    @WrapOperation(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"))
    public void increaseStat(ServerPlayerEntity player, Identifier stat, int amount, Operation<Void> original) {
        PlayerData state = StateSaverAndLoader.getPlayerState(player);
        
        //if (stat.equals(Stats.SWIM_ONE_CM) || stat.equals(Stats.WALK_UNDER_WATER_ONE_CM) || stat.equals(Stats.WALK_ON_WATER_ONE_CM)) {
        //    IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(player, state, "swimming", (float) (amount / 24));
        //}
        if (stat.equals(Stats.WALK_ONE_CM) || stat.equals(Stats.SPRINT_ONE_CM) || stat.equals(Stats.CROUCH_ONE_CM) || stat.equals(Stats.CLIMB_ONE_CM)) {
            IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(player, state, "agility", 0.05F);
        }
    }
}
