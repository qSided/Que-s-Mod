package qsided.quesmod.gui.skills;

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
import qsided.quesmod.QuesModClient;

import java.text.DecimalFormat;

public class CombatSkillScreen extends BaseUIModelScreen<FlowLayout> {
    public static Integer combatLevel = 1;
    public static Float combatExperience = 0F;
    public CombatSkillScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(QuesMod.MOD_ID, "combat")));
    }
    
    public static void setCombatLevel(int level) {
        CombatSkillScreen.combatLevel = level;
    }
    public static void setCombatExperience(float experience) {
        CombatSkillScreen.combatExperience = experience;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        rootComponent.childById(GridLayout.class, "combat")
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                0,
                0)
                .child(
                        Components.label(Text.of(String.valueOf(combatLevel)))
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
                        Components.label(Text.translatable("skills.ques-mod.combat.attack_damage"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        2,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.combat.attack_speed"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        3,
                        0);
        
        if (combatLevel.equals(1)) {
            rootComponent.childById(GridLayout.class, "combat")
                    .child(
                            Components.label(Text.of(String.valueOf(0)))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "combat")
                    .child(
                            Components.label(Text.of(String.valueOf(combatLevel * .18)))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        }
        if (combatLevel.equals(1)) {
            rootComponent.childById(GridLayout.class, "combat")
                    .child(
                            Components.label(Text.of(String.valueOf(0)))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            3,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "combat")
                    .child(
                            Components.label(Text.of(String.valueOf(combatLevel * .03)))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            3,
                            2);
        }
        
        if (combatLevel < 100) {
            rootComponent.childById(GridLayout.class, "combat")
                    .child(
                            Components.label(Text.of(df.format(combatExperience) + "/" + df.format(60 * (combatLevel * 3.8))))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "combat")
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
                .button(Text.translatable("skills.ques-mod.woodcutting"), button -> {
                    QuesModClient.setLastScreenOpen("woodcutting");
                    client.setScreen(new WoodcuttingSkillScreen());
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
                .button(Text.translatable("skills.ques-mod.endurance"), button -> {
                    QuesModClient.setLastScreenOpen("endurance");
                    client.setScreen(new EnduranceSkillScreen());
                })
                .divider()
                .button(Text.translatable("skills.ques-mod.agility"), button -> {
                    QuesModClient.setLastScreenOpen("agility");
                    client.setScreen(new AgilitySkillScreen());
                })
                .positioning(Positioning.absolute(0, 0));
    }
}
