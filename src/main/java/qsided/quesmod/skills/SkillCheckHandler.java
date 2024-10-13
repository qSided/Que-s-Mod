package qsided.quesmod.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import qsided.quesmod.PlayerData;
import qsided.quesmod.QuesMod;
import qsided.quesmod.StateSaverAndLoader;
import qsided.quesmod.config.requirements.ItemWithRequirements;
import qsided.quesmod.config.requirements.Requirements;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SkillCheckHandler {
    
    public static ItemWithRequirements lastItemNotMet = new ItemWithRequirements("null", new Requirements());
    
    public static void register() {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, ItemWithRequirements.class);
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, equipmentSlot, previousStack, currentStack) -> {
            if (livingEntity instanceof PlayerEntity player && !player.isCreative()) {
                PlayerData state = StateSaverAndLoader.getPlayerState(player);
                
                if (QuesMod.OWO_CONFIG.enableRequirements()) {
                    try {
                        List<ItemWithRequirements> items = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/reqs.json"), typeReference);
                        items.forEach(item -> {
                            if (currentStack.getItem().toString().equals(item.getItemId())
                                    && state.skillLevels.getOrDefault(item.getRequirements().getSkill(), 1) < item.getRequirements().getLevel()) {
                                player.dropItem(currentStack, true);
                                player.equipStack(equipmentSlot, Items.AIR.getDefaultStack());
                                player.sendMessage(Text.translatable("skills.ques-mod.failed_reqs"), false);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    
                    Identifier efficiencyModifier = Identifier.of(QuesMod.MOD_ID, "efficiency_modifier");
                    if (currentStack.isIn(ItemTags.PICKAXES) || currentStack.isIn(ItemTags.SHOVELS) && equipmentSlot.equals(EquipmentSlot.MAINHAND)) {
                        player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).overwritePersistentModifier(
                                new EntityAttributeModifier(efficiencyModifier, state.skillLevels.getOrDefault("mining", 1) * .5, EntityAttributeModifier.Operation.ADD_VALUE));
                        QuesMod.LOGGER.info(String.valueOf(player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).getModifier(efficiencyModifier)));
                    } else if (currentStack.isIn(ItemTags.AXES) && equipmentSlot.equals(EquipmentSlot.MAINHAND)) {
                        player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).overwritePersistentModifier(
                                new EntityAttributeModifier(efficiencyModifier, state.skillLevels.getOrDefault("woodcutting", 1) * .5, EntityAttributeModifier.Operation.ADD_VALUE)
                        );
                        QuesMod.LOGGER.info(String.valueOf(player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).getModifier(efficiencyModifier)));
                    } else {
                        player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).removeModifier(efficiencyModifier);
                    }
                }
            }
        });
    }
    
    public static ItemWithRequirements getLastItemNotMet() {
        return lastItemNotMet;
    }
    
    public static void setLastItemNotMet(ItemWithRequirements lastItemNotMet) {
        SkillCheckHandler.lastItemNotMet = lastItemNotMet;
    }
}
