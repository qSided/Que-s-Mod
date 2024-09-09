package qsided.quesmod.gui;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Color;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

import java.text.DecimalFormat;
import java.util.HashMap;

public class DataSkillsScreen extends BaseUIModelScreen<FlowLayout> {
    public static HashMap<String, Integer> skillsLevels;
    public static HashMap<String, Float> skillsExperience;
    public DataSkillsScreen() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(QuesMod.MOD_ID, "skills")));
    }
    
    public static void setSkillsLevels(int mining, int enchanting, int combat, int alchemy) {
        HashMap<String, Integer> levels = new HashMap<>();
        levels.put("mining", mining);
        levels.put("enchanting", enchanting);
        levels.put("combat", combat);
        levels.put("alchemy", alchemy);
        DataSkillsScreen.skillsLevels = levels;
    }
    public static void setSkillsExperience(float mining, float enchanting, float combat, float alchemy) {
        HashMap<String, Float> experience = new HashMap<>();
        experience.put("mining", mining);
        experience.put("enchanting", enchanting);
        experience.put("combat", combat);
        experience.put("alchemy", alchemy);
        DataSkillsScreen.skillsExperience = experience;
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        
        int valueColor = 0xbbd543;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        rootComponent.childById(GridLayout.class, "grid")
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_level"))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        0,
                        0)
                .child(
                        Components.label(Text.of(String.valueOf(skillsLevels.getOrDefault("mining", 1))))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT)
                                .color(Color.ofArgb(valueColor)),
                        0,
                        1)
                .child(
                        Components.label(Text.translatable("skills.ques-mod.current_experience"))
                                .horizontalTextAlignment(HorizontalAlignment.RIGHT),
                        1,
                        0)
                .child(
                        Components.label(Text.of(df.format(skillsExperience.getOrDefault("mining", 0F)) + "/" + df.format(120 * (skillsLevels.getOrDefault("mining", 1) * 3.8))))
                                .horizontalTextAlignment(HorizontalAlignment.LEFT)
                                .color(Color.ofArgb(valueColor)),
                        1,
                        1)
                .child(
                        Components.spacer(15),
                        0,
                        2)
                .tooltip(Text.translatable("skills.descriptions.ques-mod.mining"));
    }
}
