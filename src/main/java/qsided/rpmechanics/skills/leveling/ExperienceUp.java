package qsided.rpmechanics.skills.leveling;

import net.minecraft.util.ActionResult;
import qsided.rpmechanics.RoleplayMechanicsCommon;
import qsided.rpmechanics.events.IncreaseSkillExperienceCallback;
import qsided.rpmechanics.events.IncreaseSkillLevelCallback;

import java.text.DecimalFormat;

import static qsided.rpmechanics.RoleplayMechanicsCommon.sendSkillData;

public class ExperienceUp {
    public static void onExperienceUp() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        IncreaseSkillExperienceCallback.EVENT.register((player, state, skill, value) -> {
            if (state.skillLevels.getOrDefault(skill, 1) < 100) {
                state.skillExperience.put(skill, state.skillExperience.getOrDefault(skill, 0F) + value);
                state.expModifiers.forEach((id, modifier) -> {
                    if (modifier.containsKey(skill)) {
                        state.skillExperience.put(skill, state.skillExperience.getOrDefault(skill, 0F) + (value * (modifier.get(skill) / 100)));
                    }
                });
                
            }
            
            //player.sendMessage(Text.translatable("skills.ques-mod." + skill).append(Text.of(" +" + df.format(value) + "XP")).formatted(Formatting.BOLD, Formatting.GRAY), true);
            
            if (!RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.useGlobal()) {
                switch (skill) {
                    case "agility" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.agilityOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    case "combat" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.combatOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.combatOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.combatOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.combatOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.combatOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    case "crafting" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.craftingOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.craftingOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.craftingOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.craftingOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.craftingOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    case "enchanting" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enchantingOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enchantingOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enchantingOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enchantingOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enchantingOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    case "endurance" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enduranceOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enduranceOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enduranceOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enduranceOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.enduranceOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    case "mining" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.miningOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.miningOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.miningOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.miningOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.miningOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    case "woodcutting" -> {
                        switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.woodcuttingOptions.multiplicativeOrAdditive()) {
                            case ADDITIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.woodcuttingOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.woodcuttingOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                            case MULTIPLICATIVE -> {
                                if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.woodcuttingOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.woodcuttingOptions.amount())) {
                                    IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                                }
                            }
                        }
                    }
                    
                }
            } else {
                switch (RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.multiplicativeOrAdditive()) {
                    case ADDITIVE -> {
                        if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.baseExperience() + (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.amount())) {
                            IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                        }
                    }
                    case MULTIPLICATIVE -> {
                        if (state.skillExperience.getOrDefault(skill, 0F) >= RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.baseExperience() * (state.skillLevels.getOrDefault(skill, 1) * RoleplayMechanicsCommon.OWO_CONFIG.experienceOptions.globalOptions.amount())) {
                            IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1, true);
                        }
                    }
                }
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
    }
}
