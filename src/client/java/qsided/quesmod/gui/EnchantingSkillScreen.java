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

public class EnchantingSkillScreen extends BaseUIModelScreen<FlowLayout> {
    public static Integer enchantingLevel = 1;
    public static Float enchantingExperience = 0F;
    public EnchantingSkillScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(QuesMod.MOD_ID, "enchanting")));
    }
    
    public static void setEnchantingLevel(int level) {
        EnchantingSkillScreen.enchantingLevel = level;
    }
    public static void setEnchantingExperience(float experience) {
        EnchantingSkillScreen.enchantingExperience = experience;
    }
    
    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        rootComponent.childById(GridLayout.class, "enchanting")
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                0,
                0)
                .child(
                        Components.label(Text.of(String.valueOf(enchantingLevel)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        0,
                        2)
                .child(
                        Components.spacer(5),
                        0,
                        1)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_experience"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        1,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.enchanting.cost"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        3,
                        0);
        
        if (isBetween(enchantingLevel, 0, 32)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of("-" + 0))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            3,
                            2);
        } else if (isBetween(enchantingLevel, 33, 65)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of("-" + 1))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            3,
                            2);
        } else if (isBetween(enchantingLevel, 66, 100)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of("-" + 2))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            3,
                            2);
        }
        
        if (enchantingLevel < 100) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(df.format(enchantingExperience) + "/" + df.format(120 * (enchantingLevel * 3.8))))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.translatable("skills.ques-mod.max_level"))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        }
        
        rootComponent.childById(DropdownComponent.class, "skill-selection")
                .button(Text.translatable("skills.ques-mod.mining"), button -> {
                    client.setScreen(new MiningSkillScreen());
                })
                .button(Text.translatable("skills.ques-mod.enchanting"), button -> {
                    client.setScreen(new EnchantingSkillScreen());
                })
                .button(Text.translatable("skills.ques-mod.combat"), button -> {
                    client.setScreen(new CombatSkillScreen());
                })
                .button(Text.translatable("skills.ques-mod.alchemy"), button -> {
                
                })
                .positioning(Positioning.absolute(0, 0));
    }
}
