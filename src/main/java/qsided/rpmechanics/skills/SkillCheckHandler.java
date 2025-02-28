package qsided.rpmechanics.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import qsided.rpmechanics.PlayerData;
import qsided.rpmechanics.RoleplayMechanicsCommon;
import qsided.rpmechanics.StateSaverAndLoader;
import qsided.rpmechanics.config.requirements.ItemWithRequirements;
import qsided.rpmechanics.config.requirements.Requirements;
import qsided.rpmechanics.config.roleplay_classes.RoleplayClass;

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
                
                if (RoleplayMechanicsCommon.OWO_CONFIG.enableRequirements()) {
                    try {
                        List<ItemWithRequirements> items = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir() + "/rpmechanics/reqs.json"), typeReference);
                        items.forEach(item -> {
                            //Check if current item being iterated is the same id as one currently equipped.
                            if (currentStack.getItem().toString().equals(item.getItemId())) {
                                if (
                                    //Check if the skill requirement for that items is the same or greater than player's current level.
                                    state.skillLevels.getOrDefault(item.getRequirements().getSkill(), 1) < item.getRequirements().getSkillLevel()
                                    //Check if the player's class is the required one
                                    || (!state.rpClass.equals(RoleplayMechanicsCommon.getRpClasses().getOrDefault(item.getRequirements().getRpClassId(), new RoleplayClass("", "", "", null, null, null)).getName()) && !item.getRequirements().getRpClassId().equals(-1))) {
                                player.dropItem(currentStack, true);
                                player.equipStack(equipmentSlot, Items.AIR.getDefaultStack());
                                player.sendMessage(Text.translatable("skills.rpmechanics.failed_reqs"), false);
                            }
                        }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    
                    Identifier efficiencyModifier = Identifier.of(RoleplayMechanicsCommon.MOD_ID, "efficiency_modifier");
                    if (currentStack.isIn(ItemTags.PICKAXES) || currentStack.isIn(ItemTags.SHOVELS) && equipmentSlot.equals(EquipmentSlot.MAINHAND)) {
                        player.getAttributeInstance(EntityAttributes.MINING_EFFICIENCY).overwritePersistentModifier(
                                new EntityAttributeModifier(efficiencyModifier, state.skillLevels.getOrDefault("mining", 1) * .5, EntityAttributeModifier.Operation.ADD_VALUE));
                        RoleplayMechanicsCommon.LOGGER.info(String.valueOf(player.getAttributeInstance(EntityAttributes.MINING_EFFICIENCY).getModifier(efficiencyModifier)));
                    } else if (currentStack.isIn(ItemTags.AXES) && equipmentSlot.equals(EquipmentSlot.MAINHAND)) {
                        player.getAttributeInstance(EntityAttributes.MINING_EFFICIENCY).overwritePersistentModifier(
                                new EntityAttributeModifier(efficiencyModifier, state.skillLevels.getOrDefault("woodcutting", 1) * .5, EntityAttributeModifier.Operation.ADD_VALUE)
                        );
                        RoleplayMechanicsCommon.LOGGER.info(String.valueOf(player.getAttributeInstance(EntityAttributes.MINING_EFFICIENCY).getModifier(efficiencyModifier)));
                    } else {
                        player.getAttributeInstance(EntityAttributes.MINING_EFFICIENCY).removeModifier(efficiencyModifier);
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
