package qsided.quesmod.gui;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Color;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import io.wispforest.owo.ui.core.Positioning;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

import java.text.DecimalFormat;

public class WoodcuttingSkillScreen extends BaseUIModelScreen<FlowLayout> {
    public static Integer woodcuttingLevel = 1;
    public static Float woodcuttingExperience = 0F;
    public WoodcuttingSkillScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(QuesMod.MOD_ID, "woodcutting")));
    }
    
    public static void setWoodcuttingLevel(int level) {
        WoodcuttingSkillScreen.woodcuttingLevel = level;
    }
    public static void setWoodcuttingExperience(float experience) {
        WoodcuttingSkillScreen.woodcuttingExperience = experience;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        rootComponent.childById(GridLayout.class, "woodcutting")
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                0,
                0)
                .child(
                        Components.label(Text.of(String.valueOf(woodcuttingLevel)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        0,
                        2)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_experience"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        1,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.woodcutting.efficiency"))
                            .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        2,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.woodcutting.degrade_chance"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        3,
                        0)
                .child(
                        Components.label(Text.of("-" + woodcuttingLevel + "%"))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        3,
                        2);
        
        if (woodcuttingLevel.equals(1)) {
            rootComponent.childById(GridLayout.class, "woodcutting")
                    .child(
                            Components.label(Text.of(String.valueOf(0)))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "woodcutting")
                    .child(
                        Components.label(Text.of(String.valueOf(woodcuttingLevel * .5)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        2,
                        2);
        }
        
        if (woodcuttingLevel < 100) {
            rootComponent.childById(GridLayout.class, "woodcutting")
                    .child(
                            Components.label(Text.of(df.format(woodcuttingExperience) + "/" + df.format(120 * (woodcuttingLevel * 3.8))))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "woodcutting")
                    .child(
                            Components.label(Text.translatable("skills.ques-mod.max_level"))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        }
        
        rootComponent.childById(DropdownComponent.class, "skill-selection")
                .text(Text.translatable("skills.ques-mod.select_skill"))
                .button(Text.translatable("skills.ques-mod.mining"), button -> {
                    client.setScreen(new WoodcuttingSkillScreen());
                })
                .button(Text.translatable("skills.ques-mod.enchanting"), button -> {
                    client.setScreen(new EnchantingSkillScreen());
                })
                .button(Text.translatable("skills.ques-mod.combat"), button -> {
                    client.setScreen(new CombatSkillScreen());
                })
                .button(Text.translatable("skills.ques-mod.woodcutting"), button -> {
                    client.setScreen(new WoodcuttingSkillScreen());
                })
                .positioning(Positioning.absolute(10, 20));
    }
}
