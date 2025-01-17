package qsided.quesmod.config.roleplay_classes;

public class SkillModifier {
    
    String skill;
    Float percentage;
    
    public SkillModifier() {
        super();
    }
    
    public SkillModifier(String skill, Float percentage) {
        this.skill = skill;
        this.percentage = percentage;
    }
    
    public String getSkill() {
        return skill;
    }
    
    public void setSkill(String skill) {
        this.skill = skill;
    }
    
    public Float getPercentage() {
        return percentage;
    }
    
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}
