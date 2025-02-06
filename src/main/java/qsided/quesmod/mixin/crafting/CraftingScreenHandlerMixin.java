package qsided.quesmod.mixin.crafting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.config.requirements.ItemCraftingRequirement;

import java.util.List;
import java.util.Optional;

import static qsided.quesmod.StateSaverAndLoader.getPlayerState;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {
    
    @Shadow @Final private ScreenHandlerContext context;
    @Shadow @Final private PlayerEntity player;
    private static ItemStack output = ItemStack.EMPTY;
    
    @Unique
    private static ItemStack getOutput() {
        return output;
    }
    
    @Unique
    private static void setOutput(ItemStack output) {
        CraftingScreenHandlerMixin.output = output;
    }
    
    /**
     * @author qSided
     * @reason Custom crafting logic based off skill level
     */
    
    @Overwrite
    public static void updateResult(ScreenHandler handler, ServerWorld world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, @Nullable RecipeEntry<CraftingRecipe> recipe) {
        
        setOutput(ItemStack.EMPTY);
        
        CraftingRecipeInput craftingRecipeInput = craftingInventory.createRecipeInput();
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
        Optional<RecipeEntry<CraftingRecipe>> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, world, recipe);
        if (optional.isPresent()) {
            RecipeEntry<CraftingRecipe> recipeEntry = optional.get();
            CraftingRecipe craftingRecipe = recipeEntry.value();
            PlayerData state = getPlayerState(serverPlayerEntity);
            int craftingLevel = state.skillLevels.getOrDefault("crafting", 1);
            if (resultInventory.shouldCraftRecipe(serverPlayerEntity, recipeEntry)) {
                ItemStack craftedItem = craftingRecipe.craft(craftingRecipeInput, world.getRegistryManager());
                
                if (containsItem(QuesMod.getCraftingReqs(), craftedItem.getItem().toString())) {
                    QuesMod.getCraftingReqs().forEach(requirement -> {
                        if (craftedItem.getItem().toString().equals(requirement.getItemId()) && !(craftingLevel >= requirement.getLevelReq())) {
                            ItemStack failedReqs = ItemStack.EMPTY;
                            setOutput(failedReqs);
                            System.out.println("Failed crafting skill reqs");
                        } else if (craftedItem.getItem().toString().equals(requirement.getItemId()) && craftingLevel >= requirement.getLevelReq() && craftedItem.isItemEnabled(world.getEnabledFeatures())) {
                            setOutput(craftedItem);
                            System.out.println("Succeeded crafting skill reqs");
                        }
                    });
                } else {
                    setOutput(craftedItem);
                    System.out.println("Item had no reqs");
                }
            }
        }
        
        resultInventory.setStack(0, getOutput());
        handler.setPreviousTrackedSlot(0, getOutput());
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, getOutput()));
    }
    
    @Unique
    private static boolean containsItem(List<ItemCraftingRequirement> items, String itemId) {
        return items.stream().map(ItemCraftingRequirement::getItemId).anyMatch(itemId::equals);
    }
}
