package qsided.quesmod.mixin.crafting;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.config.requirements.ItemCraftingRequirement;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;

import java.util.List;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {
    
    @Shadow @Final private PlayerEntity player;
    
    @WrapMethod(method = "onCrafted(Lnet/minecraft/item/ItemStack;)V")
    public void onCrafted(ItemStack stack, Operation<Void> original) {
        if (!player.getWorld().isClient) {
            awardXp(QuesMod.getCraftingReqs(), stack.getItem().toString());
        }
    }
    
    @Unique
    private void awardXp(List<ItemCraftingRequirement> items, String itemId) {
        items.stream().filter(item -> item.getItemId().equals(itemId)).forEach(
                item -> {
                    PlayerData state = StateSaverAndLoader.getPlayerState(player);
                    IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp((ServerPlayerEntity) player, state, "crafting", item.getExpWorth());
                    System.out.println("Crafted " + item.getItemId() + " for " + item.getExpWorth() + "XP");
                }
        );
    }
    
}
