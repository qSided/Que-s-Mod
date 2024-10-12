package qsided.quesmod.gui;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Color;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import io.wispforest.owo.ui.core.Positioning;
import io.wispforest.owo.ui.core.Surface;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;
import qsided.quesmod.QuesModClient;

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
                        Components.label(Text.translatable("skills.ques-mod.current_experience"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        1,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.enchanting.cost"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT)
                                .tooltip(Text.translatable("skills.ques-mod.enchanting.cost.tooltip")),
                        2,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.enchanting.modifier"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT)
                                .tooltip(Text.translatable("skills.ques-mod.enchanting.modifier.tooltip")),
                        3,
                        0
                );
        
        if (isBetween(enchantingLevel, 20, 39)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(String.valueOf(1)))
                                    .color(Color.ofArgb(0xd1d0cd)),
                            3,
                            2
                    );
        } else if (isBetween(enchantingLevel, 40, 59)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(String.valueOf(2)))
                                    .color(Color.ofArgb(0xd1d0cd)),
                            3,
                            2
                    );
        } else if (isBetween(enchantingLevel, 60, 79)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(String.valueOf(3)))
                                    .color(Color.ofArgb(0xd1d0cd)),
                            3,
                            2
                    );
        } else if (isBetween(enchantingLevel, 80, 99)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(String.valueOf(4)))
                                    .color(Color.ofArgb(0xd1d0cd)),
                            3,
                            2
                    );
        } else if (enchantingLevel == 100) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(String.valueOf(5)))
                                    .color(Color.ofArgb(0xd1d0cd)),
                            3,
                            2
                    );
        } else {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(String.valueOf(0)))
                                    .color(Color.ofArgb(0xd1d0cd)),
                            3,
                            2
                    );
        }
        
        if (isBetween(enchantingLevel, 0, 32)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of("-" + 0))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        } else if (isBetween(enchantingLevel, 33, 65)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of("-" + 1))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        } else if (isBetween(enchantingLevel, 66, 100)) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of("-" + 2))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        }
        
        if (enchantingLevel < 100) {
            rootComponent.childById(GridLayout.class, "enchanting")
                    .child(
                            Components.label(Text.of(df.format(enchantingExperience) + "/" + df.format(60 * (enchantingLevel * 3.8))))
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
                .text(Text.translatable("skills.ques-mod.select_skill"))
                .divider()
                .button(Text.translatable("skills.ques-mod.mining"), button -> {
                    QuesModClient.setLastScreenOpen("mining");
                    client.setScreen(new MiningSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.ques-mod.enchanting"), button -> {
                    QuesModClient.setLastScreenOpen("enchanting");
                    client.setScreen(new EnchantingSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.ques-mod.combat"), button -> {
                    QuesModClient.setLastScreenOpen("combat");
                    client.setScreen(new CombatSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.ques-mod.woodcutting"), button -> {
                    QuesModClient.setLastScreenOpen("woodcutting");
                    client.setScreen(new WoodcuttingSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.ques-mod.endurance"), button -> {
                    QuesModClient.setLastScreenOpen("endurance");
                    client.setScreen(new EnduranceSkillScreen());
                })
                .positioning(Positioning.absolute(10, 10));
    }
}
