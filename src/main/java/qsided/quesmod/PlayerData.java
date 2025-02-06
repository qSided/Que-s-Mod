package qsided.quesmod;

import qsided.quesmod.config.roleplay_classes.RoleplayClassState;

import java.util.HashMap;

public class PlayerData {
    
    public HashMap<String, Integer> skillLevels = new HashMap<>();
    public HashMap<String, Float> skillExperience = new HashMap<>();
    
    public HashMap<String, HashMap<String, Integer>> expModifiers = new HashMap<>();
    
    public String rpClass = "";
}
