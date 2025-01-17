package qsided.quesmod;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import qsided.quesmod.config.roleplay_classes.RoleplayClass;
import qsided.quesmod.config.roleplay_classes.SkillModifier;
import qsided.quesmod.config.roleplay_classes.StartingEquipment;
import qsided.quesmod.config.roleplay_classes.StartingSkill;

import java.util.List;

public class RoleplayClasses {
    public static final RoleplayClass PALADIN;
    public static final RoleplayClass RANGER;
    public static final RoleplayClass FIGHTER;
    
    static {
        PALADIN =
                new RoleplayClass(
                        "Paladin",
                        "#1a3078",
                        "Paladins train their bodies, minds, and spirits in order to protect themselves and their party. Some Paladins even wield powerful magic to buff themselves and their party.",
                        List.of(new StartingEquipment(Items.LEATHER_HELMET.toString(), 1),
                                new StartingEquipment(Items.LEATHER_CHESTPLATE.toString(), 1),
                                new StartingEquipment(Items.LEATHER_LEGGINGS.toString(), 1),
                                new StartingEquipment(Items.LEATHER_BOOTS.toString(), 1),
                                new StartingEquipment(Items.WOODEN_SWORD.toString(), 1),
                                new StartingEquipment(Items.SHIELD.toString(), 1),
                                new StartingEquipment(Items.COOKED_BEEF.toString(), 12)),
                        
                        List.of(new StartingSkill("endurance", 5)),
                        
                        List.of(new SkillModifier("endurance", 10F),
                                new SkillModifier("combat", 5F)));
        RANGER =
                new RoleplayClass(
                        "Ranger",
                        "#16703a",
                        "Needs finished! Got a suggestion for this? Join the Discord! This can also be edited in the config.",
                        List.of(new StartingEquipment(Items.LEATHER_CHESTPLATE.toString(), 1),
                                new StartingEquipment(Items.WOODEN_SWORD.toString(), 1)),
                        
                        List.of(new StartingSkill("agility", 5)),
                        
                        List.of(new SkillModifier("agility", 15F)));
        FIGHTER =
                new RoleplayClass(
                        "Fighter",
                        "#8f242b",
                        "Whether it's a bar brawl or an all out war, Fighters live for battle. While not skilled in magic or defense, Fighters are quite agile and hit hard.",
                        List.of(new StartingEquipment(Items.LEATHER_CHESTPLATE.toString(), 1),
                                new StartingEquipment(Items.WOODEN_SWORD.toString(), 1)),
                        
                        List.of(new StartingSkill("combat", 5)),
                        
                        List.of(new SkillModifier("combat", 10F),
                                new SkillModifier("agility", 5F)));
        
    }
}
