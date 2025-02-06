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

public class MiningSkillScreen extends BaseUIModelScreen<FlowLayout> {
    public static Integer miningLevel = 1;
    public static Float miningExperience = 0F;
    public MiningSkillScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(QuesMod.MOD_ID, "mining")));
    }
    
    public static void setMiningLevel(int level) {
        MiningSkillScreen.miningLevel = level;
    }
    public static void setMiningExperience(float experience) {
        MiningSkillScreen.miningExperience = experience;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        rootComponent.childById(GridLayout.class, "mining")
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                0,
                0)
                .child(
                        Components.label(Text.of(String.valueOf(miningLevel)))
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
                        Components.label(Text.translatable("skills.ques-mod.mining.efficiency"))
                            .horizontalTextAlignment(HorizontalAlignment.LEFT)
                                .tooltip(Text.translatable("skills.ques-mod.mining.efficiency.tooltip")),
                        2,
                        0)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.mining.degrade_chance"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT)
                                .tooltip(Text.translatable("skills.ques-mod.mining.degrade_chance.tooltip")),
                        3,
                        0)
                .child(
                        Components.label(Text.of("-" + miningLevel + "%"))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        3,
                        2);
        
        if (miningLevel.equals(1)) {
            rootComponent.childById(GridLayout.class, "mining")
                    .child(
                            Components.label(Text.of(String.valueOf(0)))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            2,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "mining")
                    .child(
                        Components.label(Text.of(String.valueOf(miningLevel * .5)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        2,
                        2);
        }
        
        if (miningLevel < 100) {
            rootComponent.childById(GridLayout.class, "mining")
                    .child(
                            Components.label(Text.of(df.format(miningExperience) + "/" + df.format(60 * (miningLevel * 3.8))))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "mining")
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
                .button(Text.translatable("skills.ques-mod.crafting"), button -> {
                    QuesModClient.setLastScreenOpen("crafting");
                    client.setScreen(new CraftingSkillScreen());
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
