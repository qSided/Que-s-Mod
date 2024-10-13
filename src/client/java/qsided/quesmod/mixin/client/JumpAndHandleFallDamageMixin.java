package qsided.quesmod.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import qsided.quesmod.networking.SendPlayerFallPayload;
import qsided.quesmod.networking.SendPlayerJumpPayload;

@Mixin(PlayerEntity.class)
public class JumpAndHandleFallDamageMixin {
    
    @WrapOperation(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"))
    public void handleFallDamage(PlayerEntity player, Identifier stat, int fallDistance, Operation<Void> original) {
        ClientPlayNetworking.send(new SendPlayerFallPayload(fallDistance));
    }
    
    @WrapOperation(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V"))
    public void jump(PlayerEntity player, Identifier stat, Operation<Void> original) {
        ClientPlayNetworking.send(new SendPlayerJumpPayload(1));
    }
    
}
