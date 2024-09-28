package qsided.quesmod.config.experience_values;

import java.util.Map;

public class BlockExperience {
    
    public String id;
    public Map<String, Integer> experience;
    
    public BlockExperience() {
        super();
    }
    
    public BlockExperience(String id, Map<String, Integer> experience) {
        this.id = id;
        this.experience = experience;
    }
    
    public String getId() {
        return id;
    }
    
    public Map<String, Integer> getExperience() {
        return experience;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setExperience(Map<String, Integer> experience) {
        this.experience = experience;
    }
}
