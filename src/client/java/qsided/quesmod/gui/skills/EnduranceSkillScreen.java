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

public class EnduranceSkillScreen extends BaseUIModelScreen<FlowLayout> {
    public static Integer enduranceLevel = 1;
    public static Float enduranceExperience = 0F;
    public static Float maxHealth = 0F;
    
    public EnduranceSkillScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(QuesMod.MOD_ID, "endurance")));
    }
    
    public static void setMaxHealth(Float maxHealth) {
        EnduranceSkillScreen.maxHealth = maxHealth;
    }
    public static void setEnduranceLevel(int level) {
        EnduranceSkillScreen.enduranceLevel = level;
    }
    public static void setEnduranceExperience(float experience) {
        EnduranceSkillScreen.enduranceExperience = experience;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        rootComponent.childById(GridLayout.class, "endurance")
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                0,
                0)
                .child(
                        Components.label(Text.of(String.valueOf(enduranceLevel)))
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
                        Components.label(Text.translatable("skills.ques-mod.endurance.max_health"))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT),
                        2,
                        0)
                .child(
                        Components.label(Text.of(String.valueOf(maxHealth)))
                                .color(Color.ofArgb(0xd1d0cd))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        2,
                        2);
        
        if (enduranceLevel < 100) {
            rootComponent.childById(GridLayout.class, "endurance")
                    .child(
                            Components.label(Text.of(df.format(enduranceExperience) + "/" + df.format(60 * (enduranceLevel * 3.8))))
                                    .color(Color.ofArgb(0xd1d0cd))
                                    .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                            1,
                            2);
        } else {
            rootComponent.childById(GridLayout.class, "endurance")
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
