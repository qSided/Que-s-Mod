package qsided.rpmechanics.gui.skills;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Color;
import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import io.wispforest.owo.ui.core.Positioning;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import qsided.rpmechanics.RoleplayMechanicsCommon;
import qsided.rpmechanics.RoleplayMechanicsClient;

import java.text.DecimalFormat;
import java.util.List;

public class AgilitySkillScreen extends BaseUIModelScreen<FlowLayout> {
    public static Integer agilityLevel = 1;
    public static Float agilityExperience = 0F;
    public static Double safeDistance = 3.0;
    
    
    public static Double jumpStrength = 0.42;
    
    public AgilitySkillScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(RoleplayMechanicsCommon.MOD_ID, "agility")));
    }
    
    public static void setAgilityLevel(int level) {
        AgilitySkillScreen.agilityLevel = level;
    }
    public static void setAgilityExperience(float experience) {
        AgilitySkillScreen.agilityExperience = experience;
    }
    public static void setJumpStrength(Double jumpStrength) {
        AgilitySkillScreen.jumpStrength = jumpStrength;
    }
    public static void setSafeDistance(Double safeDistance) {
        AgilitySkillScreen.safeDistance = safeDistance;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        DecimalFormat df2 = new DecimalFormat();
        df2.setMaximumFractionDigits(0);
        
        List<Component> gridChildren = List.of(
                Components.label(Text.translatable("skills.rpmechanics.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT)
        );
        
        rootComponent.childById(GridLayout.class, "agility")
                .child(
                        Components.label(Text.translatable("skills.rpmechanics.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                0,
                0)
                .child(
                        Components.label(Text.of(String.valueOf(agilityLevel)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        0,
                        2)
                
                .child(
                        Components.label(Text.translatable("skills.rpmechanics.current_experience"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        1,
                        0)
                .child(
                        Components.label(Text.translatable("skills.rpmechanics.agility.movement_speed"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        2,
                        0)
                .child(
                        Components.label(Text.translatable("skills.rpmechanics.agility.jump_strength"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        3,
                        0)
                .child(
                        Components.label(Text.translatable("skills.rpmechanics.agility.safe_distance"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        4,
                        0)
                .child(
                        Components.label(Text.of("+" + agilityLevel + "%"))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        2,
                        2)
                .child(
                        Components.label(Text.of(df.format(jumpStrength)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        3,
                        2)
                .child(
                        Components.label(Text.of(df.format(safeDistance) + " Blocks"))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        4,
                        2);
        
        if (agilityLevel < 100) {
            
            if (!RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.useGlobal()) {
                switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.multiplicativeOrAdditive()) {
                    case ADD -> rootComponent.childById(GridLayout.class, "agility")
                            .child(
                                    Components.label(Text.of(df.format(agilityExperience) + "/" + df.format(RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.baseExperience() + (agilityLevel * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.amount()))))
                                            .color(Color.ofArgb(0xd1d0cd))
                                            .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                                    1,
                                    2);
                    case MULTIPLY -> rootComponent.childById(GridLayout.class, "agility")
                            .child(
                                    Components.label(Text.of(df.format(agilityExperience) + "/" + df.format(RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.baseExperience() * (agilityLevel * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.amount()))))
                                            .color(Color.ofArgb(0xd1d0cd))
                                            .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                                    1,
                                    2);
                }
            } else {
                switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.multiplicativeOrAdditive()) {
                    case ADD -> rootComponent.childById(GridLayout.class, "agility")
                            .child(
                                    Components.label(Text.of(df.format(agilityExperience) + "/" + df.format(RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.baseExperience() + (agilityLevel * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.amount()))))
                                            .color(Color.ofArgb(0xd1d0cd))
                                            .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                                    1,
                                    2);
                    case MULTIPLY -> rootComponent.childById(GridLayout.class, "agility")
                            .child(
                                    Components.label(Text.of(df.format(agilityExperience) + "/" + df.format(RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.baseExperience() * (agilityLevel * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.amount()))))
                                            .color(Color.ofArgb(0xd1d0cd))
                                            .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                                    1,
                                    2);
                }
            }
            
        } else {
            rootComponent.childById(GridLayout.class, "agility")
                    .child(
                            Components.label(Text.translatable("skills.rpmechanics.max_level"))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        }
        
        rootComponent.childById(DropdownComponent.class, "skill-selection")
                .text(Text.translatable("skills.rpmechanics.select_skill"))
                .divider()
                .button(Text.translatable("skills.rpmechanics.mining"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("mining");
                    client.setScreen(new MiningSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.rpmechanics.woodcutting"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("woodcutting");
                    client.setScreen(new WoodcuttingSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.rpmechanics.enchanting"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("enchanting");
                    client.setScreen(new EnchantingSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.rpmechanics.crafting"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("crafting");
                    client.setScreen(new CraftingSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.rpmechanics.combat"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("combat");
                    client.setScreen(new CombatSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.rpmechanics.endurance"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("endurance");
                    client.setScreen(new EnduranceSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.rpmechanics.agility"), button -> {
                    RoleplayMechanicsClient.setLastScreenOpen("agility");
                    client.setScreen(new AgilitySkillScreen());
                })
                .positioning(Positioning.absolute(0, 0));
    }
}
