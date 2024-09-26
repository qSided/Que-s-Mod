package qsided.quesmod.data.requirements;

public class Requirements {
    String skill;
    Integer level;
    
    public Requirements() {
        super();
    }
    
    public Requirements(String skill, Integer level) {
        this.skill = skill;
        this.level = level;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public String getSkill() {
        return skill;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public void setSkill(String skill) {
        this.skill = skill;
    }
}
